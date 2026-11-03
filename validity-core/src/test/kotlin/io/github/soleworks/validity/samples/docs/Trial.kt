package io.github.soleworks.validity.samples.docs

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.constraints.futureOrPresent
import io.github.soleworks.validity.constraints.minAge
import io.github.soleworks.validity.constraints.withinNext
import io.github.soleworks.validity.now
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation
import java.time.LocalDate
import kotlin.time.Duration.Companion.days

data class Trial(
    val birthDate: LocalDate?,
    val startsOn: LocalDate?
) : Validatable {
    override fun validation() = validation {
        ::birthDate required { minAge(18) }
        ::startsOn required {
            futureOrPresent()
            withinNext(30.days)
            constraint("must start this year", code = "thisYear") { it.year == now(LocalDate::now).year }
        }
    }
}
