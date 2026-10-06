package io.github.soleworks.validity.constraints.cyprus

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class CyprusConstraintsTest {
    @Nested
    @DisplayName("When afm is called")
    inner class Afm {
        @ParameterizedTest
        @ValueSource(strings = ["00123123T", "99652156X"])
        fun `given an AFM with a valid check letter should accept it`(value: String) {
            val node = ValidationNode("afm", value).apply { afm() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["99652156A", "00124123T", "00123123", "001123123T", "00 12-3123/T"])
        fun `given an AFM with a wrong check letter or another format should report it`(value: String) {
            val node = ValidationNode("afm", value).apply { afm() }

            node.validate() shouldBe listOf(Violation("afm", "must be a valid AFM"))
        }
    }
}
