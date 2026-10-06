package io.github.soleworks.validity.constraints.brazil

import io.github.soleworks.validity.BrazilMessages
import io.github.soleworks.validity.ValidationNode

private const val MODULUS = 11

private val CPF_FORMAT = Regex("\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")
private val CNPJ_FORMAT = Regex("[0-9A-Z]{12}\\d{2}|[0-9A-Z]{2}\\.[0-9A-Z]{3}\\.[0-9A-Z]{3}/[0-9A-Z]{4}-\\d{2}")
private val CNH_FORMAT = Regex("\\d{11}")
private val PIS_FORMAT = Regex("\\d{11}|\\d{3}\\.\\d{5}\\.\\d{2}-\\d")
private val TITULO_ELEITORAL_FORMAT = Regex("\\d{12}")
private val CHAVE_NFE_FORMAT = Regex("\\d{44}")
private val CEP_FORMAT = Regex("\\d{5}-?\\d{3}")
private val PLACA_FORMAT = Regex("[A-Z]{3}[- ]?\\d[A-Z0-9]\\d{2}")

private val CNPJ_WEIGHTS = listOf(6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2)
private val PIS_WEIGHTS = listOf(3, 2, 9, 8, 7, 6, 5, 4, 3, 2)
private val CHAVE_NFE_WEIGHTS = (0 until 43).map { index -> 2 + (42 - index) % 8 }
private val TITULO_ELEITORAL_STATES = 1..28
private val SAO_PAULO_AND_MINAS_GERAIS = setOf(1, 2)

public fun ValidationNode<String>.cpf(
    message: String = BrazilMessages.CPF
): Unit = constraint(
    message = message,
    predicate = { CPF_FORMAT.matches(it) && it.digits().isCpf() }
)

public fun ValidationNode<String>.cnpj(
    message: String = BrazilMessages.CNPJ
): Unit = constraint(
    message = message,
    predicate = { CNPJ_FORMAT.matches(it) && it.cnpjValues().isCnpj() }
)

public fun ValidationNode<String>.cnh(
    message: String = BrazilMessages.CNH
): Unit = constraint(
    message = message,
    predicate = { CNH_FORMAT.matches(it) && it.digits().isCnh() }
)

public fun ValidationNode<String>.pis(
    message: String = BrazilMessages.PIS
): Unit = constraint(
    message = message,
    predicate = { PIS_FORMAT.matches(it) && it.digits().isPis() }
)

public fun ValidationNode<String>.tituloEleitoral(
    message: String = BrazilMessages.TITULO_ELEITORAL
): Unit = constraint(
    message = message,
    predicate = { TITULO_ELEITORAL_FORMAT.matches(it) && it.digits().isTituloEleitoral() }
)

public fun ValidationNode<String>.chaveNfe(
    message: String = BrazilMessages.CHAVE_NFE
): Unit = constraint(
    message = message,
    predicate = { CHAVE_NFE_FORMAT.matches(it) && it.digits().isChaveNfe() }
)

public fun ValidationNode<String>.cep(
    message: String = BrazilMessages.CEP
): Unit = constraint(
    message = message,
    predicate = { CEP_FORMAT.matches(it) }
)

public fun ValidationNode<String>.placa(
    message: String = BrazilMessages.PLACA
): Unit = constraint(
    message = message,
    predicate = { PLACA_FORMAT.matches(it) }
)

private fun String.digits(): List<Int> = filter(Char::isDigit).map(Char::digitToInt)

private fun String.cnpjValues(): List<Int> = filter(Char::isLetterOrDigit).map { char -> char.code - '0'.code }

private fun List<Int>.isRepeated(): Boolean = all { it == first() }

private fun List<Int>.checkDigit(weights: List<Int>): Int {
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

private fun List<Int>.isChaveNfe(): Boolean = this[43] == take(43).checkDigit(CHAVE_NFE_WEIGHTS)

private fun List<Int>.isCnh(): Boolean {
    val first = take(9).zip(9 downTo 1) { digit, weight -> digit * weight }.sum() % MODULUS
    val second = take(9).zip(1..9) { digit, weight -> digit * weight }.sum() % MODULUS - if (first > 9) 2 else 0

    val firstDigit = if (first > 9) 0 else first
    val secondDigit = when {
        second < 0 -> second + MODULUS
        second > 9 -> 0
        else -> second
    }

    return !isRepeated() && this[9] == firstDigit && this[10] == secondDigit
}

private fun List<Int>.isTituloEleitoral(): Boolean {
    val state = this[8] * 10 + this[9]
    val first = tituloEleitoralCheckDigit(take(8).zip(2..9) { digit, weight -> digit * weight }.sum(), state)
    val second = tituloEleitoralCheckDigit(this[8] * 7 + this[9] * 8 + first * 9, state)

    return state in TITULO_ELEITORAL_STATES && this[10] == first && this[11] == second
}

private fun tituloEleitoralCheckDigit(sum: Int, state: Int): Int = when (val remainder = sum % MODULUS) {
    10 -> 0
    0 -> if (state in SAO_PAULO_AND_MINAS_GERAIS) 1 else 0
    else -> remainder
}
