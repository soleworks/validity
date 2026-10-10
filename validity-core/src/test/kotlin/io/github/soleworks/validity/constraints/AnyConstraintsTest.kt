package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.github.soleworks.validity.samples.Survey
import io.github.soleworks.validity.validate
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class AnyConstraintsTest {
    @Nested
    @DisplayName("When notNull is called")
    inner class NotNull {
        @Test
        fun `given a missing comment should report it as required`() {
            val survey = Survey(comments = listOf("great service", null))

            survey.validate().violations shouldBe listOf(Violation("comments[1]", "is required", "notNull"))
        }

        @Test
        fun `given an unanswered question should report the custom message`() {
            val survey = Survey(answers = listOf("yes", null))

            survey.validate().violations shouldBe listOf(Violation("answers[1]", "must be answered", "notNull"))
        }
    }

    @Nested
    @DisplayName("When equalTo is called")
    inner class EqualTo {
        @Test
        fun `given a currency different from the account currency should report it`() {
            val node = ValidationNode("currency", "USD").apply { equalTo("BRL") }

            node.validate() shouldBe listOf(Violation("currency", "must be equal to BRL", "equalTo"))
        }

        @Test
        fun `given the account currency should accept it`() {
            val node = ValidationNode("currency", "BRL").apply { equalTo("BRL") }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given an account without a currency should skip the comparison`() {
            val accountCurrency: String? = null

            val node = ValidationNode("currency", "USD").apply { equalTo(accountCurrency) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function should build it from the value and the expected value`() {
            val node = ValidationNode("currency", "USD").apply { equalTo("BRL") { other -> "$this is not $other" } }

            node.validate() shouldBe listOf(Violation("currency", "USD is not BRL", "equalTo"))
        }
    }

    @Nested
    @DisplayName("When notEqualTo is called")
    inner class NotEqualTo {
        @Test
        fun `given a destination equal to the source account should report it`() {
            val node = ValidationNode("destinationAccount", "0001-1").apply { notEqualTo("0001-1") }

            node.validate() shouldBe listOf(Violation("destinationAccount", "must not be equal to 0001-1", "notEqualTo"))
        }

        @Test
        fun `given a destination different from the source account should accept it`() {
            val node = ValidationNode("destinationAccount", "0002-2").apply { notEqualTo("0001-1") }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a transfer without a source account should skip the comparison`() {
            val sourceAccount: String? = null

            val node = ValidationNode("destinationAccount", "0001-1").apply { notEqualTo(sourceAccount) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function should build it from the value and the other value`() {
            val node = ValidationNode("destinationAccount", "0001-1").apply {
                notEqualTo("0001-1") { other -> "$this is the source account $other" }
            }

            node.validate() shouldBe listOf(Violation("destinationAccount", "0001-1 is the source account 0001-1", "notEqualTo"))
        }
    }

    @Nested
    @DisplayName("When oneOf is called")
    inner class OneOf {
        @Test
        fun `given a status outside the allowed ones should report it`() {
            val node = ValidationNode("status", "CANCELLED").apply { oneOf(listOf("PENDING", "PAID")) }

            node.validate() shouldBe listOf(Violation("status", "must be one of PENDING, PAID", "oneOf"))
        }

        @Test
        fun `given an allowed status should accept it`() {
            val node = ValidationNode("status", "PAID").apply { oneOf(listOf("PENDING", "PAID")) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no allowed statuses should skip the check`() {
            val allowedStatuses: List<String>? = null

            val node = ValidationNode("status", "CANCELLED").apply { oneOf(allowedStatuses) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function should build it from the value and the allowed values`() {
            val node = ValidationNode("status", "CANCELLED").apply {
                oneOf(listOf("PENDING", "PAID")) { values -> "$this is not in $values" }
            }

            node.validate() shouldBe listOf(Violation("status", "CANCELLED is not in [PENDING, PAID]", "oneOf"))
        }
    }

    @Nested
    @DisplayName("When noneOf is called")
    inner class NoneOf {
        @Test
        fun `given a reserved username should report it`() {
            val node = ValidationNode("username", "admin").apply { noneOf(listOf("admin", "root")) }

            node.validate() shouldBe listOf(Violation("username", "must not be one of admin, root", "noneOf"))
        }

        @Test
        fun `given a username that is not reserved should accept it`() {
            val node = ValidationNode("username", "ana").apply { noneOf(listOf("admin", "root")) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reserved usernames should skip the check`() {
            val reservedUsernames: List<String>? = null

            val node = ValidationNode("username", "admin").apply { noneOf(reservedUsernames) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function should build it from the value and the forbidden values`() {
            val node = ValidationNode("username", "admin").apply {
                noneOf(listOf("admin", "root")) { values -> "$this is reserved, avoid $values" }
            }

            node.validate() shouldBe listOf(Violation("username", "admin is reserved, avoid [admin, root]", "noneOf"))
        }
    }
}
