package io.github.soleworks.validity.constraints.unitedstates

import io.github.soleworks.validity.UnitedStatesMessages
import io.github.soleworks.validity.ValidationNode

private const val PREFIX_LENGTH = 2

private val EIN_FORMAT = Regex("\\d{2}[- ]?\\d{7}")
private val EIN_PREFIXES = setOf(
    "01", "02", "03", "04", "05", "06", "10", "11", "12", "13", "14", "15", "16", "20", "21", "22", "23", "24", "25", "26",
    "27", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48",
    "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "71",
    "72", "73", "74", "75", "76", "77", "80", "81", "82", "83", "84", "85", "86", "87", "88", "90", "91", "92", "93", "94",
    "95", "98", "99"
)

public fun ValidationNode<String>.ein(
    message: String = UnitedStatesMessages.EIN
): Unit = constraint(
    message = message,
    predicate = { EIN_FORMAT.matches(it) && it.take(PREFIX_LENGTH) in EIN_PREFIXES }
)
