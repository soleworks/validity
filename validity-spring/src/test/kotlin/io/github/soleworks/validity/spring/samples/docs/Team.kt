package io.github.soleworks.validity.spring.samples.docs

interface Team {
    suspend fun has(
        email: String
    ): Boolean
}
