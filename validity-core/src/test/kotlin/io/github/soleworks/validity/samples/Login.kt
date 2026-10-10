package io.github.soleworks.validity.samples

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.atLeastOneOf
import io.github.soleworks.validity.forbidden
import io.github.soleworks.validity.validation

data class Login(
    val email: String? = "ana@mail.com",
    val phone: String? = null,
    val password: String? = null
) : Validatable {
    override fun validation() = validation {
        atLeastOneOf(::email, ::phone, message = "informe {fields}")

        if (email == null) ::password.forbidden("password needs an email")
    }
}
