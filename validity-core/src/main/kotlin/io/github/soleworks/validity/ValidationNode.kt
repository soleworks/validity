package io.github.soleworks.validity

@ValidityDsl
public class ValidationNode<V>(
    internal val path: String,
    internal val value: V
) {
    private val constraints = mutableListOf<Constraint<V>>()
    private val nodes = mutableListOf<ValidationNode<*>>()

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
        constraints += Constraint(message, code, predicate)
    }

    internal fun add(node: ValidationNode<*>) {
        nodes += node
    }

    internal fun validate(): List<Violation> =
        constraints.flatMap { it.validate(path, value) } + nodes.flatMap { it.validate() }

    internal fun prefixed(prefix: String): ValidationNode<V> = ValidationNode("$prefix.$path", value).also {
        it.constraints += constraints
        it.nodes += nodes.map { node -> node.prefixed(prefix) }
    }
}
