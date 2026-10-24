package io.github.soleworks.validity.samples.readme

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation

private val SKU_FORMAT = Regex("""[A-Z]{3}-\d{4}""")

fun ValidationNode<String>.sku() {
    constraint(
        message = "must look like ABC-1234",
        code = "sku",
        predicate = { SKU_FORMAT.matches(it) }
    )
}

data class Product(
    val sku: String?
) : Validatable {
    override fun validation() = validation {
        ::sku required { sku() }
    }
}
