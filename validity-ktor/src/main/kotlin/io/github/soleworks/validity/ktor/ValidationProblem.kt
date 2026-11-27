package io.github.soleworks.validity.ktor

import kotlinx.serialization.Serializable

@Serializable
internal class ValidationProblem(
    val title: String,
    val status: Int,
    val violations: List<Entry>
) {
    @Serializable
    class Entry(
        val path: String,
        val code: String,
        val message: String
    )
}
