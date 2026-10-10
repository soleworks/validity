package io.github.soleworks.validity.constraints.romania

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class RomaniaConstraintsTest {
    @Nested
    @DisplayName("When cnp is called")
    inner class Cnp {
        @ParameterizedTest
        @ValueSource(strings = ["8001011234563", "9000123456789", "1001011234560", "3001011234564", "5001011234568"])
        fun `given a CNP with a valid date and check digit should accept it`(value: String) {
            val node = ValidationNode("cnp", value).apply { cnp() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(
            strings = [
                "5001011234569",
                "500 1011-234/568",
                "500101123456",
                "50010112345688",
                "5001011504568",
                "8000230234563",
                "6000230234563"
            ]
        )
        fun `given a CNP with an impossible date, a wrong check digit or another format should report it`(
            value: String
        ) {
            val node = ValidationNode("cnp", value).apply { cnp() }

            node.validate() shouldBe listOf(Violation("cnp", "must be a valid CNP", "cnp"))
        }
    }
}
