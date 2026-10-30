package io.github.soleworks.validity.constraints.israel

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.constraints.hasLuhnCheckDigit
import io.github.soleworks.validity.messages

private val TEUDAT_ZEHUT_FORMAT = Regex("""\d{9}""")

/**
 * Checks that the string is an Israeli Teudat Zehut number: 9 digits with a valid check digit.
 *
 * Whitespace around the number is ignored. Shorter numbers need their leading zeros.
 *
 * Fails with code `teudatZehut` and, by default, the message `must be a valid teudat zehut`
 * ([IsraelMessages.teudatZehut][io.github.soleworks.validity.IsraelMessages.teudatZehut]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.teudatZehut(
    message: String = messages.israel.teudatZehut
): Unit = constraint(
    message = message,
    code = "teudatZehut",
    predicate = { it.trim().let { value -> TEUDAT_ZEHUT_FORMAT.matches(value) && value.hasLuhnCheckDigit() } }
)
