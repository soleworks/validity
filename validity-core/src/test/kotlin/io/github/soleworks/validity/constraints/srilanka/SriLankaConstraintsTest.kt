package io.github.soleworks.validity.constraints.srilanka

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class SriLankaConstraintsTest {
    @Nested
    @DisplayName("When nic is called")
    inner class Nic {
        @ParameterizedTest
        @ValueSource(strings = ["722222222v", "993151225X", "199931512253", "200023125632"])
        fun `given a valid NIC should accept it`(value: String) {
            val node = ValidationNode("nic", value).apply { nic() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["023125648V", "02135465462", "199931512253X", "055321231x"])
        fun `given an invalid NIC should report it`(value: String) {
            val node = ValidationNode("nic", value).apply { nic() }

            node.validate() shouldBe listOf(Violation("nic", "must be a valid NIC", "nic"))
        }
    }
}
