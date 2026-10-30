package io.github.soleworks.validity.constraints.brazil

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.constraints.digits
import io.github.soleworks.validity.constraints.weightedSum
import io.github.soleworks.validity.messages

private const val MODULUS = 11
private const val MAX_DIGIT = 9
private const val CNH_DISCOUNT = 2

private val CPF_FORMAT = Regex("""\d{11}|\d{3}\.\d{3}\.\d{3}-\d{2}""")
private val CNPJ_FORMAT = Regex("""[0-9A-Z]{12}\d{2}|[0-9A-Z]{2}\.[0-9A-Z]{3}\.[0-9A-Z]{3}/[0-9A-Z]{4}-\d{2}""")
private val CNH_FORMAT = Regex("""\d{11}""")
private val PIS_FORMAT = Regex("""\d{11}|\d{3}\.\d{5}\.\d{2}-\d""")
private val CEP_FORMAT = Regex("""\d{5}-?\d{3}""")
private val PLACA_FORMAT = Regex("""[A-Z]{3}[- ]?\d[A-Z0-9]\d{2}""")
private val CPF_WEIGHTS = (11 downTo 2).toList()
private val CNPJ_WEIGHTS = listOf(6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2)
private val PIS_WEIGHTS = listOf(3, 2, 9, 8, 7, 6, 5, 4, 3, 2)
private val CNH_FIRST_WEIGHTS = (9 downTo 1).toList()
private val CNH_SECOND_WEIGHTS = (1..9).toList()

/**
 * Checks that the string is a Brazilian CPF with valid check digits, as 11 digits or as `000.000.000-00`.
 *
 * A number with all digits the same fails.
 *
 * Fails with code `cpf` and, by default, the message `must be a valid CPF`
 * ([BrazilMessages.cpf][io.github.soleworks.validity.BrazilMessages.cpf]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.cpf(
    message: String = messages.brazil.cpf
): Unit = constraint(
    message = message,
    code = "cpf",
    predicate = { CPF_FORMAT.matches(it) && it.digits().isCpf() }
)

/**
 * Checks that the string is a Brazilian CNPJ with valid check digits, as 14 characters or as `00.000.000/0000-00`.
 *
 * The first 12 characters can be uppercase letters, as in the alphanumeric CNPJ.
 *
 * Fails with code `cnpj` and, by default, the message `must be a valid CNPJ`
 * ([BrazilMessages.cnpj][io.github.soleworks.validity.BrazilMessages.cnpj]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.cnpj(
    message: String = messages.brazil.cnpj
): Unit = constraint(
    message = message,
    code = "cnpj",
    predicate = { CNPJ_FORMAT.matches(it) && it.cnpjValues().isCnpj() }
)

/**
 * Checks that the string is a Brazilian CNH (driver's license number): 11 digits with valid check digits.
 *
 * A number with all digits the same fails.
 *
 * Fails with code `cnh` and, by default, the message `must be a valid CNH`
 * ([BrazilMessages.cnh][io.github.soleworks.validity.BrazilMessages.cnh]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.cnh(
    message: String = messages.brazil.cnh
): Unit = constraint(
    message = message,
    code = "cnh",
    predicate = { CNH_FORMAT.matches(it) && it.digits().isCnh() }
)

/**
 * Checks that the string is a Brazilian PIS/PASEP/NIT with a valid check digit, as 11 digits or as `000.00000.00-0`.
 *
 * A number with all digits the same fails.
 *
 * Fails with code `pis` and, by default, the message `must be a valid PIS`
 * ([BrazilMessages.pis][io.github.soleworks.validity.BrazilMessages.pis]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.pis(
    message: String = messages.brazil.pis
): Unit = constraint(
    message = message,
    code = "pis",
    predicate = { PIS_FORMAT.matches(it) && it.digits().isPis() }
)

/**
 * Checks that the string is a Brazilian CEP: 8 digits, with or without the dash, such as `01310-100`.
 *
 * Fails with code `cep` and, by default, the message `must be a valid CEP`
 * ([BrazilMessages.cep][io.github.soleworks.validity.BrazilMessages.cep]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.cep(
    message: String = messages.brazil.cep
): Unit = constraint(
    message = message,
    code = "cep",
    predicate = { CEP_FORMAT.matches(it) }
)

/**
 * Checks that the string is a Brazilian license plate, in the old (`ABC-1234`) or the Mercosul (`ABC1D23`) layout.
 *
 * Letters are uppercase, and the separator can be a dash, a space or nothing.
 *
 * Fails with code `placa` and, by default, the message `must be a valid license plate`
 * ([BrazilMessages.placa][io.github.soleworks.validity.BrazilMessages.placa]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.placa(
    message: String = messages.brazil.placa
): Unit = constraint(
    message = message,
    code = "placa",
    predicate = { PLACA_FORMAT.matches(it) }
)

private fun String.cnpjValues(): List<Int> = filter(Char::isLetterOrDigit).map { char -> char.code - '0'.code }

private fun List<Int>.isRepeated(): Boolean = all { it == first() }

private fun List<Int>.checkDigit(
    weights: List<Int>
): Int {
    val remainder = weightedSum(weights) % MODULUS

    return if (remainder < 2) 0 else MODULUS - remainder
}

private fun List<Int>.hasCheckDigits(
    weights: List<Int>
): Boolean = this[lastIndex - 1] == dropLast(2).checkDigit(weights.drop(1)) &&
    last() == dropLast(1).checkDigit(weights)

private fun Int.singleDigit(): Int = if (this > MAX_DIGIT) 0 else this

private fun List<Int>.isCpf(): Boolean = !isRepeated() && hasCheckDigits(CPF_WEIGHTS)

private fun List<Int>.isCnpj(): Boolean = any { it != 0 } && hasCheckDigits(CNPJ_WEIGHTS)

private fun List<Int>.isPis(): Boolean = !isRepeated() && last() == dropLast(1).checkDigit(PIS_WEIGHTS)

private fun List<Int>.isCnh(): Boolean {
    val first = weightedSum(CNH_FIRST_WEIGHTS) % MODULUS
    val discount = if (first > MAX_DIGIT) CNH_DISCOUNT else 0
    val second = (weightedSum(CNH_SECOND_WEIGHTS) % MODULUS - discount).mod(MODULUS)

    return !isRepeated() && this[lastIndex - 1] == first.singleDigit() && last() == second.singleDigit()
}
