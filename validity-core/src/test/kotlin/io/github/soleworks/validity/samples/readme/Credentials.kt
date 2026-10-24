package io.github.soleworks.validity.samples.readme

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.constraints.minLength
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation

data class Credentials(
    val username: String?,
    val password: String?
) : Validatable {
    override fun validation() = validation {
        ::password required {
            minLength(12)
            constraint("must not contain the username", code = "username") {
                username == null || username !in it
            }
        }
    }
}
