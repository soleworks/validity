package io.github.soleworks.validity.constraints.belgium

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.github.soleworks.validity.constraints.validate
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class BelgiumConstraintsTest {
    @Nested
    @DisplayName("When bce is called")
    inner class Bce {
        @ParameterizedTest
        @ValueSource(strings = ["0661488530", "0569.657.937", "0403170701"])
        fun `given a valid enterprise number should accept it`(
            value: String
        ) {
            val node = ValidationNode(path = "bce", value = value).apply { bce() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["0661488531", "2661488530", "066148853"])
        fun `given an invalid enterprise number should report it`(
            value: String
        ) {
            val node = ValidationNode(path = "bce", value = value).apply { bce() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "bce", message = "must be a valid enterprise number", code = "bce")
            )
        }
    }

    @Nested
    @DisplayName("When nationalNumber is called")
    inner class NationalNumber {
        @ParameterizedTest
        @ValueSource(strings = ["74022569427", "94.06.08-365.53", "85073003328"])
        fun `given a valid national number should accept it`(
            value: String
        ) {
            val node = ValidationNode(path = "nationalNumber", value = value).apply { nationalNumber() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["74022569420", "74132569427", "7402256942"])
        fun `given an invalid national number should report it`(
            value: String
        ) {
            val node = ValidationNode(path = "nationalNumber", value = value).apply { nationalNumber() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "nationalNumber", message = "must be a valid national number", code = "nationalNumber")
            )
        }
    }
}
