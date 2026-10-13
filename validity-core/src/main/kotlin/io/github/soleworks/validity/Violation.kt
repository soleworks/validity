package io.github.soleworks.validity

public data class Violation(
    val path: String,
    val message: String,
    val code: String,
    val metadata: Any? = null
) {
    override fun toString(): String = if (metadata == null)
        "Violation(path=$path, message=$message, code=$code)"
    else
        "Violation(path=$path, message=$message, code=$code, metadata=$metadata)"
}
