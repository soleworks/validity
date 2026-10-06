package io.github.soleworks.validity.constraints.pakistan

import io.github.soleworks.validity.PakistanMessages
import io.github.soleworks.validity.ValidationNode

private val CNIC_FORMAT = Regex("[1-7]\\d{4}-\\d{7}-[1-9]")

public fun ValidationNode<String>.cnic(
    message: String = PakistanMessages.CNIC
): Unit = constraint(
    message = message,
    predicate = { CNIC_FORMAT.matches(it.trim()) }
)
