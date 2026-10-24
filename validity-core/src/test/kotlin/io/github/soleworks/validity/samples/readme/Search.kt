package io.github.soleworks.validity.samples.readme

import io.github.soleworks.validity.constraints.min
import io.github.soleworks.validity.constraints.minLength
import io.github.soleworks.validity.ifPresent
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation

fun search(
    query: String?,
    page: Int?
) = validation {
    required(name = "query", value = query) { minLength(3) }
    ifPresent(name = "page", value = page) { min(1) }
}
