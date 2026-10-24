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
        fun `given a Duration at the minimum should accept it`() {
            val node = ValidationNode(path = "timeout", value = 10.minutes).apply { min(10.minutes) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a Duration below the minimum should report it`() {
            val node = ValidationNode(path = "timeout", value = 9.minutes).apply { min(10.minutes) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "timeout", message = "must be at least 10m", code = "min")
            )
        }

        @Test
        fun `given no minimum for a Duration should skip the check`() {
            val minimum: Duration? = null
            val node = ValidationNode(path = "timeout", value = 9.minutes).apply { min(minimum) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a message function for a Duration should build it from the value and the minimum`() {
            val node = ValidationNode(
                path = "timeout",
                value = 9.minutes
            ).apply { min(10.minutes) { min -> "$this is below $min" } }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "timeout", message = "9m is below 10m", code = "min")
            )
        }
    }

    @Nested
    @DisplayName("When max is called")
    inner class Max {
        @Test
        fun `given a Duration at the maximum should accept it`() {
            val node = ValidationNode(path = "timeout", value = 10.minutes).apply { max(10.minutes) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a Duration above the maximum should report it`() {
            val node = ValidationNode(path = "timeout", value = 11.minutes).apply { max(10.minutes) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "timeout", message = "must be at most 10m", code = "max")
            )
        }

        @Test
        fun `given no maximum for a Duration should skip the check`() {
            val maximum: Duration? = null
            val node = ValidationNode(path = "timeout", value = 11.minutes).apply { max(maximum) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a message function for a Duration should build it from the value and the maximum`() {
            val node = ValidationNode(
                path = "timeout",
                value = 11.minutes
            ).apply { max(10.minutes) { max -> "$this is above $max" } }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "timeout", message = "11m is above 10m", code = "max")
            )
        }
    }

    @Nested
    @DisplayName("When greaterThan is called")
    inner class GreaterThan {
        @Test
        fun `given a Duration above the reference should accept it`() {
            val node = ValidationNode(path = "timeout", value = 11.minutes).apply { greaterThan(10.minutes) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a Duration equal to the reference should report it`() {
            val node = ValidationNode(path = "timeout", value = 10.minutes).apply { greaterThan(10.minutes) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "timeout", message = "must be greater than 10m", code = "greaterThan")
            )
        }

        @Test
        fun `given no reference for a Duration should skip the check`() {
            val reference: Duration? = null
            val node = ValidationNode(path = "timeout", value = 10.minutes).apply { greaterThan(reference) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a message function for a Duration should build it from the value and the reference`() {
            val node = ValidationNode(path = "timeout", value = 10.minutes).apply {
                greaterThan(10.minutes) { other -> "$this is not greater than $other" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "timeout", message = "10m is not greater than 10m", code = "greaterThan")
            )
        }
    }

    @Nested
    @DisplayName("When lessThan is called")
    inner class LessThan {
        @Test
        fun `given a Duration below the reference should accept it`() {
            val node = ValidationNode(path = "timeout", value = 9.minutes).apply { lessThan(10.minutes) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a Duration equal to the reference should report it`() {
            val node = ValidationNode(path = "timeout", value = 10.minutes).apply { lessThan(10.minutes) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "timeout", message = "must be less than 10m", code = "lessThan")
            )
        }

        @Test
        fun `given no reference for a Duration should skip the check`() {
            val reference: Duration? = null
            val node = ValidationNode(path = "timeout", value = 10.minutes).apply { lessThan(reference) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a message function for a Duration should build it from the value and the reference`() {
            val node = ValidationNode(path = "timeout", value = 10.minutes).apply {
                lessThan(10.minutes) { other -> "$this is not less than $other" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "timeout", message = "10m is not less than 10m", code = "lessThan")
            )
        }
    }

    @Nested
    @DisplayName("When between is called")
    inner class Between {
        @Test
        fun `given a Duration at the lower limit should accept it`() {
            val node = ValidationNode(
                path = "timeout",
                value = 9.minutes
            ).apply { between(min = 9.minutes, max = 11.minutes) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a Duration inside the range should accept it`() {
            val node = ValidationNode(
                path = "timeout",
                value = 10.minutes
            ).apply { between(min = 9.minutes, max = 11.minutes) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a Duration at the upper limit should accept it`() {
            val node = ValidationNode(
                path = "timeout",
                value = 11.minutes
            ).apply { between(min = 9.minutes, max = 11.minutes) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a Duration below the range should report it`() {
            val node = ValidationNode(
                path = "timeout",
                value = 8.minutes
            ).apply { between(min = 9.minutes, max = 11.minutes) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "timeout", message = "must be between 9m and 11m", code = "between")
            )
        }

        @Test
        fun `given a Duration above the range should report it`() {
            val node = ValidationNode(
                path = "timeout",
                value = 12.minutes
            ).apply { between(min = 9.minutes, max = 11.minutes) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "timeout", message = "must be between 9m and 11m", code = "between")
            )
        }

        @Test
        fun `given no minimum for a Duration should skip the check`() {
            val min: Duration? = null
            val node = ValidationNode(
                path = "timeout",
                value = 12.minutes
            ).apply { between(min = min, max = 11.minutes) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a message function for a Duration should build it from the value and the limits`() {
            val node = ValidationNode(path = "timeout", value = 12.minutes).apply {
                between(min = 9.minutes, max = 11.minutes) { min, max -> "$this is not from $min to $max" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "timeout", message = "12m is not from 9m to 11m", code = "between")
            )
        }
    }
}
