package io.github.soleworks.validity.constraints.thailand

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.constraints.digits
import io.github.soleworks.validity.constraints.weightedSum
import io.github.soleworks.validity.messages

private const val MODULUS = 11
private const val DECIMAL_MODULUS = 10

private val NATIONAL_ID_FORMAT = Regex("""[1-8]\d{12}""")
private val NATIONAL_ID_WEIGHTS = (13 downTo 2).toList()

/**
 * Checks that the string is a Thai national ID number: 13 digits with a valid check digit, with no dashes.
 *
 * Fails with code `nationalId` and, by default, the message `must be a valid national identification number`
 * ([ThailandMessages.nationalId][io.github.soleworks.validity.ThailandMessages.nationalId]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.nationalId(
    message: String = messages.thailand.nationalId
): Unit = constraint(
    message = message,
    code = "nationalId",
    predicate = { NATIONAL_ID_FORMAT.matches(it) && it.digits().isNationalId() }
)

private fun List<Int>.isNationalId(): Boolean =
    last() == (MODULUS - weightedSum(NATIONAL_ID_WEIGHTS) % MODULUS) % DECIMAL_MODULUS
