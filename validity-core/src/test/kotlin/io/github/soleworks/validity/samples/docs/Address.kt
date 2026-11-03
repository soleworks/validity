package io.github.soleworks.validity.samples.docs

import io.github.soleworks.validity.constraints.isoCountryCode
import io.github.soleworks.validity.constraints.notBlank
import io.github.soleworks.validity.constraints.postalCode
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation

data class Address(
    val street: String?,
    val zipCode: String?,
    val country: String?
)

val addressValidation = validation<Address> {
    ::street required { notBlank() }
    ::country required { isoCountryCode() }
    ::zipCode required { postalCode(country) }
}
