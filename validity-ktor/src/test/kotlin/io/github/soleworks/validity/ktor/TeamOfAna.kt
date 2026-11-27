package io.github.soleworks.validity.ktor

import io.github.soleworks.validity.ktor.samples.docs.Team

object TeamOfAna : Team {
    override suspend fun has(
        email: String
    ): Boolean = email == "ana@example.com"
}
