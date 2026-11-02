package io.github.soleworks.validity.constraints.switzerland

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.constraints.digits
import io.github.soleworks.validity.constraints.hasGtinCheckDigit
import io.github.soleworks.validity.constraints.weightedSum
import io.github.soleworks.validity.messages

private const val MODULUS = 11

private val UID_FORMAT = Regex("""(CHE)?\d{9}|CHE-\d{3}\.\d{3}\.\d{3}""")
private val AHV_FORMAT = Regex("""756\d{10}|756\.\d{4}\.\d{4}\.\d{2}""")
private val UID_WEIGHTS = listOf(5, 4, 3, 2, 7, 6, 5, 4)

internal val SWITZERLAND_TAX_IDS: Map<String, (String) -> Boolean> = mapOf(
    "UID" to String::isUid,
    "AHV" to String::isAhv
)

/**
 * Checks that the string is a Swiss UID (business identification number) with a valid check digit, as
 * `CHE-116.281.710`, `CHE116281710` or the 9 digits alone.
 *
 * Fails with code `uid` and, by default, the message `must be a valid UID`
 * ([SwitzerlandMessages.uid][io.github.soleworks.validity.SwitzerlandMessages.uid]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.uid(
    message: String = messages.switzerland.uid
): Unit = constraint(
    message = message,
    code = "uid",
    predicate = String::isUid
)

/**
 * Checks that the string is a Swiss AHV number (AVS, social security number): 13 digits that start with `756` and
 * end with a valid EAN-13 check digit, as `7569217076985` or `756.9217.0769.85`.
 *
 * Fails with code `ahv` and, by default, the message `must be a valid AHV number`
 * ([SwitzerlandMessages.ahv][io.github.soleworks.validity.SwitzerlandMessages.ahv]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.ahv(
    message: String = messages.switzerland.ahv
): Unit = constraint(
    message = message,
    code = "ahv",
    predicate = String::isAhv
)

private fun String.isUid(): Boolean {
    if (!UID_FORMAT.matches(this))
        return false

    val digits = digits()

    return digits.last() == (-digits.weightedSum(UID_WEIGHTS)).mod(MODULUS)
}

private fun String.isAhv(): Boolean = AHV_FORMAT.matches(this) && hasGtinCheckDigit()
