package io.github.soleworks.validity.constraints.norway

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.github.soleworks.validity.constraints.validate
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class NorwayConstraintsTest {
    @Nested
    @DisplayName("When organisasjonsnummer is called")
    inner class Organisasjonsnummer {
        @ParameterizedTest
        @ValueSource(strings = ["913885821", "914 118 751", "988077917"])
        fun `given a valid organisasjonsnummer should accept it`(
            value: String
        ) {
            val node = ValidationNode(path = "organisasjonsnummer", value = value).apply { organisasjonsnummer() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["913885820", "91388582"])
        fun `given an invalid organisasjonsnummer should report it`(
            value: String
        ) {
            val node = ValidationNode(path = "organisasjonsnummer", value = value).apply { organisasjonsnummer() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(
                    path = "organisasjonsnummer",
                    message = "must be a valid organisasjonsnummer",
                    code = "organisasjonsnummer"
                )
            )
        }
    }

    @Nested
    @DisplayName("When fodselsnummer is called")
    inner class Fodselsnummer {
        @ParameterizedTest
        @ValueSource(strings = ["19045128175", "280587 68814", "15108695088"])
        fun `given a valid fødselsnummer should accept it`(
            value: String
        ) {
            val node = ValidationNode(path = "fodselsnummer", value = value).apply { fodselsnummer() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["19045128170", "1904512817"])
        fun `given an invalid fødselsnummer should report it`(
            value: String
        ) {
            val node = ValidationNode(path = "fodselsnummer", value = value).apply { fodselsnummer() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "fodselsnummer", message = "must be a valid fødselsnummer", code = "fodselsnummer")
            )
        }
    }
}
