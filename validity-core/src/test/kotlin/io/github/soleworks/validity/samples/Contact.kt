package io.github.soleworks.validity.samples

import io.github.soleworks.validity.constraints.minLength
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation

data class Contact(
    val phone: String? = "11999999999"
)

fun Contact.validation() = validation {
    ::phone required { minLength(8) }
}
