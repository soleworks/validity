package io.github.soleworks.validity.constraints.argentina

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.messages

private const val MODULUS = 11
private const val PREFIX_LENGTH = 2

private val TAX_ID_FORMAT = Regex("\\d{11}|\\d{2}-\\d{8}-\\d")
private val WEIGHTS = listOf(5, 4, 3, 2, 7, 6, 5, 4, 3, 2)
private val CUIL_PREFIXES = setOf("20", "23", "24", "25", "26", "27")
private val CUIT_PREFIXES = CUIL_PREFIXES + setOf("30", "33", "34")

public fun ValidationNode<String>.cuit(
    message: String = messages.argentina.cuit
): Unit = constraint(
    message = message,
    code = "cuit",
    predicate = { TAX_ID_FORMAT.matches(it) && it.isTaxId(CUIT_PREFIXES) }
)

public fun ValidationNode<String>.cuil(
    message: String = messages.argentina.cuil
): Unit = constraint(
    message = message,
    code = "cuil",
    predicate = { TAX_ID_FORMAT.matches(it) && it.isTaxId(CUIL_PREFIXES) }
)

private fun String.isTaxId(prefixes: Set<String>): Boolean {
    val digits = filter(Char::isDigit).map(Char::digitToInt)
    val sum = digits.zip(WEIGHTS) { digit, weight -> digit * weight }.sum()

    return digits.take(PREFIX_LENGTH).joinToString("") in prefixes && (MODULUS - sum % MODULUS) % MODULUS == digits.last()
}
