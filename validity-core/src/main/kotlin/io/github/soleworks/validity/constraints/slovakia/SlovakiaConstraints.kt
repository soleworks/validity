package io.github.soleworks.validity.constraints.slovakia

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.messages
import java.time.LocalDate

private const val PRE_1954_LENGTH = 9
private const val LAST_PRE_1954_YEAR = 53
private const val UNASSIGNED_SERIAL = "000"
private const val FEMALE_MONTH_OFFSET = 50
private const val TWENTIETH_CENTURY = 1900

private val RODNE_CISLO_FORMAT = Regex("(\\d{2})(\\d{2})(\\d{2})/?(\\d{3,4})")

public fun ValidationNode<String>.rodneCislo(
    message: String = messages.slovakia.rodneCislo
): Unit = constraint(
    message = message,
    predicate = { RODNE_CISLO_FORMAT.matches(it) && (it.length != PRE_1954_LENGTH || it.isPre1954RodneCislo()) }
)

private fun isDate(year: Int, month: Int, day: Int): Boolean = runCatching { LocalDate.of(year, month, day) }.isSuccess

private fun String.isPre1954RodneCislo(): Boolean {
    val (yearPart, monthPart, dayPart, serial) = RODNE_CISLO_FORMAT.matchEntire(this)?.destructured ?: return false
    val shortYear = yearPart.toInt()
    val month = monthPart.toInt().let { if (it > FEMALE_MONTH_OFFSET) it - FEMALE_MONTH_OFFSET else it }

    return serial != UNASSIGNED_SERIAL &&
        shortYear <= LAST_PRE_1954_YEAR &&
        isDate(TWENTIETH_CENTURY + shortYear, month, dayPart.toInt())
}
