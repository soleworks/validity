package io.github.soleworks.validity.samples.docs

import io.github.soleworks.validity.ContextualValidatable
import io.github.soleworks.validity.constraints.max
import io.github.soleworks.validity.each
import io.github.soleworks.validity.required
import io.github.soleworks.validity.valid
import io.github.soleworks.validity.validation
import java.math.BigDecimal

data class Purchase(
    val total: BigDecimal?,
    val lines: List<PurchaseLine>?
) : ContextualValidatable<Shopper> {
    override fun validation(
        context: Shopper
    ) = validation {
        ::total required { max(context.creditLimit) }
        ::lines required { each { valid(context) } }
    }
}
