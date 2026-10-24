package io.github.soleworks.validity

import io.github.soleworks.validity.samples.Company
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ValidationNodeTest {
    @Nested
    @DisplayName("When constraint is called")
    inner class Constraint {
        @Test
        fun `given a code should report it with the violation`() {
            val node = ValidationNode(path = "amount", value = -1).apply {
                constraint(message = "must be positive", code = "positive") { it > 0 }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "amount", message = "must be positive", code = "positive")
            )
        }

        @Test
        fun `given a message function and a code should report both`() {
            val node = ValidationNode(path = "amount", value = -1).apply {
                constraint(message = { "$it is not positive" }, code = "positive") { it > 0 }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "amount", message = "-1 is not positive", code = "positive")
            )
        }

        @Test
        fun `given no code should report an empty code`() {
            val node = ValidationNode(path = "amount", value = -1).apply {
                constraint("must be positive") { it > 0 }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "amount", message = "must be positive", code = "")
            )
        }

        @Test
        fun `given the path and value placeholders should replace them in the message`() {
            val node = ValidationNode(path = "amount", value = -1).apply {
                constraint(message = "{path} must be positive, got {value}", code = "positive") { it > 0 }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "amount", message = "amount must be positive, got -1", code = "positive")
            )
        }

        @Test
        fun `given the placeholders in a message function should replace them too`() {
            val node = ValidationNode(path = "amount", value = -1).apply {
                constraint(message = { "{path}: $it is not positive" }, code = "positive") { it > 0 }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "amount", message = "amount: -1 is not positive", code = "positive")
            )
        }

        @Test
        fun `given a path with a placeholder text should not fill it again`() {
            val node = ValidationNode(path = "prices[{value}]", value = -1).apply {
                constraint("{path} must be positive") { it > 0 }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "prices[{value}]", message = "prices[{value}] must be positive", code = "")
            )
        }

        @Test
        fun `given a path placeholder inside a logical operator of a nested object should fill the full path`() {
            val node = ValidationNode(path = "company", value = Company(document = "123")).apply { valid() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(
                    path = "company.document",
                    message = "company.document is not a CPF or company.document is not a CNPJ",
                    code = "or"
                )
            )
        }
    }
}
