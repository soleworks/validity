package io.github.soleworks.validity.samples.docs

object Subscribers {
    private val emails = setOf("ana@mail.com")

    fun contains(
        email: String
    ): Boolean = email in emails
}
