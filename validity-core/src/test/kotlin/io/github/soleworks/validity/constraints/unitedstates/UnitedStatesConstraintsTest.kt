package io.github.soleworks.validity.constraints.unitedstates

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.github.soleworks.validity.constraints.validate
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class UnitedStatesConstraintsTest {
    @Nested
    @DisplayName("When ein is called")
    inner class Ein {
        @ParameterizedTest
        @ValueSource(strings = ["01-1234567", "01 1234567", "011234567", "10-1234567"])
        fun `given a valid EIN should accept it`(
            value: String
        ) {
            val node = ValidationNode(path = "ein", value = value).apply { ein() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["0-11234567", "01#1234567", "01  1234567", "07-1234567", "28-1234567", "96-1234567"])
        fun `given an invalid EIN should report it`(
            value: String
        ) {
            val node = ValidationNode(path = "ein", value = value).apply { ein() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "ein", message = "must be a valid EIN", code = "ein")
            )
        }
    }
}
