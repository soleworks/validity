package io.github.soleworks.validity.samples.docs

import io.github.soleworks.validity.constraints.creditCard
import io.github.soleworks.validity.constraints.iban
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation

sealed interface Payment {
    data class Card(
        val number: String?
    ) : Payment

    data class BankTransfer(
        val iban: String?
    ) : Payment
}

val cardValidation = validation<Payment.Card> {
    ::number required { creditCard() }
}

val bankTransferValidation = validation<Payment.BankTransfer> {
    ::iban required { iban() }
}
