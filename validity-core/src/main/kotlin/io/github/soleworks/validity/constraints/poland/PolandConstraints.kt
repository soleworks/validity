package io.github.soleworks.validity.constraints.poland

import io.github.soleworks.validity.PolandMessages
import io.github.soleworks.validity.ValidationNode
import java.time.LocalDate

private const val DECIMAL_MODULUS = 10
private const val MODULUS = 11
private const val PESEL_MONTH_BLOCK = 20
private const val LETTER_VALUE_OFFSET = 55
private const val ID_CARD_CHECK_INDEX = 3
private const val REGON_SHORT_LENGTH = 9
private const val NIP_INVALID_REMAINDER = 10

private val PESEL_FORMAT = Regex("(\\d{2})(\\d{2})(\\d{2})\\d{5}")
private val NIP_FORMAT = Regex("\\d{10}")
private val REGON_FORMAT = Regex("\\d{9}|\\d{14}")
private val ID_CARD_FORMAT = Regex("[A-Z]{3}\\d{6}")

private val PESEL_CENTURIES = listOf(1900, 2000, 2100, 2200, 1800)
private val PESEL_WEIGHTS = listOf(1, 3, 7, 9, 1, 3, 7, 9, 1, 3)
private val NIP_WEIGHTS = listOf(6, 5, 7, 2, 3, 4, 5, 6, 7)
private val REGON_SHORT_WEIGHTS = listOf(8, 9, 2, 3, 4, 5, 6, 7)
private val REGON_LONG_WEIGHTS = listOf(2, 4, 8, 5, 0, 9, 7, 3, 6, 1, 2, 4, 8)
private val ID_CARD_WEIGHTS = listOf(7, 3, 1, 0, 7, 3, 1, 7, 3)

public fun ValidationNode<String>.pesel(
    message: String = PolandMessages.PESEL
): Unit = constraint(
    message = message,
    predicate = { it.isPesel() }
)

public fun ValidationNode<String>.nip(
    message: String = PolandMessages.NIP
): Unit = constraint(
    message = message,
    predicate = { NIP_FORMAT.matches(it) && it.digits().isNip() }
)

public fun ValidationNode<String>.regon(
    message: String = PolandMessages.REGON
): Unit = constraint(
    message = message,
    predicate = { REGON_FORMAT.matches(it) && it.digits().isRegon() }
)

public fun ValidationNode<String>.dowodOsobisty(
    message: String = PolandMessages.DOWOD_OSOBISTY
): Unit = constraint(
    message = message,
    predicate = { ID_CARD_FORMAT.matches(it) && it.isDowodOsobisty() }
)

private fun String.digits(): List<Int> = filter(Char::isDigit).map(Char::digitToInt)

private fun isDate(year: Int, month: Int, day: Int): Boolean = runCatching { LocalDate.of(year, month, day) }.isSuccess

private fun List<Int>.weightedSum(weights: List<Int>): Int = zip(weights) { digit, weight -> digit * weight }.sum()

private fun String.isPesel(): Boolean {
    val (year, month, day) = PESEL_FORMAT.matchEntire(this)?.destructured?.toList()?.map(String::toInt) ?: return false
    val block = (month - 1).coerceAtLeast(0) / PESEL_MONTH_BLOCK
    val digits = digits()
    val sum = digits.dropLast(1).zip(PESEL_WEIGHTS) { digit, weight -> digit * weight % DECIMAL_MODULUS }.sum()
    val checkDigit = (DECIMAL_MODULUS - sum % DECIMAL_MODULUS) % DECIMAL_MODULUS

    return isDate(PESEL_CENTURIES[block] + year, month - block * PESEL_MONTH_BLOCK, day) && digits.last() == checkDigit
}

private fun List<Int>.isNip(): Boolean {
    val remainder = take(NIP_WEIGHTS.size).weightedSum(NIP_WEIGHTS) % MODULUS

    return remainder != NIP_INVALID_REMAINDER && remainder == last()
}

private fun List<Int>.isRegon(): Boolean {
    val weights = if (size == REGON_SHORT_LENGTH) REGON_SHORT_WEIGHTS else REGON_LONG_WEIGHTS

    return last() == dropLast(1).weightedSum(weights) % MODULUS % DECIMAL_MODULUS
}

private fun String.isDowodOsobisty(): Boolean {
    val values = map { if (it.isDigit()) it.digitToInt() else it.code - LETTER_VALUE_OFFSET }

    return values[ID_CARD_CHECK_INDEX] == values.weightedSum(ID_CARD_WEIGHTS) % DECIMAL_MODULUS
}
