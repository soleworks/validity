package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.filled
import io.github.soleworks.validity.messages
import java.math.BigDecimal
import java.math.BigInteger

/**
 * Checks that the number is at least [min].
 *
 * When [min] is `null`, nothing is checked.
 *
 * Fails with code `min` and, by default, the message `must be at least {min}`
 * ([Messages.min][io.github.soleworks.validity.Messages.min]).
 *
 * @param min the smallest value accepted.
 * @param message the message of the violation. `{min}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Int>.min(
    min: Int?,
    message: String = messages.min
): Unit = min(
    min = min,
    message = { message.filled("min" to min) }
)

/**
 * Checks that the number is at least [min].
 *
 * When [min] is `null`, nothing is checked.
 *
 * Fails with code `min` and the message that [message] builds.
 *
 * @param min the smallest value accepted.
 * @param message builds the message from the value (the receiver) and [min].
 */
public fun ValidationNode<Int>.min(
    min: Int?,
    message: suspend Int.(Int) -> String
): Unit = constraint(
    argument = min,
    message = message,
    code = "min",
    predicate = { value, argument -> value >= argument }
)

/**
 * Checks that the number is at least [min].
 *
 * When [min] is `null`, nothing is checked.
 *
 * Fails with code `min` and, by default, the message `must be at least {min}`
 * ([Messages.min][io.github.soleworks.validity.Messages.min]).
 *
 * @param min the smallest value accepted.
 * @param message the message of the violation. `{min}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Long>.min(
    min: Long?,
    message: String = messages.min
): Unit = min(
    min = min,
    message = { message.filled("min" to min) }
)

/**
 * Checks that the number is at least [min].
 *
 * When [min] is `null`, nothing is checked.
 *
 * Fails with code `min` and the message that [message] builds.
 *
 * @param min the smallest value accepted.
 * @param message builds the message from the value (the receiver) and [min].
 */
public fun ValidationNode<Long>.min(
    min: Long?,
    message: suspend Long.(Long) -> String
): Unit = constraint(
    argument = min,
    message = message,
    code = "min",
    predicate = { value, argument -> value >= argument }
)

/**
 * Checks that the number is at least [min].
 *
 * When [min] is `null`, nothing is checked.
 *
 * Fails with code `min` and, by default, the message `must be at least {min}`
 * ([Messages.min][io.github.soleworks.validity.Messages.min]).
 *
 * @param min the smallest value accepted.
 * @param message the message of the violation. `{min}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Short>.min(
    min: Short?,
    message: String = messages.min
): Unit = min(
    min = min,
    message = { message.filled("min" to min) }
)

/**
 * Checks that the number is at least [min].
 *
 * When [min] is `null`, nothing is checked.
 *
 * Fails with code `min` and the message that [message] builds.
 *
 * @param min the smallest value accepted.
 * @param message builds the message from the value (the receiver) and [min].
 */
public fun ValidationNode<Short>.min(
    min: Short?,
    message: suspend Short.(Short) -> String
): Unit = constraint(
    argument = min,
    message = message,
    code = "min",
    predicate = { value, argument -> value >= argument }
)

/**
 * Checks that the number is at least [min].
 *
 * When [min] is `null`, nothing is checked.
 *
 * Fails with code `min` and, by default, the message `must be at least {min}`
 * ([Messages.min][io.github.soleworks.validity.Messages.min]).
 *
 * @param min the smallest value accepted.
 * @param message the message of the violation. `{min}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Byte>.min(
    min: Byte?,
    message: String = messages.min
): Unit = min(
    min = min,
    message = { message.filled("min" to min) }
)

/**
 * Checks that the number is at least [min].
 *
 * When [min] is `null`, nothing is checked.
 *
 * Fails with code `min` and the message that [message] builds.
 *
 * @param min the smallest value accepted.
 * @param message builds the message from the value (the receiver) and [min].
 */
public fun ValidationNode<Byte>.min(
    min: Byte?,
    message: suspend Byte.(Byte) -> String
): Unit = constraint(
    argument = min,
    message = message,
    code = "min",
    predicate = { value, argument -> value >= argument }
)

/**
 * Checks that the number is at least [min].
 *
 * When [min] is `null`, nothing is checked.
 *
 * Fails with code `min` and, by default, the message `must be at least {min}`
 * ([Messages.min][io.github.soleworks.validity.Messages.min]).
 *
 * @param min the smallest value accepted.
 * @param message the message of the violation. `{min}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Double>.min(
    min: Double?,
    message: String = messages.min
): Unit = min(
    min = min,
    message = { message.filled("min" to min) }
)

/**
 * Checks that the number is at least [min].
 *
 * When [min] is `null`, nothing is checked.
 *
 * Fails with code `min` and the message that [message] builds.
 *
 * @param min the smallest value accepted.
 * @param message builds the message from the value (the receiver) and [min].
 */
public fun ValidationNode<Double>.min(
    min: Double?,
    message: suspend Double.(Double) -> String
): Unit = constraint(
    argument = min,
    message = message,
    code = "min",
    predicate = { value, argument -> value >= argument }
)

/**
 * Checks that the number is at least [min].
 *
 * When [min] is `null`, nothing is checked.
 *
 * Fails with code `min` and, by default, the message `must be at least {min}`
 * ([Messages.min][io.github.soleworks.validity.Messages.min]).
 *
 * @param min the smallest value accepted.
 * @param message the message of the violation. `{min}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Float>.min(
    min: Float?,
    message: String = messages.min
): Unit = min(
    min = min,
    message = { message.filled("min" to min) }
)

/**
 * Checks that the number is at least [min].
 *
 * When [min] is `null`, nothing is checked.
 *
 * Fails with code `min` and the message that [message] builds.
 *
 * @param min the smallest value accepted.
 * @param message builds the message from the value (the receiver) and [min].
 */
public fun ValidationNode<Float>.min(
    min: Float?,
    message: suspend Float.(Float) -> String
): Unit = constraint(
    argument = min,
    message = message,
    code = "min",
    predicate = { value, argument -> value >= argument }
)

/**
 * Checks that the number is at least [min].
 *
 * When [min] is `null`, nothing is checked.
 *
 * Fails with code `min` and, by default, the message `must be at least {min}`
 * ([Messages.min][io.github.soleworks.validity.Messages.min]).
 *
 * @param min the smallest value accepted.
 * @param message the message of the violation. `{min}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<BigInteger>.min(
    min: BigInteger?,
    message: String = messages.min
): Unit = min(
    min = min,
    message = { message.filled("min" to min) }
)

/**
 * Checks that the number is at least [min].
 *
 * When [min] is `null`, nothing is checked.
 *
 * Fails with code `min` and the message that [message] builds.
 *
 * @param min the smallest value accepted.
 * @param message builds the message from the value (the receiver) and [min].
 */
public fun ValidationNode<BigInteger>.min(
    min: BigInteger?,
    message: suspend BigInteger.(BigInteger) -> String
): Unit = constraint(
    argument = min,
    message = message,
    code = "min",
    predicate = { value, argument -> value >= argument }
)

/**
 * Checks that the number is at least [min].
 *
 * When [min] is `null`, nothing is checked.
 *
 * Fails with code `min` and, by default, the message `must be at least {min}`
 * ([Messages.min][io.github.soleworks.validity.Messages.min]).
 *
 * @param min the smallest value accepted.
 * @param message the message of the violation. `{min}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<BigDecimal>.min(
    min: BigDecimal?,
    message: String = messages.min
): Unit = min(
    min = min,
    message = { message.filled("min" to min) }
)

/**
 * Checks that the number is at least [min].
 *
 * When [min] is `null`, nothing is checked.
 *
 * Fails with code `min` and the message that [message] builds.
 *
 * @param min the smallest value accepted.
 * @param message builds the message from the value (the receiver) and [min].
 */
public fun ValidationNode<BigDecimal>.min(
    min: BigDecimal?,
    message: suspend BigDecimal.(BigDecimal) -> String
): Unit = constraint(
    argument = min,
    message = message,
    code = "min",
    predicate = { value, argument -> value >= argument }
)

/**
 * Checks that the number is at most [max].
 *
 * When [max] is `null`, nothing is checked.
 *
 * Fails with code `max` and, by default, the message `must be at most {max}`
 * ([Messages.max][io.github.soleworks.validity.Messages.max]).
 *
 * @param max the largest value accepted.
 * @param message the message of the violation. `{max}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Int>.max(
    max: Int?,
    message: String = messages.max
): Unit = max(
    max = max,
    message = { message.filled("max" to max) }
)

/**
 * Checks that the number is at most [max].
 *
 * When [max] is `null`, nothing is checked.
 *
 * Fails with code `max` and the message that [message] builds.
 *
 * @param max the largest value accepted.
 * @param message builds the message from the value (the receiver) and [max].
 */
public fun ValidationNode<Int>.max(
    max: Int?,
    message: suspend Int.(Int) -> String
): Unit = constraint(
    argument = max,
    message = message,
    code = "max",
    predicate = { value, argument -> value <= argument }
)

/**
 * Checks that the number is at most [max].
 *
 * When [max] is `null`, nothing is checked.
 *
 * Fails with code `max` and, by default, the message `must be at most {max}`
 * ([Messages.max][io.github.soleworks.validity.Messages.max]).
 *
 * @param max the largest value accepted.
 * @param message the message of the violation. `{max}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Long>.max(
    max: Long?,
    message: String = messages.max
): Unit = max(
    max = max,
    message = { message.filled("max" to max) }
)

/**
 * Checks that the number is at most [max].
 *
 * When [max] is `null`, nothing is checked.
 *
 * Fails with code `max` and the message that [message] builds.
 *
 * @param max the largest value accepted.
 * @param message builds the message from the value (the receiver) and [max].
 */
public fun ValidationNode<Long>.max(
    max: Long?,
    message: suspend Long.(Long) -> String
): Unit = constraint(
    argument = max,
    message = message,
    code = "max",
    predicate = { value, argument -> value <= argument }
)

/**
 * Checks that the number is at most [max].
 *
 * When [max] is `null`, nothing is checked.
 *
 * Fails with code `max` and, by default, the message `must be at most {max}`
 * ([Messages.max][io.github.soleworks.validity.Messages.max]).
 *
 * @param max the largest value accepted.
 * @param message the message of the violation. `{max}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Short>.max(
    max: Short?,
    message: String = messages.max
): Unit = max(
    max = max,
    message = { message.filled("max" to max) }
)

/**
 * Checks that the number is at most [max].
 *
 * When [max] is `null`, nothing is checked.
 *
 * Fails with code `max` and the message that [message] builds.
 *
 * @param max the largest value accepted.
 * @param message builds the message from the value (the receiver) and [max].
 */
public fun ValidationNode<Short>.max(
    max: Short?,
    message: suspend Short.(Short) -> String
): Unit = constraint(
    argument = max,
    message = message,
    code = "max",
    predicate = { value, argument -> value <= argument }
)

/**
 * Checks that the number is at most [max].
 *
 * When [max] is `null`, nothing is checked.
 *
 * Fails with code `max` and, by default, the message `must be at most {max}`
 * ([Messages.max][io.github.soleworks.validity.Messages.max]).
 *
 * @param max the largest value accepted.
 * @param message the message of the violation. `{max}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Byte>.max(
    max: Byte?,
    message: String = messages.max
): Unit = max(
    max = max,
    message = { message.filled("max" to max) }
)

/**
 * Checks that the number is at most [max].
 *
 * When [max] is `null`, nothing is checked.
 *
 * Fails with code `max` and the message that [message] builds.
 *
 * @param max the largest value accepted.
 * @param message builds the message from the value (the receiver) and [max].
 */
public fun ValidationNode<Byte>.max(
    max: Byte?,
    message: suspend Byte.(Byte) -> String
): Unit = constraint(
    argument = max,
    message = message,
    code = "max",
    predicate = { value, argument -> value <= argument }
)

/**
 * Checks that the number is at most [max].
 *
 * When [max] is `null`, nothing is checked.
 *
 * Fails with code `max` and, by default, the message `must be at most {max}`
 * ([Messages.max][io.github.soleworks.validity.Messages.max]).
 *
 * @param max the largest value accepted.
 * @param message the message of the violation. `{max}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Double>.max(
    max: Double?,
    message: String = messages.max
): Unit = max(
    max = max,
    message = { message.filled("max" to max) }
)

/**
 * Checks that the number is at most [max].
 *
 * When [max] is `null`, nothing is checked.
 *
 * Fails with code `max` and the message that [message] builds.
 *
 * @param max the largest value accepted.
 * @param message builds the message from the value (the receiver) and [max].
 */
public fun ValidationNode<Double>.max(
    max: Double?,
    message: suspend Double.(Double) -> String
): Unit = constraint(
    argument = max,
    message = message,
    code = "max",
    predicate = { value, argument -> value <= argument }
)

/**
 * Checks that the number is at most [max].
 *
 * When [max] is `null`, nothing is checked.
 *
 * Fails with code `max` and, by default, the message `must be at most {max}`
 * ([Messages.max][io.github.soleworks.validity.Messages.max]).
 *
 * @param max the largest value accepted.
 * @param message the message of the violation. `{max}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Float>.max(
    max: Float?,
    message: String = messages.max
): Unit = max(
    max = max,
    message = { message.filled("max" to max) }
)

/**
 * Checks that the number is at most [max].
 *
 * When [max] is `null`, nothing is checked.
 *
 * Fails with code `max` and the message that [message] builds.
 *
 * @param max the largest value accepted.
 * @param message builds the message from the value (the receiver) and [max].
 */
public fun ValidationNode<Float>.max(
    max: Float?,
    message: suspend Float.(Float) -> String
): Unit = constraint(
    argument = max,
    message = message,
    code = "max",
    predicate = { value, argument -> value <= argument }
)

/**
 * Checks that the number is at most [max].
 *
 * When [max] is `null`, nothing is checked.
 *
 * Fails with code `max` and, by default, the message `must be at most {max}`
 * ([Messages.max][io.github.soleworks.validity.Messages.max]).
 *
 * @param max the largest value accepted.
 * @param message the message of the violation. `{max}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<BigInteger>.max(
    max: BigInteger?,
    message: String = messages.max
): Unit = max(
    max = max,
    message = { message.filled("max" to max) }
)

/**
 * Checks that the number is at most [max].
 *
 * When [max] is `null`, nothing is checked.
 *
 * Fails with code `max` and the message that [message] builds.
 *
 * @param max the largest value accepted.
 * @param message builds the message from the value (the receiver) and [max].
 */
public fun ValidationNode<BigInteger>.max(
    max: BigInteger?,
    message: suspend BigInteger.(BigInteger) -> String
): Unit = constraint(
    argument = max,
    message = message,
    code = "max",
    predicate = { value, argument -> value <= argument }
)

/**
 * Checks that the number is at most [max].
 *
 * When [max] is `null`, nothing is checked.
 *
 * Fails with code `max` and, by default, the message `must be at most {max}`
 * ([Messages.max][io.github.soleworks.validity.Messages.max]).
 *
 * @param max the largest value accepted.
 * @param message the message of the violation. `{max}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<BigDecimal>.max(
    max: BigDecimal?,
    message: String = messages.max
): Unit = max(
    max = max,
    message = { message.filled("max" to max) }
)

/**
 * Checks that the number is at most [max].
 *
 * When [max] is `null`, nothing is checked.
 *
 * Fails with code `max` and the message that [message] builds.
 *
 * @param max the largest value accepted.
 * @param message builds the message from the value (the receiver) and [max].
 */
public fun ValidationNode<BigDecimal>.max(
    max: BigDecimal?,
    message: suspend BigDecimal.(BigDecimal) -> String
): Unit = constraint(
    argument = max,
    message = message,
    code = "max",
    predicate = { value, argument -> value <= argument }
)

/**
 * Checks that the number is greater than [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `greaterThan` and, by default, the message `must be greater than {other}`
 * ([Messages.greaterThan][io.github.soleworks.validity.Messages.greaterThan]).
 *
 * @param other the value it must be greater than.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Int>.greaterThan(
    other: Int?,
    message: String = messages.greaterThan
): Unit = greaterThan(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the number is greater than [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `greaterThan` and the message that [message] builds.
 *
 * @param other the value it must be greater than.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<Int>.greaterThan(
    other: Int?,
    message: suspend Int.(Int) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "greaterThan",
    predicate = { value, argument -> value > argument }
)

/**
 * Checks that the number is greater than [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `greaterThan` and, by default, the message `must be greater than {other}`
 * ([Messages.greaterThan][io.github.soleworks.validity.Messages.greaterThan]).
 *
 * @param other the value it must be greater than.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Long>.greaterThan(
    other: Long?,
    message: String = messages.greaterThan
): Unit = greaterThan(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the number is greater than [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `greaterThan` and the message that [message] builds.
 *
 * @param other the value it must be greater than.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<Long>.greaterThan(
    other: Long?,
    message: suspend Long.(Long) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "greaterThan",
    predicate = { value, argument -> value > argument }
)

/**
 * Checks that the number is greater than [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `greaterThan` and, by default, the message `must be greater than {other}`
 * ([Messages.greaterThan][io.github.soleworks.validity.Messages.greaterThan]).
 *
 * @param other the value it must be greater than.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Short>.greaterThan(
    other: Short?,
    message: String = messages.greaterThan
): Unit = greaterThan(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the number is greater than [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `greaterThan` and the message that [message] builds.
 *
 * @param other the value it must be greater than.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<Short>.greaterThan(
    other: Short?,
    message: suspend Short.(Short) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "greaterThan",
    predicate = { value, argument -> value > argument }
)

/**
 * Checks that the number is greater than [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `greaterThan` and, by default, the message `must be greater than {other}`
 * ([Messages.greaterThan][io.github.soleworks.validity.Messages.greaterThan]).
 *
 * @param other the value it must be greater than.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Byte>.greaterThan(
    other: Byte?,
    message: String = messages.greaterThan
): Unit = greaterThan(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the number is greater than [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `greaterThan` and the message that [message] builds.
 *
 * @param other the value it must be greater than.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<Byte>.greaterThan(
    other: Byte?,
    message: suspend Byte.(Byte) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "greaterThan",
    predicate = { value, argument -> value > argument }
)

/**
 * Checks that the number is greater than [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `greaterThan` and, by default, the message `must be greater than {other}`
 * ([Messages.greaterThan][io.github.soleworks.validity.Messages.greaterThan]).
 *
 * @param other the value it must be greater than.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Double>.greaterThan(
    other: Double?,
    message: String = messages.greaterThan
): Unit = greaterThan(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the number is greater than [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `greaterThan` and the message that [message] builds.
 *
 * @param other the value it must be greater than.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<Double>.greaterThan(
    other: Double?,
    message: suspend Double.(Double) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "greaterThan",
    predicate = { value, argument -> value > argument }
)

/**
 * Checks that the number is greater than [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `greaterThan` and, by default, the message `must be greater than {other}`
 * ([Messages.greaterThan][io.github.soleworks.validity.Messages.greaterThan]).
 *
 * @param other the value it must be greater than.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Float>.greaterThan(
    other: Float?,
    message: String = messages.greaterThan
): Unit = greaterThan(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the number is greater than [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `greaterThan` and the message that [message] builds.
 *
 * @param other the value it must be greater than.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<Float>.greaterThan(
    other: Float?,
    message: suspend Float.(Float) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "greaterThan",
    predicate = { value, argument -> value > argument }
)

/**
 * Checks that the number is greater than [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `greaterThan` and, by default, the message `must be greater than {other}`
 * ([Messages.greaterThan][io.github.soleworks.validity.Messages.greaterThan]).
 *
 * @param other the value it must be greater than.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<BigInteger>.greaterThan(
    other: BigInteger?,
    message: String = messages.greaterThan
): Unit = greaterThan(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the number is greater than [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `greaterThan` and the message that [message] builds.
 *
 * @param other the value it must be greater than.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<BigInteger>.greaterThan(
    other: BigInteger?,
    message: suspend BigInteger.(BigInteger) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "greaterThan",
    predicate = { value, argument -> value > argument }
)

/**
 * Checks that the number is greater than [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `greaterThan` and, by default, the message `must be greater than {other}`
 * ([Messages.greaterThan][io.github.soleworks.validity.Messages.greaterThan]).
 *
 * @param other the value it must be greater than.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<BigDecimal>.greaterThan(
    other: BigDecimal?,
    message: String = messages.greaterThan
): Unit = greaterThan(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the number is greater than [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `greaterThan` and the message that [message] builds.
 *
 * @param other the value it must be greater than.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<BigDecimal>.greaterThan(
    other: BigDecimal?,
    message: suspend BigDecimal.(BigDecimal) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "greaterThan",
    predicate = { value, argument -> value > argument }
)

/**
 * Checks that the number is less than [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `lessThan` and, by default, the message `must be less than {other}`
 * ([Messages.lessThan][io.github.soleworks.validity.Messages.lessThan]).
 *
 * @param other the value it must be less than.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Int>.lessThan(
    other: Int?,
    message: String = messages.lessThan
): Unit = lessThan(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the number is less than [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `lessThan` and the message that [message] builds.
 *
 * @param other the value it must be less than.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<Int>.lessThan(
    other: Int?,
    message: suspend Int.(Int) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "lessThan",
    predicate = { value, argument -> value < argument }
)

/**
 * Checks that the number is less than [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `lessThan` and, by default, the message `must be less than {other}`
 * ([Messages.lessThan][io.github.soleworks.validity.Messages.lessThan]).
 *
 * @param other the value it must be less than.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Long>.lessThan(
    other: Long?,
    message: String = messages.lessThan
): Unit = lessThan(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the number is less than [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `lessThan` and the message that [message] builds.
 *
 * @param other the value it must be less than.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<Long>.lessThan(
    other: Long?,
    message: suspend Long.(Long) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "lessThan",
    predicate = { value, argument -> value < argument }
)

/**
 * Checks that the number is less than [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `lessThan` and, by default, the message `must be less than {other}`
 * ([Messages.lessThan][io.github.soleworks.validity.Messages.lessThan]).
 *
 * @param other the value it must be less than.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Short>.lessThan(
    other: Short?,
    message: String = messages.lessThan
): Unit = lessThan(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the number is less than [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `lessThan` and the message that [message] builds.
 *
 * @param other the value it must be less than.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<Short>.lessThan(
    other: Short?,
    message: suspend Short.(Short) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "lessThan",
    predicate = { value, argument -> value < argument }
)

/**
 * Checks that the number is less than [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `lessThan` and, by default, the message `must be less than {other}`
 * ([Messages.lessThan][io.github.soleworks.validity.Messages.lessThan]).
 *
 * @param other the value it must be less than.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Byte>.lessThan(
    other: Byte?,
    message: String = messages.lessThan
): Unit = lessThan(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the number is less than [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `lessThan` and the message that [message] builds.
 *
 * @param other the value it must be less than.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<Byte>.lessThan(
    other: Byte?,
    message: suspend Byte.(Byte) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "lessThan",
    predicate = { value, argument -> value < argument }
)

/**
 * Checks that the number is less than [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `lessThan` and, by default, the message `must be less than {other}`
 * ([Messages.lessThan][io.github.soleworks.validity.Messages.lessThan]).
 *
 * @param other the value it must be less than.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Double>.lessThan(
    other: Double?,
    message: String = messages.lessThan
): Unit = lessThan(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the number is less than [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `lessThan` and the message that [message] builds.
 *
 * @param other the value it must be less than.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<Double>.lessThan(
    other: Double?,
    message: suspend Double.(Double) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "lessThan",
    predicate = { value, argument -> value < argument }
)

/**
 * Checks that the number is less than [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `lessThan` and, by default, the message `must be less than {other}`
 * ([Messages.lessThan][io.github.soleworks.validity.Messages.lessThan]).
 *
 * @param other the value it must be less than.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Float>.lessThan(
    other: Float?,
    message: String = messages.lessThan
): Unit = lessThan(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the number is less than [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `lessThan` and the message that [message] builds.
 *
 * @param other the value it must be less than.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<Float>.lessThan(
    other: Float?,
    message: suspend Float.(Float) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "lessThan",
    predicate = { value, argument -> value < argument }
)

/**
 * Checks that the number is less than [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `lessThan` and, by default, the message `must be less than {other}`
 * ([Messages.lessThan][io.github.soleworks.validity.Messages.lessThan]).
 *
 * @param other the value it must be less than.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<BigInteger>.lessThan(
    other: BigInteger?,
    message: String = messages.lessThan
): Unit = lessThan(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the number is less than [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `lessThan` and the message that [message] builds.
 *
 * @param other the value it must be less than.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<BigInteger>.lessThan(
    other: BigInteger?,
    message: suspend BigInteger.(BigInteger) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "lessThan",
    predicate = { value, argument -> value < argument }
)

/**
 * Checks that the number is less than [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `lessThan` and, by default, the message `must be less than {other}`
 * ([Messages.lessThan][io.github.soleworks.validity.Messages.lessThan]).
 *
 * @param other the value it must be less than.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<BigDecimal>.lessThan(
    other: BigDecimal?,
    message: String = messages.lessThan
): Unit = lessThan(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the number is less than [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `lessThan` and the message that [message] builds.
 *
 * @param other the value it must be less than.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<BigDecimal>.lessThan(
    other: BigDecimal?,
    message: suspend BigDecimal.(BigDecimal) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "lessThan",
    predicate = { value, argument -> value < argument }
)

/**
 * Checks that the number is between [min] and [max], both included.
 *
 * When [min] or [max] is `null`, nothing is checked.
 *
 * Fails with code `between` and, by default, the message `must be between {min} and {max}`
 * ([Messages.between][io.github.soleworks.validity.Messages.between]).
 *
 * @param min the smallest value accepted.
 * @param max the largest value accepted.
 * @param message the message of the violation. `{min}`, `{max}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Int>.between(
    min: Int?,
    max: Int?,
    message: String = messages.between
): Unit = between(
    min = min,
    max = max,
    message = { _, _ -> message.filled("min" to min, "max" to max) }
)

/**
 * Checks that the number is between [min] and [max], both included.
 *
 * When [min] or [max] is `null`, nothing is checked.
 *
 * Fails with code `between` and the message that [message] builds.
 *
 * @param min the smallest value accepted.
 * @param max the largest value accepted.
 * @param message builds the message from the value (the receiver), [min] and [max].
 */
public fun ValidationNode<Int>.between(
    min: Int?,
    max: Int?,
    message: suspend Int.(Int, Int) -> String
): Unit = constraint(
    first = min,
    second = max,
    message = message,
    code = "between",
    predicate = { value, first, second -> value in first..second }
)

/**
 * Checks that the number is between [min] and [max], both included.
 *
 * When [min] or [max] is `null`, nothing is checked.
 *
 * Fails with code `between` and, by default, the message `must be between {min} and {max}`
 * ([Messages.between][io.github.soleworks.validity.Messages.between]).
 *
 * @param min the smallest value accepted.
 * @param max the largest value accepted.
 * @param message the message of the violation. `{min}`, `{max}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Long>.between(
    min: Long?,
    max: Long?,
    message: String = messages.between
): Unit = between(
    min = min,
    max = max,
    message = { _, _ -> message.filled("min" to min, "max" to max) }
)

/**
 * Checks that the number is between [min] and [max], both included.
 *
 * When [min] or [max] is `null`, nothing is checked.
 *
 * Fails with code `between` and the message that [message] builds.
 *
 * @param min the smallest value accepted.
 * @param max the largest value accepted.
 * @param message builds the message from the value (the receiver), [min] and [max].
 */
public fun ValidationNode<Long>.between(
    min: Long?,
    max: Long?,
    message: suspend Long.(Long, Long) -> String
): Unit = constraint(
    first = min,
    second = max,
    message = message,
    code = "between",
    predicate = { value, first, second -> value in first..second }
)

/**
 * Checks that the number is between [min] and [max], both included.
 *
 * When [min] or [max] is `null`, nothing is checked.
 *
 * Fails with code `between` and, by default, the message `must be between {min} and {max}`
 * ([Messages.between][io.github.soleworks.validity.Messages.between]).
 *
 * @param min the smallest value accepted.
 * @param max the largest value accepted.
 * @param message the message of the violation. `{min}`, `{max}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Short>.between(
    min: Short?,
    max: Short?,
    message: String = messages.between
): Unit = between(
    min = min,
    max = max,
    message = { _, _ -> message.filled("min" to min, "max" to max) }
)

/**
 * Checks that the number is between [min] and [max], both included.
 *
 * When [min] or [max] is `null`, nothing is checked.
 *
 * Fails with code `between` and the message that [message] builds.
 *
 * @param min the smallest value accepted.
 * @param max the largest value accepted.
 * @param message builds the message from the value (the receiver), [min] and [max].
 */
public fun ValidationNode<Short>.between(
    min: Short?,
    max: Short?,
    message: suspend Short.(Short, Short) -> String
): Unit = constraint(
    first = min,
    second = max,
    message = message,
    code = "between",
    predicate = { value, first, second -> value in first..second }
)

/**
 * Checks that the number is between [min] and [max], both included.
 *
 * When [min] or [max] is `null`, nothing is checked.
 *
 * Fails with code `between` and, by default, the message `must be between {min} and {max}`
 * ([Messages.between][io.github.soleworks.validity.Messages.between]).
 *
 * @param min the smallest value accepted.
 * @param max the largest value accepted.
 * @param message the message of the violation. `{min}`, `{max}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Byte>.between(
    min: Byte?,
    max: Byte?,
    message: String = messages.between
): Unit = between(
    min = min,
    max = max,
    message = { _, _ -> message.filled("min" to min, "max" to max) }
)

/**
 * Checks that the number is between [min] and [max], both included.
 *
 * When [min] or [max] is `null`, nothing is checked.
 *
 * Fails with code `between` and the message that [message] builds.
 *
 * @param min the smallest value accepted.
 * @param max the largest value accepted.
 * @param message builds the message from the value (the receiver), [min] and [max].
 */
public fun ValidationNode<Byte>.between(
    min: Byte?,
    max: Byte?,
    message: suspend Byte.(Byte, Byte) -> String
): Unit = constraint(
    first = min,
    second = max,
    message = message,
    code = "between",
    predicate = { value, first, second -> value in first..second }
)

/**
 * Checks that the number is between [min] and [max], both included.
 *
 * When [min] or [max] is `null`, nothing is checked.
 *
 * Fails with code `between` and, by default, the message `must be between {min} and {max}`
 * ([Messages.between][io.github.soleworks.validity.Messages.between]).
 *
 * @param min the smallest value accepted.
 * @param max the largest value accepted.
 * @param message the message of the violation. `{min}`, `{max}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Double>.between(
    min: Double?,
    max: Double?,
    message: String = messages.between
): Unit = between(
    min = min,
    max = max,
    message = { _, _ -> message.filled("min" to min, "max" to max) }
)

/**
 * Checks that the number is between [min] and [max], both included.
 *
 * When [min] or [max] is `null`, nothing is checked.
 *
 * Fails with code `between` and the message that [message] builds.
 *
 * @param min the smallest value accepted.
 * @param max the largest value accepted.
 * @param message builds the message from the value (the receiver), [min] and [max].
 */
public fun ValidationNode<Double>.between(
    min: Double?,
    max: Double?,
    message: suspend Double.(Double, Double) -> String
): Unit = constraint(
    first = min,
    second = max,
    message = message,
    code = "between",
    predicate = { value, first, second -> value in first..second }
)

/**
 * Checks that the number is between [min] and [max], both included.
 *
 * When [min] or [max] is `null`, nothing is checked.
 *
 * Fails with code `between` and, by default, the message `must be between {min} and {max}`
 * ([Messages.between][io.github.soleworks.validity.Messages.between]).
 *
 * @param min the smallest value accepted.
 * @param max the largest value accepted.
 * @param message the message of the violation. `{min}`, `{max}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Float>.between(
    min: Float?,
    max: Float?,
    message: String = messages.between
): Unit = between(
    min = min,
    max = max,
    message = { _, _ -> message.filled("min" to min, "max" to max) }
)

/**
 * Checks that the number is between [min] and [max], both included.
 *
 * When [min] or [max] is `null`, nothing is checked.
 *
 * Fails with code `between` and the message that [message] builds.
 *
 * @param min the smallest value accepted.
 * @param max the largest value accepted.
 * @param message builds the message from the value (the receiver), [min] and [max].
 */
public fun ValidationNode<Float>.between(
    min: Float?,
    max: Float?,
    message: suspend Float.(Float, Float) -> String
): Unit = constraint(
    first = min,
    second = max,
    message = message,
    code = "between",
    predicate = { value, first, second -> value in first..second }
)

/**
 * Checks that the number is between [min] and [max], both included.
 *
 * When [min] or [max] is `null`, nothing is checked.
 *
 * Fails with code `between` and, by default, the message `must be between {min} and {max}`
 * ([Messages.between][io.github.soleworks.validity.Messages.between]).
 *
 * @param min the smallest value accepted.
 * @param max the largest value accepted.
 * @param message the message of the violation. `{min}`, `{max}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<BigInteger>.between(
    min: BigInteger?,
    max: BigInteger?,
    message: String = messages.between
): Unit = between(
    min = min,
    max = max,
    message = { _, _ -> message.filled("min" to min, "max" to max) }
)

/**
 * Checks that the number is between [min] and [max], both included.
 *
 * When [min] or [max] is `null`, nothing is checked.
 *
 * Fails with code `between` and the message that [message] builds.
 *
 * @param min the smallest value accepted.
 * @param max the largest value accepted.
 * @param message builds the message from the value (the receiver), [min] and [max].
 */
public fun ValidationNode<BigInteger>.between(
    min: BigInteger?,
    max: BigInteger?,
    message: suspend BigInteger.(BigInteger, BigInteger) -> String
): Unit = constraint(
    first = min,
    second = max,
    message = message,
    code = "between",
    predicate = { value, first, second -> value in first..second }
)

/**
 * Checks that the number is between [min] and [max], both included.
 *
 * When [min] or [max] is `null`, nothing is checked.
 *
 * Fails with code `between` and, by default, the message `must be between {min} and {max}`
 * ([Messages.between][io.github.soleworks.validity.Messages.between]).
 *
 * @param min the smallest value accepted.
 * @param max the largest value accepted.
 * @param message the message of the violation. `{min}`, `{max}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<BigDecimal>.between(
    min: BigDecimal?,
    max: BigDecimal?,
    message: String = messages.between
): Unit = between(
    min = min,
    max = max,
    message = { _, _ -> message.filled("min" to min, "max" to max) }
)

/**
 * Checks that the number is between [min] and [max], both included.
 *
 * When [min] or [max] is `null`, nothing is checked.
 *
 * Fails with code `between` and the message that [message] builds.
 *
 * @param min the smallest value accepted.
 * @param max the largest value accepted.
 * @param message builds the message from the value (the receiver), [min] and [max].
 */
public fun ValidationNode<BigDecimal>.between(
    min: BigDecimal?,
    max: BigDecimal?,
    message: suspend BigDecimal.(BigDecimal, BigDecimal) -> String
): Unit = constraint(
    first = min,
    second = max,
    message = message,
    code = "between",
    predicate = { value, first, second -> value in first..second }
)

/**
 * Checks that the number is greater than zero.
 *
 * Fails with code `positive` and, by default, the message `must be positive`
 * ([Messages.positive][io.github.soleworks.validity.Messages.positive]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Int>.positive(
    message: String = messages.positive
): Unit = constraint(
    message = message,
    code = "positive",
    predicate = { it > 0 }
)

/**
 * Checks that the number is greater than zero.
 *
 * Fails with code `positive` and, by default, the message `must be positive`
 * ([Messages.positive][io.github.soleworks.validity.Messages.positive]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("positiveLong")
public fun ValidationNode<Long>.positive(
    message: String = messages.positive
): Unit = constraint(
    message = message,
    code = "positive",
    predicate = { it > 0 }
)

/**
 * Checks that the number is greater than zero.
 *
 * Fails with code `positive` and, by default, the message `must be positive`
 * ([Messages.positive][io.github.soleworks.validity.Messages.positive]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("positiveShort")
public fun ValidationNode<Short>.positive(
    message: String = messages.positive
): Unit = constraint(
    message = message,
    code = "positive",
    predicate = { it > 0 }
)

/**
 * Checks that the number is greater than zero.
 *
 * Fails with code `positive` and, by default, the message `must be positive`
 * ([Messages.positive][io.github.soleworks.validity.Messages.positive]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("positiveByte")
public fun ValidationNode<Byte>.positive(
    message: String = messages.positive
): Unit = constraint(
    message = message,
    code = "positive",
    predicate = { it > 0 }
)

/**
 * Checks that the number is greater than zero.
 *
 * Fails with code `positive` and, by default, the message `must be positive`
 * ([Messages.positive][io.github.soleworks.validity.Messages.positive]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("positiveDouble")
public fun ValidationNode<Double>.positive(
    message: String = messages.positive
): Unit = constraint(
    message = message,
    code = "positive",
    predicate = { it > 0.0 }
)

/**
 * Checks that the number is greater than zero.
 *
 * Fails with code `positive` and, by default, the message `must be positive`
 * ([Messages.positive][io.github.soleworks.validity.Messages.positive]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("positiveFloat")
public fun ValidationNode<Float>.positive(
    message: String = messages.positive
): Unit = constraint(
    message = message,
    code = "positive",
    predicate = { it > 0f }
)

/**
 * Checks that the number is greater than zero.
 *
 * Fails with code `positive` and, by default, the message `must be positive`
 * ([Messages.positive][io.github.soleworks.validity.Messages.positive]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("positiveBigInteger")
public fun ValidationNode<BigInteger>.positive(
    message: String = messages.positive
): Unit = constraint(
    message = message,
    code = "positive",
    predicate = { it.signum() > 0 }
)

/**
 * Checks that the number is greater than zero.
 *
 * Fails with code `positive` and, by default, the message `must be positive`
 * ([Messages.positive][io.github.soleworks.validity.Messages.positive]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("positiveBigDecimal")
public fun ValidationNode<BigDecimal>.positive(
    message: String = messages.positive
): Unit = constraint(
    message = message,
    code = "positive",
    predicate = { it.signum() > 0 }
)

/**
 * Checks that the number is less than zero.
 *
 * Fails with code `negative` and, by default, the message `must be negative`
 * ([Messages.negative][io.github.soleworks.validity.Messages.negative]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Int>.negative(
    message: String = messages.negative
): Unit = constraint(
    message = message,
    code = "negative",
    predicate = { it < 0 }
)

/**
 * Checks that the number is less than zero.
 *
 * Fails with code `negative` and, by default, the message `must be negative`
 * ([Messages.negative][io.github.soleworks.validity.Messages.negative]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("negativeLong")
public fun ValidationNode<Long>.negative(
    message: String = messages.negative
): Unit = constraint(
    message = message,
    code = "negative",
    predicate = { it < 0 }
)

/**
 * Checks that the number is less than zero.
 *
 * Fails with code `negative` and, by default, the message `must be negative`
 * ([Messages.negative][io.github.soleworks.validity.Messages.negative]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("negativeShort")
public fun ValidationNode<Short>.negative(
    message: String = messages.negative
): Unit = constraint(
    message = message,
    code = "negative",
    predicate = { it < 0 }
)

/**
 * Checks that the number is less than zero.
 *
 * Fails with code `negative` and, by default, the message `must be negative`
 * ([Messages.negative][io.github.soleworks.validity.Messages.negative]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("negativeByte")
public fun ValidationNode<Byte>.negative(
    message: String = messages.negative
): Unit = constraint(
    message = message,
    code = "negative",
    predicate = { it < 0 }
)

/**
 * Checks that the number is less than zero.
 *
 * Fails with code `negative` and, by default, the message `must be negative`
 * ([Messages.negative][io.github.soleworks.validity.Messages.negative]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("negativeDouble")
public fun ValidationNode<Double>.negative(
    message: String = messages.negative
): Unit = constraint(
    message = message,
    code = "negative",
    predicate = { it < 0.0 }
)

/**
 * Checks that the number is less than zero.
 *
 * Fails with code `negative` and, by default, the message `must be negative`
 * ([Messages.negative][io.github.soleworks.validity.Messages.negative]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("negativeFloat")
public fun ValidationNode<Float>.negative(
    message: String = messages.negative
): Unit = constraint(
    message = message,
    code = "negative",
    predicate = { it < 0f }
)

/**
 * Checks that the number is less than zero.
 *
 * Fails with code `negative` and, by default, the message `must be negative`
 * ([Messages.negative][io.github.soleworks.validity.Messages.negative]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("negativeBigInteger")
public fun ValidationNode<BigInteger>.negative(
    message: String = messages.negative
): Unit = constraint(
    message = message,
    code = "negative",
    predicate = { it.signum() < 0 }
)

/**
 * Checks that the number is less than zero.
 *
 * Fails with code `negative` and, by default, the message `must be negative`
 * ([Messages.negative][io.github.soleworks.validity.Messages.negative]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("negativeBigDecimal")
public fun ValidationNode<BigDecimal>.negative(
    message: String = messages.negative
): Unit = constraint(
    message = message,
    code = "negative",
    predicate = { it.signum() < 0 }
)

/**
 * Checks that the number is zero or greater.
 *
 * Fails with code `positiveOrZero` and, by default, the message `must be positive or zero`
 * ([Messages.positiveOrZero][io.github.soleworks.validity.Messages.positiveOrZero]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Int>.positiveOrZero(
    message: String = messages.positiveOrZero
): Unit = constraint(
    message = message,
    code = "positiveOrZero",
    predicate = { it >= 0 }
)

/**
 * Checks that the number is zero or greater.
 *
 * Fails with code `positiveOrZero` and, by default, the message `must be positive or zero`
 * ([Messages.positiveOrZero][io.github.soleworks.validity.Messages.positiveOrZero]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("positiveOrZeroLong")
public fun ValidationNode<Long>.positiveOrZero(
    message: String = messages.positiveOrZero
): Unit = constraint(
    message = message,
    code = "positiveOrZero",
    predicate = { it >= 0 }
)

/**
 * Checks that the number is zero or greater.
 *
 * Fails with code `positiveOrZero` and, by default, the message `must be positive or zero`
 * ([Messages.positiveOrZero][io.github.soleworks.validity.Messages.positiveOrZero]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("positiveOrZeroShort")
public fun ValidationNode<Short>.positiveOrZero(
    message: String = messages.positiveOrZero
): Unit = constraint(
    message = message,
    code = "positiveOrZero",
    predicate = { it >= 0 }
)

/**
 * Checks that the number is zero or greater.
 *
 * Fails with code `positiveOrZero` and, by default, the message `must be positive or zero`
 * ([Messages.positiveOrZero][io.github.soleworks.validity.Messages.positiveOrZero]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("positiveOrZeroByte")
public fun ValidationNode<Byte>.positiveOrZero(
    message: String = messages.positiveOrZero
): Unit = constraint(
    message = message,
    code = "positiveOrZero",
    predicate = { it >= 0 }
)

/**
 * Checks that the number is zero or greater.
 *
 * Fails with code `positiveOrZero` and, by default, the message `must be positive or zero`
 * ([Messages.positiveOrZero][io.github.soleworks.validity.Messages.positiveOrZero]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("positiveOrZeroDouble")
public fun ValidationNode<Double>.positiveOrZero(
    message: String = messages.positiveOrZero
): Unit = constraint(
    message = message,
    code = "positiveOrZero",
    predicate = { it >= 0.0 }
)

/**
 * Checks that the number is zero or greater.
 *
 * Fails with code `positiveOrZero` and, by default, the message `must be positive or zero`
 * ([Messages.positiveOrZero][io.github.soleworks.validity.Messages.positiveOrZero]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("positiveOrZeroFloat")
public fun ValidationNode<Float>.positiveOrZero(
    message: String = messages.positiveOrZero
): Unit = constraint(
    message = message,
    code = "positiveOrZero",
    predicate = { it >= 0f }
)

/**
 * Checks that the number is zero or greater.
 *
 * Fails with code `positiveOrZero` and, by default, the message `must be positive or zero`
 * ([Messages.positiveOrZero][io.github.soleworks.validity.Messages.positiveOrZero]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("positiveOrZeroBigInteger")
public fun ValidationNode<BigInteger>.positiveOrZero(
    message: String = messages.positiveOrZero
): Unit = constraint(
    message = message,
    code = "positiveOrZero",
    predicate = { it.signum() >= 0 }
)

/**
 * Checks that the number is zero or greater.
 *
 * Fails with code `positiveOrZero` and, by default, the message `must be positive or zero`
 * ([Messages.positiveOrZero][io.github.soleworks.validity.Messages.positiveOrZero]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("positiveOrZeroBigDecimal")
public fun ValidationNode<BigDecimal>.positiveOrZero(
    message: String = messages.positiveOrZero
): Unit = constraint(
    message = message,
    code = "positiveOrZero",
    predicate = { it.signum() >= 0 }
)

/**
 * Checks that the number is zero or less.
 *
 * Fails with code `negativeOrZero` and, by default, the message `must be negative or zero`
 * ([Messages.negativeOrZero][io.github.soleworks.validity.Messages.negativeOrZero]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Int>.negativeOrZero(
    message: String = messages.negativeOrZero
): Unit = constraint(
    message = message,
    code = "negativeOrZero",
    predicate = { it <= 0 }
)

/**
 * Checks that the number is zero or less.
 *
 * Fails with code `negativeOrZero` and, by default, the message `must be negative or zero`
 * ([Messages.negativeOrZero][io.github.soleworks.validity.Messages.negativeOrZero]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("negativeOrZeroLong")
public fun ValidationNode<Long>.negativeOrZero(
    message: String = messages.negativeOrZero
): Unit = constraint(
    message = message,
    code = "negativeOrZero",
    predicate = { it <= 0 }
)

/**
 * Checks that the number is zero or less.
 *
 * Fails with code `negativeOrZero` and, by default, the message `must be negative or zero`
 * ([Messages.negativeOrZero][io.github.soleworks.validity.Messages.negativeOrZero]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("negativeOrZeroShort")
public fun ValidationNode<Short>.negativeOrZero(
    message: String = messages.negativeOrZero
): Unit = constraint(
    message = message,
    code = "negativeOrZero",
    predicate = { it <= 0 }
)

/**
 * Checks that the number is zero or less.
 *
 * Fails with code `negativeOrZero` and, by default, the message `must be negative or zero`
 * ([Messages.negativeOrZero][io.github.soleworks.validity.Messages.negativeOrZero]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("negativeOrZeroByte")
public fun ValidationNode<Byte>.negativeOrZero(
    message: String = messages.negativeOrZero
): Unit = constraint(
    message = message,
    code = "negativeOrZero",
    predicate = { it <= 0 }
)

/**
 * Checks that the number is zero or less.
 *
 * Fails with code `negativeOrZero` and, by default, the message `must be negative or zero`
 * ([Messages.negativeOrZero][io.github.soleworks.validity.Messages.negativeOrZero]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("negativeOrZeroDouble")
public fun ValidationNode<Double>.negativeOrZero(
    message: String = messages.negativeOrZero
): Unit = constraint(
    message = message,
    code = "negativeOrZero",
    predicate = { it <= 0.0 }
)

/**
 * Checks that the number is zero or less.
 *
 * Fails with code `negativeOrZero` and, by default, the message `must be negative or zero`
 * ([Messages.negativeOrZero][io.github.soleworks.validity.Messages.negativeOrZero]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("negativeOrZeroFloat")
public fun ValidationNode<Float>.negativeOrZero(
    message: String = messages.negativeOrZero
): Unit = constraint(
    message = message,
    code = "negativeOrZero",
    predicate = { it <= 0f }
)

/**
 * Checks that the number is zero or less.
 *
 * Fails with code `negativeOrZero` and, by default, the message `must be negative or zero`
 * ([Messages.negativeOrZero][io.github.soleworks.validity.Messages.negativeOrZero]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("negativeOrZeroBigInteger")
public fun ValidationNode<BigInteger>.negativeOrZero(
    message: String = messages.negativeOrZero
): Unit = constraint(
    message = message,
    code = "negativeOrZero",
    predicate = { it.signum() <= 0 }
)

/**
 * Checks that the number is zero or less.
 *
 * Fails with code `negativeOrZero` and, by default, the message `must be negative or zero`
 * ([Messages.negativeOrZero][io.github.soleworks.validity.Messages.negativeOrZero]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("negativeOrZeroBigDecimal")
public fun ValidationNode<BigDecimal>.negativeOrZero(
    message: String = messages.negativeOrZero
): Unit = constraint(
    message = message,
    code = "negativeOrZero",
    predicate = { it.signum() <= 0 }
)

/**
 * Checks that the number is a multiple of [factor].
 *
 * Fails with code `multipleOf` and, by default, the message `must be a multiple of {factor}`
 * ([Messages.multipleOf][io.github.soleworks.validity.Messages.multipleOf]).
 *
 * @param factor the number it must be a multiple of. It cannot be zero.
 * @param message the message of the violation. `{factor}`, `{path}` and `{value}` are filled in.
 * @throws IllegalArgumentException when [factor] is zero.
 */
public fun ValidationNode<Int>.multipleOf(
    factor: Int,
    message: String = messages.multipleOf
): Unit = multipleOf(
    factor = factor,
    message = { message.filled("factor" to factor) }
)

/**
 * Checks that the number is a multiple of [factor].
 *
 * Fails with code `multipleOf` and the message that [message] builds.
 *
 * @param factor the number it must be a multiple of. It cannot be zero.
 * @param message builds the message from the value (the receiver) and [factor].
 * @throws IllegalArgumentException when [factor] is zero.
 */
public fun ValidationNode<Int>.multipleOf(
    factor: Int,
    message: suspend Int.(Int) -> String
) {
    require(factor != 0) { "multipleOf needs a factor other than 0" }

    constraint(
        message = { it.message(factor) },
        code = "multipleOf",
        predicate = { it % factor == 0 }
    )
}

/**
 * Checks that the number is a multiple of [factor].
 *
 * Fails with code `multipleOf` and, by default, the message `must be a multiple of {factor}`
 * ([Messages.multipleOf][io.github.soleworks.validity.Messages.multipleOf]).
 *
 * @param factor the number it must be a multiple of. It cannot be zero.
 * @param message the message of the violation. `{factor}`, `{path}` and `{value}` are filled in.
 * @throws IllegalArgumentException when [factor] is zero.
 */
public fun ValidationNode<Long>.multipleOf(
    factor: Long,
    message: String = messages.multipleOf
): Unit = multipleOf(
    factor = factor,
    message = { message.filled("factor" to factor) }
)

/**
 * Checks that the number is a multiple of [factor].
 *
 * Fails with code `multipleOf` and the message that [message] builds.
 *
 * @param factor the number it must be a multiple of. It cannot be zero.
 * @param message builds the message from the value (the receiver) and [factor].
 * @throws IllegalArgumentException when [factor] is zero.
 */
public fun ValidationNode<Long>.multipleOf(
    factor: Long,
    message: suspend Long.(Long) -> String
) {
    require(factor != 0L) { "multipleOf needs a factor other than 0" }

    constraint(
        message = { it.message(factor) },
        code = "multipleOf",
        predicate = { it % factor == 0L }
    )
}

/**
 * Checks that the number is a multiple of [factor].
 *
 * Fails with code `multipleOf` and, by default, the message `must be a multiple of {factor}`
 * ([Messages.multipleOf][io.github.soleworks.validity.Messages.multipleOf]).
 *
 * @param factor the number it must be a multiple of. It cannot be zero.
 * @param message the message of the violation. `{factor}`, `{path}` and `{value}` are filled in.
 * @throws IllegalArgumentException when [factor] is zero.
 */
public fun ValidationNode<Short>.multipleOf(
    factor: Short,
    message: String = messages.multipleOf
): Unit = multipleOf(
    factor = factor,
    message = { message.filled("factor" to factor) }
)

/**
 * Checks that the number is a multiple of [factor].
 *
 * Fails with code `multipleOf` and the message that [message] builds.
 *
 * @param factor the number it must be a multiple of. It cannot be zero.
 * @param message builds the message from the value (the receiver) and [factor].
 * @throws IllegalArgumentException when [factor] is zero.
 */
public fun ValidationNode<Short>.multipleOf(
    factor: Short,
    message: suspend Short.(Short) -> String
) {
    require(factor.toInt() != 0) { "multipleOf needs a factor other than 0" }

    constraint(
        message = { it.message(factor) },
        code = "multipleOf",
        predicate = { it % factor == 0 }
    )
}

/**
 * Checks that the number is a multiple of [factor].
 *
 * Fails with code `multipleOf` and, by default, the message `must be a multiple of {factor}`
 * ([Messages.multipleOf][io.github.soleworks.validity.Messages.multipleOf]).
 *
 * @param factor the number it must be a multiple of. It cannot be zero.
 * @param message the message of the violation. `{factor}`, `{path}` and `{value}` are filled in.
 * @throws IllegalArgumentException when [factor] is zero.
 */
public fun ValidationNode<Byte>.multipleOf(
    factor: Byte,
    message: String = messages.multipleOf
): Unit = multipleOf(
    factor = factor,
    message = { message.filled("factor" to factor) }
)

/**
 * Checks that the number is a multiple of [factor].
 *
 * Fails with code `multipleOf` and the message that [message] builds.
 *
 * @param factor the number it must be a multiple of. It cannot be zero.
 * @param message builds the message from the value (the receiver) and [factor].
 * @throws IllegalArgumentException when [factor] is zero.
 */
public fun ValidationNode<Byte>.multipleOf(
    factor: Byte,
    message: suspend Byte.(Byte) -> String
) {
    require(factor.toInt() != 0) { "multipleOf needs a factor other than 0" }

    constraint(
        message = { it.message(factor) },
        code = "multipleOf",
        predicate = { it % factor == 0 }
    )
}

/**
 * Checks that the number is a multiple of [factor].
 *
 * Fails with code `multipleOf` and, by default, the message `must be a multiple of {factor}`
 * ([Messages.multipleOf][io.github.soleworks.validity.Messages.multipleOf]).
 *
 * @param factor the number it must be a multiple of. It cannot be zero.
 * @param message the message of the violation. `{factor}`, `{path}` and `{value}` are filled in.
 * @throws IllegalArgumentException when [factor] is zero.
 */
public fun ValidationNode<BigInteger>.multipleOf(
    factor: BigInteger,
    message: String = messages.multipleOf
): Unit = multipleOf(
    factor = factor,
    message = { message.filled("factor" to factor) }
)

/**
 * Checks that the number is a multiple of [factor].
 *
 * Fails with code `multipleOf` and the message that [message] builds.
 *
 * @param factor the number it must be a multiple of. It cannot be zero.
 * @param message builds the message from the value (the receiver) and [factor].
 * @throws IllegalArgumentException when [factor] is zero.
 */
public fun ValidationNode<BigInteger>.multipleOf(
    factor: BigInteger,
    message: suspend BigInteger.(BigInteger) -> String
) {
    require(factor.signum() != 0) { "multipleOf needs a factor other than 0" }

    constraint(
        message = { it.message(factor) },
        code = "multipleOf",
        predicate = { it.remainder(factor).signum() == 0 }
    )
}

/**
 * Checks that the number is a multiple of [factor].
 *
 * Fails with code `multipleOf` and, by default, the message `must be a multiple of {factor}`
 * ([Messages.multipleOf][io.github.soleworks.validity.Messages.multipleOf]).
 *
 * @param factor the number it must be a multiple of. It cannot be zero.
 * @param message the message of the violation. `{factor}`, `{path}` and `{value}` are filled in.
 * @throws IllegalArgumentException when [factor] is zero.
 */
public fun ValidationNode<BigDecimal>.multipleOf(
    factor: BigDecimal,
    message: String = messages.multipleOf
): Unit = multipleOf(
    factor = factor,
    message = { message.filled("factor" to factor) }
)

/**
 * Checks that the number is a multiple of [factor].
 *
 * Fails with code `multipleOf` and the message that [message] builds.
 *
 * @param factor the number it must be a multiple of. It cannot be zero.
 * @param message builds the message from the value (the receiver) and [factor].
 * @throws IllegalArgumentException when [factor] is zero.
 */
public fun ValidationNode<BigDecimal>.multipleOf(
    factor: BigDecimal,
    message: suspend BigDecimal.(BigDecimal) -> String
) {
    require(factor.signum() != 0) { "multipleOf needs a factor other than 0" }

    constraint(
        message = { it.message(factor) },
        code = "multipleOf",
        predicate = { it.remainder(factor).signum() == 0 }
    )
}

/**
 * Checks that the number is a multiple of [factor].
 *
 * The remainder is computed in decimal, so `0.3` is a multiple of `0.1`. Infinity and `NaN` fail.
 *
 * Fails with code `multipleOf` and, by default, the message `must be a multiple of {factor}`
 * ([Messages.multipleOf][io.github.soleworks.validity.Messages.multipleOf]).
 *
 * @param factor the number it must be a multiple of. It cannot be zero.
 * @param message the message of the violation. `{factor}`, `{path}` and `{value}` are filled in.
 * @throws IllegalArgumentException when [factor] is zero, infinite or `NaN`.
 */
public fun ValidationNode<Double>.multipleOf(
    factor: Double,
    message: String = messages.multipleOf
): Unit = multipleOf(
    factor = factor,
    message = { message.filled("factor" to factor) }
)

/**
 * Checks that the number is a multiple of [factor].
 *
 * The remainder is computed in decimal, so `0.3` is a multiple of `0.1`. Infinity and `NaN` fail.
 *
 * Fails with code `multipleOf` and the message that [message] builds.
 *
 * @param factor the number it must be a multiple of. It cannot be zero.
 * @param message builds the message from the value (the receiver) and [factor].
 * @throws IllegalArgumentException when [factor] is zero, infinite or `NaN`.
 */
public fun ValidationNode<Double>.multipleOf(
    factor: Double,
    message: suspend Double.(Double) -> String
) {
    require(factor.isFinite() && factor != 0.0) { "multipleOf needs a finite factor other than 0" }

    constraint(
        message = { it.message(factor) },
        code = "multipleOf",
        predicate = { it.isFinite() && it.toBigDecimal().remainder(factor.toBigDecimal()).signum() == 0 }
    )
}

/**
 * Checks that the number is a multiple of [factor].
 *
 * The remainder is computed in decimal, so `0.3` is a multiple of `0.1`. Infinity and `NaN` fail.
 *
 * Fails with code `multipleOf` and, by default, the message `must be a multiple of {factor}`
 * ([Messages.multipleOf][io.github.soleworks.validity.Messages.multipleOf]).
 *
 * @param factor the number it must be a multiple of. It cannot be zero.
 * @param message the message of the violation. `{factor}`, `{path}` and `{value}` are filled in.
 * @throws IllegalArgumentException when [factor] is zero, infinite or `NaN`.
 */
public fun ValidationNode<Float>.multipleOf(
    factor: Float,
    message: String = messages.multipleOf
): Unit = multipleOf(
    factor = factor,
    message = { message.filled("factor" to factor) }
)

/**
 * Checks that the number is a multiple of [factor].
 *
 * The remainder is computed in decimal, so `0.3` is a multiple of `0.1`. Infinity and `NaN` fail.
 *
 * Fails with code `multipleOf` and the message that [message] builds.
 *
 * @param factor the number it must be a multiple of. It cannot be zero.
 * @param message builds the message from the value (the receiver) and [factor].
 * @throws IllegalArgumentException when [factor] is zero, infinite or `NaN`.
 */
public fun ValidationNode<Float>.multipleOf(
    factor: Float,
    message: suspend Float.(Float) -> String
) {
    require(factor.isFinite() && factor != 0f) { "multipleOf needs a finite factor other than 0" }

    constraint(
        message = { it.message(factor) },
        code = "multipleOf",
        predicate = { it.isFinite() && it.toBigDecimal().remainder(factor.toBigDecimal()).signum() == 0 }
    )
}

/**
 * Checks that the number has no fractional part.
 *
 * Infinity and `NaN` fail.
 *
 * Fails with code `integer` and, by default, the message `must be an integer`
 * ([Messages.integer][io.github.soleworks.validity.Messages.integer]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Double>.integer(
    message: String = messages.integer
): Unit = constraint(
    message = message,
    code = "integer",
    predicate = { it.isFinite() && it % 1.0 == 0.0 }
)

/**
 * Checks that the number has no fractional part.
 *
 * Infinity and `NaN` fail.
 *
 * Fails with code `integer` and, by default, the message `must be an integer`
 * ([Messages.integer][io.github.soleworks.validity.Messages.integer]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("integerFloat")
public fun ValidationNode<Float>.integer(
    message: String = messages.integer
): Unit = constraint(
    message = message,
    code = "integer",
    predicate = { it.isFinite() && it % 1f == 0f }
)

/**
 * Checks that the number has no fractional part.
 *
 * Trailing zeros do not count, so `2.00` passes.
 *
 * Fails with code `integer` and, by default, the message `must be an integer`
 * ([Messages.integer][io.github.soleworks.validity.Messages.integer]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("integerBigDecimal")
public fun ValidationNode<BigDecimal>.integer(
    message: String = messages.integer
): Unit = constraint(
    message = message,
    code = "integer",
    predicate = { it.signum() == 0 || it.stripTrailingZeros().scale() <= 0 }
)

/**
 * Checks that the number is neither infinite nor `NaN`.
 *
 * Fails with code `finite` and, by default, the message `must be finite`
 * ([Messages.finite][io.github.soleworks.validity.Messages.finite]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Double>.finite(
    message: String = messages.finite
): Unit = constraint(
    message = message,
    code = "finite",
    predicate = { it.isFinite() }
)

/**
 * Checks that the number is neither infinite nor `NaN`.
 *
 * Fails with code `finite` and, by default, the message `must be finite`
 * ([Messages.finite][io.github.soleworks.validity.Messages.finite]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("finiteFloat")
public fun ValidationNode<Float>.finite(
    message: String = messages.finite
): Unit = constraint(
    message = message,
    code = "finite",
    predicate = { it.isFinite() }
)

/**
 * Checks that the number has at most [max] digits after the decimal point.
 *
 * Trailing zeros do not count, so `1.50` has one decimal place.
 *
 * Fails with code `maxDecimalPlaces` and, by default, the message `must have at most {max} decimal places`
 * ([Messages.maxDecimalPlaces][io.github.soleworks.validity.Messages.maxDecimalPlaces]).
 *
 * ```kotlin
 * ::amount required { maxDecimalPlaces(2) }
 * ```
 *
 * @param max the most decimal places accepted.
 * @param message the message of the violation. `{max}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<BigDecimal>.maxDecimalPlaces(
    max: Int,
    message: String = messages.maxDecimalPlaces
): Unit = maxDecimalPlaces(
    max = max,
    message = { message.filled("max" to max) }
)

/**
 * Checks that the number has at most [max] digits after the decimal point.
 *
 * Trailing zeros do not count, so `1.50` has one decimal place.
 *
 * Fails with code `maxDecimalPlaces` and the message that [message] builds.
 *
 * @param max the most decimal places accepted.
 * @param message builds the message from the value (the receiver) and [max].
 */
public fun ValidationNode<BigDecimal>.maxDecimalPlaces(
    max: Int,
    message: suspend BigDecimal.(Int) -> String
): Unit = constraint(
    message = { it.message(max) },
    code = "maxDecimalPlaces",
    predicate = { it.stripTrailingZeros().scale() <= max }
)

/**
 * Checks that the number has at most [max] digits before the decimal point.
 *
 * Fails with code `maxIntegerDigits` and, by default, the message `must have at most {max} integer digits`
 * ([Messages.maxIntegerDigits][io.github.soleworks.validity.Messages.maxIntegerDigits]).
 *
 * @param max the most integer digits accepted.
 * @param message the message of the violation. `{max}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<BigDecimal>.maxIntegerDigits(
    max: Int,
    message: String = messages.maxIntegerDigits
): Unit = maxIntegerDigits(
    max = max,
    message = { message.filled("max" to max) }
)

/**
 * Checks that the number has at most [max] digits before the decimal point.
 *
 * Fails with code `maxIntegerDigits` and the message that [message] builds.
 *
 * @param max the most integer digits accepted.
 * @param message builds the message from the value (the receiver) and [max].
 */
public fun ValidationNode<BigDecimal>.maxIntegerDigits(
    max: Int,
    message: suspend BigDecimal.(Int) -> String
): Unit = constraint(
    message = { it.message(max) },
    code = "maxIntegerDigits",
    predicate = { it.stripTrailingZeros().run { precision() - scale() } <= max }
)

/**
 * Checks that the number has at least [min] digits, not counting the sign.
 *
 * Fails with code `minDigits` and, by default, the message `must have at least {min} digits`
 * ([Messages.minDigits][io.github.soleworks.validity.Messages.minDigits]).
 *
 * @param min the fewest digits accepted.
 * @param message the message of the violation. `{min}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Int>.minDigits(
    min: Int,
    message: String = messages.minDigits
): Unit = minDigits(
    min = min,
    message = { message.filled("min" to min) }
)

/**
 * Checks that the number has at least [min] digits, not counting the sign.
 *
 * Fails with code `minDigits` and the message that [message] builds.
 *
 * @param min the fewest digits accepted.
 * @param message builds the message from the value (the receiver) and [min].
 */
public fun ValidationNode<Int>.minDigits(
    min: Int,
    message: suspend Int.(Int) -> String
): Unit = constraint(
    message = { it.message(min) },
    code = "minDigits",
    predicate = { it.toString().removePrefix("-").length >= min }
)

/**
 * Checks that the number has at least [min] digits, not counting the sign.
 *
 * Fails with code `minDigits` and, by default, the message `must have at least {min} digits`
 * ([Messages.minDigits][io.github.soleworks.validity.Messages.minDigits]).
 *
 * @param min the fewest digits accepted.
 * @param message the message of the violation. `{min}`, `{path}` and `{value}` are filled in.
 */
@JvmName("minDigitsLong")
public fun ValidationNode<Long>.minDigits(
    min: Int,
    message: String = messages.minDigits
): Unit = minDigits(
    min = min,
    message = { message.filled("min" to min) }
)

/**
 * Checks that the number has at least [min] digits, not counting the sign.
 *
 * Fails with code `minDigits` and the message that [message] builds.
 *
 * @param min the fewest digits accepted.
 * @param message builds the message from the value (the receiver) and [min].
 */
@JvmName("minDigitsLong")
public fun ValidationNode<Long>.minDigits(
    min: Int,
    message: suspend Long.(Int) -> String
): Unit = constraint(
    message = { it.message(min) },
    code = "minDigits",
    predicate = { it.toString().removePrefix("-").length >= min }
)

/**
 * Checks that the number has at least [min] digits, not counting the sign.
 *
 * Fails with code `minDigits` and, by default, the message `must have at least {min} digits`
 * ([Messages.minDigits][io.github.soleworks.validity.Messages.minDigits]).
 *
 * @param min the fewest digits accepted.
 * @param message the message of the violation. `{min}`, `{path}` and `{value}` are filled in.
 */
@JvmName("minDigitsShort")
public fun ValidationNode<Short>.minDigits(
    min: Int,
    message: String = messages.minDigits
): Unit = minDigits(
    min = min,
    message = { message.filled("min" to min) }
)

/**
 * Checks that the number has at least [min] digits, not counting the sign.
 *
 * Fails with code `minDigits` and the message that [message] builds.
 *
 * @param min the fewest digits accepted.
 * @param message builds the message from the value (the receiver) and [min].
 */
@JvmName("minDigitsShort")
public fun ValidationNode<Short>.minDigits(
    min: Int,
    message: suspend Short.(Int) -> String
): Unit = constraint(
    message = { it.message(min) },
    code = "minDigits",
    predicate = { it.toString().removePrefix("-").length >= min }
)

/**
 * Checks that the number has at least [min] digits, not counting the sign.
 *
 * Fails with code `minDigits` and, by default, the message `must have at least {min} digits`
 * ([Messages.minDigits][io.github.soleworks.validity.Messages.minDigits]).
 *
 * @param min the fewest digits accepted.
 * @param message the message of the violation. `{min}`, `{path}` and `{value}` are filled in.
 */
@JvmName("minDigitsByte")
public fun ValidationNode<Byte>.minDigits(
    min: Int,
    message: String = messages.minDigits
): Unit = minDigits(
    min = min,
    message = { message.filled("min" to min) }
)

/**
 * Checks that the number has at least [min] digits, not counting the sign.
 *
 * Fails with code `minDigits` and the message that [message] builds.
 *
 * @param min the fewest digits accepted.
 * @param message builds the message from the value (the receiver) and [min].
 */
@JvmName("minDigitsByte")
public fun ValidationNode<Byte>.minDigits(
    min: Int,
    message: suspend Byte.(Int) -> String
): Unit = constraint(
    message = { it.message(min) },
    code = "minDigits",
    predicate = { it.toString().removePrefix("-").length >= min }
)

/**
 * Checks that the number has at least [min] digits, not counting the sign.
 *
 * Fails with code `minDigits` and, by default, the message `must have at least {min} digits`
 * ([Messages.minDigits][io.github.soleworks.validity.Messages.minDigits]).
 *
 * @param min the fewest digits accepted.
 * @param message the message of the violation. `{min}`, `{path}` and `{value}` are filled in.
 */
@JvmName("minDigitsBigInteger")
public fun ValidationNode<BigInteger>.minDigits(
    min: Int,
    message: String = messages.minDigits
): Unit = minDigits(
    min = min,
    message = { message.filled("min" to min) }
)

/**
 * Checks that the number has at least [min] digits, not counting the sign.
 *
 * Fails with code `minDigits` and the message that [message] builds.
 *
 * @param min the fewest digits accepted.
 * @param message builds the message from the value (the receiver) and [min].
 */
@JvmName("minDigitsBigInteger")
public fun ValidationNode<BigInteger>.minDigits(
    min: Int,
    message: suspend BigInteger.(Int) -> String
): Unit = constraint(
    message = { it.message(min) },
    code = "minDigits",
    predicate = { it.toString().removePrefix("-").length >= min }
)

/**
 * Checks that the number has at most [max] digits, not counting the sign.
 *
 * Fails with code `maxDigits` and, by default, the message `must have at most {max} digits`
 * ([Messages.maxDigits][io.github.soleworks.validity.Messages.maxDigits]).
 *
 * @param max the most digits accepted.
 * @param message the message of the violation. `{max}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Int>.maxDigits(
    max: Int,
    message: String = messages.maxDigits
): Unit = maxDigits(
    max = max,
    message = { message.filled("max" to max) }
)

/**
 * Checks that the number has at most [max] digits, not counting the sign.
 *
 * Fails with code `maxDigits` and the message that [message] builds.
 *
 * @param max the most digits accepted.
 * @param message builds the message from the value (the receiver) and [max].
 */
public fun ValidationNode<Int>.maxDigits(
    max: Int,
    message: suspend Int.(Int) -> String
): Unit = constraint(
    message = { it.message(max) },
    code = "maxDigits",
    predicate = { it.toString().removePrefix("-").length <= max }
)

/**
 * Checks that the number has at most [max] digits, not counting the sign.
 *
 * Fails with code `maxDigits` and, by default, the message `must have at most {max} digits`
 * ([Messages.maxDigits][io.github.soleworks.validity.Messages.maxDigits]).
 *
 * @param max the most digits accepted.
 * @param message the message of the violation. `{max}`, `{path}` and `{value}` are filled in.
 */
@JvmName("maxDigitsLong")
public fun ValidationNode<Long>.maxDigits(
    max: Int,
    message: String = messages.maxDigits
): Unit = maxDigits(
    max = max,
    message = { message.filled("max" to max) }
)

/**
 * Checks that the number has at most [max] digits, not counting the sign.
 *
 * Fails with code `maxDigits` and the message that [message] builds.
 *
 * @param max the most digits accepted.
 * @param message builds the message from the value (the receiver) and [max].
 */
@JvmName("maxDigitsLong")
public fun ValidationNode<Long>.maxDigits(
    max: Int,
    message: suspend Long.(Int) -> String
): Unit = constraint(
    message = { it.message(max) },
    code = "maxDigits",
    predicate = { it.toString().removePrefix("-").length <= max }
)

/**
 * Checks that the number has at most [max] digits, not counting the sign.
 *
 * Fails with code `maxDigits` and, by default, the message `must have at most {max} digits`
 * ([Messages.maxDigits][io.github.soleworks.validity.Messages.maxDigits]).
 *
 * @param max the most digits accepted.
 * @param message the message of the violation. `{max}`, `{path}` and `{value}` are filled in.
 */
@JvmName("maxDigitsShort")
public fun ValidationNode<Short>.maxDigits(
    max: Int,
    message: String = messages.maxDigits
): Unit = maxDigits(
    max = max,
    message = { message.filled("max" to max) }
)

/**
 * Checks that the number has at most [max] digits, not counting the sign.
 *
 * Fails with code `maxDigits` and the message that [message] builds.
 *
 * @param max the most digits accepted.
 * @param message builds the message from the value (the receiver) and [max].
 */
@JvmName("maxDigitsShort")
public fun ValidationNode<Short>.maxDigits(
    max: Int,
    message: suspend Short.(Int) -> String
): Unit = constraint(
    message = { it.message(max) },
    code = "maxDigits",
    predicate = { it.toString().removePrefix("-").length <= max }
)

/**
 * Checks that the number has at most [max] digits, not counting the sign.
 *
 * Fails with code `maxDigits` and, by default, the message `must have at most {max} digits`
 * ([Messages.maxDigits][io.github.soleworks.validity.Messages.maxDigits]).
 *
 * @param max the most digits accepted.
 * @param message the message of the violation. `{max}`, `{path}` and `{value}` are filled in.
 */
@JvmName("maxDigitsByte")
public fun ValidationNode<Byte>.maxDigits(
    max: Int,
    message: String = messages.maxDigits
): Unit = maxDigits(
    max = max,
    message = { message.filled("max" to max) }
)

/**
 * Checks that the number has at most [max] digits, not counting the sign.
 *
 * Fails with code `maxDigits` and the message that [message] builds.
 *
 * @param max the most digits accepted.
 * @param message builds the message from the value (the receiver) and [max].
 */
@JvmName("maxDigitsByte")
public fun ValidationNode<Byte>.maxDigits(
    max: Int,
    message: suspend Byte.(Int) -> String
): Unit = constraint(
    message = { it.message(max) },
    code = "maxDigits",
    predicate = { it.toString().removePrefix("-").length <= max }
)

/**
 * Checks that the number has at most [max] digits, not counting the sign.
 *
 * Fails with code `maxDigits` and, by default, the message `must have at most {max} digits`
 * ([Messages.maxDigits][io.github.soleworks.validity.Messages.maxDigits]).
 *
 * @param max the most digits accepted.
 * @param message the message of the violation. `{max}`, `{path}` and `{value}` are filled in.
 */
@JvmName("maxDigitsBigInteger")
public fun ValidationNode<BigInteger>.maxDigits(
    max: Int,
    message: String = messages.maxDigits
): Unit = maxDigits(
    max = max,
    message = { message.filled("max" to max) }
)

/**
 * Checks that the number has at most [max] digits, not counting the sign.
 *
 * Fails with code `maxDigits` and the message that [message] builds.
 *
 * @param max the most digits accepted.
 * @param message builds the message from the value (the receiver) and [max].
 */
@JvmName("maxDigitsBigInteger")
public fun ValidationNode<BigInteger>.maxDigits(
    max: Int,
    message: suspend BigInteger.(Int) -> String
): Unit = constraint(
    message = { it.message(max) },
    code = "maxDigits",
    predicate = { it.toString().removePrefix("-").length <= max }
)

/**
 * Checks that the number has exactly [count] digits, not counting the sign.
 *
 * Fails with code `digitCount` and, by default, the message `must have exactly {count} digits`
 * ([Messages.digitCount][io.github.soleworks.validity.Messages.digitCount]).
 *
 * @param count the number of digits required.
 * @param message the message of the violation. `{count}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Int>.digitCount(
    count: Int,
    message: String = messages.digitCount
): Unit = digitCount(
    count = count,
    message = { message.filled("count" to count) }
)

/**
 * Checks that the number has exactly [count] digits, not counting the sign.
 *
 * Fails with code `digitCount` and the message that [message] builds.
 *
 * @param count the number of digits required.
 * @param message builds the message from the value (the receiver) and [count].
 */
public fun ValidationNode<Int>.digitCount(
    count: Int,
    message: suspend Int.(Int) -> String
): Unit = constraint(
    message = { it.message(count) },
    code = "digitCount",
    predicate = { it.toString().removePrefix("-").length == count }
)

/**
 * Checks that the number has exactly [count] digits, not counting the sign.
 *
 * Fails with code `digitCount` and, by default, the message `must have exactly {count} digits`
 * ([Messages.digitCount][io.github.soleworks.validity.Messages.digitCount]).
 *
 * @param count the number of digits required.
 * @param message the message of the violation. `{count}`, `{path}` and `{value}` are filled in.
 */
@JvmName("digitCountLong")
public fun ValidationNode<Long>.digitCount(
    count: Int,
    message: String = messages.digitCount
): Unit = digitCount(
    count = count,
    message = { message.filled("count" to count) }
)

/**
 * Checks that the number has exactly [count] digits, not counting the sign.
 *
 * Fails with code `digitCount` and the message that [message] builds.
 *
 * @param count the number of digits required.
 * @param message builds the message from the value (the receiver) and [count].
 */
@JvmName("digitCountLong")
public fun ValidationNode<Long>.digitCount(
    count: Int,
    message: suspend Long.(Int) -> String
): Unit = constraint(
    message = { it.message(count) },
    code = "digitCount",
    predicate = { it.toString().removePrefix("-").length == count }
)

/**
 * Checks that the number has exactly [count] digits, not counting the sign.
 *
 * Fails with code `digitCount` and, by default, the message `must have exactly {count} digits`
 * ([Messages.digitCount][io.github.soleworks.validity.Messages.digitCount]).
 *
 * @param count the number of digits required.
 * @param message the message of the violation. `{count}`, `{path}` and `{value}` are filled in.
 */
@JvmName("digitCountShort")
public fun ValidationNode<Short>.digitCount(
    count: Int,
    message: String = messages.digitCount
): Unit = digitCount(
    count = count,
    message = { message.filled("count" to count) }
)

/**
 * Checks that the number has exactly [count] digits, not counting the sign.
 *
 * Fails with code `digitCount` and the message that [message] builds.
 *
 * @param count the number of digits required.
 * @param message builds the message from the value (the receiver) and [count].
 */
@JvmName("digitCountShort")
public fun ValidationNode<Short>.digitCount(
    count: Int,
    message: suspend Short.(Int) -> String
): Unit = constraint(
    message = { it.message(count) },
    code = "digitCount",
    predicate = { it.toString().removePrefix("-").length == count }
)

/**
 * Checks that the number has exactly [count] digits, not counting the sign.
 *
 * Fails with code `digitCount` and, by default, the message `must have exactly {count} digits`
 * ([Messages.digitCount][io.github.soleworks.validity.Messages.digitCount]).
 *
 * @param count the number of digits required.
 * @param message the message of the violation. `{count}`, `{path}` and `{value}` are filled in.
 */
@JvmName("digitCountByte")
public fun ValidationNode<Byte>.digitCount(
    count: Int,
    message: String = messages.digitCount
): Unit = digitCount(
    count = count,
    message = { message.filled("count" to count) }
)

/**
 * Checks that the number has exactly [count] digits, not counting the sign.
 *
 * Fails with code `digitCount` and the message that [message] builds.
 *
 * @param count the number of digits required.
 * @param message builds the message from the value (the receiver) and [count].
 */
@JvmName("digitCountByte")
public fun ValidationNode<Byte>.digitCount(
    count: Int,
    message: suspend Byte.(Int) -> String
): Unit = constraint(
    message = { it.message(count) },
    code = "digitCount",
    predicate = { it.toString().removePrefix("-").length == count }
)

/**
 * Checks that the number has exactly [count] digits, not counting the sign.
 *
 * Fails with code `digitCount` and, by default, the message `must have exactly {count} digits`
 * ([Messages.digitCount][io.github.soleworks.validity.Messages.digitCount]).
 *
 * @param count the number of digits required.
 * @param message the message of the violation. `{count}`, `{path}` and `{value}` are filled in.
 */
@JvmName("digitCountBigInteger")
public fun ValidationNode<BigInteger>.digitCount(
    count: Int,
    message: String = messages.digitCount
): Unit = digitCount(
    count = count,
    message = { message.filled("count" to count) }
)

/**
 * Checks that the number has exactly [count] digits, not counting the sign.
 *
 * Fails with code `digitCount` and the message that [message] builds.
 *
 * @param count the number of digits required.
 * @param message builds the message from the value (the receiver) and [count].
 */
@JvmName("digitCountBigInteger")
public fun ValidationNode<BigInteger>.digitCount(
    count: Int,
    message: suspend BigInteger.(Int) -> String
): Unit = constraint(
    message = { it.message(count) },
    code = "digitCount",
    predicate = { it.toString().removePrefix("-").length == count }
)

/**
 * Checks that the number has exactly [count] digits after the decimal point.
 *
 * Trailing zeros count, so `1.50` has two decimal places, and a whole number has none.
 *
 * Fails with code `decimalPlaces` and, by default, the message `must have exactly {count} decimal places`
 * ([Messages.decimalPlaces][io.github.soleworks.validity.Messages.decimalPlaces]).
 *
 * @param count the number of decimal places required.
 * @param message the message of the violation. `{count}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<BigDecimal>.decimalPlaces(
    count: Int,
    message: String = messages.decimalPlaces
): Unit = decimalPlaces(
    count = count,
    message = { message.filled("count" to count) }
)

/**
 * Checks that the number has exactly [count] digits after the decimal point.
 *
 * Trailing zeros count, so `1.50` has two decimal places, and a whole number has none.
 *
 * Fails with code `decimalPlaces` and the message that [message] builds.
 *
 * @param count the number of decimal places required.
 * @param message builds the message from the value (the receiver) and [count].
 */
public fun ValidationNode<BigDecimal>.decimalPlaces(
    count: Int,
    message: suspend BigDecimal.(Int) -> String
): Unit = constraint(
    message = { it.message(count) },
    code = "decimalPlaces",
    predicate = { it.scale().coerceAtLeast(0) == count }
)

/**
 * Checks that the number has at least [min] digits after the decimal point.
 *
 * Trailing zeros count, so `1.50` has two decimal places.
 *
 * Fails with code `minDecimalPlaces` and, by default, the message `must have at least {min} decimal places`
 * ([Messages.minDecimalPlaces][io.github.soleworks.validity.Messages.minDecimalPlaces]).
 *
 * @param min the fewest decimal places accepted.
 * @param message the message of the violation. `{min}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<BigDecimal>.minDecimalPlaces(
    min: Int,
    message: String = messages.minDecimalPlaces
): Unit = minDecimalPlaces(
    min = min,
    message = { message.filled("min" to min) }
)

/**
 * Checks that the number has at least [min] digits after the decimal point.
 *
 * Trailing zeros count, so `1.50` has two decimal places.
 *
 * Fails with code `minDecimalPlaces` and the message that [message] builds.
 *
 * @param min the fewest decimal places accepted.
 * @param message builds the message from the value (the receiver) and [min].
 */
public fun ValidationNode<BigDecimal>.minDecimalPlaces(
    min: Int,
    message: suspend BigDecimal.(Int) -> String
): Unit = constraint(
    message = { it.message(min) },
    code = "minDecimalPlaces",
    predicate = { it.scale() >= min }
)

/**
 * Checks that the number has at most [max] digits after the decimal point.
 *
 * Trailing zeros do not count, so `1.50` has one decimal place.
 *
 * The number is read in decimal, the way it prints. Infinity and `NaN` fail.
 *
 * Fails with code `maxDecimalPlaces` and, by default, the message `must have at most {max} decimal places`
 * ([Messages.maxDecimalPlaces][io.github.soleworks.validity.Messages.maxDecimalPlaces]).
 *
 * @param max the most decimal places accepted.
 * @param message the message of the violation. `{max}`, `{path}` and `{value}` are filled in.
 */
@JvmName("maxDecimalPlacesDouble")
public fun ValidationNode<Double>.maxDecimalPlaces(
    max: Int,
    message: String = messages.maxDecimalPlaces
): Unit = maxDecimalPlaces(
    max = max,
    message = { message.filled("max" to max) }
)

/**
 * Checks that the number has at most [max] digits after the decimal point.
 *
 * Trailing zeros do not count, so `1.50` has one decimal place.
 *
 * The number is read in decimal, the way it prints. Infinity and `NaN` fail.
 *
 * Fails with code `maxDecimalPlaces` and the message that [message] builds.
 *
 * @param max the most decimal places accepted.
 * @param message builds the message from the value (the receiver) and [max].
 */
@JvmName("maxDecimalPlacesDouble")
public fun ValidationNode<Double>.maxDecimalPlaces(
    max: Int,
    message: suspend Double.(Int) -> String
): Unit = constraint(
    message = { it.message(max) },
    code = "maxDecimalPlaces",
    predicate = { it.isFinite() && it.toBigDecimal().stripTrailingZeros().scale() <= max }
)

/**
 * Checks that the number has at most [max] digits after the decimal point.
 *
 * Trailing zeros do not count, so `1.50` has one decimal place.
 *
 * The number is read in decimal, the way it prints. Infinity and `NaN` fail.
 *
 * Fails with code `maxDecimalPlaces` and, by default, the message `must have at most {max} decimal places`
 * ([Messages.maxDecimalPlaces][io.github.soleworks.validity.Messages.maxDecimalPlaces]).
 *
 * @param max the most decimal places accepted.
 * @param message the message of the violation. `{max}`, `{path}` and `{value}` are filled in.
 */
@JvmName("maxDecimalPlacesFloat")
public fun ValidationNode<Float>.maxDecimalPlaces(
    max: Int,
    message: String = messages.maxDecimalPlaces
): Unit = maxDecimalPlaces(
    max = max,
    message = { message.filled("max" to max) }
)

/**
 * Checks that the number has at most [max] digits after the decimal point.
 *
 * Trailing zeros do not count, so `1.50` has one decimal place.
 *
 * The number is read in decimal, the way it prints. Infinity and `NaN` fail.
 *
 * Fails with code `maxDecimalPlaces` and the message that [message] builds.
 *
 * @param max the most decimal places accepted.
 * @param message builds the message from the value (the receiver) and [max].
 */
@JvmName("maxDecimalPlacesFloat")
public fun ValidationNode<Float>.maxDecimalPlaces(
    max: Int,
    message: suspend Float.(Int) -> String
): Unit = constraint(
    message = { it.message(max) },
    code = "maxDecimalPlaces",
    predicate = { it.isFinite() && it.toBigDecimal().stripTrailingZeros().scale() <= max }
)
