package io.github.soleworks.validity.samples

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.each
import io.github.soleworks.validity.metadata
import io.github.soleworks.validity.required
import io.github.soleworks.validity.valid
import io.github.soleworks.validity.validation

data class Batch(
    val clientId: String = "c-9",
    val operations: List<Operation>? = listOf(Operation())
) : Validatable {
    override fun validation() = validation {
        metadata("clientId" to clientId) {
            ::operations required { each { valid() } }
        }
    }
}
