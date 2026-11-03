package io.github.soleworks.validity.constraints.argentina

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.github.soleworks.validity.constraints.validate
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
        @ValueSource(strings = ["33301661318", "30091390996", "20-30824628-1", "20945807300", "20003322459"])
        fun `given a valid CUIT should accept it`(
            value: String
        ) {
            val node = ValidationNode(path = "cuit", value = value).apply { cuit() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["33301661310", "30091390990", "10308246281", "2030824628", "20.30824628.1"])
        fun `given an invalid CUIT should report it`(
            value: String
        ) {
            val node = ValidationNode(path = "cuit", value = value).apply { cuit() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "cuit", message = "must be a valid CUIT", code = "cuit")
            )
        }
    }

    @Nested
    @DisplayName("When cuil is called")
    inner class Cuil {
        @ParameterizedTest
        @ValueSource(strings = ["20308246281", "20-30824628-1"])
        fun `given a valid CUIL should accept it`(
            value: String
        ) {
            val node = ValidationNode(path = "cuil", value = value).apply { cuil() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["30091390996", "20308246280", "2030824628"])
        fun `given an invalid CUIL should report it`(
            value: String
        ) {
            val node = ValidationNode(path = "cuil", value = value).apply { cuil() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "cuil", message = "must be a valid CUIL", code = "cuil")
            )
        }
    }
}
