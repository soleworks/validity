package io.github.soleworks.validity

/**
 * A class whose rules depend on data the caller passes in: the customer who is asking, the catalog of a tenant, a
 * repository.
 *
 * ```kotlin
 * data class Checkout(val total: Int?, val items: List<Item>?) : ContextualValidatable<Customer> {
 *     override fun validation(context: Customer) = validation {
 *         ::total required { max(context.creditLimit) }
 *         ::items required { each { valid(context) } }
 *     }
 * }
 *
 * checkout.validate(customer).violations
 * ```
 *
 * A property of a [ContextualValidatable] type is validated with [valid][io.github.soleworks.validity.valid], which
 * takes the context to pass to it.
 *
 * @param C the type of the context.
 */
public interface ContextualValidatable<in C> {
    /**
     * Declares the rules of this object for [context], usually with
     * [validation][io.github.soleworks.validity.validation].
     *
     * @param context what the rules read.
     * @return the rules of this object.
     */
    public fun validation(
        context: C
    ): Validation
}

/**
 * Checks the rules of this object for [context] and returns every violation.
 *
 * @param C the type of the context.
 * @param context what the rules read.
 * @param validity the configuration to validate with, the default one unless it is given.
 * @return the violations, empty when every rule holds.
 */
public suspend fun <C> ContextualValidatable<C>.validate(
    context: C,
    validity: Validity = Validity.default
): ValidationResult = validation(context).validate(validity)

/**
 * Checks the rules of this object for [context] and returns every violation, blocking the thread until the rules
 * finish.
 *
 * Use it outside a coroutine. Inside one, call [validate][io.github.soleworks.validity.validate].
 *
 * @param C the type of the context.
 * @param context what the rules read.
 * @param validity the configuration to validate with, the default one unless it is given.
 * @return the violations, empty when every rule holds.
 */
public fun <C> ContextualValidatable<C>.validateBlocking(
    context: C,
    validity: Validity = Validity.default
): ValidationResult = validation(context).validateBlocking(validity)

/**
 * Checks the rules of this object for [context] and throws when any is broken.
 *
 * @param C the type of the context.
 * @param context what the rules read.
 * @param validity the configuration to validate with, the default one unless it is given.
 * @throws ValidationException with every violation, when a rule is broken.
 */
public suspend fun <C> ContextualValidatable<C>.validateOrThrow(
    context: C,
    validity: Validity = Validity.default
): Unit = validate(context, validity).orThrow()

/**
 * Checks the rules of this object for [context] and throws when any is broken, blocking the thread until the rules
 * finish.
 *
 * Use it outside a coroutine. Inside one, call [validateOrThrow][io.github.soleworks.validity.validateOrThrow].
 *
 * @param C the type of the context.
 * @param context what the rules read.
 * @param validity the configuration to validate with, the default one unless it is given.
 * @throws ValidationException with every violation, when a rule is broken.
 */
public fun <C> ContextualValidatable<C>.validateOrThrowBlocking(
    context: C,
    validity: Validity = Validity.default
): Unit = validateBlocking(context, validity).orThrow()
