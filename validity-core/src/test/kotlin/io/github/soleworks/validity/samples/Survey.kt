package io.github.soleworks.validity.samples

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.constraints.notNull
import io.github.soleworks.validity.each
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation

data class Survey(
    val answers: List<String?>? = listOf("yes"),
    val comments: List<String?>? = listOf("great service")
) : Validatable {
    override fun validation() = validation {
        ::answers required { each { notNull("must be answered") } }
        ::comments required { each { notNull() } }
    }
}
