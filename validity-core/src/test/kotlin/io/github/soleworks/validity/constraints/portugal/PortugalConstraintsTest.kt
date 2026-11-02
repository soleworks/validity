package io.github.soleworks.validity.constraints.portugal

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.github.soleworks.validity.constraints.validate
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
        @ValueSource(strings = ["335429106", "123456789", "232013969"])
        fun `given a valid NIF should accept it`(
            value: String
        ) {
            val node = ValidationNode(path = "nif", value = value).apply { nif() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["335429100", "690798016", "023456789"])
        fun `given an invalid NIF should report it`(
            value: String
        ) {
            val node = ValidationNode(path = "nif", value = value).apply { nif() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "nif", message = "must be a valid NIF", code = "nif")
            )
        }
    }

    @Nested
    @DisplayName("When nipc is called")
    inner class Nipc {
        @ParameterizedTest
        @ValueSource(strings = ["690798016", "503504564", "814877400"])
        fun `given a valid NIPC should accept it`(
            value: String
        ) {
            val node = ValidationNode(path = "nipc", value = value).apply { nipc() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["690798010", "335429106"])
        fun `given an invalid NIPC should report it`(
            value: String
        ) {
            val node = ValidationNode(path = "nipc", value = value).apply { nipc() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "nipc", message = "must be a valid NIPC", code = "nipc")
            )
        }
    }
}
