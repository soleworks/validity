package io.github.soleworks.validity.constraints.greece

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class GreeceConstraintsTest {
    @Nested
    @DisplayName("When afm is called")
    inner class Afm {
        @ParameterizedTest
        @ValueSource(strings = ["758426713", "032792320", "054100004"])
        fun `given an AFM with a valid check digit should accept it`(value: String) {
            val node = ValidationNode("afm", value).apply { afm() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(
            strings = [
                "054100005",
                "05410000",
                "0541000055",
                "05 4100005",
                "05-410/0005",
                "658426713",
                "558426713"
            ]
        )
        fun `given an AFM with a wrong check digit, an invalid first digit or another format should report it`(
            value: String
        ) {
            val node = ValidationNode("afm", value).apply { afm() }

            node.validate() shouldBe listOf(Violation("afm", "must be a valid AFM"))
        }
    }
}
