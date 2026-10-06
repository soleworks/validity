package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class MapConstraintsTest {
    @Nested
    @DisplayName("When minSize is called")
    inner class MinSize {
        @Test
        fun `given fewer prices than the minimum should report it`() {
            val node = ValidationNode("prices", mapOf("USD" to 1000)).apply { minSize(2) }

            node.validate() shouldBe listOf(Violation("prices", "must have at least 2 entries"))
        }

        @Test
        fun `given at least the minimum of prices should accept them`() {
            val node = ValidationNode("prices", mapOf("USD" to 1000, "BRL" to 5000)).apply { minSize(2) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function should build it from the value and the minimum`() {
            val node = ValidationNode("prices", mapOf("USD" to 1000)).apply {
                minSize(2) { min -> "has $size prices, needs at least $min" }
            }

            node.validate() shouldBe listOf(Violation("prices", "has 1 prices, needs at least 2"))
        }
    }

    @Nested
    @DisplayName("When maxSize is called")
    inner class MaxSize {
        @Test
        fun `given more prices than the maximum should report it`() {
            val node = ValidationNode("prices", mapOf("USD" to 1000, "BRL" to 5000, "EUR" to 900)).apply { maxSize(2) }

            node.validate() shouldBe listOf(Violation("prices", "must have at most 2 entries"))
        }

        @Test
        fun `given at most the maximum of prices should accept them`() {
            val node = ValidationNode("prices", mapOf("USD" to 1000, "BRL" to 5000)).apply { maxSize(2) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function should build it from the value and the maximum`() {
            val node = ValidationNode("prices", mapOf("USD" to 1000, "BRL" to 5000, "EUR" to 900)).apply {
                maxSize(2) { max -> "has $size prices, allows at most $max" }
            }

            node.validate() shouldBe listOf(Violation("prices", "has 3 prices, allows at most 2"))
        }
    }

    @Nested
    @DisplayName("When size is called")
    inner class Size {
        @Test
        fun `given prices without exactly the expected count should report it`() {
            val node = ValidationNode("prices", mapOf("USD" to 1000)).apply { size(2) }

            node.validate() shouldBe listOf(Violation("prices", "must have exactly 2 entries"))
        }

        @Test
        fun `given exactly the expected count of prices should accept them`() {
            val node = ValidationNode("prices", mapOf("USD" to 1000, "BRL" to 5000)).apply { size(2) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function should build it from the value and the size`() {
            val node = ValidationNode("prices", mapOf("USD" to 1000)).apply {
                size(2) { size -> "has ${this.size} prices, needs $size" }
            }

            node.validate() shouldBe listOf(Violation("prices", "has 1 prices, needs 2"))
        }
    }

    @Nested
    @DisplayName("When sizeBetween is called")
    inner class SizeBetween {
        @Test
        fun `given prices below the range should report it`() {
            val node = ValidationNode("prices", emptyMap<String, Int>()).apply { sizeBetween(1, 2) }

            node.validate() shouldBe listOf(Violation("prices", "must have between 1 and 2 entries"))
        }

        @Test
        fun `given prices above the range should report it`() {
            val node = ValidationNode("prices", mapOf("USD" to 1000, "BRL" to 5000, "EUR" to 900)).apply {
                sizeBetween(1, 2)
            }

            node.validate() shouldBe listOf(Violation("prices", "must have between 1 and 2 entries"))
        }

        @Test
        fun `given prices at the lower limit should accept them`() {
            val node = ValidationNode("prices", mapOf("USD" to 1000)).apply { sizeBetween(1, 2) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given prices at the upper limit should accept them`() {
            val node = ValidationNode("prices", mapOf("USD" to 1000, "BRL" to 5000)).apply { sizeBetween(1, 2) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function should build it from the value and the limits`() {
            val node = ValidationNode("prices", emptyMap<String, Int>()).apply {
                sizeBetween(1, 2) { min, max -> "has $size prices, needs from $min to $max" }
            }

            node.validate() shouldBe listOf(Violation("prices", "has 0 prices, needs from 1 to 2"))
        }
    }

    @Nested
    @DisplayName("When notEmpty is called")
    inner class NotEmpty {
        @Test
        fun `given no prices should report it`() {
            val node = ValidationNode("prices", emptyMap<String, Int>()).apply { notEmpty() }

            node.validate() shouldBe listOf(Violation("prices", "must not be empty"))
        }

        @Test
        fun `given one price should accept it`() {
            val node = ValidationNode("prices", mapOf("USD" to 1000)).apply { notEmpty() }

            node.validate() shouldBe emptyList()
        }
    }

    @Nested
    @DisplayName("When containsKey is called")
    inner class ContainsKey {
        @Test
        fun `given prices without the local currency should report it`() {
            val node = ValidationNode("prices", mapOf("USD" to 1000)).apply { containsKey("BRL") }

            node.validate() shouldBe listOf(Violation("prices", "must contain the key BRL"))
        }

        @Test
        fun `given prices with the local currency should accept them`() {
            val node = ValidationNode("prices", mapOf("USD" to 1000, "BRL" to 5000)).apply { containsKey("BRL") }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a store without a local currency should skip the check`() {
            val localCurrency: String? = null

            val node = ValidationNode("prices", mapOf("USD" to 1000)).apply { containsKey(localCurrency) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function should build it from the value and the key`() {
            val node = ValidationNode("prices", mapOf("USD" to 1000)).apply {
                containsKey("BRL") { key -> "has no price in $key" }
            }

            node.validate() shouldBe listOf(Violation("prices", "has no price in BRL"))
        }
    }

    @Nested
    @DisplayName("When containsKeys is called")
    inner class ContainsKeys {
        @Test
        fun `given prices missing one of the required currencies should report it`() {
            val node = ValidationNode("prices", mapOf("USD" to 1000)).apply { containsKeys(listOf("BRL", "USD")) }

            node.validate() shouldBe listOf(Violation("prices", "must contain the keys BRL, USD"))
        }

        @Test
        fun `given prices in all the required currencies should accept them`() {
            val node = ValidationNode("prices", mapOf("USD" to 1000, "BRL" to 5000, "EUR" to 900)).apply {
                containsKeys(listOf("BRL", "USD"))
            }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a store without required currencies should skip the check`() {
            val requiredCurrencies: List<String>? = null

            val node = ValidationNode("prices", mapOf("USD" to 1000)).apply { containsKeys(requiredCurrencies) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function should build it from the value and the keys`() {
            val node = ValidationNode("prices", mapOf("USD" to 1000)).apply {
                containsKeys(listOf("BRL", "USD")) { keys -> "needs prices in $keys" }
            }

            node.validate() shouldBe listOf(Violation("prices", "needs prices in [BRL, USD]"))
        }
    }
}
