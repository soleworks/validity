package io.github.soleworks.validity.samples.docs

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.constraints.between
import io.github.soleworks.validity.constraints.max
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

data class Meeting(
    val length: Duration?,
    val reminderBefore: java.time.Duration?
) : Validatable {
    override fun validation() = validation {
        ::length required { between(min = 15.minutes, max = 4.hours) }
        ::reminderBefore required { max(java.time.Duration.ofDays(1)) }
    }
}
