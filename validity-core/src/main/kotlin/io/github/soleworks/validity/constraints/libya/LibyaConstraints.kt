package io.github.soleworks.validity.constraints.libya

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.messages

private val NIN_FORMAT = Regex("[12]\\d{11}")

public fun ValidationNode<String>.nin(
    message: String = messages.libya.nin
): Unit = constraint(
    message = message,
    predicate = { NIN_FORMAT.matches(it.trim()) }
)
