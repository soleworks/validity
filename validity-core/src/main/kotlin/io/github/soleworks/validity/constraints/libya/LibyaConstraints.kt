package io.github.soleworks.validity.constraints.libya

import io.github.soleworks.validity.LibyaMessages
import io.github.soleworks.validity.ValidationNode

private val NIN_FORMAT = Regex("[12]\\d{11}")

public fun ValidationNode<String>.nin(
    message: String = LibyaMessages.NIN
): Unit = constraint(
    message = message,
    predicate = { NIN_FORMAT.matches(it.trim()) }
)
