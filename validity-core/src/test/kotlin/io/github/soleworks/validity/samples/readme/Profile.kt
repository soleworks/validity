package io.github.soleworks.validity.samples.readme

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.constraints.minLength
import io.github.soleworks.validity.forbidden
import io.github.soleworks.validity.ifPresent
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation

data class Profile(
    val name: String?,
    val nickname: String?,
    val isPersonal: Boolean,
    val companyName: String?
) : Validatable {
    override fun validation() = validation {
        ::name.required("tell us your name") { minLength(2) }
        ::nickname ifPresent { minLength(3) }

        if (isPersonal) ::companyName.forbidden()
    }
}
