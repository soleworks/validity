package io.github.soleworks.validity.samples.docs

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.constraints.minLength
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation

data class Product(
    val sku: String?,
    val name: String?,
    val stock: Int?
) : Validatable {
    override fun validation() = validation {
        ::sku required { sku() }
        ::name required { minLength(3, message = { min -> "has $length characters, needs $min" }) }
        ::stock required {
            constraint("must be a multiple of the box size", code = "box") { it % 12 == 0 }
        }
    }
}
