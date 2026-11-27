package io.github.soleworks.validity.spring

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.validateOrThrow
import kotlinx.coroutines.reactor.mono
import org.springframework.core.ResolvableType
import org.springframework.http.codec.HttpMessageReader
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import reactor.core.publisher.Mono

// WebFlux reads a single @RequestBody with this readMono, so it is the one that validates.
internal class ValidatingReader<T : Any>(
    private val reader: HttpMessageReader<T>
) : HttpMessageReader<T> by reader {
    override fun readMono(
        actualType: ResolvableType,
        elementType: ResolvableType,
        request: ServerHttpRequest,
        response: ServerHttpResponse,
        hints: Map<String, Any>
    ): Mono<T> = reader.readMono(actualType, elementType, request, response, hints).flatMap { body ->
        mono {
            if (body is Validatable) body.validateOrThrow()
            body
        }
    }
}
