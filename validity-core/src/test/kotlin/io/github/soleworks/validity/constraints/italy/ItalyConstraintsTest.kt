package io.github.soleworks.validity.constraints.italy

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ItalyConstraintsTest {
    @Nested
    @DisplayName("When codiceFiscale is called")
    inner class CodiceFiscale {
        @ParameterizedTest
        @ValueSource(strings = ["DMLPRY77D15H501F", "AXXFAXTTD41H501D"])
        fun `given a valid codice fiscale should accept it`(value: String) {
            val node = ValidationNode("codiceFiscale", value).apply { codiceFiscale() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(
            strings = [
                "DML PRY/77D15H501-F",
                "DMLPRY77D15H501",
                "DMLPRY77D15H501FF",
                "AAPPRY77D15H501F",
                "DMLAXA77D15H501F",
                "AXXFAX90A01Z001F",
                "DMLPRY77B29H501F",
                "AXXFAX3TD41H501E"
            ]
        )
        fun `given an invalid codice fiscale should report it`(value: String) {
            val node = ValidationNode("codiceFiscale", value).apply { codiceFiscale() }

            node.validate() shouldBe listOf(Violation("codiceFiscale", "must be a valid codice fiscale", "codiceFiscale"))
        }
    }

    @Nested
    @DisplayName("When cie is called")
    inner class Cie {
        @ParameterizedTest
        @ValueSource(strings = ["CR43675TM", "CA79382RA"])
        fun `given a valid CIE should accept it`(value: String) {
            val node = ValidationNode("cie", value).apply { cie() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["CA00000AA", "CB2342TG", "CS123456A", "C1236EC"])
        fun `given an invalid CIE should report it`(value: String) {
            val node = ValidationNode("cie", value).apply { cie() }

            node.validate() shouldBe listOf(Violation("cie", "must be a valid CIE number", "cie"))
        }
    }
}
