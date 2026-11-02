package io.github.soleworks.validity.constraints.china

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.messages

private const val ALPHABET = "0123456789ABCDEFGHJKLMNPQRTUWXY"

private val USCC_FORMAT = Regex("""\d{8}[\dA-HJ-NPQRTUWXY]{10}""")
private val USCC_WEIGHTS = listOf(1, 3, 9, 27, 19, 26, 16, 17, 20, 29, 25, 13, 8, 24, 10, 30, 28)

internal val CHINA_TAX_IDS: Map<String, (String) -> Boolean> = mapOf(
    "USCC" to String::isUscc
)

/**
 * Checks that the string is a Chinese USCC (unified social credit code): 18 characters with a valid check character,
 * such as `91110000600037341L`.
 *
 * The first 8 characters are digits, and the others are digits or uppercase letters other than `I`, `O`, `S`, `V`
 * and `Z`.
 *
 * Fails with code `uscc` and, by default, the message `must be a valid unified social credit code`
 * ([ChinaMessages.uscc][io.github.soleworks.validity.ChinaMessages.uscc]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.uscc(
    message: String = messages.china.uscc
): Unit = constraint(
    message = message,
    code = "uscc",
    predicate = String::isUscc
)

private fun String.isUscc(): Boolean {
    if (!USCC_FORMAT.matches(this))
        return false

    val sum = asIterable().zip(USCC_WEIGHTS) { char, weight -> ALPHABET.indexOf(char) * weight }.sum()

    return last() == ALPHABET[(-sum).mod(ALPHABET.length)]
}
