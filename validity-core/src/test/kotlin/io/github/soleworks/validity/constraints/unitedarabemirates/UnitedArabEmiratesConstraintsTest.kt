package io.github.soleworks.validity.constraints.unitedarabemirates

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.github.soleworks.validity.constraints.validate
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class UnitedArabEmiratesConstraintsTest {
    @Nested
    @DisplayName("When tradeLicense is called")
    inner class TradeLicense {
        @ParameterizedTest
        @ValueSource(strings = ["CN-1234567", "123456", "DED/2020/12345"])
        fun `given a valid trade license number should accept it`(
            value: String
        ) {
            val node = ValidationNode(path = "tradeLicense", value = value).apply { tradeLicense() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["1234", "-12345", "cn-1234567"])
        fun `given an invalid trade license number should report it`(
            value: String
        ) {
            val node = ValidationNode(path = "tradeLicense", value = value).apply { tradeLicense() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(
                    path = "tradeLicense",
                    message = "must be a valid trade license number",
                    code = "tradeLicense"
                )
            )
        }
    }

    @Nested
    @DisplayName("When emiratesId is called")
    inner class EmiratesId {
        @ParameterizedTest
        @ValueSource(strings = ["784196865703050", "784-1990-1234567-6", "784200111222335"])
        fun `given a valid Emirates ID should accept it`(
            value: String
        ) {
            val node = ValidationNode(path = "emiratesId", value = value).apply { emiratesId() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["784196865703051", "785196865703059"])
        fun `given an invalid Emirates ID should report it`(
            value: String
        ) {
            val node = ValidationNode(path = "emiratesId", value = value).apply { emiratesId() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "emiratesId", message = "must be a valid Emirates ID", code = "emiratesId")
            )
        }
    }
}
