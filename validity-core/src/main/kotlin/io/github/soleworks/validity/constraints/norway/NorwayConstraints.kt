package io.github.soleworks.validity.constraints.norway

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.constraints.digits
import io.github.soleworks.validity.constraints.weightedSum
import io.github.soleworks.validity.messages

private const val MODULUS = 11
private const val FIRST_CHECK_INDEX = 9

private val ORGANISASJONSNUMMER_FORMAT = Regex("""\d{9}|\d{3} \d{3} \d{3}""")
private val FODSELSNUMMER_FORMAT = Regex("""\d{11}|\d{6} \d{5}""")
private val ORGANISASJONSNUMMER_WEIGHTS = listOf(3, 2, 7, 6, 5, 4, 3, 2, 1)
private val FODSELSNUMMER_FIRST_WEIGHTS = listOf(3, 7, 6, 1, 8, 9, 4, 5, 2)
private val FODSELSNUMMER_SECOND_WEIGHTS = listOf(5, 4, 3, 2, 7, 6, 5, 4, 3, 2)

internal val NORWAY_TAX_IDS: Map<String, (String) -> Boolean> = mapOf(
    "ORGANISASJONSNUMMER" to String::isOrganisasjonsnummer,
    "FODSELSNUMMER" to String::isFodselsnummer
)

/**
 * Checks that the string is a Norwegian organisasjonsnummer: 9 digits with a valid check digit, as `988077917` or
 * `988 077 917`.
 *
 * Fails with code `organisasjonsnummer` and, by default, the message `must be a valid organisasjonsnummer`
 * ([NorwayMessages.organisasjonsnummer][io.github.soleworks.validity.NorwayMessages.organisasjonsnummer]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.organisasjonsnummer(
    message: String = messages.norway.organisasjonsnummer
): Unit = constraint(
    message = message,
    code = "organisasjonsnummer",
    predicate = String::isOrganisasjonsnummer
)

/**
 * Checks that the string is a Norwegian fødselsnummer (or D-nummer): 11 digits with two valid check digits, as
 * `15108695088` or `151086 95088`.
 *
 * Fails with code `fodselsnummer` and, by default, the message `must be a valid fødselsnummer`
 * ([NorwayMessages.fodselsnummer][io.github.soleworks.validity.NorwayMessages.fodselsnummer]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.fodselsnummer(
    message: String = messages.norway.fodselsnummer
): Unit = constraint(
    message = message,
    code = "fodselsnummer",
    predicate = String::isFodselsnummer
)

private fun String.isOrganisasjonsnummer(): Boolean =
    ORGANISASJONSNUMMER_FORMAT.matches(this) && digits().weightedSum(ORGANISASJONSNUMMER_WEIGHTS) % MODULUS == 0

private fun String.isFodselsnummer(): Boolean {
    if (!FODSELSNUMMER_FORMAT.matches(this))
        return false

    val digits = digits()

    return digits[FIRST_CHECK_INDEX] == digits.checkDigit(FODSELSNUMMER_FIRST_WEIGHTS) &&
        digits.last() == digits.checkDigit(FODSELSNUMMER_SECOND_WEIGHTS)
}

private fun List<Int>.checkDigit(
    weights: List<Int>
): Int = (-weightedSum(weights)).mod(MODULUS)
