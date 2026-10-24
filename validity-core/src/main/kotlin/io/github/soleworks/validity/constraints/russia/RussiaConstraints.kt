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

public fun ValidationNode<String>.innIndividual(
    message: String = messages.russia.innIndividual
): Unit = constraint(
    message = message,
    code = "innIndividual",
    predicate = { INN_INDIVIDUAL_FORMAT.matches(it) && it.digits().isInnIndividual() }
)

public fun ValidationNode<String>.innLegalEntity(
    message: String = messages.russia.innLegalEntity
): Unit = constraint(
    message = message,
    code = "innLegalEntity",
    predicate = { INN_LEGAL_ENTITY_FORMAT.matches(it) && it.digits().isInnLegalEntity() }
)

private fun List<Int>.checkDigit(
    weights: List<Int>
): Int = weightedSum(weights) % MODULUS % DECIMAL_MODULUS

private fun List<Int>.isInnIndividual(): Boolean = this[lastIndex - 1] == checkDigit(INN_INDIVIDUAL_FIRST_WEIGHTS) &&
    last() == checkDigit(INN_INDIVIDUAL_SECOND_WEIGHTS)

private fun List<Int>.isInnLegalEntity(): Boolean = last() == checkDigit(INN_LEGAL_ENTITY_WEIGHTS)
