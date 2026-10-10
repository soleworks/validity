package io.github.soleworks.validity.constraints.thailand

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.messages

private const val MODULUS = 11
private const val DIGIT_MODULUS = 10
private const val FIRST_WEIGHT = 13
private const val WEIGHTED_LENGTH = 12

private val NATIONAL_ID_FORMAT = Regex("[1-8]\\d{12}")

public fun ValidationNode<String>.nationalId(
    message: String = messages.thailand.nationalId
): Unit = constraint(
    message = message,
    code = "nationalId",
    predicate = { NATIONAL_ID_FORMAT.matches(it) && it.isNationalId() }
)

private fun String.isNationalId(): Boolean {
    val sum = take(WEIGHTED_LENGTH).withIndex().sumOf { (index, char) -> char.digitToInt() * (FIRST_WEIGHT - index) }

    return (MODULUS - sum % MODULUS) % DIGIT_MODULUS == last().digitToInt()
}
