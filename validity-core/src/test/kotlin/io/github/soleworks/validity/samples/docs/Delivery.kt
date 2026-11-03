package io.github.soleworks.validity.samples.docs

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.constraints.future
import io.github.soleworks.validity.required
import io.github.soleworks.validity.valid
import io.github.soleworks.validity.validation
import java.time.LocalDate

data class Delivery(
    val address: Address?,
    val scheduledFor: LocalDate?
) : Validatable {
    override fun validation() = validation {
        ::address required { valid(addressValidation) }
        ::scheduledFor required { future() }
    }
}
