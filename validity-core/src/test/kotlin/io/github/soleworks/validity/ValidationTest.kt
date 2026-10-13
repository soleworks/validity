package io.github.soleworks.validity

import io.github.soleworks.validity.samples.Batch
import io.github.soleworks.validity.samples.Contact
import io.github.soleworks.validity.samples.Operation
import io.github.soleworks.validity.samples.validation
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ValidationTest {
    @Nested
    @DisplayName("When validate is called")
    inner class Validate {
        @Test
        fun `given a contact with a short phone should report it at the root`() {
            val contact = Contact(phone = "123")

            contact.validation().validate().violations shouldBe listOf(
                Violation("phone", "must have at least 8 characters", "minLength")
            )
        }

        @Test
        fun `given a valid contact should be valid`() {
            val contact = Contact()

            contact.validation().validate().isValid shouldBe true
        }
    }

    @Nested
    @DisplayName("When metadata is called")
    inner class Metadata {
        @Test
        fun `given premises inside the block should attach the entries to their violations`() {
            val operation = Operation(email = null, phone = null)

            operation.validate().violations shouldBe listOf(
                Violation("email", "at least one of email, phone is required", "atLeastOneOf", mapOf("operationId" to "op-1")),
                Violation("phone", "at least one of email, phone is required", "atLeastOneOf", mapOf("operationId" to "op-1"))
            )
        }

        @Test
        fun `given a missing value inside the block should attach the entries to the required violation`() {
            val validation = validation {
                metadata("operationId" to "op-1") { required<String>("email", null) {} }
            }

            validation.validate().violations shouldBe listOf(
                Violation("email", "is required", "required", mapOf("operationId" to "op-1"))
            )
        }

        @Test
        fun `given nested objects inside the block should merge the entries of each level`() {
            val batch = Batch(operations = listOf(Operation(id = "op-7", amount = 0)))

            batch.validate().violations shouldBe listOf(
                Violation("operations[0].amount", "must be positive", "positive", mapOf("clientId" to "c-9", "operationId" to "op-7"))
            )
        }

        @Test
        fun `given a collection of a nested object inside the block should attach the entries to its elements`() {
            val batch = Batch(operations = listOf(Operation(tags = listOf("x"))))

            batch.validate().violations shouldBe listOf(
                Violation(
                    "operations[0].tags[0]",
                    "must have at least 3 characters",
                    "minLength",
                    mapOf("clientId" to "c-9", "operationId" to "op-1")
                )
            )
        }

        @Test
        fun `given a rule after the block should report it without the entries`() {
            val validation = validation {
                metadata("operationId" to "op-1") { required<String>("email", null) {} }
                required<String>("phone", null) {}
            }

            validation.validate().violations shouldBe listOf(
                Violation("email", "is required", "required", mapOf("operationId" to "op-1")),
                Violation("phone", "is required", "required")
            )
        }
    }
}
