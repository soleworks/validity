package io.github.soleworks.validity.samples

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.each
import io.github.soleworks.validity.required
import io.github.soleworks.validity.valid
import io.github.soleworks.validity.validation

data class Ledger(
    val invoice: Invoice? = Invoice(),
    val invoices: List<Invoice>? = listOf(Invoice())
) : Validatable {
    override fun validation() = validation {
        ::invoice required { valid() }
        ::invoices required { each { valid() } }
    }
}
