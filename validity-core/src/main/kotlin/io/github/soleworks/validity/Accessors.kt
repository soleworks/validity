package io.github.soleworks.validity

import java.math.BigDecimal
import java.time.LocalDate

private const val NULL_KEY = "null"

public fun <T> ValidationNode<out Iterable<T>>.each(
    block: ValidationNode<T>.() -> Unit
): Unit = add(
    value.mapIndexed { index, item -> child(path.indexed(index), item, block) }
)

@JvmName("eachArray")
public fun <T> ValidationNode<Array<T>>.each(
    block: ValidationNode<T>.() -> Unit
): Unit = add(
    value.mapIndexed { index, item -> child(path.indexed(index), item, block) }
)

@JvmName("eachMap")
public fun <K, V> ValidationNode<out Map<K, V>>.each(
    block: ValidationNode<Map.Entry<K, V>>.() -> Unit
): Unit = add(
    value.entries.map { entry -> child(path.indexed(entry.key), entry, block) }
)

public fun <K> ValidationNode<out Map<K, *>>.eachKey(
    block: ValidationNode<K>.() -> Unit
): Unit = add(
    value.keys.map { key -> child(path.indexed(key), key, block) }
)

public fun <V> ValidationNode<out Map<*, V>>.eachValue(
    block: ValidationNode<V>.() -> Unit
): Unit = add(
    value.map { (key, item) -> child(path.indexed(key), item, block) }
)

public fun <V : Validatable> ValidationNode<V>.valid(): Unit = valid(
    validation = { it.validation() }
)

public fun <V> ValidationNode<V>.valid(
    validation: (V) -> Validation
): Unit = add(
    validation(value).nodes.map { node -> node.relocated(path.nested(node.path), metadata) }
)

public fun <V, T : Any> ValidationNode<V>.convert(
    converter: (V) -> T?,
    message: String = messages.convert,
    block: ValidationNode<T>.() -> Unit
): Unit = convert(
    converter = converter,
    message = message,
    code = "convert",
    block = block
)

public fun ValidationNode<String>.convertToBigDecimal(
    message: String = messages.convertToBigDecimal,
    block: ValidationNode<BigDecimal>.() -> Unit
): Unit = convert(
    converter = String::toBigDecimalOrNull,
    message = message,
    code = "convertToBigDecimal",
    block = block
)

public fun ValidationNode<String>.convertToInt(
    message: String = messages.convertToInt,
    block: ValidationNode<Int>.() -> Unit
): Unit = convert(
    converter = String::toIntOrNull,
    message = message,
    code = "convertToInt",
    block = block
)

public fun ValidationNode<String>.convertToLong(
    message: String = messages.convertToLong,
    block: ValidationNode<Long>.() -> Unit
): Unit = convert(
    converter = String::toLongOrNull,
    message = message,
    code = "convertToLong",
    block = block
)

public fun ValidationNode<String>.convertToLocalDate(
    message: String = messages.convertToLocalDate,
    block: ValidationNode<LocalDate>.() -> Unit
): Unit = convert(
    converter = { text -> runCatching { LocalDate.parse(text) }.getOrNull() },
    message = message,
    code = "convertToLocalDate",
    block = block
)

private fun <V, T : Any> ValidationNode<V>.convert(
    converter: (V) -> T?,
    message: String,
    code: String,
    block: ValidationNode<T>.() -> Unit
) {
    val converted = converter(value)

    constraint(
        message = message,
        code = code,
        predicate = { converted != null }
    )

    if (converted != null)
        add(listOf(child(path, converted, block)))
}

private fun <T> ValidationNode<*>.child(
    path: String,
    value: T,
    block: ValidationNode<T>.() -> Unit
): ValidationNode<T> {
    val child = ValidationNode(path, value)

    child.operator = operator
    child.metadata = metadata

    return child.apply(block)
}

private fun String.nested(
    path: String
): String = if (path.isEmpty())
    this
else
    "$this.$path"

private fun String.indexed(
    key: Any?
): String = "$this[${key ?: NULL_KEY}]"
