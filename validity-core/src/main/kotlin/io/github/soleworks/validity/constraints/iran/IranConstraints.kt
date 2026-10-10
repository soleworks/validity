package io.github.soleworks.validity.constraints.iran

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.messages

private const val MODULUS = 11
private const val FIRST_WEIGHT = 10
private const val WEIGHTED_LENGTH = 9
private const val SERIAL_START = 3
private const val SERIAL_END = 9
private const val SMALL_REMAINDER = 2

private val CODE_FORMAT = Regex("\\d{10}")

public fun ValidationNode<String>.codeMelli(
    message: String = messages.iran.codeMelli
): Unit = constraint(
    message = message,
    code = "codeMelli",
    predicate = { CODE_FORMAT.matches(it) && it.isCodeMelli() }
)

private fun String.isCodeMelli(): Boolean {
    val remainder = take(WEIGHTED_LENGTH).withIndex().sumOf { (index, char) -> char.digitToInt() * (FIRST_WEIGHT - index) } % MODULUS
    val checkDigit = last().digitToInt()

    return substring(SERIAL_START, SERIAL_END).toInt() != 0 &&
        if (remainder < SMALL_REMAINDER) checkDigit == remainder else checkDigit == MODULUS - remainder
}
