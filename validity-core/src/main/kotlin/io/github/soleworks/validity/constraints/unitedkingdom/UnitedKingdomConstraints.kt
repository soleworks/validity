package io.github.soleworks.validity.constraints.unitedkingdom

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.constraints.digits
import io.github.soleworks.validity.constraints.weightedSum
import io.github.soleworks.validity.messages

private const val MODULUS = 97
private const val VAT_LENGTH = 9
private const val PREFIX_LENGTH = 2
private const val BLOCK_LENGTH = 3
private const val NEW_SERIES_START = 100
private const val DECIMAL_RADIX = 10

private val VAT_FORMAT = Regex("""(GB)?\d{9}(\d{3})?|(GB ?)?\d{3} \d{4} \d{2}( \d{3})?""")
private val NINO_FORMAT = Regex("""[A-CEGHJ-PR-TW-Z][A-CEGHJ-NPR-TW-Z] ?\d{2} ?\d{2} ?\d{2} ?[A-D]""")
private val VAT_WEIGHTS = listOf(8, 7, 6, 5, 4, 3, 2, 10, 1)
private val NEW_SERIES_REMAINDERS = setOf(0, 42, 55)
private val NINO_EXCLUDED_PREFIXES = setOf("BG", "GB", "NK", "KN", "TN", "NT", "ZZ")

internal val UNITED_KINGDOM_TAX_IDS: Map<String, (String) -> Boolean> = mapOf(
    "VAT" to String::isVat,
    "NINO" to String::isNino
)

/**
 * Checks that the string is a UK VAT registration number with valid check digits, as 9 or 12 digits, with or without
 * the `GB` prefix, such as `GB980780684` or `GB 980 7806 84`.
 *
 * The 12-digit form is a branch of a company: the first 9 digits are checked and the last 3 are its branch number.
 * Numbers for government departments and health authorities (`GD` and `HA`) are not accepted.
 *
 * Fails with code `vat` and, by default, the message `must be a valid VAT number`
 * ([UnitedKingdomMessages.vat][io.github.soleworks.validity.UnitedKingdomMessages.vat]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.vat(
    message: String = messages.unitedKingdom.vat
): Unit = constraint(
    message = message,
    code = "vat",
    predicate = String::isVat
)

/**
 * Checks that the string is a UK National Insurance number: 2 letters, 6 digits and a letter from `A` to `D`, as
 * `AB123456C` or `AB 12 34 56 C`.
 *
 * Letters are uppercase. The first two letters follow HMRC's rules: `D`, `F`, `I`, `Q`, `U` and `V` are never used,
 * `O` is never the second letter, and `BG`, `GB`, `NK`, `KN`, `TN`, `NT` and `ZZ` are not assigned.
 *
 * Fails with code `nino` and, by default, the message `must be a valid National Insurance number`
 * ([UnitedKingdomMessages.nino][io.github.soleworks.validity.UnitedKingdomMessages.nino]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.nino(
    message: String = messages.unitedKingdom.nino
): Unit = constraint(
    message = message,
    code = "nino",
    predicate = String::isNino
)

private fun String.isVat(): Boolean {
    if (!VAT_FORMAT.matches(this))
        return false

    val digits = digits().take(VAT_LENGTH)
    val remainder = digits.weightedSum(VAT_WEIGHTS) % MODULUS
    val series = digits.take(BLOCK_LENGTH).fold(0) { number, digit -> number * DECIMAL_RADIX + digit }

    return if (series >= NEW_SERIES_START) remainder in NEW_SERIES_REMAINDERS else remainder == 0
}

private fun String.isNino(): Boolean = NINO_FORMAT.matches(this) && take(PREFIX_LENGTH) !in NINO_EXCLUDED_PREFIXES
