package io.github.soleworks.validity.constraints.italy

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.github.soleworks.validity.constraints.validate
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ItalyConstraintsTest {
    @Nested
    @DisplayName("When partitaIva is called")
    inner class PartitaIva {
        @ParameterizedTest
        @ValueSource(strings = ["55396190013", "68438700343", "00743110157"])
        fun `given a valid partita IVA should accept it`(
            value: String
        ) {
            val node = ValidationNode(path = "partitaIva", value = value).apply { partitaIva() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["55396190010", "00000001017", "5539619001"])
        fun `given an invalid partita IVA should report it`(
            value: String
        ) {
            val node = ValidationNode(path = "partitaIva", value = value).apply { partitaIva() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "partitaIva", message = "must be a valid partita IVA", code = "partitaIva")
            )
        }
    }

    @Nested
    @DisplayName("When codiceFiscale is called")
    inner class CodiceFiscale {
        @ParameterizedTest
        @ValueSource(strings = ["XUFIAX24L20X122G", "AGOCIH30M01S510A", "RSSMRA85T10A562S"])
        fun `given a valid codice fiscale should accept it`(
            value: String
        ) {
            val node = ValidationNode(path = "codiceFiscale", value = value).apply { codiceFiscale() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["XUFIAX24L20X122A", "AGOCIH30M01S510B", "rssmra85t10a562s", "RSSMRA85T10A56"])
        fun `given an invalid codice fiscale should report it`(
            value: String
        ) {
            val node = ValidationNode(path = "codiceFiscale", value = value).apply { codiceFiscale() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "codiceFiscale", message = "must be a valid codice fiscale", code = "codiceFiscale")
            )
        }
    }
}
