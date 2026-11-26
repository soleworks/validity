package io.github.soleworks.validity.samples

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.constraints.notBlank
import io.github.soleworks.validity.include
import io.github.soleworks.validity.required
import io.github.soleworks.validity.samples.docs.Person
import io.github.soleworks.validity.samples.docs.personValidation
import io.github.soleworks.validity.validation

class Intern(
    name: String?,
    email: String?,
    val school: String?
) : Person(name, email),
    Validatable {
    override fun validation() = validation {
        include(personValidation)
        ::school required { notBlank() }
    }
}
