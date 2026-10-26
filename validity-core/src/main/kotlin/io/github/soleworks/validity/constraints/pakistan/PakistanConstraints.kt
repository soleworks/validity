package io.github.soleworks.validity.constraints.pakistan

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.messages

private val CNIC_FORMAT = Regex("""[1-7]\d{4}-\d{7}-[1-9]""")

/**
 * Checks that the string is a Pakistani CNIC written as `00000-0000000-0`.
 *
 * Whitespace around the number is ignored.
 *
 * Fails with code `cnic` and, by default, the message `must be a valid CNIC`
 * ([PakistanMessages.cnic][io.github.soleworks.validity.PakistanMessages.cnic]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.cnic(
    message: String = messages.pakistan.cnic
): Unit = constraint(
    message = message,
    code = "cnic",
    predicate = { CNIC_FORMAT.matches(it.trim()) }
)
