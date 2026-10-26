package io.github.soleworks.validity

public interface ContextualValidatable<in C> {
    public fun validation(
        context: C
    ): Validation
}

public suspend fun <C> ContextualValidatable<C>.validate(
    context: C,
    validity: Validity = Validity.default
): ValidationResult = validation(context).validate(validity)

public fun <C> ContextualValidatable<C>.validateBlocking(
    context: C,
    validity: Validity = Validity.default
): ValidationResult = validation(context).validateBlocking(validity)

public suspend fun <C> ContextualValidatable<C>.validateOrThrow(
    context: C,
    validity: Validity = Validity.default
): Unit = validate(context, validity).orThrow()

public fun <C> ContextualValidatable<C>.validateOrThrowBlocking(
    context: C,
    validity: Validity = Validity.default
): Unit = validateBlocking(context, validity).orThrow()
