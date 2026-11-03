package io.github.soleworks.validity.constraints.spain

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.constraints.luhnCheckDigit
import io.github.soleworks.validity.messages

private const val MODULUS = 23
private const val CONTROL_LETTERS = "TRWAGMYFPDXBNJZSQVHLCKE"
private const val CIF_CONTROL_LETTERS = "JABCDEFGHI"

private val DNI_FORMAT = Regex("""\d{8}[$CONTROL_LETTERS]""")
private val NIE_FORMAT = Regex("""[X-Z]\d{7}[$CONTROL_LETTERS]""")
private val NIE_PREFIX_VALUES = mapOf('X' to '0', 'Y' to '1', 'Z' to '2')
private val CIF_FORMAT = Regex("""[ABCDEFGHJNPQRSUVW]\d{7}[\dA-J]""")

internal val SPAIN_TAX_IDS: Map<String, (String) -> Boolean> = mapOf(
    "DNI" to String::isDni,
    "NIE" to String::isNie,
    "CIF" to String::isCif
)

/**
 * Checks that the string is a Spanish DNI: 8 digits and the matching control letter, such as `12345678Z`.
 *
 * Case and whitespace around it are ignored.
 *
 * Fails with code `dni` and, by default, the message `must be a valid DNI`
 * ([SpainMessages.dni][io.github.soleworks.validity.SpainMessages.dni]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.dni(
    message: String = messages.spain.dni
): Unit = constraint(
    message = message,
    code = "dni",
    predicate = String::isDni
)

/**
 * Checks that the string is a Spanish NIE: `X`, `Y` or `Z`, 7 digits and the matching control letter, such as
 * `X1234567L`.
 *
 * Case and whitespace around it are ignored.
 *
 * Fails with code `nie` and, by default, the message `must be a valid NIE`
 * ([SpainMessages.nie][io.github.soleworks.validity.SpainMessages.nie]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.nie(
    message: String = messages.spain.nie
): Unit = constraint(
    message = message,
    code = "nie",
    predicate = String::isNie
)

/**
 * Checks that the string is a Spanish CIF (the tax number of a company): a letter, 7 digits and a check character,
 * such as `B12345674`.
 *
 * The first letter is the type of entity (`A` to `H`, `J`, `N`, `P` to `S`, `U` to `W`), and the check character is the
 * Luhn check digit of the 7 digits or the letter that stands for it (`J` for 0, `A` for 1, ..., `I` for 9). Letters are
 * uppercase.
 *
 * Fails with code `cif` and, by default, the message `must be a valid CIF`
 * ([SpainMessages.cif][io.github.soleworks.validity.SpainMessages.cif]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.cif(
    message: String = messages.spain.cif
): Unit = constraint(
    message = message,
    code = "cif",
    predicate = String::isCif
)

private fun String.isDni(): Boolean = trim().uppercase().let { value ->
    DNI_FORMAT.matches(value) && value.hasControlLetter()
}

private fun String.isNie(): Boolean = trim().uppercase().let { value ->
    NIE_FORMAT.matches(value) && value.hasControlLetter()
}

private fun String.hasControlLetter(): Boolean {
    val number = dropLast(1).map { NIE_PREFIX_VALUES[it] ?: it }.joinToString("").toInt()

    return last() == CONTROL_LETTERS[number % MODULUS]
}

private fun String.isCif(): Boolean {
    if (!CIF_FORMAT.matches(this))
        return false

    val checkDigit = drop(1).dropLast(1).luhnCheckDigit()

    return last() == checkDigit.digitToChar() || last() == CIF_CONTROL_LETTERS[checkDigit]
}
