package io.github.soleworks.validity.constraints.sweden

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.github.soleworks.validity.constraints.validate
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class SwedenConstraintsTest {
    @Nested
    @DisplayName("When organisationsnummer is called")
    inner class Organisationsnummer {
        @ParameterizedTest
        @ValueSource(strings = ["2836185831", "730092-9986", "5560360793"])
        fun `given a valid organisationsnummer should accept it`(
            value: String
        ) {
            val node = ValidationNode(path = "organisationsnummer", value = value).apply { organisationsnummer() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["2836185830", "28361858"])
        fun `given an invalid organisationsnummer should report it`(
            value: String
        ) {
            val node = ValidationNode(path = "organisationsnummer", value = value).apply { organisationsnummer() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(
                    path = "organisationsnummer",
                    message = "must be a valid organisationsnummer",
                    code = "organisationsnummer"
                )
            )
        }
    }

    @Nested
    @DisplayName("When personnummer is called")
    inner class Personnummer {
        @ParameterizedTest
        @ValueSource(strings = ["811211-2225", "19811211-2225", "8112112225", "811211+2225", "630383-2015"])
        fun `given a valid personnummer should accept it`(
            value: String
        ) {
            val node = ValidationNode(path = "personnummer", value = value).apply { personnummer() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["811211-2220", "811311-2225", "81121-12225", "811245-2225", "811200-2225"])
        fun `given an invalid personnummer should report it`(
            value: String
        ) {
            val node = ValidationNode(path = "personnummer", value = value).apply { personnummer() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "personnummer", message = "must be a valid personnummer", code = "personnummer")
            )
        }
    }
}
