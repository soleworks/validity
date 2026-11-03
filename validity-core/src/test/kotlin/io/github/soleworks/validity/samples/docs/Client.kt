package io.github.soleworks.validity.samples.docs

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.constraints.email
import io.github.soleworks.validity.constraints.minLength
import io.github.soleworks.validity.constraints.notBlank
import io.github.soleworks.validity.constraints.oneOf
import io.github.soleworks.validity.forbidden
import io.github.soleworks.validity.ifPresent
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation

data class Client(
    val type: String?,
    val name: String?,
    val companyName: String? = null,
    val nickname: String? = null,
    val email: String? = null
) : Validatable {
    override fun validation() = validation {
        ::type required { oneOf(listOf("PERSON", "COMPANY")) }
        ::name required { notBlank() }
        ::nickname ifPresent { minLength(3) }
        ::email.required("we need an email to send the receipt") { email() }

        when (type) {
            "PERSON" -> ::companyName.forbidden()
            "COMPANY" -> ::companyName required { notBlank() }
        }
    }
}
