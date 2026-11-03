package io.github.soleworks.validity.constraints.germany

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.github.soleworks.validity.constraints.validate
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class GermanyConstraintsTest {
    @Nested
    @DisplayName("When ustIdNr is called")
    inner class UstIdNr {
        @ParameterizedTest
        @ValueSource(strings = ["894753402", "DE532312718", "DE136695976"])
        fun `given a valid USt-IdNr should accept it`(
            value: String
        ) {
            val node = ValidationNode(path = "ustIdNr", value = value).apply { ustIdNr() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["894753400", "094753402", "DE89475340"])
        fun `given an invalid USt-IdNr should report it`(
            value: String
        ) {
            val node = ValidationNode(path = "ustIdNr", value = value).apply { ustIdNr() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "ustIdNr", message = "must be a valid USt-IdNr.", code = "ustIdNr")
            )
        }
    }

    @Nested
    @DisplayName("When steuerId is called")
    inner class SteuerId {
        @ParameterizedTest
        @ValueSource(strings = ["84159263274", "84612011539", "36574261809"])
        fun `given a valid Steuer-ID should accept it`(
            value: String
        ) {
            val node = ValidationNode(path = "steuerId", value = value).apply { steuerId() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["84159263275", "12345678903", "04159263274", "11112345670", "11223456780"])
        fun `given an invalid Steuer-ID should report it`(
            value: String
        ) {
            val node = ValidationNode(path = "steuerId", value = value).apply { steuerId() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "steuerId", message = "must be a valid Steuer-ID", code = "steuerId")
            )
        }
    }
}
