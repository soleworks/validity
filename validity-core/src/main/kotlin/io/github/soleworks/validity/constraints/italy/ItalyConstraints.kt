package io.github.soleworks.validity.constraints.italy

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.constraints.hasLuhnCheckDigit
import io.github.soleworks.validity.messages

private const val ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
private const val OMOCODE_DIGITS = "LMNPQRSTUV"
private const val PERSON_LENGTH = 7
private const val OFFICE_START = 7
private const val OFFICE_END = 10
private const val FEMALE_DAY_OFFSET = 40
private const val DECIMAL_RADIX = 10

private val PARTITA_IVA_FORMAT = Regex("""\d{11}""")
private val CODICE_FISCALE_FORMAT = Regex(
    """[A-Z]{6}[\dLMNPQRSTUV]{2}[ABCDEHLMPRST]([\dLMNPQRSTUV]{2})[A-Z][\dLMNPQRSTUV]{3}[A-Z]"""
)
private val ODD_VALUES = listOf(
    1,
    0,
    5,
    7,
    9,
    13,
    15,
    17,
    19,
    21,
    2,
    4,
    18,
    20,
    11,
    3,
    6,
    8,
    12,
    14,
    16,
    10,
    22,
    25,
    24,
    23
)
private val PARTITA_IVA_OFFICES = (1..100).toSet() + setOf(120, 121, 888, 999)
private val DAYS = 1..31

internal val ITALY_TAX_IDS: Map<String, (String) -> Boolean> = mapOf(
    "PARTITA_IVA" to String::isPartitaIva,
    "CODICE_FISCALE" to String::isCodiceFiscale
)

/**
 * Checks that the string is an Italian partita IVA: 11 digits with a valid Luhn check digit, such as `00743110157`.
 *
 * The first 7 digits are not all zeros, and digits 8 to 10 are the code of a tax office: `001` to `100`, `120`, `121`,
 * `888` or `999`.
 *
 * Fails with code `partitaIva` and, by default, the message `must be a valid partita IVA`
 * ([ItalyMessages.partitaIva][io.github.soleworks.validity.ItalyMessages.partitaIva]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.partitaIva(
    message: String = messages.italy.partitaIva
): Unit = constraint(
    message = message,
    code = "partitaIva",
    predicate = String::isPartitaIva
)

/**
 * Checks that the string is the Italian codice fiscale of a person: 16 characters with a valid check letter, such as
 * `RSSMRA85T10A562S`.
 *
 * Letters are uppercase. The digits can be replaced by the letters `L` to `V`, as the Agenzia delle Entrate does for
 * codes that repeat (omocodia), and the day of birth must be from 1 to 31, plus 40 for women.
 *
 * Fails with code `codiceFiscale` and, by default, the message `must be a valid codice fiscale`
 * ([ItalyMessages.codiceFiscale][io.github.soleworks.validity.ItalyMessages.codiceFiscale]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.codiceFiscale(
    message: String = messages.italy.codiceFiscale
): Unit = constraint(
    message = message,
    code = "codiceFiscale",
    predicate = String::isCodiceFiscale
)

private fun String.isPartitaIva(): Boolean = PARTITA_IVA_FORMAT.matches(this) &&
    take(PERSON_LENGTH).toInt() != 0 &&
    substring(startIndex = OFFICE_START, endIndex = OFFICE_END).toInt() in PARTITA_IVA_OFFICES &&
    hasLuhnCheckDigit()

private fun String.isCodiceFiscale(): Boolean {
    val (day) = CODICE_FISCALE_FORMAT.matchEntire(this)?.destructured ?: return false
    val dayOfMonth = day.map(Char::omocodeDigit).fold(0) { number, digit -> number * DECIMAL_RADIX + digit }

    return (dayOfMonth in DAYS || dayOfMonth - FEMALE_DAY_OFFSET in DAYS) && last() == checkLetter()
}

private fun String.checkLetter(): Char {
    val sum = dropLast(1)
        .mapIndexed { index, char -> if (index % 2 == 0) ODD_VALUES[char.position()] else char.position() }
        .sum()

    return ALPHABET[sum % ALPHABET.length]
}

private fun Char.position(): Int = if (isDigit()) digitToInt() else ALPHABET.indexOf(this)

private fun Char.omocodeDigit(): Int = if (isDigit()) digitToInt() else OMOCODE_DIGITS.indexOf(this)
