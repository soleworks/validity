package io.github.soleworks.validity.samples.readme

interface Users {
    suspend fun exists(
        email: String
    ): Boolean
}
