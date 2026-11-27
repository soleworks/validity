package io.github.soleworks.validity.ktor.samples.docs

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.constraints.email
import io.github.soleworks.validity.constraints.minLength
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation
import kotlinx.serialization.Serializable

@Serializable
data class Registration(
    val email: String? = null,
    val password: String? = null
) : Validatable {
    override fun validation() = validation {
        ::email required { email() }
        ::password required { minLength(8) }
    }
}
