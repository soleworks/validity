package io.github.soleworks.validity.samples

import io.github.soleworks.validity.ContextualValidatable
import io.github.soleworks.validity.constraints.email
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation

data class Enrollment(
    val email: String? = "ana@mail.com"
) : ContextualValidatable<Directory> {
    override fun validation(
        context: Directory
    ) = validation {
        ::email required {
            email()
            constraint("is already taken", code = "taken") { !context.exists(it) }
        }
    }
}
