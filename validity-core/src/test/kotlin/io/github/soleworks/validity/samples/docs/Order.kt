package io.github.soleworks.validity.samples.docs

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.constraints.notBlank
import io.github.soleworks.validity.ifPresent
import io.github.soleworks.validity.required
import io.github.soleworks.validity.valid
import io.github.soleworks.validity.validation

data class Order(
    val id: String?,
    val shipping: ShippingAddress?,
    val billing: ShippingAddress? = null
) : Validatable {
    override fun validation() = validation {
        ::id required { notBlank() }
        ::shipping required { valid() }
        ::billing ifPresent { valid() }
    }
}
