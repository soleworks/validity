package io.github.soleworks.validity.constraints.srilanka

import io.github.soleworks.validity.SriLankaMessages
import io.github.soleworks.validity.ValidationNode

private val NIC_FORMAT = Regex("[1-9]\\d{8}[vVxX]|[1-9]\\d{11}")

public fun ValidationNode<String>.nic(
    message: String = SriLankaMessages.NIC
): Unit = constraint(
    message = message,
    predicate = { NIC_FORMAT.matches(it) }
)
