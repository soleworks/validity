package io.github.soleworks.validity.constraints.belgium

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.messages
import java.time.LocalDate

private const val MODULUS = 97L
private const val CENTURY = 100
private const val TWENTIETH_CENTURY = 1900
private const val TWENTY_FIRST_CENTURY = 2000
private const val BASE_LENGTH = 9
private const val UNKNOWN_DATE_PART = "00"
private const val POST_2000_PREFIX = 2_000_000_000L

private val RIJKSREGISTERNUMMER_FORMAT = Regex("\\d{11}")
private val SYMBOLS = Regex("[-\\\\/!@#\$%^&*()+=\\[\\]]+")

public fun ValidationNode<String>.rijksregisternummer(
    message: String = messages.belgium.rijksregisternummer
): Unit = constraint(
    message = message,
    code = "rijksregisternummer",
    predicate = {
        it.replace(SYMBOLS, "").let { number -> RIJKSREGISTERNUMMER_FORMAT.matches(number) && number.isRijksregisternummer() }
    }
)

private fun isShortYearDate(shortYear: Int, month: Int, day: Int): Boolean {
    val century = if (shortYear < LocalDate.now().year % CENTURY) TWENTY_FIRST_CENTURY else TWENTIETH_CENTURY

    return runCatching { LocalDate.of(century + shortYear, month, day) }.isSuccess
}

private fun String.hasValidBirthDate(): Boolean {
    val month = substring(2, 4)
    val day = substring(4, 6)

    return (month == UNKNOWN_DATE_PART && day == UNKNOWN_DATE_PART) ||
        isShortYearDate(take(2).toInt(), month.toInt(), day.toInt())
}

private fun String.isRijksregisternummer(): Boolean {
    val base = take(BASE_LENGTH).toLong()
    val checkDigits = substring(BASE_LENGTH).toLong()

    return hasValidBirthDate() &&
        (MODULUS - base % MODULUS == checkDigits || MODULUS - (POST_2000_PREFIX + base) % MODULUS == checkDigits)
}
