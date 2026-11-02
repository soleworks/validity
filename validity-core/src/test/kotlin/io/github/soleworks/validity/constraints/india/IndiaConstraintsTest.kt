package io.github.soleworks.validity.constraints.india

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.github.soleworks.validity.constraints.validate
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class IndiaConstraintsTest {
    @Nested
    @DisplayName("When pan is called")
    inner class Pan {
        @ParameterizedTest
        @ValueSource(strings = ["ABCPE1234F", "AAAPZ1234C"])
        fun `given a valid PAN should accept it`(
            value: String
        ) {
            val node = ValidationNode(path = "pan", value = value).apply { pan() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["ABCDE1234F", "ABCPE0000F", "abcpe1234f"])
        fun `given an invalid PAN should report it`(
            value: String
        ) {
            val node = ValidationNode(path = "pan", value = value).apply { pan() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "pan", message = "must be a valid PAN", code = "pan")
            )
        }
    }

    @Nested
    @DisplayName("When gstin is called")
    inner class Gstin {
        @ParameterizedTest
        @ValueSource(strings = ["10GXMFD3255G2ZB", "18DPNPN5193Z1Z7", "27AAPFU0939F1ZV"])
        fun `given a valid GSTIN should accept it`(
            value: String
        ) {
            val node = ValidationNode(path = "gstin", value = value).apply { gstin() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["10GXMFD3255G2ZA", "40GXMFD3255G2ZB", "10GXMFD3255G0ZB"])
        fun `given an invalid GSTIN should report it`(
            value: String
        ) {
            val node = ValidationNode(path = "gstin", value = value).apply { gstin() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "gstin", message = "must be a valid GSTIN", code = "gstin")
            )
        }
    }
}
