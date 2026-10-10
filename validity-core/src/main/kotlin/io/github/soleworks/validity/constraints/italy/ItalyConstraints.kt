package io.github.soleworks.validity.constraints.italy

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.messages
import java.time.LocalDate

private const val CENTURY = 100
private const val TWENTIETH_CENTURY = 1900
private const val TWENTY_FIRST_CENTURY = 2000
private const val FEMALE_DAY_OFFSET = 40
private const val ALPHABET_SIZE = 26
private const val CIE_LENGTH = 9
private const val CIE_PLACEHOLDER = "CA00000AA"
private const val VOWELS = "AEIOU"
private const val PADDING = 'X'
private const val LETTER_OFFSET = 'A'.code
private const val ODD_MULTIPLIER = 2
private const val ODD_LARGE_DIGIT = 4
private const val ODD_LARGE_DIGIT_BONUS = 2

private val CODICE_FISCALE_FORMAT = Regex(
    "[A-Z]{6}[L-NP-V0-9]{2}[A-EHLMPRST][L-NP-V0-9]{2}[A-ILMZ][L-NP-V0-9]{3}[A-Z]",
    RegexOption.IGNORE_CASE
)
private val CIE_FORMAT = Regex("C[A-Z]\\d{5}[A-Z]{2}", RegexOption.IGNORE_CASE)

private val NUMBER_LOCATIONS = listOf(6, 7, 9, 10, 12, 13, 14)
private val NUMBER_REPLACEMENTS = "LMNPQRSTUV".withIndex().associate { (index, letter) -> letter to '0' + index }
private val MONTH_REPLACEMENTS = "ABCDEHLMPRST".withIndex().associate { (index, letter) -> letter to index + 1 }
private val ODD_CONVERSIONS = mapOf(
    'A' to 1, 'B' to 0, 'C' to 5, 'D' to 7, 'E' to 9, 'F' to 13, 'G' to 15, 'H' to 17, 'I' to 19, 'J' to 21,
    'K' to 2, 'L' to 4, 'M' to 18, 'N' to 20, 'O' to 11, 'P' to 3, 'Q' to 6, 'R' to 8, 'S' to 12, 'T' to 14,
    'U' to 16, 'V' to 10, 'W' to 22, 'X' to 25, 'Y' to 24, 'Z' to 23, '0' to 1, '1' to 0
)

public fun ValidationNode<String>.codiceFiscale(
    message: String = messages.italy.codiceFiscale
): Unit = constraint(
    message = message,
    code = "codiceFiscale",
    predicate = { CODICE_FISCALE_FORMAT.matches(it) && it.uppercase().isCodiceFiscale() }
)

public fun ValidationNode<String>.cie(
    message: String = messages.italy.cie
): Unit = constraint(
    message = message,
    code = "cie",
    predicate = { it.length == CIE_LENGTH && it != CIE_PLACEHOLDER && CIE_FORMAT.matches(it) }
)

private fun isDate(year: Int, month: Int, day: Int): Boolean = runCatching { LocalDate.of(year, month, day) }.isSuccess

private fun isShortYearDate(shortYear: Int, month: Int, day: Int): Boolean {
    val century = if (shortYear < LocalDate.now().year % CENTURY) TWENTY_FIRST_CENTURY else TWENTIETH_CENTURY

    return isDate(century + shortYear, month, day)
}

private fun List<Char>.isNameCode(): Boolean {
    var vowelFound = false
    var paddingFound = false

    forEachIndexed { index, char ->
        if (!vowelFound && char in VOWELS) {
            vowelFound = true
        } else if (!paddingFound && vowelFound && char == PADDING) {
            paddingFound = true
        } else if (index > 0) {
            if (vowelFound && !paddingFound && char !in VOWELS) return false
            if (paddingFound && char != PADDING) return false
        }
    }

    return true
}

private fun String.isCodiceFiscale(): Boolean {
    val chars = toMutableList()

    if (!chars.subList(0, 3).isNameCode() || !chars.subList(3, 6).isNameCode()) return false

    NUMBER_LOCATIONS.forEach { location -> chars[location] = NUMBER_REPLACEMENTS[chars[location]] ?: chars[location] }

    val month = MONTH_REPLACEMENTS.getValue(chars[8])
    val day = "${chars[9]}${chars[10]}".toInt().let { if (it > FEMALE_DAY_OFFSET) it - FEMALE_DAY_OFFSET else it }

    if (!isShortYearDate("${chars[6]}${chars[7]}".toInt(), month, day)) return false

    val even = (1 until chars.size - 1 step 2).sumOf { chars[it].evenValue() }
    val odd = (0 until chars.size - 1 step 2).sumOf { chars[it].oddValue() }

    return 'A' + (even + odd) % ALPHABET_SIZE == chars.last()
}

private fun Char.evenValue(): Int = if (isDigit()) digitToInt() else code - LETTER_OFFSET

private fun Char.oddValue(): Int = ODD_CONVERSIONS[this] ?: digitToInt().let { digit ->
    ODD_MULTIPLIER * digit + 1 + if (digit > ODD_LARGE_DIGIT) ODD_LARGE_DIGIT_BONUS else 0
}
