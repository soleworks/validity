package io.github.soleworks.validity.constraints.southkorea

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class SouthKoreaConstraintsTest {
    @Nested
    @DisplayName("When rrn is called")
    inner class Rrn {
        @ParameterizedTest
        @ValueSource(
            strings = [
                "861224-2567484",
                "960223-2499378",
                "790707-1133360",
                "850101-5000005",
                "010101-7000006"
            ]
        )
        fun `given a valid RRN should accept it`(value: String) {
            val node = ValidationNode("rrn", value).apply { rrn() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(
            strings = [
                "861224-2567481",
                "861324-2567481",
                "960292-2499371",
                "861224-9567484",
                "861324-2567481123",
                "hello-world"
            ]
        )
        fun `given an invalid RRN should report it`(value: String) {
            val node = ValidationNode("rrn", value).apply { rrn() }

            node.validate() shouldBe listOf(Violation("rrn", "must be a valid RRN"))
        }
    }
}
