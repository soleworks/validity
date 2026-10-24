package io.github.soleworks.validity.samples.readme

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.each
import io.github.soleworks.validity.required
import io.github.soleworks.validity.valid
import io.github.soleworks.validity.validation

data class Batch(
    val clientId: String,
    val operations: List<Operation>?
) : Validatable {
    override fun validation() = validation {
        metadata("clientId" to clientId) {
            ::operations required { each { valid() } }
        }
    }
}
