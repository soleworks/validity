package io.github.soleworks.validity.constraints.chile

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.github.soleworks.validity.constraints.validate
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ChileConstraintsTest {
    @Nested
    @DisplayName("When rut is called")
    inner class Rut {
        @ParameterizedTest
        @ValueSource(strings = ["84268465-K", "84268465K", "63212233-0", "31.372.159-0"])
        fun `given a valid RUT should accept it`(
            value: String
        ) {
            val node = ValidationNode(path = "rut", value = value).apply { rut() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["63212233-1", "84268465-k", "8426846-5K", "31.372.1590"])
        fun `given an invalid RUT should report it`(
            value: String
        ) {
            val node = ValidationNode(path = "rut", value = value).apply { rut() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "rut", message = "must be a valid RUT", code = "rut")
            )
        }
    }
}
