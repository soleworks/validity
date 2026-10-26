package io.github.soleworks.validity

/**
 * A class that knows its own rules.
 *
 * ```kotlin
 * data class User(val name: String?, val email: String?) : Validatable {
 *     override fun validation() = validation {
 *         ::name required { minLength(2) }
 *         ::email required { email() }
 *     }
 * }
 *
 * User(name = "A", email = "ana@").validate().violations
 * ```
 *
 * A property of a [Validatable] type is validated with [valid][io.github.soleworks.validity.valid], and its violations
 * are nested under the property.
 */
public interface Validatable {
    /**
     * Declares the rules of this object, usually with [validation][io.github.soleworks.validity.validation].
     *
     * @return the rules of this object.
     */
    public fun validation(): Validation
}

/**
 * Checks the rules of this object and returns every violation.
 *
 * @param validity the configuration to validate with, the default one unless it is given.
 * @return the violations, empty when every rule holds.
 */
public fun Validatable.validate(
    validity: Validity = Validity.default
): ValidationResult = validity.use { validation() }.validate(validity)

/**
 * Checks the rules of this object and throws when any is broken.
 *
 * @param validity the configuration to validate with, the default one unless it is given.
 * @throws ValidationException with every violation, when a rule is broken.
 */
public fun Validatable.validateOrThrow(
    validity: Validity = Validity.default
): Unit = validate(validity).orThrow()
