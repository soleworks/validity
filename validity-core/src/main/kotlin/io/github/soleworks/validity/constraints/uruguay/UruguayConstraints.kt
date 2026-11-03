package io.github.soleworks.validity.constraints.uruguay

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.constraints.digits
import io.github.soleworks.validity.constraints.weightedSum
import io.github.soleworks.validity.messages

private const val MODULUS = 11
private const val DECIMAL_MODULUS = 10
private const val CI_BODY_LENGTH = 7
private const val EMPTY_RUT_NUMBER = "000000"

private val RUT_FORMAT = Regex("""(\d{2})(\d{6})001\d""")
private val CI_FORMAT = Regex("""\d{6,7}-?\d|(\d\.)?\d{3}\.\d{3}-\d""")
private val RUT_WEIGHTS = listOf(4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2)
private val CI_WEIGHTS = listOf(2, 9, 8, 7, 6, 3, 4)
private val RUT_REGIONS = 1..22

internal val URUGUAY_TAX_IDS: Map<String, (String) -> Boolean> = mapOf(
    "RUT" to String::isRut,
    "CI" to String::isCi
)

/**
 * Checks that the string is a Uruguayan RUT: 12 digits with a valid check digit, such as `211003420017`.
 *
 * The first two digits are a region from `01` to `22`, the next six are not all zeros, and digits 9 to 11 are `001`.
 *
 * Fails with code `rut` and, by default, the message `must be a valid RUT`
 * ([UruguayMessages.rut][io.github.soleworks.validity.UruguayMessages.rut]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.rut(
    message: String = messages.uruguay.rut
): Unit = constraint(
    message = message,
    code = "rut",
    predicate = String::isRut
)

/**
 * Checks that the string is a Uruguayan CI (cédula de identidad) with a valid check digit, as 7 or 8 digits or as
 * `1.234.567-2`.
 *
 * Fails with code `ci` and, by default, the message `must be a valid CI`
 * ([UruguayMessages.ci][io.github.soleworks.validity.UruguayMessages.ci]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.ci(
    message: String = messages.uruguay.ci
): Unit = constraint(
    message = message,
    code = "ci",
    predicate = String::isCi
)

private fun String.isRut(): Boolean {
    val (region, number) = RUT_FORMAT.matchEntire(this)?.destructured ?: return false
    val checkDigit = (-digits().weightedSum(RUT_WEIGHTS)).mod(MODULUS)

    return region.toInt() in RUT_REGIONS && number != EMPTY_RUT_NUMBER && last().digitToInt() == checkDigit
}

private fun String.isCi(): Boolean {
    if (!CI_FORMAT.matches(this))
        return false

    val digits = digits()
    val body = List(CI_BODY_LENGTH - digits.size + 1) { 0 } + digits.dropLast(1)
    val checkDigit = (DECIMAL_MODULUS - body.weightedSum(CI_WEIGHTS) % DECIMAL_MODULUS) % DECIMAL_MODULUS

    return digits.last() == checkDigit
}
