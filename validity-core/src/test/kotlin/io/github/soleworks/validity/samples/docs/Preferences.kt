package io.github.soleworks.validity.samples.docs

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.constraints.equalTo
import io.github.soleworks.validity.constraints.noneOf
import io.github.soleworks.validity.constraints.oneOf
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation

private val RESERVED_NAMES = listOf("admin", "root", "support")

data class Preferences(
    val theme: String?,
    val username: String?,
    val password: String?,
    val confirmation: String?
) : Validatable {
    override fun validation() = validation {
        ::theme required { oneOf(listOf("light", "dark", "system")) }
        ::username required { noneOf(RESERVED_NAMES) }
        ::confirmation required { equalTo(password) }
    }
}
