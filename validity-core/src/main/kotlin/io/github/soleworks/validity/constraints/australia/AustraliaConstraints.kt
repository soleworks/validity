package io.github.soleworks.validity.constraints.australia

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.constraints.digits
import io.github.soleworks.validity.constraints.weightedSum
import io.github.soleworks.validity.messages

private const val ABN_MODULUS = 89
private const val TFN_MODULUS = 11

private val ABN_FORMAT = Regex("""\d{11}|\d{2} \d{3} \d{3} \d{3}""")
private val TFN_FORMAT = Regex("""\d{8,9}|\d{3} \d{3} \d{2,3}""")
private val ABN_WEIGHTS = listOf(10, 1, 3, 5, 7, 9, 11, 13, 15, 17, 19)
private val TFN_WEIGHTS = listOf(1, 4, 3, 7, 5, 8, 6, 9, 10)

internal val AUSTRALIA_TAX_IDS: Map<String, (String) -> Boolean> = mapOf(
    "ABN" to String::isAbn,
    "TFN" to String::isTfn
)

/**
 * Checks that the string is an Australian ABN: 11 digits with valid check digits, as `51824753556` or
 * `51 824 753 556`.
 *
 * Fails with code `abn` and, by default, the message `must be a valid ABN`
 * ([AustraliaMessages.abn][io.github.soleworks.validity.AustraliaMessages.abn]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.abn(
    message: String = messages.australia.abn
): Unit = constraint(
    message = message,
    code = "abn",
    predicate = String::isAbn
)

/**
 * Checks that the string is an Australian TFN (tax file number): 8 or 9 digits with a valid check digit, as
 * `123456782` or `123 456 782`.
 *
 * Fails with code `tfn` and, by default, the message `must be a valid TFN`
 * ([AustraliaMessages.tfn][io.github.soleworks.validity.AustraliaMessages.tfn]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.tfn(
    message: String = messages.australia.tfn
): Unit = constraint(
    message = message,
    code = "tfn",
    predicate = String::isTfn
)

private fun String.isAbn(): Boolean {
    if (!ABN_FORMAT.matches(this))
        return false

    val digits = digits()
    val adjusted = listOf(digits.first() - 1) + digits.drop(1)

    return adjusted.weightedSum(ABN_WEIGHTS) % ABN_MODULUS == 0
}

private fun String.isTfn(): Boolean = TFN_FORMAT.matches(this) && digits().weightedSum(TFN_WEIGHTS) % TFN_MODULUS == 0
