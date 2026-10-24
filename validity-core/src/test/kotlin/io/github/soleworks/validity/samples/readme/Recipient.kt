package io.github.soleworks.validity.samples.readme

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.atLeastOneOf
import io.github.soleworks.validity.exactlyOneOf
import io.github.soleworks.validity.validation

data class Recipient(
    val email: String?,
    val phone: String?,
    val iban: String?,
    val cardNumber: String?
) : Validatable {
    override fun validation() = validation {
        atLeastOneOf(::email, ::phone)
        exactlyOneOf(::iban, ::cardNumber)
    }
}
