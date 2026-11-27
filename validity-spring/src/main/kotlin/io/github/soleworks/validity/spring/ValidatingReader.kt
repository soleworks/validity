package io.github.soleworks.validity.spring

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.validateOrThrow
import kotlinx.coroutines.reactor.mono
import org.springframework.core.ResolvableType
import org.springframework.http.ReactiveHttpInputMessage
import org.springframework.http.codec.HttpMessageReader
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

internal class ValidatingReader<T : Any>(
    private val reader: HttpMessageReader<T>
) : HttpMessageReader<T> by reader {
    override fun read(
        elementType: ResolvableType,
        message: ReactiveHttpInputMessage,
        hints: Map<String, Any>
    ): Flux<T> = reader.read(elementType, message, hints).concatMap(::validated)

    override fun read(
        actualType: ResolvableType,
        elementType: ResolvableType,
        request: ServerHttpRequest,
        response: ServerHttpResponse,
        hints: Map<String, Any>
    ): Flux<T> = reader.read(actualType, elementType, request, response, hints).concatMap(::validated)

    override fun readMono(
        elementType: ResolvableType,
        message: ReactiveHttpInputMessage,
        hints: Map<String, Any>
    ): Mono<T> = reader.readMono(elementType, message, hints).flatMap(::validated)

    override fun readMono(
        actualType: ResolvableType,
        elementType: ResolvableType,
        request: ServerHttpRequest,
        response: ServerHttpResponse,
        hints: Map<String, Any>
    ): Mono<T> = reader.readMono(actualType, elementType, request, response, hints).flatMap(::validated)

    private fun validated(
        body: T
    ): Mono<T> = mono {
        if (body is Validatable) body.validateOrThrow()
        body
    }
}
