package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.filled
import io.github.soleworks.validity.messages
import kotlin.time.Duration

/**
 * Checks that the duration is at least [min].
 *
 * When [min] is `null`, nothing is checked.
 *
 * Fails with code `min` and, by default, the message `must be at least {min}`
 * ([Messages.min][io.github.soleworks.validity.Messages.min]).
 *
 * @param min the smallest value accepted.
 * @param message the message of the violation. `{min}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Duration>.min(
    min: Duration?,
    message: String = messages.min
): Unit = min(
    min = min,
    message = { message.filled("min" to min) }
)

/**
 * Checks that the duration is at least [min].
 *
 * When [min] is `null`, nothing is checked.
 *
 * Fails with code `min` and the message that [message] builds.
 *
 * @param min the smallest value accepted.
 * @param message builds the message from the value (the receiver) and [min].
 */
public fun ValidationNode<Duration>.min(
    min: Duration?,
    message: Duration.(Duration) -> String
) {
    if (min == null)
        return

    constraint(
        message = { it.message(min) },
        code = "min",
        predicate = { it >= min }
    )
}

/**
 * Checks that the duration is at most [max].
 *
 * When [max] is `null`, nothing is checked.
 *
 * Fails with code `max` and, by default, the message `must be at most {max}`
 * ([Messages.max][io.github.soleworks.validity.Messages.max]).
 *
 * @param max the largest value accepted.
 * @param message the message of the violation. `{max}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Duration>.max(
    max: Duration?,
    message: String = messages.max
): Unit = max(
    max = max,
    message = { message.filled("max" to max) }
)

/**
 * Checks that the duration is at most [max].
 *
 * When [max] is `null`, nothing is checked.
 *
 * Fails with code `max` and the message that [message] builds.
 *
 * @param max the largest value accepted.
 * @param message builds the message from the value (the receiver) and [max].
 */
public fun ValidationNode<Duration>.max(
    max: Duration?,
    message: Duration.(Duration) -> String
) {
    if (max == null)
        return

    constraint(
        message = { it.message(max) },
        code = "max",
        predicate = { it <= max }
    )
}

/**
 * Checks that the duration is greater than [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `greaterThan` and, by default, the message `must be greater than {other}`
 * ([Messages.greaterThan][io.github.soleworks.validity.Messages.greaterThan]).
 *
 * @param other the value it must be greater than.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Duration>.greaterThan(
    other: Duration?,
    message: String = messages.greaterThan
): Unit = greaterThan(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the duration is greater than [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `greaterThan` and the message that [message] builds.
 *
 * @param other the value it must be greater than.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<Duration>.greaterThan(
    other: Duration?,
    message: Duration.(Duration) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "greaterThan",
        predicate = { it > other }
    )
}

/**
 * Checks that the duration is less than [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `lessThan` and, by default, the message `must be less than {other}`
 * ([Messages.lessThan][io.github.soleworks.validity.Messages.lessThan]).
 *
 * @param other the value it must be less than.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Duration>.lessThan(
    other: Duration?,
    message: String = messages.lessThan
): Unit = lessThan(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the duration is less than [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `lessThan` and the message that [message] builds.
 *
 * @param other the value it must be less than.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<Duration>.lessThan(
    other: Duration?,
    message: Duration.(Duration) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "lessThan",
        predicate = { it < other }
    )
}

/**
 * Checks that the duration is between [min] and [max], both included.
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
public fun ValidationNode<Duration>.between(
    min: Duration?,
    max: Duration?,
    message: String = messages.between
): Unit = between(
    min = min,
    max = max,
    message = { _, _ -> message.filled("min" to min, "max" to max) }
)

/**
 * Checks that the duration is between [min] and [max], both included.
 *
 * When [min] or [max] is `null`, nothing is checked.
 *
 * Fails with code `between` and the message that [message] builds.
 *
 * @param min the smallest value accepted.
 * @param max the largest value accepted.
 * @param message builds the message from the value (the receiver), [min] and [max].
 */
public fun ValidationNode<Duration>.between(
    min: Duration?,
    max: Duration?,
    message: Duration.(Duration, Duration) -> String
) {
    if (min == null || max == null)
        return

    constraint(
        message = { it.message(min, max) },
        code = "between",
        predicate = { it in min..max }
    )
}
