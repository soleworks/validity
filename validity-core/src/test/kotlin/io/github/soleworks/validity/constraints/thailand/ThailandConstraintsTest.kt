package io.github.soleworks.validity.constraints.thailand

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ThailandConstraintsTest {
    @Nested
    @DisplayName("When nationalId is called")
    inner class NationalId {
        @ParameterizedTest
        @ValueSource(strings = ["1101230000001", "1101230000060"])
        fun `given a valid national identification number should accept it`(value: String) {
            val node = ValidationNode("nationalId", value).apply { nationalId() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["abc", "1101230000007", "0101123450000", "9101123450008", "11012300000011"])
        fun `given an invalid national identification number should report it`(value: String) {
            val node = ValidationNode("nationalId", value).apply { nationalId() }

            node.validate() shouldBe listOf(Violation("nationalId", "must be a valid national identification number"))
        }
    }
}
