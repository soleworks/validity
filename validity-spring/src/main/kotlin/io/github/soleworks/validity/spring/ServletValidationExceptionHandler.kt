package io.github.soleworks.validity.spring

import io.github.soleworks.validity.ValidationException
import kotlinx.coroutines.runBlocking
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
internal class ServletValidationExceptionHandler(
    private val onInvalid: OnInvalid
) {
    @ExceptionHandler(ValidationException::class)
    fun handle(
        error: ValidationException
    ): ResponseEntity<*> = runBlocking { onInvalid.respond(error.violations) }
}
