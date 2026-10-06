package io.github.soleworks.validity.constraints.ireland

import io.github.soleworks.validity.IrelandMessages
import io.github.soleworks.validity.ValidationNode

private const val MODULUS = 23
private const val LETTER_OFFSET = 64
private const val NUMBER_LENGTH = 7
private const val SECOND_LETTER_INDEX = 8
private const val SECOND_LETTER_WEIGHT = 9
private const val NUMBER_WEIGHT_BASE = 8
private const val ZERO_REMAINDER_LETTER = 'W'

private val PPS_FORMAT = Regex("\\d{7}[A-W][A-IW]?", RegexOption.IGNORE_CASE)

public fun ValidationNode<String>.pps(
    message: String = IrelandMessages.PPS
): Unit = constraint(
    message = message,
    predicate = { PPS_FORMAT.matches(it) && it.isPps() }
)

private fun String.isPps(): Boolean {
    val numberSum = take(NUMBER_LENGTH).withIndex().sumOf { (index, char) -> char.digitToInt() * (NUMBER_WEIGHT_BASE - index) }
    val secondLetterSum = if (length > SECOND_LETTER_INDEX && this[SECOND_LETTER_INDEX] != ZERO_REMAINDER_LETTER) {
        (this[SECOND_LETTER_INDEX].code - LETTER_OFFSET) * SECOND_LETTER_WEIGHT
    } else {
        0
    }
    val remainder = (numberSum + secondLetterSum) % MODULUS
    val expected = if (remainder == 0) ZERO_REMAINDER_LETTER else (LETTER_OFFSET + remainder).toChar()

    return this[NUMBER_LENGTH].uppercaseChar() == expected
}
