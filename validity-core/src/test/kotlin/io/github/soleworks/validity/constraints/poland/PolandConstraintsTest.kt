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

    @Nested
    @DisplayName("When regon is called")
    inner class Regon {
        @ParameterizedTest
        @ValueSource(strings = ["123456785", "691657182", "12345678512347", "59418566359965"])
        fun `given a 9 or 14 digit REGON with a valid check digit should accept it`(value: String) {
            val node = ValidationNode("regon", value).apply { regon() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["1234567845", "12345673", "123-456-785", "6-9-1-6-5-7-1-8-2"])
        fun `given a REGON with a wrong check digit or another format should report it`(value: String) {
            val node = ValidationNode("regon", value).apply { regon() }

            node.validate() shouldBe listOf(Violation("regon", "must be a valid REGON", "regon"))
        }
    }

    @Nested
    @DisplayName("When dowodOsobisty is called")
    inner class DowodOsobisty {
        @ParameterizedTest
        @ValueSource(strings = ["APH505567", "AYE205410", "AYW036733"])
        fun `given a Polish identity card number with a valid check digit should accept it`(value: String) {
            val node = ValidationNode("dowodOsobisty", value).apply { dowodOsobisty() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["999205411", "AAAAAAAAA", "APH 505567", "AYE205411", "AYW036731"])
        fun `given a Polish identity card number with a wrong check digit or another format should report it`(
            value: String
        ) {
            val node = ValidationNode("dowodOsobisty", value).apply { dowodOsobisty() }

            node.validate() shouldBe listOf(Violation("dowodOsobisty", "must be a valid dowód osobisty number", "dowodOsobisty"))
        }
    }
}
