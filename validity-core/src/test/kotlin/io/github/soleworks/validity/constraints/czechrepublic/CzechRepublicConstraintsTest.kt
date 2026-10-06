package io.github.soleworks.validity.constraints.czechrepublic

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class CzechRepublicConstraintsTest {
    @Nested
    @DisplayName("When rodneCislo is called")
    inner class RodneCislo {
        @ParameterizedTest
        @ValueSource(
            strings = [
                "530121999",
                "530121/999",
                "530121/9990",
                "5301219990",
                "1602295134",
                "5451219994",
                "0424175466",
                "0532175468",
                "7159079940"
            ]
        )
        fun `given a rodné číslo with a valid date and remainder should accept it`(value: String) {
            val node = ValidationNode("rodneCislo", value).apply { rodneCislo() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(
            strings = [
                "53-0121 999",
                "530121000",
                "960121999",
                "0124175466",
                "0472301754",
                "1975116400",
                "7159079945"
            ]
        )
        fun `given a rodné číslo with an impossible date, a wrong remainder or another format should report it`(
            value: String
        ) {
            val node = ValidationNode("rodneCislo", value).apply { rodneCislo() }

            node.validate() shouldBe listOf(Violation("rodneCislo", "must be a valid rodné číslo"))
        }
    }
}
