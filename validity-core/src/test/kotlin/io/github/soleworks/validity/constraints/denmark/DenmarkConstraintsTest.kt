package io.github.soleworks.validity.constraints.denmark

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class DenmarkConstraintsTest {
    @Nested
    @DisplayName("When cpr is called")
    inner class Cpr {
        @ParameterizedTest
        @ValueSource(
            strings = [
                "010111-1113",
                "0101110117",
                "2110084008",
                "2110489008",
                "2110595002",
                "2110197007",
                "0101110230"
            ]
        )
        fun `given a valid CPR number should accept it`(value: String) {
            val node = ValidationNode("cpr", value).apply { cpr() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["010111/1113", "010111111", "01011111133", "2110485008", "2902034000", "0101110630"])
        fun `given an invalid CPR number should report it`(value: String) {
            val node = ValidationNode("cpr", value).apply { cpr() }

            node.validate() shouldBe listOf(Violation("cpr", "must be a valid CPR number", "cpr"))
        }
    }
}
