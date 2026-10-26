package io.github.soleworks.validity.samples

import kotlinx.coroutines.withContext

class Accounts(
    private vararg val emails: String
) {
    suspend fun exists(
        email: String
    ): Boolean = withContext(Threads.other) { email in emails }
}
