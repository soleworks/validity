package io.github.soleworks.validity.samples.docs

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.constraints.hexColor
import io.github.soleworks.validity.constraints.rgbColor
import io.github.soleworks.validity.ifPresent
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation

data class Theme(
    val accent: String?,
    val overlay: String? = null
) : Validatable {
    override fun validation() = validation {
        ::accent required { hexColor() }
        ::overlay ifPresent { rgbColor() }
    }
}
