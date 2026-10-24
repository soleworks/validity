package io.github.soleworks.validity.constraints.netherlands

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.constraints.digits
import io.github.soleworks.validity.constraints.weightedSum
import io.github.soleworks.validity.messages

private const val MODULUS = 11

private val BSN_FORMAT = Regex("""\d{9}""")
private val BSN_WEIGHTS = (9 downTo 2).toList()

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
