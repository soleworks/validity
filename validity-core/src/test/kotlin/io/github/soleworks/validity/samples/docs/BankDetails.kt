package io.github.soleworks.validity.samples.docs

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.constraints.bic
import io.github.soleworks.validity.constraints.currencyCode
import io.github.soleworks.validity.constraints.iban
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation

data class BankDetails(
    val iban: String?,
    val bic: String?,
    val currency: String?
) : Validatable {
    override fun validation() = validation {
        ::iban required { iban() }
        ::bic required { bic() }
        ::currency required { currencyCode() }
    }
}
