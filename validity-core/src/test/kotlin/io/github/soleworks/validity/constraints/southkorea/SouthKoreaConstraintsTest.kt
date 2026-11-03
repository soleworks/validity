package io.github.soleworks.validity.constraints.southkorea

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.github.soleworks.validity.constraints.validate
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class SouthKoreaConstraintsTest {
    @Nested
    @DisplayName("When brn is called")
    inner class Brn {
        @ParameterizedTest
        @ValueSource(strings = ["1348672683", "220-81-62517"])
        fun `given a valid BRN should accept it`(
            value: String
        ) {
            val node = ValidationNode(path = "brn", value = value).apply { brn() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["100-12-34567", "101-00-12345", "101-12-00000", "12345"])
        fun `given an invalid BRN should report it`(
            value: String
        ) {
            val node = ValidationNode(path = "brn", value = value).apply { brn() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "brn", message = "must be a valid business registration number", code = "brn")
            )
        }
    }

    @Nested
    @DisplayName("When rrn is called")
    inner class Rrn {
        @ParameterizedTest
        @ValueSource(strings = ["5605231067971", "590419-2090853", "9710139019902"])
        fun `given a valid RRN should accept it`(
            value: String
        ) {
            val node = ValidationNode(path = "rrn", value = value).apply { rrn() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["5605231067970", "5613231067971", "56052-31067971", "5605231977971"])
        fun `given an invalid RRN should report it`(
            value: String
        ) {
            val node = ValidationNode(path = "rrn", value = value).apply { rrn() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "rrn", message = "must be a valid resident registration number", code = "rrn")
            )
        }
    }
}
