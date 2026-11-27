package io.github.soleworks.validity.spring

import io.kotest.matchers.shouldBe
import kotlinx.coroutines.yield
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.boot.WebApplicationType
import org.springframework.http.ResponseEntity

class OnInvalidTest {
    @Nested
    @DisplayName("When respond is called")
    inner class Respond {
        private val codesWith422 = OnInvalid { violations ->
            yield()
            ResponseEntity.status(422).body(mapOf("codes" to violations.map { it.code }))
        }

        @Test
        fun `given an invalid registration in Spring MVC should use the response of the bean`() {
            val result = replyTo(
                path = "/registrations",
                json = INVALID_REGISTRATION,
                stack = WebApplicationType.SERVLET,
                onInvalid = codesWith422
            )

            result shouldBe Reply(status = 422, body = json("""{"codes":["email","minLength"]}"""))
        }

        @Test
        fun `given an invitation of a team member in Spring WebFlux should use the response of the bean`() {
            val result = replyTo(
                path = "/invitations",
                json = INVITATION_OF_ANA,
                stack = WebApplicationType.REACTIVE,
                onInvalid = codesWith422
            )

            result shouldBe Reply(status = 422, body = json("""{"codes":["member"]}"""))
        }
    }
}
