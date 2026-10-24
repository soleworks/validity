package io.github.soleworks.validity

private val PLACEHOLDER = Regex("""\{(\w+)}""")

internal class Constraint<V>(
    private val check: (path: String, value: V) -> Violation?
) {
    fun validate(
        path: String,
        value: V
    ): List<Violation> = listOfNotNull(check(path, value))
}

@PublishedApi
internal fun String.filled(
    vararg placeholders: Pair<String, Any?>
): String {
    val values = placeholders.toMap()

    return PLACEHOLDER.replace(this) { match ->
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
