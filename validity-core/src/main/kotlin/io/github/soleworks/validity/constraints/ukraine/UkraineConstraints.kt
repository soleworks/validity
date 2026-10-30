package io.github.soleworks.validity.constraints.ukraine

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.constraints.digits
import io.github.soleworks.validity.constraints.weightedSum
import io.github.soleworks.validity.messages

private const val MODULUS = 11
private const val DECIMAL_MODULUS = 10

private val RNOKPP_FORMAT = Regex("""\d{10}""")
private val RNOKPP_WEIGHTS = listOf(-1, 5, 7, 9, 4, 6, 10, 5, 7)

/**
 * Checks that the string is a Ukrainian RNOKPP (tax number): 10 digits with a valid check digit.
 *
 * Fails with code `rnokpp` and, by default, the message `must be a valid RNOKPP`
 * ([UkraineMessages.rnokpp][io.github.soleworks.validity.UkraineMessages.rnokpp]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.rnokpp(
    message: String = messages.ukraine.rnokpp
): Unit = constraint(
    message = message,
    code = "rnokpp",
    predicate = { RNOKPP_FORMAT.matches(it) && it.digits().isRnokpp() }
)

private fun List<Int>.isRnokpp(): Boolean = last() == weightedSum(RNOKPP_WEIGHTS) % MODULUS % DECIMAL_MODULUS
