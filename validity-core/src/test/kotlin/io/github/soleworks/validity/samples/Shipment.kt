package io.github.soleworks.validity.samples

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.exactlyOneOf
import io.github.soleworks.validity.validation

data class Shipment(
    val trackingCode: String? = "BR123",
    val pickupCode: String? = null
) : Validatable {
    override fun validation() = validation {
        exactlyOneOf(::trackingCode, ::trackingCode, ::pickupCode)
    }
}
