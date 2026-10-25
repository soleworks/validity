package io.github.soleworks.validity.phone.samples

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.phone.phone
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation

data class Contact(
    val phone: String? = "1"
) : Validatable {
    override fun validation() = validation { ::phone required { phone() } }
}
