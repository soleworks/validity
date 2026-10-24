package io.github.soleworks.validity.samples.readme

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.constraints.after
import io.github.soleworks.validity.constraints.pastOrPresent
import io.github.soleworks.validity.ifPresent
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation
import java.time.LocalDate

data class Employment(
    val hiredOn: LocalDate?,
    val leftOn: LocalDate?
) : Validatable {
    override fun validation() = validation {
        ::hiredOn required { pastOrPresent() }
        ::leftOn ifPresent { after(hiredOn) }
    }
}
