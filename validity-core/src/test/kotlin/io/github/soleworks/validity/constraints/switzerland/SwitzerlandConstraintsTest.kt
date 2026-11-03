package io.github.soleworks.validity.constraints.switzerland

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.github.soleworks.validity.constraints.validate
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class SwitzerlandConstraintsTest {
    @Nested
    @DisplayName("When uid is called")
    inner class Uid {
        @ParameterizedTest
        @ValueSource(strings = ["CHE785278205", "CHE-887.359.855", "122644347", "CHE-116.281.710"])
        fun `given a valid UID should accept it`(
            value: String
        ) {
            val node = ValidationNode(path = "uid", value = value).apply { uid() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["CHE785278200", "CHE-785278205", "CHE78527820"])
        fun `given an invalid UID should report it`(
            value: String
        ) {
            val node = ValidationNode(path = "uid", value = value).apply { uid() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "uid", message = "must be a valid UID", code = "uid")
            )
        }
    }

    @Nested
    @DisplayName("When ahv is called")
    inner class Ahv {
        @ParameterizedTest
        @ValueSource(strings = ["7564699338286", "756.9390.8354.23", "7569217076985"])
        fun `given a valid AHV should accept it`(
            value: String
        ) {
            val node = ValidationNode(path = "ahv", value = value).apply { ahv() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["7564699338280", "7574699338285"])
        fun `given an invalid AHV should report it`(
            value: String
        ) {
            val node = ValidationNode(path = "ahv", value = value).apply { ahv() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "ahv", message = "must be a valid AHV number", code = "ahv")
            )
        }
    }
}
