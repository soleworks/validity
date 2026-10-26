package io.github.soleworks.validity

internal sealed interface ValidationNodeRule<V> {
    fun validate(
        path: String,
        value: V
    ): List<Violation>

    fun relocated(
        from: String,
        to: String,
        metadata: Map<String, Any?>
    ): ValidationNodeRule<V>

    class Check<V>(
        private val constraint: Constraint<V>
    ) : ValidationNodeRule<V> {
        override fun validate(
            path: String,
            value: V
        ): List<Violation> = constraint.validate(path, value)

        override fun relocated(
            from: String,
            to: String,
            metadata: Map<String, Any?>
        ): ValidationNodeRule<V> = Check(constraint.inheriting(metadata))
    }

    class Group<V>(
        private val nodes: List<ValidationNode<*>>
    ) : ValidationNodeRule<V> {
        override fun validate(
            path: String,
            value: V
        ): List<Violation> = nodes.flatMap { node -> node.validate() }

        override fun relocated(
            from: String,
            to: String,
            metadata: Map<String, Any?>
        ): ValidationNodeRule<V> = Group(
            nodes.map { node -> node.relocated(to + node.path.removePrefix(from), metadata) }
        )
    }

    class Chain<V>(
        private val node: ValidationNode<V>
    ) : ValidationNodeRule<V> {
        override fun validate(
            path: String,
            value: V
        ): List<Violation> = node.firstFailure()

        override fun relocated(
            from: String,
            to: String,
            metadata: Map<String, Any?>
        ): ValidationNodeRule<V> = Chain(node.relocated(to + node.path.removePrefix(from), metadata))
    }
}
