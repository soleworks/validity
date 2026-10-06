package io.github.soleworks.validity.constraints.argentina

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ArgentinaConstraintsTest {
    @Nested
    @DisplayName("When cuit is called")
    inner class Cuit {
        @ParameterizedTest
        @ValueSource(strings = ["20123456786", "30-12345678-1", "33123456780", "34-12345678-7"])
        fun `given a valid CUIT should accept it`(value: String) {
            val node = ValidationNode("cuit", value).apply { cuit() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["20-12345678-7", "21-12345678-2", "3012345678", "30.12345678.1"])
        fun `given an invalid CUIT should report it`(value: String) {
            val node = ValidationNode("cuit", value).apply { cuit() }

            node.validate() shouldBe listOf(Violation("cuit", "must be a valid CUIT"))
        }
    }

    @Nested
    @DisplayName("When cuil is called")
    inner class Cuil {
        @ParameterizedTest
        @ValueSource(strings = ["20123456786", "25-12345678-8", "27123456780"])
        fun `given a valid CUIL should accept it`(value: String) {
            val node = ValidationNode("cuil", value).apply { cuil() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["20-12345678-7", "30-12345678-1", "2012345678", "20.12345678.6", "20-00000001-0"])
        fun `given an invalid CUIL should report it`(value: String) {
            val node = ValidationNode("cuil", value).apply { cuil() }

            node.validate() shouldBe listOf(Violation("cuil", "must be a valid CUIL"))
        }
    }
}
