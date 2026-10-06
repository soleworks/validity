package io.github.soleworks.validity.constraints.hongkong

import io.github.soleworks.validity.HongKongMessages
import io.github.soleworks.validity.ValidationNode

private const val MODULUS = 11
private const val WEIGHT_BASE = 9
private const val WEIGHTED_LENGTH = 8
private const val SHORT_LENGTH = 8
private const val SINGLE_LETTER_PREFIX = "3"
private const val LETTER_OFFSET = 55
private const val ALTERNATE_CHECK_SUM = 1
private const val ALTERNATE_CHECK_CHAR = 'A'

private val HKID_FORMAT = Regex("[A-Z]{1,2}[0-9]{6}(\\([0-9A]\\)|\\[[0-9A]]|[0-9A])")
private val BRACKETS = setOf('[', ']', '(', ')')

public fun ValidationNode<String>.hkid(
    message: String = HongKongMessages.HKID
): Unit = constraint(
    message = message,
    predicate = { it.trim().uppercase().let { value -> HKID_FORMAT.matches(value) && value.isHkid() } }
)

private fun String.isHkid(): Boolean {
    val id = filterNot(BRACKETS::contains).let { if (it.length == SHORT_LENGTH) SINGLE_LETTER_PREFIX + it else it }
    val sum = id.take(WEIGHTED_LENGTH).withIndex().sumOf { (index, char) ->
        val value = if (char.isDigit()) char.digitToInt() else (char.code - LETTER_OFFSET) % MODULUS

        value * (WEIGHT_BASE - index)
    }
    val remainder = sum % MODULUS
    val expected = when (remainder) {
        0 -> '0'
        ALTERNATE_CHECK_SUM -> ALTERNATE_CHECK_CHAR
        else -> (MODULUS - remainder).digitToChar()
    }

    return id.last() == expected
}
