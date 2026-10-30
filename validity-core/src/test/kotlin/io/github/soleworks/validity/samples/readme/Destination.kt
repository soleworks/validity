package io.github.soleworks.validity.samples.readme

import io.github.soleworks.validity.constraints.digits
import io.github.soleworks.validity.constraints.minLength
import io.github.soleworks.validity.constraints.notBlank
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation

data class Destination(
    val street: String?,
    val zipCode: String?
)

val destinationValidation = validation<Destination> {
    ::street required { notBlank() }
    ::zipCode required {
        minLength(5)
        digits()
    }
}
