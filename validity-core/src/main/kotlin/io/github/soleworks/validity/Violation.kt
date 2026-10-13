package io.github.soleworks.validity

public data class Violation(
    val path: String,
    val message: String,
    val code: String,
    val metadata: Map<String, Any?> = emptyMap()
) {
    override fun toString(): String = if (metadata.isEmpty())
        "Violation(path=$path, message=$message, code=$code)"
    else
        "Violation(path=$path, message=$message, code=$code, metadata=$metadata)"
}
