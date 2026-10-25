package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.filled
import io.github.soleworks.validity.messages
import java.math.BigDecimal
import java.math.BigInteger

public fun ValidationNode<Int>.min(
    min: Int?,
    message: String = messages.min
): Unit = min(
    min = min,
    message = { message.filled("min" to min) }
)

public fun ValidationNode<Int>.min(
    min: Int?,
    message: Int.(Int) -> String
) {
    if (min == null)
        return

    constraint(
        message = { it.message(min) },
        code = "min",
        predicate = { it >= min }
    )
}

public fun ValidationNode<Long>.min(
    min: Long?,
    message: String = messages.min
): Unit = min(
    min = min,
    message = { message.filled("min" to min) }
)

public fun ValidationNode<Long>.min(
    min: Long?,
    message: Long.(Long) -> String
) {
    if (min == null)
        return

    constraint(
        message = { it.message(min) },
        code = "min",
        predicate = { it >= min }
    )
}

public fun ValidationNode<Short>.min(
    min: Short?,
    message: String = messages.min
): Unit = min(
    min = min,
    message = { message.filled("min" to min) }
)

public fun ValidationNode<Short>.min(
    min: Short?,
    message: Short.(Short) -> String
) {
    if (min == null)
        return

    constraint(
        message = { it.message(min) },
        code = "min",
        predicate = { it >= min }
    )
}

public fun ValidationNode<Byte>.min(
    min: Byte?,
    message: String = messages.min
): Unit = min(
    min = min,
    message = { message.filled("min" to min) }
)

public fun ValidationNode<Byte>.min(
    min: Byte?,
    message: Byte.(Byte) -> String
) {
    if (min == null)
        return

    constraint(
        message = { it.message(min) },
        code = "min",
        predicate = { it >= min }
    )
}

public fun ValidationNode<Double>.min(
    min: Double?,
    message: String = messages.min
): Unit = min(
    min = min,
    message = { message.filled("min" to min) }
)

public fun ValidationNode<Double>.min(
    min: Double?,
    message: Double.(Double) -> String
) {
    if (min == null)
        return

    constraint(
        message = { it.message(min) },
        code = "min",
        predicate = { it >= min }
    )
}

public fun ValidationNode<Float>.min(
    min: Float?,
    message: String = messages.min
): Unit = min(
    min = min,
    message = { message.filled("min" to min) }
)

public fun ValidationNode<Float>.min(
    min: Float?,
    message: Float.(Float) -> String
) {
    if (min == null)
        return

    constraint(
        message = { it.message(min) },
        code = "min",
        predicate = { it >= min }
    )
}

public fun ValidationNode<BigInteger>.min(
    min: BigInteger?,
    message: String = messages.min
): Unit = min(
    min = min,
    message = { message.filled("min" to min) }
)

public fun ValidationNode<BigInteger>.min(
    min: BigInteger?,
    message: BigInteger.(BigInteger) -> String
) {
    if (min == null)
        return

    constraint(
        message = { it.message(min) },
        code = "min",
        predicate = { it >= min }
    )
}

public fun ValidationNode<BigDecimal>.min(
    min: BigDecimal?,
    message: String = messages.min
): Unit = min(
    min = min,
    message = { message.filled("min" to min) }
)

public fun ValidationNode<BigDecimal>.min(
    min: BigDecimal?,
    message: BigDecimal.(BigDecimal) -> String
) {
    if (min == null)
        return

    constraint(
        message = { it.message(min) },
        code = "min",
        predicate = { it >= min }
    )
}

public fun ValidationNode<Int>.max(
    max: Int?,
    message: String = messages.max
): Unit = max(
    max = max,
    message = { message.filled("max" to max) }
)

public fun ValidationNode<Int>.max(
    max: Int?,
    message: Int.(Int) -> String
) {
    if (max == null)
        return

    constraint(
        message = { it.message(max) },
        code = "max",
        predicate = { it <= max }
    )
}

public fun ValidationNode<Long>.max(
    max: Long?,
    message: String = messages.max
): Unit = max(
    max = max,
    message = { message.filled("max" to max) }
)

public fun ValidationNode<Long>.max(
    max: Long?,
    message: Long.(Long) -> String
) {
    if (max == null)
        return

    constraint(
        message = { it.message(max) },
        code = "max",
        predicate = { it <= max }
    )
}

public fun ValidationNode<Short>.max(
    max: Short?,
    message: String = messages.max
): Unit = max(
    max = max,
    message = { message.filled("max" to max) }
)

public fun ValidationNode<Short>.max(
    max: Short?,
    message: Short.(Short) -> String
) {
    if (max == null)
        return

    constraint(
        message = { it.message(max) },
        code = "max",
        predicate = { it <= max }
    )
}

public fun ValidationNode<Byte>.max(
    max: Byte?,
    message: String = messages.max
): Unit = max(
    max = max,
    message = { message.filled("max" to max) }
)

public fun ValidationNode<Byte>.max(
    max: Byte?,
    message: Byte.(Byte) -> String
) {
    if (max == null)
        return

    constraint(
        message = { it.message(max) },
        code = "max",
        predicate = { it <= max }
    )
}

public fun ValidationNode<Double>.max(
    max: Double?,
    message: String = messages.max
): Unit = max(
    max = max,
    message = { message.filled("max" to max) }
)

public fun ValidationNode<Double>.max(
    max: Double?,
    message: Double.(Double) -> String
) {
    if (max == null)
        return

    constraint(
        message = { it.message(max) },
        code = "max",
        predicate = { it <= max }
    )
}

public fun ValidationNode<Float>.max(
    max: Float?,
    message: String = messages.max
): Unit = max(
    max = max,
    message = { message.filled("max" to max) }
)

public fun ValidationNode<Float>.max(
    max: Float?,
    message: Float.(Float) -> String
) {
    if (max == null)
        return

    constraint(
        message = { it.message(max) },
        code = "max",
        predicate = { it <= max }
    )
}

public fun ValidationNode<BigInteger>.max(
    max: BigInteger?,
    message: String = messages.max
): Unit = max(
    max = max,
    message = { message.filled("max" to max) }
)

public fun ValidationNode<BigInteger>.max(
    max: BigInteger?,
    message: BigInteger.(BigInteger) -> String
) {
    if (max == null)
        return

    constraint(
        message = { it.message(max) },
        code = "max",
        predicate = { it <= max }
    )
}

public fun ValidationNode<BigDecimal>.max(
    max: BigDecimal?,
    message: String = messages.max
): Unit = max(
    max = max,
    message = { message.filled("max" to max) }
)

public fun ValidationNode<BigDecimal>.max(
    max: BigDecimal?,
    message: BigDecimal.(BigDecimal) -> String
) {
    if (max == null)
        return

    constraint(
        message = { it.message(max) },
        code = "max",
        predicate = { it <= max }
    )
}

public fun ValidationNode<Int>.greaterThan(
    other: Int?,
    message: String = messages.greaterThan
): Unit = greaterThan(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<Int>.greaterThan(
    other: Int?,
    message: Int.(Int) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "greaterThan",
        predicate = { it > other }
    )
}

public fun ValidationNode<Long>.greaterThan(
    other: Long?,
    message: String = messages.greaterThan
): Unit = greaterThan(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<Long>.greaterThan(
    other: Long?,
    message: Long.(Long) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "greaterThan",
        predicate = { it > other }
    )
}

public fun ValidationNode<Short>.greaterThan(
    other: Short?,
    message: String = messages.greaterThan
): Unit = greaterThan(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<Short>.greaterThan(
    other: Short?,
    message: Short.(Short) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "greaterThan",
        predicate = { it > other }
    )
}

public fun ValidationNode<Byte>.greaterThan(
    other: Byte?,
    message: String = messages.greaterThan
): Unit = greaterThan(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<Byte>.greaterThan(
    other: Byte?,
    message: Byte.(Byte) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "greaterThan",
        predicate = { it > other }
    )
}

public fun ValidationNode<Double>.greaterThan(
    other: Double?,
    message: String = messages.greaterThan
): Unit = greaterThan(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<Double>.greaterThan(
    other: Double?,
    message: Double.(Double) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "greaterThan",
        predicate = { it > other }
    )
}

public fun ValidationNode<Float>.greaterThan(
    other: Float?,
    message: String = messages.greaterThan
): Unit = greaterThan(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<Float>.greaterThan(
    other: Float?,
    message: Float.(Float) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "greaterThan",
        predicate = { it > other }
    )
}

public fun ValidationNode<BigInteger>.greaterThan(
    other: BigInteger?,
    message: String = messages.greaterThan
): Unit = greaterThan(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<BigInteger>.greaterThan(
    other: BigInteger?,
    message: BigInteger.(BigInteger) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "greaterThan",
        predicate = { it > other }
    )
}

public fun ValidationNode<BigDecimal>.greaterThan(
    other: BigDecimal?,
    message: String = messages.greaterThan
): Unit = greaterThan(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<BigDecimal>.greaterThan(
    other: BigDecimal?,
    message: BigDecimal.(BigDecimal) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "greaterThan",
        predicate = { it > other }
    )
}

public fun ValidationNode<Int>.lessThan(
    other: Int?,
    message: String = messages.lessThan
): Unit = lessThan(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<Int>.lessThan(
    other: Int?,
    message: Int.(Int) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "lessThan",
        predicate = { it < other }
    )
}

public fun ValidationNode<Long>.lessThan(
    other: Long?,
    message: String = messages.lessThan
): Unit = lessThan(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<Long>.lessThan(
    other: Long?,
    message: Long.(Long) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "lessThan",
        predicate = { it < other }
    )
}

public fun ValidationNode<Short>.lessThan(
    other: Short?,
    message: String = messages.lessThan
): Unit = lessThan(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<Short>.lessThan(
    other: Short?,
    message: Short.(Short) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "lessThan",
        predicate = { it < other }
    )
}

public fun ValidationNode<Byte>.lessThan(
    other: Byte?,
    message: String = messages.lessThan
): Unit = lessThan(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<Byte>.lessThan(
    other: Byte?,
    message: Byte.(Byte) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "lessThan",
        predicate = { it < other }
    )
}

public fun ValidationNode<Double>.lessThan(
    other: Double?,
    message: String = messages.lessThan
): Unit = lessThan(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<Double>.lessThan(
    other: Double?,
    message: Double.(Double) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "lessThan",
        predicate = { it < other }
    )
}

public fun ValidationNode<Float>.lessThan(
    other: Float?,
    message: String = messages.lessThan
): Unit = lessThan(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<Float>.lessThan(
    other: Float?,
    message: Float.(Float) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "lessThan",
        predicate = { it < other }
    )
}

public fun ValidationNode<BigInteger>.lessThan(
    other: BigInteger?,
    message: String = messages.lessThan
): Unit = lessThan(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<BigInteger>.lessThan(
    other: BigInteger?,
    message: BigInteger.(BigInteger) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "lessThan",
        predicate = { it < other }
    )
}

public fun ValidationNode<BigDecimal>.lessThan(
    other: BigDecimal?,
    message: String = messages.lessThan
): Unit = lessThan(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<BigDecimal>.lessThan(
    other: BigDecimal?,
    message: BigDecimal.(BigDecimal) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "lessThan",
        predicate = { it < other }
    )
}

public fun ValidationNode<Int>.between(
    min: Int?,
    max: Int?,
    message: String = messages.between
): Unit = between(
    min = min,
    max = max,
    message = { _, _ -> message.filled("min" to min, "max" to max) }
)

public fun ValidationNode<Int>.between(
    min: Int?,
    max: Int?,
    message: Int.(Int, Int) -> String
) {
    if (min == null || max == null)
        return

    constraint(
        message = { it.message(min, max) },
        code = "between",
        predicate = { it in min..max }
    )
}

public fun ValidationNode<Long>.between(
    min: Long?,
    max: Long?,
    message: String = messages.between
): Unit = between(
    min = min,
    max = max,
    message = { _, _ -> message.filled("min" to min, "max" to max) }
)

public fun ValidationNode<Long>.between(
    min: Long?,
    max: Long?,
    message: Long.(Long, Long) -> String
) {
    if (min == null || max == null)
        return

    constraint(
        message = { it.message(min, max) },
        code = "between",
        predicate = { it in min..max }
    )
}

public fun ValidationNode<Short>.between(
    min: Short?,
    max: Short?,
    message: String = messages.between
): Unit = between(
    min = min,
    max = max,
    message = { _, _ -> message.filled("min" to min, "max" to max) }
)

public fun ValidationNode<Short>.between(
    min: Short?,
    max: Short?,
    message: Short.(Short, Short) -> String
) {
    if (min == null || max == null)
        return

    constraint(
        message = { it.message(min, max) },
        code = "between",
        predicate = { it in min..max }
    )
}

public fun ValidationNode<Byte>.between(
    min: Byte?,
    max: Byte?,
    message: String = messages.between
): Unit = between(
    min = min,
    max = max,
    message = { _, _ -> message.filled("min" to min, "max" to max) }
)

public fun ValidationNode<Byte>.between(
    min: Byte?,
    max: Byte?,
    message: Byte.(Byte, Byte) -> String
) {
    if (min == null || max == null)
        return

    constraint(
        message = { it.message(min, max) },
        code = "between",
        predicate = { it in min..max }
    )
}

public fun ValidationNode<Double>.between(
    min: Double?,
    max: Double?,
    message: String = messages.between
): Unit = between(
    min = min,
    max = max,
    message = { _, _ -> message.filled("min" to min, "max" to max) }
)

public fun ValidationNode<Double>.between(
    min: Double?,
    max: Double?,
    message: Double.(Double, Double) -> String
) {
    if (min == null || max == null)
        return

    constraint(
        message = { it.message(min, max) },
        code = "between",
        predicate = { it in min..max }
    )
}

public fun ValidationNode<Float>.between(
    min: Float?,
    max: Float?,
    message: String = messages.between
): Unit = between(
    min = min,
    max = max,
    message = { _, _ -> message.filled("min" to min, "max" to max) }
)

public fun ValidationNode<Float>.between(
    min: Float?,
    max: Float?,
    message: Float.(Float, Float) -> String
) {
    if (min == null || max == null)
        return

    constraint(
        message = { it.message(min, max) },
        code = "between",
        predicate = { it in min..max }
    )
}

public fun ValidationNode<BigInteger>.between(
    min: BigInteger?,
    max: BigInteger?,
    message: String = messages.between
): Unit = between(
    min = min,
    max = max,
    message = { _, _ -> message.filled("min" to min, "max" to max) }
)

public fun ValidationNode<BigInteger>.between(
    min: BigInteger?,
    max: BigInteger?,
    message: BigInteger.(BigInteger, BigInteger) -> String
) {
    if (min == null || max == null)
        return

    constraint(
        message = { it.message(min, max) },
        code = "between",
        predicate = { it in min..max }
    )
}

public fun ValidationNode<BigDecimal>.between(
    min: BigDecimal?,
    max: BigDecimal?,
    message: String = messages.between
): Unit = between(
    min = min,
    max = max,
    message = { _, _ -> message.filled("min" to min, "max" to max) }
)

public fun ValidationNode<BigDecimal>.between(
    min: BigDecimal?,
    max: BigDecimal?,
    message: BigDecimal.(BigDecimal, BigDecimal) -> String
) {
    if (min == null || max == null)
        return

    constraint(
        message = { it.message(min, max) },
        code = "between",
        predicate = { it in min..max }
    )
}

public fun ValidationNode<Int>.positive(
    message: String = messages.positive
): Unit = constraint(
    message = message,
    code = "positive",
    predicate = { it > 0 }
)

@JvmName("positiveLong")
public fun ValidationNode<Long>.positive(
    message: String = messages.positive
): Unit = constraint(
    message = message,
    code = "positive",
    predicate = { it > 0 }
)

@JvmName("positiveShort")
public fun ValidationNode<Short>.positive(
    message: String = messages.positive
): Unit = constraint(
    message = message,
    code = "positive",
    predicate = { it > 0 }
)

@JvmName("positiveByte")
public fun ValidationNode<Byte>.positive(
    message: String = messages.positive
): Unit = constraint(
    message = message,
    code = "positive",
    predicate = { it > 0 }
)

@JvmName("positiveDouble")
public fun ValidationNode<Double>.positive(
    message: String = messages.positive
): Unit = constraint(
    message = message,
    code = "positive",
    predicate = { it > 0.0 }
)

@JvmName("positiveFloat")
public fun ValidationNode<Float>.positive(
    message: String = messages.positive
): Unit = constraint(
    message = message,
    code = "positive",
    predicate = { it > 0f }
)

@JvmName("positiveBigInteger")
public fun ValidationNode<BigInteger>.positive(
    message: String = messages.positive
): Unit = constraint(
    message = message,
    code = "positive",
    predicate = { it.signum() > 0 }
)

@JvmName("positiveBigDecimal")
public fun ValidationNode<BigDecimal>.positive(
    message: String = messages.positive
): Unit = constraint(
    message = message,
    code = "positive",
    predicate = { it.signum() > 0 }
)

public fun ValidationNode<Int>.negative(
    message: String = messages.negative
): Unit = constraint(
    message = message,
    code = "negative",
    predicate = { it < 0 }
)

@JvmName("negativeLong")
public fun ValidationNode<Long>.negative(
    message: String = messages.negative
): Unit = constraint(
    message = message,
    code = "negative",
    predicate = { it < 0 }
)

@JvmName("negativeShort")
public fun ValidationNode<Short>.negative(
    message: String = messages.negative
): Unit = constraint(
    message = message,
    code = "negative",
    predicate = { it < 0 }
)

@JvmName("negativeByte")
public fun ValidationNode<Byte>.negative(
    message: String = messages.negative
): Unit = constraint(
    message = message,
    code = "negative",
    predicate = { it < 0 }
)

@JvmName("negativeDouble")
public fun ValidationNode<Double>.negative(
    message: String = messages.negative
): Unit = constraint(
    message = message,
    code = "negative",
    predicate = { it < 0.0 }
)

@JvmName("negativeFloat")
public fun ValidationNode<Float>.negative(
    message: String = messages.negative
): Unit = constraint(
    message = message,
    code = "negative",
    predicate = { it < 0f }
)

@JvmName("negativeBigInteger")
public fun ValidationNode<BigInteger>.negative(
    message: String = messages.negative
): Unit = constraint(
    message = message,
    code = "negative",
    predicate = { it.signum() < 0 }
)

@JvmName("negativeBigDecimal")
public fun ValidationNode<BigDecimal>.negative(
    message: String = messages.negative
): Unit = constraint(
    message = message,
    code = "negative",
    predicate = { it.signum() < 0 }
)

public fun ValidationNode<Int>.positiveOrZero(
    message: String = messages.positiveOrZero
): Unit = constraint(
    message = message,
    code = "positiveOrZero",
    predicate = { it >= 0 }
)

@JvmName("positiveOrZeroLong")
public fun ValidationNode<Long>.positiveOrZero(
    message: String = messages.positiveOrZero
): Unit = constraint(
    message = message,
    code = "positiveOrZero",
    predicate = { it >= 0 }
)

@JvmName("positiveOrZeroShort")
public fun ValidationNode<Short>.positiveOrZero(
    message: String = messages.positiveOrZero
): Unit = constraint(
    message = message,
    code = "positiveOrZero",
    predicate = { it >= 0 }
)

@JvmName("positiveOrZeroByte")
public fun ValidationNode<Byte>.positiveOrZero(
    message: String = messages.positiveOrZero
): Unit = constraint(
    message = message,
    code = "positiveOrZero",
    predicate = { it >= 0 }
)

@JvmName("positiveOrZeroDouble")
public fun ValidationNode<Double>.positiveOrZero(
    message: String = messages.positiveOrZero
): Unit = constraint(
    message = message,
    code = "positiveOrZero",
    predicate = { it >= 0.0 }
)

@JvmName("positiveOrZeroFloat")
public fun ValidationNode<Float>.positiveOrZero(
    message: String = messages.positiveOrZero
): Unit = constraint(
    message = message,
    code = "positiveOrZero",
    predicate = { it >= 0f }
)

@JvmName("positiveOrZeroBigInteger")
public fun ValidationNode<BigInteger>.positiveOrZero(
    message: String = messages.positiveOrZero
): Unit = constraint(
    message = message,
    code = "positiveOrZero",
    predicate = { it.signum() >= 0 }
)

@JvmName("positiveOrZeroBigDecimal")
public fun ValidationNode<BigDecimal>.positiveOrZero(
    message: String = messages.positiveOrZero
): Unit = constraint(
    message = message,
    code = "positiveOrZero",
    predicate = { it.signum() >= 0 }
)

public fun ValidationNode<Int>.negativeOrZero(
    message: String = messages.negativeOrZero
): Unit = constraint(
    message = message,
    code = "negativeOrZero",
    predicate = { it <= 0 }
)

@JvmName("negativeOrZeroLong")
public fun ValidationNode<Long>.negativeOrZero(
    message: String = messages.negativeOrZero
): Unit = constraint(
    message = message,
    code = "negativeOrZero",
    predicate = { it <= 0 }
)

@JvmName("negativeOrZeroShort")
public fun ValidationNode<Short>.negativeOrZero(
    message: String = messages.negativeOrZero
): Unit = constraint(
    message = message,
    code = "negativeOrZero",
    predicate = { it <= 0 }
)

@JvmName("negativeOrZeroByte")
public fun ValidationNode<Byte>.negativeOrZero(
    message: String = messages.negativeOrZero
): Unit = constraint(
    message = message,
    code = "negativeOrZero",
    predicate = { it <= 0 }
)

@JvmName("negativeOrZeroDouble")
public fun ValidationNode<Double>.negativeOrZero(
    message: String = messages.negativeOrZero
): Unit = constraint(
    message = message,
    code = "negativeOrZero",
    predicate = { it <= 0.0 }
)

@JvmName("negativeOrZeroFloat")
public fun ValidationNode<Float>.negativeOrZero(
    message: String = messages.negativeOrZero
): Unit = constraint(
    message = message,
    code = "negativeOrZero",
    predicate = { it <= 0f }
)

@JvmName("negativeOrZeroBigInteger")
public fun ValidationNode<BigInteger>.negativeOrZero(
    message: String = messages.negativeOrZero
): Unit = constraint(
    message = message,
    code = "negativeOrZero",
    predicate = { it.signum() <= 0 }
)

@JvmName("negativeOrZeroBigDecimal")
public fun ValidationNode<BigDecimal>.negativeOrZero(
    message: String = messages.negativeOrZero
): Unit = constraint(
    message = message,
    code = "negativeOrZero",
    predicate = { it.signum() <= 0 }
)

public fun ValidationNode<Int>.multipleOf(
    factor: Int,
    message: String = messages.multipleOf
): Unit = multipleOf(
    factor = factor,
    message = { message.filled("factor" to factor) }
)

public fun ValidationNode<Int>.multipleOf(
    factor: Int,
    message: Int.(Int) -> String
) {
    require(factor != 0) { "multipleOf needs a factor other than 0" }

    constraint(
        message = { it.message(factor) },
        code = "multipleOf",
        predicate = { it % factor == 0 }
    )
}

public fun ValidationNode<Long>.multipleOf(
    factor: Long,
    message: String = messages.multipleOf
): Unit = multipleOf(
    factor = factor,
    message = { message.filled("factor" to factor) }
)

public fun ValidationNode<Long>.multipleOf(
    factor: Long,
    message: Long.(Long) -> String
) {
    require(factor != 0L) { "multipleOf needs a factor other than 0" }

    constraint(
        message = { it.message(factor) },
        code = "multipleOf",
        predicate = { it % factor == 0L }
    )
}

public fun ValidationNode<Short>.multipleOf(
    factor: Short,
    message: String = messages.multipleOf
): Unit = multipleOf(
    factor = factor,
    message = { message.filled("factor" to factor) }
)

public fun ValidationNode<Short>.multipleOf(
    factor: Short,
    message: Short.(Short) -> String
) {
    require(factor.toInt() != 0) { "multipleOf needs a factor other than 0" }

    constraint(
        message = { it.message(factor) },
        code = "multipleOf",
        predicate = { it % factor == 0 }
    )
}

public fun ValidationNode<Byte>.multipleOf(
    factor: Byte,
    message: String = messages.multipleOf
): Unit = multipleOf(
    factor = factor,
    message = { message.filled("factor" to factor) }
)

public fun ValidationNode<Byte>.multipleOf(
    factor: Byte,
    message: Byte.(Byte) -> String
) {
    require(factor.toInt() != 0) { "multipleOf needs a factor other than 0" }

    constraint(
        message = { it.message(factor) },
        code = "multipleOf",
        predicate = { it % factor == 0 }
    )
}

public fun ValidationNode<BigInteger>.multipleOf(
    factor: BigInteger,
    message: String = messages.multipleOf
): Unit = multipleOf(
    factor = factor,
    message = { message.filled("factor" to factor) }
)

public fun ValidationNode<BigInteger>.multipleOf(
    factor: BigInteger,
    message: BigInteger.(BigInteger) -> String
) {
    require(factor.signum() != 0) { "multipleOf needs a factor other than 0" }

    constraint(
        message = { it.message(factor) },
        code = "multipleOf",
        predicate = { it.remainder(factor).signum() == 0 }
    )
}

public fun ValidationNode<BigDecimal>.multipleOf(
    factor: BigDecimal,
    message: String = messages.multipleOf
): Unit = multipleOf(
    factor = factor,
    message = { message.filled("factor" to factor) }
)

public fun ValidationNode<BigDecimal>.multipleOf(
    factor: BigDecimal,
    message: BigDecimal.(BigDecimal) -> String
) {
    require(factor.signum() != 0) { "multipleOf needs a factor other than 0" }

    constraint(
        message = { it.message(factor) },
        code = "multipleOf",
        predicate = { it.remainder(factor).signum() == 0 }
    )
}

public fun ValidationNode<Double>.multipleOf(
    factor: Double,
    message: String = messages.multipleOf
): Unit = multipleOf(
    factor = factor,
    message = { message.filled("factor" to factor) }
)

public fun ValidationNode<Double>.multipleOf(
    factor: Double,
    message: Double.(Double) -> String
) {
    require(factor.isFinite() && factor != 0.0) { "multipleOf needs a finite factor other than 0" }

    constraint(
        message = { it.message(factor) },
        code = "multipleOf",
        predicate = { it.isFinite() && it.toBigDecimal().remainder(factor.toBigDecimal()).signum() == 0 }
    )
}

public fun ValidationNode<Float>.multipleOf(
    factor: Float,
    message: String = messages.multipleOf
): Unit = multipleOf(
    factor = factor,
    message = { message.filled("factor" to factor) }
)

public fun ValidationNode<Float>.multipleOf(
    factor: Float,
    message: Float.(Float) -> String
) {
    require(factor.isFinite() && factor != 0f) { "multipleOf needs a finite factor other than 0" }

    constraint(
        message = { it.message(factor) },
        code = "multipleOf",
        predicate = { it.isFinite() && it.toBigDecimal().remainder(factor.toBigDecimal()).signum() == 0 }
    )
}

public fun ValidationNode<Double>.integer(
    message: String = messages.integer
): Unit = constraint(
    message = message,
    code = "integer",
    predicate = { it.isFinite() && it % 1.0 == 0.0 }
)

@JvmName("integerFloat")
public fun ValidationNode<Float>.integer(
    message: String = messages.integer
): Unit = constraint(
    message = message,
    code = "integer",
    predicate = { it.isFinite() && it % 1f == 0f }
)

@JvmName("integerBigDecimal")
public fun ValidationNode<BigDecimal>.integer(
    message: String = messages.integer
): Unit = constraint(
    message = message,
    code = "integer",
    predicate = { it.signum() == 0 || it.stripTrailingZeros().scale() <= 0 }
)

public fun ValidationNode<Double>.finite(
    message: String = messages.finite
): Unit = constraint(
    message = message,
    code = "finite",
    predicate = { it.isFinite() }
)

@JvmName("finiteFloat")
public fun ValidationNode<Float>.finite(
    message: String = messages.finite
): Unit = constraint(
    message = message,
    code = "finite",
    predicate = { it.isFinite() }
)

public fun ValidationNode<BigDecimal>.maxDecimalPlaces(
    max: Int,
    message: String = messages.maxDecimalPlaces
): Unit = maxDecimalPlaces(
    max = max,
    message = { message.filled("max" to max) }
)

public fun ValidationNode<BigDecimal>.maxDecimalPlaces(
    max: Int,
    message: BigDecimal.(Int) -> String
): Unit = constraint(
    message = { it.message(max) },
    code = "maxDecimalPlaces",
    predicate = { it.stripTrailingZeros().scale() <= max }
)

public fun ValidationNode<BigDecimal>.maxIntegerDigits(
    max: Int,
    message: String = messages.maxIntegerDigits
): Unit = maxIntegerDigits(
    max = max,
    message = { message.filled("max" to max) }
)

public fun ValidationNode<BigDecimal>.maxIntegerDigits(
    max: Int,
    message: BigDecimal.(Int) -> String
): Unit = constraint(
    message = { it.message(max) },
    code = "maxIntegerDigits",
    predicate = { it.stripTrailingZeros().run { precision() - scale() } <= max }
)

public fun ValidationNode<Int>.minDigits(
    min: Int,
    message: String = messages.minDigits
): Unit = minDigits(
    min = min,
    message = { message.filled("min" to min) }
)

public fun ValidationNode<Int>.minDigits(
    min: Int,
    message: Int.(Int) -> String
): Unit = constraint(
    message = { it.message(min) },
    code = "minDigits",
    predicate = { it.toString().removePrefix("-").length >= min }
)

@JvmName("minDigitsLong")
public fun ValidationNode<Long>.minDigits(
    min: Int,
    message: String = messages.minDigits
): Unit = minDigits(
    min = min,
    message = { message.filled("min" to min) }
)

@JvmName("minDigitsLong")
public fun ValidationNode<Long>.minDigits(
    min: Int,
    message: Long.(Int) -> String
): Unit = constraint(
    message = { it.message(min) },
    code = "minDigits",
    predicate = { it.toString().removePrefix("-").length >= min }
)

@JvmName("minDigitsShort")
public fun ValidationNode<Short>.minDigits(
    min: Int,
    message: String = messages.minDigits
): Unit = minDigits(
    min = min,
    message = { message.filled("min" to min) }
)

@JvmName("minDigitsShort")
public fun ValidationNode<Short>.minDigits(
    min: Int,
    message: Short.(Int) -> String
): Unit = constraint(
    message = { it.message(min) },
    code = "minDigits",
    predicate = { it.toString().removePrefix("-").length >= min }
)

@JvmName("minDigitsByte")
public fun ValidationNode<Byte>.minDigits(
    min: Int,
    message: String = messages.minDigits
): Unit = minDigits(
    min = min,
    message = { message.filled("min" to min) }
)

@JvmName("minDigitsByte")
public fun ValidationNode<Byte>.minDigits(
    min: Int,
    message: Byte.(Int) -> String
): Unit = constraint(
    message = { it.message(min) },
    code = "minDigits",
    predicate = { it.toString().removePrefix("-").length >= min }
)

@JvmName("minDigitsBigInteger")
public fun ValidationNode<BigInteger>.minDigits(
    min: Int,
    message: String = messages.minDigits
): Unit = minDigits(
    min = min,
    message = { message.filled("min" to min) }
)

@JvmName("minDigitsBigInteger")
public fun ValidationNode<BigInteger>.minDigits(
    min: Int,
    message: BigInteger.(Int) -> String
): Unit = constraint(
    message = { it.message(min) },
    code = "minDigits",
    predicate = { it.toString().removePrefix("-").length >= min }
)

public fun ValidationNode<Int>.maxDigits(
    max: Int,
    message: String = messages.maxDigits
): Unit = maxDigits(
    max = max,
    message = { message.filled("max" to max) }
)

public fun ValidationNode<Int>.maxDigits(
    max: Int,
    message: Int.(Int) -> String
): Unit = constraint(
    message = { it.message(max) },
    code = "maxDigits",
    predicate = { it.toString().removePrefix("-").length <= max }
)

@JvmName("maxDigitsLong")
public fun ValidationNode<Long>.maxDigits(
    max: Int,
    message: String = messages.maxDigits
): Unit = maxDigits(
    max = max,
    message = { message.filled("max" to max) }
)

@JvmName("maxDigitsLong")
public fun ValidationNode<Long>.maxDigits(
    max: Int,
    message: Long.(Int) -> String
): Unit = constraint(
    message = { it.message(max) },
    code = "maxDigits",
    predicate = { it.toString().removePrefix("-").length <= max }
)

@JvmName("maxDigitsShort")
public fun ValidationNode<Short>.maxDigits(
    max: Int,
    message: String = messages.maxDigits
): Unit = maxDigits(
    max = max,
    message = { message.filled("max" to max) }
)

@JvmName("maxDigitsShort")
public fun ValidationNode<Short>.maxDigits(
    max: Int,
    message: Short.(Int) -> String
): Unit = constraint(
    message = { it.message(max) },
    code = "maxDigits",
    predicate = { it.toString().removePrefix("-").length <= max }
)

@JvmName("maxDigitsByte")
public fun ValidationNode<Byte>.maxDigits(
    max: Int,
    message: String = messages.maxDigits
): Unit = maxDigits(
    max = max,
    message = { message.filled("max" to max) }
)

@JvmName("maxDigitsByte")
public fun ValidationNode<Byte>.maxDigits(
    max: Int,
    message: Byte.(Int) -> String
): Unit = constraint(
    message = { it.message(max) },
    code = "maxDigits",
    predicate = { it.toString().removePrefix("-").length <= max }
)

@JvmName("maxDigitsBigInteger")
public fun ValidationNode<BigInteger>.maxDigits(
    max: Int,
    message: String = messages.maxDigits
): Unit = maxDigits(
    max = max,
    message = { message.filled("max" to max) }
)

@JvmName("maxDigitsBigInteger")
public fun ValidationNode<BigInteger>.maxDigits(
    max: Int,
    message: BigInteger.(Int) -> String
): Unit = constraint(
    message = { it.message(max) },
    code = "maxDigits",
    predicate = { it.toString().removePrefix("-").length <= max }
)

public fun ValidationNode<Int>.digitCount(
    count: Int,
    message: String = messages.digitCount
): Unit = digitCount(
    count = count,
    message = { message.filled("count" to count) }
)

public fun ValidationNode<Int>.digitCount(
    count: Int,
    message: Int.(Int) -> String
): Unit = constraint(
    message = { it.message(count) },
    code = "digitCount",
    predicate = { it.toString().removePrefix("-").length == count }
)

@JvmName("digitCountLong")
public fun ValidationNode<Long>.digitCount(
    count: Int,
    message: String = messages.digitCount
): Unit = digitCount(
    count = count,
    message = { message.filled("count" to count) }
)

@JvmName("digitCountLong")
public fun ValidationNode<Long>.digitCount(
    count: Int,
    message: Long.(Int) -> String
): Unit = constraint(
    message = { it.message(count) },
    code = "digitCount",
    predicate = { it.toString().removePrefix("-").length == count }
)

@JvmName("digitCountShort")
public fun ValidationNode<Short>.digitCount(
    count: Int,
    message: String = messages.digitCount
): Unit = digitCount(
    count = count,
    message = { message.filled("count" to count) }
)

@JvmName("digitCountShort")
public fun ValidationNode<Short>.digitCount(
    count: Int,
    message: Short.(Int) -> String
): Unit = constraint(
    message = { it.message(count) },
    code = "digitCount",
    predicate = { it.toString().removePrefix("-").length == count }
)

@JvmName("digitCountByte")
public fun ValidationNode<Byte>.digitCount(
    count: Int,
    message: String = messages.digitCount
): Unit = digitCount(
    count = count,
    message = { message.filled("count" to count) }
)

@JvmName("digitCountByte")
public fun ValidationNode<Byte>.digitCount(
    count: Int,
    message: Byte.(Int) -> String
): Unit = constraint(
    message = { it.message(count) },
    code = "digitCount",
    predicate = { it.toString().removePrefix("-").length == count }
)

@JvmName("digitCountBigInteger")
public fun ValidationNode<BigInteger>.digitCount(
    count: Int,
    message: String = messages.digitCount
): Unit = digitCount(
    count = count,
    message = { message.filled("count" to count) }
)

@JvmName("digitCountBigInteger")
public fun ValidationNode<BigInteger>.digitCount(
    count: Int,
    message: BigInteger.(Int) -> String
): Unit = constraint(
    message = { it.message(count) },
    code = "digitCount",
    predicate = { it.toString().removePrefix("-").length == count }
)

public fun ValidationNode<BigDecimal>.decimalPlaces(
    count: Int,
    message: String = messages.decimalPlaces
): Unit = decimalPlaces(
    count = count,
    message = { message.filled("count" to count) }
)

public fun ValidationNode<BigDecimal>.decimalPlaces(
    count: Int,
    message: BigDecimal.(Int) -> String
): Unit = constraint(
    message = { it.message(count) },
    code = "decimalPlaces",
    predicate = { it.scale().coerceAtLeast(0) == count }
)

public fun ValidationNode<BigDecimal>.minDecimalPlaces(
    min: Int,
    message: String = messages.minDecimalPlaces
): Unit = minDecimalPlaces(
    min = min,
    message = { message.filled("min" to min) }
)

public fun ValidationNode<BigDecimal>.minDecimalPlaces(
    min: Int,
    message: BigDecimal.(Int) -> String
): Unit = constraint(
    message = { it.message(min) },
    code = "minDecimalPlaces",
    predicate = { it.scale() >= min }
)

@JvmName("maxDecimalPlacesDouble")
public fun ValidationNode<Double>.maxDecimalPlaces(
    max: Int,
    message: String = messages.maxDecimalPlaces
): Unit = maxDecimalPlaces(
    max = max,
    message = { message.filled("max" to max) }
)

@JvmName("maxDecimalPlacesDouble")
public fun ValidationNode<Double>.maxDecimalPlaces(
    max: Int,
    message: Double.(Int) -> String
): Unit = constraint(
    message = { it.message(max) },
    code = "maxDecimalPlaces",
    predicate = { it.isFinite() && it.toBigDecimal().stripTrailingZeros().scale() <= max }
)

@JvmName("maxDecimalPlacesFloat")
public fun ValidationNode<Float>.maxDecimalPlaces(
    max: Int,
    message: String = messages.maxDecimalPlaces
): Unit = maxDecimalPlaces(
    max = max,
    message = { message.filled("max" to max) }
)

@JvmName("maxDecimalPlacesFloat")
public fun ValidationNode<Float>.maxDecimalPlaces(
    max: Int,
    message: Float.(Int) -> String
): Unit = constraint(
    message = { it.message(max) },
    code = "maxDecimalPlaces",
    predicate = { it.isFinite() && it.toBigDecimal().stripTrailingZeros().scale() <= max }
)
