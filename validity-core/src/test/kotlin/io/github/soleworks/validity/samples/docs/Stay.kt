package io.github.soleworks.validity.samples.docs

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.constraint
import io.github.soleworks.validity.validation
import java.time.LocalDate

data class Stay(
    val checkIn: LocalDate,
    val checkOut: LocalDate
) : Validatable {
    override fun validation() = validation {
        constraint(path = "checkOut", message = "must be after the check-in", code = "stay") {
            checkOut.isAfter(checkIn)
        }
    }
}
