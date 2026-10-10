package io.github.soleworks.validity.constraints.norway

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class NorwayConstraintsTest {
    @Nested
    @DisplayName("When fodselsnummer is called")
    inner class Fodselsnummer {
        @ParameterizedTest
        @ValueSource(
            strings = [
                "09053426694",
                "26028338723",
                "08031470790",
                "12051539514",
                "02077448074",
                "14035638319",
                "13031379673",
                "29126214926"
            ]
        )
        fun `given a valid fødselsnummer should accept it`(value: String) {
            val node = ValidationNode("fodselsnummer", value).apply { fodselsnummer() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["09053426699", "00000000000", "26028338724", "92031470790"])
        fun `given an invalid fødselsnummer should report it`(value: String) {
            val node = ValidationNode("fodselsnummer", value).apply { fodselsnummer() }

            node.validate() shouldBe listOf(Violation("fodselsnummer", "must be a valid fødselsnummer", "fodselsnummer"))
        }
    }
}
