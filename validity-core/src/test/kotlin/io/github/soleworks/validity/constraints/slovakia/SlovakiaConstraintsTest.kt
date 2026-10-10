package io.github.soleworks.validity.constraints.slovakia

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class SlovakiaConstraintsTest {
    @Nested
    @DisplayName("When rodneCislo is called")
    inner class RodneCislo {
        @ParameterizedTest
        @ValueSource(strings = ["530121999", "536221/999", "031121999", "520229999", "1234567890"])
        fun `given a rodné číslo with a valid date or one that is not checked should accept it`(value: String) {
            val node = ValidationNode("rodneCislo", value).apply { rodneCislo() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["53012199999", "990101999", "530121000", "53012199", "53-0121 999", "535229999"])
        fun `given a rodné číslo with an impossible date, an unassigned serial or another format should report it`(
            value: String
        ) {
            val node = ValidationNode("rodneCislo", value).apply { rodneCislo() }

            node.validate() shouldBe listOf(Violation("rodneCislo", "must be a valid rodné číslo", "rodneCislo"))
        }
    }
}
