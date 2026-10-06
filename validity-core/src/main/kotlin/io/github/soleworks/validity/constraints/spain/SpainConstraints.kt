package io.github.soleworks.validity.constraints.spain

import io.github.soleworks.validity.SpainMessages
import io.github.soleworks.validity.ValidationNode

private const val MODULUS = 23
private const val CONTROL_LETTERS = "TRWAGMYFPDXBNJZSQVHLCKE"
private const val NIF_LENGTH = 9

private val DNI_FORMAT = Regex("\\d{8}[$CONTROL_LETTERS]")
private val NIE_FORMAT = Regex("[X-Z]\\d{7}[$CONTROL_LETTERS]")
private val NIF_FORMAT = Regex("(\\d{0,8}|[XYZKLM]\\d{7})[A-HJ-NP-TV-Z]", RegexOption.IGNORE_CASE)

private val NIE_PREFIX_VALUES = mapOf('X' to '0', 'Y' to '1', 'Z' to '2')
private val NIF_PREFIX_VALUES = mapOf('Y' to '1', 'Z' to '2')

public fun ValidationNode<String>.dni(
    message: String = SpainMessages.DNI
): Unit = constraint(
    message = message,
    predicate = { it.trim().uppercase().let { value -> DNI_FORMAT.matches(value) && value.hasControlLetter() } }
)

public fun ValidationNode<String>.nie(
    message: String = SpainMessages.NIE
): Unit = constraint(
    message = message,
    predicate = { it.trim().uppercase().let { value -> NIE_FORMAT.matches(value) && value.hasControlLetter() } }
)

public fun ValidationNode<String>.nif(
    message: String = SpainMessages.NIF
): Unit = constraint(
    message = message,
    predicate = { NIF_FORMAT.matches(it) && it.uppercase().hasNifControlLetter() }
)

private fun String.hasControlLetter(): Boolean {
    val number = dropLast(1).map { NIE_PREFIX_VALUES[it] ?: it }.joinToString("").toInt()

    return last() == CONTROL_LETTERS[number % MODULUS]
}

private fun String.hasNifControlLetter(): Boolean {
    val padded = if (!first().isDigit() && length > 1) {
        (NIF_PREFIX_VALUES[first()] ?: '0') + drop(1)
    } else {
        padStart(NIF_LENGTH, '0')
    }

    return padded.last() == CONTROL_LETTERS[padded.dropLast(1).toInt() % MODULUS]
}
