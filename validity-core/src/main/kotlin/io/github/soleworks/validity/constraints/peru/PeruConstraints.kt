package io.github.soleworks.validity.constraints.peru

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.constraints.digits
import io.github.soleworks.validity.constraints.weightedSum
import io.github.soleworks.validity.messages

private const val MODULUS = 11
private const val DECIMAL_MODULUS = 10
private const val PREFIX_LENGTH = 2
private const val DNI_LENGTH = 8
private const val DNI_CHECK_DIGITS = "65432110987"
private const val DNI_CHECK_LETTERS = "KJIHGFEDCBA"

private val RUC_FORMAT = Regex("""\d{11}""")
private val DNI_FORMAT = Regex("""\d{8}(-?[\dA-K])?""")
private val RUC_WEIGHTS = listOf(5, 4, 3, 2, 7, 6, 5, 4, 3, 2)
private val DNI_WEIGHTS = listOf(3, 2, 7, 6, 5, 4, 3, 2)
private val RUC_PREFIXES = setOf("10", "15", "17", "20")

internal val PERU_TAX_IDS: Map<String, (String) -> Boolean> = mapOf(
    "RUC" to String::isRuc,
    "DNI" to String::isDni
)

/**
 * Checks that the string is a Peruvian RUC: 11 digits with a valid check digit, such as `20131312955`.
 *
 * The first two digits must be `10`, `15`, `17` or `20`.
 *
 * Fails with code `ruc` and, by default, the message `must be a valid RUC`
 * ([PeruMessages.ruc][io.github.soleworks.validity.PeruMessages.ruc]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.ruc(
    message: String = messages.peru.ruc
): Unit = constraint(
    message = message,
    code = "ruc",
    predicate = String::isRuc
)

/**
 * Checks that the string is a Peruvian DNI: 8 digits, optionally followed by its check character, such as `10117410`
 * or `10117410-2`.
 *
 * The check character is a digit or a letter from `A` to `K`, and it must match the 8 digits when it is there.
 *
 * Fails with code `dni` and, by default, the message `must be a valid DNI`
 * ([PeruMessages.dni][io.github.soleworks.validity.PeruMessages.dni]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.dni(
    message: String = messages.peru.dni
): Unit = constraint(
    message = message,
    code = "dni",
    predicate = String::isDni
)

private fun String.isRuc(): Boolean {
    if (!RUC_FORMAT.matches(this) || take(PREFIX_LENGTH) !in RUC_PREFIXES)
        return false

    val digits = digits()

    return digits.last() == (MODULUS - digits.weightedSum(RUC_WEIGHTS) % MODULUS) % DECIMAL_MODULUS
}

private fun String.isDni(): Boolean {
    if (!DNI_FORMAT.matches(this))
        return false

    if (length == DNI_LENGTH)
        return true

    val remainder = take(DNI_LENGTH).digits().weightedSum(DNI_WEIGHTS) % MODULUS

    return last() == DNI_CHECK_DIGITS[remainder] || last() == DNI_CHECK_LETTERS[remainder]
}
