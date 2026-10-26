package io.github.soleworks.validity.samples.readme

import io.github.soleworks.validity.ContextualValidatable
import io.github.soleworks.validity.constraints.max
import io.github.soleworks.validity.each
import io.github.soleworks.validity.required
import io.github.soleworks.validity.valid
import io.github.soleworks.validity.validation

data class Checkout(
    val total: Int?,
    val items: List<Item>?
) : ContextualValidatable<Customer> {
    override fun validation(
        context: Customer
    ) = validation {
        ::total required { max(context.creditLimit) }
        ::items required { each { valid(context) } }
    }
}
