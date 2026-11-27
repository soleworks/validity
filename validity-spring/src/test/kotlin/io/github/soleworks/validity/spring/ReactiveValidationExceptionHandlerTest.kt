package io.github.soleworks.validity.spring

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.boot.WebApplicationType

class ReactiveValidationExceptionHandlerTest {
    @Nested
    @DisplayName("When handle is called")
    inner class Handle {
        @Test
        fun `given an invitation of a team member should report it`() {
            val invitation = INVITATION_OF_ANA

            val result = replyTo(path = "/invitations", json = invitation, stack = WebApplicationType.REACTIVE)

            result shouldBe Reply(status = 400, body = MEMBER_VIOLATION)
        }
    }
}
