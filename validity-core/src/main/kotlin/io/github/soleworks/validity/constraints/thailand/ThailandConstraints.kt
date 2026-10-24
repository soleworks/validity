package io.github.soleworks.validity.constraints.thailand

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.constraints.digits
import io.github.soleworks.validity.constraints.weightedSum
import io.github.soleworks.validity.messages

private const val MODULUS = 11
private const val DECIMAL_MODULUS = 10

private val NATIONAL_ID_FORMAT = Regex("""[1-8]\d{12}""")
private val NATIONAL_ID_WEIGHTS = (13 downTo 2).toList()

public fun ValidationNode<String>.nationalId(
    message: String = messages.thailand.nationalId
): Unit = constraint(
    message = message,
    code = "nationalId",
    predicate = { NATIONAL_ID_FORMAT.matches(it) && it.digits().isNationalId() }
)

private fun List<Int>.isNationalId(): Boolean =
    last() == (MODULUS - weightedSum(NATIONAL_ID_WEIGHTS) % MODULUS) % DECIMAL_MODULUS
