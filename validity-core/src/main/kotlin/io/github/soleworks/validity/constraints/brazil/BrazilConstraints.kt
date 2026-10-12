package io.github.soleworks.validity.constraints.brazil

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.messages

private const val MODULUS = 11

private val CPF_FORMAT = Regex("\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")
private val CNPJ_FORMAT = Regex("[0-9A-Z]{12}\\d{2}|[0-9A-Z]{2}\\.[0-9A-Z]{3}\\.[0-9A-Z]{3}/[0-9A-Z]{4}-\\d{2}")
private val CNH_FORMAT = Regex("\\d{11}")
private val PIS_FORMAT = Regex("\\d{11}|\\d{3}\\.\\d{5}\\.\\d{2}-\\d")
private val CEP_FORMAT = Regex("\\d{5}-?\\d{3}")
private val PLACA_FORMAT = Regex("[A-Z]{3}[- ]?\\d[A-Z0-9]\\d{2}")

private val CNPJ_WEIGHTS = listOf(6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2)
private val PIS_WEIGHTS = listOf(3, 2, 9, 8, 7, 6, 5, 4, 3, 2)

public fun ValidationNode<String>.cpf(
    message: String = messages.brazil.cpf
): Unit = constraint(
    message = message,
    code = "cpf",
    predicate = { CPF_FORMAT.matches(it) && it.digits().isCpf() }
)

public fun ValidationNode<String>.cnpj(
    message: String = messages.brazil.cnpj
): Unit = constraint(
    message = message,
    code = "cnpj",
    predicate = { CNPJ_FORMAT.matches(it) && it.cnpjValues().isCnpj() }
)

public fun ValidationNode<String>.cnh(
    message: String = messages.brazil.cnh
): Unit = constraint(
    message = message,
    code = "cnh",
    predicate = { CNH_FORMAT.matches(it) && it.digits().isCnh() }
)

public fun ValidationNode<String>.pis(
    message: String = messages.brazil.pis
): Unit = constraint(
    message = message,
    code = "pis",
    predicate = { PIS_FORMAT.matches(it) && it.digits().isPis() }
)

public fun ValidationNode<String>.cep(
    message: String = messages.brazil.cep
): Unit = constraint(
    message = message,
    code = "cep",
    predicate = { CEP_FORMAT.matches(it) }
)

public fun ValidationNode<String>.placa(
    message: String = messages.brazil.placa
): Unit = constraint(
    message = message,
    code = "placa",
    predicate = { PLACA_FORMAT.matches(it) }
)

private fun String.digits(): List<Int> = filter(Char::isDigit).map(Char::digitToInt)

private fun String.cnpjValues(): List<Int> = filter(Char::isLetterOrDigit).map { char -> char.code - '0'.code }

private fun List<Int>.isRepeated(): Boolean = all { it == first() }

private fun List<Int>.checkDigit(
    weights: List<Int>
): Int {
    val remainder = zip(weights) { value, weight -> value * weight }.sum() % MODULUS

    return if (remainder < 2) 0 else MODULUS - remainder
}

private fun List<Int>.isCpf(): Boolean = !isRepeated() &&
    this[9] == take(9).checkDigit((10 downTo 2).toList()) &&
    this[10] == take(10).checkDigit((11 downTo 2).toList())

private fun List<Int>.isCnpj(): Boolean = any { it != 0 } &&
    this[12] == take(12).checkDigit(CNPJ_WEIGHTS.drop(1)) &&
    this[13] == take(13).checkDigit(CNPJ_WEIGHTS)

private fun List<Int>.isPis(): Boolean = !isRepeated() && this[10] == take(10).checkDigit(PIS_WEIGHTS)

private fun List<Int>.isCnh(): Boolean {
    val first = take(9).zip(9 downTo 1) { digit, weight -> digit * weight }.sum() % MODULUS
    val second = take(9).zip(1..9) { digit, weight -> digit * weight }.sum() % MODULUS - if (first > 9) 2 else 0

    val firstDigit = if (first > 9) 0 else first
    val secondDigit = (if (second < 0) second + MODULUS else second).let { if (it > 9) 0 else it }

    return !isRepeated() && this[9] == firstDigit && this[10] == secondDigit
}
