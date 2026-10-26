package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.filled
import io.github.soleworks.validity.messages

public fun ValidationNode<UInt>.min(
    min: UInt?,
    message: String = messages.min
): Unit = min(
    min = min,
    message = { message.filled("min" to min) }
)

public fun ValidationNode<UInt>.min(
    min: UInt?,
    message: suspend UInt.(UInt) -> String
): Unit = constraint(
    argument = min,
    message = message,
    code = "min",
    predicate = { value, argument -> value >= argument }
)

@JvmName("minULong")
public fun ValidationNode<ULong>.min(
    min: ULong?,
    message: String = messages.min
): Unit = min(
    min = min,
    message = { message.filled("min" to min) }
)

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

@JvmName("minUShort")
public fun ValidationNode<UShort>.min(
    min: UShort?,
    message: String = messages.min
): Unit = min(
    min = min,
    message = { message.filled("min" to min) }
)

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

@JvmName("minUByte")
public fun ValidationNode<UByte>.min(
    min: UByte?,
    message: String = messages.min
): Unit = min(
    min = min,
    message = { message.filled("min" to min) }
)

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

public fun ValidationNode<UInt>.max(
    max: UInt?,
    message: String = messages.max
): Unit = max(
    max = max,
    message = { message.filled("max" to max) }
)

public fun ValidationNode<UInt>.max(
    max: UInt?,
    message: suspend UInt.(UInt) -> String
): Unit = constraint(
    argument = max,
    message = message,
    code = "max",
    predicate = { value, argument -> value <= argument }
)

@JvmName("maxULong")
public fun ValidationNode<ULong>.max(
    max: ULong?,
    message: String = messages.max
): Unit = max(
    max = max,
    message = { message.filled("max" to max) }
)

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

@JvmName("maxUShort")
public fun ValidationNode<UShort>.max(
    max: UShort?,
    message: String = messages.max
): Unit = max(
    max = max,
    message = { message.filled("max" to max) }
)

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

@JvmName("maxUByte")
public fun ValidationNode<UByte>.max(
    max: UByte?,
    message: String = messages.max
): Unit = max(
    max = max,
    message = { message.filled("max" to max) }
)

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

public fun ValidationNode<UInt>.greaterThan(
    other: UInt?,
    message: String = messages.greaterThan
): Unit = greaterThan(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<UInt>.greaterThan(
    other: UInt?,
    message: suspend UInt.(UInt) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "greaterThan",
    predicate = { value, argument -> value > argument }
)

@JvmName("greaterThanULong")
public fun ValidationNode<ULong>.greaterThan(
    other: ULong?,
    message: String = messages.greaterThan
): Unit = greaterThan(
    other = other,
    message = { message.filled("other" to other) }
)

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

@JvmName("greaterThanUShort")
public fun ValidationNode<UShort>.greaterThan(
    other: UShort?,
    message: String = messages.greaterThan
): Unit = greaterThan(
    other = other,
    message = { message.filled("other" to other) }
)

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

@JvmName("greaterThanUByte")
public fun ValidationNode<UByte>.greaterThan(
    other: UByte?,
    message: String = messages.greaterThan
): Unit = greaterThan(
    other = other,
    message = { message.filled("other" to other) }
)

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

public fun ValidationNode<UInt>.lessThan(
    other: UInt?,
    message: String = messages.lessThan
): Unit = lessThan(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<UInt>.lessThan(
    other: UInt?,
    message: suspend UInt.(UInt) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "lessThan",
    predicate = { value, argument -> value < argument }
)

@JvmName("lessThanULong")
public fun ValidationNode<ULong>.lessThan(
    other: ULong?,
    message: String = messages.lessThan
): Unit = lessThan(
    other = other,
    message = { message.filled("other" to other) }
)

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

@JvmName("lessThanUShort")
public fun ValidationNode<UShort>.lessThan(
    other: UShort?,
    message: String = messages.lessThan
): Unit = lessThan(
    other = other,
    message = { message.filled("other" to other) }
)

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

@JvmName("lessThanUByte")
public fun ValidationNode<UByte>.lessThan(
    other: UByte?,
    message: String = messages.lessThan
): Unit = lessThan(
    other = other,
    message = { message.filled("other" to other) }
)

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

public fun ValidationNode<UInt>.between(
    min: UInt?,
    max: UInt?,
    message: String = messages.between
): Unit = between(
    min = min,
    max = max,
    message = { _, _ -> message.filled("min" to min, "max" to max) }
)

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
