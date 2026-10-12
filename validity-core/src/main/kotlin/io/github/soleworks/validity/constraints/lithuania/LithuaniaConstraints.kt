package io.github.soleworks.validity.constraints.lithuania

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.constraints.estonia.isPersonalCode
import io.github.soleworks.validity.messages

public fun ValidationNode<String>.asmensKodas(
    message: String = messages.lithuania.asmensKodas
): Unit = constraint(
    message = message,
    code = "asmensKodas",
    predicate = { it.isPersonalCode() }
)
