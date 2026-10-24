package io.github.soleworks.validity.samples.readme

import io.github.soleworks.validity.constraints.notBlank
import io.github.soleworks.validity.constraints.postalCode
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation

data class Address(
    val street: String?,
    val zipCode: String?,
    val country: String?
)

fun Address.validation() = validation {
    ::street required { notBlank() }
    ::zipCode required { postalCode(country) }
}
