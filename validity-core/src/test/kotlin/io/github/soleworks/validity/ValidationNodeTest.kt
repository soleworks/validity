package io.github.soleworks.validity

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ValidationNodeTest {
    @Nested
    @DisplayName("When constraint is called")
    inner class Constraint {
        @Test
        fun `given a code should report it with the violation`() {
            val node = ValidationNode("amount", -1).apply {
                constraint("must be positive", "positive") { it > 0 }
            }

            node.validate() shouldBe listOf(Violation("amount", "must be positive", "positive"))
        }

        @Test
        fun `given a message function and a code should report both`() {
            val node = ValidationNode("amount", -1).apply {
                constraint({ "$it is not positive" }, "positive") { it > 0 }
            }

            node.validate() shouldBe listOf(Violation("amount", "-1 is not positive", "positive"))
        }

        @Test
        fun `given no code should report an empty code`() {
            val node = ValidationNode("amount", -1).apply {
                constraint("must be positive") { it > 0 }
            }

            node.validate() shouldBe listOf(Violation("amount", "must be positive", ""))
        }
    }
}
