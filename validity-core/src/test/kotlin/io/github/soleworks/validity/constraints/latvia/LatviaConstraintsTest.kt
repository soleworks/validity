package io.github.soleworks.validity.constraints.latvia

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class LatviaConstraintsTest {
    @Nested
    @DisplayName("When personasKods is called")
    inner class PersonasKods {
        @ParameterizedTest
        @ValueSource(
            strings = [
                "01011012344",
                "32579461005",
                "01019902341",
                "325794-61005",
                "01011000010",
                "01011010040",
                "01011020070"
            ]
        )
        fun `given a personas kods with a valid date and check digit should accept it`(value: String) {
            val node = ValidationNode("personasKods", value).apply { personasKods() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(
            strings = [
                "010110123444",
                "0101101234",
                "01001612345",
                "290217-22343",
                "01011000011",
                "01011010041"
            ]
        )
        fun `given a personas kods with an impossible date, a wrong check digit or another format should report it`(
            value: String
        ) {
            val node = ValidationNode("personasKods", value).apply { personasKods() }

            node.validate() shouldBe listOf(Violation("personasKods", "must be a valid personas kods", "personasKods"))
        }
    }
}
