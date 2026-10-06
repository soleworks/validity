package io.github.soleworks.validity.constraints.estonia

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class EstoniaConstraintsTest {
    @Nested
    @DisplayName("When isikukood is called")
    inner class Isikukood {
        @ParameterizedTest
        @ValueSource(strings = ["10001010080", "46304280206", "37102250382", "32708101201"])
        fun `given an isikukood with a valid date and check digit should accept it`(value: String) {
            val node = ValidationNode("isikukood", value).apply { isikukood() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["46304280205", "61002293333", "4-6304 28/0206", "4630428020", "463042802066"])
        fun `given an isikukood with an impossible date, a wrong check digit or another format should report it`(
            value: String
        ) {
            val node = ValidationNode("isikukood", value).apply { isikukood() }

            node.validate() shouldBe listOf(Violation("isikukood", "must be a valid isikukood"))
        }
    }
}
