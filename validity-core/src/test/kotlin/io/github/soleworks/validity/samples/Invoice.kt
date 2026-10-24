package io.github.soleworks.validity.samples

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.each
import io.github.soleworks.validity.required
import io.github.soleworks.validity.valid
import io.github.soleworks.validity.validation

data class Invoice(
    val items: List<Int>? = listOf(10, 20),
    val total: Int? = 30
) : Validatable {
    override fun validation() = validation {
        constraint(message = "items must add up to the total", code = "total") { items.orEmpty().sum() == total }
    }
}

data class Ledger(
    val invoice: Invoice? = Invoice(),
    val invoices: List<Invoice>? = listOf(Invoice())
) : Validatable {
    override fun validation() = validation {
        ::invoice required { valid() }
        ::invoices required { each { valid() } }
    }
}
