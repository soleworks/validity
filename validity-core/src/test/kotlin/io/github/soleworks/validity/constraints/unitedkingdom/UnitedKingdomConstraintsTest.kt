package io.github.soleworks.validity.constraints.unitedkingdom

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.github.soleworks.validity.constraints.validate
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class UnitedKingdomConstraintsTest {
    @Nested
    @DisplayName("When vat is called")
    inner class Vat {
        @ParameterizedTest
        @ValueSource(strings = ["385599877", "GB408650610", "GB 980 7806 84", "980780684001"])
        fun `given a valid VAT should accept it`(
            value: String
        ) {
            val node = ValidationNode(path = "vat", value = value).apply { vat() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["385599870", "GB38559987"])
        fun `given an invalid VAT should report it`(
            value: String
        ) {
            val node = ValidationNode(path = "vat", value = value).apply { vat() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "vat", message = "must be a valid VAT number", code = "vat")
            )
        }
    }

    @Nested
    @DisplayName("When nino is called")
    inner class Nino {
        @ParameterizedTest
        @ValueSource(strings = ["AB123456C", "AB 12 34 56 C"])
        fun `given a valid NINO should accept it`(
            value: String
        ) {
            val node = ValidationNode(path = "nino", value = value).apply { nino() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["BG123456C", "AO123456C", "AB123456E", "ab123456c"])
        fun `given an invalid NINO should report it`(
            value: String
        ) {
            val node = ValidationNode(path = "nino", value = value).apply { nino() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "nino", message = "must be a valid National Insurance number", code = "nino")
            )
        }
    }
}
