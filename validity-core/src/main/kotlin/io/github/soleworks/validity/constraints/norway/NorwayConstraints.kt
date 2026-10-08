package io.github.soleworks.validity.constraints.norway

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.messages

private const val MODULUS = 11
private const val EMPTY_NUMBER = "00000000000"
private const val FIRST_CHECK_INDEX = 9
private const val SECOND_CHECK_INDEX = 10
private const val SECOND_CHECK_FIRST_WEIGHT = 2

private val FODSELSNUMMER_FORMAT = Regex("\\d{11}")

private val FIRST_WEIGHTS = listOf(3, 7, 6, 1, 8, 9, 4, 5, 2)
private val SECOND_WEIGHTS = listOf(5, 4, 3, 2, 7, 6, 5, 4, 3)

public fun ValidationNode<String>.fodselsnummer(
    message: String = messages.norway.fodselsnummer
): Unit = constraint(
    message = message,
    predicate = {
        it.trim().let { number ->
            FODSELSNUMMER_FORMAT.matches(number) && number != EMPTY_NUMBER && number.map(Char::digitToInt).isFodselsnummer()
        }
    }
)

private fun List<Int>.checkDigit(weights: List<Int>, extra: Int): Int =
    (MODULUS - (zip(weights) { digit, weight -> digit * weight }.sum() + extra) % MODULUS) % MODULUS

private fun List<Int>.isFodselsnummer(): Boolean {
    val first = checkDigit(FIRST_WEIGHTS, 0)
    val second = checkDigit(SECOND_WEIGHTS, SECOND_CHECK_FIRST_WEIGHT * first)

    return first == this[FIRST_CHECK_INDEX] && second == this[SECOND_CHECK_INDEX]
}
