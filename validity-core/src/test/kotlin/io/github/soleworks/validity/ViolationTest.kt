package io.github.soleworks.validity

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ViolationTest {
    @Nested
    @DisplayName("When toString is called")
    inner class ToString {
        @Test
        fun `given no metadata should leave it out of the text`() {
            val violation = Violation("amount", "must be positive", "positive")

            violation.toString() shouldBe "Violation(path=amount, message=must be positive, code=positive)"
        }

        @Test
        fun `given metadata should show it in the text`() {
            val violation = Violation("amount", "must be positive", "positive", mapOf("operationId" to "op-1"))

            violation.toString() shouldBe "Violation(path=amount, message=must be positive, code=positive, metadata={operationId=op-1})"
        }
    }
}
