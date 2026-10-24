package io.github.soleworks.validity.samples.readme

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.constraints.minLength
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation

data class Signup(
    val username: String?,
    val password: String?
) : Validatable {
    override fun validation() = validation {
        ::username required {
            minLength(3) { min -> "'$this' is too short, use at least $min characters" }
        }
        ::password required { minLength(12, "{path} needs {min} characters or more") }
    }
}
