package io.github.soleworks.validity.constraints.finland

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.messages
import java.time.LocalDate

private const val MODULUS = 31
private const val CHECK_CHARACTERS = "0123456789ABCDEFHJKLMNPRSTUVWXY"
private const val NINETEENTH_CENTURY = 1800
private const val TWENTIETH_CENTURY = 1900
private const val TWENTY_FIRST_CENTURY = 2000
private const val CENTURY_SIGN_INDEX = 6
private const val INDIVIDUAL_START = 7
private const val INDIVIDUAL_END = 10

private val HETU_FORMAT = Regex("\\d{6}[-+A]\\d{3}[0-9A-FHJ-NPR-Y]", RegexOption.IGNORE_CASE)

public fun ValidationNode<String>.hetu(
    message: String = messages.finland.hetu
): Unit = constraint(
    message = message,
    predicate = { HETU_FORMAT.matches(it) && it.hasValidBirthDate() && it.hasValidCheckCharacter() }
)

private fun String.birthYear(): Int {
    val shortYear = substring(4, 6).toInt()

    return when (this[CENTURY_SIGN_INDEX]) {
        '+' -> NINETEENTH_CENTURY + shortYear
        '-' -> TWENTIETH_CENTURY + shortYear
        else -> TWENTY_FIRST_CENTURY + shortYear
    }
}

private fun String.hasValidBirthDate(): Boolean =
    runCatching { LocalDate.of(birthYear(), substring(2, 4).toInt(), take(2).toInt()) }.isSuccess

private fun String.hasValidCheckCharacter(): Boolean {
    val number = (take(CENTURY_SIGN_INDEX) + substring(INDIVIDUAL_START, INDIVIDUAL_END)).toInt()

    return CHECK_CHARACTERS[number % MODULUS] == last()
}
