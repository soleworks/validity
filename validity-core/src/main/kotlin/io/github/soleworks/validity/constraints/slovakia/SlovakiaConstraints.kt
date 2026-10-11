package io.github.soleworks.validity.constraints.slovakia

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.constraints.czechrepublic.isRodneCislo
import io.github.soleworks.validity.messages

public fun ValidationNode<String>.rodneCislo(
    message: String = messages.slovakia.rodneCislo
): Unit = constraint(
    message = message,
    code = "rodneCislo",
    predicate = { it.isRodneCislo() }
)
