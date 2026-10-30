package io.github.soleworks.validity

/**
 * What [validate][io.github.soleworks.validity.validate] or [Validation.validate] found.
 *
 * @property violations every broken rule, in the order the rules are declared.
 */
public class ValidationResult(
    public val violations: List<Violation>
) {
    /** Whether every rule holds. */
    public val isValid: Boolean get() = violations.isEmpty()

    /** Whether at least one rule is broken. */
    public val isInvalid: Boolean get() = violations.isNotEmpty()

    /**
     * Throws a [ValidationException] with the [violations] when there is any.
     *
     * @throws ValidationException when the result [isInvalid].
     */
    public fun orThrow() {
        if (isInvalid)
            throw ValidationException(violations)
    }
}
