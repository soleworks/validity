package io.github.soleworks.validity.constraints.ukraine

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.constraints.digits
import io.github.soleworks.validity.constraints.weightedSum
import io.github.soleworks.validity.messages

private const val MODULUS = 11
private const val DECIMAL_MODULUS = 10

private val RNOKPP_FORMAT = Regex("""\d{10}""")
private val RNOKPP_WEIGHTS = listOf(-1, 5, 7, 9, 4, 6, 10, 5, 7)

public fun ValidationNode<String>.rnokpp(
    message: String = messages.ukraine.rnokpp
): Unit = constraint(
    message = message,
    code = "rnokpp",
    predicate = { RNOKPP_FORMAT.matches(it) && it.digits().isRnokpp() }
)

private fun List<Int>.isRnokpp(): Boolean = last() == weightedSum(RNOKPP_WEIGHTS) % MODULUS % DECIMAL_MODULUS
