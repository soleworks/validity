package io.github.soleworks.validity

import kotlin.reflect.KProperty0
import kotlinx.coroutines.runBlocking

/**
 * The block where the rules of one object are written, with [validation] where the object is in scope, usually in
 * [Validatable.validation].
 *
 * Inside the block, [required][io.github.soleworks.validity.required],
 * [ifPresent][io.github.soleworks.validity.ifPresent]
 * and [forbidden][io.github.soleworks.validity.forbidden] start the rules of a property, the presence rules such as
 * [atLeastOneOf][io.github.soleworks.validity.atLeastOneOf] relate several properties, and
 * [constraint][io.github.soleworks.validity.constraint] checks anything about the object.
 *
 * It has nothing to call on its own: the rules run when the object is validated, with
 * [validate][io.github.soleworks.validity.validate] on a [Validatable], or with [Validation.validate] for the rules
 * of a type.
 */
@ValidityDsl
public class SelfValidation internal constructor(
    private val block: suspend SelfValidation.() -> Unit
) {
    internal val nodes = mutableListOf<ValidationNode<*>>()

    @PublishedApi
    internal var metadata: Map<String, Any?> = emptyMap()

    internal suspend fun validate(
        validity: Validity = Validity.default
    ): ValidationResult = validity.use {
        ValidationResult(
            violations = built().nodes.flatMap { it.violations() }
        )
    }

    internal fun validateBlocking(
        validity: Validity = Validity.default
    ): ValidationResult = runBlocking { validate(validity) }

    internal suspend fun built(): SelfValidation = SelfValidation(block).apply { block() }
}

/**
 * Writes the rules of an object in scope.
 *
 * It is usually the body of [Validatable.validation], where `::name` is a property of the object itself, but it also
 * works on its own, in an extension of a class that is not yours.
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
 * @return the rules of the object, which run when it is validated.
 */
public fun validation(
    block: suspend SelfValidation.() -> Unit
): SelfValidation = SelfValidation(block)

/**
 * Adds the rules of a [ValidationNode] made by hand, for a value that is not a property.
 *
 * @param node the rules to add, with the path of their violations.
 */
context(validation: SelfValidation)
public fun add(
    node: ValidationNode<*>
) {
    validation.nodes += node
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
context(validation: SelfValidation)
public fun constraint(
    property: KProperty0<*>,
    message: String,
    code: String = "",
    predicate: suspend () -> Boolean
): Unit = validation.node(path = property.name, value = property.get()).constraint(
    message = message,
    code = code,
    predicate = { predicate() }
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
context(validation: SelfValidation)
public fun constraint(
    path: String = "",
    message: String,
    code: String = "",
    predicate: suspend () -> Boolean
): Unit = validation.node(path = path, value = null).constraint(
    message = message,
    code = code,
    predicate = { predicate() }
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
context(validation: SelfValidation)
public inline fun metadata(
    vararg entries: Pair<String, Any?>,
    block: () -> Unit
) {
    val outer = validation.metadata

    validation.metadata = outer + entries

    try {
        block()
    } finally {
        validation.metadata = outer
    }
}
