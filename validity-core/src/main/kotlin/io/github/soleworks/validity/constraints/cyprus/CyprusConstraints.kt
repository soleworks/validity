package io.github.soleworks.validity.constraints.cyprus

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.messages

private const val ALPHABET_SIZE = 26
private const val EVEN_INDEX_DIVISOR = 2

private val AFM_FORMAT = Regex("[09]\\d{7}[A-Z]")
private val AFM_EVEN_INDEX_VALUES = listOf(1, 0, 5, 7, 9, 13, 15, 17, 19, 21)

public fun ValidationNode<String>.afm(
    message: String = messages.cyprus.afm
): Unit = constraint(
    message = message,
    code = "afm",
    predicate = { AFM_FORMAT.matches(it) && it.isAfm() }
)

private fun String.isAfm(): Boolean {
    val sum = dropLast(1).map(Char::digitToInt).withIndex().sumOf { (index, digit) ->
        if (index % EVEN_INDEX_DIVISOR == 0) AFM_EVEN_INDEX_VALUES[digit] else digit
    }

    return last() == 'A' + sum % ALPHABET_SIZE
}
