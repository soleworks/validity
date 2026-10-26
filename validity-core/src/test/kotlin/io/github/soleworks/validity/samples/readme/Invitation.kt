package io.github.soleworks.validity.samples.readme

import io.github.soleworks.validity.ContextualValidatable
import io.github.soleworks.validity.constraints.email
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation

data class Invitation(
    val email: String?
) : ContextualValidatable<Users> {
    override fun validation(
        context: Users
    ) = validation {
        ::email required {
            failFast {
                email()
                constraint("is already registered", code = "registered") { !context.exists(it) }
            }
        }
    }
}
