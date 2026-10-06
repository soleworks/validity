package io.github.soleworks.validity.constraints.ukraine

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class UkraineConstraintsTest {
    @Nested
    @DisplayName("When rnokpp is called")
    inner class Rnokpp {
        @ParameterizedTest
        @ValueSource(strings = ["3006321856", "3003102490", "2164212906"])
        fun `given an RNOKPP with a valid check digit should accept it`(value: String) {
            val node = ValidationNode("rnokpp", value).apply { rnokpp() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["2565975632", "256597563287", "КС00123456", "2896235845"])
        fun `given an RNOKPP with a wrong check digit or another format should report it`(value: String) {
            val node = ValidationNode("rnokpp", value).apply { rnokpp() }

            node.validate() shouldBe listOf(Violation("rnokpp", "must be a valid RNOKPP"))
        }
    }
}
