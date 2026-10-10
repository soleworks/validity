package io.github.soleworks.validity

import io.github.soleworks.validity.samples.Customer
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ValidationResultTest {
    @Nested
    @DisplayName("When orThrow is called")
    inner class OrThrow {
        @Test
        fun `given violations should throw them in the exception`() {
            val customer = Customer(name = "A", email = null)

            val exception = shouldThrow<ValidationException> { customer.validate().orThrow() }

            exception.violations shouldBe listOf(
                Violation("name", "must have at least 2 characters", "minLength"),
                Violation("email", "email is mandatory", "required")
            )
        }

        @Test
        fun `given violations should describe each one in the message`() {
            val customer = Customer(name = "A", email = null)

            val exception = shouldThrow<ValidationException> { customer.validate().orThrow() }

            exception.message shouldBe "name: must have at least 2 characters, email: email is mandatory"
        }

        @Test
        fun `given no violations should not throw`() {
            val customer = Customer()

            shouldNotThrowAny { customer.validate().orThrow() }
        }
    }
}
