package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class BooleanConstraintsTest {
    @Nested
    @DisplayName("When isTrue is called")
    inner class IsTrue {
        @Test
        fun `given accepted terms should accept them`() {
            val node = ValidationNode(path = "termsAccepted", value = true).apply { isTrue() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given declined terms should report them`() {
            val node = ValidationNode(path = "termsAccepted", value = false).apply { isTrue() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "termsAccepted", message = "must be true", code = "isTrue")
            )
        }

        @Test
        fun `given a custom message should report it`() {
            val node = ValidationNode(path = "termsAccepted", value = false).apply { isTrue("must accept the terms") }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "termsAccepted", message = "must accept the terms", code = "isTrue")
            )
        }
    }

    @Nested
    @DisplayName("When isFalse is called")
    inner class IsFalse {
        @Test
        fun `given an account that is not deleted should accept it`() {
            val node = ValidationNode(path = "deleted", value = false).apply { isFalse() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a deleted account should report it`() {
            val node = ValidationNode(path = "deleted", value = true).apply { isFalse() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "deleted", message = "must be false", code = "isFalse")
            )
        }

        @Test
        fun `given a custom message should report it`() {
            val node = ValidationNode(path = "deleted", value = true).apply { isFalse("must not be deleted") }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "deleted", message = "must not be deleted", code = "isFalse")
            )
        }
    }
}
