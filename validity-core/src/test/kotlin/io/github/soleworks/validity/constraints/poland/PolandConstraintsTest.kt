package io.github.soleworks.validity.constraints.poland

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class PolandConstraintsTest {
    @Nested
    @DisplayName("When pesel is called")
    inner class Pesel {
        @ParameterizedTest
        @ValueSource(strings = ["02070803628", "02870803622", "02670803626", "01510813623", "02070800090"])
        fun `given a PESEL with a valid date and check digit should accept it`(value: String) {
            val node = ValidationNode("pesel", value).apply { pesel() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["020708036285", "02223013623", "02070800091", "02870800078", "02 070-8036/28"])
        fun `given a PESEL with a wrong check digit, an impossible date or another format should report it`(
            value: String
        ) {
            val node = ValidationNode("pesel", value).apply { pesel() }

            node.validate() shouldBe listOf(Violation("pesel", "must be a valid PESEL", "pesel"))
        }
    }

    @Nested
    @DisplayName("When nip is called")
    inner class Nip {
        @ParameterizedTest
        @ValueSource(strings = ["2234567895", "5931423811", "2596048500", "4163450312"])
        fun `given a NIP with a valid check digit should accept it`(value: String) {
            val node = ValidationNode("nip", value).apply { nip() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["2234567855", "223456789", "22 345-678/95", "5931423812", "2596048505"])
        fun `given a NIP with a wrong check digit or another format should report it`(value: String) {
            val node = ValidationNode("nip", value).apply { nip() }

            node.validate() shouldBe listOf(Violation("nip", "must be a valid NIP", "nip"))
        }
    }
}
