package io.github.soleworks.validity.constraints.tunisia

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class TunisiaConstraintsTest {
    @Nested
    @DisplayName("When cin is called")
    inner class Cin {
        @ParameterizedTest
        @ValueSource(strings = ["09958092", "65126506", "73260311"])
        fun `given a valid national identity card number should accept it`(value: String) {
            val node = ValidationNode("cin", value).apply { cin() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["123456789", "12345", "12345678A", "12345678-Z"])
        fun `given an invalid national identity card number should report it`(value: String) {
            val node = ValidationNode("cin", value).apply { cin() }

            node.validate() shouldBe listOf(Violation("cin", "must be a valid national identity card number", "cin"))
        }
    }
}
