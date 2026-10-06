package io.github.soleworks.validity.constraints.canada

import io.github.soleworks.validity.CanadaMessages
import io.github.soleworks.validity.ValidationNode

private const val MODULUS = 10
private const val PARITY = 2
private const val DOUBLING_FACTOR = 2

private val SIN_FORMAT = Regex("\\d{9}")

public fun ValidationNode<String>.sin(
    message: String = CanadaMessages.SIN
): Unit = constraint(
    message = message,
    predicate = { SIN_FORMAT.matches(it) && it.isSin() }
)

private fun String.isSin(): Boolean = withIndex().sumOf { (index, char) ->
    val digit = char.digitToInt()
    val doubled = digit * DOUBLING_FACTOR

    if (index % PARITY == 1) doubled / MODULUS + doubled % MODULUS else digit
} % MODULUS == 0
