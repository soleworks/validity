package io.github.soleworks.validity

public interface Validatable {
    public fun validation(): Validation
}

public fun Validatable.validate(): ValidationResult {
    val violations = validation().validate()

    return ValidationResult(violations)
}
