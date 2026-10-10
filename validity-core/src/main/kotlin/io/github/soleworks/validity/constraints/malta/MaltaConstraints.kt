package io.github.soleworks.validity.constraints.malta

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.messages

private const val ID_CARD_LENGTH = 8
private const val LETTER_INDEX = 7
private const val LAST_DIGIT_INDEX = 6
private const val FIRST_PART_END = 5
private const val SECOND_PART_END = 7
private const val FIRST_PART_LIMIT = 32000

private val ID_CARD_FORMAT = Regex("\\d{3,7}[APMGLHBZ]", RegexOption.IGNORE_CASE)

public fun ValidationNode<String>.idCardNumber(
    message: String = messages.malta.idCardNumber
): Unit = constraint(
    message = message,
    code = "idCardNumber",
    predicate = { ID_CARD_FORMAT.matches(it) && it.isIdCardNumber() }
)

private fun String.isIdCardNumber(): Boolean {
    val padded = uppercase().padStart(ID_CARD_LENGTH, '0')

    return when (getOrNull(LETTER_INDEX)) {
        'A', 'P' -> padded[LAST_DIGIT_INDEX] != '0'
        else -> {
            val firstPart = padded.take(FIRST_PART_END).toInt()
            val secondPart = padded.substring(FIRST_PART_END, SECOND_PART_END).toInt()

            firstPart <= FIRST_PART_LIMIT && firstPart != secondPart
        }
    }
}
