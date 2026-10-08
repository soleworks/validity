package io.github.soleworks.validity.constraints.tunisia

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.messages

private val CIN_FORMAT = Regex("\\d{8}")

public fun ValidationNode<String>.cin(
    message: String = messages.tunisia.cin
): Unit = constraint(
    message = message,
    predicate = { CIN_FORMAT.matches(it.trim()) }
)
