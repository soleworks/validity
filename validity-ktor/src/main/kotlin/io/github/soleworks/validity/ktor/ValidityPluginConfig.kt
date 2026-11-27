package io.github.soleworks.validity.ktor

import io.github.soleworks.validity.Violation
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond
import io.ktor.utils.io.KtorDsl

/**
 * The configuration of [ValidityPlugin], set in the block of `install(ValidityPlugin) { }`.
 */
@KtorDsl
public class ValidityPluginConfig internal constructor() {
    internal var respond: suspend (ApplicationCall, List<Violation>) -> Unit = ::respondBadRequest
        private set

    /**
     * Answers each broken rule of a call with what [respond] writes, in place of the `400 Bad Request` the plugin
     * answers by default.
     *
     * [respond] runs for the body that `call.receive<T>()` validates and for every `validateOrThrow` the route calls.
     * It can call `suspend` functions.
     *
     * ```kotlin
     * install(ValidityPlugin) {
     *     onInvalid { call, violations ->
     *         call.respond(HttpStatusCode.UnprocessableEntity, ApiError(violations.map { it.code }))
     *     }
     * }
     * ```
     *
     * @param respond writes the response, from the call and its violations, in the order the rules are declared.
     */
    public fun onInvalid(
        respond: suspend (call: ApplicationCall, violations: List<Violation>) -> Unit
    ) {
        this.respond = respond
    }
}

private suspend fun respondBadRequest(
    call: ApplicationCall,
    violations: List<Violation>
) = call.respond(
    HttpStatusCode.BadRequest,
    ValidationProblem(
        title = HttpStatusCode.BadRequest.description,
        status = HttpStatusCode.BadRequest.value,
        violations = violations.map { violation ->
            ValidationProblem.Entry(path = violation.path, code = violation.code, message = violation.message)
        }
    )
)
