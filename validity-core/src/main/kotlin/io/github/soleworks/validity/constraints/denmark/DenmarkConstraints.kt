package io.github.soleworks.validity.constraints.denmark

import io.github.soleworks.validity.DenmarkMessages
import io.github.soleworks.validity.ValidationNode
import java.time.LocalDate

private const val MODULUS = 11
private const val NINETEENTH_CENTURY = 1800
private const val TWENTIETH_CENTURY = 1900
private const val TWENTY_FIRST_CENTURY = 2000
private const val NINETEEN_HUNDRED_CUTOFF = 37
private const val EIGHTEEN_HUNDRED_CUTOFF = 58
private const val CENTURY_DIGIT_INDEX = 6
private const val INVALID_REMAINDER = 1
private const val BODY_LENGTH = 9
private const val CHECK_DIGIT_INDEX = 9
private const val SEPARATOR = "-"

private val CPR_FORMAT = Regex("\\d{6}-?\\d{4}")

private val CPR_WEIGHTS = listOf(4, 3, 2, 7, 6, 5, 4, 3, 2)

public fun ValidationNode<String>.cpr(
    message: String = DenmarkMessages.CPR
): Unit = constraint(
    message = message,
    predicate = { CPR_FORMAT.matches(it) && it.replace(SEPARATOR, "").isCpr() }
)

private fun String.birthYear(): Int {
    val shortYear = substring(4, 6).toInt()

    return when (this[CENTURY_DIGIT_INDEX]) {
        '0', '1', '2', '3' -> TWENTIETH_CENTURY + shortYear
        '4', '9' -> if (shortYear < NINETEEN_HUNDRED_CUTOFF) TWENTY_FIRST_CENTURY + shortYear else TWENTIETH_CENTURY + shortYear
        else -> if (shortYear < EIGHTEEN_HUNDRED_CUTOFF) TWENTY_FIRST_CENTURY + shortYear else NINETEENTH_CENTURY + shortYear
    }
}

private fun String.hasValidBirthDate(): Boolean =
    runCatching { LocalDate.of(birthYear(), substring(2, 4).toInt(), take(2).toInt()) }.isSuccess

private fun String.isCpr(): Boolean {
    val digits = map(Char::digitToInt)
    val remainder = digits.take(BODY_LENGTH).zip(CPR_WEIGHTS) { digit, weight -> digit * weight }.sum() % MODULUS

    return hasValidBirthDate() &&
        remainder != INVALID_REMAINDER &&
        digits[CHECK_DIGIT_INDEX] == if (remainder == 0) 0 else MODULUS - remainder
}
