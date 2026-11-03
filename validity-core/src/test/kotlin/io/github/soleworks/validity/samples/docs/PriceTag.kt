package io.github.soleworks.validity.samples.docs

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.constraints.between
import io.github.soleworks.validity.constraints.maxDecimalPlaces
import io.github.soleworks.validity.constraints.multipleOf
import io.github.soleworks.validity.constraints.positive
import io.github.soleworks.validity.constraints.positiveOrZero
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation
import java.math.BigDecimal

data class PriceTag(
    val price: BigDecimal?,
    val discountPercent: Int?,
    val packSize: Int?,
    val stock: Long?
) : Validatable {
    override fun validation() = validation {
        ::price required {
            positive()
            maxDecimalPlaces(2)
        }
        ::discountPercent required { between(min = 0, max = 90) }
        ::packSize required { multipleOf(6) }
        ::stock required { positiveOrZero() }
    }
}
