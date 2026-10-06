package io.github.soleworks.validity.constraints.canada

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class CanadaConstraintsTest {
    @Nested
    @DisplayName("When sin is called")
    inner class Sin {
        @ParameterizedTest
        @ValueSource(strings = ["000000000", "521719666", "469317481"])
        fun `given a valid SIN should accept it`(value: String) {
            val node = ValidationNode("sin", value).apply { sin() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["012345678", "111111111", "999999999", "744-797-853", "30692544"])
        fun `given an invalid SIN should report it`(value: String) {
            val node = ValidationNode("sin", value).apply { sin() }

            node.validate() shouldBe listOf(Violation("sin", "must be a valid SIN"))
        }
    }
}
