package io.github.soleworks.validity

import io.github.soleworks.validity.samples.Booking
import io.github.soleworks.validity.samples.Contact
import io.github.soleworks.validity.samples.Customer
import io.github.soleworks.validity.samples.Subscription
import io.github.soleworks.validity.samples.Threads
import io.github.soleworks.validity.samples.readme.Payment
import io.github.soleworks.validity.samples.readme.User
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import java.time.Clock
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset.UTC
import kotlinx.coroutines.runBlocking
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

            val result = runBlocking { user.validate() }

            result.isValid shouldBe true
        }

        @Test
        fun `given a Validity with a clock in 2000 should read it in a rule that resumes on another thread`() {
            val validity = Validity { clock = Clock.fixed(Instant.parse("2000-06-15T12:00:00Z"), UTC) }
            val booking = Booking(date = LocalDate.of(2010, 1, 1))

            val result = runBlocking(Threads.caller) { booking.validate(validity) }

            result.isValid shouldBe true
        }

        @Test
        fun `given a Validity with a clock in 2020 should report a date of 2010 read on another thread`() {
            val validity = Validity { clock = Clock.fixed(Instant.parse("2020-06-15T12:00:00Z"), UTC) }
            val booking = Booking(date = LocalDate.of(2010, 1, 1))

            val result = runBlocking(Threads.caller) { booking.validate(validity) }

            result.violations shouldBe listOf(
                Violation(path = "date", message = "must not be in the past", code = "")
            )
        }
    }

    @Nested
    @DisplayName("When validateBlocking is called")
    inner class ValidateBlocking {
        @Test
        fun `given a valid user should be valid`() {
            val user = User(name = "Ana", email = "ana@mail.com", age = null)

            val result = user.validateBlocking()

            result.isValid shouldBe true
        }

        @Test
        fun `given a SEPA payment with a bad amount and currency should report every violation at once`() {
            val payment = Payment(method = "SEPA", amount = "-9.999".toBigDecimal(), currency = "EURO")

            val violations = payment.validateBlocking().violations

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

            val violations = user.validateBlocking().violations

            violations shouldBe listOf(
                Violation(path = "name", message = "must have at least 2 characters", code = "minLength"),
                Violation(path = "email", message = "must be a valid email", code = "email"),
                Violation(path = "age", message = "must be at least 18", code = "min")
            )
        }

        @Test
        fun `given a Validity with a clock in 2000 should treat 2010 as the future`() {
            val validity = Validity { clock = Clock.fixed(Instant.parse("2000-06-15T12:00:00Z"), UTC) }
            val subscription = Subscription(startsOn = LocalDate.of(2010, 1, 1))

            val result = subscription.validateBlocking(validity)

            result.isValid shouldBe true
        }

        @Test
        fun `given a Validity with a clock in 2020 should report 2010 as not in the future`() {
            val validity = Validity { clock = Clock.fixed(Instant.parse("2020-06-15T12:00:00Z"), UTC) }
            val subscription = Subscription(startsOn = LocalDate.of(2010, 1, 1))

            val result = subscription.validateBlocking(validity)

            result.violations shouldBe listOf(
                Violation(path = "startsOn", message = "must be in the future", code = "future")
            )
        }

        @Test
        fun `given a Validity with a clock in 2000 should read it in a rule that runs on another thread`() {
            val validity = Validity { clock = Clock.fixed(Instant.parse("2000-06-15T12:00:00Z"), UTC) }
            val booking = Booking(date = LocalDate.of(2010, 1, 1))

            val result = booking.validateBlocking(validity)

            result.isValid shouldBe true
        }

        @Test
        fun `given a Validity with messages should report them in nested objects too`() {
            val validity = Validity { messages { minLength = "deve ter pelo menos {min} caracteres" } }
            val customer = Customer(name = "A", contact = Contact(phone = "123"))

            val result = customer.validateBlocking(validity)

            result.violations shouldBe listOf(
                Violation(path = "name", message = "deve ter pelo menos 2 caracteres", code = "minLength"),
                Violation(path = "contact.phone", message = "deve ter pelo menos 8 caracteres", code = "minLength")
            )
        }
    }

    @Nested
    @DisplayName("When validateOrThrow is called")
    inner class ValidateOrThrow {
        @Test
        fun `given a valid object should not throw`() {
            val customer = Customer()

            shouldNotThrowAny { runBlocking { customer.validateOrThrow() } }
        }

        @Test
        fun `given an invalid object should throw its violations`() {
            val customer = Customer(name = "A")

            val error = shouldThrow<ValidationException> { runBlocking { customer.validateOrThrow() } }

            error.violations shouldBe listOf(
                Violation(path = "name", message = "must have at least 2 characters", code = "minLength")
            )
        }
    }

    @Nested
    @DisplayName("When validateOrThrowBlocking is called")
    inner class ValidateOrThrowBlocking {
        @Test
        fun `given a valid object should not throw`() {
            val customer = Customer()

            shouldNotThrowAny { customer.validateOrThrowBlocking() }
        }

        @Test
        fun `given an invalid object should throw its violations`() {
            val customer = Customer(name = "A")

            val error = shouldThrow<ValidationException> { customer.validateOrThrowBlocking() }

            error.violations shouldBe listOf(
                Violation(path = "name", message = "must have at least 2 characters", code = "minLength")
            )
        }

        @Test
        fun `given an invalid user should throw an error that carries every violation`() {
            val user = User(name = "A", email = "ana@", age = 16)

            val error = shouldThrow<ValidationException> { user.validateOrThrowBlocking() }

            error.violations shouldBe listOf(
                Violation(path = "name", message = "must have at least 2 characters", code = "minLength"),
                Violation(path = "email", message = "must be a valid email", code = "email"),
                Violation(path = "age", message = "must be at least 18", code = "min")
            )
        }

        @Test
        fun `given a Validity with messages should throw its violations with them`() {
            val validity = Validity { messages { minLength = "deve ter pelo menos {min} caracteres" } }
            val customer = Customer(name = "A")

            val error = shouldThrow<ValidationException> { customer.validateOrThrowBlocking(validity) }

            error.violations shouldBe listOf(
                Violation(path = "name", message = "deve ter pelo menos 2 caracteres", code = "minLength")
            )
        }
    }
}
