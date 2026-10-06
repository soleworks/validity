package io.github.soleworks.validity.constraints.slovenia

import io.github.soleworks.validity.SloveniaMessages
import io.github.soleworks.validity.ValidationNode

private const val MODULUS = 11
private const val INVALID_CHECK_DIGIT = 10

private val DAVCNA_STEVILKA_FORMAT = Regex("[1-9]\\d{7}")
private val DAVCNA_STEVILKA_WEIGHTS = listOf(8, 7, 6, 5, 4, 3, 2)

public fun ValidationNode<String>.davcnaStevilka(
    message: String = SloveniaMessages.DAVCNA_STEVILKA
): Unit = constraint(
    message = message,
    predicate = { DAVCNA_STEVILKA_FORMAT.matches(it) && it.isDavcnaStevilka() }
)

private fun String.isDavcnaStevilka(): Boolean {
    val digits = map(Char::digitToInt)
    val checkDigit = MODULUS - digits.zip(DAVCNA_STEVILKA_WEIGHTS) { digit, weight -> digit * weight }.sum() % MODULUS

    return digits.last() == if (checkDigit == INVALID_CHECK_DIGIT) 0 else checkDigit
}
