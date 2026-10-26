package io.github.soleworks.validity.constraints.netherlands

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.constraints.digits
import io.github.soleworks.validity.constraints.weightedSum
import io.github.soleworks.validity.messages

private const val MODULUS = 11

private val BSN_FORMAT = Regex("""\d{9}""")
private val BSN_WEIGHTS = (9 downTo 2).toList()

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
    predicate = { BSN_FORMAT.matches(it) && it.digits().isBsn() }
)

private fun List<Int>.isBsn(): Boolean {
    val sum = weightedSum(BSN_WEIGHTS) - last()

    return sum != 0 && sum % MODULUS == 0
}
