package io.github.soleworks.validity

@ValidityDsl
public class ValidationNode<V>(
    @PublishedApi
    internal val path: String,
    @PublishedApi
    internal val value: V
) {
    private val constraints = mutableListOf<Constraint<V>>()
    private val groups = mutableListOf<suspend () -> List<ValidationNode<*>>>()

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

    internal fun add(
        constraint: Constraint<V>
    ) {
        constraints += constraint
    }

    @PublishedApi
    internal fun add(
        nodes: List<ValidationNode<*>>
    ) {
        groups += { nodes }
    }

    internal fun add(
        group: suspend () -> List<ValidationNode<*>>
    ) {
        groups += group
    }

    internal suspend fun violations(): List<Violation> = branches().flatten()

    internal suspend fun branches(): List<List<Violation>> =
        constraints.map { it.validate(path, value) } + groups.map { group -> group().flatMap { it.violations() } }

    internal fun relocated(
        path: String,
        metadata: Map<String, Any?> = emptyMap()
    ): ValidationNode<V> = if (path == this.path && metadata.isEmpty())
        this
    else
        ValidationNode(path, value).also { relocated ->
            relocated.constraints += constraints.map { constraint -> constraint.inheriting(metadata) }
            relocated.groups += groups.map { group ->
                suspend { group().map { node -> node.relocated(path + node.path.removePrefix(this.path), metadata) } }
            }
        }
}
