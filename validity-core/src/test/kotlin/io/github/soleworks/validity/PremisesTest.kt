package io.github.soleworks.validity

import io.github.soleworks.validity.samples.Customer
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class PremisesTest {
    @Nested
    @DisplayName("When required is called")
    inner class Required {
        @Test
        fun `given a customer without a name should report only that it is required`() {
            val customer = Customer(name = null)

            customer.validate().violations shouldBe listOf(Violation("name", "is required", "required"))
        }

        @Test
        fun `given a customer with a short name should run the rules of the name`() {
            val customer = Customer(name = "A")

            customer.validate().violations shouldBe listOf(Violation("name", "must have at least 2 characters", "minLength"))
        }

        @Test
        fun `given a custom message should report it when the email is missing`() {
            val customer = Customer(email = null)

            customer.validate().violations shouldBe listOf(Violation("email", "email is mandatory", "required"))
        }
    }

    @Nested
    @DisplayName("When ifPresent is called")
    inner class IfPresent {
        @Test
        fun `given a customer without a nickname should not validate it`() {
            val customer = Customer(nickname = null)

            customer.validate().violations shouldBe emptyList()
        }

        @Test
        fun `given a short nickname should run its rules`() {
            val customer = Customer(nickname = "Al")

            customer.validate().violations shouldBe listOf(Violation("nickname", "must have at least 3 characters", "minLength"))
        }
    }
}
