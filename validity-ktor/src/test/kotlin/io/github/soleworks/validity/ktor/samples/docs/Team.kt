package io.github.soleworks.validity.ktor.samples.docs

interface Team {
    suspend fun has(
        email: String
    ): Boolean
}
