package io.github.soleworks.validity.constraints.ireland

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class IrelandConstraintsTest {
    @Nested
    @DisplayName("When pps is called")
    inner class Pps {
        @ParameterizedTest
        @ValueSource(strings = ["1234567T", "1234567TW", "1234577W", "1234577WW", "1234577IA"])
        fun `given a valid PPS number should accept it`(value: String) {
            val node = ValidationNode("pps", value).apply { pps() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["1234567", "1234577WWW", "1234577A", "1234577JA"])
        fun `given an invalid PPS number should report it`(value: String) {
            val node = ValidationNode("pps", value).apply { pps() }

            node.validate() shouldBe listOf(Violation("pps", "must be a valid PPS number", "pps"))
        }
    }
}
