package io.github.soleworks.validity

@ValidityDsl
public class ValidationNode<V>(
    internal val path: String,
    internal val value: V
) {
    private val rules = mutableListOf<ValidationNodeRule<V>>()

    internal var operator: String? = null

    internal var metadata: Map<String, Any?> = emptyMap()

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
                    Violation(
                        path = path,
                        message = message(value).filled("path" to path, "value" to value),
                        code = code,
                        metadata = metadata
                    )
            }
        )
    }

    public fun metadata(
        vararg entries: Pair<String, Any?>,
        block: ValidationNode<V>.() -> Unit
    ) {
        val outer = metadata

        metadata = outer + entries

        try {
            block()
        } finally {
            metadata = outer
        }
    }

    public fun failFast(
        block: ValidationNode<V>.() -> Unit
    ) {
        val rules = ValidationNode(path, value).also { rules ->
            rules.operator = operator
            rules.metadata = metadata
        }

        this.rules += ValidationNodeRule.Chain(rules.apply(block))
    }

    internal fun add(
        constraint: Constraint<V>
    ) {
        rules += ValidationNodeRule.Check(constraint)
    }

    internal fun add(
        nodes: List<ValidationNode<*>>
    ) {
        rules += ValidationNodeRule.Group(nodes)
    }

    internal fun validate(): List<Violation> = branches().flatten()

    internal fun firstFailure(): List<Violation> = rules
        .asSequence()
        .map { rule -> rule.validate(path, value) }
        .firstOrNull { violations -> violations.isNotEmpty() }
        .orEmpty()

    internal fun branches(): List<List<Violation>> =
        rules.filterNot { rule -> rule is ValidationNodeRule.Group }.map { rule -> rule.validate(path, value) } +
            rules.filterIsInstance<ValidationNodeRule.Group<V>>().map { rule -> rule.validate(path, value) }

    internal fun relocated(
        path: String,
        metadata: Map<String, Any?> = emptyMap()
    ): ValidationNode<V> = if (path == this.path && metadata.isEmpty())
        this
    else
        ValidationNode(path, value).also { relocated ->
            relocated.rules += rules.map { rule -> rule.relocated(from = this.path, to = path, metadata = metadata) }
        }
}
