package io.github.soleworks.validity.samples.docs

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.constraints.and
import io.github.soleworks.validity.constraints.brazil.cnpj
import io.github.soleworks.validity.constraints.brazil.cpf
import io.github.soleworks.validity.constraints.contains
import io.github.soleworks.validity.constraints.length
import io.github.soleworks.validity.constraints.not
import io.github.soleworks.validity.constraints.or
import io.github.soleworks.validity.constraints.startsWith
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation

data class Seller(
    val document: String?,
    val storeCode: String?,
    val username: String?,
    val password: String?
) : Validatable {
    override fun validation() = validation {
        ::document required {
            or {
                cpf()
                cnpj()
            }
        }
        ::storeCode required {
            and("must be ST- and 5 more characters") {
                startsWith("ST-")
                length(8)
            }
        }
        ::password required { not("must not contain the username") { contains(username) } }
    }
}
