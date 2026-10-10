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
