package io.github.soleworks.validity

import kotlin.reflect.KProperty0

context(validation: Validation)
public infix fun <V : Any> KProperty0<V?>.required(
    block: ValidationNode<V>.() -> Unit
): Unit = required(
    message = messages.required,
    block = block
)

context(validation: Validation)
public fun <V : Any> KProperty0<V?>.required(
    message: String,
    block: ValidationNode<V>.() -> Unit
) {
    val value = get()

    if (value == null)
        return validation.add(
            ValidationNode<V?>(name, null).apply {
                constraint(
                    message = message,
                    code = "required",
                    predicate = { it != null }
                )
            }
        )

    val node = ValidationNode(name, value)

    validation.add(node)

    node.apply(block)
}

context(validation: Validation)
public infix fun <V : Any> KProperty0<V?>.ifPresent(
    block: ValidationNode<V>.() -> Unit
) {
    val value = get() ?: return

    val node = ValidationNode(name, value)

    validation.add(node)

    node.apply(block)
}

context(validation: Validation)
public fun KProperty0<*>.forbidden(
    message: String = messages.forbidden
): Unit = validation.add(
    ValidationNode(name, get()).apply {
        constraint(
            message = message,
            code = "forbidden",
            predicate = { it == null }
        )
    }
)

context(validation: Validation)
public fun atLeastOneOf(
    vararg properties: KProperty0<*>,
    message: String = messages.atLeastOneOf
): Unit = presence(
    properties = properties,
    message = message,
    code = "atLeastOneOf",
    accepts = { filled, _ -> filled >= 1 }
)

context(validation: Validation)
public fun atMostOneOf(
    vararg properties: KProperty0<*>,
    message: String = messages.atMostOneOf
): Unit = presence(
    properties = properties,
    message = message,
    code = "atMostOneOf",
    accepts = { filled, value -> value == null || filled <= 1 }
)

context(validation: Validation)
public fun exactlyOneOf(
    vararg properties: KProperty0<*>,
    message: String = messages.exactlyOneOf
): Unit = presence(
    properties = properties,
    message = message,
    code = "exactlyOneOf",
    accepts = { filled, value -> filled == 1 || (filled > 1 && value == null) }
)

context(validation: Validation)
private fun presence(
    properties: Array<out KProperty0<*>>,
    message: String,
    code: String,
    accepts: (filled: Int, value: Any?) -> Boolean
) {
    val values = properties.map { it.get() }
    val filled = values.count { it != null }
    val fields = properties.joinToString { it.name }

    properties.zip(values).forEach { (property, value) ->
        validation.add(
            ValidationNode(property.name, value).apply {
                constraint(
                    message = message.replace("{fields}", fields),
                    code = code,
                    predicate = { accepts(filled, it) }
                )
            }
        )
    }
}
