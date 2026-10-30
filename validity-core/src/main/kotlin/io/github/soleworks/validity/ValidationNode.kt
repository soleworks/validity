package io.github.soleworks.validity

/**
 * The rules of one value, and the receiver of every constraint.
 *
 * The block of [required][io.github.soleworks.validity.required], [each][io.github.soleworks.validity.each] or
 * [convertToInt][io.github.soleworks.validity.convertToInt] runs on a node, so `minLength(3)` inside it is a rule of
 * that value. Its violations have the path of the node.
 *
 * A constraint is an extension of `ValidationNode<V>` that calls [constraint]:
 *
 * ```kotlin
 * fun ValidationNode<String>.sku(message: String = "must be a valid SKU"): Unit = constraint(
 *     message = message,
 *     code = "sku",
 *     predicate = { SKU_FORMAT.matches(it) }
 * )
 * ```
 *
 * @param V the type of the value.
 * @property path where the value is, such as `items[1].quantity`. It is the path of its violations.
 * @property value the value the rules check.
 */
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

    /**
     * Checks that [predicate] holds for the value.
     *
     * ```kotlin
     * ::email required {
     *     constraint("is already registered", code = "registered") { !Accounts.exists(it) }
     * }
     * ```
     *
     * @param message the message of the violation. `{path}` and `{value}` are filled in.
     * @param code the code of the violation, empty by default. The built-in constraints use their name.
     * @param predicate returns `true` when the value is valid.
     */
    public fun constraint(
        message: String,
        code: String = "",
        predicate: suspend (V) -> Boolean
    ): Unit = constraint(
        message = { message },
        code = code,
        predicate = predicate
    )

    /**
     * Checks that [predicate] holds for the value, with a message built from the value.
     *
     * @param message builds the message from the value. `{path}` and `{value}` in what it returns are filled in.
     * @param code the code of the violation, empty by default. The built-in constraints use their name.
     * @param predicate returns `true` when the value is valid.
     */
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

    /**
     * Attaches [entries] to the [metadata][Violation.metadata] of every violation of the constraints declared in
     * [block].
     *
     * Nested calls add to the entries around them, and an inner entry replaces an outer one with the same key.
     *
     * ```kotlin
     * ::amount required {
     *     metadata("operationId" to "op-1") { min(10) }
     * }
     * ```
     *
     * @param entries the keys and values to attach.
     * @param block the constraints whose violations get them.
     */
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

    /**
     * Runs the constraints of [block] in order and stops at the first that fails, so the ones after it are not checked.
     *
     * Use it when a rule only makes sense after another, such as a lookup after a format check. The constraints after
     * the block run as usual. Each call in the block is one step: an [each][io.github.soleworks.validity.each] or a
     * [valid][io.github.soleworks.validity.valid] counts once, with all its elements.
     *
     * ```kotlin
     * ::email required {
     *     failFast {
     *         email()
     *         constraint("is already registered", code = "registered") { !Accounts.exists(it) }
     *     }
     * }
     * ```
     *
     * It skips constraints, not code: work written in the block but outside a constraint runs every time.
     *
     * @param block the constraints, in the order they run.
     */
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
