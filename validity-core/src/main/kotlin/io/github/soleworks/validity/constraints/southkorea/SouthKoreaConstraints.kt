package io.github.soleworks.validity.constraints.southkorea

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.constraints.digits
import io.github.soleworks.validity.constraints.isDate
import io.github.soleworks.validity.constraints.weightedSum
import io.github.soleworks.validity.messages

private const val MODULUS = 11
private const val DECIMAL_MODULUS = 10
private const val FIRST_TAX_OFFICE = 101
private const val EMPTY_BUSINESS_TYPE = "00"
private const val EMPTY_SERIAL = "0000"
private const val CENTURY_INDEX = 6
private const val LAST_PLACE_OF_BIRTH = 96
private const val PLACE_OF_BIRTH_START = 7
private const val PLACE_OF_BIRTH_END = 9

private val BRN_FORMAT = Regex("""(\d{3})-?(\d{2})-?(\d{4})\d""")
private val RRN_FORMAT = Regex("""(\d{2})(\d{2})(\d{2})-?\d{7}""")
private val RRN_WEIGHTS = listOf(2, 3, 4, 5, 6, 7, 8, 9, 2, 3, 4, 5)
private val RRN_CENTURIES = mapOf(
    1 to 1900, 2 to 1900, 5 to 1900, 6 to 1900,
    3 to 2000, 4 to 2000, 7 to 2000, 8 to 2000,
    9 to 1800, 0 to 1800
)

internal val SOUTH_KOREA_TAX_IDS: Map<String, (String) -> Boolean> = mapOf(
    "BRN" to String::isBrn,
    "RRN" to String::isRrn
)

/**
 * Checks that the string is a South Korean BRN (business registration number): 10 digits, as `1348672683` or
 * `134-86-72683`.
 *
 * The first 3 digits are a tax office from `101`, and neither the next 2 (the type of business) nor the 4 after them
 * (the serial) are all zeros.
 *
 * Fails with code `brn` and, by default, the message `must be a valid business registration number`
 * ([SouthKoreaMessages.brn][io.github.soleworks.validity.SouthKoreaMessages.brn]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.brn(
    message: String = messages.southKorea.brn
): Unit = constraint(
    message = message,
    code = "brn",
    predicate = String::isBrn
)

/**
 * Checks that the string is a South Korean RRN (resident registration number): 13 digits with a valid check digit, as
 * `9710139019902` or `971013-9019902`.
 *
 * The first 6 digits are a date of birth that exists, in the century the 7th digit gives, and the place of birth
 * (digits 8 and 9) is at most `96`.
 *
 * Fails with code `rrn` and, by default, the message `must be a valid resident registration number`
 * ([SouthKoreaMessages.rrn][io.github.soleworks.validity.SouthKoreaMessages.rrn]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.rrn(
    message: String = messages.southKorea.rrn
): Unit = constraint(
    message = message,
    code = "rrn",
    predicate = String::isRrn
)

private fun String.isBrn(): Boolean {
    val (taxOffice, businessType, serial) = BRN_FORMAT.matchEntire(this)?.destructured ?: return false

    return taxOffice.toInt() >= FIRST_TAX_OFFICE && businessType != EMPTY_BUSINESS_TYPE && serial != EMPTY_SERIAL
}

private fun String.isRrn(): Boolean {
    val (year, month, day) = RRN_FORMAT.matchEntire(this)?.destructured ?: return false
    val digits = digits()
    val century = RRN_CENTURIES.getValue(digits[CENTURY_INDEX])
    val checkDigit = (MODULUS - digits.weightedSum(RRN_WEIGHTS) % MODULUS) % DECIMAL_MODULUS
    val placeOfBirth = filter(Char::isDigit).substring(startIndex = PLACE_OF_BIRTH_START, endIndex = PLACE_OF_BIRTH_END)

    return isDate(year = century + year.toInt(), month = month.toInt(), day = day.toInt()) &&
        placeOfBirth.toInt() <= LAST_PLACE_OF_BIRTH &&
        digits.last() == checkDigit
}
