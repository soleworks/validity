package io.github.soleworks.validity

import io.github.soleworks.validity.samples.Address
import io.github.soleworks.validity.samples.Contact
import io.github.soleworks.validity.samples.Severity
import io.github.soleworks.validity.samples.Signup
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
        fun `given premises inside the block should attach the metadata to their violations`() {
            val signup = Signup(email = null, phone = null)

            signup.validate().violations shouldBe listOf(
                Violation("email", "at least one of email, phone is required", "atLeastOneOf", Severity.ERROR),
                Violation("phone", "at least one of email, phone is required", "atLeastOneOf", Severity.ERROR)
            )
        }

        @Test
        fun `given a missing value inside the block should attach the metadata to the required violation`() {
            val validation = validation {
                metadata(Severity.ERROR) { required<String>("email", null) {} }
            }

            validation.validate().violations shouldBe listOf(Violation("email", "is required", "required", Severity.ERROR))
        }

        @Test
        fun `given a nested object inside the block should attach the metadata to its violations`() {
            val signup = Signup(address = Address(street = null))

            signup.validate().violations shouldBe listOf(
                Violation("address.street", "is required", "required", Severity.WARNING)
            )
        }

        @Test
        fun `given a collection of a nested object inside the block should attach the metadata to its elements`() {
            val signup = Signup(address = Address(phones = listOf("123")))

            signup.validate().violations shouldBe listOf(
                Violation("address.phones[0]", "must have at least 8 characters", "minLength", Severity.WARNING)
            )
        }

        @Test
        fun `given a rule outside every block should report it without metadata`() {
            val signup = Signup(password = null)

            signup.validate().violations shouldBe listOf(Violation("password", "is required", "required"))
        }
    }
}
