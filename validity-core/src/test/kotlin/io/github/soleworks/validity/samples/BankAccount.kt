package io.github.soleworks.validity.samples

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.allOrNoneOf
import io.github.soleworks.validity.validation

data class BankAccount(
    val bank: String? = null,
    val agency: String? = null,
    val account: String? = null
) : Validatable {
    override fun validation() = validation {
        allOrNoneOf(::bank, ::agency, ::account)
    }
}
