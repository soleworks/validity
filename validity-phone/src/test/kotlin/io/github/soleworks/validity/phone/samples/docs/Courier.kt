package io.github.soleworks.validity.phone.samples.docs

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.ifPresent
import io.github.soleworks.validity.phone.PhoneFormat
import io.github.soleworks.validity.phone.mobilePhone
import io.github.soleworks.validity.phone.phone
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation

data class Courier(
    val mobile: String?,
    val office: String? = null
) : Validatable {
    override fun validation() = validation {
        ::mobile required { mobilePhone(countryCode = "55", format = PhoneFormat.COMPLETE) }
        ::office ifPresent { phone(countryCode = "55") }
    }
}
