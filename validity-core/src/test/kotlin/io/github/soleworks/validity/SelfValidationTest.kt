package io.github.soleworks.validity

import io.github.soleworks.validity.constraints.minLength
import io.github.soleworks.validity.samples.Batch
import io.github.soleworks.validity.samples.Contact
import io.github.soleworks.validity.samples.Directory
import io.github.soleworks.validity.samples.Invoice
import io.github.soleworks.validity.samples.Operation
import io.github.soleworks.validity.samples.Payout
import io.github.soleworks.validity.samples.Threads
import io.github.soleworks.validity.samples.readme.Destination
import io.github.soleworks.validity.samples.validation
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class SelfValidationTest {
    @Nested
    @DisplayName("When validate is called")
    inner class Validate {
        @Test
        fun `given a valid contact should be valid`() {
            val rules = Contact().validation()

            val result = runBlocking { rules.validate() }

            result.isValid shouldBe true
        }

        @Test
        fun `given a block that resumes on another thread should report the rules after it with the messages`() {
            val validity = Validity { messages { minLength = "deve ter pelo menos {min} caracteres" } }
            val contact = Contact(phone = "123")
            val rules = validation {
                withContext(Threads.other) { }
                add(ValidationNode(path = "phone", value = contact.phone.orEmpty()).apply { minLength(8) })
            }

            val result = runBlocking(Threads.caller) { rules.validate(validity) }

            result.violations shouldBe listOf(
                Violation(path = "phone", message = "deve ter pelo menos 8 caracteres", code = "minLength")
            )
        }
    }

    @Nested
    @DisplayName("When validateBlocking is called")
    inner class ValidateBlocking {
        @Test
        fun `given a valid contact should be valid`() {
            val contact = Contact()
            val rules = contact.validation()

            val result = rules.validateBlocking()

            result.isValid shouldBe true
        }

        @Test
        fun `given a contact with a short phone should report it at the root`() {
            val contact = Contact(phone = "123")
            val rules = contact.validation()

            val result = rules.validateBlocking()

            result.violations shouldBe listOf(
                Violation(path = "phone", message = "must have at least 8 characters", code = "minLength")
            )
        }

        @Test
        fun `given a Validity with messages should report the rules with them`() {
            val validity = Validity { messages { minLength = "deve ter pelo menos {min} caracteres" } }
            val rules = Contact(phone = "123").validation()

            val result = rules.validateBlocking(validity)

            result.violations shouldBe listOf(
                Violation(path = "phone", message = "deve ter pelo menos 8 caracteres", code = "minLength")
            )
        }

        @Test
        fun `given a block that calls a suspend function should report the rule that depends on it`() {
            val accounts = Directory("ana@mail.com")
            val rules = validation {
                val isTaken = accounts.exists("ana@mail.com")
                constraint(path = "email", message = "is already taken") { !isTaken }
            }

            val result = rules.validateBlocking()

            result.violations shouldBe listOf(
                Violation(path = "email", message = "is already taken", code = "")
            )
        }
    }

    @Nested
    @DisplayName("When constraint is called")
    inner class Constraint {
        @Test
        fun `given a rule that holds should accept it`() {
            val invoice = Invoice()

            val result = invoice.validateBlocking()

            result.violations shouldBe emptyList()
        }

        @Test
        fun `given a missing pix key should report the broken rule at the property`() {
            val payout = Payout(pixKey = null)

            val result = payout.validateBlocking()

            result.violations shouldBe listOf(
                Violation(path = "pixKey", message = "fill the pix key", code = "pixKey")
            )
        }

        @Test
        fun `given a broken rule without a path should report it at the root`() {
            val invoice = Invoice(total = 5)

            val result = invoice.validateBlocking()

            result.violations shouldBe listOf(
                Violation(path = "", message = "items must add up to the total", code = "total")
            )
        }

        @Test
        fun `given a broken rule with a path should report it at that path`() {
            val rules = validation { constraint(path = "bankAccount", message = "fill the bank account") { false } }

            val result = rules.validateBlocking()

            result.violations shouldBe listOf(
                Violation(path = "bankAccount", message = "fill the bank account", code = "")
            )
        }

        @Test
        fun `given a broken rule inside a metadata block should report it with the entries`() {
            val rules = validation {
                metadata("operationId" to "op-1") { constraint(message = "send at least one field") { false } }
            }

            val result = rules.validateBlocking()

            result.violations shouldBe listOf(
                Violation(
                    path = "",
                    message = "send at least one field",
                    code = "",
                    metadata = mapOf("operationId" to "op-1")
                )
            )
        }

        @Test
        fun `given a broken rule in the rules of a type should report it at the property of the value`() {
            val rules = validation<Destination> {
                constraint(::zipCode, message = "must not start with 0", code = "zipCode") { zipCode?.first() != '0' }
            }

            val result = rules.validateBlocking(Destination(street = "Rua das Flores", zipCode = "01310"))

            result.violations shouldBe listOf(
                Violation(path = "zipCode", message = "must not start with 0", code = "zipCode")
            )
        }

        @Test
        fun `given a message with placeholders should fill the path and the value`() {
            val payout = Payout(amount = 0)

            val result = payout.validateBlocking()

            result.violations shouldBe listOf(
                Violation(path = "amount", message = "amount cannot be 0", code = "")
            )
        }
    }

    @Nested
    @DisplayName("When metadata is called")
    inner class Metadata {
        @Test
        fun `given premises inside the block should attach the entries to their violations`() {
            val operation = Operation(email = null, phone = null)

            val result = operation.validateBlocking()

            result.violations shouldBe listOf(
                Violation(
                    path = "",
                    message = "at least one of email, phone is required",
                    code = "atLeastOneOf",
                    metadata = mapOf("operationId" to "op-1", "fields" to listOf("email", "phone"))
                )
            )
        }

        @Test
        fun `given a missing value inside the block should attach the entries to the required violation`() {
            val rules = validation {
                metadata("operationId" to "op-1") { required<String>(path = "email", value = null) {} }
            }

            val result = rules.validateBlocking()

            result.violations shouldBe listOf(
                Violation(
                    path = "email",
                    message = "is required",
                    code = "required",
                    metadata = mapOf("operationId" to "op-1")
                )
            )
        }

        @Test
        fun `given nested objects inside the block should merge the entries of each level`() {
            val batch = Batch(operations = listOf(Operation(id = "op-7", amount = 0)))

            val result = batch.validateBlocking()

            result.violations shouldBe listOf(
                Violation(
                    path = "operations[0].amount",
                    message = "must be positive",
                    code = "positive",
                    metadata = mapOf("clientId" to "c-9", "operationId" to "op-7")
                )
            )
        }

        @Test
        fun `given a collection of a nested object inside the block should attach the entries to its elements`() {
            val batch = Batch(operations = listOf(Operation(tags = listOf("x"))))

            val result = batch.validateBlocking()

            result.violations shouldBe listOf(
                Violation(
                    path = "operations[0].tags[0]",
                    message = "must have at least 3 characters",
                    code = "minLength",
                    metadata = mapOf("clientId" to "c-9", "operationId" to "op-1")
                )
            )
        }

        @Test
        fun `given a rule after the block should report it without the entries`() {
            val rules = validation {
                metadata("operationId" to "op-1") { required<String>(path = "email", value = null) {} }
                required<String>(path = "phone", value = null) {}
            }

            val result = rules.validateBlocking()

            result.violations shouldBe listOf(
                Violation(
                    path = "email",
                    message = "is required",
                    code = "required",
                    metadata = mapOf("operationId" to "op-1")
                ),
                Violation(path = "phone", message = "is required", code = "required")
            )
        }

        @Test
        fun `given a missing street in the rules of a type should report it with the entries`() {
            val rules = validation<Destination> {
                metadata("severity" to "warning") { ::street required {} }
            }

            val result = rules.validateBlocking(Destination(street = null, zipCode = "01310"))

            result.violations shouldBe listOf(
                Violation(
                    path = "street",
                    message = "is required",
                    code = "required",
                    metadata = mapOf("severity" to "warning")
                )
            )
        }
    }
}
