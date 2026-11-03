package io.github.soleworks.validity.samples.docs

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.constraints.currencyCode
import io.github.soleworks.validity.constraints.distinct
import io.github.soleworks.validity.constraints.length
import io.github.soleworks.validity.constraints.maxSize
import io.github.soleworks.validity.constraints.notEmpty
import io.github.soleworks.validity.constraints.positive
import io.github.soleworks.validity.each
import io.github.soleworks.validity.eachKey
import io.github.soleworks.validity.eachValue
import io.github.soleworks.validity.ifPresent
import io.github.soleworks.validity.required
import io.github.soleworks.validity.valid
import io.github.soleworks.validity.validation
import java.math.BigDecimal

data class Cart(
    val items: List<CartItem>?,
    val coupons: List<String>? = null,
    val prices: Map<String, BigDecimal>? = null
) : Validatable {
    override fun validation() = validation {
        ::items required {
            notEmpty()
            maxSize(50)
            each { valid() }
        }
        ::coupons ifPresent {
            distinct()
            each { length(8) }
        }
        ::prices ifPresent {
            eachKey { currencyCode() }
            eachValue { positive() }
        }
    }
}
