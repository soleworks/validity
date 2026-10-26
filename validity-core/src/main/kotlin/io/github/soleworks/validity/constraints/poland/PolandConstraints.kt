package io.github.soleworks.validity.constraints.poland

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.constraints.digits
import io.github.soleworks.validity.constraints.groups
import io.github.soleworks.validity.constraints.isDate
import io.github.soleworks.validity.constraints.weightedSum
import io.github.soleworks.validity.messages

private const val MODULUS = 11
private const val DECIMAL_MODULUS = 10
private const val PESEL_MONTH_BLOCK = 20

private val PESEL_FORMAT = Regex("""(\d{2})(\d{2})(\d{2})\d{5}""")
private val NIP_FORMAT = Regex("""\d{10}""")
private val PESEL_CENTURIES = listOf(1900, 2000, 2100, 2200, 1800)
private val PESEL_WEIGHTS = listOf(1, 3, 7, 9, 1, 3, 7, 9, 1, 3)
private val NIP_WEIGHTS = listOf(6, 5, 7, 2, 3, 4, 5, 6, 7)

/**
 * Checks that the string is a Polish PESEL: 11 digits that start with a real birth date, and a valid check digit.
 *
 * Fails with code `pesel` and, by default, the message `must be a valid PESEL`
 * ([PolandMessages.pesel][io.github.soleworks.validity.PolandMessages.pesel]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.pesel(
    message: String = messages.poland.pesel
): Unit = constraint(
    message = message,
    code = "pesel",
    predicate = { it.isPesel() }
)

/**
 * Checks that the string is a Polish NIP (tax number): 10 digits with a valid check digit, with no dashes.
 *
 * Fails with code `nip` and, by default, the message `must be a valid NIP`
 * ([PolandMessages.nip][io.github.soleworks.validity.PolandMessages.nip]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.nip(
    message: String = messages.poland.nip
): Unit = constraint(
    message = message,
    code = "nip",
    predicate = { NIP_FORMAT.matches(it) && it.digits().isNip() }
)

private fun String.isPesel(): Boolean {
    val (year, encodedMonth, day) = PESEL_FORMAT.groups(this)?.map(String::toInt) ?: return false
    val block = (encodedMonth - 1).coerceAtLeast(0) / PESEL_MONTH_BLOCK
    val digits = digits()
    val checkDigit = (DECIMAL_MODULUS - digits.weightedSum(PESEL_WEIGHTS) % DECIMAL_MODULUS) % DECIMAL_MODULUS

    return isDate(
        year = PESEL_CENTURIES[block] + year,
        month = encodedMonth - block * PESEL_MONTH_BLOCK,
        day = day
    ) &&
        digits.last() == checkDigit
}

private fun List<Int>.isNip(): Boolean = weightedSum(NIP_WEIGHTS) % MODULUS == last()
