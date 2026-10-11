package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.clock
import java.time.LocalDate

private const val CENTURY = 100
private const val TWENTIETH_CENTURY = 1900
private const val TWENTY_FIRST_CENTURY = 2000

internal fun isDate(
    year: Int,
    month: Int,
    day: Int
): Boolean = runCatching { LocalDate.of(year, month, day) }.isSuccess

internal fun isShortYearDate(
    shortYear: Int,
    month: Int,
    day: Int
): Boolean {
    val century = if (shortYear < LocalDate.now(clock).year % CENTURY) TWENTY_FIRST_CENTURY else TWENTIETH_CENTURY

    return isDate(century + shortYear, month, day)
}
