package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class DurationConstraintsTest {
    @Nested
    @DisplayName("When min is called")
    inner class Min {
        @Test
        fun `given a Duration below the minimum should report it`() {
            val node = ValidationNode("timeout", 9.minutes).apply { min(10.minutes) }

            node.validate() shouldBe listOf(Violation("timeout", "must be at least 10m"))
        }

        @Test
        fun `given a Duration at the minimum should accept it`() {
            val node = ValidationNode("timeout", 10.minutes).apply { min(10.minutes) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no minimum for a Duration should skip the check`() {
            val minimum: Duration? = null

            val node = ValidationNode("timeout", 9.minutes).apply { min(minimum) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a Duration should build it from the value and the minimum`() {
            val node = ValidationNode("timeout", 9.minutes).apply { min(10.minutes) { min -> "$this is below $min" } }

            node.validate() shouldBe listOf(Violation("timeout", "9m is below 10m"))
        }
    }

    @Nested
    @DisplayName("When max is called")
    inner class Max {
        @Test
        fun `given a Duration above the maximum should report it`() {
            val node = ValidationNode("timeout", 11.minutes).apply { max(10.minutes) }

            node.validate() shouldBe listOf(Violation("timeout", "must be at most 10m"))
        }

        @Test
        fun `given a Duration at the maximum should accept it`() {
            val node = ValidationNode("timeout", 10.minutes).apply { max(10.minutes) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no maximum for a Duration should skip the check`() {
            val maximum: Duration? = null

            val node = ValidationNode("timeout", 11.minutes).apply { max(maximum) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a Duration should build it from the value and the maximum`() {
            val node = ValidationNode("timeout", 11.minutes).apply { max(10.minutes) { max -> "$this is above $max" } }

            node.validate() shouldBe listOf(Violation("timeout", "11m is above 10m"))
        }
    }

    @Nested
    @DisplayName("When greaterThan is called")
    inner class GreaterThan {
        @Test
        fun `given a Duration equal to the reference should report it`() {
            val node = ValidationNode("timeout", 10.minutes).apply { greaterThan(10.minutes) }

            node.validate() shouldBe listOf(Violation("timeout", "must be greater than 10m"))
        }

        @Test
        fun `given a Duration above the reference should accept it`() {
            val node = ValidationNode("timeout", 11.minutes).apply { greaterThan(10.minutes) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for a Duration should skip the check`() {
            val reference: Duration? = null

            val node = ValidationNode("timeout", 10.minutes).apply { greaterThan(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a Duration should build it from the value and the reference`() {
            val node = ValidationNode("timeout", 10.minutes).apply {
                greaterThan(10.minutes) { other -> "$this is not greater than $other" }
            }

            node.validate() shouldBe listOf(Violation("timeout", "10m is not greater than 10m"))
        }
    }

    @Nested
    @DisplayName("When lessThan is called")
    inner class LessThan {
        @Test
        fun `given a Duration equal to the reference should report it`() {
            val node = ValidationNode("timeout", 10.minutes).apply { lessThan(10.minutes) }

            node.validate() shouldBe listOf(Violation("timeout", "must be less than 10m"))
        }

        @Test
        fun `given a Duration below the reference should accept it`() {
            val node = ValidationNode("timeout", 9.minutes).apply { lessThan(10.minutes) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for a Duration should skip the check`() {
            val reference: Duration? = null

            val node = ValidationNode("timeout", 10.minutes).apply { lessThan(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a Duration should build it from the value and the reference`() {
            val node = ValidationNode("timeout", 10.minutes).apply {
                lessThan(10.minutes) { other -> "$this is not less than $other" }
            }

            node.validate() shouldBe listOf(Violation("timeout", "10m is not less than 10m"))
        }
    }

    @Nested
    @DisplayName("When between is called")
    inner class Between {
        @Test
        fun `given a Duration below the range should report it`() {
            val node = ValidationNode("timeout", 8.minutes).apply { between(9.minutes, 11.minutes) }

            node.validate() shouldBe listOf(Violation("timeout", "must be between 9m and 11m"))
        }

        @Test
        fun `given a Duration above the range should report it`() {
            val node = ValidationNode("timeout", 12.minutes).apply { between(9.minutes, 11.minutes) }

            node.validate() shouldBe listOf(Violation("timeout", "must be between 9m and 11m"))
        }

        @Test
        fun `given a Duration at the lower limit should accept it`() {
            val node = ValidationNode("timeout", 9.minutes).apply { between(9.minutes, 11.minutes) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a Duration inside the range should accept it`() {
            val node = ValidationNode("timeout", 10.minutes).apply { between(9.minutes, 11.minutes) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a Duration at the upper limit should accept it`() {
            val node = ValidationNode("timeout", 11.minutes).apply { between(9.minutes, 11.minutes) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no minimum for a Duration should skip the check`() {
            val min: Duration? = null

            val node = ValidationNode("timeout", 12.minutes).apply { between(min, 11.minutes) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a Duration should build it from the value and the limits`() {
            val node = ValidationNode("timeout", 12.minutes).apply {
                between(9.minutes, 11.minutes) { min, max -> "$this is not from $min to $max" }
            }

            node.validate() shouldBe listOf(Violation("timeout", "12m is not from 9m to 11m"))
        }
    }
}
