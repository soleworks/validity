package io.github.soleworks.validity.constraints.taiwan

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.messages

private const val MODULUS = 10
private const val WEIGHT_BASE = 9
private const val LAST_WEIGHTED_INDEX = 8

private val NATIONAL_ID_FORMAT = Regex("[A-Z]\\d{9}")
private val LETTER_CODES = mapOf(
    'A' to 10, 'B' to 11, 'C' to 12, 'D' to 13, 'E' to 14, 'F' to 15, 'G' to 16, 'H' to 17, 'I' to 34,
    'J' to 18, 'K' to 19, 'L' to 20, 'M' to 21, 'N' to 22, 'O' to 35, 'P' to 23, 'Q' to 24, 'R' to 25,
    'S' to 26, 'T' to 27, 'U' to 28, 'V' to 29, 'W' to 32, 'X' to 30, 'Y' to 31, 'Z' to 33
)

public fun ValidationNode<String>.nationalId(
    message: String = messages.taiwan.nationalId
): Unit = constraint(
    message = message,
    code = "nationalId",
    predicate = { it.trim().uppercase().let { value -> NATIONAL_ID_FORMAT.matches(value) && value.isNationalId() } }
)

private fun String.isNationalId(): Boolean {
    val code = LETTER_CODES.getValue(first())
    val digits = (1..LAST_WEIGHTED_INDEX).sumOf { this[it].digitToInt() * (WEIGHT_BASE - it) }
    val sum = code % MODULUS * WEIGHT_BASE + code / MODULUS + digits

    return (MODULUS - sum % MODULUS - last().digitToInt()) % MODULUS == 0
}
