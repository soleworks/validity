package io.github.soleworks.validity

@ValidityDsl
public class ValidationNode<V>(
    @PublishedApi
    internal val path: String,
    @PublishedApi
    internal val value: V
) {
    private val rules = mutableListOf<ValidationNodeRule<V>>()

    @PublishedApi
    internal var operator: String? = null

    @PublishedApi
    internal var metadata: Map<String, Any?> = emptyMap()

    public fun constraint(
        message: String,
        code: String = "",
        predicate: suspend (V) -> Boolean
    ): Unit = constraint(
        message = { message },
        code = code,
        predicate = predicate
    )

    public fun constraint(
        message: suspend (V) -> String,
        code: String = "",
        predicate: suspend (V) -> Boolean
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

    public inline fun metadata(
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

    public inline fun failFast(
        block: ValidationNode<V>.() -> Unit
    ): Unit = chain(child(path, value).apply(block))

    internal fun add(
        constraint: Constraint<V>
    ) {
        rules += ValidationNodeRule.Check(constraint)
    }

    @PublishedApi
    internal fun add(
        nodes: List<ValidationNode<*>>
    ) {
        rules += ValidationNodeRule.Group { nodes }
    }

    internal fun add(
        group: suspend () -> List<ValidationNode<*>>
    ) {
        rules += ValidationNodeRule.Group(group)
    }

    @PublishedApi
    internal fun chain(
        node: ValidationNode<V>
    ) {
        rules += ValidationNodeRule.Chain(node)
    }

    internal suspend fun violations(): List<Violation> = branches().flatten()

    internal suspend fun firstFailure(): List<Violation> {
        for (rule in rules) {
            val violations = rule.validate(path, value)

            if (violations.isNotEmpty())
                return violations
        }

        return emptyList()
    }

    internal suspend fun branches(): List<List<Violation>> =
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
