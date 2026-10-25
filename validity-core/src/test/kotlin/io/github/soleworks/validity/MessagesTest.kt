package io.github.soleworks.validity

import io.github.soleworks.validity.samples.GREETING_MESSAGES
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class MessagesTest {
    @AfterEach
    fun restoreDefaultMessages() {
        Validity.configure { }
    }

    @Nested
    @DisplayName("When module is called")
    inner class Module {
        @Test
        fun `given no configuration should report the default message of the module`() {
            val rules = validation { constraint(message = GREETING_MESSAGES.current.greeting) { false } }

            val result = rules.validate()

            result.violations shouldBe listOf(Violation(path = "", message = "hello", code = ""))
        }

        @Test
        fun `given the module messages configured should report them`() {
            Validity.configure { messages { module(GREETING_MESSAGES).greeting = "olá" } }
            val rules = validation { constraint(message = GREETING_MESSAGES.current.greeting) { false } }

            val result = rules.validate()

            result.violations shouldBe listOf(Violation(path = "", message = "olá", code = ""))
        }

        @Test
        fun `given the module messages in a Validity should report them only in its validations`() {
            val validity = Validity { messages { module(GREETING_MESSAGES).greeting = "olá" } }
            val rules = validation { constraint(message = GREETING_MESSAGES.current.greeting) { false } }

            val violations = listOf(rules.validate(validity), rules.validate()).flatMap { it.violations }

            violations shouldBe listOf(
                Violation(path = "", message = "olá", code = ""),
                Violation(path = "", message = "hello", code = "")
            )
        }
    }
}
