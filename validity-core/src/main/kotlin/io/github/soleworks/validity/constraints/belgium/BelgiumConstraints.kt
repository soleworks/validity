package io.github.soleworks.validity.constraints.belgium

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.constraints.digits
import io.github.soleworks.validity.messages

private const val MODULUS = 97L
private const val CHECK_LENGTH = 2
private const val MILLENNIUM_PREFIX = "2"

private val BCE_FORMAT = Regex("""[01]\d{9}|[01]\d{3}\.\d{3}\.\d{3}""")
private val NATIONAL_NUMBER_FORMAT = Regex("""\d{2}(\d{2})\d{7}|\d{2}\.(\d{2})\.\d{2}-\d{3}\.\d{2}""")
private val MONTHS = 0..12

internal val BELGIUM_TAX_IDS: Map<String, (String) -> Boolean> = mapOf(
    "BCE" to String::isBce,
    "NATIONAL_NUMBER" to String::isNationalNumber
)

/**
 * Checks that the string is a Belgian enterprise number (BCE/KBO, also the VAT number): 10 digits with valid check
 * digits, as `0403170701` or `0403.170.701`.
 *
 * The first digit is `0` or `1`, and the last two digits are 97 minus the first eight modulo 97.
 *
 * Fails with code `bce` and, by default, the message `must be a valid enterprise number`
 * ([BelgiumMessages.bce][io.github.soleworks.validity.BelgiumMessages.bce]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.bce(
    message: String = messages.belgium.bce
): Unit = constraint(
    message = message,
    code = "bce",
    predicate = String::isBce
)

/**
 * Checks that the string is a Belgian national number (rijksregisternummer / numéro de registre national): 11 digits
 * with valid check digits, as `85073003328` or `85.07.30-033.28`.
 *
 * The month of birth is from `00` to `12`, and the check digits must match for a birth in the 1900s or the 2000s.
 *
 * Fails with code `nationalNumber` and, by default, the message `must be a valid national number`
 * ([BelgiumMessages.nationalNumber][io.github.soleworks.validity.BelgiumMessages.nationalNumber]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.nationalNumber(
    message: String = messages.belgium.nationalNumber
): Unit = constraint(
    message = message,
    code = "nationalNumber",
    predicate = String::isNationalNumber
)

private fun String.isBce(): Boolean {
    if (!BCE_FORMAT.matches(this))
        return false

    val digits = digits().joinToString("")

    return digits.toLong() > 0 && digits.hasMod97CheckDigits()
}

private fun String.isNationalNumber(): Boolean {
    val match = NATIONAL_NUMBER_FORMAT.matchEntire(this) ?: return false
    val month = match.groupValues
        .drop(1)
        .first(String::isNotEmpty)
        .toInt()
    val digits = digits().joinToString("")

    return month in MONTHS && (digits.hasMod97CheckDigits() || (MILLENNIUM_PREFIX + digits).hasMod97CheckDigits())
}

private fun String.hasMod97CheckDigits(): Boolean =
    MODULUS - dropLast(CHECK_LENGTH).toLong() % MODULUS == takeLast(CHECK_LENGTH).toLong()
