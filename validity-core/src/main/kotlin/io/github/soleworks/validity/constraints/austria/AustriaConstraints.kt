package io.github.soleworks.validity.constraints.austria

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.messages

private const val MODULUS = 10
private const val DOUBLE_DIGIT_LIMIT = 9
private const val DOUBLING = 2

private val ABGABENKONTONUMMER_FORMAT = Regex("\\d{9}")
private val SYMBOLS = Regex("[-\\\\/!@#\$%^&*()+=\\[\\]]+")

public fun ValidationNode<String>.abgabenkontonummer(
    message: String = messages.austria.abgabenkontonummer
): Unit = constraint(
    message = message,
    code = "abgabenkontonummer",
    predicate = {
        it.replace(SYMBOLS, "").let { number -> ABGABENKONTONUMMER_FORMAT.matches(number) && number.isLuhnValid() }
    }
)

private fun String.isLuhnValid(): Boolean = reversed().map(Char::digitToInt).withIndex().sumOf { (index, digit) ->
    if (index % DOUBLING == 1) (digit * DOUBLING).let { if (it > DOUBLE_DIGIT_LIMIT) it - DOUBLE_DIGIT_LIMIT else it } else digit
} % MODULUS == 0
