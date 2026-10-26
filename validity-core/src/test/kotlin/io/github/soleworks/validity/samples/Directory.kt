package io.github.soleworks.validity.samples

import kotlinx.coroutines.withContext

class Directory(
    private vararg val emails: String
) {
    suspend fun exists(
        email: String
    ): Boolean = withContext(Threads.other) { email in emails }
}
