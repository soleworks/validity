package io.github.soleworks.validity

import kotlin.reflect.KProperty0

@ValidityDsl
public class Validation internal constructor() {
    internal val nodes = mutableListOf<ValidationNode<*>>()

    internal var metadata: Map<String, Any?> = emptyMap()

    public fun add(
        node: ValidationNode<*>
    ) {
        nodes += node
    }

    public fun constraint(
        property: KProperty0<*>,
        message: String,
        code: String = "",
        predicate: () -> Boolean
    ): Unit = constraint(
        path = property.name,
        value = property.get(),
        message = message,
        code = code,
        predicate = predicate
    )

    public fun constraint(
        path: String = "",
        message: String,
        code: String = "",
        predicate: () -> Boolean
    ): Unit = constraint(
        path = path,
        value = null,
        message = message,
        code = code,
        predicate = predicate
    )

    public fun metadata(
        vararg entries: Pair<String, Any?>,
        block: Validation.() -> Unit
    ) {
        val outer = metadata

        metadata = outer + entries

        try {
            block()
        } finally {
            metadata = outer
        }
    }

    public fun validate(): ValidationResult = ValidationResult(
        violations = nodes.flatMap { it.validate() }
    )

    private fun constraint(
        path: String,
        value: Any?,
        message: String,
        code: String,
        predicate: () -> Boolean
    ) {
        val node = ValidationNode(path = path, value = value).also { node -> node.metadata = metadata }

        add(node)

        node.constraint(
            message = message,
            code = code,
            predicate = { predicate() }
        )
    }
}

public fun validation(
    block: Validation.() -> Unit
): Validation = Validation().apply(block)
