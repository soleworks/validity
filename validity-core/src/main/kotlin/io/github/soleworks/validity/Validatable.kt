package io.github.soleworks.validity

public interface Validatable {
    public fun validation(): Validation
}

public suspend fun Validatable.validate(
    validity: Validity = Validity.default
): ValidationResult = validation().validate(validity)

public fun Validatable.validateBlocking(
    validity: Validity = Validity.default
): ValidationResult = validation().validateBlocking(validity)

public suspend fun Validatable.validateOrThrow(
    validity: Validity = Validity.default
): Unit = validate(validity).orThrow()

public fun Validatable.validateOrThrowBlocking(
    validity: Validity = Validity.default
): Unit = validateBlocking(validity).orThrow()
