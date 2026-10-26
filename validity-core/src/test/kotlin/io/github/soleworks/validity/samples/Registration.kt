package io.github.soleworks.validity.samples

import io.github.soleworks.validity.ContextualValidatable
import io.github.soleworks.validity.constraints.email
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation

data class Registration(
    val email: String? = "ana@mail.com"
) : ContextualValidatable<Accounts> {
    override fun validation(
        context: Accounts
    ) = validation {
        ::email required {
            email()
            constraint("is already taken", code = "taken") { !context.exists(it) }
        }
    }
}
