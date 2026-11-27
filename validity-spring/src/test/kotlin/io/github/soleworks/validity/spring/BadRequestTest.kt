package io.github.soleworks.validity.spring

import io.github.soleworks.validity.Language
import io.github.soleworks.validity.Validity
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.boot.WebApplicationType

class BadRequestTest {
    @AfterEach
    fun restoreTheLanguage() {
        Validity.configure { language = Language.EN }
    }

    @Nested
    @DisplayName("When respond is called")
    inner class Respond {
        @Test
        fun `given messages in Portuguese should report them in Portuguese`() {
            Validity.configure { language = Language.PT_BR }

            val result = replyTo(
                path = "/registrations",
                json = INVALID_REGISTRATION,
                stack = WebApplicationType.SERVLET
            )

            result shouldBe Reply(
                status = 400,
                body = json(
                    """{"title":"Bad Request","status":400,"violations":[""" +
                        """{"path":"email","code":"email","message":"deve ser um e-mail válido"},""" +
                        """{"path":"password","code":"minLength","message":"deve ter pelo menos 8 caracteres"}]}"""
                )
            )
        }
    }
}
