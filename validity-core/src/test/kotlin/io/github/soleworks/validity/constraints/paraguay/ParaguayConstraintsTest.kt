package io.github.soleworks.validity.constraints.paraguay

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.github.soleworks.validity.constraints.validate
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ParaguayConstraintsTest {
    @Nested
    @DisplayName("When ruc is called")
    inner class Ruc {
        @ParameterizedTest
        @ValueSource(strings = ["2899059-5", "28990595", "6819504-4", "80028061-0"])
        fun `given a valid RUC should accept it`(
            value: String
        ) {
            val node = ValidationNode(path = "ruc", value = value).apply { ruc() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["28990590", "1234", "80028061-1"])
        fun `given an invalid RUC should report it`(
            value: String
        ) {
            val node = ValidationNode(path = "ruc", value = value).apply { ruc() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "ruc", message = "must be a valid RUC", code = "ruc")
            )
        }
    }

    @Nested
    @DisplayName("When ci is called")
    inner class Ci {
        @ParameterizedTest
        @ValueSource(strings = ["1234567", "12345"])
        fun `given a valid CI should accept it`(
            value: String
        ) {
            val node = ValidationNode(path = "ci", value = value).apply { ci() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["1234", "12345678901", "123A567"])
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
