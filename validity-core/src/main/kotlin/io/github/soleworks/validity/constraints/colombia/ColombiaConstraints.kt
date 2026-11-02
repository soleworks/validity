package io.github.soleworks.validity.constraints.colombia

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.constraints.digits
import io.github.soleworks.validity.constraints.weightedSum
import io.github.soleworks.validity.messages

private const val MODULUS = 11
private const val CHECK_DIGITS = "01987654321"

private val NIT_FORMAT = Regex("""\d{7,15}-?\d|\d{1,3}(\.\d{3}){2,4}-\d""")
private val CC_FORMAT = Regex("""\d{6,10}""")
private val NIT_WEIGHTS = listOf(3, 7, 13, 17, 19, 23, 29, 37, 41, 43, 47, 53, 59, 67, 71)

internal val COLOMBIA_TAX_IDS: Map<String, (String) -> Boolean> = mapOf(
    "NIT" to String::isNit,
    "CC" to String::isCc
)

/**
 * Checks that the string is a Colombian NIT with a valid check digit, as 8 to 16 digits or as `900.123.456-8`.
 *
 * The check digit is the last one, and the dash before it is optional when there are no dots.
 *
 * Fails with code `nit` and, by default, the message `must be a valid NIT`
 * ([ColombiaMessages.nit][io.github.soleworks.validity.ColombiaMessages.nit]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.nit(
    message: String = messages.colombia.nit
): Unit = constraint(
    message = message,
    code = "nit",
    predicate = String::isNit
)

/**
 * Checks that the string is a Colombian CC (cédula de ciudadanía): 6 to 10 digits.
 *
 * The CC has no check digit, so only the format is checked.
 *
 * Fails with code `cc` and, by default, the message `must be a valid CC`
 * ([ColombiaMessages.cc][io.github.soleworks.validity.ColombiaMessages.cc]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.cc(
    message: String = messages.colombia.cc
): Unit = constraint(
    message = message,
    code = "cc",
    predicate = String::isCc
)

private fun String.isNit(): Boolean {
    if (!NIT_FORMAT.matches(this))
        return false

    val digits = digits()
    val sum = digits.dropLast(1).reversed().weightedSum(NIT_WEIGHTS)

    return last() == CHECK_DIGITS[sum % MODULUS]
}

private fun String.isCc(): Boolean = CC_FORMAT.matches(this)
