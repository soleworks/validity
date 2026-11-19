package io.github.soleworks.validity.constraints.pakistan

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.messages

private val CNIC_FORMAT = Regex("""[1-7]\d{4}-\d{7}-[1-9]""")

internal val PAKISTAN_TAX_IDS: Map<String, (String) -> Boolean> = mapOf(
    "CNIC" to String::isCnic
)

/**
 * Checks that the string is a Pakistani CNIC written as `NNNNN-NNNNNNN-N`, such as `35202-1234567-1`.
 *
 * The first digit is 1 to 7 and the last 1 to 9. Whitespace around the number is ignored.
 *
 * Fails with code `cnic` and, by default, the message `must be a valid CNIC`
 * ([PakistanMessages.cnic]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.cnic(
    message: String = messages.pakistan.cnic
): Unit = constraint(
    message = message,
    code = "cnic",
    predicate = String::isCnic
)

private fun String.isCnic(): Boolean = CNIC_FORMAT.matches(trim())
