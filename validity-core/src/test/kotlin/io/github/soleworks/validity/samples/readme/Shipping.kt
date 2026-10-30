package io.github.soleworks.validity.samples.readme

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.required
import io.github.soleworks.validity.valid
import io.github.soleworks.validity.validation

data class Shipping(
    val destination: Destination?
) : Validatable {
    override fun validation() = validation {
        ::destination required { valid(destinationValidation) }
    }
}
