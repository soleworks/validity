package io.github.soleworks.validity.samples

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.constraints.minLength
import io.github.soleworks.validity.constraints.notNull
import io.github.soleworks.validity.each
import io.github.soleworks.validity.eachKey
import io.github.soleworks.validity.eachValue
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation

class Order(
    val tags: List<String>? = listOf("gift"),
    val boxes: List<List<String>>? = listOf(listOf("SKU-1")),
    val couponCodes: Array<String>? = arrayOf("PROMO10"),
    val prices: Map<String, Int?>? = mapOf("USD" to 1000)
) : Validatable {
    override fun validation() = validation {
        ::tags required {
            each { minLength(3) }
            constraint("must have at most 3 tags") { it.size <= 3 }
        }
        ::boxes required { each { each { minLength(5) } } }
        ::couponCodes required { each { minLength(5) } }
        ::prices required {
            each { constraint("must be at least 100 in BRL") { it.key != "BRL" || (it.value ?: 100) >= 100 } }
            eachKey { minLength(3) }
            eachValue { notNull() }
        }
    }
}
