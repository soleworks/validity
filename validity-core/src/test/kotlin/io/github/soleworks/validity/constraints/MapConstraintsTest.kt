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
    @DisplayName("When empty is called")
    inner class Empty {
        @Test
        fun `given no overrides should accept them`() {
            val node = ValidationNode(path = "overrides", value = emptyMap<String, Int>()).apply { empty() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given one override should report it`() {
            val node = ValidationNode(path = "overrides", value = mapOf("USD" to 1000)).apply { empty() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "overrides", message = "must be empty", code = "empty")
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

    @Nested
    @DisplayName("When notContainsKey is called")
    inner class NotContainsKey {
        @Test
        fun `given headers without the authorization key should accept them`() {
            val node = ValidationNode(path = "headers", value = mapOf("Accept" to "json")).apply {
                notContainsKey("Authorization")
            }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given headers with the authorization key should report them`() {
            val node = ValidationNode(path = "headers", value = mapOf("Authorization" to "token")).apply {
                notContainsKey("Authorization")
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "headers", message = "must not contain the key Authorization", code = "notContainsKey")
            )
        }

        @Test
        fun `given no forbidden key should skip the check`() {
            val forbidden: String? = null
            val node = ValidationNode(path = "headers", value = mapOf("Authorization" to "token")).apply {
                notContainsKey(forbidden)
            }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a message function should build it from the value and the key`() {
            val node = ValidationNode(path = "headers", value = mapOf("Authorization" to "token")).apply {
                notContainsKey("Authorization") { key -> "has $key among $size headers" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "headers", message = "has Authorization among 1 headers", code = "notContainsKey")
            )
        }
    }

    @Nested
    @DisplayName("When onlyKeys is called")
    inner class OnlyKeys {
        @Test
        fun `given settings with allowed keys only should accept them`() {
            val node = ValidationNode(path = "settings", value = mapOf("theme" to "dark")).apply {
                onlyKeys(listOf("theme", "language"))
            }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given settings with an unknown key should report them`() {
            val node = ValidationNode(path = "settings", value = mapOf("theme" to "dark", "debug" to "on")).apply {
                onlyKeys(listOf("theme", "language"))
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "settings", message = "must contain only the keys theme, language", code = "onlyKeys")
            )
        }

        @Test
        fun `given no allowed keys should skip the check`() {
            val allowed: List<String>? = null
            val node = ValidationNode(path = "settings", value = mapOf("debug" to "on")).apply { onlyKeys(allowed) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a message function should build it from the value and the keys`() {
            val node = ValidationNode(path = "settings", value = mapOf("debug" to "on")).apply {
                onlyKeys(listOf("theme")) { keys -> "has keys outside $keys" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "settings", message = "has keys outside [theme]", code = "onlyKeys")
            )
        }
    }

    @Nested
    @DisplayName("When containsValue is called")
    inner class ContainsValue {
        @Test
        fun `given prices with a free item should accept them`() {
            val node = ValidationNode(path = "prices", value = mapOf("gift" to 0, "shirt" to 50)).apply {
                containsValue(0)
            }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given prices without a free item should report them`() {
            val node = ValidationNode(path = "prices", value = mapOf("shirt" to 50)).apply { containsValue(0) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "prices", message = "must contain the value 0", code = "containsValue")
            )
        }

        @Test
        fun `given no expected value should skip the check`() {
            val expected: Int? = null
            val node = ValidationNode(path = "prices", value = mapOf("shirt" to 50)).apply { containsValue(expected) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a message function should build it from the value and the expected value`() {
            val node = ValidationNode(path = "prices", value = mapOf("shirt" to 50)).apply {
                containsValue(0) { other -> "has no $other among $size prices" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "prices", message = "has no 0 among 1 prices", code = "containsValue")
            )
        }
    }
}
