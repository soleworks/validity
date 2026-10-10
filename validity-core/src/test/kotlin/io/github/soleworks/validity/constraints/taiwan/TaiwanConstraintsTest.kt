package io.github.soleworks.validity.constraints.taiwan

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class TaiwanConstraintsTest {
    @Nested
    @DisplayName("When nationalId is called")
    inner class NationalId {
        @ParameterizedTest
        @ValueSource(strings = ["B176944193", "K101189797", "A219758834", "X231071923"])
        fun `given a valid national identification number should accept it`(value: String) {
            val node = ValidationNode("nationalId", value).apply { nationalId() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["123456789", "A185034995", "X431071923", "X231071922", "A1234567L"])
        fun `given an invalid national identification number should report it`(value: String) {
            val node = ValidationNode("nationalId", value).apply { nationalId() }

            node.validate() shouldBe listOf(Violation("nationalId", "must be a valid national identification number", "nationalId"))
        }
    }
}
