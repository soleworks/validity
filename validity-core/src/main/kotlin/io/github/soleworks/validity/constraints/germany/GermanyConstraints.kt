package io.github.soleworks.validity.constraints.germany

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.messages

private const val CHECK_MODULUS = 11
private const val DIGIT_MODULUS = 10
private const val INITIAL_PRODUCT = 10
private const val DOUBLING = 2
private const val BODY_LENGTH = 10
private const val TRIPLICATE_SIZE = 3
private const val ADJACENT_PAIRS_IN_ROW = 2
private val REPEAT_GROUP_COUNTS = 2..3

private val STEUER_ID_FORMAT = Regex("[1-9]\\d{10}")
private val SEPARATORS = Regex("[/\\\\]")

public fun ValidationNode<String>.steuerId(
    message: String = messages.germany.steuerId
): Unit = constraint(
    message = message,
    predicate = {
        it.replace(SEPARATORS, "").let { number ->
            STEUER_ID_FORMAT.matches(number) && number.map(Char::digitToInt).isSteuerId()
        }
    }
)

private fun List<Int>.repeatedDigitGroups(): List<List<Int>> = take(BODY_LENGTH)
    .map { digit -> indices.take(BODY_LENGTH).filter { this[it] == digit } }
    .filter { it.size > 1 }

private fun List<Int>.hasValidRepetition(): Boolean {
    val groups = repeatedDigitGroups()

    if (groups.size !in REPEAT_GROUP_COUNTS) return false

    val first = groups.first()

    return first.size != TRIPLICATE_SIZE || first.zipWithNext().count { (a, b) -> a + 1 == b } != ADJACENT_PAIRS_IN_ROW
}

private fun List<Int>.isSteuerId(): Boolean {
    val product = take(BODY_LENGTH).fold(INITIAL_PRODUCT) { acc, digit ->
        val sum = (digit + acc) % DIGIT_MODULUS

        (if (sum == 0) DIGIT_MODULUS else sum) * DOUBLING % CHECK_MODULUS
    }
    val checkDigit = if (product == 1) 0 else CHECK_MODULUS - product

    return hasValidRepetition() && checkDigit == last()
}
