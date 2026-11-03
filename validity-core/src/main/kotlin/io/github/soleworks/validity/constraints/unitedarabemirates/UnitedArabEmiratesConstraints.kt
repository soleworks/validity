package io.github.soleworks.validity.constraints.unitedarabemirates

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.constraints.hasLuhnCheckDigit
import io.github.soleworks.validity.messages

private val TRADE_LICENSE_FORMAT = Regex("""[A-Z\d][A-Z\d/-]{3,18}[A-Z\d]""")
private val EMIRATES_ID_FORMAT = Regex("""784\d{12}|784-\d{4}-\d{7}-\d""")

internal val UNITED_ARAB_EMIRATES_TAX_IDS: Map<String, (String) -> Boolean> = mapOf(
    "TRADE_LICENSE" to String::isTradeLicense,
    "EMIRATES_ID" to String::isEmiratesId
)

/**
 * Checks that the string looks like a trade license number of the United Arab Emirates: 5 to 20 uppercase letters,
 * digits, dashes or slashes, starting and ending with a letter or a digit, such as `CN-1234567`.
 *
 * Each emirate issues its own numbers and none has a check digit, so only the format is checked.
 *
 * Fails with code `tradeLicense` and, by default, the message `must be a valid trade license number`
 * ([UnitedArabEmiratesMessages.tradeLicense][io.github.soleworks.validity.UnitedArabEmiratesMessages.tradeLicense]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.tradeLicense(
    message: String = messages.unitedArabEmirates.tradeLicense
): Unit = constraint(
    message = message,
    code = "tradeLicense",
    predicate = String::isTradeLicense
)

/**
 * Checks that the string is an Emirates ID: 15 digits that start with `784` and end with a valid Luhn check digit, as
 * `784196865703050` or `784-1968-6570305-0`.
 *
 * Fails with code `emiratesId` and, by default, the message `must be a valid Emirates ID`
 * ([UnitedArabEmiratesMessages.emiratesId][io.github.soleworks.validity.UnitedArabEmiratesMessages.emiratesId]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.emiratesId(
    message: String = messages.unitedArabEmirates.emiratesId
): Unit = constraint(
    message = message,
    code = "emiratesId",
    predicate = String::isEmiratesId
)

private fun String.isTradeLicense(): Boolean = TRADE_LICENSE_FORMAT.matches(this)

private fun String.isEmiratesId(): Boolean = EMIRATES_ID_FORMAT.matches(this) && hasLuhnCheckDigit()
