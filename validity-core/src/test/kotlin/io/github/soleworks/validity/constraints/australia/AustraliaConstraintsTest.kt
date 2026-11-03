package io.github.soleworks.validity.constraints.australia

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.github.soleworks.validity.constraints.validate
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class AustraliaConstraintsTest {
    @Nested
    @DisplayName("When abn is called")
    inner class Abn {
        @ParameterizedTest
        @ValueSource(strings = ["53710731177", "60 493 413 053", "51824753556"])
        fun `given a valid ABN should accept it`(
            value: String
        ) {
            val node = ValidationNode(path = "abn", value = value).apply { abn() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["53710731170", "5371073117"])
        fun `given an invalid ABN should report it`(
            value: String
        ) {
            val node = ValidationNode(path = "abn", value = value).apply { abn() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "abn", message = "must be a valid ABN", code = "abn")
            )
        }
    }

    @Nested
    @DisplayName("When tfn is called")
    inner class Tfn {
        @ParameterizedTest
        @ValueSource(strings = ["468344386", "585 129 888", "123456782"])
        fun `given a valid TFN should accept it`(
            value: String
        ) {
            val node = ValidationNode(path = "tfn", value = value).apply { tfn() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["468344380", "1234567"])
        fun `given an invalid TFN should report it`(
            value: String
        ) {
            val node = ValidationNode(path = "tfn", value = value).apply { tfn() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "tfn", message = "must be a valid TFN", code = "tfn")
            )
        }
    }
}
