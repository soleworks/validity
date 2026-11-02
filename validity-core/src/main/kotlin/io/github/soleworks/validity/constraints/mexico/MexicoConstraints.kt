package io.github.soleworks.validity.constraints.mexico

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.constraints.groups
import io.github.soleworks.validity.constraints.isDate
import io.github.soleworks.validity.messages

private const val CENTURY = 2000

private val RFC_FORMAT = Regex("""[A-ZÑ&]{3,4}(\d{2})(\d{2})(\d{2})[A-Z\d]{3}""")

internal val MEXICO_TAX_IDS: Map<String, (String) -> Boolean> = mapOf(
    "RFC" to String::isRfc
)

/**
 * Checks that the string is a Mexican RFC: 3 letters (a company) or 4 letters (a person), the date of registration or
 * birth as `YYMMDD`, and a 3-character homoclave, such as `GODE561231GR8`.
 *
 * Letters are uppercase, and `Ñ` and `&` are accepted in the first part. The date must exist. The homoclave is not
 * checked against the name, since it is assigned by the SAT.
 *
 * Fails with code `rfc` and, by default, the message `must be a valid RFC`
 * ([MexicoMessages.rfc][io.github.soleworks.validity.MexicoMessages.rfc]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.rfc(
    message: String = messages.mexico.rfc
): Unit = constraint(
    message = message,
    code = "rfc",
    predicate = String::isRfc
)

private fun String.isRfc(): Boolean {
    val (year, month, day) = RFC_FORMAT.groups(this) ?: return false

    return isDate(year = CENTURY + year.toInt(), month = month.toInt(), day = day.toInt())
}
