package io.github.soleworks.validity

internal class Constraint<V>(
    private val check: (path: String, value: V) -> Violation?
) {
    fun validate(path: String, value: V): List<Violation> = listOfNotNull(check(path, value))
}
