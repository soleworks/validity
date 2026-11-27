package io.github.soleworks.validity.ktor

import io.github.soleworks.validity.Language
import io.github.soleworks.validity.Validity
import io.kotest.matchers.shouldBe
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.jackson.jackson
import io.ktor.server.application.Application
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

private const val INVALID_REGISTRATION = """{"email":"ana@","password":"123"}"""
private const val BOTH_VIOLATIONS = """{"title":"Bad Request","status":400,"violations":[""" +
    """{"path":"email","code":"email","message":"must be a valid email"},""" +
    """{"path":"password","code":"minLength","message":"must have at least 8 characters"}]}"""

private const val BOTH_VIOLATIONS_IN_PORTUGUESE = """{"title":"Bad Request","status":400,"violations":[""" +
    """{"path":"email","code":"email","message":"deve ser um e-mail válido"},""" +
    """{"path":"password","code":"minLength","message":"deve ter pelo menos 8 caracteres"}]}"""

class ValidityPluginTest {
    @AfterEach
    fun restoreTheLanguage() {
        Validity.configure { language = Language.EN }
    }

    @Nested
    @DisplayName("When install is called")
    inner class Install {
        @Test
        fun `given a valid registration should accept it`() {
            val registration = """{"email":"ana@example.com","password":"correct horse"}"""

            val result = replyTo(path = "/registrations", json = registration) { accounts() }

            result shouldBe Reply(status = HttpStatusCode.Created, body = "")
        }

        @Test
        fun `given a registration with an invalid email and a short password should report both violations`() {
            val registration = INVALID_REGISTRATION

            val result = replyTo(path = "/registrations", json = registration) { accounts() }

            result shouldBe Reply(status = HttpStatusCode.BadRequest, body = BOTH_VIOLATIONS)
        }

        @Test
        fun `given an application that writes JSON with Jackson should report both violations`() {
            val module: Application.() -> Unit = { accounts(serialization = { jackson() }) }

            val result = replyTo(path = "/registrations", json = INVALID_REGISTRATION, module = module)

            result shouldBe Reply(status = HttpStatusCode.BadRequest, body = BOTH_VIOLATIONS)
        }

        @Test
        fun `given an invitation of a team member should report it`() {
            val invitation = """{"email":"ana@example.com"}"""

            val result = replyTo(path = "/invitations", json = invitation) { accounts() }

            result shouldBe Reply(
                status = HttpStatusCode.BadRequest,
                body = """{"title":"Bad Request","status":400,"violations":[""" +
                    """{"path":"email","code":"member","message":"is already in the team"}]}"""
            )
        }

        @Test
        fun `given messages in Portuguese should report them in Portuguese`() {
            Validity.configure { language = Language.PT_BR }

            val result = replyTo(path = "/registrations", json = INVALID_REGISTRATION) { accounts() }

            result shouldBe Reply(status = HttpStatusCode.BadRequest, body = BOTH_VIOLATIONS_IN_PORTUGUESE)
        }
    }
}
