package io.github.soleworks.validity.constraints.france

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.messages

private const val MODULUS = 511L
private const val CHECK_LENGTH = 3

private val SPI_FORMAT = Regex("""[0-3]\d{12}|[0-3]\d\s\d{2}(\s\d{3}){3}""")

/**
 * Checks that the string is a French tax number (SPI): 13 digits with a valid check, as one block or as `00 00 000 000
 * 000`.
 *
 * Fails with code `spi` and, by default, the message `must be a valid SPI`
 * ([FranceMessages.spi][io.github.soleworks.validity.FranceMessages.spi]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.spi(
    message: String = messages.france.spi
): Unit = constraint(
    message = message,
    code = "spi",
    predicate = { SPI_FORMAT.matches(it) && it.filterNot(Char::isWhitespace).isSpi() }
)

private fun String.isSpi(): Boolean = dropLast(CHECK_LENGTH).toLong() % MODULUS == takeLast(CHECK_LENGTH).toLong()
