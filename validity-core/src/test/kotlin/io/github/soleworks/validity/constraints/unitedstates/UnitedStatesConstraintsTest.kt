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

    @Nested
    @DisplayName("When ssn is called")
    inner class Ssn {
        @ParameterizedTest
        @ValueSource(strings = ["123-45-6789", "123456789", "001-01-0001"])
        fun `given a valid SSN should accept it`(
            value: String
        ) {
            val node = ValidationNode(path = "ssn", value = value).apply { ssn() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(
            strings = [
                "000-12-3456",
                "666-12-3456",
                "900-70-1234",
                "123-00-4567",
                "123-45-0000",
                "078-05-1120",
                "12-345-6789"
            ]
        )
        fun `given an invalid SSN should report it`(
            value: String
        ) {
            val node = ValidationNode(path = "ssn", value = value).apply { ssn() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "ssn", message = "must be a valid SSN", code = "ssn")
            )
        }
    }

    @Nested
    @DisplayName("When itin is called")
    inner class Itin {
        @ParameterizedTest
        @ValueSource(strings = ["912-70-1234", "900881234", "999-99-9999"])
        fun `given a valid ITIN should accept it`(
            value: String
        ) {
            val node = ValidationNode(path = "itin", value = value).apply { itin() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["912-89-1234", "812-70-1234", "912-93-1234", "9127-01234"])
        fun `given an invalid ITIN should report it`(
            value: String
        ) {
            val node = ValidationNode(path = "itin", value = value).apply { itin() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "itin", message = "must be a valid ITIN", code = "itin")
            )
        }
    }
}
