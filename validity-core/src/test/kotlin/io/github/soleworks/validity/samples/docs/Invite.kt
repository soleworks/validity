package io.github.soleworks.validity.samples.docs

import io.github.soleworks.validity.ContextualValidatable
import io.github.soleworks.validity.constraints.email
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation

data class Invite(
    val email: String?
) : ContextualValidatable<Members> {
    override fun validation(
        context: Members
    ) = validation {
        ::email required {
            failFast {
                email()
                constraint("is already a member", code = "member") { !context.exists(it) }
            }
        }
    }
}
