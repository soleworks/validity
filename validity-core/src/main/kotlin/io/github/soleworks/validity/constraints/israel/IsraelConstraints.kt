package io.github.soleworks.validity.constraints.israel

import io.github.soleworks.validity.IsraelMessages
import io.github.soleworks.validity.ValidationNode

private const val MODULUS = 10
private const val PARITY = 2
private const val MAX_DIGIT = 9

private val TEUDAT_ZEHUT_FORMAT = Regex("\\d{9}")

public fun ValidationNode<String>.teudatZehut(
    message: String = IsraelMessages.TEUDAT_ZEHUT
): Unit = constraint(
    message = message,
    predicate = { it.trim().let { value -> TEUDAT_ZEHUT_FORMAT.matches(value) && value.isTeudatZehut() } }
)

private fun String.isTeudatZehut(): Boolean = withIndex().sumOf { (index, char) ->
    val weighted = char.digitToInt() * (index % PARITY + 1)

    if (weighted > MAX_DIGIT) weighted - MAX_DIGIT else weighted
} % MODULUS == 0
