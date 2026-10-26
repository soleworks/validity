package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.filled
import io.github.soleworks.validity.messages
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.time.OffsetTime
import java.time.ZonedDateTime

public fun <V : Any> ValidationNode<V?>.notNull(
    message: String = messages.required
): Unit = constraint(
    message = message,
    code = "notNull",
    predicate = { it != null }
)

public fun <V> ValidationNode<V>.equalTo(
    other: V?,
    message: String = messages.equalTo
): Unit = equalTo(
    other = other,
    message = { message.filled("other" to other) }
)

public fun <V> ValidationNode<V>.equalTo(
    other: V?,
    message: suspend V.(V) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "equalTo",
        predicate = { it.isSameValue(other) }
    )
}

public fun <V> ValidationNode<V>.notEqualTo(
    other: V?,
    message: String = messages.notEqualTo
): Unit = notEqualTo(
    other = other,
    message = { message.filled("other" to other) }
)

public fun <V> ValidationNode<V>.notEqualTo(
    other: V?,
    message: suspend V.(V) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "notEqualTo",
        predicate = { !it.isSameValue(other) }
    )
}

public fun <V> ValidationNode<V>.oneOf(
    values: Iterable<V>?,
    message: String = messages.oneOf
): Unit = oneOf(
    values = values,
    message = { message.filled("values" to values) }
)

public fun <V> ValidationNode<V>.oneOf(
    values: Iterable<V>?,
    message: suspend V.(Iterable<V>) -> String
) {
    if (values == null)
        return

    constraint(
        message = { it.message(values) },
        code = "oneOf",
        predicate = { values.hasValue(it) }
    )
}

public fun <V> ValidationNode<V>.noneOf(
    values: Iterable<V>?,
    message: String = messages.noneOf
): Unit = noneOf(
    values = values,
    message = { message.filled("values" to values) }
)

public fun <V> ValidationNode<V>.noneOf(
    values: Iterable<V>?,
    message: suspend V.(Iterable<V>) -> String
) {
    if (values == null)
        return

    constraint(
        message = { it.message(values) },
        code = "noneOf",
        predicate = { !values.hasValue(it) }
    )
}

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
