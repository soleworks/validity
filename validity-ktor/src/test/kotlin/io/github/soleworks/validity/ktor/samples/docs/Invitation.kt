package io.github.soleworks.validity.ktor.samples.docs

import io.github.soleworks.validity.ContextualValidatable
import io.github.soleworks.validity.constraints.email
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation
import kotlinx.serialization.Serializable

@Serializable
data class Invitation(
    val email: String? = null
) : ContextualValidatable<Team> {
    override fun validation(
        context: Team
    ) = validation {
        ::email required {
            failFast {
                email()
                constraint("is already in the team", code = "member") { !context.has(it) }
            }
        }
    }
}
