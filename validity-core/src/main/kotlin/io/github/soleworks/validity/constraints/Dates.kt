package io.github.soleworks.validity.constraints

import java.time.LocalDate

internal fun isDate(
    year: Int,
    month: Int,
    day: Int
): Boolean = runCatching { LocalDate.of(year, month, day) }.isSuccess
