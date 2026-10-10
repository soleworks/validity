package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ColorConstraintsTest {
    @Nested
    @DisplayName("When hexColor is called")
    inner class HexColor {
        @ParameterizedTest
        @ValueSource(strings = ["#fff", "#FFAA00", "#ffaa0080", "#fffa"])
        fun `given a valid hexadecimal color should accept it`(color: String) {
            val node = ValidationNode("color", color).apply { hexColor() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["fff", "#ff", "#ggg", "#fffff"])
        fun `given an invalid hexadecimal color should report it`(color: String) {
            val node = ValidationNode("color", color).apply { hexColor() }

            node.validate() shouldBe listOf(Violation("color", "must be a valid hexadecimal color", "hexColor"))
        }
    }

    @Nested
    @DisplayName("When rgbColor is called")
    inner class RgbColor {
        @ParameterizedTest
        @ValueSource(strings = ["rgb(255, 0, 0)", "rgba(0,0,0,0.5)", "rgb(10,20,30)"])
        fun `given a valid RGB color should accept it`(color: String) {
            val node = ValidationNode("color", color).apply { rgbColor() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["rgb(256, 0, 0)", "rgb(255, 0)", "rgba(0,0,0,2)", "#ff0000"])
        fun `given an invalid RGB color should report it`(color: String) {
            val node = ValidationNode("color", color).apply { rgbColor() }

            node.validate() shouldBe listOf(Violation("color", "must be a valid RGB color", "rgbColor"))
        }
    }

    @Nested
    @DisplayName("When hslColor is called")
    inner class HslColor {
        @ParameterizedTest
        @ValueSource(strings = ["hsl(120, 100%, 50%)", "hsla(240,50%,50%,0.3)"])
        fun `given a valid HSL color should accept it`(color: String) {
            val node = ValidationNode("color", color).apply { hslColor() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["hsl(361, 100%, 50%)", "hsl(120, 101%, 50%)", "hsl(120, 100, 50)"])
        fun `given an invalid HSL color should report it`(color: String) {
            val node = ValidationNode("color", color).apply { hslColor() }

            node.validate() shouldBe listOf(Violation("color", "must be a valid HSL color", "hslColor"))
        }
    }
}
