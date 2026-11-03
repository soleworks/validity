package io.github.soleworks.validity.constraints.mexico

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.github.soleworks.validity.constraints.validate
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class MexicoConstraintsTest {
    @Nested
    @DisplayName("When rfc is called")
    inner class Rfc {
        @ParameterizedTest
        @ValueSource(strings = ["GODE561231GR8", "GOD561231GR8", "ABC680524P76"])
        fun `given a valid RFC should accept it`(
            value: String
        ) {
            val node = ValidationNode(path = "rfc", value = value).apply { rfc() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["GODE561331GR8", "GODE5612", "gode561231gr8", "GODE560230GR8"])
        fun `given an invalid RFC should report it`(
            value: String
        ) {
            val node = ValidationNode(path = "rfc", value = value).apply { rfc() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "rfc", message = "must be a valid RFC", code = "rfc")
            )
        }
    }
}
