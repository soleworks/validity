package io.github.soleworks.validity.samples.docs

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.constraints.containsDigit
import io.github.soleworks.validity.constraints.containsSymbol
import io.github.soleworks.validity.constraints.containsUppercase
import io.github.soleworks.validity.constraints.lengthBetween
import io.github.soleworks.validity.constraints.lowercase
import io.github.soleworks.validity.constraints.maxBytes
import io.github.soleworks.validity.constraints.minLength
import io.github.soleworks.validity.constraints.notBlank
import io.github.soleworks.validity.constraints.slug
import io.github.soleworks.validity.constraints.trimmed
import io.github.soleworks.validity.ifPresent
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation

data class MemberProfile(
    val handle: String?,
    val displayName: String?,
    val password: String?,
    val bio: String? = null
) : Validatable {
    override fun validation() = validation {
        ::handle required {
            lengthBetween(min = 3, max = 30)
            lowercase()
            slug()
        }
        ::displayName required {
            notBlank()
            trimmed()
        }
        ::password required {
            minLength(12)
            containsUppercase()
            containsDigit()
            containsSymbol()
        }
        ::bio ifPresent { maxBytes(500) }
    }
}
