package io.github.soleworks.validity.samples.docs

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.allOrNoneOf
import io.github.soleworks.validity.constraint
import io.github.soleworks.validity.exactlyOneOf
import io.github.soleworks.validity.validation
import java.math.BigDecimal

data class Transfer(
    val pixKey: String? = null,
    val iban: String? = null,
    val bic: String? = null,
    val amount: BigDecimal,
    val fee: BigDecimal,
    val total: BigDecimal
) : Validatable {
    override fun validation() = validation {
        exactlyOneOf(::pixKey, ::iban)
        allOrNoneOf(::iban, ::bic)
        constraint(::total, message = "must be the amount plus the fee", code = "total") {
            total.compareTo(amount + fee) == 0
        }
    }
}
