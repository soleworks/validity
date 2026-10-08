package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.messages
import kotlin.time.Duration

public fun ValidationNode<Duration>.min(
    min: Duration?,
    message: String = messages.min
): Unit = min(
    min = min,
    message = { message.replace("{min}", "$min") }
)

public fun ValidationNode<Duration>.min(
    min: Duration?,
    message: Duration.(Duration?) -> String
): Unit = constraint(
    message = { it.message(min) },
    predicate = { min == null || it >= min }
)

public fun ValidationNode<Duration>.max(
    max: Duration?,
    message: String = messages.max
): Unit = max(
    max = max,
    message = { message.replace("{max}", "$max") }
)

public fun ValidationNode<Duration>.max(
    max: Duration?,
    message: Duration.(Duration?) -> String
): Unit = constraint(
    message = { it.message(max) },
    predicate = { max == null || it <= max }
)

public fun ValidationNode<Duration>.greaterThan(
    other: Duration?,
    message: String = messages.greaterThan
): Unit = greaterThan(
    other = other,
    message = { message.replace("{other}", "$other") }
)

public fun ValidationNode<Duration>.greaterThan(
    other: Duration?,
    message: Duration.(Duration?) -> String
): Unit = constraint(
    message = { it.message(other) },
    predicate = { other == null || it > other }
)

public fun ValidationNode<Duration>.lessThan(
    other: Duration?,
    message: String = messages.lessThan
): Unit = lessThan(
    other = other,
    message = { message.replace("{other}", "$other") }
)

public fun ValidationNode<Duration>.lessThan(
    other: Duration?,
    message: Duration.(Duration?) -> String
): Unit = constraint(
    message = { it.message(other) },
    predicate = { other == null || it < other }
)

public fun ValidationNode<Duration>.between(
    min: Duration?,
    max: Duration?,
    message: String = messages.between
): Unit = between(
    min = min,
    max = max,
    message = { _, _ -> message.replace("{min}", "$min").replace("{max}", "$max") }
)

public fun ValidationNode<Duration>.between(
    min: Duration?,
    max: Duration?,
    message: Duration.(Duration?, Duration?) -> String
): Unit = constraint(
    message = { it.message(min, max) },
    predicate = { min == null || max == null || it in min..max }
)
