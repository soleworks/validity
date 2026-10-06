package io.github.soleworks.validity.constraints.malta

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class MaltaConstraintsTest {
    @Nested
    @DisplayName("When idCardNumber is called")
    inner class IdCardNumber {
        @ParameterizedTest
        @ValueSource(strings = ["1234567A", "34581M", "199Z"])
        fun `given a valid ID card number should accept it`(value: String) {
            val node = ValidationNode("idCardNumber", value).apply { idCardNumber() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["11234567A", "12/34-567 A", "1234560A", "0000000M", "3200100G"])
        fun `given an invalid ID card number should report it`(value: String) {
            val node = ValidationNode("idCardNumber", value).apply { idCardNumber() }

            node.validate() shouldBe listOf(Violation("idCardNumber", "must be a valid ID card number"))
        }
    }
}
