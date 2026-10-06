package io.github.soleworks.validity.constraints.ukraine

import io.github.soleworks.validity.UkraineMessages
import io.github.soleworks.validity.ValidationNode

private const val MODULUS = 11
private const val INVALID_REMAINDER = 10

private val RNOKPP_FORMAT = Regex("\\d{10}")
private val RNOKPP_WEIGHTS = listOf(-1, 5, 7, 9, 4, 6, 10, 5, 7)

public fun ValidationNode<String>.rnokpp(
    message: String = UkraineMessages.RNOKPP
): Unit = constraint(
    message = message,
    predicate = { RNOKPP_FORMAT.matches(it) && it.isRnokpp() }
)

private fun String.isRnokpp(): Boolean {
    val digits = map(Char::digitToInt)
    val remainder = digits.zip(RNOKPP_WEIGHTS) { digit, weight -> digit * weight }.sum() % MODULUS

    return digits.last() == if (remainder == INVALID_REMAINDER) 0 else remainder
}
