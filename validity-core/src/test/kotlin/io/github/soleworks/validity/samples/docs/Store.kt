package io.github.soleworks.validity.samples.docs

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.constraints.coordinates
import io.github.soleworks.validity.constraints.isoCountryCode
import io.github.soleworks.validity.constraints.locale
import io.github.soleworks.validity.constraints.postalCode
import io.github.soleworks.validity.constraints.timeZone
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation

data class Store(
    val country: String?,
    val zipCode: String?,
    val position: String?,
    val timeZone: String?,
    val locale: String?
) : Validatable {
    override fun validation() = validation {
        ::country required { isoCountryCode() }
        ::zipCode required { postalCode(country) }
        ::position required { coordinates() }
        ::timeZone required { timeZone() }
        ::locale required { locale() }
    }
}
