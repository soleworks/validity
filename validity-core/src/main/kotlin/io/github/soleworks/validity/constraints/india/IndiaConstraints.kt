package io.github.soleworks.validity.constraints.india

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.messages

private const val ALPHANUMERIC_RADIX = 36
private const val EMPTY_SERIAL = "0000"
private const val SERIAL_START = 5
private const val SERIAL_END = 9

private val PAN_FORMAT = Regex("""[A-Z]{3}[ABCFGHJLPTK][A-Z]\d{4}[A-Z]""")
private val GSTIN_FORMAT = Regex("""(\d{2})([A-Z\d]{10})[1-9A-Z]Z[\dA-Z]""")
private val GSTIN_STATES = (1..38).toSet() + setOf(97, 99)

internal val INDIA_TAX_IDS: Map<String, (String) -> Boolean> = mapOf(
    "PAN" to String::isPan,
    "GSTIN" to String::isGstin
)

/**
 * Checks that the string is an Indian PAN (permanent account number): 5 letters, 4 digits and a letter, such as
 * `ABCPE1234F`.
 *
 * Letters are uppercase. The 4th letter is the type of holder (`A`, `B`, `C`, `F`, `G`, `H`, `J`, `L`, `P`, `T` or
 * `K`), and the digits are not all zeros.
 *
 * Fails with code `pan` and, by default, the message `must be a valid PAN`
 * ([IndiaMessages.pan][io.github.soleworks.validity.IndiaMessages.pan]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.pan(
    message: String = messages.india.pan
): Unit = constraint(
    message = message,
    code = "pan",
    predicate = String::isPan
)

/**
 * Checks that the string is an Indian GSTIN: 15 characters with a valid check character, such as `27AAPFU0939F1ZV`.
 *
 * Letters are uppercase. The first 2 digits are a state code (`01` to `38`, `97` or `99`), the next 10 characters are
 * a valid PAN, the 13th is not `0` and the 14th is `Z`.
 *
 * Fails with code `gstin` and, by default, the message `must be a valid GSTIN`
 * ([IndiaMessages.gstin][io.github.soleworks.validity.IndiaMessages.gstin]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.gstin(
    message: String = messages.india.gstin
): Unit = constraint(
    message = message,
    code = "gstin",
    predicate = String::isGstin
)

private fun String.isPan(): Boolean =
    PAN_FORMAT.matches(this) && substring(startIndex = SERIAL_START, endIndex = SERIAL_END) != EMPTY_SERIAL

private fun String.isGstin(): Boolean {
    val (state, pan) = GSTIN_FORMAT.matchEntire(this)?.destructured ?: return false

    return state.toInt() in GSTIN_STATES && pan.isPan() && hasGstinCheckCharacter()
}

private fun String.hasGstinCheckCharacter(): Boolean {
    val sum = reversed()
        .map { char -> char.digitToInt(ALPHANUMERIC_RADIX) }
        .mapIndexed { index, value -> if (index % 2 == 0) value else value.doubledInBase36() }
        .sum()

    return sum % ALPHANUMERIC_RADIX == 0
}

private fun Int.doubledInBase36(): Int = (this * 2).let { doubled ->
    doubled / ALPHANUMERIC_RADIX + doubled % ALPHANUMERIC_RADIX
}
