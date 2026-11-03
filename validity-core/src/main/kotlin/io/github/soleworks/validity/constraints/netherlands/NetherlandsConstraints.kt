package io.github.soleworks.validity.constraints.netherlands

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.constraints.digits
import io.github.soleworks.validity.constraints.weightedSum
import io.github.soleworks.validity.messages

private const val MODULUS = 11

private val BSN_FORMAT = Regex("""\d{9}""")
private val BSN_WEIGHTS = (9 downTo 2).toList()
private val KVK_FORMAT = Regex("""\d{8}""")

internal val NETHERLANDS_TAX_IDS: Map<String, (String) -> Boolean> = mapOf(
    "BSN" to String::isBsn,
    "KVK" to String::isKvk
)

/**
 * Checks that the string is a Dutch BSN: 9 digits that pass the eleven test.
 *
 * Fails with code `bsn` and, by default, the message `must be a valid BSN`
 * ([NetherlandsMessages.bsn][io.github.soleworks.validity.NetherlandsMessages.bsn]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.bsn(
    message: String = messages.netherlands.bsn
): Unit = constraint(
    message = message,
    code = "bsn",
    predicate = String::isBsn
)

/**
 * Checks that the string is a Dutch KvK number (Kamer van Koophandel): 8 digits, such as `69599084`.
 *
 * The KvK number has had no check digit since 2016, so only the format is checked.
 *
 * Fails with code `kvk` and, by default, the message `must be a valid KvK number`
 * ([NetherlandsMessages.kvk][io.github.soleworks.validity.NetherlandsMessages.kvk]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.kvk(
    message: String = messages.netherlands.kvk
): Unit = constraint(
    message = message,
    code = "kvk",
    predicate = String::isKvk
)

private fun String.isBsn(): Boolean = BSN_FORMAT.matches(this) && digits().isBsn()

private fun List<Int>.isBsn(): Boolean {
    val sum = weightedSum(BSN_WEIGHTS) - last()

    return sum != 0 && sum % MODULUS == 0
}

private fun String.isKvk(): Boolean = KVK_FORMAT.matches(this)
