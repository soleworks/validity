package io.github.soleworks.validity.samples

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.now
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation
import java.time.LocalDate
import kotlinx.coroutines.withContext

data class Booking(
    val date: LocalDate? = LocalDate.of(2026, 12, 25)
) : Validatable {
    override fun validation() = validation {
        ::date required {
            constraint("must not be in the past") { date ->
                withContext(Threads.other) { !date.isBefore(now(LocalDate::now)) }
            }
        }
    }
}
