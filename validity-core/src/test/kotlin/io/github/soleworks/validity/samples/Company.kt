package io.github.soleworks.validity.samples

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.constraints.brazil.cnpj
import io.github.soleworks.validity.constraints.brazil.cpf
import io.github.soleworks.validity.constraints.or
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation

data class Company(
    val document: String? = "11222333000181"
) : Validatable {
    override fun validation() = validation {
        ::document required {
            or {
                cpf("{path} is not a CPF")
                cnpj("{path} is not a CNPJ")
            }
        }
    }
}
