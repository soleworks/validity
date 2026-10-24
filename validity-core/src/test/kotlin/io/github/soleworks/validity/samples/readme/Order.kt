package io.github.soleworks.validity.samples.readme

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.constraints.between
import io.github.soleworks.validity.constraints.currencyCode
import io.github.soleworks.validity.constraints.notBlank
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

data class Order(
    val shipping: Address?,
    val items: List<Item>?,
    val prices: Map<String, BigDecimal>?
) : Validatable {
    override fun validation() = validation {
        ::shipping required { valid(Address::validation) }
        ::items required {
            notEmpty()
            each { valid() }
        }
        ::prices ifPresent {
            eachKey { currencyCode() }
            eachValue { positive() }
        }
    }
}

data class Item(
    val sku: String?,
    val quantity: Int?
) : Validatable {
    override fun validation() = validation {
        ::sku required { notBlank() }
        ::quantity required { between(min = 1, max = 99) }
    }
}
