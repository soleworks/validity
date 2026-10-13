package io.github.soleworks.validity

import io.github.soleworks.validity.constraints.brazil.cnpj
import io.github.soleworks.validity.constraints.brazil.cpf
import io.github.soleworks.validity.constraints.minLength
import io.github.soleworks.validity.constraints.or
import io.github.soleworks.validity.samples.Company
import io.github.soleworks.validity.samples.Severity
import io.github.soleworks.validity.samples.Signup
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
            val node = ValidationNode("amount", -1).apply {
                constraint("must be positive", "positive") { it > 0 }
            }

            node.validate() shouldBe listOf(Violation("amount", "must be positive", "positive"))
        }

        @Test
        fun `given a message function and a code should report both`() {
            val node = ValidationNode("amount", -1).apply {
                constraint({ "$it is not positive" }, "positive") { it > 0 }
            }

            node.validate() shouldBe listOf(Violation("amount", "-1 is not positive", "positive"))
        }

        @Test
        fun `given no code should report an empty code`() {
            val node = ValidationNode("amount", -1).apply {
                constraint("must be positive") { it > 0 }
            }

            node.validate() shouldBe listOf(Violation("amount", "must be positive", ""))
        }

        @Test
        fun `given the path and value placeholders should replace them in the message`() {
            val node = ValidationNode("amount", -1).apply {
                constraint("{path} must be positive, got {value}", "positive") { it > 0 }
            }

            node.validate() shouldBe listOf(Violation("amount", "amount must be positive, got -1", "positive"))
        }

        @Test
        fun `given the placeholders in a message function should replace them too`() {
            val node = ValidationNode("amount", -1).apply {
                constraint({ "{path}: $it is not positive" }, "positive") { it > 0 }
            }

            node.validate() shouldBe listOf(Violation("amount", "amount: -1 is not positive", "positive"))
        }

        @Test
        fun `given a path with a placeholder text should not fill it again`() {
            val node = ValidationNode("prices[{value}]", -1).apply {
                constraint("{path} must be positive") { it > 0 }
            }

            node.validate() shouldBe listOf(Violation("prices[{value}]", "prices[{value}] must be positive", ""))
        }

        @Test
        fun `given a path placeholder inside a logical operator of a nested object should fill the full path`() {
            val node = ValidationNode("company", Company(document = "123")).apply { valid() }

            node.validate() shouldBe listOf(
                Violation("company.document", "company.document is not a CPF or company.document is not a CNPJ", "or")
            )
        }
    }

    @Nested
    @DisplayName("When metadata is called")
    inner class Metadata {
        @Test
        fun `given rules inside the block should attach the metadata to their violations`() {
            val node = ValidationNode("password", "abc1234").apply {
                minLength(8)
                metadata(Severity.WARNING) { minLength(12) }
            }

            node.validate() shouldBe listOf(
                Violation("password", "must have at least 8 characters", "minLength"),
                Violation("password", "must have at least 12 characters", "minLength", Severity.WARNING)
            )
        }

        @Test
        fun `given nested blocks should attach the innermost metadata and restore the outer one after them`() {
            val node = ValidationNode("password", "abc").apply {
                metadata(Severity.ERROR) {
                    minLength(4)
                    metadata(Severity.WARNING) { minLength(8) }
                    minLength(12)
                }
                minLength(16)
            }

            node.validate() shouldBe listOf(
                Violation("password", "must have at least 4 characters", "minLength", Severity.ERROR),
                Violation("password", "must have at least 8 characters", "minLength", Severity.WARNING),
                Violation("password", "must have at least 12 characters", "minLength", Severity.ERROR),
                Violation("password", "must have at least 16 characters", "minLength")
            )
        }

        @Test
        fun `given each inside the block should attach the metadata to every element`() {
            val node = ValidationNode("phones", listOf("123", "456")).apply {
                metadata(Severity.WARNING) { each { minLength(8) } }
            }

            node.validate() shouldBe listOf(
                Violation("phones[0]", "must have at least 8 characters", "minLength", Severity.WARNING),
                Violation("phones[1]", "must have at least 8 characters", "minLength", Severity.WARNING)
            )
        }

        @Test
        fun `given a nested object inside the block should attach the metadata to the violations without their own`() {
            val node = ValidationNode("signup", Signup(email = "ana", password = "abc")).apply {
                metadata("outer") { valid() }
            }

            node.validate() shouldBe listOf(
                Violation("signup.email", "must be a valid email", "email", Severity.ERROR),
                Violation("signup.password", "must have at least 8 characters", "minLength", "outer"),
                Violation("signup.password", "must have at least 12 characters", "minLength", Severity.WARNING)
            )
        }

        @Test
        fun `given a logical operator inside the block should attach the metadata to its violation`() {
            val node = ValidationNode("document", "123").apply {
                metadata(Severity.ERROR) { or { cpf(); cnpj() } }
            }

            node.validate() shouldBe listOf(
                Violation("document", "must be a valid CPF or must be a valid CNPJ", "or", Severity.ERROR)
            )
        }

        @Test
        fun `given the block inside a logical operator should keep each rule as an alternative`() {
            val node = ValidationNode("document", "52998224725").apply {
                or { metadata(Severity.WARNING) { cpf(); cnpj() } }
            }

            node.validate() shouldBe emptyList()
        }
    }
}
