package io.github.soleworks.validity.samples

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.constraints.minLength
import io.github.soleworks.validity.each
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation

data class Address(
    val street: String? = "Rua das Flores",
    val phones: List<String>? = listOf("11999999999")
) : Validatable {
    override fun validation() = validation {
        ::street required {}
        ::phones required { each { minLength(8) } }
    }
}
