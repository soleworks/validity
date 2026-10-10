package io.github.soleworks.validity.constraints.srilanka

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.messages

private val NIC_FORMAT = Regex("[1-9]\\d{8}[vVxX]|[1-9]\\d{11}")

public fun ValidationNode<String>.nic(
    message: String = messages.sriLanka.nic
): Unit = constraint(
    message = message,
    code = "nic",
    predicate = { NIC_FORMAT.matches(it) }
)
