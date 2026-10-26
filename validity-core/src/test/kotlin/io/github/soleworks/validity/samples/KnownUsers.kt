package io.github.soleworks.validity.samples

import io.github.soleworks.validity.samples.readme.Users

class KnownUsers(
    private vararg val emails: String
) : Users {
    override suspend fun exists(
        email: String
    ): Boolean = email in emails
}
