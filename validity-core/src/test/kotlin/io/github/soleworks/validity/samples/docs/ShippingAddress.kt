package io.github.soleworks.validity.samples.docs

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.constraints.notBlank
import io.github.soleworks.validity.constraints.postalCode
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation

data class ShippingAddress(
    val street: String?,
    val zipCode: String?,
    val country: String? = "US"
) : Validatable {
    override fun validation() = validation {
        ::street required { notBlank() }
        ::zipCode required { postalCode(country) }
    }
}
