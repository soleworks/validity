package io.github.soleworks.validity.samples

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.constraints.minLength
import io.github.soleworks.validity.each
import io.github.soleworks.validity.ifPresent
import io.github.soleworks.validity.required
import io.github.soleworks.validity.valid
import io.github.soleworks.validity.validation

data class Customer(
    val name: String? = "Ana",
    val nickname: String? = null,
    val email: String? = "ana@mail.com",
    val address: Address? = Address(),
    val addresses: List<Address>? = emptyList()
) : Validatable {
    override fun validation() = validation {
        ::name required { minLength(2) }
        ::nickname ifPresent { minLength(3) }
        ::email.required("email is mandatory") { minLength(5) }
        ::address required { valid() }
        ::addresses required { each { valid() } }
    }
}
