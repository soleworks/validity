package io.github.soleworks.validity.constraints.greece

import io.github.soleworks.validity.GreeceMessages
import io.github.soleworks.validity.ValidationNode

private const val MODULUS = 11
private const val DECIMAL_MODULUS = 10

private val AFM_FORMAT = Regex("[0-47-9]\\d{8}")
private val AFM_WEIGHTS = listOf(256, 128, 64, 32, 16, 8, 4, 2)

public fun ValidationNode<String>.afm(
    message: String = GreeceMessages.AFM
): Unit = constraint(
    message = message,
    predicate = { AFM_FORMAT.matches(it) && it.isAfm() }
)

private fun String.isAfm(): Boolean {
    val digits = map(Char::digitToInt)

    return digits.last() == digits.zip(AFM_WEIGHTS) { digit, weight -> digit * weight }.sum() % MODULUS % DECIMAL_MODULUS
}
