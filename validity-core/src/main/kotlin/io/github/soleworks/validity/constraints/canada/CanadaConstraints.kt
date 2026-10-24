package io.github.soleworks.validity.constraints.canada

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.constraints.hasLuhnCheckDigit
import io.github.soleworks.validity.messages

private val SIN_FORMAT = Regex("""\d{9}""")

public fun ValidationNode<String>.sin(
    message: String = messages.canada.sin
): Unit = constraint(
    message = message,
    code = "sin",
    predicate = { SIN_FORMAT.matches(it) && it.hasLuhnCheckDigit() }
)
