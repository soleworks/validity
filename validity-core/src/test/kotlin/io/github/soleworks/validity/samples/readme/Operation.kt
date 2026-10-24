package io.github.soleworks.validity.samples.readme

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.constraints.positive
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation
import java.math.BigDecimal

data class Operation(
    val id: String,
    val amount: BigDecimal?
) : Validatable {
    override fun validation() = validation {
        metadata("operationId" to id) {
            ::amount required { positive() }
        }
    }
}
