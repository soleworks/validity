package io.github.soleworks.validity.samples.docs

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.constraints.containsKey
import io.github.soleworks.validity.constraints.maxLength
import io.github.soleworks.validity.constraints.onlyKeys
import io.github.soleworks.validity.eachValue
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation

private val LANGUAGES = setOf("en", "pt", "es")

data class Translations(
    val titles: Map<String, String>?
) : Validatable {
    override fun validation() = validation {
        ::titles required {
            containsKey("en")
            onlyKeys(LANGUAGES)
            eachValue { maxLength(80) }
        }
    }
}
