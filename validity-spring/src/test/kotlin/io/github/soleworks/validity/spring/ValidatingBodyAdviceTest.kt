package io.github.soleworks.validity.spring

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.boot.WebApplicationType

class ValidatingBodyAdviceTest {
    @Nested
    @DisplayName("When afterBodyRead is called")
    inner class AfterBodyRead {
        @Test
        fun `given a valid registration should accept it`() {
            val registration = VALID_REGISTRATION

            val result = replyTo(path = "/registrations", json = registration, stack = WebApplicationType.SERVLET)

            result shouldBe Reply(status = 201, body = NO_BODY)
        }

        @Test
        fun `given a registration with an invalid email and a short password should report both violations`() {
            val registration = INVALID_REGISTRATION

            val result = replyTo(path = "/registrations", json = registration, stack = WebApplicationType.SERVLET)

            result shouldBe Reply(status = 400, body = BOTH_VIOLATIONS)
        }
    }
}
