package io.github.soleworks.validity

import io.github.soleworks.validity.samples.Buyer
import io.github.soleworks.validity.samples.Cart
import io.github.soleworks.validity.samples.CartItem
import io.github.soleworks.validity.samples.Directory
import io.github.soleworks.validity.samples.Enrollment
import io.github.soleworks.validity.samples.KnownUsers
import io.github.soleworks.validity.samples.readme.Checkout
import io.github.soleworks.validity.samples.readme.Customer
import io.github.soleworks.validity.samples.readme.Invitation
import io.github.soleworks.validity.samples.readme.Item
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ContextualValidatableTest {
    @Nested
    @DisplayName("When validate is called")
    inner class Validate {
        @Test
        fun `given a checkout within the limit and the catalog should be valid`() {
            val customer = Customer(creditLimit = 100, catalog = setOf("BOOK-1", "PEN-2"))
            val checkout = Checkout(total = 80, items = listOf(Item(sku = "BOOK-1")))

            val result = runBlocking { checkout.validate(customer) }

            result.isValid shouldBe true
        }

        @Test
        fun `given a cart within the limit of the buyer should be valid`() {
            val cart = Cart(total = 80)

            val result = runBlocking { cart.validate(Buyer(creditLimit = 100)) }

            result.isValid shouldBe true
        }

        @Test
        fun `given a checkout over the limit with an item outside the catalog should report both`() {
            val customer = Customer(creditLimit = 100, catalog = setOf("BOOK-1", "PEN-2"))
            val checkout = Checkout(total = 150, items = listOf(Item(sku = "BOOK-1"), Item(sku = "LAPTOP-9")))

            val result = runBlocking { checkout.validate(customer) }

            result.violations shouldBe listOf(
                Violation(path = "total", message = "must be at most 100", code = "max"),
                Violation(path = "items[1].sku", message = "is not in your catalog", code = "catalog")
            )
        }

        @Test
        fun `given an invitation for a registered email should report it`() {
            val invitation = Invitation(email = "ana@mail.com")

            val result = runBlocking { invitation.validate(KnownUsers("ana@mail.com")) }

            result.violations shouldBe listOf(
                Violation(path = "email", message = "is already registered", code = "registered")
            )
        }

        @Test
        fun `given an email the accounts already have should report it`() {
            val enrollment = Enrollment(email = "ana@mail.com")

            val result = runBlocking { enrollment.validate(Directory("ana@mail.com")) }

            result.violations shouldBe listOf(
                Violation(path = "email", message = "is already taken", code = "taken")
            )
        }

        @Test
        fun `given a cart over the limit with an item the buyer cannot buy should report both`() {
            val cart = Cart(total = 150, items = listOf(CartItem(), CartItem(sku = "SKU-2")))

            val result = runBlocking { cart.validate(Buyer(creditLimit = 100)) }

            result.violations shouldBe listOf(
                Violation(path = "total", message = "must be at most 100", code = "max"),
                Violation(path = "items[1].sku", message = "is not sold to this buyer", code = "sku")
            )
        }
    }

    @Nested
    @DisplayName("When validateBlocking is called")
    inner class ValidateBlocking {
        @Test
        fun `given an invitation for a new email should be valid`() {
            val invitation = Invitation(email = "bia@mail.com")

            val result = invitation.validateBlocking(KnownUsers("ana@mail.com"))

            result.isValid shouldBe true
        }

        @Test
        fun `given an email the accounts do not have should be valid`() {
            val enrollment = Enrollment(email = "bia@mail.com")

            val result = enrollment.validateBlocking(Directory("ana@mail.com"))

            result.isValid shouldBe true
        }

        @Test
        fun `given an email the accounts already have should report it`() {
            val enrollment = Enrollment(email = "ana@mail.com")

            val result = enrollment.validateBlocking(Directory("ana@mail.com"))

            result.violations shouldBe listOf(
                Violation(path = "email", message = "is already taken", code = "taken")
            )
        }

        @Test
        fun `given a Validity with messages should report the rules of the cart with them`() {
            val validity = Validity { messages { max = "deve ser no máximo {max}" } }
            val cart = Cart(total = 150)

            val result = cart.validateBlocking(Buyer(creditLimit = 100), validity)

            result.violations shouldBe listOf(
                Violation(path = "total", message = "deve ser no máximo 100", code = "max")
            )
        }
    }

    @Nested
    @DisplayName("When validateOrThrow is called")
    inner class ValidateOrThrow {
        @Test
        fun `given an email the accounts do not have should not throw`() {
            val enrollment = Enrollment(email = "bia@mail.com")

            shouldNotThrowAny { runBlocking { enrollment.validateOrThrow(Directory("ana@mail.com")) } }
        }

        @Test
        fun `given an email the accounts already have should throw its violation`() {
            val enrollment = Enrollment(email = "ana@mail.com")

            val error = shouldThrow<ValidationException> {
                runBlocking { enrollment.validateOrThrow(Directory("ana@mail.com")) }
            }

            error.violations shouldBe listOf(
                Violation(path = "email", message = "is already taken", code = "taken")
            )
        }
    }

    @Nested
    @DisplayName("When validateOrThrowBlocking is called")
    inner class ValidateOrThrowBlocking {
        @Test
        fun `given a cart within the limit of the buyer should not throw`() {
            val cart = Cart(total = 80)

            shouldNotThrowAny { cart.validateOrThrowBlocking(Buyer(creditLimit = 100)) }
        }

        @Test
        fun `given a cart over the limit of the buyer should throw its violation`() {
            val cart = Cart(total = 150)

            val error = shouldThrow<ValidationException> { cart.validateOrThrowBlocking(Buyer(creditLimit = 100)) }

            error.violations shouldBe listOf(
                Violation(path = "total", message = "must be at most 100", code = "max")
            )
        }
    }
}
