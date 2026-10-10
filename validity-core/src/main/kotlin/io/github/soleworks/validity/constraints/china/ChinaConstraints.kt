package io.github.soleworks.validity.constraints.china

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.messages
import java.time.LocalDate
import java.time.YearMonth

private const val MODULUS = 11
private const val ADDRESS_LENGTH = 2
private const val ID15_LENGTH = 15
private const val ID15_CENTURY = "19"
private const val BIRTH_START = 6
private const val ID15_BIRTH_END = 12
private const val ID18_BIRTH_END = 14
private const val YEAR_END = 4
private const val MONTH_END = 6

private val ID15_FORMAT = Regex("[1-9]\\d{7}(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])\\d{3}")
private val ID18_FORMAT = Regex("[1-9]\\d{5}[1-9]\\d{3}(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])\\d{3}[\\dxX]")
private val PROVINCES = setOf(
    "11", "12", "13", "14", "15", "21", "22", "23", "31", "32", "33", "34", "35", "36", "37", "41", "42", "43", "44", "45",
    "46", "50", "51", "52", "53", "54", "61", "62", "63", "64", "65"
)
private val POWERS = listOf(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2)
private val PARITY_BITS = listOf('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2')

public fun ValidationNode<String>.residentId(
    message: String = messages.china.residentId
): Unit = constraint(
    message = message,
    code = "residentId",
    predicate = { it.isResidentId() }
)

private fun String.isResidentId(): Boolean = take(ADDRESS_LENGTH) in PROVINCES && when (length) {
    ID15_LENGTH -> ID15_FORMAT.matches(this) && (ID15_CENTURY + substring(BIRTH_START, ID15_BIRTH_END)).isPastDate()
    else -> ID18_FORMAT.matches(this) && substring(BIRTH_START, ID18_BIRTH_END).isPastDate() && hasParityBit()
}

private fun String.isPastDate(): Boolean {
    val yearMonth = YearMonth.of(take(YEAR_END).toInt(), substring(YEAR_END, MONTH_END).toInt())
    val day = substring(MONTH_END).toInt()

    return yearMonth.isValidDay(day) && !yearMonth.atDay(day).isAfter(LocalDate.now())
}

private fun String.hasParityBit(): Boolean {
    val sum = take(POWERS.size).map(Char::digitToInt).zip(POWERS) { digit, power -> digit * power }.sum()

    return PARITY_BITS[sum % MODULUS] == last().uppercaseChar()
}
