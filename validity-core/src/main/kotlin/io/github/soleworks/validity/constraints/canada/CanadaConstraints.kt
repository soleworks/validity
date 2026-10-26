package io.github.soleworks.validity.constraints.canada

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.constraints.hasLuhnCheckDigit
import io.github.soleworks.validity.messages

private val SIN_FORMAT = Regex("""\d{9}""")

/**
 * Checks that the string is a Canadian SIN: 9 digits with a valid Luhn check digit, with no spaces.
 *
 * Fails with code `sin` and, by default, the message `must be a valid SIN`
 * ([CanadaMessages.sin][io.github.soleworks.validity.CanadaMessages.sin]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.sin(
    message: String = messages.canada.sin
): Unit = constraint(
    message = message,
    code = "sin",
    predicate = { SIN_FORMAT.matches(it) && it.hasLuhnCheckDigit() }
)
