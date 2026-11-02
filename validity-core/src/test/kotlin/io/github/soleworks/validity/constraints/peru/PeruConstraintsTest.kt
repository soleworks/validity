package io.github.soleworks.validity.constraints.peru

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.github.soleworks.validity.constraints.validate
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class PeruConstraintsTest {
    @Nested
    @DisplayName("When ruc is called")
    inner class Ruc {
        @ParameterizedTest
        @ValueSource(strings = ["20654444634", "10454409103", "20131312955"])
        fun `given a valid RUC should accept it`(
            value: String
        ) {
            val node = ValidationNode(path = "ruc", value = value).apply { ruc() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["20654444630", "30654444634", "2065444463"])
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
    @DisplayName("When dni is called")
    inner class Dni {
        @ParameterizedTest
        @ValueSource(strings = ["99530552", "99530552-1", "16168281K", "30626116I"])
        fun `given a valid DNI should accept it`(
            value: String
        ) {
            val node = ValidationNode(path = "dni", value = value).apply { dni() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["995305520", "1616828", "16168281L"])
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
}
