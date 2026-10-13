package io.github.soleworks.validity

@ValidityDsl
public class ValidationNode<V>(
    internal val path: String,
    internal val value: V
) {
    private val constraints = mutableListOf<Constraint<V>>()
    private val groups = mutableListOf<List<ValidationNode<*>>>()

    internal var operator: String? = null

    internal var metadata: Any? = null

    public fun constraint(
        message: String,
        code: String = "",
        predicate: (V) -> Boolean
    ): Unit = constraint(
        message = { message },
        code = code,
        predicate = predicate
    )

    public fun constraint(
        message: (V) -> String,
        code: String = "",
        predicate: (V) -> Boolean
    ) {
        val metadata = metadata

        add(
            Constraint { path, value ->
                if (predicate(value))
                    null
                else
                    Violation(path, message(value).filled(path, value), code, metadata)
            }
        )
    }

    public fun metadata(
        metadata: Any?,
        block: ValidationNode<V>.() -> Unit
    ) {
        val outer = this.metadata

        this.metadata = metadata

        try {
            block()
        } finally {
            this.metadata = outer
        }
    }

    internal fun constraint(
        message: (V) -> String,
        code: String,
        skipped: Boolean,
        predicate: (V) -> Boolean
    ) {
        if (skipped)
            return

        constraint(
            message = message,
            code = code,
            predicate = predicate
        )
    }

    internal fun add(
        constraint: Constraint<V>
    ) {
        constraints += constraint
    }

    internal fun add(
        nodes: List<ValidationNode<*>>
    ) {
        groups += nodes
    }

    internal fun validate(): List<Violation> = branches().flatten()

    internal fun branches(): List<List<Violation>> =
        constraints.map { it.validate(path, value) } + groups.map { nodes -> nodes.flatMap { it.validate() } }

    internal fun relocated(
        path: String,
        metadata: Any? = null
    ): ValidationNode<V> = if (path == this.path && metadata == null)
        this
    else
        ValidationNode(path, value).also {
            it.constraints += if (metadata == null) constraints else constraints.map { constraint -> constraint.inheriting(metadata) }
            it.groups += groups.map { nodes ->
                nodes.map { node -> node.relocated(path + node.path.removePrefix(this.path), metadata) }
            }
        }
}
