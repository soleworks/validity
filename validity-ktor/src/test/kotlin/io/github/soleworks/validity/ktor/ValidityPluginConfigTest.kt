package io.github.soleworks.validity.ktor

import io.kotest.matchers.shouldBe
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respondText
import kotlinx.coroutines.yield
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ValidityPluginConfigTest {
    @Nested
    @DisplayName("When onInvalid is called")
    inner class OnInvalid {
        private val codesWith422: ValidityPluginConfig.() -> Unit = {
            onInvalid { call, violations ->
                yield()
                call.respondText(violations.joinToString { it.code }, status = HttpStatusCode.UnprocessableEntity)
            }
        }

        @Test
        fun `given an invalid registration should use the response of the lambda`() {
            val registration = """{"email":"ana@","password":"123"}"""

            val result = replyTo(path = "/registrations", json = registration) { accounts(validity = codesWith422) }

            result shouldBe Reply(status = HttpStatusCode.UnprocessableEntity, body = "email, minLength")
        }

        @Test
        fun `given an invitation of a team member should use the response of the lambda`() {
            val invitation = """{"email":"ana@example.com"}"""

            val result = replyTo(path = "/invitations", json = invitation) { accounts(validity = codesWith422) }

            result shouldBe Reply(status = HttpStatusCode.UnprocessableEntity, body = "member")
        }
    }
}
