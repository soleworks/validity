package io.github.soleworks.validity.constraints.unitedstates

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.messages

private const val PREFIX_LENGTH = 2
private const val EMPTY_AREA = "000"
private const val UNASSIGNED_AREA = "666"
private const val EMPTY_GROUP = "00"
private const val EMPTY_SERIAL = "0000"
private const val ITIN_AREA_PREFIX = '9'

private val EIN_FORMAT = Regex("""\d{2}[- ]?\d{7}""")
private val EIN_PREFIXES = setOf(
    "01", "02", "03", "04", "05", "06", "10", "11", "12", "13", "14", "15", "16", "20", "21", "22",
    "23", "24", "25", "26", "27", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40",
    "41", "42", "43", "44", "45", "46", "47", "48", "50", "51", "52", "53", "54", "55", "56", "57",
    "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "71", "72", "73", "74", "75",
    "76", "77", "80", "81", "82", "83", "84", "85", "86", "87", "88", "90", "91", "92", "93", "94",
    "95", "98", "99"
)
private val SSN_FORMAT = Regex("""(\d{3})-?(\d{2})-?(\d{4})""")
private val SSN_EXAMPLES = setOf("078051120", "457555462", "219099999")
private val ITIN_GROUPS = (70..99).map(Int::toString).toSet() - setOf("89", "93")

internal val UNITED_STATES_TAX_IDS: Map<String, (String) -> Boolean> = mapOf(
    "EIN" to String::isEin,
    "SSN" to String::isSsn,
    "ITIN" to String::isItin
)

/**
 * Checks that the string is a US EIN with a prefix the IRS assigns, such as `12-3456789`.
 *
 * The separator can be a dash, a space or nothing.
 *
 * Fails with code `ein` and, by default, the message `must be a valid EIN`
 * ([UnitedStatesMessages.ein][io.github.soleworks.validity.UnitedStatesMessages.ein]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.ein(
    message: String = messages.unitedStates.ein
): Unit = constraint(
    message = message,
    code = "ein",
    predicate = String::isEin
)

/**
 * Checks that the string is a US SSN that can be assigned, as `123-45-6789` or `123456789`.
 *
 * The area (first 3 digits) is not `000`, `666` or `900` to `999`, the group (next 2) is not `00` and the serial
 * (last 4) is not `0000`. Numbers the SSA has published as examples, such as `078-05-1120`, fail too.
 *
 * Fails with code `ssn` and, by default, the message `must be a valid SSN`
 * ([UnitedStatesMessages.ssn][io.github.soleworks.validity.UnitedStatesMessages.ssn]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.ssn(
    message: String = messages.unitedStates.ssn
): Unit = constraint(
    message = message,
    code = "ssn",
    predicate = String::isSsn
)

/**
 * Checks that the string is a US ITIN, as `912-70-1234` or `912701234`.
 *
 * The first digit is `9`, and the group (digits 4 and 5) is from `70` to `99`, except `89` and `93`.
 *
 * Fails with code `itin` and, by default, the message `must be a valid ITIN`
 * ([UnitedStatesMessages.itin][io.github.soleworks.validity.UnitedStatesMessages.itin]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.itin(
    message: String = messages.unitedStates.itin
): Unit = constraint(
    message = message,
    code = "itin",
    predicate = String::isItin
)

private fun String.isEin(): Boolean = EIN_FORMAT.matches(this) && take(PREFIX_LENGTH) in EIN_PREFIXES

private fun String.isSsn(): Boolean {
    val (area, group, serial) = SSN_FORMAT.matchEntire(this)?.destructured ?: return false

    return area != EMPTY_AREA &&
        area != UNASSIGNED_AREA &&
        area.first() != ITIN_AREA_PREFIX &&
        group != EMPTY_GROUP &&
        serial != EMPTY_SERIAL &&
        area + group + serial !in SSN_EXAMPLES
}

private fun String.isItin(): Boolean {
    val (area, group) = SSN_FORMAT.matchEntire(this)?.destructured ?: return false

    return area.first() == ITIN_AREA_PREFIX && group in ITIN_GROUPS
}
