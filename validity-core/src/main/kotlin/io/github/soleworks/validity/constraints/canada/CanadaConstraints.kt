package io.github.soleworks.validity.constraints.canada

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.constraints.hasLuhnCheckDigit
import io.github.soleworks.validity.messages

private const val BN_ROOT_LENGTH = 9

private val SIN_FORMAT = Regex("""\d{9}""")
private val BN_FORMAT = Regex("""\d{9}((RC|RM|RP|RT)\d{4})?""")

internal val CANADA_TAX_IDS: Map<String, (String) -> Boolean> = mapOf(
    "SIN" to String::isSin,
    "BN" to String::isBn
)

/**
 * Checks that the string is a Canadian SIN: 9 digits with a valid Luhn check digit, with no spaces.
 *
 * Fails with code `sin` and, by default, the message `must be a valid SIN`
 * ([CanadaMessages.sin][io.github.soleworks.validity.CanadaMessages.sin]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.sin(
    message: String = messages.canada.sin
): Unit = constraint(
    message = message,
    code = "sin",
    predicate = String::isSin
)

/**
 * Checks that the string is a Canadian BN (business number) with a valid Luhn check digit: the 9-digit root, such as
 * `123456782`, or the 15-character program account, such as `123456782RT0001`.
 *
 * A program account adds the program (`RC`, `RM`, `RP` or `RT`, in uppercase) and 4 digits to the root.
 *
 * Fails with code `bn` and, by default, the message `must be a valid business number`
 * ([CanadaMessages.bn][io.github.soleworks.validity.CanadaMessages.bn]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.bn(
    message: String = messages.canada.bn
): Unit = constraint(
    message = message,
    code = "bn",
    predicate = String::isBn
)

private fun String.isSin(): Boolean = SIN_FORMAT.matches(this) && hasLuhnCheckDigit()

private fun String.isBn(): Boolean = BN_FORMAT.matches(this) && take(BN_ROOT_LENGTH).hasLuhnCheckDigit()
