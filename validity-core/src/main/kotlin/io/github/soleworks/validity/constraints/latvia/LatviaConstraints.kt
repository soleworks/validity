package io.github.soleworks.validity.constraints.latvia

import io.github.soleworks.validity.LatviaMessages
import io.github.soleworks.validity.ValidationNode
import java.time.LocalDate

private const val MODULUS = 11
private const val DECIMAL_MODULUS = 10
private const val CHECKSUM_SEED = 1101
private const val NEW_FORMAT_DAY = "32"
private const val UNKNOWN_MONTH = "00"
private const val NINETEENTH_CENTURY = 1800
private const val TWENTIETH_CENTURY = 1900
private const val TWENTY_FIRST_CENTURY = 2000

private val PERSONAS_KODS_FORMAT = Regex("(\\d{2})(\\d{2})(\\d{2})-?(\\d)\\d{4}")
private val PERSONAS_KODS_WEIGHTS = listOf(1, 6, 3, 7, 9, 10, 5, 8, 4, 2)

public fun ValidationNode<String>.personasKods(
    message: String = LatviaMessages.PERSONAS_KODS
): Unit = constraint(
    message = message,
    predicate = { it.isPersonasKods() }
)

private fun isDate(year: Int, month: Int, day: Int): Boolean = runCatching { LocalDate.of(year, month, day) }.isSuccess

private fun String.isPersonasKods(): Boolean {
    val (day, month, year, centuryDigit) = PERSONAS_KODS_FORMAT.matchEntire(this)?.destructured ?: return false

    if (day == NEW_FORMAT_DAY) return true

    val century = when (centuryDigit.toInt()) {
        0 -> NINETEENTH_CENTURY
        1 -> TWENTIETH_CENTURY
        else -> TWENTY_FIRST_CENTURY
    }
    val digits = filter(Char::isDigit).map(Char::digitToInt)
    val checkDigit = (CHECKSUM_SEED - digits.zip(PERSONAS_KODS_WEIGHTS) { digit, weight -> digit * weight }.sum()) %
        MODULUS % DECIMAL_MODULUS

    return (month == UNKNOWN_MONTH || isDate(century + year.toInt(), month.toInt(), day.toInt())) &&
        digits.last() == checkDigit
}
