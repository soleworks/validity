package io.github.soleworks.validity.samples.docs

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.constraints.maxLength
import io.github.soleworks.validity.constraints.positive
import io.github.soleworks.validity.ifPresent
import io.github.soleworks.validity.metadata
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation
import java.math.BigDecimal

data class Withdrawal(
    val accountId: String,
    val amount: BigDecimal?,
    val note: String? = null
) : Validatable {
    override fun validation() = validation {
        metadata("accountId" to accountId) {
            ::amount required { positive() }
            ::note ifPresent {
                metadata("severity" to "warning") { maxLength(140) }
            }
        }
    }
}
