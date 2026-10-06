package io.github.soleworks.validity.constraints.portugal

import io.github.soleworks.validity.PortugalMessages
import io.github.soleworks.validity.ValidationNode

private const val MODULUS = 11
private const val MAX_DIGIT = 9

private val NIF_FORMAT = Regex("\\d{9}")

private val NIF_WEIGHTS = (9 downTo 2).toList()

public fun ValidationNode<String>.nif(
    message: String = PortugalMessages.NIF
): Unit = constraint(
    message = message,
    predicate = { NIF_FORMAT.matches(it) && it.map(Char::digitToInt).isNif() }
)

private fun List<Int>.isNif(): Boolean {
    val checksum = MODULUS - take(NIF_WEIGHTS.size).zip(NIF_WEIGHTS) { digit, weight -> digit * weight }.sum() % MODULUS

    return last() == if (checksum > MAX_DIGIT) 0 else checksum
}
