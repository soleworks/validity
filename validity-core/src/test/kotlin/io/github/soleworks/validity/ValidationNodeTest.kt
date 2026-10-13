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
        fun `given rules inside the block should attach the entries to their violations`() {
            val node = ValidationNode("amount", 0).apply {
                positive()
                metadata("operationId" to "op-1") { min(10) }
            }

            node.validate() shouldBe listOf(
                Violation("amount", "must be positive", "positive"),
                Violation("amount", "must be at least 10", "min", mapOf("operationId" to "op-1"))
            )
        }

        @Test
        fun `given nested blocks should merge the entries and restore the outer ones after them`() {
            val node = ValidationNode("amount", 0).apply {
                metadata("operationId" to "op-1", "step" to "outer") {
                    min(1)
                    metadata("step" to "inner") { min(2) }
                    min(3)
                }
                min(4)
            }

            node.validate() shouldBe listOf(
                Violation("amount", "must be at least 1", "min", mapOf("operationId" to "op-1", "step" to "outer")),
                Violation("amount", "must be at least 2", "min", mapOf("operationId" to "op-1", "step" to "inner")),
                Violation("amount", "must be at least 3", "min", mapOf("operationId" to "op-1", "step" to "outer")),
                Violation("amount", "must be at least 4", "min")
            )
        }

        @Test
        fun `given each inside the block should attach the entries to every element`() {
            val node = ValidationNode("tags", listOf("a", "b")).apply {
                metadata("operationId" to "op-1") { each { minLength(3) } }
            }

            node.validate() shouldBe listOf(
                Violation("tags[0]", "must have at least 3 characters", "minLength", mapOf("operationId" to "op-1")),
                Violation("tags[1]", "must have at least 3 characters", "minLength", mapOf("operationId" to "op-1"))
            )
        }

        @Test
        fun `given a nested object inside the block should merge the entries with its own`() {
            val node = ValidationNode("operation", Operation(amount = 0, currency = "BR")).apply {
                metadata("clientId" to "c-9", "operationId" to "outer") { valid() }
            }

            node.validate() shouldBe listOf(
                Violation("operation.amount", "must be positive", "positive", mapOf("clientId" to "c-9", "operationId" to "op-1")),
                Violation(
                    "operation.currency",
                    "must have exactly 3 characters",
                    "length",
                    mapOf("clientId" to "c-9", "operationId" to "op-1", "field" to "currency")
                )
            )
        }

        @Test
        fun `given a logical operator inside the block should attach the entries to its violation`() {
            val node = ValidationNode("document", "123").apply {
                metadata("operationId" to "op-1") { or { cpf(); cnpj() } }
            }

            node.validate() shouldBe listOf(
                Violation("document", "must be a valid CPF or must be a valid CNPJ", "or", mapOf("operationId" to "op-1"))
            )
        }

        @Test
        fun `given the block inside a logical operator should keep each rule as an alternative`() {
            val node = ValidationNode("document", "52998224725").apply {
                or { metadata("operationId" to "op-1") { cpf(); cnpj() } }
            }

            node.validate() shouldBe emptyList()
        }
    }
}
