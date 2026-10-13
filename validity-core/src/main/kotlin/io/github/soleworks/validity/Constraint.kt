package io.github.soleworks.validity

private val MESSAGE_PLACEHOLDERS = Regex("\\{path}|\\{value}")

internal class Constraint<V>(
    private val check: (path: String, value: V) -> Violation?
) {
    fun validate(
        path: String,
        value: V
    ): List<Violation> = listOfNotNull(check(path, value))

    fun inheriting(
        metadata: Map<String, Any?>
    ): Constraint<V> = Constraint { path, value ->
        check(path, value)?.let { violation -> violation.copy(metadata = metadata + violation.metadata) }
    }
}

internal fun String.filled(
    path: String,
    value: Any?
): String = MESSAGE_PLACEHOLDERS.replace(this) { if (it.value == "{path}") path else "$value" }
