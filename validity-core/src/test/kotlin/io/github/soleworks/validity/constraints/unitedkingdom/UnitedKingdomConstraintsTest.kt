package io.github.soleworks.validity.constraints.unitedkingdom

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class UnitedKingdomConstraintsTest {
    @Nested
    @DisplayName("When nino is called")
    inner class Nino {
        @ParameterizedTest
        @ValueSource(strings = ["AA123456A", "AA123456 "])
        fun `given a valid NINO should accept it`(value: String) {
            val node = ValidationNode("nino", value).apply { nino() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(
            strings = [
                "GB123456A",
                "NK123456A",
                "TN123456A",
                "ZZ123456A",
                "GB123456Z",
                "DM123456A",
                "AO123456A",
                "GB-123456A",
                "GB 123456 A",
                "GB123456 "
            ]
        )
        fun `given an invalid NINO should report it`(value: String) {
            val node = ValidationNode("nino", value).apply { nino() }

            node.validate() shouldBe listOf(Violation("nino", "must be a valid NINO", "nino"))
        }
    }
}
