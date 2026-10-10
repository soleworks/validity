package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.messages

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
    message = { message.replace("{other}", "$other") }
)

public fun <V> ValidationNode<V>.equalTo(
    other: V?,
    message: V.(V?) -> String
): Unit = constraint(
    message = { it.message(other) },
    code = "equalTo",
    skipped = other == null,
    predicate = { other == null || it == other }
)

public fun <V> ValidationNode<V>.notEqualTo(
    other: V?,
    message: String = messages.notEqualTo
): Unit = notEqualTo(
    other = other,
    message = { message.replace("{other}", "$other") }
)

public fun <V> ValidationNode<V>.notEqualTo(
    other: V?,
    message: V.(V?) -> String
): Unit = constraint(
    message = { it.message(other) },
    code = "notEqualTo",
    skipped = other == null,
    predicate = { other == null || it != other }
)

public fun <V> ValidationNode<V>.oneOf(
    values: Iterable<V>?,
    message: String = messages.oneOf
): Unit = oneOf(
    values = values,
    message = { message.replace("{values}", values?.joinToString().orEmpty()) }
)

public fun <V> ValidationNode<V>.oneOf(
    values: Iterable<V>?,
    message: V.(Iterable<V>?) -> String
): Unit = constraint(
    message = { it.message(values) },
    code = "oneOf",
    skipped = values == null,
    predicate = { values == null || it in values }
)

public fun <V> ValidationNode<V>.noneOf(
    values: Iterable<V>?,
    message: String = messages.noneOf
): Unit = noneOf(
    values = values,
    message = { message.replace("{values}", values?.joinToString().orEmpty()) }
)

public fun <V> ValidationNode<V>.noneOf(
    values: Iterable<V>?,
    message: V.(Iterable<V>?) -> String
): Unit = constraint(
    message = { it.message(values) },
    code = "noneOf",
    skipped = values == null,
    predicate = { values == null || it !in values }
)
