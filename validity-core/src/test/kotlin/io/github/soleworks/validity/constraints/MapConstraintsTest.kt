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
        fun `given at least the minimum of prices should accept them`() {
            val node = ValidationNode(path = "prices", value = mapOf("USD" to 1000, "BRL" to 5000)).apply { minSize(2) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given fewer prices than the minimum should report it`() {
            val node = ValidationNode(path = "prices", value = mapOf("USD" to 1000)).apply { minSize(2) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "prices", message = "must have at least 2 entries", code = "minSize")
            )
        }

        @Test
        fun `given a message function should build it from the value and the minimum`() {
            val node = ValidationNode(path = "prices", value = mapOf("USD" to 1000)).apply {
                minSize(2) { min -> "has $size prices, needs at least $min" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "prices", message = "has 1 prices, needs at least 2", code = "minSize")
            )
        }
    }

    @Nested
    @DisplayName("When maxSize is called")
    inner class MaxSize {
        @Test
        fun `given at most the maximum of prices should accept them`() {
            val node = ValidationNode(path = "prices", value = mapOf("USD" to 1000, "BRL" to 5000)).apply { maxSize(2) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given more prices than the maximum should report it`() {
            val node = ValidationNode(
                path = "prices",
                value = mapOf("USD" to 1000, "BRL" to 5000, "EUR" to 900)
            ).apply { maxSize(2) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "prices", message = "must have at most 2 entries", code = "maxSize")
            )
        }

        @Test
        fun `given a message function should build it from the value and the maximum`() {
            val node = ValidationNode(
                path = "prices",
                value = mapOf("USD" to 1000, "BRL" to 5000, "EUR" to 900)
            ).apply {
                maxSize(2) { max -> "has $size prices, allows at most $max" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "prices", message = "has 3 prices, allows at most 2", code = "maxSize")
            )
        }
    }

    @Nested
    @DisplayName("When size is called")
    inner class Size {
        @Test
        fun `given exactly the expected count of prices should accept them`() {
            val node = ValidationNode(path = "prices", value = mapOf("USD" to 1000, "BRL" to 5000)).apply { size(2) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given prices without exactly the expected count should report it`() {
            val node = ValidationNode(path = "prices", value = mapOf("USD" to 1000)).apply { size(2) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "prices", message = "must have exactly 2 entries", code = "size")
            )
        }

        @Test
        fun `given a message function should build it from the value and the size`() {
            val node = ValidationNode(path = "prices", value = mapOf("USD" to 1000)).apply {
                size(2) { size -> "has ${this.size} prices, needs $size" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "prices", message = "has 1 prices, needs 2", code = "size")
            )
        }
    }

    @Nested
    @DisplayName("When sizeBetween is called")
    inner class SizeBetween {
        @Test
        fun `given prices at the lower limit should accept them`() {
            val node = ValidationNode(
                path = "prices",
                value = mapOf("USD" to 1000)
            ).apply { sizeBetween(min = 1, max = 2) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given prices at the upper limit should accept them`() {
            val node = ValidationNode(
                path = "prices",
                value = mapOf("USD" to 1000, "BRL" to 5000)
            ).apply { sizeBetween(min = 1, max = 2) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given prices below the range should report it`() {
            val node = ValidationNode(
                path = "prices",
                value = emptyMap<String, Int>()
            ).apply { sizeBetween(min = 1, max = 2) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "prices", message = "must have between 1 and 2 entries", code = "sizeBetween")
            )
        }

        @Test
        fun `given prices above the range should report it`() {
            val node = ValidationNode(
                path = "prices",
                value = mapOf("USD" to 1000, "BRL" to 5000, "EUR" to 900)
            ).apply {
                sizeBetween(min = 1, max = 2)
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "prices", message = "must have between 1 and 2 entries", code = "sizeBetween")
            )
        }

        @Test
        fun `given a message function should build it from the value and the limits`() {
            val node = ValidationNode(path = "prices", value = emptyMap<String, Int>()).apply {
                sizeBetween(min = 1, max = 2) { min, max -> "has $size prices, needs from $min to $max" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "prices", message = "has 0 prices, needs from 1 to 2", code = "sizeBetween")
            )
        }
    }

    @Nested
    @DisplayName("When notEmpty is called")
    inner class NotEmpty {
        @Test
        fun `given one price should accept it`() {
            val node = ValidationNode(path = "prices", value = mapOf("USD" to 1000)).apply { notEmpty() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no prices should report it`() {
            val node = ValidationNode(path = "prices", value = emptyMap<String, Int>()).apply { notEmpty() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "prices", message = "must not be empty", code = "notEmpty")
            )
        }
    }

    @Nested
    @DisplayName("When containsKey is called")
    inner class ContainsKey {
        @Test
        fun `given prices with the local currency should accept them`() {
            val node = ValidationNode(
                path = "prices",
                value = mapOf("USD" to 1000, "BRL" to 5000)
            ).apply { containsKey("BRL") }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given prices without the local currency should report it`() {
            val node = ValidationNode(path = "prices", value = mapOf("USD" to 1000)).apply { containsKey("BRL") }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "prices", message = "must contain the key BRL", code = "containsKey")
            )
        }

        @Test
        fun `given a store without a local currency should skip the check`() {
            val localCurrency: String? = null
            val node = ValidationNode(
                path = "prices",
                value = mapOf("USD" to 1000)
            ).apply { containsKey(localCurrency) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a message function should build it from the value and the key`() {
            val node = ValidationNode(path = "prices", value = mapOf("USD" to 1000)).apply {
                containsKey("BRL") { key -> "has no price in $key" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "prices", message = "has no price in BRL", code = "containsKey")
            )
        }
    }

    @Nested
    @DisplayName("When containsKeys is called")
    inner class ContainsKeys {
        @Test
        fun `given prices in all the required currencies should accept them`() {
            val node = ValidationNode(
                path = "prices",
                value = mapOf("USD" to 1000, "BRL" to 5000, "EUR" to 900)
            ).apply {
                containsKeys(listOf("BRL", "USD"))
            }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given prices missing one of the required currencies should report it`() {
            val node = ValidationNode(
                path = "prices",
                value = mapOf("USD" to 1000)
            ).apply { containsKeys(listOf("BRL", "USD")) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "prices", message = "must contain the keys BRL, USD", code = "containsKeys")
            )
        }

        @Test
        fun `given a store without required currencies should skip the check`() {
            val requiredCurrencies: List<String>? = null
            val node = ValidationNode(
                path = "prices",
                value = mapOf("USD" to 1000)
            ).apply { containsKeys(requiredCurrencies) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a message function should build it from the value and the keys`() {
            val node = ValidationNode(path = "prices", value = mapOf("USD" to 1000)).apply {
                containsKeys(listOf("BRL", "USD")) { keys -> "needs prices in $keys" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "prices", message = "needs prices in [BRL, USD]", code = "containsKeys")
            )
        }
    }
}
