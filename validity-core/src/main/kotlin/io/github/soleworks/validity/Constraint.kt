package io.github.soleworks.validity

internal class Constraint<V>(
    private val message: (V) -> String,
    private val code: String,
    private val predicate: (V) -> Boolean
) {
    fun validate(path: String, value: V): List<Violation> = if (predicate(value))
        emptyList()
    else
        listOf(
            Violation(
                path = path,
                message = message(value)
                    .replace("{path}", path)
                    .replace("{value}", "$value"),
                code = code
            )
        )
}
