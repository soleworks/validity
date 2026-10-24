package io.github.soleworks.validity.samples.readme

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.constraints.email
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation

object Accounts {
    fun exists(
        email: String
    ): Boolean = email == "ana@mail.com"
}

data class Registration(
    val email: String?
) : Validatable {
    override fun validation() = validation {
        ::email required {
            failFast {
                email()
                constraint("is already registered", code = "registered") { !Accounts.exists(it) }
            }
        }
    }
}
