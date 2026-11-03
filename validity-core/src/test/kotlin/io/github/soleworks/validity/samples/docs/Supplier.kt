package io.github.soleworks.validity.samples.docs

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.constraints.brazil.cep
import io.github.soleworks.validity.constraints.brazil.cnpj
import io.github.soleworks.validity.constraints.spain.nie
import io.github.soleworks.validity.constraints.unitedstates.ein
import io.github.soleworks.validity.ifPresent
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation

data class Supplier(
    val country: String?,
    val taxId: String?,
    val zipCode: String? = null
) : Validatable {
    override fun validation() = validation {
        when (country) {
            "BR" -> {
                ::taxId required { cnpj() }
                ::zipCode ifPresent { cep() }
            }

            "ES" -> {
                ::taxId required { nie() }
            }

            "US" -> {
                ::taxId required { ein() }
            }
        }
    }
}
