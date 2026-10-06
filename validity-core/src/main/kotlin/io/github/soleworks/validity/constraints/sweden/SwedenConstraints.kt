package io.github.soleworks.validity.constraints.sweden

import io.github.soleworks.validity.SwedenMessages
import io.github.soleworks.validity.ValidationNode
import java.time.LocalDate

private const val CENTURY = 100
private const val TWENTIETH_CENTURY = 1900
private const val TWENTY_FIRST_CENTURY = 2000
private const val COORDINATION_OFFSET = 60
private const val MAX_SHORT_LENGTH = 11
private const val CENTENARIAN_YEARS = 100
private const val SEPARATOR_INDEX = 6
private const val CENTURY_DIGITS = 2
private const val DOUBLE_DIGIT_LIMIT = 9
private const val DOUBLING = 2
private const val LUHN_MODULUS = 10

private val PERSONAL_NUMBER_FORMAT = Regex("(\\d{6}[-+]?\\d{4}|(18|19|20)\\d{6}[-+]?\\d{4})")
private val NON_WORD = Regex("\\W")

public fun ValidationNode<String>.personnummer(
    message: String = SwedenMessages.PERSONNUMMER
): Unit = constraint(
    message = message,
    predicate = { PERSONAL_NUMBER_FORMAT.matches(it) && it.isValid() && !it.isCoordination() }
)

public fun ValidationNode<String>.samordningsnummer(
    message: String = SwedenMessages.SAMORDNINGSNUMMER
): Unit = constraint(
    message = message,
    predicate = { PERSONAL_NUMBER_FORMAT.matches(it) && it.isValid() && it.isCoordination() }
)

private fun String.shortForm(): String = if (length > MAX_SHORT_LENGTH) drop(CENTURY_DIGITS) else this

private fun String.rawDay(): Int = shortForm().substring(4, 6).toInt()

private fun String.isCoordination(): Boolean = rawDay() > COORDINATION_OFFSET

private fun isDate(year: Int, month: Int, day: Int): Boolean = runCatching { LocalDate.of(year, month, day) }.isSuccess

private fun isShortYearDate(shortYear: Int, month: Int, day: Int): Boolean {
    val century = if (shortYear < LocalDate.now().year % CENTURY) TWENTY_FIRST_CENTURY else TWENTIETH_CENTURY

    return isDate(century + shortYear, month, day)
}

private fun String.centenarianYear(): Int? {
    val currentYear = LocalDate.now().year
    val currentCentury = currentYear / CENTURY
    val shortYear = take(CENTURY_DIGITS).toInt()
    val sameCentury = currentCentury * CENTURY + shortYear
    val previousCentury = (currentCentury - 1) * CENTURY + shortYear

    return if (this[SEPARATOR_INDEX] == '-') {
        if (sameCentury > currentYear) previousCentury else sameCentury
    } else {
        previousCentury.takeIf { currentYear - it >= CENTENARIAN_YEARS }
    }
}

private fun String.hasValidBirthDate(): Boolean {
    val short = shortForm()
    val month = short.substring(2, 4).toInt()
    val rawDay = rawDay()
    val day = if (rawDay > COORDINATION_OFFSET) rawDay - COORDINATION_OFFSET else rawDay

    return when {
        length > MAX_SHORT_LENGTH -> isDate(take(4).toInt(), month, day)
        length == MAX_SHORT_LENGTH && rawDay < COORDINATION_OFFSET -> centenarianYear()?.let { isDate(it, month, day) } ?: false
        else -> isShortYearDate(take(CENTURY_DIGITS).toInt(), month, day)
    }
}

private fun String.isLuhnValid(): Boolean = reversed().map(Char::digitToInt).withIndex().sumOf { (index, digit) ->
    if (index % DOUBLING == 1) (digit * DOUBLING).let { if (it > DOUBLE_DIGIT_LIMIT) it - DOUBLE_DIGIT_LIMIT else it } else digit
} % LUHN_MODULUS == 0

private fun String.isValid(): Boolean = hasValidBirthDate() && replaceFirst(NON_WORD, "").isLuhnValid()
