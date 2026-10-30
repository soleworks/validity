package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.filled
import io.github.soleworks.validity.messages

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
public fun ValidationNode<UInt>.min(
    min: UInt?,
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
public fun ValidationNode<UInt>.min(
    min: UInt?,
    message: suspend UInt.(UInt) -> String
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
@JvmName("minULong")
public fun ValidationNode<ULong>.min(
    min: ULong?,
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
@JvmName("minULong")
public fun ValidationNode<ULong>.min(
    min: ULong?,
    message: suspend ULong.(ULong) -> String
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
@JvmName("minUShort")
public fun ValidationNode<UShort>.min(
    min: UShort?,
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
@JvmName("minUShort")
public fun ValidationNode<UShort>.min(
    min: UShort?,
    message: suspend UShort.(UShort) -> String
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
@JvmName("minUByte")
public fun ValidationNode<UByte>.min(
    min: UByte?,
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
@JvmName("minUByte")
public fun ValidationNode<UByte>.min(
    min: UByte?,
    message: suspend UByte.(UByte) -> String
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
public fun ValidationNode<UInt>.max(
    max: UInt?,
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
public fun ValidationNode<UInt>.max(
    max: UInt?,
    message: suspend UInt.(UInt) -> String
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
@JvmName("maxULong")
public fun ValidationNode<ULong>.max(
    max: ULong?,
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
@JvmName("maxULong")
public fun ValidationNode<ULong>.max(
    max: ULong?,
    message: suspend ULong.(ULong) -> String
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
@JvmName("maxUShort")
public fun ValidationNode<UShort>.max(
    max: UShort?,
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
@JvmName("maxUShort")
public fun ValidationNode<UShort>.max(
    max: UShort?,
    message: suspend UShort.(UShort) -> String
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
@JvmName("maxUByte")
public fun ValidationNode<UByte>.max(
    max: UByte?,
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
@JvmName("maxUByte")
public fun ValidationNode<UByte>.max(
    max: UByte?,
    message: suspend UByte.(UByte) -> String
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
public fun ValidationNode<UInt>.greaterThan(
    other: UInt?,
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
public fun ValidationNode<UInt>.greaterThan(
    other: UInt?,
    message: suspend UInt.(UInt) -> String
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
@JvmName("greaterThanULong")
public fun ValidationNode<ULong>.greaterThan(
    other: ULong?,
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
@JvmName("greaterThanULong")
public fun ValidationNode<ULong>.greaterThan(
    other: ULong?,
    message: suspend ULong.(ULong) -> String
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
@JvmName("greaterThanUShort")
public fun ValidationNode<UShort>.greaterThan(
    other: UShort?,
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
@JvmName("greaterThanUShort")
public fun ValidationNode<UShort>.greaterThan(
    other: UShort?,
    message: suspend UShort.(UShort) -> String
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
@JvmName("greaterThanUByte")
public fun ValidationNode<UByte>.greaterThan(
    other: UByte?,
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
@JvmName("greaterThanUByte")
public fun ValidationNode<UByte>.greaterThan(
    other: UByte?,
    message: suspend UByte.(UByte) -> String
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
public fun ValidationNode<UInt>.lessThan(
    other: UInt?,
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
public fun ValidationNode<UInt>.lessThan(
    other: UInt?,
    message: suspend UInt.(UInt) -> String
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
@JvmName("lessThanULong")
public fun ValidationNode<ULong>.lessThan(
    other: ULong?,
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
@JvmName("lessThanULong")
public fun ValidationNode<ULong>.lessThan(
    other: ULong?,
    message: suspend ULong.(ULong) -> String
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
@JvmName("lessThanUShort")
public fun ValidationNode<UShort>.lessThan(
    other: UShort?,
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
@JvmName("lessThanUShort")
public fun ValidationNode<UShort>.lessThan(
    other: UShort?,
    message: suspend UShort.(UShort) -> String
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
@JvmName("lessThanUByte")
public fun ValidationNode<UByte>.lessThan(
    other: UByte?,
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
@JvmName("lessThanUByte")
public fun ValidationNode<UByte>.lessThan(
    other: UByte?,
    message: suspend UByte.(UByte) -> String
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
public fun ValidationNode<UInt>.between(
    min: UInt?,
    max: UInt?,
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
public fun ValidationNode<UInt>.between(
    min: UInt?,
    max: UInt?,
    message: suspend UInt.(UInt, UInt) -> String
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
@JvmName("betweenULong")
public fun ValidationNode<ULong>.between(
    min: ULong?,
    max: ULong?,
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
@JvmName("betweenULong")
public fun ValidationNode<ULong>.between(
    min: ULong?,
    max: ULong?,
    message: suspend ULong.(ULong, ULong) -> String
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
@JvmName("betweenUShort")
public fun ValidationNode<UShort>.between(
    min: UShort?,
    max: UShort?,
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
@JvmName("betweenUShort")
public fun ValidationNode<UShort>.between(
    min: UShort?,
    max: UShort?,
    message: suspend UShort.(UShort, UShort) -> String
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
@JvmName("betweenUByte")
public fun ValidationNode<UByte>.between(
    min: UByte?,
    max: UByte?,
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
@JvmName("betweenUByte")
public fun ValidationNode<UByte>.between(
    min: UByte?,
    max: UByte?,
    message: suspend UByte.(UByte, UByte) -> String
): Unit = constraint(
    first = min,
    second = max,
    message = message,
    code = "between",
    predicate = { value, first, second -> value in first..second }
)
