package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.assertions.throwables.shouldThrow
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
        fun `given an Int at the minimum should accept it`() {
            val node = ValidationNode(path = "quantity", value = 10).apply { min(10) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a Long at the minimum should accept it`() {
            val node = ValidationNode(path = "quantity", value = 10L).apply { min(10L) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a Short at the minimum should accept it`() {
            val node = ValidationNode(path = "quantity", value = 10.toShort()).apply { min(10.toShort()) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a Byte at the minimum should accept it`() {
            val node = ValidationNode(path = "quantity", value = 10.toByte()).apply { min(10.toByte()) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a Double at the minimum should accept it`() {
            val node = ValidationNode(path = "rate", value = 10.0).apply { min(10.0) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a Float at the minimum should accept it`() {
            val node = ValidationNode(path = "rate", value = 10f).apply { min(10f) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a BigInteger at the minimum should accept it`() {
            val node = ValidationNode(path = "supply", value = BigInteger("10")).apply { min(BigInteger("10")) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a BigDecimal at the minimum should accept it`() {
            val node = ValidationNode(
                path = "amount",
                value = "10.00".toBigDecimal()
            ).apply { min("10.00".toBigDecimal()) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given an Int below the minimum should report it`() {
            val node = ValidationNode(path = "quantity", value = 9).apply { min(10) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be at least 10", code = "min")
            )
        }

        @Test
        fun `given a Long below the minimum should report it`() {
            val node = ValidationNode(path = "quantity", value = 9L).apply { min(10L) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be at least 10", code = "min")
            )
        }

        @Test
        fun `given a Short below the minimum should report it`() {
            val node = ValidationNode(path = "quantity", value = 9.toShort()).apply { min(10.toShort()) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be at least 10", code = "min")
            )
        }

        @Test
        fun `given a Byte below the minimum should report it`() {
            val node = ValidationNode(path = "quantity", value = 9.toByte()).apply { min(10.toByte()) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be at least 10", code = "min")
            )
        }

        @Test
        fun `given a Double below the minimum should report it`() {
            val node = ValidationNode(path = "rate", value = 9.0).apply { min(10.0) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "rate", message = "must be at least 10.0", code = "min")
            )
        }

        @Test
        fun `given a Float below the minimum should report it`() {
            val node = ValidationNode(path = "rate", value = 9f).apply { min(10f) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "rate", message = "must be at least 10.0", code = "min")
            )
        }

        @Test
        fun `given a BigInteger below the minimum should report it`() {
            val node = ValidationNode(path = "supply", value = BigInteger("9")).apply { min(BigInteger("10")) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "supply", message = "must be at least 10", code = "min")
            )
        }

        @Test
        fun `given a BigDecimal below the minimum should report it`() {
            val node = ValidationNode(
                path = "amount",
                value = "9.00".toBigDecimal()
            ).apply { min("10.00".toBigDecimal()) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "amount", message = "must be at least 10.00", code = "min")
            )
        }

        @Test
        fun `given no minimum for an Int should skip the check`() {
            val minimum: Int? = null
            val node = ValidationNode(path = "quantity", value = 9).apply { min(minimum) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no minimum for a Long should skip the check`() {
            val minimum: Long? = null
            val node = ValidationNode(path = "quantity", value = 9L).apply { min(minimum) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no minimum for a Short should skip the check`() {
            val minimum: Short? = null
            val node = ValidationNode(path = "quantity", value = 9.toShort()).apply { min(minimum) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no minimum for a Byte should skip the check`() {
            val minimum: Byte? = null
            val node = ValidationNode(path = "quantity", value = 9.toByte()).apply { min(minimum) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no minimum for a Double should skip the check`() {
            val minimum: Double? = null
            val node = ValidationNode(path = "rate", value = 9.0).apply { min(minimum) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no minimum for a Float should skip the check`() {
            val minimum: Float? = null
            val node = ValidationNode(path = "rate", value = 9f).apply { min(minimum) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no minimum for a BigInteger should skip the check`() {
            val minimum: BigInteger? = null
            val node = ValidationNode(path = "supply", value = BigInteger("9")).apply { min(minimum) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no minimum for a BigDecimal should skip the check`() {
            val minimum: BigDecimal? = null
            val node = ValidationNode(path = "amount", value = "9.00".toBigDecimal()).apply { min(minimum) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a message function for an Int should build it from the value and the minimum`() {
            val node = ValidationNode(path = "quantity", value = 9).apply { min(10) { min -> "$this is below $min" } }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "9 is below 10", code = "min")
            )
        }

        @Test
        fun `given a message function for a Long should build it from the value and the minimum`() {
            val node = ValidationNode(path = "quantity", value = 9L).apply { min(10L) { min -> "$this is below $min" } }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "9 is below 10", code = "min")
            )
        }

        @Test
        fun `given a message function for a Short should build it from the value and the minimum`() {
            val node = ValidationNode(path = "quantity", value = 9.toShort()).apply {
                min(10.toShort()) { min -> "$this is below $min" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "9 is below 10", code = "min")
            )
        }

        @Test
        fun `given a message function for a Byte should build it from the value and the minimum`() {
            val node = ValidationNode(path = "quantity", value = 9.toByte()).apply {
                min(10.toByte()) { min -> "$this is below $min" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "9 is below 10", code = "min")
            )
        }

        @Test
        fun `given a message function for a Double should build it from the value and the minimum`() {
            val node = ValidationNode(path = "rate", value = 9.0).apply { min(10.0) { min -> "$this is below $min" } }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "rate", message = "9.0 is below 10.0", code = "min")
            )
        }

        @Test
        fun `given a message function for a Float should build it from the value and the minimum`() {
            val node = ValidationNode(path = "rate", value = 9f).apply { min(10f) { min -> "$this is below $min" } }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "rate", message = "9.0 is below 10.0", code = "min")
            )
        }

        @Test
        fun `given a message function for a BigInteger should build it from the value and the minimum`() {
            val node = ValidationNode(path = "supply", value = BigInteger("9")).apply {
                min(BigInteger("10")) { min -> "$this is below $min" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "supply", message = "9 is below 10", code = "min")
            )
        }

        @Test
        fun `given a message function for a BigDecimal should build it from the value and the minimum`() {
            val node = ValidationNode(path = "amount", value = "9.00".toBigDecimal()).apply {
                min("10.00".toBigDecimal()) { min -> "$this is below $min" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "amount", message = "9.00 is below 10.00", code = "min")
            )
        }
    }

    @Nested
    @DisplayName("When max is called")
    inner class Max {
        @Test
        fun `given an Int at the maximum should accept it`() {
            val node = ValidationNode(path = "quantity", value = 10).apply { max(10) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a Long at the maximum should accept it`() {
            val node = ValidationNode(path = "quantity", value = 10L).apply { max(10L) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a Short at the maximum should accept it`() {
            val node = ValidationNode(path = "quantity", value = 10.toShort()).apply { max(10.toShort()) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a Byte at the maximum should accept it`() {
            val node = ValidationNode(path = "quantity", value = 10.toByte()).apply { max(10.toByte()) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a Double at the maximum should accept it`() {
            val node = ValidationNode(path = "rate", value = 10.0).apply { max(10.0) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a Float at the maximum should accept it`() {
            val node = ValidationNode(path = "rate", value = 10f).apply { max(10f) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a BigInteger at the maximum should accept it`() {
            val node = ValidationNode(path = "supply", value = BigInteger("10")).apply { max(BigInteger("10")) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a BigDecimal at the maximum should accept it`() {
            val node = ValidationNode(
                path = "amount",
                value = "10.00".toBigDecimal()
            ).apply { max("10.00".toBigDecimal()) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given an Int above the maximum should report it`() {
            val node = ValidationNode(path = "quantity", value = 11).apply { max(10) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be at most 10", code = "max")
            )
        }

        @Test
        fun `given a Long above the maximum should report it`() {
            val node = ValidationNode(path = "quantity", value = 11L).apply { max(10L) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be at most 10", code = "max")
            )
        }

        @Test
        fun `given a Short above the maximum should report it`() {
            val node = ValidationNode(path = "quantity", value = 11.toShort()).apply { max(10.toShort()) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be at most 10", code = "max")
            )
        }

        @Test
        fun `given a Byte above the maximum should report it`() {
            val node = ValidationNode(path = "quantity", value = 11.toByte()).apply { max(10.toByte()) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be at most 10", code = "max")
            )
        }

        @Test
        fun `given a Double above the maximum should report it`() {
            val node = ValidationNode(path = "rate", value = 11.0).apply { max(10.0) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "rate", message = "must be at most 10.0", code = "max")
            )
        }

        @Test
        fun `given a Float above the maximum should report it`() {
            val node = ValidationNode(path = "rate", value = 11f).apply { max(10f) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "rate", message = "must be at most 10.0", code = "max")
            )
        }

        @Test
        fun `given a BigInteger above the maximum should report it`() {
            val node = ValidationNode(path = "supply", value = BigInteger("11")).apply { max(BigInteger("10")) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "supply", message = "must be at most 10", code = "max")
            )
        }

        @Test
        fun `given a BigDecimal above the maximum should report it`() {
            val node = ValidationNode(
                path = "amount",
                value = "11.00".toBigDecimal()
            ).apply { max("10.00".toBigDecimal()) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "amount", message = "must be at most 10.00", code = "max")
            )
        }

        @Test
        fun `given no maximum for an Int should skip the check`() {
            val maximum: Int? = null
            val node = ValidationNode(path = "quantity", value = 11).apply { max(maximum) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no maximum for a Long should skip the check`() {
            val maximum: Long? = null
            val node = ValidationNode(path = "quantity", value = 11L).apply { max(maximum) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no maximum for a Short should skip the check`() {
            val maximum: Short? = null
            val node = ValidationNode(path = "quantity", value = 11.toShort()).apply { max(maximum) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no maximum for a Byte should skip the check`() {
            val maximum: Byte? = null
            val node = ValidationNode(path = "quantity", value = 11.toByte()).apply { max(maximum) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no maximum for a Double should skip the check`() {
            val maximum: Double? = null
            val node = ValidationNode(path = "rate", value = 11.0).apply { max(maximum) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no maximum for a Float should skip the check`() {
            val maximum: Float? = null
            val node = ValidationNode(path = "rate", value = 11f).apply { max(maximum) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no maximum for a BigInteger should skip the check`() {
            val maximum: BigInteger? = null
            val node = ValidationNode(path = "supply", value = BigInteger("11")).apply { max(maximum) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no maximum for a BigDecimal should skip the check`() {
            val maximum: BigDecimal? = null
            val node = ValidationNode(path = "amount", value = "11.00".toBigDecimal()).apply { max(maximum) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a message function for an Int should build it from the value and the maximum`() {
            val node = ValidationNode(path = "quantity", value = 11).apply { max(10) { max -> "$this is above $max" } }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "11 is above 10", code = "max")
            )
        }

        @Test
        fun `given a message function for a Long should build it from the value and the maximum`() {
            val node = ValidationNode(
                path = "quantity",
                value = 11L
            ).apply { max(10L) { max -> "$this is above $max" } }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "11 is above 10", code = "max")
            )
        }

        @Test
        fun `given a message function for a Short should build it from the value and the maximum`() {
            val node = ValidationNode(path = "quantity", value = 11.toShort()).apply {
                max(10.toShort()) { max -> "$this is above $max" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "11 is above 10", code = "max")
            )
        }

        @Test
        fun `given a message function for a Byte should build it from the value and the maximum`() {
            val node = ValidationNode(path = "quantity", value = 11.toByte()).apply {
                max(10.toByte()) { max -> "$this is above $max" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "11 is above 10", code = "max")
            )
        }

        @Test
        fun `given a message function for a Double should build it from the value and the maximum`() {
            val node = ValidationNode(path = "rate", value = 11.0).apply { max(10.0) { max -> "$this is above $max" } }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "rate", message = "11.0 is above 10.0", code = "max")
            )
        }

        @Test
        fun `given a message function for a Float should build it from the value and the maximum`() {
            val node = ValidationNode(path = "rate", value = 11f).apply { max(10f) { max -> "$this is above $max" } }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "rate", message = "11.0 is above 10.0", code = "max")
            )
        }

        @Test
        fun `given a message function for a BigInteger should build it from the value and the maximum`() {
            val node = ValidationNode(path = "supply", value = BigInteger("11")).apply {
                max(BigInteger("10")) { max -> "$this is above $max" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "supply", message = "11 is above 10", code = "max")
            )
        }

        @Test
        fun `given a message function for a BigDecimal should build it from the value and the maximum`() {
            val node = ValidationNode(path = "amount", value = "11.00".toBigDecimal()).apply {
                max("10.00".toBigDecimal()) { max -> "$this is above $max" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "amount", message = "11.00 is above 10.00", code = "max")
            )
        }
    }

    @Nested
    @DisplayName("When greaterThan is called")
    inner class GreaterThan {
        @Test
        fun `given an Int above the reference should accept it`() {
            val node = ValidationNode(path = "quantity", value = 11).apply { greaterThan(10) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a Long above the reference should accept it`() {
            val node = ValidationNode(path = "quantity", value = 11L).apply { greaterThan(10L) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a Short above the reference should accept it`() {
            val node = ValidationNode(path = "quantity", value = 11.toShort()).apply { greaterThan(10.toShort()) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a Byte above the reference should accept it`() {
            val node = ValidationNode(path = "quantity", value = 11.toByte()).apply { greaterThan(10.toByte()) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a Double above the reference should accept it`() {
            val node = ValidationNode(path = "rate", value = 11.0).apply { greaterThan(10.0) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a Float above the reference should accept it`() {
            val node = ValidationNode(path = "rate", value = 11f).apply { greaterThan(10f) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a BigInteger above the reference should accept it`() {
            val node = ValidationNode(path = "supply", value = BigInteger("11")).apply { greaterThan(BigInteger("10")) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a BigDecimal above the reference should accept it`() {
            val node = ValidationNode(
                path = "amount",
                value = "11.00".toBigDecimal()
            ).apply { greaterThan("10.00".toBigDecimal()) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given an Int equal to the reference should report it`() {
            val node = ValidationNode(path = "quantity", value = 10).apply { greaterThan(10) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be greater than 10", code = "greaterThan")
            )
        }

        @Test
        fun `given a Long equal to the reference should report it`() {
            val node = ValidationNode(path = "quantity", value = 10L).apply { greaterThan(10L) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be greater than 10", code = "greaterThan")
            )
        }

        @Test
        fun `given a Short equal to the reference should report it`() {
            val node = ValidationNode(path = "quantity", value = 10.toShort()).apply { greaterThan(10.toShort()) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be greater than 10", code = "greaterThan")
            )
        }

        @Test
        fun `given a Byte equal to the reference should report it`() {
            val node = ValidationNode(path = "quantity", value = 10.toByte()).apply { greaterThan(10.toByte()) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be greater than 10", code = "greaterThan")
            )
        }

        @Test
        fun `given a Double equal to the reference should report it`() {
            val node = ValidationNode(path = "rate", value = 10.0).apply { greaterThan(10.0) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "rate", message = "must be greater than 10.0", code = "greaterThan")
            )
        }

        @Test
        fun `given a Float equal to the reference should report it`() {
            val node = ValidationNode(path = "rate", value = 10f).apply { greaterThan(10f) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "rate", message = "must be greater than 10.0", code = "greaterThan")
            )
        }

        @Test
        fun `given a BigInteger equal to the reference should report it`() {
            val node = ValidationNode(path = "supply", value = BigInteger("10")).apply { greaterThan(BigInteger("10")) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "supply", message = "must be greater than 10", code = "greaterThan")
            )
        }

        @Test
        fun `given a BigDecimal equal to the reference should report it`() {
            val node = ValidationNode(
                path = "amount",
                value = "10.00".toBigDecimal()
            ).apply { greaterThan("10.00".toBigDecimal()) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "amount", message = "must be greater than 10.00", code = "greaterThan")
            )
        }

        @Test
        fun `given no reference for an Int should skip the check`() {
            val reference: Int? = null
            val node = ValidationNode(path = "quantity", value = 10).apply { greaterThan(reference) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no reference for a Long should skip the check`() {
            val reference: Long? = null
            val node = ValidationNode(path = "quantity", value = 10L).apply { greaterThan(reference) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no reference for a Short should skip the check`() {
            val reference: Short? = null
            val node = ValidationNode(path = "quantity", value = 10.toShort()).apply { greaterThan(reference) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no reference for a Byte should skip the check`() {
            val reference: Byte? = null
            val node = ValidationNode(path = "quantity", value = 10.toByte()).apply { greaterThan(reference) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no reference for a Double should skip the check`() {
            val reference: Double? = null
            val node = ValidationNode(path = "rate", value = 10.0).apply { greaterThan(reference) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no reference for a Float should skip the check`() {
            val reference: Float? = null
            val node = ValidationNode(path = "rate", value = 10f).apply { greaterThan(reference) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no reference for a BigInteger should skip the check`() {
            val reference: BigInteger? = null
            val node = ValidationNode(path = "supply", value = BigInteger("10")).apply { greaterThan(reference) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no reference for a BigDecimal should skip the check`() {
            val reference: BigDecimal? = null
            val node = ValidationNode(path = "amount", value = "10.00".toBigDecimal()).apply { greaterThan(reference) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a message function for an Int should build it from the value and the reference`() {
            val node = ValidationNode(path = "quantity", value = 10).apply {
                greaterThan(10) { other -> "$this is not greater than $other" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "10 is not greater than 10", code = "greaterThan")
            )
        }

        @Test
        fun `given a message function for a Long should build it from the value and the reference`() {
            val node = ValidationNode(path = "quantity", value = 10L).apply {
                greaterThan(10L) { other -> "$this is not greater than $other" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "10 is not greater than 10", code = "greaterThan")
            )
        }

        @Test
        fun `given a message function for a Short should build it from the value and the reference`() {
            val node = ValidationNode(path = "quantity", value = 10.toShort()).apply {
                greaterThan(10.toShort()) { other -> "$this is not greater than $other" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "10 is not greater than 10", code = "greaterThan")
            )
        }

        @Test
        fun `given a message function for a Byte should build it from the value and the reference`() {
            val node = ValidationNode(path = "quantity", value = 10.toByte()).apply {
                greaterThan(10.toByte()) { other -> "$this is not greater than $other" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "10 is not greater than 10", code = "greaterThan")
            )
        }

        @Test
        fun `given a message function for a Double should build it from the value and the reference`() {
            val node = ValidationNode(path = "rate", value = 10.0).apply {
                greaterThan(10.0) { other -> "$this is not greater than $other" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "rate", message = "10.0 is not greater than 10.0", code = "greaterThan")
            )
        }

        @Test
        fun `given a message function for a Float should build it from the value and the reference`() {
            val node = ValidationNode(path = "rate", value = 10f).apply {
                greaterThan(10f) { other -> "$this is not greater than $other" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "rate", message = "10.0 is not greater than 10.0", code = "greaterThan")
            )
        }

        @Test
        fun `given a message function for a BigInteger should build it from the value and the reference`() {
            val node = ValidationNode(path = "supply", value = BigInteger("10")).apply {
                greaterThan(BigInteger("10")) { other -> "$this is not greater than $other" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "supply", message = "10 is not greater than 10", code = "greaterThan")
            )
        }

        @Test
        fun `given a message function for a BigDecimal should build it from the value and the reference`() {
            val node = ValidationNode(path = "amount", value = "10.00".toBigDecimal()).apply {
                greaterThan("10.00".toBigDecimal()) { other -> "$this is not greater than $other" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "amount", message = "10.00 is not greater than 10.00", code = "greaterThan")
            )
        }
    }

    @Nested
    @DisplayName("When lessThan is called")
    inner class LessThan {
        @Test
        fun `given an Int below the reference should accept it`() {
            val node = ValidationNode(path = "quantity", value = 9).apply { lessThan(10) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a Long below the reference should accept it`() {
            val node = ValidationNode(path = "quantity", value = 9L).apply { lessThan(10L) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a Short below the reference should accept it`() {
            val node = ValidationNode(path = "quantity", value = 9.toShort()).apply { lessThan(10.toShort()) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a Byte below the reference should accept it`() {
            val node = ValidationNode(path = "quantity", value = 9.toByte()).apply { lessThan(10.toByte()) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a Double below the reference should accept it`() {
            val node = ValidationNode(path = "rate", value = 9.0).apply { lessThan(10.0) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a Float below the reference should accept it`() {
            val node = ValidationNode(path = "rate", value = 9f).apply { lessThan(10f) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a BigInteger below the reference should accept it`() {
            val node = ValidationNode(path = "supply", value = BigInteger("9")).apply { lessThan(BigInteger("10")) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a BigDecimal below the reference should accept it`() {
            val node = ValidationNode(
                path = "amount",
                value = "9.00".toBigDecimal()
            ).apply { lessThan("10.00".toBigDecimal()) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given an Int equal to the reference should report it`() {
            val node = ValidationNode(path = "quantity", value = 10).apply { lessThan(10) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be less than 10", code = "lessThan")
            )
        }

        @Test
        fun `given a Long equal to the reference should report it`() {
            val node = ValidationNode(path = "quantity", value = 10L).apply { lessThan(10L) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be less than 10", code = "lessThan")
            )
        }

        @Test
        fun `given a Short equal to the reference should report it`() {
            val node = ValidationNode(path = "quantity", value = 10.toShort()).apply { lessThan(10.toShort()) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be less than 10", code = "lessThan")
            )
        }

        @Test
        fun `given a Byte equal to the reference should report it`() {
            val node = ValidationNode(path = "quantity", value = 10.toByte()).apply { lessThan(10.toByte()) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be less than 10", code = "lessThan")
            )
        }

        @Test
        fun `given a Double equal to the reference should report it`() {
            val node = ValidationNode(path = "rate", value = 10.0).apply { lessThan(10.0) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "rate", message = "must be less than 10.0", code = "lessThan")
            )
        }

        @Test
        fun `given a Float equal to the reference should report it`() {
            val node = ValidationNode(path = "rate", value = 10f).apply { lessThan(10f) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "rate", message = "must be less than 10.0", code = "lessThan")
            )
        }

        @Test
        fun `given a BigInteger equal to the reference should report it`() {
            val node = ValidationNode(path = "supply", value = BigInteger("10")).apply { lessThan(BigInteger("10")) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "supply", message = "must be less than 10", code = "lessThan")
            )
        }

        @Test
        fun `given a BigDecimal equal to the reference should report it`() {
            val node = ValidationNode(
                path = "amount",
                value = "10.00".toBigDecimal()
            ).apply { lessThan("10.00".toBigDecimal()) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "amount", message = "must be less than 10.00", code = "lessThan")
            )
        }

        @Test
        fun `given no reference for an Int should skip the check`() {
            val reference: Int? = null
            val node = ValidationNode(path = "quantity", value = 10).apply { lessThan(reference) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no reference for a Long should skip the check`() {
            val reference: Long? = null
            val node = ValidationNode(path = "quantity", value = 10L).apply { lessThan(reference) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no reference for a Short should skip the check`() {
            val reference: Short? = null
            val node = ValidationNode(path = "quantity", value = 10.toShort()).apply { lessThan(reference) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no reference for a Byte should skip the check`() {
            val reference: Byte? = null
            val node = ValidationNode(path = "quantity", value = 10.toByte()).apply { lessThan(reference) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no reference for a Double should skip the check`() {
            val reference: Double? = null
            val node = ValidationNode(path = "rate", value = 10.0).apply { lessThan(reference) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no reference for a Float should skip the check`() {
            val reference: Float? = null
            val node = ValidationNode(path = "rate", value = 10f).apply { lessThan(reference) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no reference for a BigInteger should skip the check`() {
            val reference: BigInteger? = null
            val node = ValidationNode(path = "supply", value = BigInteger("10")).apply { lessThan(reference) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no reference for a BigDecimal should skip the check`() {
            val reference: BigDecimal? = null
            val node = ValidationNode(path = "amount", value = "10.00".toBigDecimal()).apply { lessThan(reference) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a message function for an Int should build it from the value and the reference`() {
            val node = ValidationNode(path = "quantity", value = 10).apply {
                lessThan(10) { other -> "$this is not less than $other" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "10 is not less than 10", code = "lessThan")
            )
        }

        @Test
        fun `given a message function for a Long should build it from the value and the reference`() {
            val node = ValidationNode(path = "quantity", value = 10L).apply {
                lessThan(10L) { other -> "$this is not less than $other" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "10 is not less than 10", code = "lessThan")
            )
        }

        @Test
        fun `given a message function for a Short should build it from the value and the reference`() {
            val node = ValidationNode(path = "quantity", value = 10.toShort()).apply {
                lessThan(10.toShort()) { other -> "$this is not less than $other" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "10 is not less than 10", code = "lessThan")
            )
        }

        @Test
        fun `given a message function for a Byte should build it from the value and the reference`() {
            val node = ValidationNode(path = "quantity", value = 10.toByte()).apply {
                lessThan(10.toByte()) { other -> "$this is not less than $other" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "10 is not less than 10", code = "lessThan")
            )
        }

        @Test
        fun `given a message function for a Double should build it from the value and the reference`() {
            val node = ValidationNode(path = "rate", value = 10.0).apply {
                lessThan(10.0) { other -> "$this is not less than $other" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "rate", message = "10.0 is not less than 10.0", code = "lessThan")
            )
        }

        @Test
        fun `given a message function for a Float should build it from the value and the reference`() {
            val node = ValidationNode(
                path = "rate",
                value = 10f
            ).apply { lessThan(10f) { other -> "$this is not less than $other" } }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "rate", message = "10.0 is not less than 10.0", code = "lessThan")
            )
        }

        @Test
        fun `given a message function for a BigInteger should build it from the value and the reference`() {
            val node = ValidationNode(path = "supply", value = BigInteger("10")).apply {
                lessThan(BigInteger("10")) { other -> "$this is not less than $other" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "supply", message = "10 is not less than 10", code = "lessThan")
            )
        }

        @Test
        fun `given a message function for a BigDecimal should build it from the value and the reference`() {
            val node = ValidationNode(path = "amount", value = "10.00".toBigDecimal()).apply {
                lessThan("10.00".toBigDecimal()) { other -> "$this is not less than $other" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "amount", message = "10.00 is not less than 10.00", code = "lessThan")
            )
        }
    }

    @Nested
    @DisplayName("When between is called")
    inner class Between {
        @Test
        fun `given an Int at the lower limit should accept it`() {
            val node = ValidationNode(path = "quantity", value = 9).apply { between(min = 9, max = 11) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given an Int inside the range should accept it`() {
            val node = ValidationNode(path = "quantity", value = 10).apply { between(min = 9, max = 11) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given an Int at the upper limit should accept it`() {
            val node = ValidationNode(path = "quantity", value = 11).apply { between(min = 9, max = 11) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a Long at the lower limit should accept it`() {
            val node = ValidationNode(path = "quantity", value = 9L).apply { between(min = 9L, max = 11L) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a Long inside the range should accept it`() {
            val node = ValidationNode(path = "quantity", value = 10L).apply { between(min = 9L, max = 11L) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a Long at the upper limit should accept it`() {
            val node = ValidationNode(path = "quantity", value = 11L).apply { between(min = 9L, max = 11L) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a Short at the lower limit should accept it`() {
            val node = ValidationNode(
                path = "quantity",
                value = 9.toShort()
            ).apply { between(min = 9.toShort(), max = 11.toShort()) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a Short inside the range should accept it`() {
            val node = ValidationNode(
                path = "quantity",
                value = 10.toShort()
            ).apply { between(min = 9.toShort(), max = 11.toShort()) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a Short at the upper limit should accept it`() {
            val node = ValidationNode(
                path = "quantity",
                value = 11.toShort()
            ).apply { between(min = 9.toShort(), max = 11.toShort()) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a Byte at the lower limit should accept it`() {
            val node = ValidationNode(
                path = "quantity",
                value = 9.toByte()
            ).apply { between(min = 9.toByte(), max = 11.toByte()) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a Byte inside the range should accept it`() {
            val node = ValidationNode(
                path = "quantity",
                value = 10.toByte()
            ).apply { between(min = 9.toByte(), max = 11.toByte()) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a Byte at the upper limit should accept it`() {
            val node = ValidationNode(
                path = "quantity",
                value = 11.toByte()
            ).apply { between(min = 9.toByte(), max = 11.toByte()) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a Double at the lower limit should accept it`() {
            val node = ValidationNode(path = "rate", value = 9.0).apply { between(min = 9.0, max = 11.0) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a Double inside the range should accept it`() {
            val node = ValidationNode(path = "rate", value = 10.0).apply { between(min = 9.0, max = 11.0) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a Double at the upper limit should accept it`() {
            val node = ValidationNode(path = "rate", value = 11.0).apply { between(min = 9.0, max = 11.0) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a Float at the lower limit should accept it`() {
            val node = ValidationNode(path = "rate", value = 9f).apply { between(min = 9f, max = 11f) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a Float inside the range should accept it`() {
            val node = ValidationNode(path = "rate", value = 10f).apply { between(min = 9f, max = 11f) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a Float at the upper limit should accept it`() {
            val node = ValidationNode(path = "rate", value = 11f).apply { between(min = 9f, max = 11f) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a BigInteger at the lower limit should accept it`() {
            val node = ValidationNode(
                path = "supply",
                value = BigInteger("9")
            ).apply { between(min = BigInteger("9"), max = BigInteger("11")) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a BigInteger inside the range should accept it`() {
            val node = ValidationNode(
                path = "supply",
                value = BigInteger("10")
            ).apply { between(min = BigInteger("9"), max = BigInteger("11")) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a BigInteger at the upper limit should accept it`() {
            val node = ValidationNode(
                path = "supply",
                value = BigInteger("11")
            ).apply { between(min = BigInteger("9"), max = BigInteger("11")) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a BigDecimal at the lower limit should accept it`() {
            val node = ValidationNode(path = "amount", value = "9.00".toBigDecimal()).apply {
                between(min = "9.00".toBigDecimal(), max = "11.00".toBigDecimal())
            }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a BigDecimal inside the range should accept it`() {
            val node = ValidationNode(path = "amount", value = "10.00".toBigDecimal()).apply {
                between(min = "9.00".toBigDecimal(), max = "11.00".toBigDecimal())
            }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a BigDecimal at the upper limit should accept it`() {
            val node = ValidationNode(path = "amount", value = "11.00".toBigDecimal()).apply {
                between(min = "9.00".toBigDecimal(), max = "11.00".toBigDecimal())
            }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given an Int below the range should report it`() {
            val node = ValidationNode(path = "quantity", value = 8).apply { between(min = 9, max = 11) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be between 9 and 11", code = "between")
            )
        }

        @Test
        fun `given an Int above the range should report it`() {
            val node = ValidationNode(path = "quantity", value = 12).apply { between(min = 9, max = 11) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be between 9 and 11", code = "between")
            )
        }

        @Test
        fun `given a Long below the range should report it`() {
            val node = ValidationNode(path = "quantity", value = 8L).apply { between(min = 9L, max = 11L) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be between 9 and 11", code = "between")
            )
        }

        @Test
        fun `given a Long above the range should report it`() {
            val node = ValidationNode(path = "quantity", value = 12L).apply { between(min = 9L, max = 11L) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be between 9 and 11", code = "between")
            )
        }

        @Test
        fun `given a Short below the range should report it`() {
            val node = ValidationNode(
                path = "quantity",
                value = 8.toShort()
            ).apply { between(min = 9.toShort(), max = 11.toShort()) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be between 9 and 11", code = "between")
            )
        }

        @Test
        fun `given a Short above the range should report it`() {
            val node = ValidationNode(
                path = "quantity",
                value = 12.toShort()
            ).apply { between(min = 9.toShort(), max = 11.toShort()) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be between 9 and 11", code = "between")
            )
        }

        @Test
        fun `given a Byte below the range should report it`() {
            val node = ValidationNode(
                path = "quantity",
                value = 8.toByte()
            ).apply { between(min = 9.toByte(), max = 11.toByte()) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be between 9 and 11", code = "between")
            )
        }

        @Test
        fun `given a Byte above the range should report it`() {
            val node = ValidationNode(
                path = "quantity",
                value = 12.toByte()
            ).apply { between(min = 9.toByte(), max = 11.toByte()) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be between 9 and 11", code = "between")
            )
        }

        @Test
        fun `given a Double below the range should report it`() {
            val node = ValidationNode(path = "rate", value = 8.0).apply { between(min = 9.0, max = 11.0) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "rate", message = "must be between 9.0 and 11.0", code = "between")
            )
        }

        @Test
        fun `given a Double above the range should report it`() {
            val node = ValidationNode(path = "rate", value = 12.0).apply { between(min = 9.0, max = 11.0) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "rate", message = "must be between 9.0 and 11.0", code = "between")
            )
        }

        @Test
        fun `given a Float below the range should report it`() {
            val node = ValidationNode(path = "rate", value = 8f).apply { between(min = 9f, max = 11f) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "rate", message = "must be between 9.0 and 11.0", code = "between")
            )
        }

        @Test
        fun `given a Float above the range should report it`() {
            val node = ValidationNode(path = "rate", value = 12f).apply { between(min = 9f, max = 11f) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "rate", message = "must be between 9.0 and 11.0", code = "between")
            )
        }

        @Test
        fun `given a BigInteger below the range should report it`() {
            val node = ValidationNode(
                path = "supply",
                value = BigInteger("8")
            ).apply { between(min = BigInteger("9"), max = BigInteger("11")) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "supply", message = "must be between 9 and 11", code = "between")
            )
        }

        @Test
        fun `given a BigInteger above the range should report it`() {
            val node = ValidationNode(
                path = "supply",
                value = BigInteger("12")
            ).apply { between(min = BigInteger("9"), max = BigInteger("11")) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "supply", message = "must be between 9 and 11", code = "between")
            )
        }

        @Test
        fun `given a BigDecimal below the range should report it`() {
            val node = ValidationNode(path = "amount", value = "8.00".toBigDecimal()).apply {
                between(min = "9.00".toBigDecimal(), max = "11.00".toBigDecimal())
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "amount", message = "must be between 9.00 and 11.00", code = "between")
            )
        }

        @Test
        fun `given a BigDecimal above the range should report it`() {
            val node = ValidationNode(path = "amount", value = "12.00".toBigDecimal()).apply {
                between(min = "9.00".toBigDecimal(), max = "11.00".toBigDecimal())
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "amount", message = "must be between 9.00 and 11.00", code = "between")
            )
        }

        @Test
        fun `given no minimum for an Int should skip the check`() {
            val min: Int? = null
            val node = ValidationNode(path = "quantity", value = 12).apply { between(min = min, max = 11) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no minimum for a Long should skip the check`() {
            val min: Long? = null
            val node = ValidationNode(path = "quantity", value = 12L).apply { between(min = min, max = 11L) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no minimum for a Short should skip the check`() {
            val min: Short? = null
            val node = ValidationNode(
                path = "quantity",
                value = 12.toShort()
            ).apply { between(min = min, max = 11.toShort()) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no minimum for a Byte should skip the check`() {
            val min: Byte? = null
            val node = ValidationNode(
                path = "quantity",
                value = 12.toByte()
            ).apply { between(min = min, max = 11.toByte()) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no minimum for a Double should skip the check`() {
            val min: Double? = null
            val node = ValidationNode(path = "rate", value = 12.0).apply { between(min = min, max = 11.0) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no minimum for a Float should skip the check`() {
            val min: Float? = null
            val node = ValidationNode(path = "rate", value = 12f).apply { between(min = min, max = 11f) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no minimum for a BigInteger should skip the check`() {
            val min: BigInteger? = null
            val node = ValidationNode(
                path = "supply",
                value = BigInteger("12")
            ).apply { between(min = min, max = BigInteger("11")) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no minimum for a BigDecimal should skip the check`() {
            val min: BigDecimal? = null
            val node = ValidationNode(
                path = "amount",
                value = "12.00".toBigDecimal()
            ).apply { between(min = min, max = "11.00".toBigDecimal()) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a message function for an Int should build it from the value and the limits`() {
            val node = ValidationNode(path = "quantity", value = 12).apply {
                between(min = 9, max = 11) { min, max -> "$this is not from $min to $max" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "12 is not from 9 to 11", code = "between")
            )
        }

        @Test
        fun `given a message function for a Long should build it from the value and the limits`() {
            val node = ValidationNode(path = "quantity", value = 12L).apply {
                between(min = 9L, max = 11L) { min, max -> "$this is not from $min to $max" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "12 is not from 9 to 11", code = "between")
            )
        }

        @Test
        fun `given a message function for a Short should build it from the value and the limits`() {
            val node = ValidationNode(path = "quantity", value = 12.toShort()).apply {
                between(min = 9.toShort(), max = 11.toShort()) { min, max -> "$this is not from $min to $max" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "12 is not from 9 to 11", code = "between")
            )
        }

        @Test
        fun `given a message function for a Byte should build it from the value and the limits`() {
            val node = ValidationNode(path = "quantity", value = 12.toByte()).apply {
                between(min = 9.toByte(), max = 11.toByte()) { min, max -> "$this is not from $min to $max" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "12 is not from 9 to 11", code = "between")
            )
        }

        @Test
        fun `given a message function for a Double should build it from the value and the limits`() {
            val node = ValidationNode(path = "rate", value = 12.0).apply {
                between(min = 9.0, max = 11.0) { min, max -> "$this is not from $min to $max" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "rate", message = "12.0 is not from 9.0 to 11.0", code = "between")
            )
        }

        @Test
        fun `given a message function for a Float should build it from the value and the limits`() {
            val node = ValidationNode(path = "rate", value = 12f).apply {
                between(min = 9f, max = 11f) { min, max -> "$this is not from $min to $max" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "rate", message = "12.0 is not from 9.0 to 11.0", code = "between")
            )
        }

        @Test
        fun `given a message function for a BigInteger should build it from the value and the limits`() {
            val node = ValidationNode(path = "supply", value = BigInteger("12")).apply {
                between(min = BigInteger("9"), max = BigInteger("11")) { min, max -> "$this is not from $min to $max" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "supply", message = "12 is not from 9 to 11", code = "between")
            )
        }

        @Test
        fun `given a message function for a BigDecimal should build it from the value and the limits`() {
            val node = ValidationNode(path = "amount", value = "12.00".toBigDecimal()).apply {
                between(
                    min = "9.00".toBigDecimal(),
                    max = "11.00".toBigDecimal()
                ) { min, max -> "$this is not from $min to $max" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "amount", message = "12.00 is not from 9.00 to 11.00", code = "between")
            )
        }
    }

    @Nested
    @DisplayName("When positive is called")
    inner class Positive {
        @ParameterizedTest
        @ValueSource(ints = [1, 10])
        fun `given a positive Int quantity should accept it`(
            quantity: Int
        ) {
            val node = ValidationNode(path = "quantity", value = quantity).apply { positive() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(longs = [1, 10])
        fun `given a positive Long quantity should accept it`(
            quantity: Long
        ) {
            val node = ValidationNode(path = "quantity", value = quantity).apply { positive() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(shorts = [1, 10])
        fun `given a positive Short quantity should accept it`(
            quantity: Short
        ) {
            val node = ValidationNode(path = "quantity", value = quantity).apply { positive() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(bytes = [1, 10])
        fun `given a positive Byte quantity should accept it`(
            quantity: Byte
        ) {
            val node = ValidationNode(path = "quantity", value = quantity).apply { positive() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(doubles = [1.0, 10.0])
        fun `given a positive Double quantity should accept it`(
            quantity: Double
        ) {
            val node = ValidationNode(path = "quantity", value = quantity).apply { positive() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(floats = [1f, 10f])
        fun `given a positive Float quantity should accept it`(
            quantity: Float
        ) {
            val node = ValidationNode(path = "quantity", value = quantity).apply { positive() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["1", "10"])
        fun `given a positive BigInteger quantity should accept it`(
            quantity: String
        ) {
            val node = ValidationNode(path = "quantity", value = BigInteger(quantity)).apply { positive() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["1.00", "10.00"])
        fun `given a positive BigDecimal quantity should accept it`(
            quantity: String
        ) {
            val node = ValidationNode(path = "quantity", value = quantity.toBigDecimal()).apply { positive() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(ints = [0, -1])
        fun `given an Int quantity that is not positive should report it`(
            quantity: Int
        ) {
            val node = ValidationNode(path = "quantity", value = quantity).apply { positive() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be positive", code = "positive")
            )
        }

        @ParameterizedTest
        @ValueSource(longs = [0, -1])
        fun `given a Long quantity that is not positive should report it`(
            quantity: Long
        ) {
            val node = ValidationNode(path = "quantity", value = quantity).apply { positive() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be positive", code = "positive")
            )
        }

        @ParameterizedTest
        @ValueSource(shorts = [0, -1])
        fun `given a Short quantity that is not positive should report it`(
            quantity: Short
        ) {
            val node = ValidationNode(path = "quantity", value = quantity).apply { positive() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be positive", code = "positive")
            )
        }

        @ParameterizedTest
        @ValueSource(bytes = [0, -1])
        fun `given a Byte quantity that is not positive should report it`(
            quantity: Byte
        ) {
            val node = ValidationNode(path = "quantity", value = quantity).apply { positive() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be positive", code = "positive")
            )
        }

        @ParameterizedTest
        @ValueSource(doubles = [0.0, -1.0])
        fun `given a Double quantity that is not positive should report it`(
            quantity: Double
        ) {
            val node = ValidationNode(path = "quantity", value = quantity).apply { positive() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be positive", code = "positive")
            )
        }

        @ParameterizedTest
        @ValueSource(floats = [0f, -1f])
        fun `given a Float quantity that is not positive should report it`(
            quantity: Float
        ) {
            val node = ValidationNode(path = "quantity", value = quantity).apply { positive() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be positive", code = "positive")
            )
        }

        @ParameterizedTest
        @ValueSource(strings = ["0", "-1"])
        fun `given a BigInteger quantity that is not positive should report it`(
            quantity: String
        ) {
            val node = ValidationNode(path = "quantity", value = BigInteger(quantity)).apply { positive() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be positive", code = "positive")
            )
        }

        @ParameterizedTest
        @ValueSource(strings = ["0.00", "-1.00"])
        fun `given a BigDecimal quantity that is not positive should report it`(
            quantity: String
        ) {
            val node = ValidationNode(path = "quantity", value = quantity.toBigDecimal()).apply { positive() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be positive", code = "positive")
            )
        }
    }

    @Nested
    @DisplayName("When negative is called")
    inner class Negative {
        @ParameterizedTest
        @ValueSource(ints = [-1, -10])
        fun `given a negative Int adjustment should accept it`(
            adjustment: Int
        ) {
            val node = ValidationNode(path = "adjustment", value = adjustment).apply { negative() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(longs = [-1, -10])
        fun `given a negative Long adjustment should accept it`(
            adjustment: Long
        ) {
            val node = ValidationNode(path = "adjustment", value = adjustment).apply { negative() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(shorts = [-1, -10])
        fun `given a negative Short adjustment should accept it`(
            adjustment: Short
        ) {
            val node = ValidationNode(path = "adjustment", value = adjustment).apply { negative() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(bytes = [-1, -10])
        fun `given a negative Byte adjustment should accept it`(
            adjustment: Byte
        ) {
            val node = ValidationNode(path = "adjustment", value = adjustment).apply { negative() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(doubles = [-1.0, -10.0])
        fun `given a negative Double adjustment should accept it`(
            adjustment: Double
        ) {
            val node = ValidationNode(path = "adjustment", value = adjustment).apply { negative() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(floats = [-1f, -10f])
        fun `given a negative Float adjustment should accept it`(
            adjustment: Float
        ) {
            val node = ValidationNode(path = "adjustment", value = adjustment).apply { negative() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["-1", "-10"])
        fun `given a negative BigInteger adjustment should accept it`(
            adjustment: String
        ) {
            val node = ValidationNode(path = "adjustment", value = BigInteger(adjustment)).apply { negative() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["-1.00", "-10.00"])
        fun `given a negative BigDecimal adjustment should accept it`(
            adjustment: String
        ) {
            val node = ValidationNode(path = "adjustment", value = adjustment.toBigDecimal()).apply { negative() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(ints = [0, 1])
        fun `given an Int adjustment that is not negative should report it`(
            adjustment: Int
        ) {
            val node = ValidationNode(path = "adjustment", value = adjustment).apply { negative() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "adjustment", message = "must be negative", code = "negative")
            )
        }

        @ParameterizedTest
        @ValueSource(longs = [0, 1])
        fun `given a Long adjustment that is not negative should report it`(
            adjustment: Long
        ) {
            val node = ValidationNode(path = "adjustment", value = adjustment).apply { negative() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "adjustment", message = "must be negative", code = "negative")
            )
        }

        @ParameterizedTest
        @ValueSource(shorts = [0, 1])
        fun `given a Short adjustment that is not negative should report it`(
            adjustment: Short
        ) {
            val node = ValidationNode(path = "adjustment", value = adjustment).apply { negative() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "adjustment", message = "must be negative", code = "negative")
            )
        }

        @ParameterizedTest
        @ValueSource(bytes = [0, 1])
        fun `given a Byte adjustment that is not negative should report it`(
            adjustment: Byte
        ) {
            val node = ValidationNode(path = "adjustment", value = adjustment).apply { negative() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "adjustment", message = "must be negative", code = "negative")
            )
        }

        @ParameterizedTest
        @ValueSource(doubles = [0.0, 1.0])
        fun `given a Double adjustment that is not negative should report it`(
            adjustment: Double
        ) {
            val node = ValidationNode(path = "adjustment", value = adjustment).apply { negative() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "adjustment", message = "must be negative", code = "negative")
            )
        }

        @ParameterizedTest
        @ValueSource(floats = [0f, 1f])
        fun `given a Float adjustment that is not negative should report it`(
            adjustment: Float
        ) {
            val node = ValidationNode(path = "adjustment", value = adjustment).apply { negative() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "adjustment", message = "must be negative", code = "negative")
            )
        }

        @ParameterizedTest
        @ValueSource(strings = ["0", "1"])
        fun `given a BigInteger adjustment that is not negative should report it`(
            adjustment: String
        ) {
            val node = ValidationNode(path = "adjustment", value = BigInteger(adjustment)).apply { negative() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "adjustment", message = "must be negative", code = "negative")
            )
        }

        @ParameterizedTest
        @ValueSource(strings = ["0.00", "1.00"])
        fun `given a BigDecimal adjustment that is not negative should report it`(
            adjustment: String
        ) {
            val node = ValidationNode(path = "adjustment", value = adjustment.toBigDecimal()).apply { negative() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "adjustment", message = "must be negative", code = "negative")
            )
        }
    }

    @Nested
    @DisplayName("When positiveOrZero is called")
    inner class PositiveOrZero {
        @ParameterizedTest
        @ValueSource(ints = [0, 1])
        fun `given a zero or positive Int balance should accept it`(
            balance: Int
        ) {
            val node = ValidationNode(path = "balance", value = balance).apply { positiveOrZero() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(longs = [0, 1])
        fun `given a zero or positive Long balance should accept it`(
            balance: Long
        ) {
            val node = ValidationNode(path = "balance", value = balance).apply { positiveOrZero() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(shorts = [0, 1])
        fun `given a zero or positive Short balance should accept it`(
            balance: Short
        ) {
            val node = ValidationNode(path = "balance", value = balance).apply { positiveOrZero() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(bytes = [0, 1])
        fun `given a zero or positive Byte balance should accept it`(
            balance: Byte
        ) {
            val node = ValidationNode(path = "balance", value = balance).apply { positiveOrZero() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(doubles = [0.0, 1.0])
        fun `given a zero or positive Double balance should accept it`(
            balance: Double
        ) {
            val node = ValidationNode(path = "balance", value = balance).apply { positiveOrZero() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(floats = [0f, 1f])
        fun `given a zero or positive Float balance should accept it`(
            balance: Float
        ) {
            val node = ValidationNode(path = "balance", value = balance).apply { positiveOrZero() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["0", "1"])
        fun `given a zero or positive BigInteger balance should accept it`(
            balance: String
        ) {
            val node = ValidationNode(path = "balance", value = BigInteger(balance)).apply { positiveOrZero() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["0.00", "1.00"])
        fun `given a zero or positive BigDecimal balance should accept it`(
            balance: String
        ) {
            val node = ValidationNode(path = "balance", value = balance.toBigDecimal()).apply { positiveOrZero() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(ints = [-1, -10])
        fun `given an Int balance below zero should report it`(
            balance: Int
        ) {
            val node = ValidationNode(path = "balance", value = balance).apply { positiveOrZero() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "balance", message = "must be positive or zero", code = "positiveOrZero")
            )
        }

        @ParameterizedTest
        @ValueSource(longs = [-1, -10])
        fun `given a Long balance below zero should report it`(
            balance: Long
        ) {
            val node = ValidationNode(path = "balance", value = balance).apply { positiveOrZero() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "balance", message = "must be positive or zero", code = "positiveOrZero")
            )
        }

        @ParameterizedTest
        @ValueSource(shorts = [-1, -10])
        fun `given a Short balance below zero should report it`(
            balance: Short
        ) {
            val node = ValidationNode(path = "balance", value = balance).apply { positiveOrZero() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "balance", message = "must be positive or zero", code = "positiveOrZero")
            )
        }

        @ParameterizedTest
        @ValueSource(bytes = [-1, -10])
        fun `given a Byte balance below zero should report it`(
            balance: Byte
        ) {
            val node = ValidationNode(path = "balance", value = balance).apply { positiveOrZero() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "balance", message = "must be positive or zero", code = "positiveOrZero")
            )
        }

        @ParameterizedTest
        @ValueSource(doubles = [-1.0, -10.0])
        fun `given a Double balance below zero should report it`(
            balance: Double
        ) {
            val node = ValidationNode(path = "balance", value = balance).apply { positiveOrZero() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "balance", message = "must be positive or zero", code = "positiveOrZero")
            )
        }

        @ParameterizedTest
        @ValueSource(floats = [-1f, -10f])
        fun `given a Float balance below zero should report it`(
            balance: Float
        ) {
            val node = ValidationNode(path = "balance", value = balance).apply { positiveOrZero() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "balance", message = "must be positive or zero", code = "positiveOrZero")
            )
        }

        @ParameterizedTest
        @ValueSource(strings = ["-1", "-10"])
        fun `given a BigInteger balance below zero should report it`(
            balance: String
        ) {
            val node = ValidationNode(path = "balance", value = BigInteger(balance)).apply { positiveOrZero() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "balance", message = "must be positive or zero", code = "positiveOrZero")
            )
        }

        @ParameterizedTest
        @ValueSource(strings = ["-1.00", "-10.00"])
        fun `given a BigDecimal balance below zero should report it`(
            balance: String
        ) {
            val node = ValidationNode(path = "balance", value = balance.toBigDecimal()).apply { positiveOrZero() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "balance", message = "must be positive or zero", code = "positiveOrZero")
            )
        }
    }

    @Nested
    @DisplayName("When negativeOrZero is called")
    inner class NegativeOrZero {
        @ParameterizedTest
        @ValueSource(ints = [0, -1])
        fun `given a zero or negative Int debit should accept it`(
            debit: Int
        ) {
            val node = ValidationNode(path = "debit", value = debit).apply { negativeOrZero() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(longs = [0, -1])
        fun `given a zero or negative Long debit should accept it`(
            debit: Long
        ) {
            val node = ValidationNode(path = "debit", value = debit).apply { negativeOrZero() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(shorts = [0, -1])
        fun `given a zero or negative Short debit should accept it`(
            debit: Short
        ) {
            val node = ValidationNode(path = "debit", value = debit).apply { negativeOrZero() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(bytes = [0, -1])
        fun `given a zero or negative Byte debit should accept it`(
            debit: Byte
        ) {
            val node = ValidationNode(path = "debit", value = debit).apply { negativeOrZero() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(doubles = [0.0, -1.0])
        fun `given a zero or negative Double debit should accept it`(
            debit: Double
        ) {
            val node = ValidationNode(path = "debit", value = debit).apply { negativeOrZero() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(floats = [0f, -1f])
        fun `given a zero or negative Float debit should accept it`(
            debit: Float
        ) {
            val node = ValidationNode(path = "debit", value = debit).apply { negativeOrZero() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["0", "-1"])
        fun `given a zero or negative BigInteger debit should accept it`(
            debit: String
        ) {
            val node = ValidationNode(path = "debit", value = BigInteger(debit)).apply { negativeOrZero() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["0.00", "-1.00"])
        fun `given a zero or negative BigDecimal debit should accept it`(
            debit: String
        ) {
            val node = ValidationNode(path = "debit", value = debit.toBigDecimal()).apply { negativeOrZero() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(ints = [1, 10])
        fun `given an Int debit above zero should report it`(
            debit: Int
        ) {
            val node = ValidationNode(path = "debit", value = debit).apply { negativeOrZero() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "debit", message = "must be negative or zero", code = "negativeOrZero")
            )
        }

        @ParameterizedTest
        @ValueSource(longs = [1, 10])
        fun `given a Long debit above zero should report it`(
            debit: Long
        ) {
            val node = ValidationNode(path = "debit", value = debit).apply { negativeOrZero() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "debit", message = "must be negative or zero", code = "negativeOrZero")
            )
        }

        @ParameterizedTest
        @ValueSource(shorts = [1, 10])
        fun `given a Short debit above zero should report it`(
            debit: Short
        ) {
            val node = ValidationNode(path = "debit", value = debit).apply { negativeOrZero() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "debit", message = "must be negative or zero", code = "negativeOrZero")
            )
        }

        @ParameterizedTest
        @ValueSource(bytes = [1, 10])
        fun `given a Byte debit above zero should report it`(
            debit: Byte
        ) {
            val node = ValidationNode(path = "debit", value = debit).apply { negativeOrZero() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "debit", message = "must be negative or zero", code = "negativeOrZero")
            )
        }

        @ParameterizedTest
        @ValueSource(doubles = [1.0, 10.0])
        fun `given a Double debit above zero should report it`(
            debit: Double
        ) {
            val node = ValidationNode(path = "debit", value = debit).apply { negativeOrZero() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "debit", message = "must be negative or zero", code = "negativeOrZero")
            )
        }

        @ParameterizedTest
        @ValueSource(floats = [1f, 10f])
        fun `given a Float debit above zero should report it`(
            debit: Float
        ) {
            val node = ValidationNode(path = "debit", value = debit).apply { negativeOrZero() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "debit", message = "must be negative or zero", code = "negativeOrZero")
            )
        }

        @ParameterizedTest
        @ValueSource(strings = ["1", "10"])
        fun `given a BigInteger debit above zero should report it`(
            debit: String
        ) {
            val node = ValidationNode(path = "debit", value = BigInteger(debit)).apply { negativeOrZero() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "debit", message = "must be negative or zero", code = "negativeOrZero")
            )
        }

        @ParameterizedTest
        @ValueSource(strings = ["1.00", "10.00"])
        fun `given a BigDecimal debit above zero should report it`(
            debit: String
        ) {
            val node = ValidationNode(path = "debit", value = debit.toBigDecimal()).apply { negativeOrZero() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "debit", message = "must be negative or zero", code = "negativeOrZero")
            )
        }
    }

    @Nested
    @DisplayName("When multipleOf is called")
    inner class MultipleOf {
        @ParameterizedTest
        @ValueSource(doubles = [0.3, 10.05, -0.15, 0.0])
        fun `given a Double price in steps of five cents should accept it`(
            price: Double
        ) {
            val node = ValidationNode(path = "price", value = price).apply { multipleOf(0.05) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(floats = [0.3f, 10.05f, -0.15f])
        fun `given a Float price in steps of five cents should accept it`(
            price: Float
        ) {
            val node = ValidationNode(path = "price", value = price).apply { multipleOf(0.05f) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(ints = [6, 12])
        fun `given an Int quantity that is a multiple of the factor should accept it`(
            quantity: Int
        ) {
            val node = ValidationNode(path = "quantity", value = quantity).apply { multipleOf(6) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(longs = [6, 12])
        fun `given a Long quantity that is a multiple of the factor should accept it`(
            quantity: Long
        ) {
            val node = ValidationNode(path = "quantity", value = quantity).apply { multipleOf(6L) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(shorts = [6, 12])
        fun `given a Short quantity that is a multiple of the factor should accept it`(
            quantity: Short
        ) {
            val node = ValidationNode(path = "quantity", value = quantity).apply { multipleOf(6) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(bytes = [6, 12])
        fun `given a Byte quantity that is a multiple of the factor should accept it`(
            quantity: Byte
        ) {
            val node = ValidationNode(path = "quantity", value = quantity).apply { multipleOf(6) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["6", "12"])
        fun `given a BigInteger quantity that is a multiple of the factor should accept it`(
            quantity: String
        ) {
            val node = ValidationNode(
                path = "quantity",
                value = BigInteger(quantity)
            ).apply { multipleOf(BigInteger("6")) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["10.05", "10.10"])
        fun `given a BigDecimal amount that is a multiple of the factor should accept it`(
            amount: String
        ) {
            val node = ValidationNode(
                path = "amount",
                value = amount.toBigDecimal()
            ).apply { multipleOf("0.05".toBigDecimal()) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(ints = [5, 7])
        fun `given an Int quantity that is not a multiple of the factor should report it`(
            quantity: Int
        ) {
            val node = ValidationNode(path = "quantity", value = quantity).apply { multipleOf(6) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be a multiple of 6", code = "multipleOf")
            )
        }

        @ParameterizedTest
        @ValueSource(longs = [5, 7])
        fun `given a Long quantity that is not a multiple of the factor should report it`(
            quantity: Long
        ) {
            val node = ValidationNode(path = "quantity", value = quantity).apply { multipleOf(6L) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be a multiple of 6", code = "multipleOf")
            )
        }

        @ParameterizedTest
        @ValueSource(shorts = [5, 7])
        fun `given a Short quantity that is not a multiple of the factor should report it`(
            quantity: Short
        ) {
            val node = ValidationNode(path = "quantity", value = quantity).apply { multipleOf(6) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be a multiple of 6", code = "multipleOf")
            )
        }

        @ParameterizedTest
        @ValueSource(bytes = [5, 7])
        fun `given a Byte quantity that is not a multiple of the factor should report it`(
            quantity: Byte
        ) {
            val node = ValidationNode(path = "quantity", value = quantity).apply { multipleOf(6) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be a multiple of 6", code = "multipleOf")
            )
        }

        @ParameterizedTest
        @ValueSource(strings = ["5", "7"])
        fun `given a BigInteger quantity that is not a multiple of the factor should report it`(
            quantity: String
        ) {
            val node = ValidationNode(
                path = "quantity",
                value = BigInteger(quantity)
            ).apply { multipleOf(BigInteger("6")) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be a multiple of 6", code = "multipleOf")
            )
        }

        @ParameterizedTest
        @ValueSource(strings = ["10.03", "10.07"])
        fun `given a BigDecimal amount that is not a multiple of the factor should report it`(
            amount: String
        ) {
            val node = ValidationNode(
                path = "amount",
                value = amount.toBigDecimal()
            ).apply { multipleOf("0.05".toBigDecimal()) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "amount", message = "must be a multiple of 0.05", code = "multipleOf")
            )
        }

        @Test
        fun `given a zero Int factor should fail when the rule is declared`() {
            val error = shouldThrow<IllegalArgumentException> {
                ValidationNode(
                    path = "quantity",
                    value = 6
                ).apply { multipleOf(0) }
            }

            error.message shouldBe "multipleOf needs a factor other than 0"
        }

        @Test
        fun `given a zero Long factor should fail when the rule is declared`() {
            val error = shouldThrow<IllegalArgumentException> {
                ValidationNode(
                    path = "quantity",
                    value = 6L
                ).apply { multipleOf(0L) }
            }

            error.message shouldBe "multipleOf needs a factor other than 0"
        }

        @Test
        fun `given a zero Short factor should fail when the rule is declared`() {
            val error = shouldThrow<IllegalArgumentException> {
                ValidationNode<Short>(
                    path = "quantity",
                    value = 6
                ).apply { multipleOf(0) }
            }

            error.message shouldBe "multipleOf needs a factor other than 0"
        }

        @Test
        fun `given a zero Byte factor should fail when the rule is declared`() {
            val error = shouldThrow<IllegalArgumentException> {
                ValidationNode<Byte>(
                    path = "quantity",
                    value = 6
                ).apply { multipleOf(0) }
            }

            error.message shouldBe "multipleOf needs a factor other than 0"
        }

        @Test
        fun `given a zero BigInteger factor should fail when the rule is declared`() {
            val error = shouldThrow<IllegalArgumentException> {
                ValidationNode(
                    path = "quantity",
                    value = BigInteger("6")
                ).apply { multipleOf(BigInteger.ZERO) }
            }

            error.message shouldBe "multipleOf needs a factor other than 0"
        }

        @Test
        fun `given a zero BigDecimal factor should fail when the rule is declared`() {
            val error = shouldThrow<IllegalArgumentException> {
                ValidationNode(
                    path = "amount",
                    value = "6".toBigDecimal()
                ).apply { multipleOf("0.00".toBigDecimal()) }
            }

            error.message shouldBe "multipleOf needs a factor other than 0"
        }

        @ParameterizedTest
        @ValueSource(doubles = [10.03, Double.NaN, Double.POSITIVE_INFINITY])
        fun `given a Double price off the steps of five cents should report it`(
            price: Double
        ) {
            val node = ValidationNode(path = "price", value = price).apply { multipleOf(0.05) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "price", message = "must be a multiple of 0.05", code = "multipleOf")
            )
        }

        @ParameterizedTest
        @ValueSource(floats = [10.03f, Float.NaN])
        fun `given a Float price off the steps of five cents should report it`(
            price: Float
        ) {
            val node = ValidationNode(path = "price", value = price).apply { multipleOf(0.05f) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "price", message = "must be a multiple of 0.05", code = "multipleOf")
            )
        }

        @ParameterizedTest
        @ValueSource(doubles = [0.0, Double.NaN, Double.POSITIVE_INFINITY])
        fun `given a Double factor that is zero or not finite should fail when the rule is declared`(
            factor: Double
        ) {
            val error = shouldThrow<IllegalArgumentException> {
                ValidationNode(path = "price", value = 1.0).apply { multipleOf(factor) }
            }

            error.message shouldBe "multipleOf needs a finite factor other than 0"
        }

        @ParameterizedTest
        @ValueSource(floats = [0f, Float.NaN])
        fun `given a Float factor that is zero or not finite should fail when the rule is declared`(
            factor: Float
        ) {
            val error = shouldThrow<IllegalArgumentException> {
                ValidationNode(path = "price", value = 1f).apply { multipleOf(factor) }
            }

            error.message shouldBe "multipleOf needs a finite factor other than 0"
        }

        @Test
        fun `given a message function for an Int should build it from the value and the factor`() {
            val node = ValidationNode(path = "quantity", value = 7).apply {
                multipleOf(6) { factor -> "$this is not a multiple of $factor" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "7 is not a multiple of 6", code = "multipleOf")
            )
        }

        @Test
        fun `given a message function for a Long should build it from the value and the factor`() {
            val node = ValidationNode(path = "quantity", value = 7L).apply {
                multipleOf(6L) { factor -> "$this is not a multiple of $factor" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "7 is not a multiple of 6", code = "multipleOf")
            )
        }

        @Test
        fun `given a message function for a Short should build it from the value and the factor`() {
            val node = ValidationNode(path = "quantity", value = 7.toShort()).apply {
                multipleOf(6) { factor -> "$this is not a multiple of $factor" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "7 is not a multiple of 6", code = "multipleOf")
            )
        }

        @Test
        fun `given a message function for a Byte should build it from the value and the factor`() {
            val node = ValidationNode(path = "quantity", value = 7.toByte()).apply {
                multipleOf(6) { factor -> "$this is not a multiple of $factor" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "7 is not a multiple of 6", code = "multipleOf")
            )
        }

        @Test
        fun `given a message function for a BigInteger should build it from the value and the factor`() {
            val node = ValidationNode(path = "quantity", value = BigInteger("7")).apply {
                multipleOf(BigInteger("6")) { factor -> "$this is not a multiple of $factor" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "7 is not a multiple of 6", code = "multipleOf")
            )
        }

        @Test
        fun `given a message function for a BigDecimal should build it from the value and the factor`() {
            val node = ValidationNode(path = "amount", value = "10.03".toBigDecimal()).apply {
                multipleOf("0.05".toBigDecimal()) { factor -> "$this is not a multiple of $factor" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "amount", message = "10.03 is not a multiple of 0.05", code = "multipleOf")
            )
        }
    }

    @Nested
    @DisplayName("When integer is called")
    inner class Integer {
        @ParameterizedTest
        @ValueSource(doubles = [0.0, 3.0, -12.0, 1e20])
        fun `given a whole Double quantity should accept it`(
            quantity: Double
        ) {
            val node = ValidationNode(path = "quantity", value = quantity).apply { integer() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(floats = [0f, 3f, -12f])
        fun `given a whole Float quantity should accept it`(
            quantity: Float
        ) {
            val node = ValidationNode(path = "quantity", value = quantity).apply { integer() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["0", "3", "3.000", "-12", "1E+3", "0.00"])
        fun `given a whole BigDecimal quantity should accept it`(
            quantity: String
        ) {
            val node = ValidationNode(path = "quantity", value = quantity.toBigDecimal()).apply { integer() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(doubles = [2.5, -0.1, Double.NaN, Double.POSITIVE_INFINITY])
        fun `given a Double quantity with a fraction or not finite should report it`(
            quantity: Double
        ) {
            val node = ValidationNode(path = "quantity", value = quantity).apply { integer() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be an integer", code = "integer")
            )
        }

        @ParameterizedTest
        @ValueSource(floats = [2.5f, Float.NaN, Float.NEGATIVE_INFINITY])
        fun `given a Float quantity with a fraction or not finite should report it`(
            quantity: Float
        ) {
            val node = ValidationNode(path = "quantity", value = quantity).apply { integer() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be an integer", code = "integer")
            )
        }

        @ParameterizedTest
        @ValueSource(strings = ["2.5", "-0.10", "0.001"])
        fun `given a BigDecimal quantity with a fraction should report it`(
            quantity: String
        ) {
            val node = ValidationNode(path = "quantity", value = quantity.toBigDecimal()).apply { integer() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be an integer", code = "integer")
            )
        }
    }

    @Nested
    @DisplayName("When finite is called")
    inner class Finite {
        @ParameterizedTest
        @ValueSource(doubles = [0.0, 1.5, -1.5])
        fun `given a finite Double rate should accept it`(
            rate: Double
        ) {
            val node = ValidationNode(path = "rate", value = rate).apply { finite() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(floats = [0f, 1.5f, -1.5f])
        fun `given a finite Float rate should accept it`(
            rate: Float
        ) {
            val node = ValidationNode(path = "rate", value = rate).apply { finite() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(doubles = [Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY])
        fun `given a Double rate that is not finite should report it`(
            rate: Double
        ) {
            val node = ValidationNode(path = "rate", value = rate).apply { finite() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "rate", message = "must be finite", code = "finite")
            )
        }

        @ParameterizedTest
        @ValueSource(floats = [Float.NaN, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY])
        fun `given a Float rate that is not finite should report it`(
            rate: Float
        ) {
            val node = ValidationNode(path = "rate", value = rate).apply { finite() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "rate", message = "must be finite", code = "finite")
            )
        }
    }

    @Nested
    @DisplayName("When maxDecimalPlaces is called")
    inner class MaxDecimalPlaces {
        @ParameterizedTest
        @ValueSource(strings = ["10", "10.5", "10.50", "10.500"])
        fun `given an amount with at most the maximum decimal places should accept it`(
            amount: String
        ) {
            val node = ValidationNode(path = "amount", value = amount.toBigDecimal()).apply { maxDecimalPlaces(2) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(doubles = [10.0, 10.5, 10.25])
        fun `given a Double amount with at most the maximum decimal places should accept it`(
            amount: Double
        ) {
            val node = ValidationNode(path = "amount", value = amount).apply { maxDecimalPlaces(2) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(floats = [10f, 10.5f, 10.25f])
        fun `given a Float amount with at most the maximum decimal places should accept it`(
            amount: Float
        ) {
            val node = ValidationNode(path = "amount", value = amount).apply { maxDecimalPlaces(2) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["10.001", "0.125"])
        fun `given an amount with more decimal places than the maximum should report it`(
            amount: String
        ) {
            val node = ValidationNode(path = "amount", value = amount.toBigDecimal()).apply { maxDecimalPlaces(2) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "amount", message = "must have at most 2 decimal places", code = "maxDecimalPlaces")
            )
        }

        @ParameterizedTest
        @ValueSource(doubles = [10.001, 0.125, Double.NaN, Double.POSITIVE_INFINITY])
        fun `given a Double amount with more decimal places than the maximum or not finite should report it`(
            amount: Double
        ) {
            val node = ValidationNode(path = "amount", value = amount).apply { maxDecimalPlaces(2) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "amount", message = "must have at most 2 decimal places", code = "maxDecimalPlaces")
            )
        }

        @ParameterizedTest
        @ValueSource(floats = [10.001f, Float.NaN])
        fun `given a Float amount with more decimal places than the maximum or not finite should report it`(
            amount: Float
        ) {
            val node = ValidationNode(path = "amount", value = amount).apply { maxDecimalPlaces(2) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "amount", message = "must have at most 2 decimal places", code = "maxDecimalPlaces")
            )
        }

        @Test
        fun `given a message function should build it from the value and the maximum`() {
            val node = ValidationNode(path = "amount", value = "10.001".toBigDecimal()).apply {
                maxDecimalPlaces(2) { max -> "$this has more than $max decimal places" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "amount", message = "10.001 has more than 2 decimal places", code = "maxDecimalPlaces")
            )
        }
    }

    @Nested
    @DisplayName("When maxIntegerDigits is called")
    inner class MaxIntegerDigits {
        @ParameterizedTest
        @ValueSource(strings = ["100", "999.99", "0.5"])
        fun `given a percentage with at most the maximum integer digits should accept it`(
            percentage: String
        ) {
            val node = ValidationNode(
                path = "percentage",
                value = percentage.toBigDecimal()
            ).apply { maxIntegerDigits(3) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["1000", "1000.5"])
        fun `given a percentage with more integer digits than the maximum should report it`(
            percentage: String
        ) {
            val node = ValidationNode(
                path = "percentage",
                value = percentage.toBigDecimal()
            ).apply { maxIntegerDigits(3) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(
                    path = "percentage",
                    message = "must have at most 3 integer digits",
                    code = "maxIntegerDigits"
                )
            )
        }

        @Test
        fun `given a message function should build it from the value and the maximum`() {
            val node = ValidationNode(path = "percentage", value = "1000".toBigDecimal()).apply {
                maxIntegerDigits(3) { max -> "$this has more than $max integer digits" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(
                    path = "percentage",
                    message = "1000 has more than 3 integer digits",
                    code = "maxIntegerDigits"
                )
            )
        }
    }

    @Nested
    @DisplayName("When decimalPlaces is called")
    inner class DecimalPlaces {
        @ParameterizedTest
        @ValueSource(strings = ["10.50", "0.99", "-3.00"])
        fun `given a price written with exactly two decimal places should accept it`(
            price: String
        ) {
            val node = ValidationNode(path = "price", value = price.toBigDecimal()).apply { decimalPlaces(2) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["10.5", "10", "10.500", "1E+3"])
        fun `given a price written with another number of decimal places should report it`(
            price: String
        ) {
            val node = ValidationNode(path = "price", value = price.toBigDecimal()).apply { decimalPlaces(2) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "price", message = "must have exactly 2 decimal places", code = "decimalPlaces")
            )
        }

        @Test
        fun `given a message function should build it from the value and the count`() {
            val node = ValidationNode(path = "price", value = "10.5".toBigDecimal()).apply {
                decimalPlaces(2) { count -> "$this needs $count decimal places" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "price", message = "10.5 needs 2 decimal places", code = "decimalPlaces")
            )
        }
    }

    @Nested
    @DisplayName("When minDecimalPlaces is called")
    inner class MinDecimalPlaces {
        @ParameterizedTest
        @ValueSource(strings = ["10.5", "10.50", "0.125"])
        fun `given a rate written with at least one decimal place should accept it`(
            rate: String
        ) {
            val node = ValidationNode(path = "rate", value = rate.toBigDecimal()).apply { minDecimalPlaces(1) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["10", "1E+3"])
        fun `given a rate written without decimal places should report it`(
            rate: String
        ) {
            val node = ValidationNode(path = "rate", value = rate.toBigDecimal()).apply { minDecimalPlaces(1) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "rate", message = "must have at least 1 decimal places", code = "minDecimalPlaces")
            )
        }

        @Test
        fun `given a message function should build it from the value and the minimum`() {
            val node = ValidationNode(path = "rate", value = "10".toBigDecimal()).apply {
                minDecimalPlaces(1) { min -> "$this needs $min decimal place" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "rate", message = "10 needs 1 decimal place", code = "minDecimalPlaces")
            )
        }
    }

    @Nested
    @DisplayName("When minDigits is called")
    inner class MinDigits {
        @ParameterizedTest
        @ValueSource(ints = [1000, 99_999, -1000])
        fun `given an Int code with at least four digits should accept it`(
            code: Int
        ) {
            val node = ValidationNode(path = "code", value = code).apply { minDigits(4) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(longs = [1000L, 99_999L, -1000L])
        fun `given an Long code with at least four digits should accept it`(
            code: Long
        ) {
            val node = ValidationNode(path = "code", value = code).apply { minDigits(4) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a Short code with at least four digits should accept it`() {
            val node = ValidationNode(path = "code", value = 1000.toShort()).apply { minDigits(4) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a BigInteger code with at least four digits should accept it`() {
            val node = ValidationNode(path = "code", value = BigInteger("1000")).apply { minDigits(4) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a Byte code with at least three digits should accept it`() {
            val node = ValidationNode(path = "code", value = 100.toByte()).apply { minDigits(3) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(ints = [12, -999])
        fun `given an Int code without at least four digits should report it`(
            code: Int
        ) {
            val node = ValidationNode(path = "code", value = code).apply { minDigits(4) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "code", message = "must have at least 4 digits", code = "minDigits")
            )
        }

        @ParameterizedTest
        @ValueSource(longs = [12L, -999L])
        fun `given an Long code without at least four digits should report it`(
            code: Long
        ) {
            val node = ValidationNode(path = "code", value = code).apply { minDigits(4) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "code", message = "must have at least 4 digits", code = "minDigits")
            )
        }

        @Test
        fun `given a Byte code without at least four digits should report it`() {
            val node = ValidationNode(path = "code", value = 12.toByte()).apply { minDigits(4) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "code", message = "must have at least 4 digits", code = "minDigits")
            )
        }

        @Test
        fun `given a BigInteger code without at least four digits should report it`() {
            val node = ValidationNode(path = "code", value = BigInteger("-999")).apply { minDigits(4) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "code", message = "must have at least 4 digits", code = "minDigits")
            )
        }

        @Test
        fun `given a Short code without at least four digits should report it`() {
            val node = ValidationNode(path = "code", value = 12.toShort()).apply { minDigits(4) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "code", message = "must have at least 4 digits", code = "minDigits")
            )
        }

        @Test
        fun `given a message function should build it from the value and the digits`() {
            val node = ValidationNode(path = "code", value = 12).apply {
                minDigits(4) { digits -> "$this does not fit $digits digits" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "code", message = "12 does not fit 4 digits", code = "minDigits")
            )
        }
    }

    @Nested
    @DisplayName("When maxDigits is called")
    inner class MaxDigits {
        @ParameterizedTest
        @ValueSource(ints = [1234, 7, -9999])
        fun `given an Int code with at most four digits should accept it`(
            code: Int
        ) {
            val node = ValidationNode(path = "code", value = code).apply { maxDigits(4) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(longs = [1234L, 7L, -9999L])
        fun `given an Long code with at most four digits should accept it`(
            code: Long
        ) {
            val node = ValidationNode(path = "code", value = code).apply { maxDigits(4) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a Short code with at most four digits should accept it`() {
            val node = ValidationNode(path = "code", value = 1234.toShort()).apply { maxDigits(4) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a BigInteger code with at most four digits should accept it`() {
            val node = ValidationNode(path = "code", value = BigInteger("1234")).apply { maxDigits(4) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given any Byte code should accept it`() {
            val node = ValidationNode(path = "code", value = Byte.MIN_VALUE).apply { maxDigits(4) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a Byte code with at most three digits should accept it`() {
            val node = ValidationNode(path = "code", value = 100.toByte()).apply { maxDigits(3) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(ints = [12_345, -10_000])
        fun `given an Int code without at most four digits should report it`(
            code: Int
        ) {
            val node = ValidationNode(path = "code", value = code).apply { maxDigits(4) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "code", message = "must have at most 4 digits", code = "maxDigits")
            )
        }

        @ParameterizedTest
        @ValueSource(longs = [12_345L, -10_000L])
        fun `given an Long code without at most four digits should report it`(
            code: Long
        ) {
            val node = ValidationNode(path = "code", value = code).apply { maxDigits(4) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "code", message = "must have at most 4 digits", code = "maxDigits")
            )
        }

        @Test
        fun `given a BigInteger code without at most four digits should report it`() {
            val node = ValidationNode(path = "code", value = BigInteger("-10000")).apply { maxDigits(4) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "code", message = "must have at most 4 digits", code = "maxDigits")
            )
        }

        @Test
        fun `given a Short code without at most four digits should report it`() {
            val node = ValidationNode(path = "code", value = 12_345.toShort()).apply { maxDigits(4) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "code", message = "must have at most 4 digits", code = "maxDigits")
            )
        }

        @Test
        fun `given a Byte code without at most two digits should report it`() {
            val node = ValidationNode(path = "code", value = 100.toByte()).apply { maxDigits(2) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "code", message = "must have at most 2 digits", code = "maxDigits")
            )
        }

        @Test
        fun `given a message function should build it from the value and the digits`() {
            val node = ValidationNode(path = "code", value = 12_345).apply {
                maxDigits(4) { digits -> "$this does not fit $digits digits" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "code", message = "12345 does not fit 4 digits", code = "maxDigits")
            )
        }
    }

    @Nested
    @DisplayName("When digitCount is called")
    inner class DigitCount {
        @ParameterizedTest
        @ValueSource(ints = [1234, 1000, -9999])
        fun `given an Int code with exactly four digits should accept it`(
            code: Int
        ) {
            val node = ValidationNode(path = "code", value = code).apply { digitCount(4) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(longs = [1234L, 1000L, -9999L])
        fun `given an Long code with exactly four digits should accept it`(
            code: Long
        ) {
            val node = ValidationNode(path = "code", value = code).apply { digitCount(4) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a Short code with exactly four digits should accept it`() {
            val node = ValidationNode(path = "code", value = 1234.toShort()).apply { digitCount(4) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a BigInteger code with exactly four digits should accept it`() {
            val node = ValidationNode(path = "code", value = BigInteger("1234")).apply { digitCount(4) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a Byte code with exactly three digits should accept it`() {
            val node = ValidationNode(path = "code", value = 100.toByte()).apply { digitCount(3) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(ints = [123, 12_345])
        fun `given an Int code without exactly four digits should report it`(
            code: Int
        ) {
            val node = ValidationNode(path = "code", value = code).apply { digitCount(4) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "code", message = "must have exactly 4 digits", code = "digitCount")
            )
        }

        @ParameterizedTest
        @ValueSource(longs = [123L, 12_345L])
        fun `given an Long code without exactly four digits should report it`(
            code: Long
        ) {
            val node = ValidationNode(path = "code", value = code).apply { digitCount(4) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "code", message = "must have exactly 4 digits", code = "digitCount")
            )
        }

        @Test
        fun `given a Byte code without exactly four digits should report it`() {
            val node = ValidationNode(path = "code", value = 123.toByte()).apply { digitCount(4) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "code", message = "must have exactly 4 digits", code = "digitCount")
            )
        }

        @Test
        fun `given a BigInteger code without exactly four digits should report it`() {
            val node = ValidationNode(path = "code", value = BigInteger("12345")).apply { digitCount(4) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "code", message = "must have exactly 4 digits", code = "digitCount")
            )
        }

        @Test
        fun `given a Short code without exactly four digits should report it`() {
            val node = ValidationNode(path = "code", value = 123.toShort()).apply { digitCount(4) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "code", message = "must have exactly 4 digits", code = "digitCount")
            )
        }

        @Test
        fun `given a message function should build it from the value and the digits`() {
            val node = ValidationNode(path = "code", value = 123).apply {
                digitCount(4) { digits -> "$this does not fit $digits digits" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "code", message = "123 does not fit 4 digits", code = "digitCount")
            )
        }
    }
}
