package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import kotlinx.coroutines.runBlocking

fun ValidationNode<*>.validate(): List<Violation> = runBlocking { violations() }
