package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import java.math.BigDecimal
import java.math.BigInteger
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class NumberConstraintsTest {
    @Nested
    @DisplayName("When min is called")
    inner class Min {
        @Test
        fun `given an Int below the minimum should report it`() {
            val node = ValidationNode("quantity", 9).apply { min(10) }

            node.validate() shouldBe listOf(Violation("quantity", "must be at least 10"))
        }

        @Test
        fun `given an Int at the minimum should accept it`() {
            val node = ValidationNode("quantity", 10).apply { min(10) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no minimum for an Int should skip the check`() {
            val minimum: Int? = null

            val node = ValidationNode("quantity", 9).apply { min(minimum) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for an Int should build it from the value and the minimum`() {
            val node = ValidationNode("quantity", 9).apply { min(10) { min -> "$this is below $min" } }

            node.validate() shouldBe listOf(Violation("quantity", "9 is below 10"))
        }

        @Test
        fun `given a Long below the minimum should report it`() {
            val node = ValidationNode("quantity", 9L).apply { min(10L) }

            node.validate() shouldBe listOf(Violation("quantity", "must be at least 10"))
        }

        @Test
        fun `given a Long at the minimum should accept it`() {
            val node = ValidationNode("quantity", 10L).apply { min(10L) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no minimum for a Long should skip the check`() {
            val minimum: Long? = null

            val node = ValidationNode("quantity", 9L).apply { min(minimum) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a Long should build it from the value and the minimum`() {
            val node = ValidationNode("quantity", 9L).apply { min(10L) { min -> "$this is below $min" } }

            node.validate() shouldBe listOf(Violation("quantity", "9 is below 10"))
        }

        @Test
        fun `given a Short below the minimum should report it`() {
            val node = ValidationNode("quantity", 9.toShort()).apply { min(10.toShort()) }

            node.validate() shouldBe listOf(Violation("quantity", "must be at least 10"))
        }

        @Test
        fun `given a Short at the minimum should accept it`() {
            val node = ValidationNode("quantity", 10.toShort()).apply { min(10.toShort()) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no minimum for a Short should skip the check`() {
            val minimum: Short? = null

            val node = ValidationNode("quantity", 9.toShort()).apply { min(minimum) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a Short should build it from the value and the minimum`() {
            val node = ValidationNode("quantity", 9.toShort()).apply {
                min(10.toShort()) { min -> "$this is below $min" }
            }

            node.validate() shouldBe listOf(Violation("quantity", "9 is below 10"))
        }

        @Test
        fun `given a Byte below the minimum should report it`() {
            val node = ValidationNode("quantity", 9.toByte()).apply { min(10.toByte()) }

            node.validate() shouldBe listOf(Violation("quantity", "must be at least 10"))
        }

        @Test
        fun `given a Byte at the minimum should accept it`() {
            val node = ValidationNode("quantity", 10.toByte()).apply { min(10.toByte()) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no minimum for a Byte should skip the check`() {
            val minimum: Byte? = null

            val node = ValidationNode("quantity", 9.toByte()).apply { min(minimum) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a Byte should build it from the value and the minimum`() {
            val node = ValidationNode("quantity", 9.toByte()).apply {
                min(10.toByte()) { min -> "$this is below $min" }
            }

            node.validate() shouldBe listOf(Violation("quantity", "9 is below 10"))
        }

        @Test
        fun `given a Double below the minimum should report it`() {
            val node = ValidationNode("rate", 9.0).apply { min(10.0) }

            node.validate() shouldBe listOf(Violation("rate", "must be at least 10.0"))
        }

        @Test
        fun `given a Double at the minimum should accept it`() {
            val node = ValidationNode("rate", 10.0).apply { min(10.0) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no minimum for a Double should skip the check`() {
            val minimum: Double? = null

            val node = ValidationNode("rate", 9.0).apply { min(minimum) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a Double should build it from the value and the minimum`() {
            val node = ValidationNode("rate", 9.0).apply { min(10.0) { min -> "$this is below $min" } }

            node.validate() shouldBe listOf(Violation("rate", "9.0 is below 10.0"))
        }

        @Test
        fun `given a Float below the minimum should report it`() {
            val node = ValidationNode("rate", 9f).apply { min(10f) }

            node.validate() shouldBe listOf(Violation("rate", "must be at least 10.0"))
        }

        @Test
        fun `given a Float at the minimum should accept it`() {
            val node = ValidationNode("rate", 10f).apply { min(10f) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no minimum for a Float should skip the check`() {
            val minimum: Float? = null

            val node = ValidationNode("rate", 9f).apply { min(minimum) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a Float should build it from the value and the minimum`() {
            val node = ValidationNode("rate", 9f).apply { min(10f) { min -> "$this is below $min" } }

            node.validate() shouldBe listOf(Violation("rate", "9.0 is below 10.0"))
        }

        @Test
        fun `given a BigInteger below the minimum should report it`() {
            val node = ValidationNode("supply", BigInteger("9")).apply { min(BigInteger("10")) }

            node.validate() shouldBe listOf(Violation("supply", "must be at least 10"))
        }

        @Test
        fun `given a BigInteger at the minimum should accept it`() {
            val node = ValidationNode("supply", BigInteger("10")).apply { min(BigInteger("10")) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no minimum for a BigInteger should skip the check`() {
            val minimum: BigInteger? = null

            val node = ValidationNode("supply", BigInteger("9")).apply { min(minimum) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a BigInteger should build it from the value and the minimum`() {
            val node = ValidationNode("supply", BigInteger("9")).apply {
                min(BigInteger("10")) { min -> "$this is below $min" }
            }

            node.validate() shouldBe listOf(Violation("supply", "9 is below 10"))
        }

        @Test
        fun `given a BigDecimal below the minimum should report it`() {
            val node = ValidationNode("amount", BigDecimal("9.00")).apply { min(BigDecimal("10.00")) }

            node.validate() shouldBe listOf(Violation("amount", "must be at least 10.00"))
        }

        @Test
        fun `given a BigDecimal at the minimum should accept it`() {
            val node = ValidationNode("amount", BigDecimal("10.00")).apply { min(BigDecimal("10.00")) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no minimum for a BigDecimal should skip the check`() {
            val minimum: BigDecimal? = null

            val node = ValidationNode("amount", BigDecimal("9.00")).apply { min(minimum) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a BigDecimal should build it from the value and the minimum`() {
            val node = ValidationNode("amount", BigDecimal("9.00")).apply {
                min(BigDecimal("10.00")) { min -> "$this is below $min" }
            }

            node.validate() shouldBe listOf(Violation("amount", "9.00 is below 10.00"))
        }
    }

    @Nested
    @DisplayName("When max is called")
    inner class Max {
        @Test
        fun `given an Int above the maximum should report it`() {
            val node = ValidationNode("quantity", 11).apply { max(10) }

            node.validate() shouldBe listOf(Violation("quantity", "must be at most 10"))
        }

        @Test
        fun `given an Int at the maximum should accept it`() {
            val node = ValidationNode("quantity", 10).apply { max(10) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no maximum for an Int should skip the check`() {
            val maximum: Int? = null

            val node = ValidationNode("quantity", 11).apply { max(maximum) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for an Int should build it from the value and the maximum`() {
            val node = ValidationNode("quantity", 11).apply { max(10) { max -> "$this is above $max" } }

            node.validate() shouldBe listOf(Violation("quantity", "11 is above 10"))
        }

        @Test
        fun `given a Long above the maximum should report it`() {
            val node = ValidationNode("quantity", 11L).apply { max(10L) }

            node.validate() shouldBe listOf(Violation("quantity", "must be at most 10"))
        }

        @Test
        fun `given a Long at the maximum should accept it`() {
            val node = ValidationNode("quantity", 10L).apply { max(10L) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no maximum for a Long should skip the check`() {
            val maximum: Long? = null

            val node = ValidationNode("quantity", 11L).apply { max(maximum) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a Long should build it from the value and the maximum`() {
            val node = ValidationNode("quantity", 11L).apply { max(10L) { max -> "$this is above $max" } }

            node.validate() shouldBe listOf(Violation("quantity", "11 is above 10"))
        }

        @Test
        fun `given a Short above the maximum should report it`() {
            val node = ValidationNode("quantity", 11.toShort()).apply { max(10.toShort()) }

            node.validate() shouldBe listOf(Violation("quantity", "must be at most 10"))
        }

        @Test
        fun `given a Short at the maximum should accept it`() {
            val node = ValidationNode("quantity", 10.toShort()).apply { max(10.toShort()) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no maximum for a Short should skip the check`() {
            val maximum: Short? = null

            val node = ValidationNode("quantity", 11.toShort()).apply { max(maximum) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a Short should build it from the value and the maximum`() {
            val node = ValidationNode("quantity", 11.toShort()).apply {
                max(10.toShort()) { max -> "$this is above $max" }
            }

            node.validate() shouldBe listOf(Violation("quantity", "11 is above 10"))
        }

        @Test
        fun `given a Byte above the maximum should report it`() {
            val node = ValidationNode("quantity", 11.toByte()).apply { max(10.toByte()) }

            node.validate() shouldBe listOf(Violation("quantity", "must be at most 10"))
        }

        @Test
        fun `given a Byte at the maximum should accept it`() {
            val node = ValidationNode("quantity", 10.toByte()).apply { max(10.toByte()) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no maximum for a Byte should skip the check`() {
            val maximum: Byte? = null

            val node = ValidationNode("quantity", 11.toByte()).apply { max(maximum) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a Byte should build it from the value and the maximum`() {
            val node = ValidationNode("quantity", 11.toByte()).apply {
                max(10.toByte()) { max -> "$this is above $max" }
            }

            node.validate() shouldBe listOf(Violation("quantity", "11 is above 10"))
        }

        @Test
        fun `given a Double above the maximum should report it`() {
            val node = ValidationNode("rate", 11.0).apply { max(10.0) }

            node.validate() shouldBe listOf(Violation("rate", "must be at most 10.0"))
        }

        @Test
        fun `given a Double at the maximum should accept it`() {
            val node = ValidationNode("rate", 10.0).apply { max(10.0) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no maximum for a Double should skip the check`() {
            val maximum: Double? = null

            val node = ValidationNode("rate", 11.0).apply { max(maximum) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a Double should build it from the value and the maximum`() {
            val node = ValidationNode("rate", 11.0).apply { max(10.0) { max -> "$this is above $max" } }

            node.validate() shouldBe listOf(Violation("rate", "11.0 is above 10.0"))
        }

        @Test
        fun `given a Float above the maximum should report it`() {
            val node = ValidationNode("rate", 11f).apply { max(10f) }

            node.validate() shouldBe listOf(Violation("rate", "must be at most 10.0"))
        }

        @Test
        fun `given a Float at the maximum should accept it`() {
            val node = ValidationNode("rate", 10f).apply { max(10f) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no maximum for a Float should skip the check`() {
            val maximum: Float? = null

            val node = ValidationNode("rate", 11f).apply { max(maximum) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a Float should build it from the value and the maximum`() {
            val node = ValidationNode("rate", 11f).apply { max(10f) { max -> "$this is above $max" } }

            node.validate() shouldBe listOf(Violation("rate", "11.0 is above 10.0"))
        }

        @Test
        fun `given a BigInteger above the maximum should report it`() {
            val node = ValidationNode("supply", BigInteger("11")).apply { max(BigInteger("10")) }

            node.validate() shouldBe listOf(Violation("supply", "must be at most 10"))
        }

        @Test
        fun `given a BigInteger at the maximum should accept it`() {
            val node = ValidationNode("supply", BigInteger("10")).apply { max(BigInteger("10")) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no maximum for a BigInteger should skip the check`() {
            val maximum: BigInteger? = null

            val node = ValidationNode("supply", BigInteger("11")).apply { max(maximum) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a BigInteger should build it from the value and the maximum`() {
            val node = ValidationNode("supply", BigInteger("11")).apply {
                max(BigInteger("10")) { max -> "$this is above $max" }
            }

            node.validate() shouldBe listOf(Violation("supply", "11 is above 10"))
        }

        @Test
        fun `given a BigDecimal above the maximum should report it`() {
            val node = ValidationNode("amount", BigDecimal("11.00")).apply { max(BigDecimal("10.00")) }

            node.validate() shouldBe listOf(Violation("amount", "must be at most 10.00"))
        }

        @Test
        fun `given a BigDecimal at the maximum should accept it`() {
            val node = ValidationNode("amount", BigDecimal("10.00")).apply { max(BigDecimal("10.00")) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no maximum for a BigDecimal should skip the check`() {
            val maximum: BigDecimal? = null

            val node = ValidationNode("amount", BigDecimal("11.00")).apply { max(maximum) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a BigDecimal should build it from the value and the maximum`() {
            val node = ValidationNode("amount", BigDecimal("11.00")).apply {
                max(BigDecimal("10.00")) { max -> "$this is above $max" }
            }

            node.validate() shouldBe listOf(Violation("amount", "11.00 is above 10.00"))
        }
    }

    @Nested
    @DisplayName("When greaterThan is called")
    inner class GreaterThan {
        @Test
        fun `given an Int equal to the reference should report it`() {
            val node = ValidationNode("quantity", 10).apply { greaterThan(10) }

            node.validate() shouldBe listOf(Violation("quantity", "must be greater than 10"))
        }

        @Test
        fun `given an Int above the reference should accept it`() {
            val node = ValidationNode("quantity", 11).apply { greaterThan(10) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for an Int should skip the check`() {
            val reference: Int? = null

            val node = ValidationNode("quantity", 10).apply { greaterThan(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for an Int should build it from the value and the reference`() {
            val node = ValidationNode("quantity", 10).apply {
                greaterThan(10) { other -> "$this is not greater than $other" }
            }

            node.validate() shouldBe listOf(Violation("quantity", "10 is not greater than 10"))
        }

        @Test
        fun `given a Long equal to the reference should report it`() {
            val node = ValidationNode("quantity", 10L).apply { greaterThan(10L) }

            node.validate() shouldBe listOf(Violation("quantity", "must be greater than 10"))
        }

        @Test
        fun `given a Long above the reference should accept it`() {
            val node = ValidationNode("quantity", 11L).apply { greaterThan(10L) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for a Long should skip the check`() {
            val reference: Long? = null

            val node = ValidationNode("quantity", 10L).apply { greaterThan(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a Long should build it from the value and the reference`() {
            val node = ValidationNode("quantity", 10L).apply {
                greaterThan(10L) { other -> "$this is not greater than $other" }
            }

            node.validate() shouldBe listOf(Violation("quantity", "10 is not greater than 10"))
        }

        @Test
        fun `given a Short equal to the reference should report it`() {
            val node = ValidationNode("quantity", 10.toShort()).apply { greaterThan(10.toShort()) }

            node.validate() shouldBe listOf(Violation("quantity", "must be greater than 10"))
        }

        @Test
        fun `given a Short above the reference should accept it`() {
            val node = ValidationNode("quantity", 11.toShort()).apply { greaterThan(10.toShort()) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for a Short should skip the check`() {
            val reference: Short? = null

            val node = ValidationNode("quantity", 10.toShort()).apply { greaterThan(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a Short should build it from the value and the reference`() {
            val node = ValidationNode("quantity", 10.toShort()).apply {
                greaterThan(10.toShort()) { other -> "$this is not greater than $other" }
            }

            node.validate() shouldBe listOf(Violation("quantity", "10 is not greater than 10"))
        }

        @Test
        fun `given a Byte equal to the reference should report it`() {
            val node = ValidationNode("quantity", 10.toByte()).apply { greaterThan(10.toByte()) }

            node.validate() shouldBe listOf(Violation("quantity", "must be greater than 10"))
        }

        @Test
        fun `given a Byte above the reference should accept it`() {
            val node = ValidationNode("quantity", 11.toByte()).apply { greaterThan(10.toByte()) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for a Byte should skip the check`() {
            val reference: Byte? = null

            val node = ValidationNode("quantity", 10.toByte()).apply { greaterThan(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a Byte should build it from the value and the reference`() {
            val node = ValidationNode("quantity", 10.toByte()).apply {
                greaterThan(10.toByte()) { other -> "$this is not greater than $other" }
            }

            node.validate() shouldBe listOf(Violation("quantity", "10 is not greater than 10"))
        }

        @Test
        fun `given a Double equal to the reference should report it`() {
            val node = ValidationNode("rate", 10.0).apply { greaterThan(10.0) }

            node.validate() shouldBe listOf(Violation("rate", "must be greater than 10.0"))
        }

        @Test
        fun `given a Double above the reference should accept it`() {
            val node = ValidationNode("rate", 11.0).apply { greaterThan(10.0) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for a Double should skip the check`() {
            val reference: Double? = null

            val node = ValidationNode("rate", 10.0).apply { greaterThan(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a Double should build it from the value and the reference`() {
            val node = ValidationNode("rate", 10.0).apply {
                greaterThan(10.0) { other -> "$this is not greater than $other" }
            }

            node.validate() shouldBe listOf(Violation("rate", "10.0 is not greater than 10.0"))
        }

        @Test
        fun `given a Float equal to the reference should report it`() {
            val node = ValidationNode("rate", 10f).apply { greaterThan(10f) }

            node.validate() shouldBe listOf(Violation("rate", "must be greater than 10.0"))
        }

        @Test
        fun `given a Float above the reference should accept it`() {
            val node = ValidationNode("rate", 11f).apply { greaterThan(10f) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for a Float should skip the check`() {
            val reference: Float? = null

            val node = ValidationNode("rate", 10f).apply { greaterThan(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a Float should build it from the value and the reference`() {
            val node = ValidationNode("rate", 10f).apply {
                greaterThan(10f) { other -> "$this is not greater than $other" }
            }

            node.validate() shouldBe listOf(Violation("rate", "10.0 is not greater than 10.0"))
        }

        @Test
        fun `given a BigInteger equal to the reference should report it`() {
            val node = ValidationNode("supply", BigInteger("10")).apply { greaterThan(BigInteger("10")) }

            node.validate() shouldBe listOf(Violation("supply", "must be greater than 10"))
        }

        @Test
        fun `given a BigInteger above the reference should accept it`() {
            val node = ValidationNode("supply", BigInteger("11")).apply { greaterThan(BigInteger("10")) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for a BigInteger should skip the check`() {
            val reference: BigInteger? = null

            val node = ValidationNode("supply", BigInteger("10")).apply { greaterThan(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a BigInteger should build it from the value and the reference`() {
            val node = ValidationNode("supply", BigInteger("10")).apply {
                greaterThan(BigInteger("10")) { other -> "$this is not greater than $other" }
            }

            node.validate() shouldBe listOf(Violation("supply", "10 is not greater than 10"))
        }

        @Test
        fun `given a BigDecimal equal to the reference should report it`() {
            val node = ValidationNode("amount", BigDecimal("10.00")).apply { greaterThan(BigDecimal("10.00")) }

            node.validate() shouldBe listOf(Violation("amount", "must be greater than 10.00"))
        }

        @Test
        fun `given a BigDecimal above the reference should accept it`() {
            val node = ValidationNode("amount", BigDecimal("11.00")).apply { greaterThan(BigDecimal("10.00")) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for a BigDecimal should skip the check`() {
            val reference: BigDecimal? = null

            val node = ValidationNode("amount", BigDecimal("10.00")).apply { greaterThan(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a BigDecimal should build it from the value and the reference`() {
            val node = ValidationNode("amount", BigDecimal("10.00")).apply {
                greaterThan(BigDecimal("10.00")) { other -> "$this is not greater than $other" }
            }

            node.validate() shouldBe listOf(Violation("amount", "10.00 is not greater than 10.00"))
        }
    }

    @Nested
    @DisplayName("When lessThan is called")
    inner class LessThan {
        @Test
        fun `given an Int equal to the reference should report it`() {
            val node = ValidationNode("quantity", 10).apply { lessThan(10) }

            node.validate() shouldBe listOf(Violation("quantity", "must be less than 10"))
        }

        @Test
        fun `given an Int below the reference should accept it`() {
            val node = ValidationNode("quantity", 9).apply { lessThan(10) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for an Int should skip the check`() {
            val reference: Int? = null

            val node = ValidationNode("quantity", 10).apply { lessThan(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for an Int should build it from the value and the reference`() {
            val node = ValidationNode("quantity", 10).apply {
                lessThan(10) { other -> "$this is not less than $other" }
            }

            node.validate() shouldBe listOf(Violation("quantity", "10 is not less than 10"))
        }

        @Test
        fun `given a Long equal to the reference should report it`() {
            val node = ValidationNode("quantity", 10L).apply { lessThan(10L) }

            node.validate() shouldBe listOf(Violation("quantity", "must be less than 10"))
        }

        @Test
        fun `given a Long below the reference should accept it`() {
            val node = ValidationNode("quantity", 9L).apply { lessThan(10L) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for a Long should skip the check`() {
            val reference: Long? = null

            val node = ValidationNode("quantity", 10L).apply { lessThan(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a Long should build it from the value and the reference`() {
            val node = ValidationNode("quantity", 10L).apply {
                lessThan(10L) { other -> "$this is not less than $other" }
            }

            node.validate() shouldBe listOf(Violation("quantity", "10 is not less than 10"))
        }

        @Test
        fun `given a Short equal to the reference should report it`() {
            val node = ValidationNode("quantity", 10.toShort()).apply { lessThan(10.toShort()) }

            node.validate() shouldBe listOf(Violation("quantity", "must be less than 10"))
        }

        @Test
        fun `given a Short below the reference should accept it`() {
            val node = ValidationNode("quantity", 9.toShort()).apply { lessThan(10.toShort()) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for a Short should skip the check`() {
            val reference: Short? = null

            val node = ValidationNode("quantity", 10.toShort()).apply { lessThan(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a Short should build it from the value and the reference`() {
            val node = ValidationNode("quantity", 10.toShort()).apply {
                lessThan(10.toShort()) { other -> "$this is not less than $other" }
            }

            node.validate() shouldBe listOf(Violation("quantity", "10 is not less than 10"))
        }

        @Test
        fun `given a Byte equal to the reference should report it`() {
            val node = ValidationNode("quantity", 10.toByte()).apply { lessThan(10.toByte()) }

            node.validate() shouldBe listOf(Violation("quantity", "must be less than 10"))
        }

        @Test
        fun `given a Byte below the reference should accept it`() {
            val node = ValidationNode("quantity", 9.toByte()).apply { lessThan(10.toByte()) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for a Byte should skip the check`() {
            val reference: Byte? = null

            val node = ValidationNode("quantity", 10.toByte()).apply { lessThan(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a Byte should build it from the value and the reference`() {
            val node = ValidationNode("quantity", 10.toByte()).apply {
                lessThan(10.toByte()) { other -> "$this is not less than $other" }
            }

            node.validate() shouldBe listOf(Violation("quantity", "10 is not less than 10"))
        }

        @Test
        fun `given a Double equal to the reference should report it`() {
            val node = ValidationNode("rate", 10.0).apply { lessThan(10.0) }

            node.validate() shouldBe listOf(Violation("rate", "must be less than 10.0"))
        }

        @Test
        fun `given a Double below the reference should accept it`() {
            val node = ValidationNode("rate", 9.0).apply { lessThan(10.0) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for a Double should skip the check`() {
            val reference: Double? = null

            val node = ValidationNode("rate", 10.0).apply { lessThan(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a Double should build it from the value and the reference`() {
            val node = ValidationNode("rate", 10.0).apply {
                lessThan(10.0) { other -> "$this is not less than $other" }
            }

            node.validate() shouldBe listOf(Violation("rate", "10.0 is not less than 10.0"))
        }

        @Test
        fun `given a Float equal to the reference should report it`() {
            val node = ValidationNode("rate", 10f).apply { lessThan(10f) }

            node.validate() shouldBe listOf(Violation("rate", "must be less than 10.0"))
        }

        @Test
        fun `given a Float below the reference should accept it`() {
            val node = ValidationNode("rate", 9f).apply { lessThan(10f) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for a Float should skip the check`() {
            val reference: Float? = null

            val node = ValidationNode("rate", 10f).apply { lessThan(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a Float should build it from the value and the reference`() {
            val node = ValidationNode("rate", 10f).apply { lessThan(10f) { other -> "$this is not less than $other" } }

            node.validate() shouldBe listOf(Violation("rate", "10.0 is not less than 10.0"))
        }

        @Test
        fun `given a BigInteger equal to the reference should report it`() {
            val node = ValidationNode("supply", BigInteger("10")).apply { lessThan(BigInteger("10")) }

            node.validate() shouldBe listOf(Violation("supply", "must be less than 10"))
        }

        @Test
        fun `given a BigInteger below the reference should accept it`() {
            val node = ValidationNode("supply", BigInteger("9")).apply { lessThan(BigInteger("10")) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for a BigInteger should skip the check`() {
            val reference: BigInteger? = null

            val node = ValidationNode("supply", BigInteger("10")).apply { lessThan(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a BigInteger should build it from the value and the reference`() {
            val node = ValidationNode("supply", BigInteger("10")).apply {
                lessThan(BigInteger("10")) { other -> "$this is not less than $other" }
            }

            node.validate() shouldBe listOf(Violation("supply", "10 is not less than 10"))
        }

        @Test
        fun `given a BigDecimal equal to the reference should report it`() {
            val node = ValidationNode("amount", BigDecimal("10.00")).apply { lessThan(BigDecimal("10.00")) }

            node.validate() shouldBe listOf(Violation("amount", "must be less than 10.00"))
        }

        @Test
        fun `given a BigDecimal below the reference should accept it`() {
            val node = ValidationNode("amount", BigDecimal("9.00")).apply { lessThan(BigDecimal("10.00")) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for a BigDecimal should skip the check`() {
            val reference: BigDecimal? = null

            val node = ValidationNode("amount", BigDecimal("10.00")).apply { lessThan(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a BigDecimal should build it from the value and the reference`() {
            val node = ValidationNode("amount", BigDecimal("10.00")).apply {
                lessThan(BigDecimal("10.00")) { other -> "$this is not less than $other" }
            }

            node.validate() shouldBe listOf(Violation("amount", "10.00 is not less than 10.00"))
        }
    }

    @Nested
    @DisplayName("When between is called")
    inner class Between {
        @Test
        fun `given an Int below the range should report it`() {
            val node = ValidationNode("quantity", 8).apply { between(9, 11) }

            node.validate() shouldBe listOf(Violation("quantity", "must be between 9 and 11"))
        }

        @Test
        fun `given an Int above the range should report it`() {
            val node = ValidationNode("quantity", 12).apply { between(9, 11) }

            node.validate() shouldBe listOf(Violation("quantity", "must be between 9 and 11"))
        }

        @Test
        fun `given an Int at the lower limit should accept it`() {
            val node = ValidationNode("quantity", 9).apply { between(9, 11) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given an Int inside the range should accept it`() {
            val node = ValidationNode("quantity", 10).apply { between(9, 11) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given an Int at the upper limit should accept it`() {
            val node = ValidationNode("quantity", 11).apply { between(9, 11) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no minimum for an Int should skip the check`() {
            val min: Int? = null

            val node = ValidationNode("quantity", 12).apply { between(min, 11) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for an Int should build it from the value and the limits`() {
            val node = ValidationNode("quantity", 12).apply {
                between(9, 11) { min, max -> "$this is not from $min to $max" }
            }

            node.validate() shouldBe listOf(Violation("quantity", "12 is not from 9 to 11"))
        }

        @Test
        fun `given a Long below the range should report it`() {
            val node = ValidationNode("quantity", 8L).apply { between(9L, 11L) }

            node.validate() shouldBe listOf(Violation("quantity", "must be between 9 and 11"))
        }

        @Test
        fun `given a Long above the range should report it`() {
            val node = ValidationNode("quantity", 12L).apply { between(9L, 11L) }

            node.validate() shouldBe listOf(Violation("quantity", "must be between 9 and 11"))
        }

        @Test
        fun `given a Long at the lower limit should accept it`() {
            val node = ValidationNode("quantity", 9L).apply { between(9L, 11L) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a Long inside the range should accept it`() {
            val node = ValidationNode("quantity", 10L).apply { between(9L, 11L) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a Long at the upper limit should accept it`() {
            val node = ValidationNode("quantity", 11L).apply { between(9L, 11L) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no minimum for a Long should skip the check`() {
            val min: Long? = null

            val node = ValidationNode("quantity", 12L).apply { between(min, 11L) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a Long should build it from the value and the limits`() {
            val node = ValidationNode("quantity", 12L).apply {
                between(9L, 11L) { min, max -> "$this is not from $min to $max" }
            }

            node.validate() shouldBe listOf(Violation("quantity", "12 is not from 9 to 11"))
        }

        @Test
        fun `given a Short below the range should report it`() {
            val node = ValidationNode("quantity", 8.toShort()).apply { between(9.toShort(), 11.toShort()) }

            node.validate() shouldBe listOf(Violation("quantity", "must be between 9 and 11"))
        }

        @Test
        fun `given a Short above the range should report it`() {
            val node = ValidationNode("quantity", 12.toShort()).apply { between(9.toShort(), 11.toShort()) }

            node.validate() shouldBe listOf(Violation("quantity", "must be between 9 and 11"))
        }

        @Test
        fun `given a Short at the lower limit should accept it`() {
            val node = ValidationNode("quantity", 9.toShort()).apply { between(9.toShort(), 11.toShort()) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a Short inside the range should accept it`() {
            val node = ValidationNode("quantity", 10.toShort()).apply { between(9.toShort(), 11.toShort()) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a Short at the upper limit should accept it`() {
            val node = ValidationNode("quantity", 11.toShort()).apply { between(9.toShort(), 11.toShort()) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no minimum for a Short should skip the check`() {
            val min: Short? = null

            val node = ValidationNode("quantity", 12.toShort()).apply { between(min, 11.toShort()) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a Short should build it from the value and the limits`() {
            val node = ValidationNode("quantity", 12.toShort()).apply {
                between(9.toShort(), 11.toShort()) { min, max -> "$this is not from $min to $max" }
            }

            node.validate() shouldBe listOf(Violation("quantity", "12 is not from 9 to 11"))
        }

        @Test
        fun `given a Byte below the range should report it`() {
            val node = ValidationNode("quantity", 8.toByte()).apply { between(9.toByte(), 11.toByte()) }

            node.validate() shouldBe listOf(Violation("quantity", "must be between 9 and 11"))
        }

        @Test
        fun `given a Byte above the range should report it`() {
            val node = ValidationNode("quantity", 12.toByte()).apply { between(9.toByte(), 11.toByte()) }

            node.validate() shouldBe listOf(Violation("quantity", "must be between 9 and 11"))
        }

        @Test
        fun `given a Byte at the lower limit should accept it`() {
            val node = ValidationNode("quantity", 9.toByte()).apply { between(9.toByte(), 11.toByte()) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a Byte inside the range should accept it`() {
            val node = ValidationNode("quantity", 10.toByte()).apply { between(9.toByte(), 11.toByte()) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a Byte at the upper limit should accept it`() {
            val node = ValidationNode("quantity", 11.toByte()).apply { between(9.toByte(), 11.toByte()) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no minimum for a Byte should skip the check`() {
            val min: Byte? = null

            val node = ValidationNode("quantity", 12.toByte()).apply { between(min, 11.toByte()) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a Byte should build it from the value and the limits`() {
            val node = ValidationNode("quantity", 12.toByte()).apply {
                between(9.toByte(), 11.toByte()) { min, max -> "$this is not from $min to $max" }
            }

            node.validate() shouldBe listOf(Violation("quantity", "12 is not from 9 to 11"))
        }

        @Test
        fun `given a Double below the range should report it`() {
            val node = ValidationNode("rate", 8.0).apply { between(9.0, 11.0) }

            node.validate() shouldBe listOf(Violation("rate", "must be between 9.0 and 11.0"))
        }

        @Test
        fun `given a Double above the range should report it`() {
            val node = ValidationNode("rate", 12.0).apply { between(9.0, 11.0) }

            node.validate() shouldBe listOf(Violation("rate", "must be between 9.0 and 11.0"))
        }

        @Test
        fun `given a Double at the lower limit should accept it`() {
            val node = ValidationNode("rate", 9.0).apply { between(9.0, 11.0) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a Double inside the range should accept it`() {
            val node = ValidationNode("rate", 10.0).apply { between(9.0, 11.0) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a Double at the upper limit should accept it`() {
            val node = ValidationNode("rate", 11.0).apply { between(9.0, 11.0) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no minimum for a Double should skip the check`() {
            val min: Double? = null

            val node = ValidationNode("rate", 12.0).apply { between(min, 11.0) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a Double should build it from the value and the limits`() {
            val node = ValidationNode("rate", 12.0).apply {
                between(9.0, 11.0) { min, max -> "$this is not from $min to $max" }
            }

            node.validate() shouldBe listOf(Violation("rate", "12.0 is not from 9.0 to 11.0"))
        }

        @Test
        fun `given a Float below the range should report it`() {
            val node = ValidationNode("rate", 8f).apply { between(9f, 11f) }

            node.validate() shouldBe listOf(Violation("rate", "must be between 9.0 and 11.0"))
        }

        @Test
        fun `given a Float above the range should report it`() {
            val node = ValidationNode("rate", 12f).apply { between(9f, 11f) }

            node.validate() shouldBe listOf(Violation("rate", "must be between 9.0 and 11.0"))
        }

        @Test
        fun `given a Float at the lower limit should accept it`() {
            val node = ValidationNode("rate", 9f).apply { between(9f, 11f) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a Float inside the range should accept it`() {
            val node = ValidationNode("rate", 10f).apply { between(9f, 11f) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a Float at the upper limit should accept it`() {
            val node = ValidationNode("rate", 11f).apply { between(9f, 11f) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no minimum for a Float should skip the check`() {
            val min: Float? = null

            val node = ValidationNode("rate", 12f).apply { between(min, 11f) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a Float should build it from the value and the limits`() {
            val node = ValidationNode("rate", 12f).apply {
                between(9f, 11f) { min, max -> "$this is not from $min to $max" }
            }

            node.validate() shouldBe listOf(Violation("rate", "12.0 is not from 9.0 to 11.0"))
        }

        @Test
        fun `given a BigInteger below the range should report it`() {
            val node = ValidationNode("supply", BigInteger("8")).apply { between(BigInteger("9"), BigInteger("11")) }

            node.validate() shouldBe listOf(Violation("supply", "must be between 9 and 11"))
        }

        @Test
        fun `given a BigInteger above the range should report it`() {
            val node = ValidationNode("supply", BigInteger("12")).apply { between(BigInteger("9"), BigInteger("11")) }

            node.validate() shouldBe listOf(Violation("supply", "must be between 9 and 11"))
        }

        @Test
        fun `given a BigInteger at the lower limit should accept it`() {
            val node = ValidationNode("supply", BigInteger("9")).apply { between(BigInteger("9"), BigInteger("11")) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a BigInteger inside the range should accept it`() {
            val node = ValidationNode("supply", BigInteger("10")).apply { between(BigInteger("9"), BigInteger("11")) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a BigInteger at the upper limit should accept it`() {
            val node = ValidationNode("supply", BigInteger("11")).apply { between(BigInteger("9"), BigInteger("11")) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no minimum for a BigInteger should skip the check`() {
            val min: BigInteger? = null

            val node = ValidationNode("supply", BigInteger("12")).apply { between(min, BigInteger("11")) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a BigInteger should build it from the value and the limits`() {
            val node = ValidationNode("supply", BigInteger("12")).apply {
                between(BigInteger("9"), BigInteger("11")) { min, max -> "$this is not from $min to $max" }
            }

            node.validate() shouldBe listOf(Violation("supply", "12 is not from 9 to 11"))
        }

        @Test
        fun `given a BigDecimal below the range should report it`() {
            val node = ValidationNode("amount", BigDecimal("8.00")).apply {
                between(BigDecimal("9.00"), BigDecimal("11.00"))
            }

            node.validate() shouldBe listOf(Violation("amount", "must be between 9.00 and 11.00"))
        }

        @Test
        fun `given a BigDecimal above the range should report it`() {
            val node = ValidationNode("amount", BigDecimal("12.00")).apply {
                between(BigDecimal("9.00"), BigDecimal("11.00"))
            }

            node.validate() shouldBe listOf(Violation("amount", "must be between 9.00 and 11.00"))
        }

        @Test
        fun `given a BigDecimal at the lower limit should accept it`() {
            val node = ValidationNode("amount", BigDecimal("9.00")).apply {
                between(BigDecimal("9.00"), BigDecimal("11.00"))
            }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a BigDecimal inside the range should accept it`() {
            val node = ValidationNode("amount", BigDecimal("10.00")).apply {
                between(BigDecimal("9.00"), BigDecimal("11.00"))
            }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a BigDecimal at the upper limit should accept it`() {
            val node = ValidationNode("amount", BigDecimal("11.00")).apply {
                between(BigDecimal("9.00"), BigDecimal("11.00"))
            }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no minimum for a BigDecimal should skip the check`() {
            val min: BigDecimal? = null

            val node = ValidationNode("amount", BigDecimal("12.00")).apply { between(min, BigDecimal("11.00")) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a BigDecimal should build it from the value and the limits`() {
            val node = ValidationNode("amount", BigDecimal("12.00")).apply {
                between(BigDecimal("9.00"), BigDecimal("11.00")) { min, max -> "$this is not from $min to $max" }
            }

            node.validate() shouldBe listOf(Violation("amount", "12.00 is not from 9.00 to 11.00"))
        }
    }

    @Nested
    @DisplayName("When positive is called")
    inner class Positive {
        @ParameterizedTest
        @ValueSource(ints = [0, -1])
        fun `given an Int quantity that is not positive should report it`(quantity: Int) {
            val node = ValidationNode("quantity", quantity).apply { positive() }

            node.validate() shouldBe listOf(Violation("quantity", "must be positive"))
        }

        @ParameterizedTest
        @ValueSource(ints = [1, 10])
        fun `given a positive Int quantity should accept it`(quantity: Int) {
            val node = ValidationNode("quantity", quantity).apply { positive() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(longs = [0, -1])
        fun `given a Long quantity that is not positive should report it`(quantity: Long) {
            val node = ValidationNode("quantity", quantity).apply { positive() }

            node.validate() shouldBe listOf(Violation("quantity", "must be positive"))
        }

        @ParameterizedTest
        @ValueSource(longs = [1, 10])
        fun `given a positive Long quantity should accept it`(quantity: Long) {
            val node = ValidationNode("quantity", quantity).apply { positive() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(shorts = [0, -1])
        fun `given a Short quantity that is not positive should report it`(quantity: Short) {
            val node = ValidationNode("quantity", quantity).apply { positive() }

            node.validate() shouldBe listOf(Violation("quantity", "must be positive"))
        }

        @ParameterizedTest
        @ValueSource(shorts = [1, 10])
        fun `given a positive Short quantity should accept it`(quantity: Short) {
            val node = ValidationNode("quantity", quantity).apply { positive() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(bytes = [0, -1])
        fun `given a Byte quantity that is not positive should report it`(quantity: Byte) {
            val node = ValidationNode("quantity", quantity).apply { positive() }

            node.validate() shouldBe listOf(Violation("quantity", "must be positive"))
        }

        @ParameterizedTest
        @ValueSource(bytes = [1, 10])
        fun `given a positive Byte quantity should accept it`(quantity: Byte) {
            val node = ValidationNode("quantity", quantity).apply { positive() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(doubles = [0.0, -1.0])
        fun `given a Double quantity that is not positive should report it`(quantity: Double) {
            val node = ValidationNode("quantity", quantity).apply { positive() }

            node.validate() shouldBe listOf(Violation("quantity", "must be positive"))
        }

        @ParameterizedTest
        @ValueSource(doubles = [1.0, 10.0])
        fun `given a positive Double quantity should accept it`(quantity: Double) {
            val node = ValidationNode("quantity", quantity).apply { positive() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(floats = [0f, -1f])
        fun `given a Float quantity that is not positive should report it`(quantity: Float) {
            val node = ValidationNode("quantity", quantity).apply { positive() }

            node.validate() shouldBe listOf(Violation("quantity", "must be positive"))
        }

        @ParameterizedTest
        @ValueSource(floats = [1f, 10f])
        fun `given a positive Float quantity should accept it`(quantity: Float) {
            val node = ValidationNode("quantity", quantity).apply { positive() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["0", "-1"])
        fun `given a BigInteger quantity that is not positive should report it`(quantity: String) {
            val node = ValidationNode("quantity", BigInteger(quantity)).apply { positive() }

            node.validate() shouldBe listOf(Violation("quantity", "must be positive"))
        }

        @ParameterizedTest
        @ValueSource(strings = ["1", "10"])
        fun `given a positive BigInteger quantity should accept it`(quantity: String) {
            val node = ValidationNode("quantity", BigInteger(quantity)).apply { positive() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["0.00", "-1.00"])
        fun `given a BigDecimal quantity that is not positive should report it`(quantity: String) {
            val node = ValidationNode("quantity", BigDecimal(quantity)).apply { positive() }

            node.validate() shouldBe listOf(Violation("quantity", "must be positive"))
        }

        @ParameterizedTest
        @ValueSource(strings = ["1.00", "10.00"])
        fun `given a positive BigDecimal quantity should accept it`(quantity: String) {
            val node = ValidationNode("quantity", BigDecimal(quantity)).apply { positive() }

            node.validate() shouldBe emptyList()
        }
    }

    @Nested
    @DisplayName("When negative is called")
    inner class Negative {
        @ParameterizedTest
        @ValueSource(ints = [0, 1])
        fun `given an Int adjustment that is not negative should report it`(adjustment: Int) {
            val node = ValidationNode("adjustment", adjustment).apply { negative() }

            node.validate() shouldBe listOf(Violation("adjustment", "must be negative"))
        }

        @ParameterizedTest
        @ValueSource(ints = [-1, -10])
        fun `given a negative Int adjustment should accept it`(adjustment: Int) {
            val node = ValidationNode("adjustment", adjustment).apply { negative() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(longs = [0, 1])
        fun `given a Long adjustment that is not negative should report it`(adjustment: Long) {
            val node = ValidationNode("adjustment", adjustment).apply { negative() }

            node.validate() shouldBe listOf(Violation("adjustment", "must be negative"))
        }

        @ParameterizedTest
        @ValueSource(longs = [-1, -10])
        fun `given a negative Long adjustment should accept it`(adjustment: Long) {
            val node = ValidationNode("adjustment", adjustment).apply { negative() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(shorts = [0, 1])
        fun `given a Short adjustment that is not negative should report it`(adjustment: Short) {
            val node = ValidationNode("adjustment", adjustment).apply { negative() }

            node.validate() shouldBe listOf(Violation("adjustment", "must be negative"))
        }

        @ParameterizedTest
        @ValueSource(shorts = [-1, -10])
        fun `given a negative Short adjustment should accept it`(adjustment: Short) {
            val node = ValidationNode("adjustment", adjustment).apply { negative() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(bytes = [0, 1])
        fun `given a Byte adjustment that is not negative should report it`(adjustment: Byte) {
            val node = ValidationNode("adjustment", adjustment).apply { negative() }

            node.validate() shouldBe listOf(Violation("adjustment", "must be negative"))
        }

        @ParameterizedTest
        @ValueSource(bytes = [-1, -10])
        fun `given a negative Byte adjustment should accept it`(adjustment: Byte) {
            val node = ValidationNode("adjustment", adjustment).apply { negative() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(doubles = [0.0, 1.0])
        fun `given a Double adjustment that is not negative should report it`(adjustment: Double) {
            val node = ValidationNode("adjustment", adjustment).apply { negative() }

            node.validate() shouldBe listOf(Violation("adjustment", "must be negative"))
        }

        @ParameterizedTest
        @ValueSource(doubles = [-1.0, -10.0])
        fun `given a negative Double adjustment should accept it`(adjustment: Double) {
            val node = ValidationNode("adjustment", adjustment).apply { negative() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(floats = [0f, 1f])
        fun `given a Float adjustment that is not negative should report it`(adjustment: Float) {
            val node = ValidationNode("adjustment", adjustment).apply { negative() }

            node.validate() shouldBe listOf(Violation("adjustment", "must be negative"))
        }

        @ParameterizedTest
        @ValueSource(floats = [-1f, -10f])
        fun `given a negative Float adjustment should accept it`(adjustment: Float) {
            val node = ValidationNode("adjustment", adjustment).apply { negative() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["0", "1"])
        fun `given a BigInteger adjustment that is not negative should report it`(adjustment: String) {
            val node = ValidationNode("adjustment", BigInteger(adjustment)).apply { negative() }

            node.validate() shouldBe listOf(Violation("adjustment", "must be negative"))
        }

        @ParameterizedTest
        @ValueSource(strings = ["-1", "-10"])
        fun `given a negative BigInteger adjustment should accept it`(adjustment: String) {
            val node = ValidationNode("adjustment", BigInteger(adjustment)).apply { negative() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["0.00", "1.00"])
        fun `given a BigDecimal adjustment that is not negative should report it`(adjustment: String) {
            val node = ValidationNode("adjustment", BigDecimal(adjustment)).apply { negative() }

            node.validate() shouldBe listOf(Violation("adjustment", "must be negative"))
        }

        @ParameterizedTest
        @ValueSource(strings = ["-1.00", "-10.00"])
        fun `given a negative BigDecimal adjustment should accept it`(adjustment: String) {
            val node = ValidationNode("adjustment", BigDecimal(adjustment)).apply { negative() }

            node.validate() shouldBe emptyList()
        }
    }

    @Nested
    @DisplayName("When positiveOrZero is called")
    inner class PositiveOrZero {
        @ParameterizedTest
        @ValueSource(ints = [-1, -10])
        fun `given an Int balance below zero should report it`(balance: Int) {
            val node = ValidationNode("balance", balance).apply { positiveOrZero() }

            node.validate() shouldBe listOf(Violation("balance", "must be positive or zero"))
        }

        @ParameterizedTest
        @ValueSource(ints = [0, 1])
        fun `given a zero or positive Int balance should accept it`(balance: Int) {
            val node = ValidationNode("balance", balance).apply { positiveOrZero() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(longs = [-1, -10])
        fun `given a Long balance below zero should report it`(balance: Long) {
            val node = ValidationNode("balance", balance).apply { positiveOrZero() }

            node.validate() shouldBe listOf(Violation("balance", "must be positive or zero"))
        }

        @ParameterizedTest
        @ValueSource(longs = [0, 1])
        fun `given a zero or positive Long balance should accept it`(balance: Long) {
            val node = ValidationNode("balance", balance).apply { positiveOrZero() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(shorts = [-1, -10])
        fun `given a Short balance below zero should report it`(balance: Short) {
            val node = ValidationNode("balance", balance).apply { positiveOrZero() }

            node.validate() shouldBe listOf(Violation("balance", "must be positive or zero"))
        }

        @ParameterizedTest
        @ValueSource(shorts = [0, 1])
        fun `given a zero or positive Short balance should accept it`(balance: Short) {
            val node = ValidationNode("balance", balance).apply { positiveOrZero() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(bytes = [-1, -10])
        fun `given a Byte balance below zero should report it`(balance: Byte) {
            val node = ValidationNode("balance", balance).apply { positiveOrZero() }

            node.validate() shouldBe listOf(Violation("balance", "must be positive or zero"))
        }

        @ParameterizedTest
        @ValueSource(bytes = [0, 1])
        fun `given a zero or positive Byte balance should accept it`(balance: Byte) {
            val node = ValidationNode("balance", balance).apply { positiveOrZero() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(doubles = [-1.0, -10.0])
        fun `given a Double balance below zero should report it`(balance: Double) {
            val node = ValidationNode("balance", balance).apply { positiveOrZero() }

            node.validate() shouldBe listOf(Violation("balance", "must be positive or zero"))
        }

        @ParameterizedTest
        @ValueSource(doubles = [0.0, 1.0])
        fun `given a zero or positive Double balance should accept it`(balance: Double) {
            val node = ValidationNode("balance", balance).apply { positiveOrZero() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(floats = [-1f, -10f])
        fun `given a Float balance below zero should report it`(balance: Float) {
            val node = ValidationNode("balance", balance).apply { positiveOrZero() }

            node.validate() shouldBe listOf(Violation("balance", "must be positive or zero"))
        }

        @ParameterizedTest
        @ValueSource(floats = [0f, 1f])
        fun `given a zero or positive Float balance should accept it`(balance: Float) {
            val node = ValidationNode("balance", balance).apply { positiveOrZero() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["-1", "-10"])
        fun `given a BigInteger balance below zero should report it`(balance: String) {
            val node = ValidationNode("balance", BigInteger(balance)).apply { positiveOrZero() }

            node.validate() shouldBe listOf(Violation("balance", "must be positive or zero"))
        }

        @ParameterizedTest
        @ValueSource(strings = ["0", "1"])
        fun `given a zero or positive BigInteger balance should accept it`(balance: String) {
            val node = ValidationNode("balance", BigInteger(balance)).apply { positiveOrZero() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["-1.00", "-10.00"])
        fun `given a BigDecimal balance below zero should report it`(balance: String) {
            val node = ValidationNode("balance", BigDecimal(balance)).apply { positiveOrZero() }

            node.validate() shouldBe listOf(Violation("balance", "must be positive or zero"))
        }

        @ParameterizedTest
        @ValueSource(strings = ["0.00", "1.00"])
        fun `given a zero or positive BigDecimal balance should accept it`(balance: String) {
            val node = ValidationNode("balance", BigDecimal(balance)).apply { positiveOrZero() }

            node.validate() shouldBe emptyList()
        }
    }

    @Nested
    @DisplayName("When negativeOrZero is called")
    inner class NegativeOrZero {
        @ParameterizedTest
        @ValueSource(ints = [1, 10])
        fun `given an Int debit above zero should report it`(debit: Int) {
            val node = ValidationNode("debit", debit).apply { negativeOrZero() }

            node.validate() shouldBe listOf(Violation("debit", "must be negative or zero"))
        }

        @ParameterizedTest
        @ValueSource(ints = [0, -1])
        fun `given a zero or negative Int debit should accept it`(debit: Int) {
            val node = ValidationNode("debit", debit).apply { negativeOrZero() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(longs = [1, 10])
        fun `given a Long debit above zero should report it`(debit: Long) {
            val node = ValidationNode("debit", debit).apply { negativeOrZero() }

            node.validate() shouldBe listOf(Violation("debit", "must be negative or zero"))
        }

        @ParameterizedTest
        @ValueSource(longs = [0, -1])
        fun `given a zero or negative Long debit should accept it`(debit: Long) {
            val node = ValidationNode("debit", debit).apply { negativeOrZero() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(shorts = [1, 10])
        fun `given a Short debit above zero should report it`(debit: Short) {
            val node = ValidationNode("debit", debit).apply { negativeOrZero() }

            node.validate() shouldBe listOf(Violation("debit", "must be negative or zero"))
        }

        @ParameterizedTest
        @ValueSource(shorts = [0, -1])
        fun `given a zero or negative Short debit should accept it`(debit: Short) {
            val node = ValidationNode("debit", debit).apply { negativeOrZero() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(bytes = [1, 10])
        fun `given a Byte debit above zero should report it`(debit: Byte) {
            val node = ValidationNode("debit", debit).apply { negativeOrZero() }

            node.validate() shouldBe listOf(Violation("debit", "must be negative or zero"))
        }

        @ParameterizedTest
        @ValueSource(bytes = [0, -1])
        fun `given a zero or negative Byte debit should accept it`(debit: Byte) {
            val node = ValidationNode("debit", debit).apply { negativeOrZero() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(doubles = [1.0, 10.0])
        fun `given a Double debit above zero should report it`(debit: Double) {
            val node = ValidationNode("debit", debit).apply { negativeOrZero() }

            node.validate() shouldBe listOf(Violation("debit", "must be negative or zero"))
        }

        @ParameterizedTest
        @ValueSource(doubles = [0.0, -1.0])
        fun `given a zero or negative Double debit should accept it`(debit: Double) {
            val node = ValidationNode("debit", debit).apply { negativeOrZero() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(floats = [1f, 10f])
        fun `given a Float debit above zero should report it`(debit: Float) {
            val node = ValidationNode("debit", debit).apply { negativeOrZero() }

            node.validate() shouldBe listOf(Violation("debit", "must be negative or zero"))
        }

        @ParameterizedTest
        @ValueSource(floats = [0f, -1f])
        fun `given a zero or negative Float debit should accept it`(debit: Float) {
            val node = ValidationNode("debit", debit).apply { negativeOrZero() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["1", "10"])
        fun `given a BigInteger debit above zero should report it`(debit: String) {
            val node = ValidationNode("debit", BigInteger(debit)).apply { negativeOrZero() }

            node.validate() shouldBe listOf(Violation("debit", "must be negative or zero"))
        }

        @ParameterizedTest
        @ValueSource(strings = ["0", "-1"])
        fun `given a zero or negative BigInteger debit should accept it`(debit: String) {
            val node = ValidationNode("debit", BigInteger(debit)).apply { negativeOrZero() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["1.00", "10.00"])
        fun `given a BigDecimal debit above zero should report it`(debit: String) {
            val node = ValidationNode("debit", BigDecimal(debit)).apply { negativeOrZero() }

            node.validate() shouldBe listOf(Violation("debit", "must be negative or zero"))
        }

        @ParameterizedTest
        @ValueSource(strings = ["0.00", "-1.00"])
        fun `given a zero or negative BigDecimal debit should accept it`(debit: String) {
            val node = ValidationNode("debit", BigDecimal(debit)).apply { negativeOrZero() }

            node.validate() shouldBe emptyList()
        }
    }

    @Nested
    @DisplayName("When multipleOf is called")
    inner class MultipleOf {
        @ParameterizedTest
        @ValueSource(ints = [5, 7])
        fun `given an Int quantity that is not a multiple of the factor should report it`(quantity: Int) {
            val node = ValidationNode("quantity", quantity).apply { multipleOf(6) }

            node.validate() shouldBe listOf(Violation("quantity", "must be a multiple of 6"))
        }

        @ParameterizedTest
        @ValueSource(ints = [6, 12])
        fun `given an Int quantity that is a multiple of the factor should accept it`(quantity: Int) {
            val node = ValidationNode("quantity", quantity).apply { multipleOf(6) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for an Int should build it from the value and the factor`() {
            val node = ValidationNode("quantity", 7).apply {
                multipleOf(6) { factor -> "$this is not a multiple of $factor" }
            }

            node.validate() shouldBe listOf(Violation("quantity", "7 is not a multiple of 6"))
        }

        @ParameterizedTest
        @ValueSource(longs = [5, 7])
        fun `given a Long quantity that is not a multiple of the factor should report it`(quantity: Long) {
            val node = ValidationNode("quantity", quantity).apply { multipleOf(6L) }

            node.validate() shouldBe listOf(Violation("quantity", "must be a multiple of 6"))
        }

        @ParameterizedTest
        @ValueSource(longs = [6, 12])
        fun `given a Long quantity that is a multiple of the factor should accept it`(quantity: Long) {
            val node = ValidationNode("quantity", quantity).apply { multipleOf(6L) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a Long should build it from the value and the factor`() {
            val node = ValidationNode("quantity", 7L).apply {
                multipleOf(6L) { factor -> "$this is not a multiple of $factor" }
            }

            node.validate() shouldBe listOf(Violation("quantity", "7 is not a multiple of 6"))
        }

        @ParameterizedTest
        @ValueSource(shorts = [5, 7])
        fun `given a Short quantity that is not a multiple of the factor should report it`(quantity: Short) {
            val node = ValidationNode("quantity", quantity).apply { multipleOf(6) }

            node.validate() shouldBe listOf(Violation("quantity", "must be a multiple of 6"))
        }

        @ParameterizedTest
        @ValueSource(shorts = [6, 12])
        fun `given a Short quantity that is a multiple of the factor should accept it`(quantity: Short) {
            val node = ValidationNode("quantity", quantity).apply { multipleOf(6) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a Short should build it from the value and the factor`() {
            val node = ValidationNode("quantity", 7.toShort()).apply {
                multipleOf(6) { factor -> "$this is not a multiple of $factor" }
            }

            node.validate() shouldBe listOf(Violation("quantity", "7 is not a multiple of 6"))
        }

        @ParameterizedTest
        @ValueSource(bytes = [5, 7])
        fun `given a Byte quantity that is not a multiple of the factor should report it`(quantity: Byte) {
            val node = ValidationNode("quantity", quantity).apply { multipleOf(6) }

            node.validate() shouldBe listOf(Violation("quantity", "must be a multiple of 6"))
        }

        @ParameterizedTest
        @ValueSource(bytes = [6, 12])
        fun `given a Byte quantity that is a multiple of the factor should accept it`(quantity: Byte) {
            val node = ValidationNode("quantity", quantity).apply { multipleOf(6) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a Byte should build it from the value and the factor`() {
            val node = ValidationNode("quantity", 7.toByte()).apply {
                multipleOf(6) { factor -> "$this is not a multiple of $factor" }
            }

            node.validate() shouldBe listOf(Violation("quantity", "7 is not a multiple of 6"))
        }

        @ParameterizedTest
        @ValueSource(strings = ["5", "7"])
        fun `given a BigInteger quantity that is not a multiple of the factor should report it`(quantity: String) {
            val node = ValidationNode("quantity", BigInteger(quantity)).apply { multipleOf(BigInteger("6")) }

            node.validate() shouldBe listOf(Violation("quantity", "must be a multiple of 6"))
        }

        @ParameterizedTest
        @ValueSource(strings = ["6", "12"])
        fun `given a BigInteger quantity that is a multiple of the factor should accept it`(quantity: String) {
            val node = ValidationNode("quantity", BigInteger(quantity)).apply { multipleOf(BigInteger("6")) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a BigInteger should build it from the value and the factor`() {
            val node = ValidationNode("quantity", BigInteger("7")).apply {
                multipleOf(BigInteger("6")) { factor -> "$this is not a multiple of $factor" }
            }

            node.validate() shouldBe listOf(Violation("quantity", "7 is not a multiple of 6"))
        }

        @ParameterizedTest
        @ValueSource(strings = ["10.03", "10.07"])
        fun `given a BigDecimal amount that is not a multiple of the factor should report it`(amount: String) {
            val node = ValidationNode("amount", BigDecimal(amount)).apply { multipleOf(BigDecimal("0.05")) }

            node.validate() shouldBe listOf(Violation("amount", "must be a multiple of 0.05"))
        }

        @ParameterizedTest
        @ValueSource(strings = ["10.05", "10.10"])
        fun `given a BigDecimal amount that is a multiple of the factor should accept it`(amount: String) {
            val node = ValidationNode("amount", BigDecimal(amount)).apply { multipleOf(BigDecimal("0.05")) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a BigDecimal should build it from the value and the factor`() {
            val node = ValidationNode("amount", BigDecimal("10.03")).apply {
                multipleOf(BigDecimal("0.05")) { factor -> "$this is not a multiple of $factor" }
            }

            node.validate() shouldBe listOf(Violation("amount", "10.03 is not a multiple of 0.05"))
        }
    }

    @Nested
    @DisplayName("When finite is called")
    inner class Finite {
        @ParameterizedTest
        @ValueSource(doubles = [Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY])
        fun `given a Double rate that is not finite should report it`(rate: Double) {
            val node = ValidationNode("rate", rate).apply { finite() }

            node.validate() shouldBe listOf(Violation("rate", "must be finite"))
        }

        @ParameterizedTest
        @ValueSource(doubles = [0.0, 1.5, -1.5])
        fun `given a finite Double rate should accept it`(rate: Double) {
            val node = ValidationNode("rate", rate).apply { finite() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(floats = [Float.NaN, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY])
        fun `given a Float rate that is not finite should report it`(rate: Float) {
            val node = ValidationNode("rate", rate).apply { finite() }

            node.validate() shouldBe listOf(Violation("rate", "must be finite"))
        }

        @ParameterizedTest
        @ValueSource(floats = [0f, 1.5f, -1.5f])
        fun `given a finite Float rate should accept it`(rate: Float) {
            val node = ValidationNode("rate", rate).apply { finite() }

            node.validate() shouldBe emptyList()
        }
    }

    @Nested
    @DisplayName("When maxDecimalPlaces is called")
    inner class MaxDecimalPlaces {
        @ParameterizedTest
        @ValueSource(strings = ["10.001", "0.125"])
        fun `given an amount with more decimal places than the maximum should report it`(amount: String) {
            val node = ValidationNode("amount", BigDecimal(amount)).apply { maxDecimalPlaces(2) }

            node.validate() shouldBe listOf(Violation("amount", "must have at most 2 decimal places"))
        }

        @ParameterizedTest
        @ValueSource(strings = ["10", "10.5", "10.50", "10.500"])
        fun `given an amount with at most the maximum decimal places should accept it`(amount: String) {
            val node = ValidationNode("amount", BigDecimal(amount)).apply { maxDecimalPlaces(2) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function should build it from the value and the maximum`() {
            val node = ValidationNode("amount", BigDecimal("10.001")).apply {
                maxDecimalPlaces(2) { max -> "$this has more than $max decimal places" }
            }

            node.validate() shouldBe listOf(Violation("amount", "10.001 has more than 2 decimal places"))
        }
    }

    @Nested
    @DisplayName("When maxIntegerDigits is called")
    inner class MaxIntegerDigits {
        @ParameterizedTest
        @ValueSource(strings = ["1000", "1000.5"])
        fun `given a percentage with more integer digits than the maximum should report it`(percentage: String) {
            val node = ValidationNode("percentage", BigDecimal(percentage)).apply { maxIntegerDigits(3) }

            node.validate() shouldBe listOf(Violation("percentage", "must have at most 3 integer digits"))
        }

        @ParameterizedTest
        @ValueSource(strings = ["100", "999.99", "0.5"])
        fun `given a percentage with at most the maximum integer digits should accept it`(percentage: String) {
            val node = ValidationNode("percentage", BigDecimal(percentage)).apply { maxIntegerDigits(3) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function should build it from the value and the maximum`() {
            val node = ValidationNode("percentage", BigDecimal("1000")).apply {
                maxIntegerDigits(3) { max -> "$this has more than $max integer digits" }
            }

            node.validate() shouldBe listOf(Violation("percentage", "1000 has more than 3 integer digits"))
        }
    }
}
