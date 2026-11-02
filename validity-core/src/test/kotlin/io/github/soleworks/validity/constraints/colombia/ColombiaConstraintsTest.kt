package io.github.soleworks.validity.constraints.colombia

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.github.soleworks.validity.constraints.validate
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ColombiaConstraintsTest {
    @Nested
    @DisplayName("When nit is called")
    inner class Nit {
        @ParameterizedTest
        @ValueSource(strings = ["9002158108", "900.215.810-8", "9800639436"])
        fun `given a valid NIT should accept it`(
            value: String
        ) {
            val node = ValidationNode(path = "nit", value = value).apply { nit() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["9002158100", "900.215.8108", "900215810"])
        fun `given an invalid NIT should report it`(
            value: String
        ) {
            val node = ValidationNode(path = "nit", value = value).apply { nit() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "nit", message = "must be a valid NIT", code = "nit")
            )
        }
    }

    @Nested
    @DisplayName("When cc is called")
    inner class Cc {
        @ParameterizedTest
        @ValueSource(strings = ["1020304050", "123456"])
        fun `given a valid CC should accept it`(
            value: String
        ) {
            val node = ValidationNode(path = "cc", value = value).apply { cc() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["12345", "12345678901", "1.020.304.050"])
        fun `given an invalid CC should report it`(
            value: String
        ) {
            val node = ValidationNode(path = "cc", value = value).apply { cc() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "cc", message = "must be a valid CC", code = "cc")
            )
        }
    }
}
