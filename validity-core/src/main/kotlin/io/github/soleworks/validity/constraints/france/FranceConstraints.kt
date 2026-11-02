package io.github.soleworks.validity.constraints.france

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.constraints.digits
import io.github.soleworks.validity.constraints.hasLuhnCheckDigit
import io.github.soleworks.validity.messages

private const val MODULUS = 511L
private const val CHECK_LENGTH = 3
private const val SIREN_LENGTH = 9
private const val LA_POSTE_SIREN = "356000000"
private const val LA_POSTE_HEADQUARTERS = "35600000000048"
private const val LA_POSTE_MODULUS = 5

private val SPI_FORMAT = Regex("""[0-3]\d{12}|[0-3]\d\s\d{2}(\s\d{3}){3}""")
private val SIREN_FORMAT = Regex("""\d{9}|\d{3} \d{3} \d{3}""")
private val SIRET_FORMAT = Regex("""\d{14}|\d{3} \d{3} \d{3} \d{5}""")

internal val FRANCE_TAX_IDS: Map<String, (String) -> Boolean> = mapOf(
    "SPI" to String::isSpi,
    "SIREN" to String::isSiren,
    "SIRET" to String::isSiret
)

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
    predicate = String::isSpi
)

/**
 * Checks that the string is a French SIREN (the number of a company): 9 digits with a valid Luhn check digit, as
 * `732829320` or `732 829 320`.
 *
 * Fails with code `siren` and, by default, the message `must be a valid SIREN`
 * ([FranceMessages.siren][io.github.soleworks.validity.FranceMessages.siren]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.siren(
    message: String = messages.france.siren
): Unit = constraint(
    message = message,
    code = "siren",
    predicate = String::isSiren
)

/**
 * Checks that the string is a French SIRET (the number of an establishment): 14 digits, as `73282932000074` or
 * `732 829 320 00074`.
 *
 * The first 9 digits are a valid SIREN, and all 14 have a valid Luhn check digit. The establishments of La Poste
 * (SIREN `356000000`) are checked by La Poste's rule instead: the sum of their digits is a multiple of 5.
 *
 * Fails with code `siret` and, by default, the message `must be a valid SIRET`
 * ([FranceMessages.siret][io.github.soleworks.validity.FranceMessages.siret]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.siret(
    message: String = messages.france.siret
): Unit = constraint(
    message = message,
    code = "siret",
    predicate = String::isSiret
)

private fun String.isSpi(): Boolean = SPI_FORMAT.matches(this) && filterNot(Char::isWhitespace).hasSpiCheckDigits()

private fun String.hasSpiCheckDigits(): Boolean =
    dropLast(CHECK_LENGTH).toLong() % MODULUS == takeLast(CHECK_LENGTH).toLong()

private fun String.isSiren(): Boolean = SIREN_FORMAT.matches(this) && hasLuhnCheckDigit()

private fun String.isSiret(): Boolean {
    if (!SIRET_FORMAT.matches(this))
        return false

    val digits = filter(Char::isDigit)
    val hasCheckDigit = if (digits.startsWith(LA_POSTE_SIREN) && digits != LA_POSTE_HEADQUARTERS)
        digits.digits().sum() % LA_POSTE_MODULUS == 0
    else
        digits.hasLuhnCheckDigit()

    return hasCheckDigit && digits.take(SIREN_LENGTH).hasLuhnCheckDigit()
}
