package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.messages

public fun ValidationNode<Boolean>.isTrue(
    message: String = messages.mustBeTrue
): Unit = constraint(
    message = message,
    code = "isTrue",
    predicate = { it }
)

public fun ValidationNode<Boolean>.isFalse(
    message: String = messages.mustBeFalse
): Unit = constraint(
    message = message,
    code = "isFalse",
    predicate = { !it }
)
