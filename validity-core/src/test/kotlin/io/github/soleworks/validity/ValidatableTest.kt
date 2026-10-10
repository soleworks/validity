package io.github.soleworks.validity

import io.github.soleworks.validity.samples.Customer
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ValidatableTest {
    @Nested
    @DisplayName("When validateOrThrow is called")
    inner class ValidateOrThrow {
        @Test
        fun `given an invalid object should throw its violations`() {
            val customer = Customer(name = "A")

            val exception = shouldThrow<ValidationException> { customer.validateOrThrow() }

            exception.violations shouldBe listOf(Violation("name", "must have at least 2 characters", "minLength"))
        }

        @Test
        fun `given a valid object should not throw`() {
            val customer = Customer()

            shouldNotThrowAny { customer.validateOrThrow() }
        }
    }
}
