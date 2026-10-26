package io.github.soleworks.validity.samples.readme

import io.github.soleworks.validity.ContextualValidatable
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation

data class Item(
    val sku: String?
) : ContextualValidatable<Customer> {
    override fun validation(
        context: Customer
    ) = validation {
        ::sku required { constraint("is not in your catalog", code = "catalog") { it in context.catalog } }
    }
}
