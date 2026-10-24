package io.github.soleworks.validity.phone.samples.readme

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.phone.areaCode
import io.github.soleworks.validity.phone.countryCode
import io.github.soleworks.validity.phone.mobilePhone
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation

data class Contact(
    val countryCode: String?,
    val areaCode: String?,
    val mobile: String?
) : Validatable {
    override fun validation() = validation {
        ::countryCode required { countryCode() }
        ::areaCode required { areaCode(countryCode) }
        ::mobile required { mobilePhone(countryCode = countryCode, areaCode = areaCode) }
    }
}
