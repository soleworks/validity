package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.Messages
import io.github.soleworks.validity.ValidationNode

public fun <V : Any> ValidationNode<V?>.notNull(
    message: String = Messages.REQUIRED
): Unit = constraint(
    message = message,
    predicate = { it != null }
)
