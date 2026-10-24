package io.github.soleworks.validity

import io.github.soleworks.validity.samples.Address
import io.github.soleworks.validity.samples.Contact
import io.github.soleworks.validity.samples.Customer
import io.github.soleworks.validity.samples.Order
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class AccessorsTest {
    @Nested
    @DisplayName("When each is called")
    inner class Each {
        @Test
        fun `given a short tag should report it with its index`() {
            val order = Order(tags = listOf("gift", "x"))

            val result = order.validate()

            result.violations shouldBe listOf(
                Violation(path = "tags[1]", message = "must have at least 3 characters", code = "minLength")
            )
        }

        @Test
        fun `given a list rule and item rules should report the list before its items`() {
            val order = Order(tags = listOf("a", "b", "gift", "sale"))

            val result = order.validate()

            result.violations shouldBe listOf(
                Violation(path = "tags", message = "must have at most 3 tags", code = ""),
                Violation(path = "tags[0]", message = "must have at least 3 characters", code = "minLength"),
                Violation(path = "tags[1]", message = "must have at least 3 characters", code = "minLength")
            )
        }

        @Test
        fun `given boxes of products should report the product with both indexes`() {
            val order = Order(boxes = listOf(listOf("SKU-1", "S2")))

            val result = order.validate()

            result.violations shouldBe listOf(
                Violation(path = "boxes[0][1]", message = "must have at least 5 characters", code = "minLength")
            )
        }

        @Test
        fun `given a short coupon code in an array should report it with its index`() {
            val order = Order(couponCodes = arrayOf("PROMO10", "OFF"))

            val result = order.validate()

            result.violations shouldBe listOf(
                Violation(path = "couponCodes[1]", message = "must have at least 5 characters", code = "minLength")
            )
        }

        @Test
        fun `given a rule on a price entry should report it with its currency`() {
            val order = Order(prices = mapOf("BRL" to 50))

            val result = order.validate()

            result.violations shouldBe listOf(
                Violation(path = "prices[BRL]", message = "must be at least 100 in BRL", code = "")
            )
        }
    }

    @Nested
    @DisplayName("When eachKey is called")
    inner class EachKey {
        @Test
        fun `given a currency code that is too short should report it with the key`() {
            val order = Order(prices = mapOf("US" to 1000))

            val result = order.validate()

            result.violations shouldBe listOf(
                Violation(path = "prices[US]", message = "must have at least 3 characters", code = "minLength")
            )
        }
    }

    @Nested
    @DisplayName("When eachValue is called")
    inner class EachValue {
        @Test
        fun `given a missing price should report it with its currency`() {
            val order = Order(prices = mapOf("USD" to null))

            val result = order.validate()

            result.violations shouldBe listOf(
                Violation(path = "prices[USD]", message = "is required", code = "notNull")
            )
        }
    }

    @Nested
    @DisplayName("When valid is called")
    inner class Valid {
        @Test
        fun `given an address without a street should report it under the address`() {
            val customer = Customer(address = Address(street = null))

            val result = customer.validate()

            result.violations shouldBe listOf(
                Violation(path = "address.street", message = "is required", code = "required")
            )
        }

        @Test
        fun `given a list of addresses should report each one under its index`() {
            val customer = Customer(addresses = listOf(Address(), Address(street = null)))

            val result = customer.validate()

            result.violations shouldBe listOf(
                Violation(path = "addresses[1].street", message = "is required", code = "required")
            )
        }

        @Test
        fun `given an address with a short phone should report it with the full path`() {
            val customer = Customer(addresses = listOf(Address(phones = listOf("123"))))

            val result = customer.validate()

            result.violations shouldBe listOf(
                Violation(
                    path = "addresses[0].phones[0]",
                    message = "must have at least 8 characters",
                    code = "minLength"
                )
            )
        }

        @Test
        fun `given a customer without an address should report only that it is required`() {
            val customer = Customer(address = null)

            val result = customer.validate()

            result.violations shouldBe listOf(
                Violation(path = "address", message = "is required", code = "required")
            )
        }

        @Test
        fun `given a contact with a short phone should report it under the contact`() {
            val customer = Customer(contact = Contact(phone = "123"))

            val result = customer.validate()

            result.violations shouldBe listOf(
                Violation(path = "contact.phone", message = "must have at least 8 characters", code = "minLength")
            )
        }

        @Test
        fun `given a list of contacts should report each one under its index`() {
            val customer = Customer(contacts = listOf(Contact(), Contact(phone = null)))

            val result = customer.validate()

            result.violations shouldBe listOf(
                Violation(path = "contacts[1].phone", message = "is required", code = "required")
            )
        }

        @Test
        fun `given a customer without a contact should report only that it is required`() {
            val customer = Customer(contact = null)

            val result = customer.validate()

            result.violations shouldBe listOf(
                Violation(path = "contact", message = "is required", code = "required")
            )
        }
    }
}
