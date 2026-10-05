package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.Messages
import io.github.soleworks.validity.ValidationNode

public fun ValidationNode<String>.minLength(
    min: Int,
    message: String = Messages.MIN_LENGTH
): Unit = constraint(
    message = message.replace("{min}", "$min"),
    predicate = { it.length >= min }
)

public fun ValidationNode<String>.minLength(
    min: Int,
    message: String.(Int) -> String
): Unit = constraint(
    message = { it.message(min) },
    predicate = { it.length >= min }
)
