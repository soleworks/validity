package io.github.soleworks.validity

@ValidityDsl
public class ValidationNode<V>(
    internal val path: String,
    internal val value: V
) {
    private val constraints = mutableListOf<Constraint<V>>()
    private val groups = mutableListOf<List<ValidationNode<*>>>()

    internal var operator: String? = null

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
    ): Unit = add(
        Constraint { path, value ->
            if (predicate(value))
                null
            else
                Violation(path, message(value), code)
        }
    )

    internal fun constraint(
        message: String,
        code: String,
        skipped: Boolean,
        predicate: (V) -> Boolean
    ): Unit = constraint(
        message = { message },
        code = code,
        skipped = skipped,
        predicate = predicate
    )

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

    internal fun add(constraint: Constraint<V>) {
        constraints += constraint
    }

    internal fun add(nodes: List<ValidationNode<*>>) {
        groups += nodes
    }

    internal fun validate(): List<Violation> =
        constraints.flatMap { it.validate(path, value) } + groups.flatMap { nodes -> nodes.flatMap { it.validate() } }

    internal fun branches(): List<List<Violation>> =
        constraints.map { it.validate(path, value) } + groups.map { nodes -> nodes.flatMap { it.validate() } }

    internal fun prefixed(prefix: String): ValidationNode<V> = ValidationNode("$prefix.$path", value).also {
        it.constraints += constraints
        it.groups += groups.map { nodes -> nodes.map { node -> node.prefixed(prefix) } }
    }
}
