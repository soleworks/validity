package io.github.soleworks.validity.samples

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.constraint
import io.github.soleworks.validity.now
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation
import java.time.LocalDate
import java.time.ZonedDateTime

data class Report(
    val issuedOn: LocalDate? = LocalDate.of(2026, 3, 1),
    val sentAt: ZonedDateTime? = null
) : Validatable {
    override fun validation() = validation {
        ::issuedOn required {
            constraint("must be issued this year") { it.year == now(LocalDate::now).year }
        }
        constraint(path = "sentAt", message = "must be sent in the zone of the clock") {
            sentAt == null || sentAt.zone == now(ZonedDateTime::now).zone
        }
    }
}
