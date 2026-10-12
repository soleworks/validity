package io.github.soleworks.validity

import io.github.soleworks.validity.samples.Contact
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
}
