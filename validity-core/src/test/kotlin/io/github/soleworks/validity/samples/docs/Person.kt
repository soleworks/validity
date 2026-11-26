package io.github.soleworks.validity.samples.docs

import io.github.soleworks.validity.constraints.email
import io.github.soleworks.validity.constraints.minLength
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation

open class Person(
    val name: String?,
    val email: String?
)

val personValidation = validation<Person> {
    ::name required { minLength(2) }
    ::email required { email() }
}
