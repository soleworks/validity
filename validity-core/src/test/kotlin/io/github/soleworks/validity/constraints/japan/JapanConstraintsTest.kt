package io.github.soleworks.validity.constraints.japan

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.github.soleworks.validity.constraints.validate
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class JapanConstraintsTest {
    @Nested
    @DisplayName("When corporateNumber is called")
    inner class CorporateNumber {
        @ParameterizedTest
        @ValueSource(strings = ["7785935252181", "3375010198817", "5835678256246"])
        fun `given a valid corporate number should accept it`(
            value: String
        ) {
            val node = ValidationNode(path = "corporateNumber", value = value).apply { corporateNumber() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["7785935252180", "778593525218"])
        fun `given an invalid corporate number should report it`(
            value: String
        ) {
            val node = ValidationNode(path = "corporateNumber", value = value).apply { corporateNumber() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(
                    path = "corporateNumber",
                    message = "must be a valid corporate number",
                    code = "corporateNumber"
                )
            )
        }
    }

    @Nested
    @DisplayName("When myNumber is called")
    inner class MyNumber {
        @ParameterizedTest
        @ValueSource(strings = ["695017077064", "7104 0386 9403", "123456789018"])
        fun `given a valid My Number should accept it`(
            value: String
        ) {
            val node = ValidationNode(path = "myNumber", value = value).apply { myNumber() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["695017077060", "69501707706"])
        fun `given an invalid My Number should report it`(
            value: String
        ) {
            val node = ValidationNode(path = "myNumber", value = value).apply { myNumber() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "myNumber", message = "must be a valid My Number", code = "myNumber")
            )
        }
    }
}
