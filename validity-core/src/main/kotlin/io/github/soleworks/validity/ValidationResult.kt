package io.github.soleworks.validity

public class ValidationResult(
    public val violations: List<Violation>
) {
    public val isValid: Boolean get() = violations.isEmpty()

    public val isInvalid: Boolean get() = violations.isNotEmpty()
}
