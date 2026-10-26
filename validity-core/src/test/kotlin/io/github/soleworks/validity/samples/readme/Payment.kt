package io.github.soleworks.validity.samples.readme

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.constraints.creditCard
import io.github.soleworks.validity.constraints.currencyCode
import io.github.soleworks.validity.constraints.enum
import io.github.soleworks.validity.constraints.iban
import io.github.soleworks.validity.constraints.maxDecimalPlaces
import io.github.soleworks.validity.constraints.positive
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation
import java.math.BigDecimal

data class Payment(
    val method: String?,
    val amount: BigDecimal?,
    val currency: String?,
    val cardNumber: String? = null,
    val iban: String? = null
) : Validatable {
    override fun validation() = validation {
        ::method required { enum<PaymentMethod>() }
        ::amount required {
            positive()
            maxDecimalPlaces(2)
        }
        ::currency required { currencyCode() }

        when (method) {
            "CARD" -> ::cardNumber required { creditCard() }
            "SEPA" -> ::iban required { iban() }
        }
    }
}
