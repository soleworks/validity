package io.github.soleworks.validity

public interface Validatable {
    public fun validation(): Validation
}

public fun Validatable.validate(
    validity: Validity = Validity.default
): ValidationResult = validity.use { validation() }.validate(validity)

public fun Validatable.validateOrThrow(
    validity: Validity = Validity.default
): Unit = validate(validity).orThrow()
