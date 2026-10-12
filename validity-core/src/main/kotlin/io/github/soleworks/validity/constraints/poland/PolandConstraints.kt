package io.github.soleworks.validity.constraints.poland

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.constraints.isDate
import io.github.soleworks.validity.messages

private const val DECIMAL_MODULUS = 10
private const val MODULUS = 11
private const val PESEL_MONTH_BLOCK = 20
private const val NIP_INVALID_REMAINDER = 10

private val PESEL_FORMAT = Regex("(\\d{2})(\\d{2})(\\d{2})\\d{5}")
private val NIP_FORMAT = Regex("\\d{10}")

private val PESEL_CENTURIES = listOf(1900, 2000, 2100, 2200, 1800)
private val PESEL_WEIGHTS = listOf(1, 3, 7, 9, 1, 3, 7, 9, 1, 3)
private val NIP_WEIGHTS = listOf(6, 5, 7, 2, 3, 4, 5, 6, 7)

public fun ValidationNode<String>.pesel(
    message: String = messages.poland.pesel
): Unit = constraint(
    message = message,
    code = "pesel",
    predicate = { it.isPesel() }
)

public fun ValidationNode<String>.nip(
    message: String = messages.poland.nip
): Unit = constraint(
    message = message,
    code = "nip",
    predicate = { NIP_FORMAT.matches(it) && it.digits().isNip() }
)

private fun String.digits(): List<Int> = filter(Char::isDigit).map(Char::digitToInt)

private fun List<Int>.weightedSum(
    weights: List<Int>
): Int = zip(weights) { digit, weight -> digit * weight }.sum()

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
