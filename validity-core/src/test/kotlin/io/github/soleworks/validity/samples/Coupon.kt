package io.github.soleworks.validity.samples

import io.github.soleworks.validity.constraints.minLength
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation

data class Coupon(
    val code: String? = "PROMO10"
)

fun Coupon.validation() = validation {
    ::code required { minLength(5) }
}
