package io.github.soleworks.validity.constraints.southafrica

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.constraints.hasLuhnCheckDigit
import io.github.soleworks.validity.constraints.isDate
import io.github.soleworks.validity.messages

private const val TWENTIETH_CENTURY = 1900
private const val TWENTY_FIRST_CENTURY = 2000

private val CIPC_FORMAT = Regex("""\d{4}/\d{6}/\d{2}""")
private val ID_NUMBER_FORMAT = Regex("""(\d{2})(\d{2})(\d{2})\d{4}[01]\d{2}""")

internal val SOUTH_AFRICA_TAX_IDS: Map<String, (String) -> Boolean> = mapOf(
    "CIPC" to String::isCipc,
    "ID_NUMBER" to String::isIdNumber
)

/**
 * Checks that the string is a South African company registration number from the CIPC, as `YYYY/NNNNNN/NN`, such as
 * `2015/123456/07`.
 *
 * The number has no check digit, so only the format is checked.
 *
 * Fails with code `cipc` and, by default, the message `must be a valid company registration number`
 * ([SouthAfricaMessages.cipc][io.github.soleworks.validity.SouthAfricaMessages.cipc]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.cipc(
    message: String = messages.southAfrica.cipc
): Unit = constraint(
    message = message,
    code = "cipc",
    predicate = String::isCipc
)

/**
 * Checks that the string is a South African ID number: 13 digits with a valid Luhn check digit, such as
 * `8001015009087`.
 *
 * The first 6 digits are a date of birth that exists (`YYMMDD`), and the 11th digit is `0` (a citizen) or `1` (a
 * permanent resident).
 *
 * Fails with code `idNumber` and, by default, the message `must be a valid ID number`
 * ([SouthAfricaMessages.idNumber][io.github.soleworks.validity.SouthAfricaMessages.idNumber]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.idNumber(
    message: String = messages.southAfrica.idNumber
): Unit = constraint(
    message = message,
    code = "idNumber",
    predicate = String::isIdNumber
)

private fun String.isCipc(): Boolean = CIPC_FORMAT.matches(this)

private fun String.isIdNumber(): Boolean {
    val (year, month, day) = ID_NUMBER_FORMAT.matchEntire(this)?.destructured ?: return false
    val isBirthDate = listOf(TWENTIETH_CENTURY, TWENTY_FIRST_CENTURY).any { century ->
        isDate(year = century + year.toInt(), month = month.toInt(), day = day.toInt())
    }

    return isBirthDate && hasLuhnCheckDigit()
}
