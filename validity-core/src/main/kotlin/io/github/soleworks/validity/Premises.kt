package io.github.soleworks.validity

import kotlin.reflect.KProperty0

context(validation: Validation)
public inline infix fun <V : Any> KProperty0<V?>.required(
    block: ValidationNode<V>.() -> Unit
): Unit = required(
    message = messages.required,
    block = block
)

context(validation: Validation)
public inline fun <V : Any> KProperty0<V?>.required(
    message: String,
    block: ValidationNode<V>.() -> Unit
): Unit = required(
    path = name,
    value = get(),
    message = message,
    block = block
)

context(validation: Validation)
public inline fun <V : Any> required(
    path: String,
    value: V?,
    message: String = messages.required,
    block: ValidationNode<V>.() -> Unit
) {
    if (value == null)
        return validation.add<V?>(
            path = path,
            value = null,
            message = message,
            code = "required",
            predicate = { it != null }
        )

    validation.node(path = path, value = value).block()
}

context(validation: Validation)
public inline infix fun <V : Any> KProperty0<V?>.ifPresent(
    block: ValidationNode<V>.() -> Unit
): Unit = ifPresent(
    path = name,
    value = get(),
    block = block
)

context(validation: Validation)
public inline fun <V : Any> ifPresent(
    path: String,
    value: V?,
    block: ValidationNode<V>.() -> Unit
) {
    if (value != null)
        validation.node(path = path, value = value).block()
}

context(validation: Validation)
public fun KProperty0<*>.forbidden(
    message: String = messages.forbidden
): Unit = validation.add(
    path = name,
    value = get(),
    message = message,
    code = "forbidden",
    predicate = { it == null }
)

context(validation: Validation)
public fun atLeastOneOf(
    vararg properties: KProperty0<*>,
    path: String = "",
    message: String = messages.atLeastOneOf
): Unit = presence(
    properties = properties,
    path = path,
    message = message,
    code = "atLeastOneOf",
    accepts = { filled, _ -> filled >= 1 }
)

context(validation: Validation)
public fun atMostOneOf(
    vararg properties: KProperty0<*>,
    path: String = "",
    message: String = messages.atMostOneOf
): Unit = presence(
    properties = properties,
    path = path,
    message = message,
    code = "atMostOneOf",
    accepts = { filled, _ -> filled <= 1 }
)

context(validation: Validation)
public fun exactlyOneOf(
    vararg properties: KProperty0<*>,
    path: String = "",
    message: String = messages.exactlyOneOf
): Unit = presence(
    properties = properties,
    path = path,
    message = message,
    code = "exactlyOneOf",
    accepts = { filled, _ -> filled == 1 }
)

context(validation: Validation)
public fun allOrNoneOf(
    vararg properties: KProperty0<*>,
    path: String = "",
    message: String = messages.allOrNoneOf
): Unit = presence(
    properties = properties,
    path = path,
    message = message,
    code = "allOrNoneOf",
    accepts = { filled, total -> filled == 0 || filled == total }
)

context(validation: Validation)
public fun atLeastOf(
    count: Int,
    vararg properties: KProperty0<*>,
    path: String = "",
    message: String = messages.atLeastOf
): Unit = presence(
    properties = properties.counting(count, code = "atLeastOf"),
    path = path,
    message = message.filled("count" to count),
    code = "atLeastOf",
    accepts = { filled, _ -> filled >= count }
)

context(validation: Validation)
public fun atMostOf(
    count: Int,
    vararg properties: KProperty0<*>,
    path: String = "",
    message: String = messages.atMostOf
): Unit = presence(
    properties = properties.counting(count, code = "atMostOf"),
    path = path,
    message = message.filled("count" to count),
    code = "atMostOf",
    accepts = { filled, _ -> filled <= count }
)

context(validation: Validation)
public fun exactlyOf(
    count: Int,
    vararg properties: KProperty0<*>,
    path: String = "",
    message: String = messages.exactlyOf
): Unit = presence(
    properties = properties.counting(count, code = "exactlyOf"),
    path = path,
    message = message.filled("count" to count),
    code = "exactlyOf",
    accepts = { filled, _ -> filled == count }
)

private fun Array<out KProperty0<*>>.counting(
    count: Int,
    code: String
): Array<out KProperty0<*>> = also {
    require(count in 0..distinct().size) { "$code needs a count between 0 and the number of properties" }
}

context(validation: Validation)
private fun presence(
    properties: Array<out KProperty0<*>>,
    path: String,
    message: String,
    code: String,
    accepts: (filled: Int, total: Int) -> Boolean
) {
    require(properties.isNotEmpty()) { "$code needs at least one property" }

    val distinct = properties.distinct()
    val names = distinct.map { it.name }
    val filled = distinct.filter { it.get() != null }.map { it.name }
    val isShort = (filled.size + 1..names.size).any { count -> accepts(count, names.size) }
    val fields = if (isShort) names - filled.toSet() else filled

    validation.metadata("fields" to fields) {
        constraint(
            path = path,
            message = message.filled("fields" to names),
            code = code,
            predicate = { accepts(filled.size, names.size) }
        )
    }
}

@PublishedApi
internal fun <V> Validation.add(
    path: String,
    value: V,
    message: String,
    code: String,
    predicate: suspend (V) -> Boolean
) {
    node(path = path, value = value).constraint(
        message = message,
        code = code,
        predicate = predicate
    )
}

@PublishedApi
internal fun <V> Validation.node(
    path: String,
    value: V
): ValidationNode<V> = ValidationNode(path = path, value = value).also { node ->
    node.metadata = metadata
    add(node)
}
