package io.github.soleworks.validity.constraints.india

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.messages

private const val PERMUTATION_PERIOD = 8

private val AADHAAR_FORMAT = Regex("[1-9]\\d{3}\\s?\\d{4}\\s?\\d{4}")
private val PAN_FORMAT = Regex("[A-Z]{3}[ABCFGHLJPT][A-Z](?!0000)[0-9]{4}[A-Z]")

private val VERHOEFF_MULTIPLICATION = listOf(
    listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9),
    listOf(1, 2, 3, 4, 0, 6, 7, 8, 9, 5),
    listOf(2, 3, 4, 0, 1, 7, 8, 9, 5, 6),
    listOf(3, 4, 0, 1, 2, 8, 9, 5, 6, 7),
    listOf(4, 0, 1, 2, 3, 9, 5, 6, 7, 8),
    listOf(5, 9, 8, 7, 6, 0, 4, 3, 2, 1),
    listOf(6, 5, 9, 8, 7, 1, 0, 4, 3, 2),
    listOf(7, 6, 5, 9, 8, 2, 1, 0, 4, 3),
    listOf(8, 7, 6, 5, 9, 3, 2, 1, 0, 4),
    listOf(9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
)
private val VERHOEFF_PERMUTATION = listOf(
    listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9),
    listOf(1, 5, 7, 6, 2, 8, 3, 0, 9, 4),
    listOf(5, 8, 0, 3, 7, 9, 6, 1, 4, 2),
    listOf(8, 9, 1, 6, 0, 4, 3, 5, 2, 7),
    listOf(9, 4, 5, 3, 1, 2, 6, 8, 7, 0),
    listOf(4, 2, 8, 6, 5, 7, 3, 9, 0, 1),
    listOf(2, 7, 9, 3, 8, 0, 6, 4, 1, 5),
    listOf(7, 0, 4, 6, 9, 1, 3, 2, 5, 8)
)

public fun ValidationNode<String>.aadhaar(
    message: String = messages.india.aadhaar
): Unit = constraint(
    message = message,
    code = "aadhaar",
    predicate = { it.trim().let { value -> AADHAAR_FORMAT.matches(value) && value.isVerhoeff() } }
)

public fun ValidationNode<String>.pan(
    message: String = messages.india.pan
): Unit = constraint(
    message = message,
    code = "pan",
    predicate = { PAN_FORMAT.matches(it) }
)

private fun String.isVerhoeff(): Boolean = filter(Char::isDigit)
    .reversed()
    .map(Char::digitToInt)
    .withIndex()
    .fold(0) { checksum, (index, digit) ->
        VERHOEFF_MULTIPLICATION[checksum][VERHOEFF_PERMUTATION[index % PERMUTATION_PERIOD][digit]]
    } == 0
