package io.github.soleworks.validity

import kotlin.reflect.KProperty0

/**
 * The rules of an object, written with [validation].
 *
 * Inside the block, [required][io.github.soleworks.validity.required],
 * [ifPresent][io.github.soleworks.validity.ifPresent]
 * and [forbidden][io.github.soleworks.validity.forbidden] start the rules of a property, the presence rules such as
 * [atLeastOneOf][io.github.soleworks.validity.atLeastOneOf] relate several properties, and [constraint] checks anything
 * about the object. Nothing is checked until [validate] runs.
 */
@ValidityDsl
public class Validation internal constructor(
    private val block: Validation.() -> Unit
) {
    private val validity: Validity = Validity.current

    internal val nodes = mutableListOf<ValidationNode<*>>()

    internal var metadata: Map<String, Any?> = emptyMap()

    /**
     * Adds the rules of a [ValidationNode] made by hand, for a value that is not a property.
     *
     * @param node the rules to add, with the path of their violations.
     */
    public fun add(
        node: ValidationNode<*>
    ) {
        nodes += node
    }

    /**
     * Checks [predicate], a rule about the whole object, and reports its violation on [property].
     *
     * Use it for a rule across fields, such as a total that must match its items. The violation has the name of
     * [property] as its path, and the value of [property] as `{value}`.
     *
     * ```kotlin
     * constraint(::total, message = "must be the sum of the items", code = "total") {
     *     total == items.sumOf { it.price }
     * }
     * ```
     *
     * @param property the property the violation is reported on.
     * @param message the message of the violation. `{path}` and `{value}` are filled in.
     * @param code the code of the violation, empty by default.
     * @param predicate returns `true` when the rule holds.
     */
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

    /**
     * Checks [predicate], a rule about the whole object, and reports its violation on [path].
     *
     * ```kotlin
     * constraint(message = "the end must be after the start", code = "period") { end > start }
     * ```
     *
     * @param path the path of the violation, empty for the object itself.
     * @param message the message of the violation. `{path}` is filled in.
     * @param code the code of the violation, empty by default.
     * @param predicate returns `true` when the rule holds.
     */
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

    /**
     * Attaches [entries] to the [metadata][Violation.metadata] of every violation of the rules declared in [block].
     *
     * Nested calls add to the entries around them, and an inner entry replaces an outer one with the same key.
     *
     * ```kotlin
     * metadata("severity" to "warning") {
     *     ::nickname ifPresent { maxLength(20) }
     * }
     * ```
     *
     * @param entries the keys and values to attach.
     * @param block the rules whose violations get them.
     */
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

    /**
     * Runs every rule and returns all the violations, in the order the rules are declared.
     *
     * With a [validity] other than the one the rules were written under, the block of [validation] runs again under
     * it, so its messages and its clock apply.
     *
     * @param validity the configuration to validate with, the default one unless it is given.
     * @return the violations, empty when every rule holds.
     */
    public fun validate(
        validity: Validity = Validity.default
    ): ValidationResult = validity.use {
        val rules = if (validity === this.validity) this else validation(block)

        ValidationResult(
            violations = rules.nodes.flatMap { it.validate() }
        )
    }

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

/**
 * Writes the rules of an object.
 *
 * It is usually the body of [Validatable.validation], but it also works on its own, for values that are not a class of
 * yours.
 *
 * ```kotlin
 * data class User(val name: String?, val email: String?) : Validatable {
 *     override fun validation() = validation {
 *         ::name required { minLength(2) }
 *         ::email required { email() }
 *     }
 * }
 * ```
 *
 * @param block declares the rules.
 * @return the rules, ready to [validate][Validation.validate].
 */
public fun validation(
    block: Validation.() -> Unit
): Validation = Validation(block).apply(block)
