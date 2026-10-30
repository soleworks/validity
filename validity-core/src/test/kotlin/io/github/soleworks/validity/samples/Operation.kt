package io.github.soleworks.validity.samples

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.atLeastOneOf
import io.github.soleworks.validity.constraints.length
import io.github.soleworks.validity.constraints.minLength
import io.github.soleworks.validity.constraints.positive
import io.github.soleworks.validity.each
import io.github.soleworks.validity.metadata
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation

data class Operation(
    val id: String = "op-1",
    val amount: Int? = 100,
    val currency: String? = "BRL",
    val email: String? = "ana@mail.com",
    val phone: String? = null,
    val tags: List<String>? = listOf("pix")
) : Validatable {
    override fun validation() = validation {
        metadata("operationId" to id) {
            atLeastOneOf(::email, ::phone)
            ::amount required { positive() }
            ::currency required { metadata("field" to "currency") { length(3) } }
            ::tags required { each { minLength(3) } }
        }
    }
}
