package io.github.soleworks.validity.constraints.russia

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class RussiaConstraintsTest {
    @Nested
    @DisplayName("When innIndividual is called")
    inner class InnIndividual {
        @ParameterizedTest
        @ValueSource(strings = ["246964567008", "356393289962", "279837166431", "086229647992"])
        fun `given an individual INN with valid check digits should accept it`(value: String) {
            val node = ValidationNode("innIndividual", value).apply { innIndividual() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["012345678912", "246964567009", "90660563173", "9066056317378", "4546366155"])
        fun `given an individual INN with wrong check digits or another length should report it`(value: String) {
            val node = ValidationNode("innIndividual", value).apply { innIndividual() }

            node.validate() shouldBe listOf(Violation("innIndividual", "must be a valid individual INN"))
        }
    }

    @Nested
    @DisplayName("When innLegalEntity is called")
    inner class InnLegalEntity {
        @ParameterizedTest
        @ValueSource(strings = ["0305773929", "5496344268", "0314580754", "8652697156"])
        fun `given a legal entity INN with a valid check digit should accept it`(value: String) {
            val node = ValidationNode("innLegalEntity", value).apply { innLegalEntity() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["0123456789", "0305773928", "906605631", "90660563173", "246964567008"])
        fun `given a legal entity INN with a wrong check digit or another length should report it`(value: String) {
            val node = ValidationNode("innLegalEntity", value).apply { innLegalEntity() }

            node.validate() shouldBe listOf(Violation("innLegalEntity", "must be a valid legal entity INN"))
        }
    }
}
