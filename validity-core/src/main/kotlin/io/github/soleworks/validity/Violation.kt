package io.github.soleworks.validity

public data class Violation(
    val path: String,
    val message: String,
    val code: String
)
