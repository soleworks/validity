package io.github.soleworks.validity.constraints.portugal

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class PortugalConstraintsTest {
    @Nested
    @DisplayName("When nif is called")
    inner class Nif {
        @ParameterizedTest
        @ValueSource(strings = ["299999998", "299992020"])
        fun `given a valid NIF should accept it`(value: String) {
            val node = ValidationNode("nif", value).apply { nif() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["2999999988", "29999999", "29 999-999/8"])
        fun `given an invalid NIF should report it`(value: String) {
            val node = ValidationNode("nif", value).apply { nif() }

            node.validate() shouldBe listOf(Violation("nif", "must be a valid NIF", "nif"))
        }
    }
}
