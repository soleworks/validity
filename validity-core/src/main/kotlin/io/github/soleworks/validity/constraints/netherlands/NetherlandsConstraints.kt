package io.github.soleworks.validity.constraints.netherlands

import io.github.soleworks.validity.NetherlandsMessages
import io.github.soleworks.validity.ValidationNode

private const val MODULUS = 11

private val BSN_FORMAT = Regex("\\d{9}")

private val BSN_WEIGHTS = (9 downTo 2).toList()

public fun ValidationNode<String>.bsn(
    message: String = NetherlandsMessages.BSN
): Unit = constraint(
    message = message,
    predicate = { BSN_FORMAT.matches(it) && it.map(Char::digitToInt).isBsn() }
)

private fun List<Int>.isBsn(): Boolean {
    val sum = take(BSN_WEIGHTS.size).zip(BSN_WEIGHTS) { digit, weight -> digit * weight }.sum() - last()

    return sum != 0 && sum % MODULUS == 0
}
