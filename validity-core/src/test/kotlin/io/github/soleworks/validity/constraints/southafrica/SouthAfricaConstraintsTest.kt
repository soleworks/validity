package io.github.soleworks.validity.constraints.southafrica

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.github.soleworks.validity.constraints.validate
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class SouthAfricaConstraintsTest {
    @Nested
    @DisplayName("When cipc is called")
    inner class Cipc {
        @ParameterizedTest
        @ValueSource(strings = ["2015/123456/07"])
        fun `given a valid CIPC should accept it`(
            value: String
        ) {
            val node = ValidationNode(path = "cipc", value = value).apply { cipc() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["201512345607", "2015/12345/07"])
        fun `given an invalid CIPC should report it`(
            value: String
        ) {
            val node = ValidationNode(path = "cipc", value = value).apply { cipc() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "cipc", message = "must be a valid company registration number", code = "cipc")
            )
        }
    }

    @Nested
    @DisplayName("When idNumber is called")
    inner class IdNumber {
        @ParameterizedTest
        @ValueSource(strings = ["5810164397017", "7903217675014", "8001015009087"])
        fun `given a valid ID number should accept it`(
            value: String
        ) {
            val node = ValidationNode(path = "idNumber", value = value).apply { idNumber() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["5810164397010", "5813164397017"])
        fun `given an invalid ID number should report it`(
            value: String
        ) {
            val node = ValidationNode(path = "idNumber", value = value).apply { idNumber() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "idNumber", message = "must be a valid ID number", code = "idNumber")
            )
        }
    }
}
