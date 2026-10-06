package io.github.soleworks.validity.constraints.china

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ChinaConstraintsTest {
    @Nested
    @DisplayName("When residentId is called")
    inner class ResidentId {
        @ParameterizedTest
        @ValueSource(strings = ["235407195106112745", "210203197503102721", "520323197806058856", "110101491001001"])
        fun `given a valid resident identity card number should accept it`(value: String) {
            val node = ValidationNode("residentId", value).apply { residentId() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(
            strings = [
                "160323197806058856",
                "010203197503102721",
                "520323297806058856",
                "520323197802318856",
                "235407195106112742",
                "110101940231001",
                "235407207006112742"
            ]
        )
        fun `given an invalid resident identity card number should report it`(value: String) {
            val node = ValidationNode("residentId", value).apply { residentId() }

            node.validate() shouldBe listOf(Violation("residentId", "must be a valid resident identity card number"))
        }
    }
}
