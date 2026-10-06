package io.github.soleworks.validity.constraints.france

import io.github.soleworks.validity.FranceMessages
import io.github.soleworks.validity.ValidationNode

private const val MODULUS = 511L
private const val CHECK_LENGTH = 3

private val SPI_FORMAT = Regex("[0-3]\\d{12}|[0-3]\\d\\s\\d{2}(\\s\\d{3}){3}")

public fun ValidationNode<String>.spi(
    message: String = FranceMessages.SPI
): Unit = constraint(
    message = message,
    predicate = { SPI_FORMAT.matches(it) && it.filterNot(Char::isWhitespace).isSpi() }
)

private fun String.isSpi(): Boolean = dropLast(CHECK_LENGTH).toLong() % MODULUS == takeLast(CHECK_LENGTH).toLong()
