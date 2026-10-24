package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import java.time.OffsetDateTime
import java.time.OffsetTime
import java.time.ZonedDateTime
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class AnyConstraintsTest {
    @Nested
    @DisplayName("When notNull is called")
    inner class NotNull {
        @Test
        fun `given a missing comment should report it as required`() {
            val node = ValidationNode<String?>(path = "comment", value = null).apply { notNull() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "comment", message = "is required", code = "notNull")
            )
        }

        @Test
        fun `given an unanswered question should report the custom message`() {
            val node = ValidationNode<String?>(path = "answer", value = null).apply { notNull("must be answered") }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "answer", message = "must be answered", code = "notNull")
            )
        }
    }

    @Nested
    @DisplayName("When equalTo is called")
    inner class EqualTo {
        @Test
        fun `given the account currency should accept it`() {
            val node = ValidationNode(path = "currency", value = "BRL").apply { equalTo("BRL") }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given the same instant in another zone should accept it`() {
            val node = ValidationNode(
                path = "startsAt",
                value = ZonedDateTime.parse("2026-10-12T10:00-03:00[America/Sao_Paulo]")
            ).apply {
                equalTo(ZonedDateTime.parse("2026-10-12T13:00Z[UTC]"))
            }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given the same instant with another offset should accept it`() {
            val node = ValidationNode(path = "startsAt", value = OffsetDateTime.parse("2026-10-12T10:00-03:00")).apply {
                equalTo(OffsetDateTime.parse("2026-10-12T13:00Z"))
            }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given the same time with another offset should accept it`() {
            val node = ValidationNode(path = "opensAt", value = OffsetTime.parse("10:00-03:00")).apply {
                equalTo(OffsetTime.parse("13:00Z"))
            }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a currency different from the account currency should report it`() {
            val node = ValidationNode(path = "currency", value = "USD").apply { equalTo("BRL") }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "currency", message = "must be equal to BRL", code = "equalTo")
            )
        }

        @Test
        fun `given an amount with another scale should compare it by value`() {
            val node = ValidationNode(
                path = "amount",
                value = "10.00".toBigDecimal()
            ).apply { equalTo("10.0".toBigDecimal()) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a different amount should report it`() {
            val node = ValidationNode(
                path = "amount",
                value = "10.01".toBigDecimal()
            ).apply { equalTo("10.0".toBigDecimal()) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "amount", message = "must be equal to 10.0", code = "equalTo")
            )
        }

        @Test
        fun `given another instant should report it`() {
            val node = ValidationNode(path = "startsAt", value = ZonedDateTime.parse("2026-10-12T10:00Z[UTC]")).apply {
                equalTo(ZonedDateTime.parse("2026-10-12T10:00-03:00[America/Sao_Paulo]"))
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(
                    path = "startsAt",
                    message = "must be equal to 2026-10-12T10:00-03:00[America/Sao_Paulo]",
                    code = "equalTo"
                )
            )
        }

        @Test
        fun `given an account without a currency should skip the comparison`() {
            val accountCurrency: String? = null
            val node = ValidationNode(path = "currency", value = "USD").apply { equalTo(accountCurrency) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a message function should build it from the value and the expected value`() {
            val node = ValidationNode(
                path = "currency",
                value = "USD"
            ).apply { equalTo("BRL") { other -> "$this is not $other" } }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "currency", message = "USD is not BRL", code = "equalTo")
            )
        }
    }

    @Nested
    @DisplayName("When notEqualTo is called")
    inner class NotEqualTo {
        @Test
        fun `given a destination different from the source account should accept it`() {
            val node = ValidationNode(path = "destinationAccount", value = "0002-2").apply { notEqualTo("0001-1") }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a destination equal to the source account should report it`() {
            val node = ValidationNode(path = "destinationAccount", value = "0001-1").apply { notEqualTo("0001-1") }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "destinationAccount", message = "must not be equal to 0001-1", code = "notEqualTo")
            )
        }

        @Test
        fun `given an amount with another scale should compare it by value`() {
            val node = ValidationNode(
                path = "amount",
                value = "10.00".toBigDecimal()
            ).apply { notEqualTo("10.0".toBigDecimal()) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "amount", message = "must not be equal to 10.0", code = "notEqualTo")
            )
        }

        @Test
        fun `given the same instant in another zone should report it`() {
            val node = ValidationNode(
                path = "startsAt",
                value = ZonedDateTime.parse("2026-10-12T10:00-03:00[America/Sao_Paulo]")
            ).apply {
                notEqualTo(ZonedDateTime.parse("2026-10-12T13:00Z[UTC]"))
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(
                    path = "startsAt",
                    message = "must not be equal to 2026-10-12T13:00Z[UTC]",
                    code = "notEqualTo"
                )
            )
        }

        @Test
        fun `given a transfer without a source account should skip the comparison`() {
            val sourceAccount: String? = null
            val node = ValidationNode(path = "destinationAccount", value = "0001-1").apply { notEqualTo(sourceAccount) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a message function should build it from the value and the other value`() {
            val node = ValidationNode(path = "destinationAccount", value = "0001-1").apply {
                notEqualTo("0001-1") { other -> "$this is the source account $other" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(
                    path = "destinationAccount",
                    message = "0001-1 is the source account 0001-1",
                    code = "notEqualTo"
                )
            )
        }
    }

    @Nested
    @DisplayName("When oneOf is called")
    inner class OneOf {
        @Test
        fun `given an allowed status should accept it`() {
            val node = ValidationNode(path = "status", value = "PAID").apply { oneOf(listOf("PENDING", "PAID")) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a status outside the allowed ones should report it`() {
            val node = ValidationNode(path = "status", value = "CANCELLED").apply { oneOf(listOf("PENDING", "PAID")) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "status", message = "must be one of PENDING, PAID", code = "oneOf")
            )
        }

        @Test
        fun `given an amount with another scale should compare it by value`() {
            val node = ValidationNode(
                path = "amount",
                value = "10.00".toBigDecimal()
            ).apply { oneOf(listOf("10.0".toBigDecimal(), "20".toBigDecimal())) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no allowed statuses should skip the check`() {
            val allowedStatuses: List<String>? = null
            val node = ValidationNode(path = "status", value = "CANCELLED").apply { oneOf(allowedStatuses) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a message function should build it from the value and the allowed values`() {
            val node = ValidationNode(path = "status", value = "CANCELLED").apply {
                oneOf(listOf("PENDING", "PAID")) { values -> "$this is not in $values" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "status", message = "CANCELLED is not in [PENDING, PAID]", code = "oneOf")
            )
        }

        @Test
        fun `given a set with its own comparison should use it`() {
            val node = ValidationNode(
                path = "role",
                value = "ADMIN"
            ).apply { oneOf(sortedSetOf(String.CASE_INSENSITIVE_ORDER, "admin", "user")) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }
    }

    @Nested
    @DisplayName("When noneOf is called")
    inner class NoneOf {
        @Test
        fun `given a username that is not reserved should accept it`() {
            val node = ValidationNode(path = "username", value = "ana").apply { noneOf(listOf("admin", "root")) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a reserved username should report it`() {
            val node = ValidationNode(path = "username", value = "admin").apply { noneOf(listOf("admin", "root")) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "username", message = "must not be one of admin, root", code = "noneOf")
            )
        }

        @Test
        fun `given an amount with another scale should compare it by value`() {
            val node = ValidationNode(
                path = "amount",
                value = "10.00".toBigDecimal()
            ).apply { noneOf(listOf("10.0".toBigDecimal())) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "amount", message = "must not be one of 10.0", code = "noneOf")
            )
        }

        @Test
        fun `given no reserved usernames should skip the check`() {
            val reservedUsernames: List<String>? = null
            val node = ValidationNode(path = "username", value = "admin").apply { noneOf(reservedUsernames) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a message function should build it from the value and the forbidden values`() {
            val node = ValidationNode(path = "username", value = "admin").apply {
                noneOf(listOf("admin", "root")) { values -> "$this is reserved, avoid $values" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "username", message = "admin is reserved, avoid [admin, root]", code = "noneOf")
            )
        }
    }
}
