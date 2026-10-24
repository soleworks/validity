package io.github.soleworks.validity

import io.github.soleworks.validity.samples.Customer
import io.github.soleworks.validity.samples.readme.Payment
import io.github.soleworks.validity.samples.readme.Registration
import io.github.soleworks.validity.samples.readme.User
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ValidatableTest {
    @Nested
    @DisplayName("When validate is called")
    inner class Validate {
        @Test
        fun `given a valid user should be valid`() {
            val user = User(name = "Ana", email = "ana@mail.com", age = null)

            val result = user.validate()

            result.isValid shouldBe true
        }

        @Test
        fun `given a SEPA payment with a bad amount and currency should report every violation at once`() {
            val payment = Payment(method = "SEPA", amount = "-9.999".toBigDecimal(), currency = "EURO")

            val violations = payment.validate().violations

            violations shouldBe listOf(
                Violation(path = "amount", message = "must be positive", code = "positive"),
                Violation(path = "amount", message = "must have at most 2 decimal places", code = "maxDecimalPlaces"),
                Violation(path = "currency", message = "must be a valid ISO 4217 currency code", code = "currencyCode"),
                Violation(path = "iban", message = "is required", code = "required")
            )
        }

        @Test
        fun `given a user with a short name, an invalid email and an age under 18 should report the three of them`() {
            val user = User(name = "A", email = "ana@", age = 16)

            val violations = user.validate().violations

            violations shouldBe listOf(
                Violation(path = "name", message = "must have at least 2 characters", code = "minLength"),
                Violation(path = "email", message = "must be a valid email", code = "email"),
                Violation(path = "age", message = "must be at least 18", code = "min")
            )
        }

        @Test
        fun `given a registration with an invalid email should report only the format`() {
            val registration = Registration(email = "ana@")

            val violations = registration.validate().violations

            violations shouldBe listOf(
                Violation(path = "email", message = "must be a valid email", code = "email")
            )
        }

        @Test
        fun `given a registration with a taken email should report the lookup`() {
            val registration = Registration(email = "ana@mail.com")

            val violations = registration.validate().violations

            violations shouldBe listOf(
                Violation(path = "email", message = "is already registered", code = "registered")
            )
        }
    }

    @Nested
    @DisplayName("When validateOrThrow is called")
    inner class ValidateOrThrow {
        @Test
        fun `given a valid object should not throw`() {
            val customer = Customer()

            shouldNotThrowAny { customer.validateOrThrow() }
        }

        @Test
        fun `given an invalid object should throw its violations`() {
            val customer = Customer(name = "A")

            val error = shouldThrow<ValidationException> { customer.validateOrThrow() }

            error.violations shouldBe listOf(
                Violation(path = "name", message = "must have at least 2 characters", code = "minLength")
            )
        }

        @Test
        fun `given an invalid user should throw an error that carries every violation`() {
            val user = User(name = "A", email = "ana@", age = 16)

            val error = shouldThrow<ValidationException> { user.validateOrThrow() }

            error.violations shouldBe listOf(
                Violation(path = "name", message = "must have at least 2 characters", code = "minLength"),
                Violation(path = "email", message = "must be a valid email", code = "email"),
                Violation(path = "age", message = "must be at least 18", code = "min")
            )
        }
    }
}
