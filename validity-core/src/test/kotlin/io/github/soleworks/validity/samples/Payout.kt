package io.github.soleworks.validity.samples

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.validation

data class Payout(
    val pixKey: String? = "ana@mail.com",
    val amount: Int? = 10
) : Validatable {
    override fun validation() = validation {
        constraint(::pixKey, message = "fill the pix key", code = "pixKey") { pixKey != null }
        constraint(::amount, message = "{path} cannot be {value}") { amount != 0 }
    }
}
