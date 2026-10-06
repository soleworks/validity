package io.github.soleworks.validity.constraints.finland

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class FinlandConstraintsTest {
    @Nested
    @DisplayName("When hetu is called")
    inner class Hetu {
        @ParameterizedTest
        @ValueSource(strings = ["131052-308T", "131002+308W", "131019A3089"])
        fun `given a valid HETU should accept it`(value: String) {
            val node = ValidationNode("hetu", value).apply { hetu() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["131052308T", "131052-308TT", "131052S308T", "13 1052-308/T", "290219A1111"])
        fun `given an invalid HETU should report it`(value: String) {
            val node = ValidationNode("hetu", value).apply { hetu() }

            node.validate() shouldBe listOf(Violation("hetu", "must be a valid HETU"))
        }
    }
}
