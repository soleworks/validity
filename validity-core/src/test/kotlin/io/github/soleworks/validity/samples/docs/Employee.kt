package io.github.soleworks.validity.samples.docs

import io.github.soleworks.validity.constraints.digits
import io.github.soleworks.validity.constraints.length
import io.github.soleworks.validity.include
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation

class Employee(
    name: String?,
    email: String?,
    val badge: String?
) : Person(name, email)

val employeeValidation = validation<Employee> {
    include(personValidation)
    ::badge required {
        length(6)
        digits()
    }
}
