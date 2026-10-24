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
                Violation(
                    path = path,
                    message = message(value).filled("path" to path, "value" to value),
                    code = code
                )
        }
    )

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
        path: String
    ): ValidationNode<V> = if (path == this.path)
        this
    else
        ValidationNode(path, value).also { relocated ->
            relocated.constraints += constraints
            relocated.groups += groups.map { nodes ->
                nodes.map { node -> node.relocated(path + node.path.removePrefix(this.path)) }
            }
        }
}
