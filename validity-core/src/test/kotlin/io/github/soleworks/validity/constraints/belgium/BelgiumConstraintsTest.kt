package io.github.soleworks.validity.constraints.belgium

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class BelgiumConstraintsTest {
    @Nested
    @DisplayName("When rijksregisternummer is called")
    inner class Rijksregisternummer {
        @ParameterizedTest
        @ValueSource(strings = ["00012511148", "00/0125-11148", "00000011115", "00012511119"])
        fun `given a valid rijksregisternummer should accept it`(value: String) {
            val node = ValidationNode("rijksregisternummer", value).apply { rijksregisternummer() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(
            strings = [
                "00 01 2511148",
                "01022911148",
                "00013211148",
                "0001251114",
                "000125111480",
                "00012511149"
            ]
        )
        fun `given an invalid rijksregisternummer should report it`(value: String) {
            val node = ValidationNode("rijksregisternummer", value).apply { rijksregisternummer() }

            node.validate() shouldBe listOf(Violation("rijksregisternummer", "must be a valid rijksregisternummer"))
        }
    }
}
