package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class UnsignedConstraintsTest {
    @Nested
    @DisplayName("When min is called")
    inner class Min {
        @Test
        fun `given a UInt quantity that meets the bound should accept it`() {
            val node = ValidationNode(path = "quantity", value = 10u).apply { min(10u) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a ULong quantity that meets the bound should accept it`() {
            val node = ValidationNode(path = "quantity", value = 10uL).apply { min(10uL) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a UShort quantity that meets the bound should accept it`() {
            val node = ValidationNode(path = "quantity", value = 10u.toUShort()).apply { min(10u.toUShort()) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a UByte quantity that meets the bound should accept it`() {
            val node = ValidationNode(path = "quantity", value = 10u.toUByte()).apply { min(10u.toUByte()) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a UInt quantity that misses the bound should report it`() {
            val node = ValidationNode(path = "quantity", value = 9u).apply { min(10u) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be at least 10", code = "min")
            )
        }

        @Test
        fun `given a ULong quantity that misses the bound should report it`() {
            val node = ValidationNode(path = "quantity", value = 9uL).apply { min(10uL) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be at least 10", code = "min")
            )
        }

        @Test
        fun `given a UShort quantity that misses the bound should report it`() {
            val node = ValidationNode(path = "quantity", value = 9u.toUShort()).apply { min(10u.toUShort()) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be at least 10", code = "min")
            )
        }

        @Test
        fun `given a UByte quantity that misses the bound should report it`() {
            val node = ValidationNode(path = "quantity", value = 9u.toUByte()).apply { min(10u.toUByte()) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be at least 10", code = "min")
            )
        }

        @Test
        fun `given no bound should skip the check`() {
            val bound: UInt? = null
            val node = ValidationNode(path = "quantity", value = 9u).apply { min(bound) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no bound for a ULong should skip the check`() {
            val bound: ULong? = null
            val node = ValidationNode(path = "quantity", value = 9uL).apply { min(bound) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no bound for a UShort should skip the check`() {
            val bound: UShort? = null
            val node = ValidationNode(path = "quantity", value = 9u.toUShort()).apply { min(bound) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no bound for a UByte should skip the check`() {
            val bound: UByte? = null
            val node = ValidationNode(path = "quantity", value = 9u.toUByte()).apply { min(bound) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a message function should build it from the value and the bound`() {
            val node = ValidationNode(path = "quantity", value = 9u).apply {
                min(10u) { min -> "$this misses $min" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "9 misses 10", code = "min")
            )
        }
    }

    @Nested
    @DisplayName("When max is called")
    inner class Max {
        @Test
        fun `given a UInt quantity that meets the bound should accept it`() {
            val node = ValidationNode(path = "quantity", value = 10u).apply { max(10u) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a ULong quantity that meets the bound should accept it`() {
            val node = ValidationNode(path = "quantity", value = 10uL).apply { max(10uL) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a UShort quantity that meets the bound should accept it`() {
            val node = ValidationNode(path = "quantity", value = 10u.toUShort()).apply { max(10u.toUShort()) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a UByte quantity that meets the bound should accept it`() {
            val node = ValidationNode(path = "quantity", value = 10u.toUByte()).apply { max(10u.toUByte()) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a UInt quantity that misses the bound should report it`() {
            val node = ValidationNode(path = "quantity", value = 11u).apply { max(10u) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be at most 10", code = "max")
            )
        }

        @Test
        fun `given a ULong quantity that misses the bound should report it`() {
            val node = ValidationNode(path = "quantity", value = 11uL).apply { max(10uL) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be at most 10", code = "max")
            )
        }

        @Test
        fun `given a UShort quantity that misses the bound should report it`() {
            val node = ValidationNode(path = "quantity", value = 11u.toUShort()).apply { max(10u.toUShort()) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be at most 10", code = "max")
            )
        }

        @Test
        fun `given a UByte quantity that misses the bound should report it`() {
            val node = ValidationNode(path = "quantity", value = 11u.toUByte()).apply { max(10u.toUByte()) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be at most 10", code = "max")
            )
        }

        @Test
        fun `given no bound should skip the check`() {
            val bound: UInt? = null
            val node = ValidationNode(path = "quantity", value = 11u).apply { max(bound) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no bound for a ULong should skip the check`() {
            val bound: ULong? = null
            val node = ValidationNode(path = "quantity", value = 9uL).apply { max(bound) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no bound for a UShort should skip the check`() {
            val bound: UShort? = null
            val node = ValidationNode(path = "quantity", value = 9u.toUShort()).apply { max(bound) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no bound for a UByte should skip the check`() {
            val bound: UByte? = null
            val node = ValidationNode(path = "quantity", value = 9u.toUByte()).apply { max(bound) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a message function should build it from the value and the bound`() {
            val node = ValidationNode(path = "quantity", value = 11u).apply {
                max(10u) { max -> "$this misses $max" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "11 misses 10", code = "max")
            )
        }
    }

    @Nested
    @DisplayName("When greaterThan is called")
    inner class GreaterThan {
        @Test
        fun `given a UInt quantity that meets the bound should accept it`() {
            val node = ValidationNode(path = "quantity", value = 11u).apply { greaterThan(10u) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a ULong quantity that meets the bound should accept it`() {
            val node = ValidationNode(path = "quantity", value = 11uL).apply { greaterThan(10uL) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a UShort quantity that meets the bound should accept it`() {
            val node = ValidationNode(path = "quantity", value = 11u.toUShort()).apply { greaterThan(10u.toUShort()) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a UByte quantity that meets the bound should accept it`() {
            val node = ValidationNode(path = "quantity", value = 11u.toUByte()).apply { greaterThan(10u.toUByte()) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a UInt quantity that misses the bound should report it`() {
            val node = ValidationNode(path = "quantity", value = 10u).apply { greaterThan(10u) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be greater than 10", code = "greaterThan")
            )
        }

        @Test
        fun `given a ULong quantity that misses the bound should report it`() {
            val node = ValidationNode(path = "quantity", value = 10uL).apply { greaterThan(10uL) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be greater than 10", code = "greaterThan")
            )
        }

        @Test
        fun `given a UShort quantity that misses the bound should report it`() {
            val node = ValidationNode(path = "quantity", value = 10u.toUShort()).apply { greaterThan(10u.toUShort()) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be greater than 10", code = "greaterThan")
            )
        }

        @Test
        fun `given a UByte quantity that misses the bound should report it`() {
            val node = ValidationNode(path = "quantity", value = 10u.toUByte()).apply { greaterThan(10u.toUByte()) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be greater than 10", code = "greaterThan")
            )
        }

        @Test
        fun `given no bound should skip the check`() {
            val bound: UInt? = null
            val node = ValidationNode(path = "quantity", value = 10u).apply { greaterThan(bound) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no bound for a ULong should skip the check`() {
            val bound: ULong? = null
            val node = ValidationNode(path = "quantity", value = 9uL).apply { greaterThan(bound) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no bound for a UShort should skip the check`() {
            val bound: UShort? = null
            val node = ValidationNode(path = "quantity", value = 9u.toUShort()).apply { greaterThan(bound) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no bound for a UByte should skip the check`() {
            val bound: UByte? = null
            val node = ValidationNode(path = "quantity", value = 9u.toUByte()).apply { greaterThan(bound) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a message function should build it from the value and the bound`() {
            val node = ValidationNode(path = "quantity", value = 10u).apply {
                greaterThan(10u) { other -> "$this misses $other" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "10 misses 10", code = "greaterThan")
            )
        }
    }

    @Nested
    @DisplayName("When lessThan is called")
    inner class LessThan {
        @Test
        fun `given a UInt quantity that meets the bound should accept it`() {
            val node = ValidationNode(path = "quantity", value = 9u).apply { lessThan(10u) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a ULong quantity that meets the bound should accept it`() {
            val node = ValidationNode(path = "quantity", value = 9uL).apply { lessThan(10uL) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a UShort quantity that meets the bound should accept it`() {
            val node = ValidationNode(path = "quantity", value = 9u.toUShort()).apply { lessThan(10u.toUShort()) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a UByte quantity that meets the bound should accept it`() {
            val node = ValidationNode(path = "quantity", value = 9u.toUByte()).apply { lessThan(10u.toUByte()) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a UInt quantity that misses the bound should report it`() {
            val node = ValidationNode(path = "quantity", value = 10u).apply { lessThan(10u) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be less than 10", code = "lessThan")
            )
        }

        @Test
        fun `given a ULong quantity that misses the bound should report it`() {
            val node = ValidationNode(path = "quantity", value = 10uL).apply { lessThan(10uL) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be less than 10", code = "lessThan")
            )
        }

        @Test
        fun `given a UShort quantity that misses the bound should report it`() {
            val node = ValidationNode(path = "quantity", value = 10u.toUShort()).apply { lessThan(10u.toUShort()) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be less than 10", code = "lessThan")
            )
        }

        @Test
        fun `given a UByte quantity that misses the bound should report it`() {
            val node = ValidationNode(path = "quantity", value = 10u.toUByte()).apply { lessThan(10u.toUByte()) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be less than 10", code = "lessThan")
            )
        }

        @Test
        fun `given no bound should skip the check`() {
            val bound: UInt? = null
            val node = ValidationNode(path = "quantity", value = 10u).apply { lessThan(bound) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no bound for a ULong should skip the check`() {
            val bound: ULong? = null
            val node = ValidationNode(path = "quantity", value = 9uL).apply { lessThan(bound) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no bound for a UShort should skip the check`() {
            val bound: UShort? = null
            val node = ValidationNode(path = "quantity", value = 9u.toUShort()).apply { lessThan(bound) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no bound for a UByte should skip the check`() {
            val bound: UByte? = null
            val node = ValidationNode(path = "quantity", value = 9u.toUByte()).apply { lessThan(bound) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a message function should build it from the value and the bound`() {
            val node = ValidationNode(path = "quantity", value = 10u).apply {
                lessThan(10u) { other -> "$this misses $other" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "10 misses 10", code = "lessThan")
            )
        }
    }

    @Nested
    @DisplayName("When between is called")
    inner class Between {
        @Test
        fun `given a UInt quantity within the range should accept it`() {
            val node = ValidationNode(path = "quantity", value = 5u).apply {
                between(min = 1u, max = 10u)
            }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a ULong quantity within the range should accept it`() {
            val node = ValidationNode(path = "quantity", value = 5uL).apply {
                between(min = 1uL, max = 10uL)
            }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a UShort quantity within the range should accept it`() {
            val node = ValidationNode(path = "quantity", value = 5u.toUShort()).apply {
                between(min = 1u.toUShort(), max = 10u.toUShort())
            }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a UByte quantity within the range should accept it`() {
            val node = ValidationNode(path = "quantity", value = 5u.toUByte()).apply {
                between(min = 1u.toUByte(), max = 10u.toUByte())
            }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a UInt quantity out of the range should report it`() {
            val node = ValidationNode(path = "quantity", value = 11u).apply {
                between(min = 1u, max = 10u)
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be between 1 and 10", code = "between")
            )
        }

        @Test
        fun `given a ULong quantity out of the range should report it`() {
            val node = ValidationNode(path = "quantity", value = 11uL).apply {
                between(min = 1uL, max = 10uL)
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be between 1 and 10", code = "between")
            )
        }

        @Test
        fun `given a UShort quantity out of the range should report it`() {
            val node = ValidationNode(path = "quantity", value = 11u.toUShort()).apply {
                between(min = 1u.toUShort(), max = 10u.toUShort())
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be between 1 and 10", code = "between")
            )
        }

        @Test
        fun `given a UByte quantity out of the range should report it`() {
            val node = ValidationNode(path = "quantity", value = 11u.toUByte()).apply {
                between(min = 1u.toUByte(), max = 10u.toUByte())
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be between 1 and 10", code = "between")
            )
        }

        @Test
        fun `given no upper bound should skip the check`() {
            val max: UInt? = null
            val node = ValidationNode(path = "quantity", value = 11u).apply { between(min = 1u, max = max) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no lower bound for a UInt should skip the check`() {
            val node = ValidationNode(path = "quantity", value = 11u).apply { between(min = null, max = 10u) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no lower bound for a ULong should skip the check`() {
            val node = ValidationNode(path = "quantity", value = 11uL).apply { between(min = null, max = 10uL) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no upper bound for a ULong should skip the check`() {
            val node = ValidationNode(path = "quantity", value = 11uL).apply { between(min = 1uL, max = null) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no lower bound for a UShort should skip the check`() {
            val node = ValidationNode(path = "quantity", value = 11u.toUShort()).apply {
                between(min = null, max = 10u.toUShort())
            }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no upper bound for a UShort should skip the check`() {
            val node = ValidationNode(path = "quantity", value = 11u.toUShort()).apply {
                between(min = 1u.toUShort(), max = null)
            }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no lower bound for a UByte should skip the check`() {
            val node = ValidationNode(path = "quantity", value = 11u.toUByte()).apply {
                between(min = null, max = 10u.toUByte())
            }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no upper bound for a UByte should skip the check`() {
            val node = ValidationNode(path = "quantity", value = 11u.toUByte()).apply {
                between(min = 1u.toUByte(), max = null)
            }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a message function should build it from the value and the range`() {
            val node = ValidationNode(path = "quantity", value = 11u).apply {
                between(min = 1u, max = 10u) { min, max -> "$this is not in $min..$max" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "11 is not in 1..10", code = "between")
            )
        }
    }
}
