package io.github.soleworks.validity

internal class Constraint<V>(
    private val message: (V) -> String,
    private val code: String,
    private val predicate: (V) -> Boolean
) {
    fun validate(path: String, value: V): List<Violation> = if (predicate(value))
        emptyList()
    else
        listOf(Violation(path, message(value), code))
}
