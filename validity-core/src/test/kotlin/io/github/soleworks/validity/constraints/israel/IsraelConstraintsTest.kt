package io.github.soleworks.validity.constraints.israel

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class IsraelConstraintsTest {
    @Nested
    @DisplayName("When teudatZehut is called")
    inner class TeudatZehut {
        @ParameterizedTest
        @ValueSource(strings = ["219472156", "334795465", "337090443"])
        fun `given a valid teudat zehut should accept it`(value: String) {
            val node = ValidationNode("teudatZehut", value).apply { teudatZehut() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["123456789", "12345678A", "12345 678Z", "219772156", "1234567L"])
        fun `given an invalid teudat zehut should report it`(value: String) {
            val node = ValidationNode("teudatZehut", value).apply { teudatZehut() }

            node.validate() shouldBe listOf(Violation("teudatZehut", "must be a valid teudat zehut", "teudatZehut"))
        }
    }
}
