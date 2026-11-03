package io.github.soleworks.validity.samples.docs

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.constraints.between
import io.github.soleworks.validity.constraints.matches
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation

private val SKU_FORMAT = Regex("""[A-Z]{3}-\d{4}""")

data class CartItem(
    val sku: String?,
    val quantity: Int?
) : Validatable {
    override fun validation() = validation {
        ::sku required { matches(SKU_FORMAT) }
        ::quantity required { between(min = 1, max = 99) }
    }
}
