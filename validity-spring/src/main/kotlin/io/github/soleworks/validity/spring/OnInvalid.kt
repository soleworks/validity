package io.github.soleworks.validity.spring

import io.github.soleworks.validity.ValidationException
import io.github.soleworks.validity.Violation
import org.springframework.http.ResponseEntity

/**
 * The response to a request whose body breaks a rule, in place of the `400 Bad Request` that `validity-spring` answers
 * by default.
 *
 * Declare it as a bean, and it answers every [ValidationException] of a handler: the one of a `@RequestBody` that
 * implements [Validatable][io.github.soleworks.validity.Validatable], and the one of a `validateOrThrow` the handler
 * calls. It can call `suspend` functions.
 *
 * ```kotlin
 * @Bean
 * fun onInvalid() = OnInvalid { violations ->
 *     ResponseEntity.status(422).body(ApiError(violations.map { it.code }))
 * }
 * ```
 */
public fun interface OnInvalid {
    /**
     * Builds the response to the broken rules of a request.
     *
     * @param violations every broken rule, in the order the rules are declared.
     */
    public suspend fun respond(
        violations: List<Violation>
    ): ResponseEntity<*>
}
