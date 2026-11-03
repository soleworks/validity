package io.github.soleworks.validity.samples.docs

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.constraints.containsDigit
import io.github.soleworks.validity.constraints.lengthBetween
import io.github.soleworks.validity.constraints.lettersOrDigits
import io.github.soleworks.validity.constraints.maxLength
import io.github.soleworks.validity.constraints.minLength
import io.github.soleworks.validity.ifPresent
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation

data class Account(
    val username: String?,
    val password: String?,
    val bio: String? = null
) : Validatable {
    override fun validation() = validation {
        ::username required {
            lengthBetween(min = 3, max = 20)
            lettersOrDigits()
        }
        ::password required {
            minLength(8)
            containsDigit()
        }
        ::bio ifPresent { maxLength(160) }
    }
}
