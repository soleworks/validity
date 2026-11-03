package io.github.soleworks.validity.samples.docs

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.constraints.after
import io.github.soleworks.validity.constraints.future
import io.github.soleworks.validity.constraints.pastOrPresent
import io.github.soleworks.validity.ifPresent
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation
import java.time.LocalDate
import java.time.OffsetDateTime

data class Event(
    val startsAt: OffsetDateTime?,
    val endsAt: OffsetDateTime?,
    val announcedOn: LocalDate? = null
) : Validatable {
    override fun validation() = validation {
        ::startsAt required { future() }
        ::endsAt required { after(startsAt) }
        ::announcedOn ifPresent { pastOrPresent() }
    }
}
