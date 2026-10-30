package io.github.soleworks.validity

import kotlinx.coroutines.runBlocking

/**
 * The rules of a type, written once with [validation] and checked against any value of it.
 *
 * Inside the block the value is `this`, so `::email` is the property of the value being checked. The block is the
 * same as the one of [SelfValidation]: [required][io.github.soleworks.validity.required],
 * [ifPresent][io.github.soleworks.validity.ifPresent], the presence rules and
 * [constraint][io.github.soleworks.validity.constraint] all work in it.
 *
 * ```kotlin
 * val userValidation = validation<User> {
 *     ::email required { email() }
 * }
 *
 * userValidation.validate(user).violations
 * ```
 *
 * Use it for a class that is not yours, or to keep the rules apart from the class. [T] is the type of the values it
 * checks, and a property of that type is validated with [valid][io.github.soleworks.validity.valid], which takes these
 * rules.
 */
public class Validation<T> internal constructor(
    private val block: suspend context(SelfValidation) T.() -> Unit
) {
    /**
     * Runs every rule on [value] and returns all the violations, in the order the rules are declared.
     *
     * @param value the value to check.
     * @param validity the configuration to validate with, the default one unless it is given.
     * @return the violations, empty when every rule holds.
     */
    public suspend fun validate(
        value: T,
        validity: Validity = Validity.default
    ): ValidationResult = of(value).validate(validity)

    /**
     * Runs every rule on [value] and returns all the violations, blocking the thread until the rules finish.
     *
     * Use it outside a coroutine. Inside one, call [validate].
     *
     * @param value the value to check.
     * @param validity the configuration to validate with, the default one unless it is given.
     * @return the violations, empty when every rule holds.
     */
    public fun validateBlocking(
        value: T,
        validity: Validity = Validity.default
    ): ValidationResult = runBlocking { validate(value, validity) }

    internal fun of(
        value: T
    ): SelfValidation = validation { block(this, value) }
}

/**
 * Writes the rules of a type, to check against any value of it later.
 *
 * ```kotlin
 * val userValidation = validation<User> {
 *     ::name required { minLength(2) }
 *     ::email required { email() }
 * }
 * ```
 *
 * @param T the type of the values the rules check.
 * @param block declares the rules, with the value as `this`.
 * @return the rules, ready to [validate][Validation.validate] a value.
 */
public fun <T> validation(
    block: suspend context(SelfValidation) T.() -> Unit
): Validation<T> = Validation(block)
