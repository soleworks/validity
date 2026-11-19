package io.github.soleworks.validity.phone

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validatable
import io.github.soleworks.validity.validateBlocking

fun <V : Any> violationsOf(
    path: String,
    value: V,
    rules: ValidationNode<V>.() -> Unit
): List<Violation> = validatable(value) {
    required(path = path, value = value, block = rules)
}.validateBlocking().violations
