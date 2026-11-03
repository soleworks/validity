package io.github.soleworks.validity.samples.docs

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.constraints.maxAge
import io.github.soleworks.validity.constraints.weekday
import io.github.soleworks.validity.constraints.withinNext
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation
import java.time.LocalDate
import kotlin.time.Duration.Companion.days

data class Appointment(
    val date: LocalDate?,
    val patientBirthDate: LocalDate?
) : Validatable {
    override fun validation() = validation {
        ::date required {
            weekday()
            withinNext(60.days)
        }
        ::patientBirthDate required { maxAge(120) }
    }
}
