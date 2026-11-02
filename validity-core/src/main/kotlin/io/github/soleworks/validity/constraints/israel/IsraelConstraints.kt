package io.github.soleworks.validity.constraints.israel

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.constraints.hasLuhnCheckDigit
import io.github.soleworks.validity.messages

private val TEUDAT_ZEHUT_FORMAT = Regex("""\d{9}""")
private val COMPANY_NUMBER_FORMAT = Regex("""5\d{8}""")

internal val ISRAEL_TAX_IDS: Map<String, (String) -> Boolean> = mapOf(
    "TEUDAT_ZEHUT" to String::isTeudatZehut,
    "COMPANY_NUMBER" to String::isCompanyNumber
)

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
    predicate = String::isTeudatZehut
)

/**
 * Checks that the string is the number of an Israeli company (ח.פ.): 9 digits that start with `5` and end with a
 * valid Luhn check digit, such as `516179157`.
 *
 * Fails with code `companyNumber` and, by default, the message `must be a valid company number`
 * ([IsraelMessages.companyNumber][io.github.soleworks.validity.IsraelMessages.companyNumber]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.companyNumber(
    message: String = messages.israel.companyNumber
): Unit = constraint(
    message = message,
    code = "companyNumber",
    predicate = String::isCompanyNumber
)

private fun String.isTeudatZehut(): Boolean = trim().let { value ->
    TEUDAT_ZEHUT_FORMAT.matches(value) && value.hasLuhnCheckDigit()
}

private fun String.isCompanyNumber(): Boolean = COMPANY_NUMBER_FORMAT.matches(this) && hasLuhnCheckDigit()
