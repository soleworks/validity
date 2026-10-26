package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.messages

/**
 * Checks that the value is `true`.
 *
 * Fails with code `isTrue` and, by default, the message `must be true`
 * ([Messages.mustBeTrue][io.github.soleworks.validity.Messages.mustBeTrue]).
 *
 * ```kotlin
 * ::acceptedTerms required { isTrue() }
 * ```
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Boolean>.isTrue(
    message: String = messages.mustBeTrue
): Unit = constraint(
    message = message,
    code = "isTrue",
    predicate = { it }
)

/**
 * Checks that the value is `false`.
 *
 * Fails with code `isFalse` and, by default, the message `must be false`
 * ([Messages.mustBeFalse][io.github.soleworks.validity.Messages.mustBeFalse]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Boolean>.isFalse(
    message: String = messages.mustBeFalse
): Unit = constraint(
    message = message,
    code = "isFalse",
    predicate = { !it }
)
