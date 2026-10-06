package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.Messages
import io.github.soleworks.validity.ValidationNode

public fun <V : Any> ValidationNode<V?>.notNull(
    message: String = Messages.REQUIRED
): Unit = constraint(
    message = message,
    predicate = { it != null }
)

public fun <V> ValidationNode<V>.equalTo(
    other: V?,
    message: String = Messages.EQUAL_TO
): Unit = equalTo(
    other = other,
    message = { message.replace("{other}", "$other") }
)

public fun <V> ValidationNode<V>.equalTo(
    other: V?,
    message: V.(V?) -> String
): Unit = constraint(
    message = { it.message(other) },
    predicate = { other == null || it == other }
)

public fun <V> ValidationNode<V>.notEqualTo(
    other: V?,
    message: String = Messages.NOT_EQUAL_TO
): Unit = notEqualTo(
    other = other,
    message = { message.replace("{other}", "$other") }
)

public fun <V> ValidationNode<V>.notEqualTo(
    other: V?,
    message: V.(V?) -> String
): Unit = constraint(
    message = { it.message(other) },
    predicate = { other == null || it != other }
)

public fun <V> ValidationNode<V>.oneOf(
    values: Iterable<V>?,
    message: String = Messages.ONE_OF
): Unit = oneOf(
    values = values,
    message = { message.replace("{values}", values?.joinToString().orEmpty()) }
)

public fun <V> ValidationNode<V>.oneOf(
    values: Iterable<V>?,
    message: V.(Iterable<V>?) -> String
): Unit = constraint(
    message = { it.message(values) },
    predicate = { values == null || it in values }
)

public fun <V> ValidationNode<V>.noneOf(
    values: Iterable<V>?,
    message: String = Messages.NONE_OF
): Unit = noneOf(
    values = values,
    message = { message.replace("{values}", values?.joinToString().orEmpty()) }
)

public fun <V> ValidationNode<V>.noneOf(
    values: Iterable<V>?,
    message: V.(Iterable<V>?) -> String
): Unit = constraint(
    message = { it.message(values) },
    predicate = { values == null || it !in values }
)
