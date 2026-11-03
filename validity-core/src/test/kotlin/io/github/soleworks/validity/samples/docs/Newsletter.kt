package io.github.soleworks.validity.samples.docs

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.constraints.email
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation

data class Newsletter(
    val email: String?
) : Validatable {
    override fun validation() = validation {
        ::email required {
            failFast {
                email()
                constraint("is already subscribed", code = "subscribed") { !Subscribers.contains(it) }
            }
        }
    }
}
