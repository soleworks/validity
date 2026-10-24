package io.github.soleworks.validity

import java.util.Objects

public class Violation(
    public val path: String,
    public val message: String,
    public val code: String
) {
    override fun equals(
        other: Any?
    ): Boolean = other is Violation && path == other.path && message == other.message && code == other.code

    override fun hashCode(): Int = Objects.hash(path, message, code)

    override fun toString(): String = "Violation(path=$path, message=$message, code=$code)"
}
