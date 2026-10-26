package io.github.soleworks.validity.samples

import io.github.soleworks.validity.ContextualValidatable
import io.github.soleworks.validity.constraints.max
import io.github.soleworks.validity.each
import io.github.soleworks.validity.required
import io.github.soleworks.validity.valid
import io.github.soleworks.validity.validation

data class Cart(
    val total: Int? = 50,
    val items: List<CartItem>? = listOf(CartItem())
) : ContextualValidatable<Buyer> {
    override fun validation(
        context: Buyer
    ) = validation {
        ::total required { max(context.creditLimit) }
        ::items required { each { valid(context) } }
    }
}
