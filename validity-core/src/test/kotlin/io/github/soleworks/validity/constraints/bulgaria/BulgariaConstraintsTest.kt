package io.github.soleworks.validity.constraints.bulgaria

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.github.soleworks.validity.constraints.validate
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class BulgariaConstraintsTest {
    @Nested
    @DisplayName("When egn is called")
    inner class Egn {
        @ParameterizedTest
        @ValueSource(strings = ["7501010010", "0101010012", "0111010010", "7521010014", "7541010019"])
        fun `given an EGN with a valid date and check digit should accept it`(
            value: String
        ) {
            val node = ValidationNode(path = "egn", value = value).apply { egn() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["750101001", "75010100101", "75-01010/01 0", "7521320010", "7501010019"])
        fun `given an EGN with an impossible date, a wrong check digit or another format should report it`(
            value: String
        ) {
            val node = ValidationNode(path = "egn", value = value).apply { egn() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "egn", message = "must be a valid EGN", code = "egn")
            )
        }
    }
}
