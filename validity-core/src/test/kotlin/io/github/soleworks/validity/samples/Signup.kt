package io.github.soleworks.validity.samples

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.atLeastOneOf
import io.github.soleworks.validity.constraints.email
import io.github.soleworks.validity.constraints.minLength
import io.github.soleworks.validity.ifPresent
import io.github.soleworks.validity.required
import io.github.soleworks.validity.valid
import io.github.soleworks.validity.validation

enum class Severity {
    ERROR,
    WARNING
}

data class Signup(
    val email: String? = "ana@mail.com",
    val phone: String? = null,
    val password: String? = "correct-horse-battery",
    val address: Address? = Address()
) : Validatable {
    override fun validation() = validation {
        metadata(Severity.ERROR) {
            atLeastOneOf(::email, ::phone)
            ::email ifPresent { email() }
        }
        ::password required {
            minLength(8)
            metadata(Severity.WARNING) { minLength(12) }
        }
        metadata(Severity.WARNING) {
            ::address required { valid() }
        }
    }
}
