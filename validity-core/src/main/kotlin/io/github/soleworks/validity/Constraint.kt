package io.github.soleworks.validity

private val PLACEHOLDER_FORMAT = Regex("""\{(\w+)}""")

internal class Constraint<V>(
    private val check: (path: String, value: V) -> Violation?
) {
    fun validate(
        path: String,
        value: V
    ): List<Violation> = listOfNotNull(check(path, value))

    fun inheriting(
        metadata: Map<String, Any?>
    ): Constraint<V> = if (metadata.isEmpty())
        this
    else
        Constraint { path, value -> check(path, value)?.inheriting(metadata) }
}

private fun Violation.inheriting(
    metadata: Map<String, Any?>
): Violation = Violation(
    path = path,
    message = message,
    code = code,
    metadata = metadata + this.metadata
)

@PublishedApi
internal fun String.filled(
    vararg placeholders: Pair<String, Any?>
): String {
    val values = placeholders.toMap()

    return PLACEHOLDER_FORMAT.replace(this) { match ->
        val name = match.groupValues[1]

        if (name in values)
            values[name].placeholderText()
        else
            match.value
    }
}

private fun Any?.placeholderText(): String = when (this) {
    null -> ""
    is Iterable<*> -> joinToString()
    else -> toString()
}
