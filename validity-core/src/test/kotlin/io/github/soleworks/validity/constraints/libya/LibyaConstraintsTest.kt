package io.github.soleworks.validity.constraints.libya

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class LibyaConstraintsTest {
    @Nested
    @DisplayName("When nin is called")
    inner class Nin {
        @ParameterizedTest
        @ValueSource(strings = ["119803455876", "120024679875", "219624876201", "220103480657"])
        fun `given a valid NIN should accept it`(value: String) {
            val node = ValidationNode("nin", value).apply { nin() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["987654320123", "123-456-7890", "012345678912", "1234567890", "9876543210123"])
        fun `given an invalid NIN should report it`(value: String) {
            val node = ValidationNode("nin", value).apply { nin() }

            node.validate() shouldBe listOf(Violation("nin", "must be a valid NIN"))
        }
    }
}
