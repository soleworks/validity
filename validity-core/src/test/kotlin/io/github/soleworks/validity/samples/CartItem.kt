package io.github.soleworks.validity.samples

import io.github.soleworks.validity.ContextualValidatable
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation

data class CartItem(
    val sku: String? = "SKU-1"
) : ContextualValidatable<Buyer> {
    override fun validation(
        context: Buyer
    ) = validation {
        ::sku required { constraint("is not sold to this buyer", code = "sku") { it in context.skus } }
    }
}
