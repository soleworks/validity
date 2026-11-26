package io.github.soleworks.validity.samples.docs

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.each
import io.github.soleworks.validity.ifInstanceOf
import io.github.soleworks.validity.required
import io.github.soleworks.validity.valid
import io.github.soleworks.validity.validation

data class Checkout(
    val payment: Payment?,
    val splits: List<Payment> = emptyList()
) : Validatable {
    override fun validation() = validation {
        ::payment required {
            ifInstanceOf<Payment.Card> { valid(cardValidation) }
        }
        ::splits required {
            each {
                ifInstanceOf<Payment.Card> { valid(cardValidation) }
                ifInstanceOf<Payment.BankTransfer> { valid(bankTransferValidation) }
            }
        }
    }
}
