package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.Messages
import io.github.soleworks.validity.ValidationNode
import java.math.BigDecimal
import java.math.BigInteger

public fun ValidationNode<Int>.min(
    min: Int?,
    message: String = Messages.MIN
): Unit = min(
    min = min,
    message = { message.replace("{min}", "$min") }
)

public fun ValidationNode<Int>.min(
    min: Int?,
    message: Int.(Int?) -> String
): Unit = constraint(
    message = { it.message(min) },
    predicate = { min == null || it >= min }
)

public fun ValidationNode<Long>.min(
    min: Long?,
    message: String = Messages.MIN
): Unit = min(
    min = min,
    message = { message.replace("{min}", "$min") }
)

public fun ValidationNode<Long>.min(
    min: Long?,
    message: Long.(Long?) -> String
): Unit = constraint(
    message = { it.message(min) },
    predicate = { min == null || it >= min }
)

public fun ValidationNode<Short>.min(
    min: Short?,
    message: String = Messages.MIN
): Unit = min(
    min = min,
    message = { message.replace("{min}", "$min") }
)

public fun ValidationNode<Short>.min(
    min: Short?,
    message: Short.(Short?) -> String
): Unit = constraint(
    message = { it.message(min) },
    predicate = { min == null || it >= min }
)

public fun ValidationNode<Byte>.min(
    min: Byte?,
    message: String = Messages.MIN
): Unit = min(
    min = min,
    message = { message.replace("{min}", "$min") }
)

public fun ValidationNode<Byte>.min(
    min: Byte?,
    message: Byte.(Byte?) -> String
): Unit = constraint(
    message = { it.message(min) },
    predicate = { min == null || it >= min }
)

public fun ValidationNode<Double>.min(
    min: Double?,
    message: String = Messages.MIN
): Unit = min(
    min = min,
    message = { message.replace("{min}", "$min") }
)

public fun ValidationNode<Double>.min(
    min: Double?,
    message: Double.(Double?) -> String
): Unit = constraint(
    message = { it.message(min) },
    predicate = { min == null || it >= min }
)

public fun ValidationNode<Float>.min(
    min: Float?,
    message: String = Messages.MIN
): Unit = min(
    min = min,
    message = { message.replace("{min}", "$min") }
)

public fun ValidationNode<Float>.min(
    min: Float?,
    message: Float.(Float?) -> String
): Unit = constraint(
    message = { it.message(min) },
    predicate = { min == null || it >= min }
)

public fun ValidationNode<BigInteger>.min(
    min: BigInteger?,
    message: String = Messages.MIN
): Unit = min(
    min = min,
    message = { message.replace("{min}", "$min") }
)

public fun ValidationNode<BigInteger>.min(
    min: BigInteger?,
    message: BigInteger.(BigInteger?) -> String
): Unit = constraint(
    message = { it.message(min) },
    predicate = { min == null || it >= min }
)

public fun ValidationNode<BigDecimal>.min(
    min: BigDecimal?,
    message: String = Messages.MIN
): Unit = min(
    min = min,
    message = { message.replace("{min}", "$min") }
)

public fun ValidationNode<BigDecimal>.min(
    min: BigDecimal?,
    message: BigDecimal.(BigDecimal?) -> String
): Unit = constraint(
    message = { it.message(min) },
    predicate = { min == null || it >= min }
)

public fun ValidationNode<Int>.max(
    max: Int?,
    message: String = Messages.MAX
): Unit = max(
    max = max,
    message = { message.replace("{max}", "$max") }
)

public fun ValidationNode<Int>.max(
    max: Int?,
    message: Int.(Int?) -> String
): Unit = constraint(
    message = { it.message(max) },
    predicate = { max == null || it <= max }
)

public fun ValidationNode<Long>.max(
    max: Long?,
    message: String = Messages.MAX
): Unit = max(
    max = max,
    message = { message.replace("{max}", "$max") }
)

public fun ValidationNode<Long>.max(
    max: Long?,
    message: Long.(Long?) -> String
): Unit = constraint(
    message = { it.message(max) },
    predicate = { max == null || it <= max }
)

public fun ValidationNode<Short>.max(
    max: Short?,
    message: String = Messages.MAX
): Unit = max(
    max = max,
    message = { message.replace("{max}", "$max") }
)

public fun ValidationNode<Short>.max(
    max: Short?,
    message: Short.(Short?) -> String
): Unit = constraint(
    message = { it.message(max) },
    predicate = { max == null || it <= max }
)

public fun ValidationNode<Byte>.max(
    max: Byte?,
    message: String = Messages.MAX
): Unit = max(
    max = max,
    message = { message.replace("{max}", "$max") }
)

public fun ValidationNode<Byte>.max(
    max: Byte?,
    message: Byte.(Byte?) -> String
): Unit = constraint(
    message = { it.message(max) },
    predicate = { max == null || it <= max }
)

public fun ValidationNode<Double>.max(
    max: Double?,
    message: String = Messages.MAX
): Unit = max(
    max = max,
    message = { message.replace("{max}", "$max") }
)

public fun ValidationNode<Double>.max(
    max: Double?,
    message: Double.(Double?) -> String
): Unit = constraint(
    message = { it.message(max) },
    predicate = { max == null || it <= max }
)

public fun ValidationNode<Float>.max(
    max: Float?,
    message: String = Messages.MAX
): Unit = max(
    max = max,
    message = { message.replace("{max}", "$max") }
)

public fun ValidationNode<Float>.max(
    max: Float?,
    message: Float.(Float?) -> String
): Unit = constraint(
    message = { it.message(max) },
    predicate = { max == null || it <= max }
)

public fun ValidationNode<BigInteger>.max(
    max: BigInteger?,
    message: String = Messages.MAX
): Unit = max(
    max = max,
    message = { message.replace("{max}", "$max") }
)

public fun ValidationNode<BigInteger>.max(
    max: BigInteger?,
    message: BigInteger.(BigInteger?) -> String
): Unit = constraint(
    message = { it.message(max) },
    predicate = { max == null || it <= max }
)

public fun ValidationNode<BigDecimal>.max(
    max: BigDecimal?,
    message: String = Messages.MAX
): Unit = max(
    max = max,
    message = { message.replace("{max}", "$max") }
)

public fun ValidationNode<BigDecimal>.max(
    max: BigDecimal?,
    message: BigDecimal.(BigDecimal?) -> String
): Unit = constraint(
    message = { it.message(max) },
    predicate = { max == null || it <= max }
)

public fun ValidationNode<Int>.greaterThan(
    other: Int?,
    message: String = Messages.GREATER_THAN
): Unit = greaterThan(
    other = other,
    message = { message.replace("{other}", "$other") }
)

public fun ValidationNode<Int>.greaterThan(
    other: Int?,
    message: Int.(Int?) -> String
): Unit = constraint(
    message = { it.message(other) },
    predicate = { other == null || it > other }
)

public fun ValidationNode<Long>.greaterThan(
    other: Long?,
    message: String = Messages.GREATER_THAN
): Unit = greaterThan(
    other = other,
    message = { message.replace("{other}", "$other") }
)

public fun ValidationNode<Long>.greaterThan(
    other: Long?,
    message: Long.(Long?) -> String
): Unit = constraint(
    message = { it.message(other) },
    predicate = { other == null || it > other }
)

public fun ValidationNode<Short>.greaterThan(
    other: Short?,
    message: String = Messages.GREATER_THAN
): Unit = greaterThan(
    other = other,
    message = { message.replace("{other}", "$other") }
)

public fun ValidationNode<Short>.greaterThan(
    other: Short?,
    message: Short.(Short?) -> String
): Unit = constraint(
    message = { it.message(other) },
    predicate = { other == null || it > other }
)

public fun ValidationNode<Byte>.greaterThan(
    other: Byte?,
    message: String = Messages.GREATER_THAN
): Unit = greaterThan(
    other = other,
    message = { message.replace("{other}", "$other") }
)

public fun ValidationNode<Byte>.greaterThan(
    other: Byte?,
    message: Byte.(Byte?) -> String
): Unit = constraint(
    message = { it.message(other) },
    predicate = { other == null || it > other }
)

public fun ValidationNode<Double>.greaterThan(
    other: Double?,
    message: String = Messages.GREATER_THAN
): Unit = greaterThan(
    other = other,
    message = { message.replace("{other}", "$other") }
)

public fun ValidationNode<Double>.greaterThan(
    other: Double?,
    message: Double.(Double?) -> String
): Unit = constraint(
    message = { it.message(other) },
    predicate = { other == null || it > other }
)

public fun ValidationNode<Float>.greaterThan(
    other: Float?,
    message: String = Messages.GREATER_THAN
): Unit = greaterThan(
    other = other,
    message = { message.replace("{other}", "$other") }
)

public fun ValidationNode<Float>.greaterThan(
    other: Float?,
    message: Float.(Float?) -> String
): Unit = constraint(
    message = { it.message(other) },
    predicate = { other == null || it > other }
)

public fun ValidationNode<BigInteger>.greaterThan(
    other: BigInteger?,
    message: String = Messages.GREATER_THAN
): Unit = greaterThan(
    other = other,
    message = { message.replace("{other}", "$other") }
)

public fun ValidationNode<BigInteger>.greaterThan(
    other: BigInteger?,
    message: BigInteger.(BigInteger?) -> String
): Unit = constraint(
    message = { it.message(other) },
    predicate = { other == null || it > other }
)

public fun ValidationNode<BigDecimal>.greaterThan(
    other: BigDecimal?,
    message: String = Messages.GREATER_THAN
): Unit = greaterThan(
    other = other,
    message = { message.replace("{other}", "$other") }
)

public fun ValidationNode<BigDecimal>.greaterThan(
    other: BigDecimal?,
    message: BigDecimal.(BigDecimal?) -> String
): Unit = constraint(
    message = { it.message(other) },
    predicate = { other == null || it > other }
)

public fun ValidationNode<Int>.lessThan(
    other: Int?,
    message: String = Messages.LESS_THAN
): Unit = lessThan(
    other = other,
    message = { message.replace("{other}", "$other") }
)

public fun ValidationNode<Int>.lessThan(
    other: Int?,
    message: Int.(Int?) -> String
): Unit = constraint(
    message = { it.message(other) },
    predicate = { other == null || it < other }
)

public fun ValidationNode<Long>.lessThan(
    other: Long?,
    message: String = Messages.LESS_THAN
): Unit = lessThan(
    other = other,
    message = { message.replace("{other}", "$other") }
)

public fun ValidationNode<Long>.lessThan(
    other: Long?,
    message: Long.(Long?) -> String
): Unit = constraint(
    message = { it.message(other) },
    predicate = { other == null || it < other }
)

public fun ValidationNode<Short>.lessThan(
    other: Short?,
    message: String = Messages.LESS_THAN
): Unit = lessThan(
    other = other,
    message = { message.replace("{other}", "$other") }
)

public fun ValidationNode<Short>.lessThan(
    other: Short?,
    message: Short.(Short?) -> String
): Unit = constraint(
    message = { it.message(other) },
    predicate = { other == null || it < other }
)

public fun ValidationNode<Byte>.lessThan(
    other: Byte?,
    message: String = Messages.LESS_THAN
): Unit = lessThan(
    other = other,
    message = { message.replace("{other}", "$other") }
)

public fun ValidationNode<Byte>.lessThan(
    other: Byte?,
    message: Byte.(Byte?) -> String
): Unit = constraint(
    message = { it.message(other) },
    predicate = { other == null || it < other }
)

public fun ValidationNode<Double>.lessThan(
    other: Double?,
    message: String = Messages.LESS_THAN
): Unit = lessThan(
    other = other,
    message = { message.replace("{other}", "$other") }
)

public fun ValidationNode<Double>.lessThan(
    other: Double?,
    message: Double.(Double?) -> String
): Unit = constraint(
    message = { it.message(other) },
    predicate = { other == null || it < other }
)

public fun ValidationNode<Float>.lessThan(
    other: Float?,
    message: String = Messages.LESS_THAN
): Unit = lessThan(
    other = other,
    message = { message.replace("{other}", "$other") }
)

public fun ValidationNode<Float>.lessThan(
    other: Float?,
    message: Float.(Float?) -> String
): Unit = constraint(
    message = { it.message(other) },
    predicate = { other == null || it < other }
)

public fun ValidationNode<BigInteger>.lessThan(
    other: BigInteger?,
    message: String = Messages.LESS_THAN
): Unit = lessThan(
    other = other,
    message = { message.replace("{other}", "$other") }
)

public fun ValidationNode<BigInteger>.lessThan(
    other: BigInteger?,
    message: BigInteger.(BigInteger?) -> String
): Unit = constraint(
    message = { it.message(other) },
    predicate = { other == null || it < other }
)

public fun ValidationNode<BigDecimal>.lessThan(
    other: BigDecimal?,
    message: String = Messages.LESS_THAN
): Unit = lessThan(
    other = other,
    message = { message.replace("{other}", "$other") }
)

public fun ValidationNode<BigDecimal>.lessThan(
    other: BigDecimal?,
    message: BigDecimal.(BigDecimal?) -> String
): Unit = constraint(
    message = { it.message(other) },
    predicate = { other == null || it < other }
)

public fun ValidationNode<Int>.between(
    min: Int?,
    max: Int?,
    message: String = Messages.BETWEEN
): Unit = between(
    min = min,
    max = max,
    message = { _, _ -> message.replace("{min}", "$min").replace("{max}", "$max") }
)

public fun ValidationNode<Int>.between(
    min: Int?,
    max: Int?,
    message: Int.(Int?, Int?) -> String
): Unit = constraint(
    message = { it.message(min, max) },
    predicate = { min == null || max == null || it in min..max }
)

public fun ValidationNode<Long>.between(
    min: Long?,
    max: Long?,
    message: String = Messages.BETWEEN
): Unit = between(
    min = min,
    max = max,
    message = { _, _ -> message.replace("{min}", "$min").replace("{max}", "$max") }
)

public fun ValidationNode<Long>.between(
    min: Long?,
    max: Long?,
    message: Long.(Long?, Long?) -> String
): Unit = constraint(
    message = { it.message(min, max) },
    predicate = { min == null || max == null || it in min..max }
)

public fun ValidationNode<Short>.between(
    min: Short?,
    max: Short?,
    message: String = Messages.BETWEEN
): Unit = between(
    min = min,
    max = max,
    message = { _, _ -> message.replace("{min}", "$min").replace("{max}", "$max") }
)

public fun ValidationNode<Short>.between(
    min: Short?,
    max: Short?,
    message: Short.(Short?, Short?) -> String
): Unit = constraint(
    message = { it.message(min, max) },
    predicate = { min == null || max == null || it in min..max }
)

public fun ValidationNode<Byte>.between(
    min: Byte?,
    max: Byte?,
    message: String = Messages.BETWEEN
): Unit = between(
    min = min,
    max = max,
    message = { _, _ -> message.replace("{min}", "$min").replace("{max}", "$max") }
)

public fun ValidationNode<Byte>.between(
    min: Byte?,
    max: Byte?,
    message: Byte.(Byte?, Byte?) -> String
): Unit = constraint(
    message = { it.message(min, max) },
    predicate = { min == null || max == null || it in min..max }
)

public fun ValidationNode<Double>.between(
    min: Double?,
    max: Double?,
    message: String = Messages.BETWEEN
): Unit = between(
    min = min,
    max = max,
    message = { _, _ -> message.replace("{min}", "$min").replace("{max}", "$max") }
)

public fun ValidationNode<Double>.between(
    min: Double?,
    max: Double?,
    message: Double.(Double?, Double?) -> String
): Unit = constraint(
    message = { it.message(min, max) },
    predicate = { min == null || max == null || it in min..max }
)

public fun ValidationNode<Float>.between(
    min: Float?,
    max: Float?,
    message: String = Messages.BETWEEN
): Unit = between(
    min = min,
    max = max,
    message = { _, _ -> message.replace("{min}", "$min").replace("{max}", "$max") }
)

public fun ValidationNode<Float>.between(
    min: Float?,
    max: Float?,
    message: Float.(Float?, Float?) -> String
): Unit = constraint(
    message = { it.message(min, max) },
    predicate = { min == null || max == null || it in min..max }
)

public fun ValidationNode<BigInteger>.between(
    min: BigInteger?,
    max: BigInteger?,
    message: String = Messages.BETWEEN
): Unit = between(
    min = min,
    max = max,
    message = { _, _ -> message.replace("{min}", "$min").replace("{max}", "$max") }
)

public fun ValidationNode<BigInteger>.between(
    min: BigInteger?,
    max: BigInteger?,
    message: BigInteger.(BigInteger?, BigInteger?) -> String
): Unit = constraint(
    message = { it.message(min, max) },
    predicate = { min == null || max == null || it in min..max }
)

public fun ValidationNode<BigDecimal>.between(
    min: BigDecimal?,
    max: BigDecimal?,
    message: String = Messages.BETWEEN
): Unit = between(
    min = min,
    max = max,
    message = { _, _ -> message.replace("{min}", "$min").replace("{max}", "$max") }
)

public fun ValidationNode<BigDecimal>.between(
    min: BigDecimal?,
    max: BigDecimal?,
    message: BigDecimal.(BigDecimal?, BigDecimal?) -> String
): Unit = constraint(
    message = { it.message(min, max) },
    predicate = { min == null || max == null || it in min..max }
)

public fun ValidationNode<Int>.positive(
    message: String = Messages.POSITIVE
): Unit = constraint(
    message = message,
    predicate = { it > 0 }
)

@JvmName("positiveLong")
public fun ValidationNode<Long>.positive(
    message: String = Messages.POSITIVE
): Unit = constraint(
    message = message,
    predicate = { it > 0 }
)

@JvmName("positiveShort")
public fun ValidationNode<Short>.positive(
    message: String = Messages.POSITIVE
): Unit = constraint(
    message = message,
    predicate = { it > 0 }
)

@JvmName("positiveByte")
public fun ValidationNode<Byte>.positive(
    message: String = Messages.POSITIVE
): Unit = constraint(
    message = message,
    predicate = { it > 0 }
)

@JvmName("positiveDouble")
public fun ValidationNode<Double>.positive(
    message: String = Messages.POSITIVE
): Unit = constraint(
    message = message,
    predicate = { it > 0.0 }
)

@JvmName("positiveFloat")
public fun ValidationNode<Float>.positive(
    message: String = Messages.POSITIVE
): Unit = constraint(
    message = message,
    predicate = { it > 0f }
)

@JvmName("positiveBigInteger")
public fun ValidationNode<BigInteger>.positive(
    message: String = Messages.POSITIVE
): Unit = constraint(
    message = message,
    predicate = { it.signum() > 0 }
)

@JvmName("positiveBigDecimal")
public fun ValidationNode<BigDecimal>.positive(
    message: String = Messages.POSITIVE
): Unit = constraint(
    message = message,
    predicate = { it.signum() > 0 }
)

public fun ValidationNode<Int>.negative(
    message: String = Messages.NEGATIVE
): Unit = constraint(
    message = message,
    predicate = { it < 0 }
)

@JvmName("negativeLong")
public fun ValidationNode<Long>.negative(
    message: String = Messages.NEGATIVE
): Unit = constraint(
    message = message,
    predicate = { it < 0 }
)

@JvmName("negativeShort")
public fun ValidationNode<Short>.negative(
    message: String = Messages.NEGATIVE
): Unit = constraint(
    message = message,
    predicate = { it < 0 }
)

@JvmName("negativeByte")
public fun ValidationNode<Byte>.negative(
    message: String = Messages.NEGATIVE
): Unit = constraint(
    message = message,
    predicate = { it < 0 }
)

@JvmName("negativeDouble")
public fun ValidationNode<Double>.negative(
    message: String = Messages.NEGATIVE
): Unit = constraint(
    message = message,
    predicate = { it < 0.0 }
)

@JvmName("negativeFloat")
public fun ValidationNode<Float>.negative(
    message: String = Messages.NEGATIVE
): Unit = constraint(
    message = message,
    predicate = { it < 0f }
)

@JvmName("negativeBigInteger")
public fun ValidationNode<BigInteger>.negative(
    message: String = Messages.NEGATIVE
): Unit = constraint(
    message = message,
    predicate = { it.signum() < 0 }
)

@JvmName("negativeBigDecimal")
public fun ValidationNode<BigDecimal>.negative(
    message: String = Messages.NEGATIVE
): Unit = constraint(
    message = message,
    predicate = { it.signum() < 0 }
)

public fun ValidationNode<Int>.positiveOrZero(
    message: String = Messages.POSITIVE_OR_ZERO
): Unit = constraint(
    message = message,
    predicate = { it >= 0 }
)

@JvmName("positiveOrZeroLong")
public fun ValidationNode<Long>.positiveOrZero(
    message: String = Messages.POSITIVE_OR_ZERO
): Unit = constraint(
    message = message,
    predicate = { it >= 0 }
)

@JvmName("positiveOrZeroShort")
public fun ValidationNode<Short>.positiveOrZero(
    message: String = Messages.POSITIVE_OR_ZERO
): Unit = constraint(
    message = message,
    predicate = { it >= 0 }
)

@JvmName("positiveOrZeroByte")
public fun ValidationNode<Byte>.positiveOrZero(
    message: String = Messages.POSITIVE_OR_ZERO
): Unit = constraint(
    message = message,
    predicate = { it >= 0 }
)

@JvmName("positiveOrZeroDouble")
public fun ValidationNode<Double>.positiveOrZero(
    message: String = Messages.POSITIVE_OR_ZERO
): Unit = constraint(
    message = message,
    predicate = { it >= 0.0 }
)

@JvmName("positiveOrZeroFloat")
public fun ValidationNode<Float>.positiveOrZero(
    message: String = Messages.POSITIVE_OR_ZERO
): Unit = constraint(
    message = message,
    predicate = { it >= 0f }
)

@JvmName("positiveOrZeroBigInteger")
public fun ValidationNode<BigInteger>.positiveOrZero(
    message: String = Messages.POSITIVE_OR_ZERO
): Unit = constraint(
    message = message,
    predicate = { it.signum() >= 0 }
)

@JvmName("positiveOrZeroBigDecimal")
public fun ValidationNode<BigDecimal>.positiveOrZero(
    message: String = Messages.POSITIVE_OR_ZERO
): Unit = constraint(
    message = message,
    predicate = { it.signum() >= 0 }
)

public fun ValidationNode<Int>.negativeOrZero(
    message: String = Messages.NEGATIVE_OR_ZERO
): Unit = constraint(
    message = message,
    predicate = { it <= 0 }
)

@JvmName("negativeOrZeroLong")
public fun ValidationNode<Long>.negativeOrZero(
    message: String = Messages.NEGATIVE_OR_ZERO
): Unit = constraint(
    message = message,
    predicate = { it <= 0 }
)

@JvmName("negativeOrZeroShort")
public fun ValidationNode<Short>.negativeOrZero(
    message: String = Messages.NEGATIVE_OR_ZERO
): Unit = constraint(
    message = message,
    predicate = { it <= 0 }
)

@JvmName("negativeOrZeroByte")
public fun ValidationNode<Byte>.negativeOrZero(
    message: String = Messages.NEGATIVE_OR_ZERO
): Unit = constraint(
    message = message,
    predicate = { it <= 0 }
)

@JvmName("negativeOrZeroDouble")
public fun ValidationNode<Double>.negativeOrZero(
    message: String = Messages.NEGATIVE_OR_ZERO
): Unit = constraint(
    message = message,
    predicate = { it <= 0.0 }
)

@JvmName("negativeOrZeroFloat")
public fun ValidationNode<Float>.negativeOrZero(
    message: String = Messages.NEGATIVE_OR_ZERO
): Unit = constraint(
    message = message,
    predicate = { it <= 0f }
)

@JvmName("negativeOrZeroBigInteger")
public fun ValidationNode<BigInteger>.negativeOrZero(
    message: String = Messages.NEGATIVE_OR_ZERO
): Unit = constraint(
    message = message,
    predicate = { it.signum() <= 0 }
)

@JvmName("negativeOrZeroBigDecimal")
public fun ValidationNode<BigDecimal>.negativeOrZero(
    message: String = Messages.NEGATIVE_OR_ZERO
): Unit = constraint(
    message = message,
    predicate = { it.signum() <= 0 }
)

public fun ValidationNode<Int>.multipleOf(
    factor: Int,
    message: String = Messages.MULTIPLE_OF
): Unit = multipleOf(
    factor = factor,
    message = { message.replace("{factor}", "$factor") }
)

public fun ValidationNode<Int>.multipleOf(
    factor: Int,
    message: Int.(Int) -> String
): Unit = constraint(
    message = { it.message(factor) },
    predicate = { it % factor == 0 }
)

public fun ValidationNode<Long>.multipleOf(
    factor: Long,
    message: String = Messages.MULTIPLE_OF
): Unit = multipleOf(
    factor = factor,
    message = { message.replace("{factor}", "$factor") }
)

public fun ValidationNode<Long>.multipleOf(
    factor: Long,
    message: Long.(Long) -> String
): Unit = constraint(
    message = { it.message(factor) },
    predicate = { it % factor == 0L }
)

public fun ValidationNode<Short>.multipleOf(
    factor: Short,
    message: String = Messages.MULTIPLE_OF
): Unit = multipleOf(
    factor = factor,
    message = { message.replace("{factor}", "$factor") }
)

public fun ValidationNode<Short>.multipleOf(
    factor: Short,
    message: Short.(Short) -> String
): Unit = constraint(
    message = { it.message(factor) },
    predicate = { it % factor == 0 }
)

public fun ValidationNode<Byte>.multipleOf(
    factor: Byte,
    message: String = Messages.MULTIPLE_OF
): Unit = multipleOf(
    factor = factor,
    message = { message.replace("{factor}", "$factor") }
)

public fun ValidationNode<Byte>.multipleOf(
    factor: Byte,
    message: Byte.(Byte) -> String
): Unit = constraint(
    message = { it.message(factor) },
    predicate = { it % factor == 0 }
)

public fun ValidationNode<BigInteger>.multipleOf(
    factor: BigInteger,
    message: String = Messages.MULTIPLE_OF
): Unit = multipleOf(
    factor = factor,
    message = { message.replace("{factor}", "$factor") }
)

public fun ValidationNode<BigInteger>.multipleOf(
    factor: BigInteger,
    message: BigInteger.(BigInteger) -> String
): Unit = constraint(
    message = { it.message(factor) },
    predicate = { it.remainder(factor).signum() == 0 }
)

public fun ValidationNode<BigDecimal>.multipleOf(
    factor: BigDecimal,
    message: String = Messages.MULTIPLE_OF
): Unit = multipleOf(
    factor = factor,
    message = { message.replace("{factor}", "$factor") }
)

public fun ValidationNode<BigDecimal>.multipleOf(
    factor: BigDecimal,
    message: BigDecimal.(BigDecimal) -> String
): Unit = constraint(
    message = { it.message(factor) },
    predicate = { it.remainder(factor).signum() == 0 }
)

public fun ValidationNode<Double>.finite(
    message: String = Messages.FINITE
): Unit = constraint(
    message = message,
    predicate = { it.isFinite() }
)

@JvmName("finiteFloat")
public fun ValidationNode<Float>.finite(
    message: String = Messages.FINITE
): Unit = constraint(
    message = message,
    predicate = { it.isFinite() }
)

public fun ValidationNode<BigDecimal>.maxDecimalPlaces(
    max: Int,
    message: String = Messages.MAX_DECIMAL_PLACES
): Unit = maxDecimalPlaces(
    max = max,
    message = { message.replace("{max}", "$max") }
)

public fun ValidationNode<BigDecimal>.maxDecimalPlaces(
    max: Int,
    message: BigDecimal.(Int) -> String
): Unit = constraint(
    message = { it.message(max) },
    predicate = { it.stripTrailingZeros().scale() <= max }
)

public fun ValidationNode<BigDecimal>.maxIntegerDigits(
    max: Int,
    message: String = Messages.MAX_INTEGER_DIGITS
): Unit = maxIntegerDigits(
    max = max,
    message = { message.replace("{max}", "$max") }
)

public fun ValidationNode<BigDecimal>.maxIntegerDigits(
    max: Int,
    message: BigDecimal.(Int) -> String
): Unit = constraint(
    message = { it.message(max) },
    predicate = { it.stripTrailingZeros().run { precision() - scale() } <= max }
)
