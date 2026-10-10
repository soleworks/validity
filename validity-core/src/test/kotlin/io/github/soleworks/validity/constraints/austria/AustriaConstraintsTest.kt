package io.github.soleworks.validity.constraints.austria

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class AustriaConstraintsTest {
    @Nested
    @DisplayName("When abgabenkontonummer is called")
    inner class Abgabenkontonummer {
        @ParameterizedTest
        @ValueSource(strings = ["931736581", "93-173/6581", "93--173/6581"])
        fun `given a valid Abgabenkontonummer should accept it`(value: String) {
            val node = ValidationNode("abgabenkontonummer", value).apply { abgabenkontonummer() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["999999999", "93 173 6581", "93-173/65811", "93-173/658"])
        fun `given an invalid Abgabenkontonummer should report it`(value: String) {
            val node = ValidationNode("abgabenkontonummer", value).apply { abgabenkontonummer() }

            node.validate() shouldBe listOf(Violation("abgabenkontonummer", "must be a valid Abgabenkontonummer", "abgabenkontonummer"))
        }
    }
}
