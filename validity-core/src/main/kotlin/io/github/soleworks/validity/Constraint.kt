package io.github.soleworks.validity

private val MESSAGE_PLACEHOLDERS = Regex("\\{path}|\\{value}")

internal class Constraint<V>(
    private val check: (path: String, value: V) -> Violation?
) {
    fun validate(path: String, value: V): List<Violation> = listOfNotNull(
        check(path, value)?.let { it.copy(message = it.message.filled(path, value)) }
    )
}

private fun String.filled(
    path: String,
    value: Any?
): String = MESSAGE_PLACEHOLDERS.replace(this) { if (it.value == "{path}") path else "$value" }
