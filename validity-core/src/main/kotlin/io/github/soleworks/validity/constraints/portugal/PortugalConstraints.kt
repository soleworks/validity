package io.github.soleworks.validity.constraints.portugal

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.constraints.digits
import io.github.soleworks.validity.constraints.weightedSum
import io.github.soleworks.validity.messages

private const val MODULUS = 11
private const val DECIMAL_MODULUS = 10
private const val NON_RESIDENT_PREFIX = "45"

private val NIF_FORMAT = Regex("""[1-9]\d{8}""")
private val NIF_WEIGHTS = (9 downTo 2).toList()
private val PERSON_PREFIXES = setOf('1', '2', '3')
private val ENTITY_PREFIXES = setOf('5', '6', '7', '8', '9')

internal val PORTUGAL_TAX_IDS: Map<String, (String) -> Boolean> = mapOf(
    "NIF" to String::isNif,
    "NIPC" to String::isNipc
)

/**
 * Checks that the string is the NIF of a person in Portugal: 9 digits with a valid check digit, such as `123456789`.
 *
 * The first digit must be `1`, `2` or `3`, or the first two `45` (a person who does not live in Portugal).
 *
 * Fails with code `nif` and, by default, the message `must be a valid NIF`
 * ([PortugalMessages.nif][io.github.soleworks.validity.PortugalMessages.nif]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.nif(
    message: String = messages.portugal.nif
): Unit = constraint(
    message = message,
    code = "nif",
    predicate = String::isNif
)

/**
 * Checks that the string is the NIPC of a company or other entity in Portugal: 9 digits with a valid check digit, such
 * as `503504564`.
 *
 * The first digit must be `5`, `6`, `7`, `8` or `9`.
 *
 * Fails with code `nipc` and, by default, the message `must be a valid NIPC`
 * ([PortugalMessages.nipc][io.github.soleworks.validity.PortugalMessages.nipc]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.nipc(
    message: String = messages.portugal.nipc
): Unit = constraint(
    message = message,
    code = "nipc",
    predicate = String::isNipc
)

private fun String.isNif(): Boolean =
    (first() in PERSON_PREFIXES || startsWith(NON_RESIDENT_PREFIX)) && hasNifCheckDigit()

private fun String.isNipc(): Boolean = hasNifCheckDigit() && first() in ENTITY_PREFIXES

private fun String.hasNifCheckDigit(): Boolean {
    if (!NIF_FORMAT.matches(this))
        return false

    val digits = digits()
    val checkDigit = (MODULUS - digits.weightedSum(NIF_WEIGHTS) % MODULUS) % MODULUS % DECIMAL_MODULUS

    return digits.last() == checkDigit
}
