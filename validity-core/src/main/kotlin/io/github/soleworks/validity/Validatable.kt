package io.github.soleworks.validity

public interface Validatable {
    public fun validation(): Validation
}

public fun Validatable.validate(): ValidationResult = validation().validate()

public fun Validatable.validateOrThrow(): Unit = validate().orThrow()
