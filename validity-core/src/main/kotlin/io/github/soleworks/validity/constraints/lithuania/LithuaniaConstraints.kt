package io.github.soleworks.validity.constraints.lithuania

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.messages
import java.time.LocalDate

private const val MODULUS = 11
private const val INVALID_REMAINDER = 10
private const val NINETEENTH_CENTURY = 1800
private const val TWENTIETH_CENTURY = 1900
private const val TWENTY_FIRST_CENTURY = 2000

private val FORMAT = Regex(
    "([1-6])(\\d{2})(\\d{2})(\\d{2})(?:00[1-9]|0[1-9][0-9]|[1-6][0-9]{2}|70[0-9]|710)\\d"
)
private val FIRST_WEIGHTS = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 1)
private val SECOND_WEIGHTS = listOf(3, 4, 5, 6, 7, 8, 9, 1, 2, 3)
private val NINETEENTH_CENTURY_DIGITS = setOf('1', '2')
private val TWENTIETH_CENTURY_DIGITS = setOf('3', '4')

public fun ValidationNode<String>.asmensKodas(
    message: String = messages.lithuania.asmensKodas
): Unit = constraint(
    message = message,
    code = "asmensKodas",
    predicate = { it.isPersonalCode() }
)

private fun isDate(year: Int, month: Int, day: Int): Boolean = runCatching { LocalDate.of(year, month, day) }.isSuccess

private fun List<Int>.remainder(weights: List<Int>): Int = zip(weights) { digit, weight -> digit * weight }.sum() % MODULUS

private fun String.isPersonalCode(): Boolean {
    val (_, year, month, day) = FORMAT.matchEntire(this)?.destructured ?: return false
    val century = when (first()) {
        in NINETEENTH_CENTURY_DIGITS -> NINETEENTH_CENTURY
        in TWENTIETH_CENTURY_DIGITS -> TWENTIETH_CENTURY
        else -> TWENTY_FIRST_CENTURY
    }
    val digits = map(Char::digitToInt)
    val primary = digits.remainder(FIRST_WEIGHTS)
    val checkDigit = if (primary == INVALID_REMAINDER) digits.remainder(SECOND_WEIGHTS) else primary

    return isDate(century + year.toInt(), month.toInt(), day.toInt()) &&
        digits.last() == if (checkDigit == INVALID_REMAINDER) 0 else checkDigit
}
