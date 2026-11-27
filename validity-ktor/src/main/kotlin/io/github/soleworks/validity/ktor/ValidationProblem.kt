package io.github.soleworks.validity.ktor

import io.github.soleworks.validity.Violation
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable

@Serializable
internal class ValidationProblem private constructor(
    val title: String,
    val status: Int,
    val violations: List<Entry>
) {
    constructor(
        violations: List<Violation>
    ) : this(
        title = HttpStatusCode.BadRequest.description,
        status = HttpStatusCode.BadRequest.value,
        violations = violations.map { violation ->
            Entry(path = violation.path, code = violation.code, message = violation.message)
        }
    )

    @Serializable
    class Entry(
        val path: String,
        val code: String,
        val message: String
    )
}
