package io.github.soleworks.validity.constraints.chile

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.constraints.digits
import io.github.soleworks.validity.constraints.weightedSum
import io.github.soleworks.validity.messages

private const val MODULUS = 11
private const val CHECK_CHARACTERS = "0123456789K"

private val RUT_FORMAT = Regex("""\d{7,8}-?[\dK]|\d{1,2}\.\d{3}\.\d{3}-[\dK]""")
private val RUT_WEIGHTS = listOf(9, 8, 7, 6, 5, 4, 9, 8)

internal val CHILE_TAX_IDS: Map<String, (String) -> Boolean> = mapOf(
    "RUT" to String::isRut
)

/**
 * Checks that the string is a Chilean RUT (or RUN) with a valid check character, such as `12345678-5` or
 * `12.345.678-5`.
 *
 * The check character is a digit or an uppercase `K`, and the dash before it is optional when there are no dots.
 *
 * Fails with code `rut` and, by default, the message `must be a valid RUT`
 * ([ChileMessages.rut][io.github.soleworks.validity.ChileMessages.rut]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.rut(
    message: String = messages.chile.rut
): Unit = constraint(
    message = message,
    code = "rut",
    predicate = String::isRut
)

private fun String.isRut(): Boolean {
    if (!RUT_FORMAT.matches(this))
        return false

    val sum = dropLast(1).digits().reversed().weightedSum(RUT_WEIGHTS)

    return last() == CHECK_CHARACTERS[sum % MODULUS]
}
