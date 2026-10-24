package io.github.soleworks.validity

import io.github.soleworks.validity.constraints.brazil.cnpj
import io.github.soleworks.validity.constraints.brazil.cpf
import io.github.soleworks.validity.constraints.min
import io.github.soleworks.validity.constraints.minLength
import io.github.soleworks.validity.constraints.or
import io.github.soleworks.validity.constraints.positive
import io.github.soleworks.validity.samples.Company
import io.github.soleworks.validity.samples.Operation
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

    @Nested
    @DisplayName("When metadata is called")
    inner class Metadata {
        @Test
        fun `given the block inside a logical operator should keep each rule as an alternative`() {
            val node = ValidationNode(path = "document", value = "52998224725").apply {
                or {
                    metadata("operationId" to "op-1") {
                        cpf()
                        cnpj()
                    }
                }
            }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given rules inside the block should attach the entries to their violations`() {
            val node = ValidationNode(path = "amount", value = 0).apply {
                positive()
                metadata("operationId" to "op-1") { min(10) }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "amount", message = "must be positive", code = "positive"),
                Violation(
                    path = "amount",
                    message = "must be at least 10",
                    code = "min",
                    metadata = mapOf("operationId" to "op-1")
                )
            )
        }

        @Test
        fun `given nested blocks should merge the entries and restore the outer ones after them`() {
            val node = ValidationNode(path = "amount", value = 0).apply {
                metadata("operationId" to "op-1", "step" to "outer") {
                    min(1)
                    metadata("step" to "inner") { min(2) }
                    min(3)
                }
                min(4)
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(
                    path = "amount",
                    message = "must be at least 1",
                    code = "min",
                    metadata = mapOf("operationId" to "op-1", "step" to "outer")
                ),
                Violation(
                    path = "amount",
                    message = "must be at least 2",
                    code = "min",
                    metadata = mapOf("operationId" to "op-1", "step" to "inner")
                ),
                Violation(
                    path = "amount",
                    message = "must be at least 3",
                    code = "min",
                    metadata = mapOf("operationId" to "op-1", "step" to "outer")
                ),
                Violation(path = "amount", message = "must be at least 4", code = "min")
            )
        }

        @Test
        fun `given each inside the block should attach the entries to every element`() {
            val node = ValidationNode(path = "tags", value = listOf("a", "b")).apply {
                metadata("operationId" to "op-1") { each { minLength(3) } }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(
                    path = "tags[0]",
                    message = "must have at least 3 characters",
                    code = "minLength",
                    metadata = mapOf("operationId" to "op-1")
                ),
                Violation(
                    path = "tags[1]",
                    message = "must have at least 3 characters",
                    code = "minLength",
                    metadata = mapOf("operationId" to "op-1")
                )
            )
        }

        @Test
        fun `given a nested object inside the block should merge the entries with its own`() {
            val node = ValidationNode(path = "operation", value = Operation(amount = 0, currency = "BR")).apply {
                metadata("clientId" to "c-9", "operationId" to "outer") { valid() }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(
                    path = "operation.amount",
                    message = "must be positive",
                    code = "positive",
                    metadata = mapOf("clientId" to "c-9", "operationId" to "op-1")
                ),
                Violation(
                    path = "operation.currency",
                    message = "must have exactly 3 characters",
                    code = "length",
                    metadata = mapOf("clientId" to "c-9", "operationId" to "op-1", "field" to "currency")
                )
            )
        }

        @Test
        fun `given a logical operator inside the block should attach the entries to its violation`() {
            val node = ValidationNode(path = "document", value = "123").apply {
                metadata("operationId" to "op-1") {
                    or {
                        cpf()
                        cnpj()
                    }
                }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(
                    path = "document",
                    message = "must be a valid CPF or must be a valid CNPJ",
                    code = "or",
                    metadata = mapOf("operationId" to "op-1")
                )
            )
        }
    }
}
