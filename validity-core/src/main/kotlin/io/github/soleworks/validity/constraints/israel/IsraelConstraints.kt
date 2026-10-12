package io.github.soleworks.validity.constraints.israel

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.constraints.hasLuhnCheckDigit
import io.github.soleworks.validity.messages

private val TEUDAT_ZEHUT_FORMAT = Regex("\\d{9}")

public fun ValidationNode<String>.teudatZehut(
    message: String = messages.israel.teudatZehut
): Unit = constraint(
    message = message,
    code = "teudatZehut",
    predicate = { it.trim().let { value -> TEUDAT_ZEHUT_FORMAT.matches(value) && value.hasLuhnCheckDigit() } }
)
