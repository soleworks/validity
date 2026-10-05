package io.github.soleworks.validity

import io.github.soleworks.validity.samples.Address
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

            order.validate().violations shouldBe listOf(Violation("tags[1]", "must have at least 3 characters"))
        }

        @Test
        fun `given a list rule and item rules should report the list before its items`() {
            val order = Order(tags = listOf("a", "b", "gift", "sale"))

            order.validate().violations shouldBe listOf(
                Violation("tags", "must have at most 3 tags"),
                Violation("tags[0]", "must have at least 3 characters"),
                Violation("tags[1]", "must have at least 3 characters")
            )
        }

        @Test
        fun `given boxes of products should report the product with both indexes`() {
            val order = Order(boxes = listOf(listOf("SKU-1", "S2")))

            order.validate().violations shouldBe listOf(Violation("boxes[0][1]", "must have at least 5 characters"))
        }

        @Test
        fun `given a short coupon code in an array should report it with its index`() {
            val order = Order(couponCodes = arrayOf("PROMO10", "OFF"))

            order.validate().violations shouldBe listOf(Violation("couponCodes[1]", "must have at least 5 characters"))
        }

        @Test
        fun `given a rule on a price entry should report it with its currency`() {
            val order = Order(prices = mapOf("BRL" to 50))

            order.validate().violations shouldBe listOf(Violation("prices[BRL]", "must be at least 100 in BRL"))
        }
    }

    @Nested
    @DisplayName("When eachKey is called")
    inner class EachKey {
        @Test
        fun `given a currency code that is too short should report it with the key`() {
            val order = Order(prices = mapOf("US" to 1000))

            order.validate().violations shouldBe listOf(Violation("prices[US]", "must have at least 3 characters"))
        }
    }

    @Nested
    @DisplayName("When eachValue is called")
    inner class EachValue {
        @Test
        fun `given a missing price should report it with its currency`() {
            val order = Order(prices = mapOf("USD" to null))

            order.validate().violations shouldBe listOf(Violation("prices[USD]", "is required"))
        }
    }

    @Nested
    @DisplayName("When valid is called")
    inner class Valid {
        @Test
        fun `given an address without a street should report it under the address`() {
            val customer = Customer(address = Address(street = null))

            customer.validate().violations shouldBe listOf(Violation("address.street", "is required"))
        }

        @Test
        fun `given a list of addresses should report each one under its index`() {
            val customer = Customer(addresses = listOf(Address(), Address(street = null)))

            customer.validate().violations shouldBe listOf(Violation("addresses[1].street", "is required"))
        }

        @Test
        fun `given an address with a short phone should report it with the full path`() {
            val customer = Customer(addresses = listOf(Address(phones = listOf("123"))))

            customer.validate().violations shouldBe listOf(
                Violation("addresses[0].phones[0]", "must have at least 8 characters")
            )
        }

        @Test
        fun `given a customer without an address should report only that it is required`() {
            val customer = Customer(address = null)

            customer.validate().violations shouldBe listOf(Violation("address", "is required"))
        }
    }
}
