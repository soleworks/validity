package io.github.soleworks.validity

import kotlin.reflect.KProperty0

/**
 * Checks that the property is not `null`, and runs [block] on its value when it is not.
 *
 * Inside [block] the value has the non-null type, so only the constraints that fit it are offered.
 *
 * ```kotlin
 * ::email required { email() }
 * ```
 *
 * Fails with code `required` and the message [Messages.required] when the property is `null`, and [block] does not
 * run.
 *
 * @param V the type of the value.
 * @param block the constraints of the value.
 */
context(validation: Validation)
public inline infix fun <V : Any> KProperty0<V?>.required(
    block: ValidationNode<V>.() -> Unit
): Unit = required(
    message = messages.required,
    block = block
)

/**
 * Checks that the property is not `null`, and runs [block] on its value when it is not.
 *
 * Fails with code `required` and [message] when the property is `null`, and [block] does not run.
 *
 * @param V the type of the value.
 * @param message the message of the violation. `{path}` is filled in.
 * @param block the constraints of the value.
 */
context(validation: Validation)
public inline fun <V : Any> KProperty0<V?>.required(
    message: String,
    block: ValidationNode<V>.() -> Unit
): Unit = required(
    path = name,
    value = get(),
    message = message,
    block = block
)

/**
 * Checks that [value] is not `null`, and runs [block] on it when it is not.
 *
 * Use it for a value that is not a property, such as a computed one or one read from a map.
 *
 * Fails with code `required` and, by default, the message [Messages.required] when [value] is `null`, and [block]
 * does not run.
 *
 * @param V the type of the value.
 * @param path the path of the value and of its violations.
 * @param value the value to check.
 * @param message the message of the violation. `{path}` is filled in.
 * @param block the constraints of the value.
 */
context(validation: Validation)
public inline fun <V : Any> required(
    path: String,
    value: V?,
    message: String = messages.required,
    block: ValidationNode<V>.() -> Unit
) {
    if (value == null)
        return validation.add<V?>(
            path = path,
            value = null,
            message = message,
            code = "required",
            predicate = { it != null }
        )

    validation.node(path = path, value = value).block()
}

/**
 * Runs [block] on the value of the property when it is not `null`. A `null` value is valid.
 *
 * ```kotlin
 * ::age ifPresent { min(18) }
 * ```
 *
 * @param V the type of the value.
 * @param block the constraints of the value.
 */
context(validation: Validation)
public inline infix fun <V : Any> KProperty0<V?>.ifPresent(
    block: ValidationNode<V>.() -> Unit
): Unit = ifPresent(
    path = name,
    value = get(),
    block = block
)

/**
 * Runs [block] on [value] when it is not `null`. A `null` value is valid.
 *
 * Use it for a value that is not a property, such as a computed one or one read from a map.
 *
 * @param V the type of the value.
 * @param path the path of the value and of its violations.
 * @param value the value to check.
 * @param block the constraints of the value.
 */
context(validation: Validation)
public inline fun <V : Any> ifPresent(
    path: String,
    value: V?,
    block: ValidationNode<V>.() -> Unit
) {
    if (value != null)
        validation.node(path = path, value = value).block()
}

/**
 * Checks that the property is `null`.
 *
 * ```kotlin
 * if (type == "PERSON") ::companyName.forbidden()
 * ```
 *
 * Fails with code `forbidden` and, by default, the message [Messages.forbidden].
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
context(validation: Validation)
public fun KProperty0<*>.forbidden(
    message: String = messages.forbidden
): Unit = validation.add(
    path = name,
    value = get(),
    message = message,
    code = "forbidden",
    predicate = { it == null }
)

/**
 * Checks that at least one of [properties] is filled, that is, not `null`.
 *
 * ```kotlin
 * atLeastOneOf(::email, ::phone)
 * ```
 *
 * Fails with code `atLeastOneOf` and, by default, the message [Messages.atLeastOneOf]. Its `{fields}` lists
 * [properties], and
 * the [metadata][Violation.metadata] of the violation has a `fields` entry with the properties to fill.
 *
 * @param properties the properties counted. A property given twice counts once.
 * @param path the path of the violation, empty for the object itself.
 * @param message the message of the violation. `{fields}` and `{path}` are filled in.
 * @throws IllegalArgumentException when [properties] is empty.
 */
context(validation: Validation)
public fun atLeastOneOf(
    vararg properties: KProperty0<*>,
    path: String = "",
    message: String = messages.atLeastOneOf
): Unit = presence(
    properties = properties,
    path = path,
    message = message,
    code = "atLeastOneOf",
    accepts = { filled, _ -> filled >= 1 }
)

/**
 * Checks that at most one of [properties] is filled, that is, not `null`.
 *
 * Fails with code `atMostOneOf` and, by default, the message [Messages.atMostOneOf]. Its `{fields}` lists [properties],
 * and
 * the [metadata][Violation.metadata] of the violation has a `fields` entry with the properties to clear.
 *
 * @param properties the properties counted. A property given twice counts once.
 * @param path the path of the violation, empty for the object itself.
 * @param message the message of the violation. `{fields}` and `{path}` are filled in.
 * @throws IllegalArgumentException when [properties] is empty.
 */
context(validation: Validation)
public fun atMostOneOf(
    vararg properties: KProperty0<*>,
    path: String = "",
    message: String = messages.atMostOneOf
): Unit = presence(
    properties = properties,
    path = path,
    message = message,
    code = "atMostOneOf",
    accepts = { filled, _ -> filled <= 1 }
)

/**
 * Checks that exactly one of [properties] is filled, that is, not `null`.
 *
 * ```kotlin
 * exactlyOneOf(::cardNumber, ::iban)
 * ```
 *
 * Fails with code `exactlyOneOf` and, by default, the message [Messages.exactlyOneOf]. Its `{fields}` lists
 * [properties], and
 * the [metadata][Violation.metadata] of the violation has a `fields` entry with the properties to fill or to clear.
 *
 * @param properties the properties counted. A property given twice counts once.
 * @param path the path of the violation, empty for the object itself.
 * @param message the message of the violation. `{fields}` and `{path}` are filled in.
 * @throws IllegalArgumentException when [properties] is empty.
 */
context(validation: Validation)
public fun exactlyOneOf(
    vararg properties: KProperty0<*>,
    path: String = "",
    message: String = messages.exactlyOneOf
): Unit = presence(
    properties = properties,
    path = path,
    message = message,
    code = "exactlyOneOf",
    accepts = { filled, _ -> filled == 1 }
)

/**
 * Checks that [properties] are all filled or all `null`.
 *
 * ```kotlin
 * allOrNoneOf(::street, ::number, ::city)
 * ```
 *
 * Fails with code `allOrNoneOf` and, by default, the message [Messages.allOrNoneOf]. Its `{fields}` lists
 * [properties], and the [metadata][Violation.metadata] of the violation has a `fields` entry with the properties to
 * fill.
 *
 * @param properties the properties counted. A property given twice counts once.
 * @param path the path of the violation, empty for the object itself.
 * @param message the message of the violation. `{fields}` and `{path}` are filled in.
 * @throws IllegalArgumentException when [properties] is empty.
 */
context(validation: Validation)
public fun allOrNoneOf(
    vararg properties: KProperty0<*>,
    path: String = "",
    message: String = messages.allOrNoneOf
): Unit = presence(
    properties = properties,
    path = path,
    message = message,
    code = "allOrNoneOf",
    accepts = { filled, total -> filled == 0 || filled == total }
)

/**
 * Checks that at least [count] of [properties] are filled, that is, not `null`.
 *
 * ```kotlin
 * atLeastOf(2, ::email, ::phone, ::address)
 * ```
 *
 * Fails with code `atLeastOf` and, by default, the message [Messages.atLeastOf]. Its `{fields}` lists [properties], and
 * the [metadata][Violation.metadata] of the violation has a `fields` entry with the properties to fill.
 *
 * @param count how many properties.
 * @param properties the properties counted. A property given twice counts once.
 * @param path the path of the violation, empty for the object itself.
 * @param message the message of the violation. `{count}`, `{fields}` and `{path}` are filled in.
 * @throws IllegalArgumentException when [properties] is empty, or [count] is negative or greater than the number of
 * properties.
 */
context(validation: Validation)
public fun atLeastOf(
    count: Int,
    vararg properties: KProperty0<*>,
    path: String = "",
    message: String = messages.atLeastOf
): Unit = presence(
    properties = properties.counting(count, code = "atLeastOf"),
    path = path,
    message = message.filled("count" to count),
    code = "atLeastOf",
    accepts = { filled, _ -> filled >= count }
)

/**
 * Checks that at most [count] of [properties] are filled, that is, not `null`.
 *
 * Fails with code `atMostOf` and, by default, the message [Messages.atMostOf]. Its `{fields}` lists [properties], and
 * the [metadata][Violation.metadata] of the violation has a `fields` entry with the properties to clear.
 *
 * @param count how many properties.
 * @param properties the properties counted. A property given twice counts once.
 * @param path the path of the violation, empty for the object itself.
 * @param message the message of the violation. `{count}`, `{fields}` and `{path}` are filled in.
 * @throws IllegalArgumentException when [properties] is empty, or [count] is negative or greater than the number of
 * properties.
 */
context(validation: Validation)
public fun atMostOf(
    count: Int,
    vararg properties: KProperty0<*>,
    path: String = "",
    message: String = messages.atMostOf
): Unit = presence(
    properties = properties.counting(count, code = "atMostOf"),
    path = path,
    message = message.filled("count" to count),
    code = "atMostOf",
    accepts = { filled, _ -> filled <= count }
)

/**
 * Checks that exactly [count] of [properties] are filled, that is, not `null`.
 *
 * Fails with code `exactlyOf` and, by default, the message [Messages.exactlyOf]. Its `{fields}` lists [properties], and
 * the [metadata][Violation.metadata] of the violation has a `fields` entry with the properties to fill or to clear.
 *
 * @param count how many properties.
 * @param properties the properties counted. A property given twice counts once.
 * @param path the path of the violation, empty for the object itself.
 * @param message the message of the violation. `{count}`, `{fields}` and `{path}` are filled in.
 * @throws IllegalArgumentException when [properties] is empty, or [count] is negative or greater than the number of
 * properties.
 */
context(validation: Validation)
public fun exactlyOf(
    count: Int,
    vararg properties: KProperty0<*>,
    path: String = "",
    message: String = messages.exactlyOf
): Unit = presence(
    properties = properties.counting(count, code = "exactlyOf"),
    path = path,
    message = message.filled("count" to count),
    code = "exactlyOf",
    accepts = { filled, _ -> filled == count }
)

private fun Array<out KProperty0<*>>.counting(
    count: Int,
    code: String
): Array<out KProperty0<*>> = also {
    require(count in 0..distinct().size) { "$code needs a count between 0 and the number of properties" }
}

context(validation: Validation)
private fun presence(
    properties: Array<out KProperty0<*>>,
    path: String,
    message: String,
    code: String,
    accepts: (filled: Int, total: Int) -> Boolean
) {
    require(properties.isNotEmpty()) { "$code needs at least one property" }

    val distinct = properties.distinct()
    val names = distinct.map { it.name }
    val filled = distinct.filter { it.get() != null }.map { it.name }
    val isShort = (filled.size + 1..names.size).any { count -> accepts(count, names.size) }
    val fields = if (isShort) names - filled.toSet() else filled

    validation.metadata("fields" to fields) {
        constraint(
            path = path,
            message = message.filled("fields" to names),
            code = code,
            predicate = { accepts(filled.size, names.size) }
        )
    }
}

@PublishedApi
internal fun <V> Validation.add(
    path: String,
    value: V,
    message: String,
    code: String,
    predicate: suspend (V) -> Boolean
) {
    node(path = path, value = value).constraint(
        message = message,
        code = code,
        predicate = predicate
    )
}

@PublishedApi
internal fun <V> Validation.node(
    path: String,
    value: V
): ValidationNode<V> = ValidationNode(path = path, value = value).also { node ->
    node.metadata = metadata
    add(node)
}
