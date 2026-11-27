package io.github.soleworks.validity.spring

import io.github.soleworks.validity.Violation
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity

internal object BadRequest : OnInvalid {
    override suspend fun respond(
        violations: List<Violation>
    ): ResponseEntity<*> {
        val problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST)
        problem.setProperty(
            "violations",
            violations.map { violation ->
                mapOf("path" to violation.path, "code" to violation.code, "message" to violation.message)
            }
        )
        return ResponseEntity.badRequest().body(problem)
    }
}
