package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.filled
import io.github.soleworks.validity.messages
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.time.OffsetTime
import java.time.ZonedDateTime

/**
 * Checks that the value is not `null`.
 *
 * Prefer [required][io.github.soleworks.validity.required] on a property: it also gives the block the non-null type.
 *
 * Fails with code `notNull` and, by default, the message `is required`
 * ([Messages.required][io.github.soleworks.validity.Messages.required]).
 *
 * @param V the type of the value.
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun <V : Any> ValidationNode<V?>.notNull(
    message: String = messages.required
): Unit = constraint(
    message = message,
    code = "notNull",
    predicate = { it != null }
)

/**
 * Checks that the value is equal to [other].
 *
 * `BigDecimal` values are compared by [compareTo][java.math.BigDecimal.compareTo], so `1.0` equals `1.00`, and zoned or
 * offset dates and times by the instant they stand for.
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `equalTo` and, by default, the message `must be equal to {other}`
 * ([Messages.equalTo][io.github.soleworks.validity.Messages.equalTo]).
 *
 * @param V the type of the value.
 * @param other the value it must be equal to.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun <V> ValidationNode<V>.equalTo(
    other: V?,
    message: String = messages.equalTo
): Unit = equalTo(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the value is equal to [other].
 *
 * `BigDecimal` values are compared by [compareTo][java.math.BigDecimal.compareTo], so `1.0` equals `1.00`, and zoned or
 * offset dates and times by the instant they stand for.
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `equalTo` and the message that [message] builds.
 *
 * @param V the type of the value.
 * @param other the value it must be equal to.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun <V> ValidationNode<V>.equalTo(
    other: V?,
    message: suspend V.(V) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "equalTo",
    predicate = { value, argument -> value.isSameValue(argument) }
)

/**
 * Checks that the value is not equal to [other].
 *
 * Values are compared the way [equalTo] compares them.
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `notEqualTo` and, by default, the message `must not be equal to {other}`
 * ([Messages.notEqualTo][io.github.soleworks.validity.Messages.notEqualTo]).
 *
 * @param V the type of the value.
 * @param other the value it must differ from.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun <V> ValidationNode<V>.notEqualTo(
    other: V?,
    message: String = messages.notEqualTo
): Unit = notEqualTo(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the value is not equal to [other].
 *
 * Values are compared the way [equalTo] compares them.
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `notEqualTo` and the message that [message] builds.
 *
 * @param V the type of the value.
 * @param other the value it must differ from.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun <V> ValidationNode<V>.notEqualTo(
    other: V?,
    message: suspend V.(V) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "notEqualTo",
    predicate = { value, argument -> !value.isSameValue(argument) }
)

/**
 * Checks that the value is one of [values].
 *
 * Values are compared the way [equalTo] compares them.
 *
 * When [values] is `null`, nothing is checked.
 *
 * Fails with code `oneOf` and, by default, the message `must be one of {values}`
 * ([Messages.oneOf][io.github.soleworks.validity.Messages.oneOf]).
 *
 * ```kotlin
 * ::status required { oneOf(listOf("draft", "published")) }
 * ```
 *
 * @param V the type of the value.
 * @param values the values accepted.
 * @param message the message of the violation. `{values}`, `{path}` and `{value}` are filled in.
 */
public fun <V> ValidationNode<V>.oneOf(
    values: Iterable<V>?,
    message: String = messages.oneOf
): Unit = oneOf(
    values = values,
    message = { message.filled("values" to values) }
)

/**
 * Checks that the value is one of [values].
 *
 * Values are compared the way [equalTo] compares them.
 *
 * When [values] is `null`, nothing is checked.
 *
 * Fails with code `oneOf` and the message that [message] builds.
 *
 * @param V the type of the value.
 * @param values the values accepted.
 * @param message builds the message from the value (the receiver) and [values].
 */
public fun <V> ValidationNode<V>.oneOf(
    values: Iterable<V>?,
    message: suspend V.(Iterable<V>) -> String
): Unit = constraint(
    argument = values,
    message = message,
    code = "oneOf",
    predicate = { value, argument -> argument.hasValue(value) }
)

/**
 * Checks that the value is none of [values].
 *
 * Values are compared the way [equalTo] compares them.
 *
 * When [values] is `null`, nothing is checked.
 *
 * Fails with code `noneOf` and, by default, the message `must not be one of {values}`
 * ([Messages.noneOf][io.github.soleworks.validity.Messages.noneOf]).
 *
 * @param V the type of the value.
 * @param values the values refused.
 * @param message the message of the violation. `{values}`, `{path}` and `{value}` are filled in.
 */
public fun <V> ValidationNode<V>.noneOf(
    values: Iterable<V>?,
    message: String = messages.noneOf
): Unit = noneOf(
    values = values,
    message = { message.filled("values" to values) }
)

/**
 * Checks that the value is none of [values].
 *
 * Values are compared the way [equalTo] compares them.
 *
 * When [values] is `null`, nothing is checked.
 *
 * Fails with code `noneOf` and the message that [message] builds.
 *
 * @param V the type of the value.
 * @param values the values refused.
 * @param message builds the message from the value (the receiver) and [values].
 */
public fun <V> ValidationNode<V>.noneOf(
    values: Iterable<V>?,
    message: suspend V.(Iterable<V>) -> String
): Unit = constraint(
    argument = values,
    message = message,
    code = "noneOf",
    predicate = { value, argument -> !argument.hasValue(value) }
)

private fun Any?.isSameValue(
    other: Any?
): Boolean = when {
    this is BigDecimal && other is BigDecimal -> compareTo(other) == 0
    this is ZonedDateTime && other is ZonedDateTime -> isEqual(other)
    this is OffsetDateTime && other is OffsetDateTime -> isEqual(other)
    this is OffsetTime && other is OffsetTime -> isEqual(other)
    else -> this == other
}

private fun <V> Iterable<V>.hasValue(
    value: V
): Boolean = value in this || any { it.isSameValue(value) }
