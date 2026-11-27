package io.github.soleworks.validity.spring

import io.github.soleworks.validity.ValidationException
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
internal class ReactiveValidationExceptionHandler(
    private val onInvalid: OnInvalid
) {
    @ExceptionHandler(ValidationException::class)
    suspend fun handle(
        error: ValidationException
    ): ResponseEntity<*> = onInvalid.respond(error.violations)
}
