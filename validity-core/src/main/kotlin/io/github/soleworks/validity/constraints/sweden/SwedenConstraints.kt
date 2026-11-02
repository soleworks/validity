package io.github.soleworks.validity.constraints.sweden

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.constraints.hasLuhnCheckDigit
import io.github.soleworks.validity.messages

private const val LUHN_LENGTH = 10
private const val COORDINATION_DAY_OFFSET = 60

private val ORGANISATIONSNUMMER_FORMAT = Regex("""\d{6}-?\d{4}""")
private val PERSONNUMMER_FORMAT = Regex("""(?:\d{2})?\d{2}(\d{2})(\d{2})[-+]?\d{4}""")
private val MONTHS = 1..12
private val DAYS = 1..31

internal val SWEDEN_TAX_IDS: Map<String, (String) -> Boolean> = mapOf(
    "ORGANISATIONSNUMMER" to String::isOrganisationsnummer,
    "PERSONNUMMER" to String::isPersonnummer
)

/**
 * Checks that the string is a Swedish organisationsnummer: 10 digits with a valid Luhn check digit, as `5560360793`
 * or `556036-0793`.
 *
 * Fails with code `organisationsnummer` and, by default, the message `must be a valid organisationsnummer`
 * ([SwedenMessages.organisationsnummer][io.github.soleworks.validity.SwedenMessages.organisationsnummer]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.organisationsnummer(
    message: String = messages.sweden.organisationsnummer
): Unit = constraint(
    message = message,
    code = "organisationsnummer",
    predicate = String::isOrganisationsnummer
)

/**
 * Checks that the string is a Swedish personnummer (or samordningsnummer) with a valid Luhn check digit, as
 * `YYMMDD-NNNN`, `YYYYMMDD-NNNN` or without the separator, such as `811218-9876`.
 *
 * The separator can be a dash or a `+` (a person 100 or older). The month is from 1 to 12 and the day from 1 to 31, or
 * from 61 to 91 for a samordningsnummer.
 *
 * Fails with code `personnummer` and, by default, the message `must be a valid personnummer`
 * ([SwedenMessages.personnummer][io.github.soleworks.validity.SwedenMessages.personnummer]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.personnummer(
    message: String = messages.sweden.personnummer
): Unit = constraint(
    message = message,
    code = "personnummer",
    predicate = String::isPersonnummer
)

private fun String.isOrganisationsnummer(): Boolean = ORGANISATIONSNUMMER_FORMAT.matches(this) && hasLuhnCheckDigit()

private fun String.isPersonnummer(): Boolean {
    val (month, day) = PERSONNUMMER_FORMAT.matchEntire(this)?.destructured ?: return false
    val dayOfMonth = day.toInt()

    return month.toInt() in MONTHS &&
        (dayOfMonth in DAYS || dayOfMonth - COORDINATION_DAY_OFFSET in DAYS) &&
        filter(Char::isDigit).takeLast(LUHN_LENGTH).hasLuhnCheckDigit()
}
