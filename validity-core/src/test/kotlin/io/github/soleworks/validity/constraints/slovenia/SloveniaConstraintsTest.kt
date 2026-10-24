package io.github.soleworks.validity.constraints.slovenia

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class SloveniaConstraintsTest {
    @Nested
    @DisplayName("When davcnaStevilka is called")
    inner class DavcnaStevilka {
        @ParameterizedTest
        @ValueSource(strings = ["15012557", "15012590"])
        fun `given a davčna številka with a valid check digit should accept it`(
            value: String
        ) {
            val node = ValidationNode(path = "davcnaStevilka", value = value).apply { davcnaStevilka() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["150125577", "1501255", "15 01-255/7"])
        fun `given a davčna številka with a wrong check digit or another format should report it`(
            value: String
        ) {
            val node = ValidationNode(path = "davcnaStevilka", value = value).apply { davcnaStevilka() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "davcnaStevilka", message = "must be a valid davčna številka", code = "davcnaStevilka")
            )
        }
    }
}
