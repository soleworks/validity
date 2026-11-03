package io.github.soleworks.validity.constraints.argentina

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.constraints.digits
import io.github.soleworks.validity.constraints.weightedSum
import io.github.soleworks.validity.messages

private const val MODULUS = 11
private const val PREFIX_LENGTH = 2
private const val REMAINDER_ONE_CHECK_DIGIT = 9

private val CUIT_FORMAT = Regex("""\d{2}-?\d{8}-?\d""")
private val CUIT_WEIGHTS = listOf(5, 4, 3, 2, 7, 6, 5, 4, 3, 2)
private val CUIT_PREFIXES = setOf("20", "23", "24", "27", "30", "33", "34", "50", "51", "55")
private val CUIL_PREFIXES = setOf("20", "23", "24", "27")

internal val ARGENTINA_TAX_IDS: Map<String, (String) -> Boolean> = mapOf(
    "CUIT" to String::isCuit,
    "CUIL" to String::isCuil
)

/**
 * Checks that the string is an Argentine CUIT with a valid check digit, as 11 digits or as `20-30824628-1`.
 *
 * The first two digits must be a prefix AFIP assigns: `20`, `23`, `24`, `27`, `30`, `33`, `34`, `50`, `51` or `55`.
 *
 * Fails with code `cuit` and, by default, the message `must be a valid CUIT`
 * ([ArgentinaMessages.cuit][io.github.soleworks.validity.ArgentinaMessages.cuit]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.cuit(
    message: String = messages.argentina.cuit
): Unit = constraint(
    message = message,
    code = "cuit",
    predicate = String::isCuit
)

/**
 * Checks that the string is an Argentine CUIL with a valid check digit, as 11 digits or as `20-30824628-1`.
 *
 * The CUIL is the CUIT of a person, so the first two digits must be `20`, `23`, `24` or `27`.
 *
 * Fails with code `cuil` and, by default, the message `must be a valid CUIL`
 * ([ArgentinaMessages.cuil][io.github.soleworks.validity.ArgentinaMessages.cuil]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.cuil(
    message: String = messages.argentina.cuil
): Unit = constraint(
    message = message,
    code = "cuil",
    predicate = String::isCuil
)

private fun String.isCuit(): Boolean = hasCuitCheckDigit(CUIT_PREFIXES)

private fun String.isCuil(): Boolean = hasCuitCheckDigit(CUIL_PREFIXES)

private fun String.hasCuitCheckDigit(
    prefixes: Set<String>
): Boolean = CUIT_FORMAT.matches(this) && take(PREFIX_LENGTH) in prefixes && digits().hasCuitCheckDigit()

private fun List<Int>.hasCuitCheckDigit(): Boolean {
    val checkDigit = when (val remainder = weightedSum(CUIT_WEIGHTS) % MODULUS) {
        0 -> 0
        1 -> REMAINDER_ONE_CHECK_DIGIT
        else -> MODULUS - remainder
    }

    return last() == checkDigit
}
