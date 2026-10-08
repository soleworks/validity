package io.github.soleworks.validity.constraints.bulgaria

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.messages
import java.time.LocalDate

private const val MODULUS = 11
private const val INVALID_REMAINDER = 10
private const val TWENTY_FIRST_CENTURY_MONTH_OFFSET = 40
private const val NINETEENTH_CENTURY_MONTH_OFFSET = 20
private const val NINETEENTH_CENTURY = 1800
private const val TWENTIETH_CENTURY = 1900
private const val TWENTY_FIRST_CENTURY = 2000

private val EGN_FORMAT = Regex("(\\d{2})(\\d{2})(\\d{2})\\d{4}")
private val EGN_WEIGHTS = listOf(2, 4, 8, 5, 10, 9, 7, 3, 6)

public fun ValidationNode<String>.egn(
    message: String = messages.bulgaria.egn
): Unit = constraint(
    message = message,
    predicate = { it.isEgn() }
)

private fun isDate(year: Int, month: Int, day: Int): Boolean = runCatching { LocalDate.of(year, month, day) }.isSuccess

private fun String.isEgn(): Boolean {
    val (year, rawMonth, day) = EGN_FORMAT.matchEntire(this)?.destructured?.toList()?.map(String::toInt) ?: return false
    val (century, month) = when {
        rawMonth > TWENTY_FIRST_CENTURY_MONTH_OFFSET -> TWENTY_FIRST_CENTURY to rawMonth - TWENTY_FIRST_CENTURY_MONTH_OFFSET
        rawMonth > NINETEENTH_CENTURY_MONTH_OFFSET -> NINETEENTH_CENTURY to rawMonth - NINETEENTH_CENTURY_MONTH_OFFSET
        else -> TWENTIETH_CENTURY to rawMonth
    }
    val digits = map(Char::digitToInt)
    val remainder = digits.zip(EGN_WEIGHTS) { digit, weight -> digit * weight }.sum() % MODULUS
    val checkDigit = if (remainder == INVALID_REMAINDER) 0 else remainder

    return isDate(century + year, month, day) && digits.last() == checkDigit
}
