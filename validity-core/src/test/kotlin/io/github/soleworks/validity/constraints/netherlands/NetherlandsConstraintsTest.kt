package io.github.soleworks.validity.constraints.netherlands

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class NetherlandsConstraintsTest {
    @Nested
    @DisplayName("When bsn is called")
    inner class Bsn {
        @ParameterizedTest
        @ValueSource(strings = ["174559434", "612890053", "087880532", "386625918"])
        fun `given a valid BSN should accept it`(value: String) {
            val node = ValidationNode("bsn", value).apply { bsn() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(
            strings = [
                "17455943",
                "1745594344",
                "17 455-94/34",
                "612890054",
                "854650703",
                "38240.678",
                "38240a678",
                "abcdefghi"
            ]
        )
        fun `given an invalid BSN should report it`(value: String) {
            val node = ValidationNode("bsn", value).apply { bsn() }

            node.validate() shouldBe listOf(Violation("bsn", "must be a valid BSN"))
        }
    }
}
