package io.github.soleworks.validity.samples

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.atLeastOneOf
import io.github.soleworks.validity.atMostOneOf
import io.github.soleworks.validity.exactlyOneOf
import io.github.soleworks.validity.forbidden
import io.github.soleworks.validity.validation

data class Recipient(
    val pixKey: String? = "ana@mail.com",
    val bankAccount: String? = null,
    val boleto: String? = null,
    val email: String? = "ana@mail.com",
    val phone: String? = null,
    val cpf: String? = "52998224725",
    val cnpj: String? = null,
    val passport: String? = null,
    val companyName: String? = null
) : Validatable {
    override fun validation() = validation {
        exactlyOneOf(::pixKey, ::bankAccount, ::boleto)
        atLeastOneOf(::email, ::phone)
        atMostOneOf(::cpf, ::cnpj, ::passport)

        if (cpf != null) ::companyName.forbidden()
    }
}
