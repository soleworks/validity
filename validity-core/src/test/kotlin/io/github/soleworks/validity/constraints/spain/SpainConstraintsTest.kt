package io.github.soleworks.validity.constraints.spain

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
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
        fun `given a valid DNI should accept it`(value: String) {
            val node = ValidationNode("dni", value).apply { dni() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["123456789", "12345678A", "12345 678Z", "12345678-Z"])
        fun `given an invalid DNI should report it`(value: String) {
            val node = ValidationNode("dni", value).apply { dni() }

            node.validate() shouldBe listOf(Violation("dni", "must be a valid DNI"))
        }
    }

    @Nested
    @DisplayName("When nie is called")
    inner class Nie {
        @ParameterizedTest
        @ValueSource(strings = ["X1234567l", "x1234567l", "X1234567L", "Y1234567X", "Z1234567R"])
        fun `given a valid NIE should accept it`(value: String) {
            val node = ValidationNode("nie", value).apply { nie() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["X1234567A", "Y1234567B", "Z1234567C", "A1234567L"])
        fun `given an invalid NIE should report it`(value: String) {
            val node = ValidationNode("nie", value).apply { nie() }

            node.validate() shouldBe listOf(Violation("nie", "must be a valid NIE"))
        }
    }

    @Nested
    @DisplayName("When nif is called")
    inner class Nif {
        @ParameterizedTest
        @ValueSource(strings = ["00054237A", "54237A", "X1234567L", "Z1234567R", "M2812345C", "Y2812345B"])
        fun `given a valid NIF should accept it`(value: String) {
            val node = ValidationNode("nif", value).apply { nif() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["M2812345CR", "A2812345C", "0/005 423-7A", "00054237U"])
        fun `given an invalid NIF should report it`(value: String) {
            val node = ValidationNode("nif", value).apply { nif() }

            node.validate() shouldBe listOf(Violation("nif", "must be a valid NIF"))
        }
    }
}
