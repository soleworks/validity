package io.github.soleworks.validity.constraints.germany

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.constraints.digits
import io.github.soleworks.validity.messages

private const val MODULUS = 11
private const val DECIMAL_MODULUS = 10
private const val INITIAL_CHECK = 5
private const val VALID_CHECK = 1
private const val STEUER_ID_BODY_LENGTH = 10

private val UST_ID_NR_FORMAT = Regex("""(DE)?[1-9]\d{8}""")
private val STEUER_ID_FORMAT = Regex("""[1-9]\d{10}""")
private val STEUER_ID_REPEATS = 2..3

internal val GERMANY_TAX_IDS: Map<String, (String) -> Boolean> = mapOf(
    "UST_ID_NR" to String::isUstIdNr,
    "STEUER_ID" to String::isSteuerId
)

/**
 * Checks that the string is a German USt-IdNr. (VAT number) with a valid check digit, as 9 digits or with the `DE`
 * prefix, such as `DE136695976`.
 *
 * The first digit is not `0`, and the check digit is the ISO 7064 MOD 11,10 of the others.
 *
 * Fails with code `ustIdNr` and, by default, the message `must be a valid USt-IdNr.`
 * ([GermanyMessages.ustIdNr][io.github.soleworks.validity.GermanyMessages.ustIdNr]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.ustIdNr(
    message: String = messages.germany.ustIdNr
): Unit = constraint(
    message = message,
    code = "ustIdNr",
    predicate = String::isUstIdNr
)

/**
 * Checks that the string is a German Steuer-ID (Steueridentifikationsnummer): 11 digits with a valid check digit, such
 * as `36574261809`.
 *
 * The first digit is not `0`, and in the first 10 digits exactly one digit appears two or three times. The check digit
 * is the ISO 7064 MOD 11,10 of the others.
 *
 * Fails with code `steuerId` and, by default, the message `must be a valid Steuer-ID`
 * ([GermanyMessages.steuerId][io.github.soleworks.validity.GermanyMessages.steuerId]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.steuerId(
    message: String = messages.germany.steuerId
): Unit = constraint(
    message = message,
    code = "steuerId",
    predicate = String::isSteuerId
)

private fun String.isUstIdNr(): Boolean = UST_ID_NR_FORMAT.matches(this) && digits().hasIso7064CheckDigit()

private fun String.isSteuerId(): Boolean {
    if (!STEUER_ID_FORMAT.matches(this))
        return false

    val repeats = take(STEUER_ID_BODY_LENGTH)
        .groupingBy { it }
        .eachCount()
        .values
        .filter { it > 1 }

    return repeats.size == 1 && repeats.single() in STEUER_ID_REPEATS && digits().hasIso7064CheckDigit()
}

private fun List<Int>.hasIso7064CheckDigit(): Boolean = fold(INITIAL_CHECK) { check, digit ->
    ((if (check == 0) DECIMAL_MODULUS else check) * 2 % MODULUS + digit) % DECIMAL_MODULUS
} == VALID_CHECK
