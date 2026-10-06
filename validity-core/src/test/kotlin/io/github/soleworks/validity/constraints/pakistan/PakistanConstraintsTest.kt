package io.github.soleworks.validity.constraints.pakistan

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class PakistanConstraintsTest {
    @Nested
    @DisplayName("When cnic is called")
    inner class Cnic {
        @ParameterizedTest
        @ValueSource(strings = ["45504-4185771-3", "39915-6182971-9", "72345-2345678-7", "21234-9876543-6"])
        fun `given a valid CNIC should accept it`(value: String) {
            val node = ValidationNode("cnic", value).apply { cnic() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["08000-1234567-5", "74321-87654321-1", "51234-98765-2", "00000-0000000-0", "11111"])
        fun `given an invalid CNIC should report it`(value: String) {
            val node = ValidationNode("cnic", value).apply { cnic() }

            node.validate() shouldBe listOf(Violation("cnic", "must be a valid CNIC"))
        }
    }
}
