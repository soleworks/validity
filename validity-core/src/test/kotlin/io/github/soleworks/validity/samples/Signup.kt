package io.github.soleworks.validity.samples

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.atLeastOf
import io.github.soleworks.validity.atMostOf
import io.github.soleworks.validity.exactlyOf
import io.github.soleworks.validity.validation

data class Signup(
    val email: String? = "ana@mail.com",
    val phone: String? = "11999999999",
    val whatsapp: String? = null,
    val coupon: String? = null,
    val giftCard: String? = null,
    val cashback: String? = null,
    val firstReference: String? = "Bia",
    val secondReference: String? = "Caio",
    val thirdReference: String? = null
) : Validatable {
    override fun validation() = validation {
        atLeastOf(2, ::email, ::phone, ::whatsapp, path = "contactInformation")
        atMostOf(2, ::coupon, ::giftCard, ::cashback)
        exactlyOf(2, ::firstReference, ::secondReference, ::thirdReference)
    }
}
