package io.github.soleworks.validity.constraints.bulgaria

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.constraints.digits
import io.github.soleworks.validity.constraints.groups
import io.github.soleworks.validity.constraints.isDate
import io.github.soleworks.validity.constraints.weightedSum
import io.github.soleworks.validity.messages

private const val MODULUS = 11
private const val DECIMAL_MODULUS = 10
private const val TWENTY_FIRST_CENTURY_MONTH_OFFSET = 40
private const val NINETEENTH_CENTURY_MONTH_OFFSET = 20
private const val NINETEENTH_CENTURY = 1800
private const val TWENTIETH_CENTURY = 1900
private const val TWENTY_FIRST_CENTURY = 2000

private val EGN_FORMAT = Regex("""(\d{2})(\d{2})(\d{2})\d{4}""")
private val EGN_WEIGHTS = listOf(2, 4, 8, 5, 10, 9, 7, 3, 6)

/**
 * Checks that the string is a Bulgarian EGN: 10 digits that start with a real birth date, and a valid check digit.
 *
 * Fails with code `egn` and, by default, the message `must be a valid EGN`
 * ([BulgariaMessages.egn][io.github.soleworks.validity.BulgariaMessages.egn]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.egn(
    message: String = messages.bulgaria.egn
): Unit = constraint(
    message = message,
    code = "egn",
    predicate = { it.isEgn() }
)

private fun String.isEgn(): Boolean {
    val (year, encodedMonth, day) = EGN_FORMAT.groups(this)?.map(String::toInt) ?: return false
    val (century, month) = when {
        encodedMonth > TWENTY_FIRST_CENTURY_MONTH_OFFSET -> {
            TWENTY_FIRST_CENTURY to encodedMonth - TWENTY_FIRST_CENTURY_MONTH_OFFSET
        }

        encodedMonth > NINETEENTH_CENTURY_MONTH_OFFSET -> {
            NINETEENTH_CENTURY to encodedMonth - NINETEENTH_CENTURY_MONTH_OFFSET
        }

        else -> {
            TWENTIETH_CENTURY to encodedMonth
        }
    }
    val digits = digits()
    val checkDigit = digits.weightedSum(EGN_WEIGHTS) % MODULUS % DECIMAL_MODULUS

    return isDate(year = century + year, month = month, day = day) && digits.last() == checkDigit
}
