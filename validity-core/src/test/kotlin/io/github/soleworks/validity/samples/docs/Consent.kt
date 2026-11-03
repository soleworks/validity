package io.github.soleworks.validity.samples.docs

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.constraints.isFalse
import io.github.soleworks.validity.constraints.isTrue
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation

data class Consent(
    val hasAcceptedTerms: Boolean?,
    val isMinor: Boolean?
) : Validatable {
    override fun validation() = validation {
        ::hasAcceptedTerms required { isTrue() }
        ::isMinor required { isFalse() }
    }
}
