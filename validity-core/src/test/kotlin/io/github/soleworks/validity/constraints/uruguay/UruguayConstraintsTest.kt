package io.github.soleworks.validity.constraints.uruguay

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.github.soleworks.validity.constraints.validate
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class UruguayConstraintsTest {
    @Nested
    @DisplayName("When rut is called")
    inner class Rut {
        @ParameterizedTest
        @ValueSource(strings = ["164121540017", "123888530019", "211003420017"])
        fun `given a valid RUT should accept it`(
            value: String
        ) {
            val node = ValidationNode(path = "rut", value = value).apply { rut() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["164121540010", "234121540012", "160000000017", "21100342001", "000000000017"])
        fun `given an invalid RUT should report it`(
            value: String
        ) {
            val node = ValidationNode(path = "rut", value = value).apply { rut() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "rut", message = "must be a valid RUT", code = "rut")
            )
        }
    }

    @Nested
    @DisplayName("When ci is called")
    inner class Ci {
        @ParameterizedTest
        @ValueSource(strings = ["1.234.567-2", "12345672", "123.456-1", "1234561"])
        fun `given a valid CI should accept it`(
            value: String
        ) {
            val node = ValidationNode(path = "ci", value = value).apply { ci() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["12345671", "1.234.567-3", "123456", "1234.567-2"])
        fun `given an invalid CI should report it`(
            value: String
        ) {
            val node = ValidationNode(path = "ci", value = value).apply { ci() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "ci", message = "must be a valid CI", code = "ci")
            )
        }
    }
}
