package io.github.soleworks.validity.samples.docs

interface Members {
    suspend fun exists(
        email: String
    ): Boolean
}
