package io.github.soleworks.validity.constraints.spain

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.messages

private const val MODULUS = 23
private const val CONTROL_LETTERS = "TRWAGMYFPDXBNJZSQVHLCKE"

private val DNI_FORMAT = Regex("""\d{8}[$CONTROL_LETTERS]""")
private val NIE_FORMAT = Regex("""[X-Z]\d{7}[$CONTROL_LETTERS]""")
private val NIE_PREFIX_VALUES = mapOf('X' to '0', 'Y' to '1', 'Z' to '2')

public fun ValidationNode<String>.dni(
    message: String = messages.spain.dni
): Unit = constraint(
    message = message,
    code = "dni",
    predicate = { it.trim().uppercase().let { value -> DNI_FORMAT.matches(value) && value.hasControlLetter() } }
)

public fun ValidationNode<String>.nie(
    message: String = messages.spain.nie
): Unit = constraint(
    message = message,
    code = "nie",
    predicate = { it.trim().uppercase().let { value -> NIE_FORMAT.matches(value) && value.hasControlLetter() } }
)

private fun String.hasControlLetter(): Boolean {
    val number = dropLast(1).map { NIE_PREFIX_VALUES[it] ?: it }.joinToString("").toInt()

    return last() == CONTROL_LETTERS[number % MODULUS]
}
