package io.github.soleworks.validity.constraints.france

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.github.soleworks.validity.constraints.validate
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class FranceConstraintsTest {
    @Nested
    @DisplayName("When spi is called")
    inner class Spi {
        @ParameterizedTest
        @ValueSource(strings = ["30 23 217 600 053", "3023217600053"])
        fun `given a valid SPI should accept it`(
            value: String
        ) {
            val node = ValidationNode(path = "spi", value = value).apply { spi() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(
            strings = [
                "30 2 3 217 600 053",
                "3 023217-600/053",
                "3023217600052",
                "3023217500053",
                "30232176000534",
                "302321760005"
            ]
        )
        fun `given an invalid SPI should report it`(
            value: String
        ) {
            val node = ValidationNode(path = "spi", value = value).apply { spi() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "spi", message = "must be a valid SPI", code = "spi")
            )
        }
    }
}
