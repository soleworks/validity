package io.github.soleworks.validity.constraints.slovenia

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.constraints.digits
import io.github.soleworks.validity.constraints.weightedSum
import io.github.soleworks.validity.messages

private const val MODULUS = 11
private const val DECIMAL_MODULUS = 10

private val DAVCNA_STEVILKA_FORMAT = Regex("""[1-9]\d{7}""")
private val DAVCNA_STEVILKA_WEIGHTS = listOf(8, 7, 6, 5, 4, 3, 2)

/**
 * Checks that the string is a Slovenian tax number (davčna številka): 8 digits, not starting with 0, with a valid check
 * digit.
 *
 * Fails with code `davcnaStevilka` and, by default, the message `must be a valid davčna številka`
 * ([SloveniaMessages.davcnaStevilka][io.github.soleworks.validity.SloveniaMessages.davcnaStevilka]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.davcnaStevilka(
    message: String = messages.slovenia.davcnaStevilka
): Unit = constraint(
    message = message,
    code = "davcnaStevilka",
    predicate = { DAVCNA_STEVILKA_FORMAT.matches(it) && it.digits().isDavcnaStevilka() }
)

private fun List<Int>.isDavcnaStevilka(): Boolean {
    val checkDigit = MODULUS - weightedSum(DAVCNA_STEVILKA_WEIGHTS) % MODULUS

    return checkDigit <= DECIMAL_MODULUS && last() == checkDigit % DECIMAL_MODULUS
}
