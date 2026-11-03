package io.github.soleworks.validity.samples.docs

class KnownMembers(
    private vararg val emails: String
) : Members {
    override suspend fun exists(
        email: String
    ): Boolean = email in emails
}
