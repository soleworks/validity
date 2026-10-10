package io.github.soleworks.validity.constraints.germany

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class GermanyConstraintsTest {
    @Nested
    @DisplayName("When steuerId is called")
    inner class SteuerId {
        @ParameterizedTest
        @ValueSource(strings = ["26954371827", "86095742719", "65929970489", "79608434120", "659/299/7048/9"])
        fun `given a valid Steuer-IdNr should accept it`(value: String) {
            val node = ValidationNode("steuerId", value).apply { steuerId() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(
            strings = [
                "26954371828",
                "86095752719",
                "8609575271",
                "860957527190",
                "65299970489",
                "65999970489",
                "6592997048-9"
            ]
        )
        fun `given an invalid Steuer-IdNr should report it`(value: String) {
            val node = ValidationNode("steuerId", value).apply { steuerId() }

            node.validate() shouldBe listOf(Violation("steuerId", "must be a valid Steuer-IdNr", "steuerId"))
        }
    }
}
