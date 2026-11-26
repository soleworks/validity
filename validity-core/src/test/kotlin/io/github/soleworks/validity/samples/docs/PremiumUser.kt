package io.github.soleworks.validity.samples.docs

import io.github.soleworks.validity.constraints.minLength
import io.github.soleworks.validity.include
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation

class PremiumUser(
    name: String?,
    email: String?,
    val coupon: String?
) : User(name, email) {
    override fun validation() = validation {
        include(super.validation())
        ::coupon required { minLength(6) }
    }
}
