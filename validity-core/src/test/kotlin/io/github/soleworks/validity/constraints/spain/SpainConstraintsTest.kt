package io.github.soleworks.validity.constraints.spain

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.github.soleworks.validity.constraints.validate
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class SpainConstraintsTest {
    @Nested
    @DisplayName("When dni is called")
    inner class Dni {
        @ParameterizedTest
        @ValueSource(strings = ["99999999R", "12345678Z", "01234567L", "01234567l"])
        fun `given a valid DNI should accept it`(
            value: String
        ) {
            val node = ValidationNode(path = "dni", value = value).apply { dni() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["123456789", "12345678A", "12345 678Z", "12345678-Z"])
        fun `given an invalid DNI should report it`(
            value: String
        ) {
            val node = ValidationNode(path = "dni", value = value).apply { dni() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "dni", message = "must be a valid DNI", code = "dni")
            )
        }
    }

    @Nested
    @DisplayName("When nie is called")
    inner class Nie {
        @ParameterizedTest
        @ValueSource(strings = ["X1234567l", "x1234567l", "X1234567L", "Y1234567X", "Z1234567R"])
        fun `given a valid NIE should accept it`(
            value: String
        ) {
            val node = ValidationNode(path = "nie", value = value).apply { nie() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["X1234567A", "Y1234567B", "Z1234567C", "A1234567L"])
        fun `given an invalid NIE should report it`(
            value: String
        ) {
            val node = ValidationNode(path = "nie", value = value).apply { nie() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "nie", message = "must be a valid NIE", code = "nie")
            )
        }
    }
}
