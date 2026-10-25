package io.github.soleworks.validity.samples

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.constraints.future
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation
import java.time.LocalDate

data class Subscription(
    val startsOn: LocalDate? = LocalDate.of(2010, 1, 1)
) : Validatable {
    override fun validation() = validation {
        ::startsOn required { future() }
    }
}
