package io.github.soleworks.validity.samples.docs

import io.github.soleworks.validity.ContextualValidatable
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation

data class PurchaseLine(
    val sku: String?
) : ContextualValidatable<Shopper> {
    override fun validation(
        context: Shopper
    ) = validation {
        ::sku required { constraint("is not sold in your region", code = "catalog") { it in context.catalog } }
    }
}
