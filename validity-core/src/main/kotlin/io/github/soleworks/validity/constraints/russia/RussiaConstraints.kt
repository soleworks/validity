package io.github.soleworks.validity.constraints.russia

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.constraints.digits
import io.github.soleworks.validity.constraints.weightedSum
import io.github.soleworks.validity.messages

private const val MODULUS = 11
private const val DECIMAL_MODULUS = 10

private val INN_INDIVIDUAL_FORMAT = Regex("""\d{12}""")
private val INN_LEGAL_ENTITY_FORMAT = Regex("""\d{10}""")
private val INN_INDIVIDUAL_FIRST_WEIGHTS = listOf(7, 2, 4, 10, 3, 5, 9, 4, 6, 8)
private val INN_INDIVIDUAL_SECOND_WEIGHTS = listOf(3, 7, 2, 4, 10, 3, 5, 9, 4, 6, 8)
private val INN_LEGAL_ENTITY_WEIGHTS = listOf(2, 4, 10, 3, 5, 9, 4, 6, 8)

internal val RUSSIA_TAX_IDS: Map<String, (String) -> Boolean> = mapOf(
    "INN_INDIVIDUAL" to String::isInnIndividual,
    "INN_LEGAL_ENTITY" to String::isInnLegalEntity
)

/**
 * Checks that the string is the INN of a Russian individual: 12 digits with two valid check digits.
 *
 * Fails with code `innIndividual` and, by default, the message `must be a valid individual INN`
 * ([RussiaMessages.innIndividual][io.github.soleworks.validity.RussiaMessages.innIndividual]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.innIndividual(
    message: String = messages.russia.innIndividual
): Unit = constraint(
    message = message,
    code = "innIndividual",
    predicate = String::isInnIndividual
)

/**
 * Checks that the string is the INN of a Russian legal entity: 10 digits with a valid check digit.
 *
 * Fails with code `innLegalEntity` and, by default, the message `must be a valid legal entity INN`
 * ([RussiaMessages.innLegalEntity][io.github.soleworks.validity.RussiaMessages.innLegalEntity]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.innLegalEntity(
    message: String = messages.russia.innLegalEntity
): Unit = constraint(
    message = message,
    code = "innLegalEntity",
    predicate = String::isInnLegalEntity
)

private fun String.isInnIndividual(): Boolean = INN_INDIVIDUAL_FORMAT.matches(this) && digits().isInnIndividual()

private fun String.isInnLegalEntity(): Boolean = INN_LEGAL_ENTITY_FORMAT.matches(this) && digits().isInnLegalEntity()

private fun List<Int>.checkDigit(
    weights: List<Int>
): Int = weightedSum(weights) % MODULUS % DECIMAL_MODULUS

private fun List<Int>.isInnIndividual(): Boolean = this[lastIndex - 1] == checkDigit(INN_INDIVIDUAL_FIRST_WEIGHTS) &&
    last() == checkDigit(INN_INDIVIDUAL_SECOND_WEIGHTS)

private fun List<Int>.isInnLegalEntity(): Boolean = last() == checkDigit(INN_LEGAL_ENTITY_WEIGHTS)
