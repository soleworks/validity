package io.github.soleworks.validity.constraints.tunisia

import io.github.soleworks.validity.TunisiaMessages
import io.github.soleworks.validity.ValidationNode

private val CIN_FORMAT = Regex("\\d{8}")

public fun ValidationNode<String>.cin(
    message: String = TunisiaMessages.CIN
): Unit = constraint(
    message = message,
    predicate = { CIN_FORMAT.matches(it.trim()) }
)
