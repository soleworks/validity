package io.github.soleworks.validity.fixtures

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.constraints.minLength
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation

data class Product(
    val sku: String? = "SKU-123",
    val description: String? = "A cotton t-shirt"
) : Validatable {
    override fun validation() = validation {
        ::sku required { minLength(5, "must have at least {min} characters, like SKU-1") }
        ::description required { minLength(10) { min -> "'$this' is too short, it needs at least $min characters" } }
    }
}
