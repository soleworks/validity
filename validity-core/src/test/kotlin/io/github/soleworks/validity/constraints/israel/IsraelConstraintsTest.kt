package io.github.soleworks.validity.constraints.israel

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.github.soleworks.validity.constraints.validate
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class IsraelConstraintsTest {
    @Nested
    @DisplayName("When teudatZehut is called")
    inner class TeudatZehut {
        @ParameterizedTest
        @ValueSource(strings = ["219472156", "334795465", "337090443"])
        fun `given a valid teudat zehut should accept it`(
            value: String
        ) {
            val node = ValidationNode(path = "teudatZehut", value = value).apply { teudatZehut() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["123456789", "12345678A", "12345 678Z", "219772156", "1234567L"])
        fun `given an invalid teudat zehut should report it`(
            value: String
        ) {
            val node = ValidationNode(path = "teudatZehut", value = value).apply { teudatZehut() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "teudatZehut", message = "must be a valid teudat zehut", code = "teudatZehut")
            )
        }
    }

    @Nested
    @DisplayName("When companyNumber is called")
    inner class CompanyNumber {
        @ParameterizedTest
        @ValueSource(strings = ["521387803", "527736680", "516179157"])
        fun `given a valid company number should accept it`(
            value: String
        ) {
            val node = ValidationNode(path = "companyNumber", value = value).apply { companyNumber() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["521387800", "421387803"])
        fun `given an invalid company number should report it`(
            value: String
        ) {
            val node = ValidationNode(path = "companyNumber", value = value).apply { companyNumber() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "companyNumber", message = "must be a valid company number", code = "companyNumber")
            )
        }
    }
}
