package io.github.soleworks.validity.samples

import io.github.soleworks.validity.Validation
import io.github.soleworks.validity.constraints.notBlank
import io.github.soleworks.validity.required
import io.github.soleworks.validity.samples.readme.Destination
import io.github.soleworks.validity.validation

class Warehouse(
    val street: String? = "Rua das Flores"
) {
    fun destinationValidation(): Validation<Destination> = validation<Destination> {
        ::street required { notBlank() }
    }
}
