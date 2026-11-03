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

    @Nested
    @DisplayName("When siren is called")
    inner class Siren {
        @ParameterizedTest
        @ValueSource(strings = ["934461799", "462 042 078", "732829320"])
        fun `given a valid SIREN should accept it`(
            value: String
        ) {
            val node = ValidationNode(path = "siren", value = value).apply { siren() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["934461790", "93446179"])
        fun `given an invalid SIREN should report it`(
            value: String
        ) {
            val node = ValidationNode(path = "siren", value = value).apply { siren() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "siren", message = "must be a valid SIREN", code = "siren")
            )
        }
    }

    @Nested
    @DisplayName("When siret is called")
    inner class Siret {
        @ParameterizedTest
        @ValueSource(
            strings = [
                "82247920095500",
                "372 157 586 22246",
                "73282932000074",
                "35600000000048",
                "35600000000010"
            ]
        )
        fun `given a valid SIRET should accept it`(
            value: String
        ) {
            val node = ValidationNode(path = "siret", value = value).apply { siret() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["82247920095501", "35600000000011"])
        fun `given an invalid SIRET should report it`(
            value: String
        ) {
            val node = ValidationNode(path = "siret", value = value).apply { siret() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "siret", message = "must be a valid SIRET", code = "siret")
            )
        }
    }
}
