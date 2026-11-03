package io.github.soleworks.validity.samples.docs

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.constraints.isbn13
import io.github.soleworks.validity.constraints.semver
import io.github.soleworks.validity.constraints.uuid
import io.github.soleworks.validity.ifPresent
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation

data class Book(
    val id: String?,
    val isbn: String?,
    val schemaVersion: String? = null
) : Validatable {
    override fun validation() = validation {
        ::id required { uuid(version = 4) }
        ::isbn required { isbn13() }
        ::schemaVersion ifPresent { semver() }
    }
}
