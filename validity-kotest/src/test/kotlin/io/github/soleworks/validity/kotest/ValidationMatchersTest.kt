package io.github.soleworks.validity.kotest

import io.github.soleworks.validity.ValidationResult
import io.github.soleworks.validity.constraints.minLength
import io.github.soleworks.validity.constraints.notBlank
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ValidationMatchersTest {
    private fun signUp(
        name: String?,
        email: String?
    ): ValidationResult = validation {
        required("name", name) { minLength(2) }
        required("email", email) { notBlank() }
    }.validate()

    @Nested
    @DisplayName("When shouldBeValid is called")
    inner class ShouldBeValid {
        @Test
        fun `given a valid result should pass and return it`() {
            val result = signUp(name = "Ana", email = "ana@mail.com")

            result.shouldBeValid() shouldBe result
        }

        @Test
        fun `given an invalid result should fail listing its violations`() {
            val result = signUp(name = "A", email = null)

            val error = shouldThrow<AssertionError> { result.shouldBeValid() }

            error.message shouldBe "Expected a valid result, but it has 2 violations: " +
                "name: must have at least 2 characters (minLength), email: is required (required)"
        }

        @Test
        fun `given the matcher should work with should`() {
            signUp(name = "Ana", email = "ana@mail.com") should beValid()
        }
    }

    @Nested
    @DisplayName("When shouldBeInvalid is called")
    inner class ShouldBeInvalid {
        @Test
        fun `given an invalid result should pass`() {
            signUp(name = "A", email = "ana@mail.com").shouldBeInvalid()
        }

        @Test
        fun `given a valid result should fail`() {
            val result = signUp(name = "Ana", email = "ana@mail.com")

            val error = shouldThrow<AssertionError> { result.shouldBeInvalid() }

            error.message shouldBe "Expected an invalid result, but it has no violations"
        }
    }

    @Nested
    @DisplayName("When shouldHaveViolation is called")
    inner class ShouldHaveViolation {
        @Test
        fun `given a violation at the path should pass`() {
            signUp(name = "A", email = "ana@mail.com").shouldHaveViolation("name")
        }

        @Test
        fun `given a violation at the path with the code and the message should pass`() {
            signUp(name = "A", email = "ana@mail.com").shouldHaveViolation(
                path = "name",
                code = "minLength",
                message = "must have at least 2 characters"
            )
        }

        @Test
        fun `given another code at the path should fail describing what it expected and what it got`() {
            val result = signUp(name = "A", email = "ana@mail.com")

            val error = shouldThrow<AssertionError> { result.shouldHaveViolation(path = "name", code = "required") }

            error.message shouldBe "Expected a violation at name with code required, but the result has " +
                "1 violation: name: must have at least 2 characters (minLength)"
        }

        @Test
        fun `given another message at the path should fail`() {
            val result = signUp(name = "A", email = "ana@mail.com")

            shouldThrow<AssertionError> { result.shouldHaveViolation(path = "name", message = "too short") }
        }

        @Test
        fun `given no violation at the path should fail`() {
            val result = signUp(name = "Ana", email = null)

            val error = shouldThrow<AssertionError> { result.shouldHaveViolation("name") }

            error.message shouldBe "Expected a violation at name, but the result has 1 violation: email: is required (required)"
        }
    }

    @Nested
    @DisplayName("When shouldNotHaveViolation is called")
    inner class ShouldNotHaveViolation {
        @Test
        fun `given no violation at the path should pass`() {
            signUp(name = "Ana", email = null).shouldNotHaveViolation("name")
        }

        @Test
        fun `given a violation at the path with another code should pass`() {
            signUp(name = "A", email = "ana@mail.com").shouldNotHaveViolation(path = "name", code = "required")
        }

        @Test
        fun `given a violation at the path should fail`() {
            val result = signUp(name = "A", email = "ana@mail.com")

            val error = shouldThrow<AssertionError> { result.shouldNotHaveViolation("name") }

            error.message shouldBe "Expected no violation at name, but the result has " +
                "1 violation: name: must have at least 2 characters (minLength)"
        }
    }
}
