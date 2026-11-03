package io.github.soleworks.validity.constraints.paraguay

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.constraints.digits
import io.github.soleworks.validity.messages

private const val MODULUS = 11
private const val DECIMAL_MODULUS = 10
private const val FIRST_WEIGHT = 2

private val RUC_FORMAT = Regex("""\d{5,8}-?\d""")
private val CI_FORMAT = Regex("""\d{5,10}""")

internal val PARAGUAY_TAX_IDS: Map<String, (String) -> Boolean> = mapOf(
    "RUC" to String::isRuc,
    "CI" to String::isCi
)

/**
 * Checks that the string is a Paraguayan RUC with a valid check digit, as 6 to 9 digits or as `80028061-0`.
 *
 * Fails with code `ruc` and, by default, the message `must be a valid RUC`
 * ([ParaguayMessages.ruc][io.github.soleworks.validity.ParaguayMessages.ruc]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.ruc(
    message: String = messages.paraguay.ruc
): Unit = constraint(
    message = message,
    code = "ruc",
    predicate = String::isRuc
)

/**
 * Checks that the string is a Paraguayan CI (cédula de identidad): 5 to 10 digits.
 *
 * The CI has no check digit, so only the format is checked.
 *
 * Fails with code `ci` and, by default, the message `must be a valid CI`
 * ([ParaguayMessages.ci][io.github.soleworks.validity.ParaguayMessages.ci]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.ci(
    message: String = messages.paraguay.ci
): Unit = constraint(
    message = message,
    code = "ci",
    predicate = String::isCi
)

private fun String.isRuc(): Boolean {
    if (!RUC_FORMAT.matches(this))
        return false

    val digits = digits()
    val sum = digits
        .dropLast(1)
        .reversed()
        .mapIndexed { index, digit -> digit * (index + FIRST_WEIGHT) }
        .sum()

    return digits.last() == (-sum).mod(MODULUS) % DECIMAL_MODULUS
}

private fun String.isCi(): Boolean = CI_FORMAT.matches(this)
