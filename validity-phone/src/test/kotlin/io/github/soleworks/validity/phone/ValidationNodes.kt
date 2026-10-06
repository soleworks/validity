package io.github.soleworks.validity.phone

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.github.soleworks.validity.validate
import io.github.soleworks.validity.validation

fun ValidationNode<*>.validate(): List<Violation> = object : Validatable {
    override fun validation() = validation { add(this@validate) }
}.validate().violations
