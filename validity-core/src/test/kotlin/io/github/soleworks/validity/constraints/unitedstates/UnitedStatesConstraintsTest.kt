package io.github.soleworks.validity.constraints.unitedstates

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
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
        fun `given a valid EIN should accept it`(value: String) {
            val node = ValidationNode("ein", value).apply { ein() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["0-11234567", "01#1234567", "01  1234567", "07-1234567", "28-1234567", "96-1234567"])
        fun `given an invalid EIN should report it`(value: String) {
            val node = ValidationNode("ein", value).apply { ein() }

            node.validate() shouldBe listOf(Violation("ein", "must be a valid EIN", "ein"))
        }
    }
}
