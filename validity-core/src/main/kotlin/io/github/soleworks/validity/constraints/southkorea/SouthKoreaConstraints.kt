package io.github.soleworks.validity.constraints.southkorea

import io.github.soleworks.validity.SouthKoreaMessages
import io.github.soleworks.validity.ValidationNode

private const val MODULUS = 11
private const val CHECK_DIGIT_MODULUS = 10
private const val GENDER_INDEX = 6
private const val MONTH_START = 2
private const val DAY_START = 4
private const val DAY_END = 6
private const val MAX_SHORT_MONTH_DAY = 30
private const val FEBRUARY = 2
private const val MAX_FEBRUARY_DAY = 29

private val RRN_FORMAT = Regex("\\d{13}")
private val GENDER_DIGITS = 1..8
private val MONTHS = 1..12
private val DAYS = 1..31
private val SHORT_MONTHS = setOf(4, 6, 9, 11)
private val WEIGHTS = listOf(5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2).reversed()

public fun ValidationNode<String>.rrn(
    message: String = SouthKoreaMessages.RRN
): Unit = constraint(
    message = message,
    predicate = { it.replace("-", "").let { value -> RRN_FORMAT.matches(value) && value.isRrn() } }
)

private fun String.isRrn(): Boolean {
    val month = substring(MONTH_START, DAY_START).toInt()
    val day = substring(DAY_START, DAY_END).toInt()
    val sum = take(WEIGHTS.size).map(Char::digitToInt).zip(WEIGHTS) { digit, weight -> digit * weight }.sum()

    return isValidDate(month, day) &&
        this[GENDER_INDEX].digitToInt() in GENDER_DIGITS &&
        (MODULUS - sum % MODULUS) % CHECK_DIGIT_MODULUS == last().digitToInt()
}

private fun isValidDate(month: Int, day: Int): Boolean = month in MONTHS &&
    day in DAYS &&
    (day <= MAX_SHORT_MONTH_DAY || month !in SHORT_MONTHS) &&
    (day <= MAX_FEBRUARY_DAY || month != FEBRUARY)
