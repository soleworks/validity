package io.github.soleworks.validity.samples

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.constraint
import io.github.soleworks.validity.validation

data class Invoice(
    val items: List<Int>? = listOf(10, 20),
    val total: Int? = 30
) : Validatable {
    override fun validation() = validation {
        constraint(message = "items must add up to the total", code = "total") { items.orEmpty().sum() == total }
    }
}
