package io.github.soleworks.validity.constraints.china

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.github.soleworks.validity.constraints.validate
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ChinaConstraintsTest {
    @Nested
    @DisplayName("When uscc is called")
    inner class Uscc {
        @ParameterizedTest
        @ValueSource(strings = ["91927469EKP84HN3YJ", "91164938PAWHWWR5BG", "91110000600037341L"])
        fun `given a valid USCC should accept it`(
            value: String
        ) {
            val node = ValidationNode(path = "uscc", value = value).apply { uscc() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["91927469EKP84HN3YA", "91927469EKP84HN3YI", "91110000600037341l"])
        fun `given an invalid USCC should report it`(
            value: String
        ) {
            val node = ValidationNode(path = "uscc", value = value).apply { uscc() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "uscc", message = "must be a valid unified social credit code", code = "uscc")
            )
        }
    }
}
