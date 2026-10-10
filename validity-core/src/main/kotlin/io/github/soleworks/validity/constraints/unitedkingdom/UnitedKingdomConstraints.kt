package io.github.soleworks.validity.constraints.unitedkingdom

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.messages

private val NINO_FORMAT = Regex(
    "(?!GB|NK|TN|ZZ)(?![DFIQUV])[A-Z](?![DFIQUVO])[A-Z]\\d{6}[ABCD ]",
    RegexOption.IGNORE_CASE
)

public fun ValidationNode<String>.nino(
    message: String = messages.unitedKingdom.nino
): Unit = constraint(
    message = message,
    code = "nino",
    predicate = { NINO_FORMAT.matches(it) }
)
