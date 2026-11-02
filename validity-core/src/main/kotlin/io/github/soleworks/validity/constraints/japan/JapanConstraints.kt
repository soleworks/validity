package io.github.soleworks.validity.constraints.japan

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.constraints.digits
import io.github.soleworks.validity.constraints.weightedSum
import io.github.soleworks.validity.messages

private const val CORPORATE_NUMBER_MODULUS = 9
private const val MY_NUMBER_MODULUS = 11
private const val DECIMAL_MODULUS = 10

private val CORPORATE_NUMBER_FORMAT = Regex("""\d{13}""")
private val MY_NUMBER_FORMAT = Regex("""\d{12}|\d{4} \d{4} \d{4}""")
private val CORPORATE_NUMBER_WEIGHTS = listOf(1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2)
private val MY_NUMBER_WEIGHTS = listOf(6, 5, 4, 3, 2, 7, 6, 5, 4, 3, 2)

internal val JAPAN_TAX_IDS: Map<String, (String) -> Boolean> = mapOf(
    "CORPORATE_NUMBER" to String::isCorporateNumber,
    "MY_NUMBER" to String::isMyNumber
)

/**
 * Checks that the string is a Japanese corporate number (法人番号): 13 digits whose first digit is a valid check digit,
 * such as `5835678256246`.
 *
 * Fails with code `corporateNumber` and, by default, the message `must be a valid corporate number`
 * ([JapanMessages.corporateNumber][io.github.soleworks.validity.JapanMessages.corporateNumber]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.corporateNumber(
    message: String = messages.japan.corporateNumber
): Unit = constraint(
    message = message,
    code = "corporateNumber",
    predicate = String::isCorporateNumber
)

/**
 * Checks that the string is a Japanese My Number (個人番号): 12 digits with a valid check digit, as `123456789018` or
 * `1234 5678 9018`.
 *
 * Fails with code `myNumber` and, by default, the message `must be a valid My Number`
 * ([JapanMessages.myNumber][io.github.soleworks.validity.JapanMessages.myNumber]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.myNumber(
    message: String = messages.japan.myNumber
): Unit = constraint(
    message = message,
    code = "myNumber",
    predicate = String::isMyNumber
)

private fun String.isCorporateNumber(): Boolean {
    if (!CORPORATE_NUMBER_FORMAT.matches(this))
        return false

    val digits = digits()
    val sum = digits.drop(1).reversed().weightedSum(CORPORATE_NUMBER_WEIGHTS)

    return digits.first() == CORPORATE_NUMBER_MODULUS - sum % CORPORATE_NUMBER_MODULUS
}

private fun String.isMyNumber(): Boolean {
    if (!MY_NUMBER_FORMAT.matches(this))
        return false

    val digits = digits()

    return digits.last() == (-digits.weightedSum(MY_NUMBER_WEIGHTS)).mod(MY_NUMBER_MODULUS) % DECIMAL_MODULUS
}
