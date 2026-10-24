package io.github.soleworks.validity.samples.readme

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.constraints.brazil.cnpj
import io.github.soleworks.validity.constraints.brazil.cpf
import io.github.soleworks.validity.constraints.not
import io.github.soleworks.validity.constraints.oneOf
import io.github.soleworks.validity.constraints.or
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation

data class Account(
    val taxId: String?,
    val username: String?
) : Validatable {
    override fun validation() = validation {
        ::taxId required {
            or {
                cpf()
                cnpj()
            }
        }
        ::username required {
            not { oneOf(listOf("admin", "root")) }
        }
    }
}
