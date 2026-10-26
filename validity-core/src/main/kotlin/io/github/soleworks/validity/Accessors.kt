package io.github.soleworks.validity

import java.math.BigDecimal
import java.math.BigInteger
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.YearMonth
import java.util.UUID
import kotlin.enums.enumEntries

private const val NULL_KEY = "null"

public inline fun <T> ValidationNode<out Iterable<T>>.each(
    block: ValidationNode<T>.() -> Unit
): Unit = add(
    value.mapIndexed { index, item -> child(path.indexed(index), item).apply(block) }
)

@JvmName("eachArray")
public inline fun <T> ValidationNode<Array<T>>.each(
    block: ValidationNode<T>.() -> Unit
): Unit = add(
    value.mapIndexed { index, item -> child(path.indexed(index), item).apply(block) }
)

@JvmName("eachMap")
public inline fun <K, V> ValidationNode<out Map<K, V>>.each(
    block: ValidationNode<Map.Entry<K, V>>.() -> Unit
): Unit = add(
    value.entries.map { entry -> child(path.indexed(entry.key), entry).apply(block) }
)

public inline fun <K> ValidationNode<out Map<K, *>>.eachKey(
    block: ValidationNode<K>.() -> Unit
): Unit = add(
    value.keys.map { key -> child(path.indexed(key), key).apply(block) }
)

public inline fun <V> ValidationNode<out Map<*, V>>.eachValue(
    block: ValidationNode<V>.() -> Unit
): Unit = add(
    value.map { (key, item) -> child(path.indexed(key), item).apply(block) }
)

public fun <V : Validatable> ValidationNode<V>.valid(): Unit = valid(
    validation = { it.validation() }
)

public fun <C, V : ContextualValidatable<C>> ValidationNode<V>.valid(
    context: C
): Unit = valid(
    validation = { it.validation(context) }
)

public fun <V> ValidationNode<V>.valid(
    validation: suspend (V) -> Validation
) {
    val metadata = metadata

    add {
        validation(value).built().nodes.map { node -> node.relocated(path.nested(node.path), metadata) }
    }
}

public inline fun <V, T : Any> ValidationNode<V>.convert(
    converter: (V) -> T?,
    message: String = messages.convert,
    block: ValidationNode<T>.() -> Unit
): Unit = convert(
    converter = converter,
    message = message,
    code = "convert",
    block = block
)

public inline fun ValidationNode<String>.convertToBigDecimal(
    message: String = messages.convertToBigDecimal,
    block: ValidationNode<BigDecimal>.() -> Unit
): Unit = convert(
    converter = String::toBigDecimalOrNull,
    message = message,
    code = "convertToBigDecimal",
    block = block
)

public inline fun ValidationNode<String>.convertToInt(
    message: String = messages.convertToInt,
    block: ValidationNode<Int>.() -> Unit
): Unit = convert(
    converter = String::toIntOrNull,
    message = message,
    code = "convertToInt",
    block = block
)

public inline fun ValidationNode<String>.convertToLong(
    message: String = messages.convertToLong,
    block: ValidationNode<Long>.() -> Unit
): Unit = convert(
    converter = String::toLongOrNull,
    message = message,
    code = "convertToLong",
    block = block
)

public inline fun ValidationNode<String>.convertToBigInteger(
    message: String = messages.convertToBigInteger,
    block: ValidationNode<BigInteger>.() -> Unit
): Unit = convert(
    converter = String::toBigIntegerOrNull,
    message = message,
    code = "convertToBigInteger",
    block = block
)

public inline fun ValidationNode<String>.convertToDouble(
    message: String = messages.convertToDouble,
    block: ValidationNode<Double>.() -> Unit
): Unit = convert(
    converter = String::toDoubleOrNull,
    message = message,
    code = "convertToDouble",
    block = block
)

public inline fun ValidationNode<String>.convertToBoolean(
    message: String = messages.convertToBoolean,
    block: ValidationNode<Boolean>.() -> Unit
): Unit = convert(
    converter = String::toBooleanStrictOrNull,
    message = message,
    code = "convertToBoolean",
    block = block
)

public inline fun ValidationNode<String>.convertToUuid(
    message: String = messages.convertToUuid,
    block: ValidationNode<UUID>.() -> Unit
): Unit = convert(
    converter = String::toUuidOrNull,
    message = message,
    code = "convertToUuid",
    block = block
)

public inline fun <reified E : Enum<E>> ValidationNode<String>.convertToEnum(
    ignoreCase: Boolean = false,
    message: String = messages.convertToEnum,
    block: ValidationNode<E>.() -> Unit
): Unit = convert(
    converter = { text -> enumEntries<E>().firstOrNull { it.name.equals(text, ignoreCase) } },
    message = message.filled("values" to enumEntries<E>().map { it.name }),
    code = "convertToEnum",
    block = block
)

public inline fun ValidationNode<String>.convertToLocalDate(
    message: String = messages.convertToLocalDate,
    block: ValidationNode<LocalDate>.() -> Unit
): Unit = convert(
    converter = { text -> runCatching { LocalDate.parse(text) }.getOrNull() },
    message = message,
    code = "convertToLocalDate",
    block = block
)

public inline fun ValidationNode<String>.convertToLocalDateTime(
    message: String = messages.convertToLocalDateTime,
    block: ValidationNode<LocalDateTime>.() -> Unit
): Unit = convert(
    converter = { text -> runCatching { LocalDateTime.parse(text) }.getOrNull() },
    message = message,
    code = "convertToLocalDateTime",
    block = block
)

public inline fun ValidationNode<String>.convertToLocalTime(
    message: String = messages.convertToLocalTime,
    block: ValidationNode<LocalTime>.() -> Unit
): Unit = convert(
    converter = { text -> runCatching { LocalTime.parse(text) }.getOrNull() },
    message = message,
    code = "convertToLocalTime",
    block = block
)

public inline fun ValidationNode<String>.convertToOffsetDateTime(
    message: String = messages.convertToOffsetDateTime,
    block: ValidationNode<OffsetDateTime>.() -> Unit
): Unit = convert(
    converter = { text -> runCatching { OffsetDateTime.parse(text) }.getOrNull() },
    message = message,
    code = "convertToOffsetDateTime",
    block = block
)

public inline fun ValidationNode<String>.convertToInstant(
    message: String = messages.convertToInstant,
    block: ValidationNode<Instant>.() -> Unit
): Unit = convert(
    converter = { text -> runCatching { Instant.parse(text) }.getOrNull() },
    message = message,
    code = "convertToInstant",
    block = block
)

public inline fun ValidationNode<String>.convertToYearMonth(
    message: String = messages.convertToYearMonth,
    block: ValidationNode<YearMonth>.() -> Unit
): Unit = convert(
    converter = { text -> runCatching { YearMonth.parse(text) }.getOrNull() },
    message = message,
    code = "convertToYearMonth",
    block = block
)

public inline fun ValidationNode<String>.convertToDuration(
    message: String = messages.convertToDuration,
    block: ValidationNode<Duration>.() -> Unit
): Unit = convert(
    converter = { text -> runCatching { Duration.parse(text) }.getOrNull() },
    message = message,
    code = "convertToDuration",
    block = block
)

@PublishedApi
internal inline fun <V, T : Any> ValidationNode<V>.convert(
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
        add(listOf(child(path, converted).apply(block)))
}

@PublishedApi
internal fun String.toUuidOrNull(): UUID? = runCatching { UUID.fromString(this) }
    .getOrNull()
    ?.takeIf { uuid -> uuid.toString().equals(this, ignoreCase = true) }

@PublishedApi
internal fun <T> ValidationNode<*>.child(
    path: String,
    value: T
): ValidationNode<T> {
    val child = ValidationNode(path, value)

    child.operator = operator
    child.metadata = metadata

    return child
}

private fun String.nested(
    path: String
): String = if (path.isEmpty())
    this
else
    "$this.$path"

@PublishedApi
internal fun String.indexed(
    key: Any?
): String = "$this[${key ?: NULL_KEY}]"
