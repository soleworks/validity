package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.Violation
import io.github.soleworks.validity.fixtures.Customer
import io.github.soleworks.validity.fixtures.Product
import io.github.soleworks.validity.validate
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class StringConstraintsTest {
    @Nested
    @DisplayName("When minLength is called")
    inner class MinLength {
        @ParameterizedTest
        @ValueSource(strings = ["", "A"])
        fun `given a name shorter than the minimum should report it`(name: String) {
            val customer = Customer(name = name)

            customer.validate().violations shouldBe listOf(Violation("name", "must have at least 2 characters"))
        }

        @ParameterizedTest
        @ValueSource(strings = ["Al", "Ana"])
        fun `given a name with at least the minimum should accept it`(name: String) {
            val customer = Customer(name = name)

            customer.validate().violations shouldBe emptyList()
        }

        @Test
        fun `given a custom message with the minimum placeholder should replace it`() {
            val product = Product(sku = "S1")

            product.validate().violations shouldBe listOf(
                Violation("sku", "must have at least 5 characters, like SKU-1")
            )
        }

        @Test
        fun `given a message function should build it from the value and the minimum`() {
            val product = Product(description = "Shirt")

            product.validate().violations shouldBe listOf(
                Violation("description", "'Shirt' is too short, it needs at least 10 characters")
            )
        }
    }
}
