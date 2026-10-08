package io.github.soleworks.validity.constraints.czechrepublic

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.messages
import java.time.LocalDate

private const val MODULUS = 11L
private const val LEGACY_REMAINDER = 10L
private const val PIVOT_YEAR = 54
private const val LONG_SERIAL_LENGTH = 4
private const val UNASSIGNED_SERIAL = "000"
private const val FEMALE_MONTH_OFFSET = 50
private const val EXTRA_MONTH_OFFSET = 20
private const val EXTRA_MONTH_FIRST_YEAR = 2004
private const val LEGACY_LAST_YEAR = 1986
private const val TWENTIETH_CENTURY = 1900
private const val TWENTY_FIRST_CENTURY = 2000

private val RODNE_CISLO_FORMAT = Regex("(\\d{2})(\\d{2})(\\d{2})/?(\\d{3,4})")

public fun ValidationNode<String>.rodneCislo(
    message: String = messages.czechRepublic.rodneCislo
): Unit = constraint(
    message = message,
    predicate = { it.isRodneCislo() }
)

private fun isDate(year: Int, month: Int, day: Int): Boolean = runCatching { LocalDate.of(year, month, day) }.isSuccess

private fun String.isRodneCislo(): Boolean {
    val (yearPart, monthPart, dayPart, serial) = RODNE_CISLO_FORMAT.matchEntire(this)?.destructured ?: return false
    val shortYear = yearPart.toInt()
    val isLong = serial.length == LONG_SERIAL_LENGTH
    val year = when {
        !isLong && serial == UNASSIGNED_SERIAL -> return false
        shortYear >= PIVOT_YEAR -> if (isLong) TWENTIETH_CENTURY + shortYear else return false
        isLong -> TWENTY_FIRST_CENTURY + shortYear
        else -> TWENTIETH_CENTURY + shortYear
    }
    val baseMonth = monthPart.toInt().let { if (it > FEMALE_MONTH_OFFSET) it - FEMALE_MONTH_OFFSET else it }
    val hasExtraMonth = baseMonth > EXTRA_MONTH_OFFSET

    if (hasExtraMonth && year < EXTRA_MONTH_FIRST_YEAR) return false

    val month = if (hasExtraMonth) baseMonth - EXTRA_MONTH_OFFSET else baseMonth

    return isDate(year, month, dayPart.toInt()) && (!isLong || hasValidRemainder(yearPart + monthPart + dayPart + serial, year))
}

private fun hasValidRemainder(number: String, year: Int): Boolean {
    if (number.toLong() % MODULUS == 0L) return true

    return year < LEGACY_LAST_YEAR && number.dropLast(1).toLong() % MODULUS == LEGACY_REMAINDER && number.last() == '0'
}
