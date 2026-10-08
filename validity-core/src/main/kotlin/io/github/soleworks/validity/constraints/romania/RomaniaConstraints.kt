package io.github.soleworks.validity.constraints.romania

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.messages
import java.time.LocalDate

private const val MODULUS = 11
private const val UNCHECKED_PREFIX = "9000"
private const val INVALID_REMAINDER = 10
private const val NINETEENTH_CENTURY = 1800
private const val TWENTIETH_CENTURY = 1900
private const val TWENTY_FIRST_CENTURY = 2000

private val CNP_FORMAT = Regex("(\\d)(\\d{2})(\\d{2})(\\d{2})\\d{6}")
private val CNP_WEIGHTS = listOf(2, 7, 9, 1, 4, 6, 3, 5, 8, 2, 7, 9)
private val TWENTIETH_CENTURY_DIGITS = setOf('1', '2')
private val NINETEENTH_CENTURY_DIGITS = setOf('3', '4')

public fun ValidationNode<String>.cnp(
    message: String = messages.romania.cnp
): Unit = constraint(
    message = message,
    predicate = { CNP_FORMAT.matches(it) && (it.startsWith(UNCHECKED_PREFIX) || it.isCnp()) }
)

private fun isDate(year: Int, month: Int, day: Int): Boolean = runCatching { LocalDate.of(year, month, day) }.isSuccess

private fun String.isCnp(): Boolean {
    val (_, yearPart, monthPart, dayPart) = CNP_FORMAT.matchEntire(this)?.destructured ?: return false
    val century = when (first()) {
        in TWENTIETH_CENTURY_DIGITS -> TWENTIETH_CENTURY
        in NINETEENTH_CENTURY_DIGITS -> NINETEENTH_CENTURY
        else -> TWENTY_FIRST_CENTURY
    }
    val digits = map(Char::digitToInt)
    val remainder = digits.zip(CNP_WEIGHTS) { digit, weight -> digit * weight }.sum() % MODULUS
    val checkDigit = if (remainder == INVALID_REMAINDER) 1 else remainder

    return isDate(century + yearPart.toInt(), monthPart.toInt(), dayPart.toInt()) && digits.last() == checkDigit
}
