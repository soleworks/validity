package io.github.soleworks.validity.spring

import io.github.soleworks.validity.spring.samples.docs.Team

object TeamOfAna : Team {
    override suspend fun has(
        email: String
    ): Boolean = email == "ana@example.com"
}
