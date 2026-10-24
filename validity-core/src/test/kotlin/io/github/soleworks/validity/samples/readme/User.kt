package io.github.soleworks.validity.samples.readme

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.constraints.email
import io.github.soleworks.validity.constraints.min
import io.github.soleworks.validity.constraints.minLength
import io.github.soleworks.validity.ifPresent
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation

data class User(
    val name: String?,
    val email: String?,
    val age: Int?
) : Validatable {
    override fun validation() = validation {
        ::name required { minLength(2) }
        ::email required { email() }
        ::age ifPresent { min(18) }
    }
}
