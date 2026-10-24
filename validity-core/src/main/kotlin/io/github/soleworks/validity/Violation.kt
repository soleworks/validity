package io.github.soleworks.validity

import java.util.Objects

public class Violation(
    public val path: String,
    public val message: String,
    public val code: String,
    public val metadata: Map<String, Any?> = emptyMap()
) {
    override fun equals(
        other: Any?
    ): Boolean = other is Violation &&
        path == other.path &&
        message == other.message &&
        code == other.code &&
        metadata == other.metadata

    override fun hashCode(): Int = Objects.hash(path, message, code, metadata)

    override fun toString(): String = if (metadata.isEmpty())
        "Violation(path=$path, message=$message, code=$code)"
    else
        "Violation(path=$path, message=$message, code=$code, metadata=$metadata)"
}
