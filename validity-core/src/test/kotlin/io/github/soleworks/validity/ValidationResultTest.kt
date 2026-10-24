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
        fun `given no violations should not throw`() {
            val customer = Customer()
            val result = customer.validate()

            shouldNotThrowAny { result.orThrow() }
        }

        @Test
        fun `given violations should describe each one in the message`() {
            val customer = Customer(name = "A", email = null)
            val result = customer.validate()

            val error = shouldThrow<ValidationException> { result.orThrow() }

            error.message shouldBe "name: must have at least 2 characters, email: email is mandatory"
        }

        @Test
        fun `given violations should throw them in the exception`() {
            val customer = Customer(name = "A", email = null)
            val result = customer.validate()

            val error = shouldThrow<ValidationException> { result.orThrow() }

            error.violations shouldBe listOf(
                Violation(path = "name", message = "must have at least 2 characters", code = "minLength"),
                Violation(path = "email", message = "email is mandatory", code = "required")
            )
        }
    }
}
