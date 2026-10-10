package io.github.soleworks.validity.constraints.india

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class IndiaConstraintsTest {
    @Nested
    @DisplayName("When aadhaar is called")
    inner class Aadhaar {
        @ParameterizedTest
        @ValueSource(strings = ["298448863364", "2984 4886 3364"])
        fun `given a valid Aadhaar should accept it`(value: String) {
            val node = ValidationNode("aadhaar", value).apply { aadhaar() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["99999999R", "12345678Z", "01234567L", "X1234567L"])
        fun `given an invalid Aadhaar should report it`(value: String) {
            val node = ValidationNode("aadhaar", value).apply { aadhaar() }

            node.validate() shouldBe listOf(Violation("aadhaar", "must be a valid Aadhaar", "aadhaar"))
        }
    }

    @Nested
    @DisplayName("When pan is called")
    inner class Pan {
        @ParameterizedTest
        @ValueSource(strings = ["AAAAA1111A", "BBBBB1111B", "FFFFF0001F", "ZZZFZ9999Z"])
        fun `given a valid PAN should accept it`(value: String) {
            val node = ValidationNode("pan", value).apply { pan() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["DDDDD1111D", "1234567890", "ABCDEFGHIJ", "ABCDE1234F", "AAAAA0000A"])
        fun `given an invalid PAN should report it`(value: String) {
            val node = ValidationNode("pan", value).apply { pan() }

            node.validate() shouldBe listOf(Violation("pan", "must be a valid PAN", "pan"))
        }
    }
}
