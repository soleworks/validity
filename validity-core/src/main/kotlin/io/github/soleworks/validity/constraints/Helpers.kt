package io.github.soleworks.validity.constraints

import java.time.LocalDate

internal fun String.digits(): List<Int> = filter(Char::isDigit).map(Char::digitToInt)

internal fun List<Int>.weightedSum(
    weights: List<Int>
): Int = zip(weights) { digit, weight -> digit * weight }.sum()

internal fun Regex.groups(
    text: String
): List<String>? = matchEntire(text)?.run { groupValues.drop(1) }

internal fun isDate(
    year: Int,
    month: Int,
    day: Int
): Boolean = runCatching { LocalDate.of(year, month, day) }.isSuccess
