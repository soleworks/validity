package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.OffsetTime
import java.time.Year
import java.time.YearMonth
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZoneOffset.UTC
import java.time.ZonedDateTime
import java.util.Date
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class TemporalConstraintsTest {
    @Nested
    @DisplayName("When past is called")
    inner class Past {
        @Test
        fun `given a LocalDate in the future should report it`() {
            val node = ValidationNode("occurredAt", LocalDate.now().plusDays(1)).apply { past() }

            node.validate() shouldBe listOf(Violation("occurredAt", "must be in the past", "past"))
        }

        @Test
        fun `given a LocalDate in the past should accept it`() {
            val node = ValidationNode("occurredAt", LocalDate.now().minusDays(1)).apply { past() }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given today as a LocalDate should report it`() {
            val node = ValidationNode("occurredAt", LocalDate.now()).apply { past() }

            node.validate() shouldBe listOf(Violation("occurredAt", "must be in the past", "past"))
        }

        @Test
        fun `given a LocalDateTime in the future should report it`() {
            val node = ValidationNode("occurredAt", LocalDateTime.now().plusHours(1)).apply { past() }

            node.validate() shouldBe listOf(Violation("occurredAt", "must be in the past", "past"))
        }

        @Test
        fun `given a LocalDateTime in the past should accept it`() {
            val node = ValidationNode("occurredAt", LocalDateTime.now().minusHours(1)).apply { past() }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a ZonedDateTime in the future should report it`() {
            val node = ValidationNode("occurredAt", ZonedDateTime.now().plusHours(1)).apply { past() }

            node.validate() shouldBe listOf(Violation("occurredAt", "must be in the past", "past"))
        }

        @Test
        fun `given a ZonedDateTime in the past should accept it`() {
            val node = ValidationNode("occurredAt", ZonedDateTime.now().minusHours(1)).apply { past() }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given an OffsetDateTime in the future should report it`() {
            val node = ValidationNode("occurredAt", OffsetDateTime.now().plusHours(1)).apply { past() }

            node.validate() shouldBe listOf(Violation("occurredAt", "must be in the past", "past"))
        }

        @Test
        fun `given an OffsetDateTime in the past should accept it`() {
            val node = ValidationNode("occurredAt", OffsetDateTime.now().minusHours(1)).apply { past() }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given an Instant in the future should report it`() {
            val node = ValidationNode("occurredAt", Instant.now().plusSeconds(3600)).apply { past() }

            node.validate() shouldBe listOf(Violation("occurredAt", "must be in the past", "past"))
        }

        @Test
        fun `given an Instant in the past should accept it`() {
            val node = ValidationNode("occurredAt", Instant.now().minusSeconds(3600)).apply { past() }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a YearMonth in the future should report it`() {
            val node = ValidationNode("occurredAt", YearMonth.now().plusMonths(1)).apply { past() }

            node.validate() shouldBe listOf(Violation("occurredAt", "must be in the past", "past"))
        }

        @Test
        fun `given a YearMonth in the past should accept it`() {
            val node = ValidationNode("occurredAt", YearMonth.now().minusMonths(1)).apply { past() }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given the current month as a YearMonth should report it`() {
            val node = ValidationNode("occurredAt", YearMonth.now()).apply { past() }

            node.validate() shouldBe listOf(Violation("occurredAt", "must be in the past", "past"))
        }

        @Test
        fun `given a Year in the future should report it`() {
            val node = ValidationNode("occurredAt", Year.now().plusYears(1)).apply { past() }

            node.validate() shouldBe listOf(Violation("occurredAt", "must be in the past", "past"))
        }

        @Test
        fun `given a Year in the past should accept it`() {
            val node = ValidationNode("occurredAt", Year.now().minusYears(1)).apply { past() }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given the current year as a Year should report it`() {
            val node = ValidationNode("occurredAt", Year.now()).apply { past() }

            node.validate() shouldBe listOf(Violation("occurredAt", "must be in the past", "past"))
        }

        @Test
        fun `given a Date in the future should report it`() {
            val node = ValidationNode("occurredAt", Date.from(Instant.now().plusSeconds(3600))).apply { past() }

            node.validate() shouldBe listOf(Violation("occurredAt", "must be in the past", "past"))
        }

        @Test
        fun `given a Date in the past should accept it`() {
            val node = ValidationNode("occurredAt", Date.from(Instant.now().minusSeconds(3600))).apply { past() }

            node.validate() shouldBe emptyList()
        }
    }

    @Nested
    @DisplayName("When future is called")
    inner class Future {
        @Test
        fun `given a LocalDate in the past should report it`() {
            val node = ValidationNode("expiresAt", LocalDate.now().minusDays(1)).apply { future() }

            node.validate() shouldBe listOf(Violation("expiresAt", "must be in the future", "future"))
        }

        @Test
        fun `given a LocalDate in the future should accept it`() {
            val node = ValidationNode("expiresAt", LocalDate.now().plusDays(1)).apply { future() }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given today as a LocalDate should report it`() {
            val node = ValidationNode("expiresAt", LocalDate.now()).apply { future() }

            node.validate() shouldBe listOf(Violation("expiresAt", "must be in the future", "future"))
        }

        @Test
        fun `given a LocalDateTime in the past should report it`() {
            val node = ValidationNode("expiresAt", LocalDateTime.now().minusHours(1)).apply { future() }

            node.validate() shouldBe listOf(Violation("expiresAt", "must be in the future", "future"))
        }

        @Test
        fun `given a LocalDateTime in the future should accept it`() {
            val node = ValidationNode("expiresAt", LocalDateTime.now().plusHours(1)).apply { future() }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a ZonedDateTime in the past should report it`() {
            val node = ValidationNode("expiresAt", ZonedDateTime.now().minusHours(1)).apply { future() }

            node.validate() shouldBe listOf(Violation("expiresAt", "must be in the future", "future"))
        }

        @Test
        fun `given a ZonedDateTime in the future should accept it`() {
            val node = ValidationNode("expiresAt", ZonedDateTime.now().plusHours(1)).apply { future() }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given an OffsetDateTime in the past should report it`() {
            val node = ValidationNode("expiresAt", OffsetDateTime.now().minusHours(1)).apply { future() }

            node.validate() shouldBe listOf(Violation("expiresAt", "must be in the future", "future"))
        }

        @Test
        fun `given an OffsetDateTime in the future should accept it`() {
            val node = ValidationNode("expiresAt", OffsetDateTime.now().plusHours(1)).apply { future() }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given an Instant in the past should report it`() {
            val node = ValidationNode("expiresAt", Instant.now().minusSeconds(3600)).apply { future() }

            node.validate() shouldBe listOf(Violation("expiresAt", "must be in the future", "future"))
        }

        @Test
        fun `given an Instant in the future should accept it`() {
            val node = ValidationNode("expiresAt", Instant.now().plusSeconds(3600)).apply { future() }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a YearMonth in the past should report it`() {
            val node = ValidationNode("expiresAt", YearMonth.now().minusMonths(1)).apply { future() }

            node.validate() shouldBe listOf(Violation("expiresAt", "must be in the future", "future"))
        }

        @Test
        fun `given a YearMonth in the future should accept it`() {
            val node = ValidationNode("expiresAt", YearMonth.now().plusMonths(1)).apply { future() }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given the current month as a YearMonth should report it`() {
            val node = ValidationNode("expiresAt", YearMonth.now()).apply { future() }

            node.validate() shouldBe listOf(Violation("expiresAt", "must be in the future", "future"))
        }

        @Test
        fun `given a Year in the past should report it`() {
            val node = ValidationNode("expiresAt", Year.now().minusYears(1)).apply { future() }

            node.validate() shouldBe listOf(Violation("expiresAt", "must be in the future", "future"))
        }

        @Test
        fun `given a Year in the future should accept it`() {
            val node = ValidationNode("expiresAt", Year.now().plusYears(1)).apply { future() }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given the current year as a Year should report it`() {
            val node = ValidationNode("expiresAt", Year.now()).apply { future() }

            node.validate() shouldBe listOf(Violation("expiresAt", "must be in the future", "future"))
        }

        @Test
        fun `given a Date in the past should report it`() {
            val node = ValidationNode("expiresAt", Date.from(Instant.now().minusSeconds(3600))).apply { future() }

            node.validate() shouldBe listOf(Violation("expiresAt", "must be in the future", "future"))
        }

        @Test
        fun `given a Date in the future should accept it`() {
            val node = ValidationNode("expiresAt", Date.from(Instant.now().plusSeconds(3600))).apply { future() }

            node.validate() shouldBe emptyList()
        }
    }

    @Nested
    @DisplayName("When pastOrPresent is called")
    inner class PastOrPresent {
        @Test
        fun `given a LocalDate in the future should report it`() {
            val node = ValidationNode("issuedAt", LocalDate.now().plusDays(1)).apply { pastOrPresent() }

            node.validate() shouldBe listOf(Violation("issuedAt", "must be in the past or present", "pastOrPresent"))
        }

        @Test
        fun `given a LocalDate in the past should accept it`() {
            val node = ValidationNode("issuedAt", LocalDate.now().minusDays(1)).apply { pastOrPresent() }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given today as a LocalDate should accept it`() {
            val node = ValidationNode("issuedAt", LocalDate.now()).apply { pastOrPresent() }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a LocalDateTime in the future should report it`() {
            val node = ValidationNode("issuedAt", LocalDateTime.now().plusHours(1)).apply { pastOrPresent() }

            node.validate() shouldBe listOf(Violation("issuedAt", "must be in the past or present", "pastOrPresent"))
        }

        @Test
        fun `given a LocalDateTime in the past should accept it`() {
            val node = ValidationNode("issuedAt", LocalDateTime.now().minusHours(1)).apply { pastOrPresent() }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a ZonedDateTime in the future should report it`() {
            val node = ValidationNode("issuedAt", ZonedDateTime.now().plusHours(1)).apply { pastOrPresent() }

            node.validate() shouldBe listOf(Violation("issuedAt", "must be in the past or present", "pastOrPresent"))
        }

        @Test
        fun `given a ZonedDateTime in the past should accept it`() {
            val node = ValidationNode("issuedAt", ZonedDateTime.now().minusHours(1)).apply { pastOrPresent() }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given an OffsetDateTime in the future should report it`() {
            val node = ValidationNode("issuedAt", OffsetDateTime.now().plusHours(1)).apply { pastOrPresent() }

            node.validate() shouldBe listOf(Violation("issuedAt", "must be in the past or present", "pastOrPresent"))
        }

        @Test
        fun `given an OffsetDateTime in the past should accept it`() {
            val node = ValidationNode("issuedAt", OffsetDateTime.now().minusHours(1)).apply { pastOrPresent() }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given an Instant in the future should report it`() {
            val node = ValidationNode("issuedAt", Instant.now().plusSeconds(3600)).apply { pastOrPresent() }

            node.validate() shouldBe listOf(Violation("issuedAt", "must be in the past or present", "pastOrPresent"))
        }

        @Test
        fun `given an Instant in the past should accept it`() {
            val node = ValidationNode("issuedAt", Instant.now().minusSeconds(3600)).apply { pastOrPresent() }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a YearMonth in the future should report it`() {
            val node = ValidationNode("issuedAt", YearMonth.now().plusMonths(1)).apply { pastOrPresent() }

            node.validate() shouldBe listOf(Violation("issuedAt", "must be in the past or present", "pastOrPresent"))
        }

        @Test
        fun `given a YearMonth in the past should accept it`() {
            val node = ValidationNode("issuedAt", YearMonth.now().minusMonths(1)).apply { pastOrPresent() }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given the current month as a YearMonth should accept it`() {
            val node = ValidationNode("issuedAt", YearMonth.now()).apply { pastOrPresent() }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a Year in the future should report it`() {
            val node = ValidationNode("issuedAt", Year.now().plusYears(1)).apply { pastOrPresent() }

            node.validate() shouldBe listOf(Violation("issuedAt", "must be in the past or present", "pastOrPresent"))
        }

        @Test
        fun `given a Year in the past should accept it`() {
            val node = ValidationNode("issuedAt", Year.now().minusYears(1)).apply { pastOrPresent() }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given the current year as a Year should accept it`() {
            val node = ValidationNode("issuedAt", Year.now()).apply { pastOrPresent() }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a Date in the future should report it`() {
            val node = ValidationNode("issuedAt", Date.from(Instant.now().plusSeconds(3600))).apply { pastOrPresent() }

            node.validate() shouldBe listOf(Violation("issuedAt", "must be in the past or present", "pastOrPresent"))
        }

        @Test
        fun `given a Date in the past should accept it`() {
            val node = ValidationNode("issuedAt", Date.from(Instant.now().minusSeconds(3600))).apply { pastOrPresent() }

            node.validate() shouldBe emptyList()
        }
    }

    @Nested
    @DisplayName("When futureOrPresent is called")
    inner class FutureOrPresent {
        @Test
        fun `given a LocalDate in the past should report it`() {
            val node = ValidationNode("scheduledAt", LocalDate.now().minusDays(1)).apply { futureOrPresent() }

            node.validate() shouldBe listOf(Violation("scheduledAt", "must be in the future or present", "futureOrPresent"))
        }

        @Test
        fun `given a LocalDate in the future should accept it`() {
            val node = ValidationNode("scheduledAt", LocalDate.now().plusDays(1)).apply { futureOrPresent() }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given today as a LocalDate should accept it`() {
            val node = ValidationNode("scheduledAt", LocalDate.now()).apply { futureOrPresent() }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a LocalDateTime in the past should report it`() {
            val node = ValidationNode("scheduledAt", LocalDateTime.now().minusHours(1)).apply { futureOrPresent() }

            node.validate() shouldBe listOf(Violation("scheduledAt", "must be in the future or present", "futureOrPresent"))
        }

        @Test
        fun `given a LocalDateTime in the future should accept it`() {
            val node = ValidationNode("scheduledAt", LocalDateTime.now().plusHours(1)).apply { futureOrPresent() }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a ZonedDateTime in the past should report it`() {
            val node = ValidationNode("scheduledAt", ZonedDateTime.now().minusHours(1)).apply { futureOrPresent() }

            node.validate() shouldBe listOf(Violation("scheduledAt", "must be in the future or present", "futureOrPresent"))
        }

        @Test
        fun `given a ZonedDateTime in the future should accept it`() {
            val node = ValidationNode("scheduledAt", ZonedDateTime.now().plusHours(1)).apply { futureOrPresent() }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given an OffsetDateTime in the past should report it`() {
            val node = ValidationNode("scheduledAt", OffsetDateTime.now().minusHours(1)).apply { futureOrPresent() }

            node.validate() shouldBe listOf(Violation("scheduledAt", "must be in the future or present", "futureOrPresent"))
        }

        @Test
        fun `given an OffsetDateTime in the future should accept it`() {
            val node = ValidationNode("scheduledAt", OffsetDateTime.now().plusHours(1)).apply { futureOrPresent() }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given an Instant in the past should report it`() {
            val node = ValidationNode("scheduledAt", Instant.now().minusSeconds(3600)).apply { futureOrPresent() }

            node.validate() shouldBe listOf(Violation("scheduledAt", "must be in the future or present", "futureOrPresent"))
        }

        @Test
        fun `given an Instant in the future should accept it`() {
            val node = ValidationNode("scheduledAt", Instant.now().plusSeconds(3600)).apply { futureOrPresent() }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a YearMonth in the past should report it`() {
            val node = ValidationNode("scheduledAt", YearMonth.now().minusMonths(1)).apply { futureOrPresent() }

            node.validate() shouldBe listOf(Violation("scheduledAt", "must be in the future or present", "futureOrPresent"))
        }

        @Test
        fun `given a YearMonth in the future should accept it`() {
            val node = ValidationNode("scheduledAt", YearMonth.now().plusMonths(1)).apply { futureOrPresent() }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given the current month as a YearMonth should accept it`() {
            val node = ValidationNode("scheduledAt", YearMonth.now()).apply { futureOrPresent() }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a Year in the past should report it`() {
            val node = ValidationNode("scheduledAt", Year.now().minusYears(1)).apply { futureOrPresent() }

            node.validate() shouldBe listOf(Violation("scheduledAt", "must be in the future or present", "futureOrPresent"))
        }

        @Test
        fun `given a Year in the future should accept it`() {
            val node = ValidationNode("scheduledAt", Year.now().plusYears(1)).apply { futureOrPresent() }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given the current year as a Year should accept it`() {
            val node = ValidationNode("scheduledAt", Year.now()).apply { futureOrPresent() }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a Date in the past should report it`() {
            val node = ValidationNode("scheduledAt", Date.from(Instant.now().minusSeconds(3600))).apply {
                futureOrPresent()
            }

            node.validate() shouldBe listOf(Violation("scheduledAt", "must be in the future or present", "futureOrPresent"))
        }

        @Test
        fun `given a Date in the future should accept it`() {
            val node = ValidationNode("scheduledAt", Date.from(Instant.now().plusSeconds(3600))).apply {
                futureOrPresent()
            }

            node.validate() shouldBe emptyList()
        }
    }

    @Nested
    @DisplayName("When after is called")
    inner class After {
        @Test
        fun `given a LocalDate equal to the reference should report it`() {
            val reference = LocalDate.of(2026, 1, 10)

            val node = ValidationNode("endsAt", LocalDate.of(2026, 1, 10)).apply { after(reference) }

            node.validate() shouldBe listOf(Violation("endsAt", "must be after 2026-01-10", "after"))
        }

        @Test
        fun `given a LocalDate after the reference should accept it`() {
            val reference = LocalDate.of(2026, 1, 10)

            val node = ValidationNode("endsAt", LocalDate.of(2026, 1, 11)).apply { after(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for a LocalDate should skip the check`() {
            val reference: LocalDate? = null

            val node = ValidationNode("endsAt", LocalDate.of(2026, 1, 10)).apply { after(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a LocalDate should build it from the value and the reference`() {
            val reference = LocalDate.of(2026, 1, 10)

            val node = ValidationNode("endsAt", LocalDate.of(2026, 1, 10)).apply {
                after(reference) { other -> "$this is not after $other" }
            }

            node.validate() shouldBe listOf(Violation("endsAt", "2026-01-10 is not after 2026-01-10", "after"))
        }

        @Test
        fun `given a LocalDateTime equal to the reference should report it`() {
            val reference = LocalDateTime.of(2026, 1, 10, 10, 0)

            val node = ValidationNode("endsAt", LocalDateTime.of(2026, 1, 10, 10, 0)).apply { after(reference) }

            node.validate() shouldBe listOf(Violation("endsAt", "must be after 2026-01-10T10:00", "after"))
        }

        @Test
        fun `given a LocalDateTime after the reference should accept it`() {
            val reference = LocalDateTime.of(2026, 1, 10, 10, 0)

            val node = ValidationNode("endsAt", LocalDateTime.of(2026, 1, 10, 11, 0)).apply { after(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for a LocalDateTime should skip the check`() {
            val reference: LocalDateTime? = null

            val node = ValidationNode("endsAt", LocalDateTime.of(2026, 1, 10, 10, 0)).apply { after(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a LocalDateTime should build it from the value and the reference`() {
            val reference = LocalDateTime.of(2026, 1, 10, 10, 0)

            val node = ValidationNode("endsAt", LocalDateTime.of(2026, 1, 10, 10, 0)).apply {
                after(reference) { other -> "$this is not after $other" }
            }

            node.validate() shouldBe listOf(Violation("endsAt", "2026-01-10T10:00 is not after 2026-01-10T10:00", "after"))
        }

        @Test
        fun `given a LocalTime equal to the reference should report it`() {
            val reference = LocalTime.of(10, 0)

            val node = ValidationNode("endsAt", LocalTime.of(10, 0)).apply { after(reference) }

            node.validate() shouldBe listOf(Violation("endsAt", "must be after 10:00", "after"))
        }

        @Test
        fun `given a LocalTime after the reference should accept it`() {
            val reference = LocalTime.of(10, 0)

            val node = ValidationNode("endsAt", LocalTime.of(11, 0)).apply { after(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for a LocalTime should skip the check`() {
            val reference: LocalTime? = null

            val node = ValidationNode("endsAt", LocalTime.of(10, 0)).apply { after(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a LocalTime should build it from the value and the reference`() {
            val reference = LocalTime.of(10, 0)

            val node = ValidationNode("endsAt", LocalTime.of(10, 0)).apply {
                after(reference) { other -> "$this is not after $other" }
            }

            node.validate() shouldBe listOf(Violation("endsAt", "10:00 is not after 10:00", "after"))
        }

        @Test
        fun `given an OffsetTime equal to the reference should report it`() {
            val reference = OffsetTime.of(10, 0, 0, 0, UTC)

            val node = ValidationNode("endsAt", OffsetTime.of(10, 0, 0, 0, UTC)).apply { after(reference) }

            node.validate() shouldBe listOf(Violation("endsAt", "must be after 10:00Z", "after"))
        }

        @Test
        fun `given an OffsetTime after the reference should accept it`() {
            val reference = OffsetTime.of(10, 0, 0, 0, UTC)

            val node = ValidationNode("endsAt", OffsetTime.of(11, 0, 0, 0, UTC)).apply { after(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for an OffsetTime should skip the check`() {
            val reference: OffsetTime? = null

            val node = ValidationNode("endsAt", OffsetTime.of(10, 0, 0, 0, UTC)).apply { after(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for an OffsetTime should build it from the value and the reference`() {
            val reference = OffsetTime.of(10, 0, 0, 0, UTC)

            val node = ValidationNode("endsAt", OffsetTime.of(10, 0, 0, 0, UTC)).apply {
                after(reference) { other -> "$this is not after $other" }
            }

            node.validate() shouldBe listOf(Violation("endsAt", "10:00Z is not after 10:00Z", "after"))
        }

        @Test
        fun `given a ZonedDateTime equal to the reference should report it`() {
            val reference = ZonedDateTime.of(2026, 1, 10, 10, 0, 0, 0, UTC)

            val node = ValidationNode("endsAt", ZonedDateTime.of(2026, 1, 10, 10, 0, 0, 0, UTC)).apply {
                after(reference)
            }

            node.validate() shouldBe listOf(Violation("endsAt", "must be after 2026-01-10T10:00Z", "after"))
        }

        @Test
        fun `given a ZonedDateTime after the reference should accept it`() {
            val reference = ZonedDateTime.of(2026, 1, 10, 10, 0, 0, 0, UTC)

            val node = ValidationNode("endsAt", ZonedDateTime.of(2026, 1, 10, 11, 0, 0, 0, UTC)).apply {
                after(reference)
            }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for a ZonedDateTime should skip the check`() {
            val reference: ZonedDateTime? = null

            val node = ValidationNode("endsAt", ZonedDateTime.of(2026, 1, 10, 10, 0, 0, 0, UTC)).apply {
                after(reference)
            }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a ZonedDateTime should build it from the value and the reference`() {
            val reference = ZonedDateTime.of(2026, 1, 10, 10, 0, 0, 0, UTC)

            val node = ValidationNode("endsAt", ZonedDateTime.of(2026, 1, 10, 10, 0, 0, 0, UTC)).apply {
                after(reference) { other -> "$this is not after $other" }
            }

            node.validate() shouldBe listOf(Violation("endsAt", "2026-01-10T10:00Z is not after 2026-01-10T10:00Z", "after"))
        }

        @Test
        fun `given an OffsetDateTime equal to the reference should report it`() {
            val reference = OffsetDateTime.of(2026, 1, 10, 10, 0, 0, 0, UTC)

            val node = ValidationNode("endsAt", OffsetDateTime.of(2026, 1, 10, 10, 0, 0, 0, UTC)).apply {
                after(reference)
            }

            node.validate() shouldBe listOf(Violation("endsAt", "must be after 2026-01-10T10:00Z", "after"))
        }

        @Test
        fun `given an OffsetDateTime after the reference should accept it`() {
            val reference = OffsetDateTime.of(2026, 1, 10, 10, 0, 0, 0, UTC)

            val node = ValidationNode("endsAt", OffsetDateTime.of(2026, 1, 10, 11, 0, 0, 0, UTC)).apply {
                after(reference)
            }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for an OffsetDateTime should skip the check`() {
            val reference: OffsetDateTime? = null

            val node = ValidationNode("endsAt", OffsetDateTime.of(2026, 1, 10, 10, 0, 0, 0, UTC)).apply {
                after(reference)
            }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for an OffsetDateTime should build it from the value and the reference`() {
            val reference = OffsetDateTime.of(2026, 1, 10, 10, 0, 0, 0, UTC)

            val node = ValidationNode("endsAt", OffsetDateTime.of(2026, 1, 10, 10, 0, 0, 0, UTC)).apply {
                after(reference) { other -> "$this is not after $other" }
            }

            node.validate() shouldBe listOf(Violation("endsAt", "2026-01-10T10:00Z is not after 2026-01-10T10:00Z", "after"))
        }

        @Test
        fun `given an Instant equal to the reference should report it`() {
            val reference = Instant.parse("2026-01-10T10:00:00Z")

            val node = ValidationNode("endsAt", Instant.parse("2026-01-10T10:00:00Z")).apply { after(reference) }

            node.validate() shouldBe listOf(Violation("endsAt", "must be after 2026-01-10T10:00:00Z", "after"))
        }

        @Test
        fun `given an Instant after the reference should accept it`() {
            val reference = Instant.parse("2026-01-10T10:00:00Z")

            val node = ValidationNode("endsAt", Instant.parse("2026-01-10T11:00:00Z")).apply { after(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for an Instant should skip the check`() {
            val reference: Instant? = null

            val node = ValidationNode("endsAt", Instant.parse("2026-01-10T10:00:00Z")).apply { after(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for an Instant should build it from the value and the reference`() {
            val reference = Instant.parse("2026-01-10T10:00:00Z")

            val node = ValidationNode("endsAt", Instant.parse("2026-01-10T10:00:00Z")).apply {
                after(reference) { other -> "$this is not after $other" }
            }

            node.validate() shouldBe listOf(
                Violation("endsAt", "2026-01-10T10:00:00Z is not after 2026-01-10T10:00:00Z", "after")
            )
        }

        @Test
        fun `given a YearMonth equal to the reference should report it`() {
            val reference = YearMonth.of(2026, 10)

            val node = ValidationNode("endsAt", YearMonth.of(2026, 10)).apply { after(reference) }

            node.validate() shouldBe listOf(Violation("endsAt", "must be after 2026-10", "after"))
        }

        @Test
        fun `given a YearMonth after the reference should accept it`() {
            val reference = YearMonth.of(2026, 10)

            val node = ValidationNode("endsAt", YearMonth.of(2026, 11)).apply { after(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for a YearMonth should skip the check`() {
            val reference: YearMonth? = null

            val node = ValidationNode("endsAt", YearMonth.of(2026, 10)).apply { after(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a YearMonth should build it from the value and the reference`() {
            val reference = YearMonth.of(2026, 10)

            val node = ValidationNode("endsAt", YearMonth.of(2026, 10)).apply {
                after(reference) { other -> "$this is not after $other" }
            }

            node.validate() shouldBe listOf(Violation("endsAt", "2026-10 is not after 2026-10", "after"))
        }

        @Test
        fun `given a Year equal to the reference should report it`() {
            val reference = Year.of(2024)

            val node = ValidationNode("endsAt", Year.of(2024)).apply { after(reference) }

            node.validate() shouldBe listOf(Violation("endsAt", "must be after 2024", "after"))
        }

        @Test
        fun `given a Year after the reference should accept it`() {
            val reference = Year.of(2024)

            val node = ValidationNode("endsAt", Year.of(2025)).apply { after(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for a Year should skip the check`() {
            val reference: Year? = null

            val node = ValidationNode("endsAt", Year.of(2024)).apply { after(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a Year should build it from the value and the reference`() {
            val reference = Year.of(2024)

            val node = ValidationNode("endsAt", Year.of(2024)).apply {
                after(reference) { other -> "$this is not after $other" }
            }

            node.validate() shouldBe listOf(Violation("endsAt", "2024 is not after 2024", "after"))
        }

        @Test
        fun `given a Date equal to the reference should report it`() {
            val reference = Date.from(Instant.parse("2026-01-10T10:00:00Z"))

            val node = ValidationNode("endsAt", Date.from(Instant.parse("2026-01-10T10:00:00Z"))).apply {
                after(reference)
            }

            node.validate() shouldBe listOf(Violation("endsAt", "must be after $reference", "after"))
        }

        @Test
        fun `given a Date after the reference should accept it`() {
            val reference = Date.from(Instant.parse("2026-01-10T10:00:00Z"))

            val node = ValidationNode("endsAt", Date.from(Instant.parse("2026-01-10T11:00:00Z"))).apply {
                after(reference)
            }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for a Date should skip the check`() {
            val reference: Date? = null

            val node = ValidationNode("endsAt", Date.from(Instant.parse("2026-01-10T10:00:00Z"))).apply {
                after(reference)
            }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a Date should build it from the value and the reference`() {
            val value = Date.from(Instant.parse("2026-01-10T10:00:00Z"))
            val reference = Date.from(Instant.parse("2026-01-10T10:00:00Z"))

            val node = ValidationNode("endsAt", value).apply {
                after(reference) { other -> "$this is not after $other" }
            }

            node.validate() shouldBe listOf(Violation("endsAt", "$value is not after $reference", "after"))
        }

        @Test
        fun `given a ZonedDateTime at the same instant in another zone should report it`() {
            val reference = ZonedDateTime.of(2026, 1, 10, 7, 0, 0, 0, ZoneId.of("America/Sao_Paulo"))

            val node = ValidationNode("endsAt", reference.withZoneSameInstant(UTC)).apply { after(reference) }

            node.validate() shouldBe listOf(
                Violation("endsAt", "must be after 2026-01-10T07:00-03:00[America/Sao_Paulo]", "after")
            )
        }

        @Test
        fun `given an OffsetDateTime at the same instant in another offset should report it`() {
            val reference = OffsetDateTime.of(2026, 1, 10, 7, 0, 0, 0, ZoneOffset.ofHours(-3))

            val node = ValidationNode("endsAt", reference.withOffsetSameInstant(UTC)).apply { after(reference) }

            node.validate() shouldBe listOf(Violation("endsAt", "must be after 2026-01-10T07:00-03:00", "after"))
        }

        @Test
        fun `given an OffsetTime at the same instant in another offset should report it`() {
            val reference = OffsetTime.of(7, 0, 0, 0, ZoneOffset.ofHours(-3))

            val node = ValidationNode("endsAt", reference.withOffsetSameInstant(UTC)).apply { after(reference) }

            node.validate() shouldBe listOf(Violation("endsAt", "must be after 07:00-03:00", "after"))
        }
    }

    @Nested
    @DisplayName("When before is called")
    inner class Before {
        @Test
        fun `given a LocalDate equal to the reference should report it`() {
            val reference = LocalDate.of(2026, 1, 10)

            val node = ValidationNode("startsAt", LocalDate.of(2026, 1, 10)).apply { before(reference) }

            node.validate() shouldBe listOf(Violation("startsAt", "must be before 2026-01-10", "before"))
        }

        @Test
        fun `given a LocalDate before the reference should accept it`() {
            val reference = LocalDate.of(2026, 1, 10)

            val node = ValidationNode("startsAt", LocalDate.of(2026, 1, 9)).apply { before(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for a LocalDate should skip the check`() {
            val reference: LocalDate? = null

            val node = ValidationNode("startsAt", LocalDate.of(2026, 1, 10)).apply { before(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a LocalDate should build it from the value and the reference`() {
            val reference = LocalDate.of(2026, 1, 10)

            val node = ValidationNode("startsAt", LocalDate.of(2026, 1, 10)).apply {
                before(reference) { other -> "$this is not before $other" }
            }

            node.validate() shouldBe listOf(Violation("startsAt", "2026-01-10 is not before 2026-01-10", "before"))
        }

        @Test
        fun `given a LocalDateTime equal to the reference should report it`() {
            val reference = LocalDateTime.of(2026, 1, 10, 10, 0)

            val node = ValidationNode("startsAt", LocalDateTime.of(2026, 1, 10, 10, 0)).apply { before(reference) }

            node.validate() shouldBe listOf(Violation("startsAt", "must be before 2026-01-10T10:00", "before"))
        }

        @Test
        fun `given a LocalDateTime before the reference should accept it`() {
            val reference = LocalDateTime.of(2026, 1, 10, 10, 0)

            val node = ValidationNode("startsAt", LocalDateTime.of(2026, 1, 10, 9, 0)).apply { before(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for a LocalDateTime should skip the check`() {
            val reference: LocalDateTime? = null

            val node = ValidationNode("startsAt", LocalDateTime.of(2026, 1, 10, 10, 0)).apply { before(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a LocalDateTime should build it from the value and the reference`() {
            val reference = LocalDateTime.of(2026, 1, 10, 10, 0)

            val node = ValidationNode("startsAt", LocalDateTime.of(2026, 1, 10, 10, 0)).apply {
                before(reference) { other -> "$this is not before $other" }
            }

            node.validate() shouldBe listOf(Violation("startsAt", "2026-01-10T10:00 is not before 2026-01-10T10:00", "before"))
        }

        @Test
        fun `given a LocalTime equal to the reference should report it`() {
            val reference = LocalTime.of(10, 0)

            val node = ValidationNode("startsAt", LocalTime.of(10, 0)).apply { before(reference) }

            node.validate() shouldBe listOf(Violation("startsAt", "must be before 10:00", "before"))
        }

        @Test
        fun `given a LocalTime before the reference should accept it`() {
            val reference = LocalTime.of(10, 0)

            val node = ValidationNode("startsAt", LocalTime.of(9, 0)).apply { before(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for a LocalTime should skip the check`() {
            val reference: LocalTime? = null

            val node = ValidationNode("startsAt", LocalTime.of(10, 0)).apply { before(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a LocalTime should build it from the value and the reference`() {
            val reference = LocalTime.of(10, 0)

            val node = ValidationNode("startsAt", LocalTime.of(10, 0)).apply {
                before(reference) { other -> "$this is not before $other" }
            }

            node.validate() shouldBe listOf(Violation("startsAt", "10:00 is not before 10:00", "before"))
        }

        @Test
        fun `given an OffsetTime equal to the reference should report it`() {
            val reference = OffsetTime.of(10, 0, 0, 0, UTC)

            val node = ValidationNode("startsAt", OffsetTime.of(10, 0, 0, 0, UTC)).apply { before(reference) }

            node.validate() shouldBe listOf(Violation("startsAt", "must be before 10:00Z", "before"))
        }

        @Test
        fun `given an OffsetTime before the reference should accept it`() {
            val reference = OffsetTime.of(10, 0, 0, 0, UTC)

            val node = ValidationNode("startsAt", OffsetTime.of(9, 0, 0, 0, UTC)).apply { before(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for an OffsetTime should skip the check`() {
            val reference: OffsetTime? = null

            val node = ValidationNode("startsAt", OffsetTime.of(10, 0, 0, 0, UTC)).apply { before(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for an OffsetTime should build it from the value and the reference`() {
            val reference = OffsetTime.of(10, 0, 0, 0, UTC)

            val node = ValidationNode("startsAt", OffsetTime.of(10, 0, 0, 0, UTC)).apply {
                before(reference) { other -> "$this is not before $other" }
            }

            node.validate() shouldBe listOf(Violation("startsAt", "10:00Z is not before 10:00Z", "before"))
        }

        @Test
        fun `given a ZonedDateTime equal to the reference should report it`() {
            val reference = ZonedDateTime.of(2026, 1, 10, 10, 0, 0, 0, UTC)

            val node = ValidationNode("startsAt", ZonedDateTime.of(2026, 1, 10, 10, 0, 0, 0, UTC)).apply {
                before(reference)
            }

            node.validate() shouldBe listOf(Violation("startsAt", "must be before 2026-01-10T10:00Z", "before"))
        }

        @Test
        fun `given a ZonedDateTime before the reference should accept it`() {
            val reference = ZonedDateTime.of(2026, 1, 10, 10, 0, 0, 0, UTC)

            val node = ValidationNode("startsAt", ZonedDateTime.of(2026, 1, 10, 9, 0, 0, 0, UTC)).apply {
                before(reference)
            }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for a ZonedDateTime should skip the check`() {
            val reference: ZonedDateTime? = null

            val node = ValidationNode("startsAt", ZonedDateTime.of(2026, 1, 10, 10, 0, 0, 0, UTC)).apply {
                before(reference)
            }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a ZonedDateTime should build it from the value and the reference`() {
            val reference = ZonedDateTime.of(2026, 1, 10, 10, 0, 0, 0, UTC)

            val node = ValidationNode("startsAt", ZonedDateTime.of(2026, 1, 10, 10, 0, 0, 0, UTC)).apply {
                before(reference) { other -> "$this is not before $other" }
            }

            node.validate() shouldBe listOf(Violation("startsAt", "2026-01-10T10:00Z is not before 2026-01-10T10:00Z", "before"))
        }

        @Test
        fun `given an OffsetDateTime equal to the reference should report it`() {
            val reference = OffsetDateTime.of(2026, 1, 10, 10, 0, 0, 0, UTC)

            val node = ValidationNode("startsAt", OffsetDateTime.of(2026, 1, 10, 10, 0, 0, 0, UTC)).apply {
                before(reference)
            }

            node.validate() shouldBe listOf(Violation("startsAt", "must be before 2026-01-10T10:00Z", "before"))
        }

        @Test
        fun `given an OffsetDateTime before the reference should accept it`() {
            val reference = OffsetDateTime.of(2026, 1, 10, 10, 0, 0, 0, UTC)

            val node = ValidationNode("startsAt", OffsetDateTime.of(2026, 1, 10, 9, 0, 0, 0, UTC)).apply {
                before(reference)
            }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for an OffsetDateTime should skip the check`() {
            val reference: OffsetDateTime? = null

            val node = ValidationNode("startsAt", OffsetDateTime.of(2026, 1, 10, 10, 0, 0, 0, UTC)).apply {
                before(reference)
            }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for an OffsetDateTime should build it from the value and the reference`() {
            val reference = OffsetDateTime.of(2026, 1, 10, 10, 0, 0, 0, UTC)

            val node = ValidationNode("startsAt", OffsetDateTime.of(2026, 1, 10, 10, 0, 0, 0, UTC)).apply {
                before(reference) { other -> "$this is not before $other" }
            }

            node.validate() shouldBe listOf(Violation("startsAt", "2026-01-10T10:00Z is not before 2026-01-10T10:00Z", "before"))
        }

        @Test
        fun `given an Instant equal to the reference should report it`() {
            val reference = Instant.parse("2026-01-10T10:00:00Z")

            val node = ValidationNode("startsAt", Instant.parse("2026-01-10T10:00:00Z")).apply { before(reference) }

            node.validate() shouldBe listOf(Violation("startsAt", "must be before 2026-01-10T10:00:00Z", "before"))
        }

        @Test
        fun `given an Instant before the reference should accept it`() {
            val reference = Instant.parse("2026-01-10T10:00:00Z")

            val node = ValidationNode("startsAt", Instant.parse("2026-01-10T09:00:00Z")).apply { before(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for an Instant should skip the check`() {
            val reference: Instant? = null

            val node = ValidationNode("startsAt", Instant.parse("2026-01-10T10:00:00Z")).apply { before(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for an Instant should build it from the value and the reference`() {
            val reference = Instant.parse("2026-01-10T10:00:00Z")

            val node = ValidationNode("startsAt", Instant.parse("2026-01-10T10:00:00Z")).apply {
                before(reference) { other -> "$this is not before $other" }
            }

            node.validate() shouldBe listOf(
                Violation("startsAt", "2026-01-10T10:00:00Z is not before 2026-01-10T10:00:00Z", "before")
            )
        }

        @Test
        fun `given a YearMonth equal to the reference should report it`() {
            val reference = YearMonth.of(2026, 10)

            val node = ValidationNode("startsAt", YearMonth.of(2026, 10)).apply { before(reference) }

            node.validate() shouldBe listOf(Violation("startsAt", "must be before 2026-10", "before"))
        }

        @Test
        fun `given a YearMonth before the reference should accept it`() {
            val reference = YearMonth.of(2026, 10)

            val node = ValidationNode("startsAt", YearMonth.of(2026, 9)).apply { before(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for a YearMonth should skip the check`() {
            val reference: YearMonth? = null

            val node = ValidationNode("startsAt", YearMonth.of(2026, 10)).apply { before(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a YearMonth should build it from the value and the reference`() {
            val reference = YearMonth.of(2026, 10)

            val node = ValidationNode("startsAt", YearMonth.of(2026, 10)).apply {
                before(reference) { other -> "$this is not before $other" }
            }

            node.validate() shouldBe listOf(Violation("startsAt", "2026-10 is not before 2026-10", "before"))
        }

        @Test
        fun `given a Year equal to the reference should report it`() {
            val reference = Year.of(2024)

            val node = ValidationNode("startsAt", Year.of(2024)).apply { before(reference) }

            node.validate() shouldBe listOf(Violation("startsAt", "must be before 2024", "before"))
        }

        @Test
        fun `given a Year before the reference should accept it`() {
            val reference = Year.of(2024)

            val node = ValidationNode("startsAt", Year.of(2023)).apply { before(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for a Year should skip the check`() {
            val reference: Year? = null

            val node = ValidationNode("startsAt", Year.of(2024)).apply { before(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a Year should build it from the value and the reference`() {
            val reference = Year.of(2024)

            val node = ValidationNode("startsAt", Year.of(2024)).apply {
                before(reference) { other -> "$this is not before $other" }
            }

            node.validate() shouldBe listOf(Violation("startsAt", "2024 is not before 2024", "before"))
        }

        @Test
        fun `given a Date equal to the reference should report it`() {
            val reference = Date.from(Instant.parse("2026-01-10T10:00:00Z"))

            val node = ValidationNode("startsAt", Date.from(Instant.parse("2026-01-10T10:00:00Z"))).apply {
                before(reference)
            }

            node.validate() shouldBe listOf(Violation("startsAt", "must be before $reference", "before"))
        }

        @Test
        fun `given a Date before the reference should accept it`() {
            val reference = Date.from(Instant.parse("2026-01-10T10:00:00Z"))

            val node = ValidationNode("startsAt", Date.from(Instant.parse("2026-01-10T09:00:00Z"))).apply {
                before(reference)
            }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for a Date should skip the check`() {
            val reference: Date? = null

            val node = ValidationNode("startsAt", Date.from(Instant.parse("2026-01-10T10:00:00Z"))).apply {
                before(reference)
            }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a Date should build it from the value and the reference`() {
            val value = Date.from(Instant.parse("2026-01-10T10:00:00Z"))
            val reference = Date.from(Instant.parse("2026-01-10T10:00:00Z"))

            val node = ValidationNode("startsAt", value).apply {
                before(reference) { other -> "$this is not before $other" }
            }

            node.validate() shouldBe listOf(Violation("startsAt", "$value is not before $reference", "before"))
        }

        @Test
        fun `given a ZonedDateTime at the same instant in another zone should report it`() {
            val reference = ZonedDateTime.of(2026, 1, 10, 7, 0, 0, 0, ZoneId.of("America/Sao_Paulo"))

            val node = ValidationNode("startsAt", reference.withZoneSameInstant(UTC)).apply { before(reference) }

            node.validate() shouldBe listOf(
                Violation("startsAt", "must be before 2026-01-10T07:00-03:00[America/Sao_Paulo]", "before")
            )
        }

        @Test
        fun `given an OffsetDateTime at the same instant in another offset should report it`() {
            val reference = OffsetDateTime.of(2026, 1, 10, 7, 0, 0, 0, ZoneOffset.ofHours(-3))

            val node = ValidationNode("startsAt", reference.withOffsetSameInstant(UTC)).apply { before(reference) }

            node.validate() shouldBe listOf(Violation("startsAt", "must be before 2026-01-10T07:00-03:00", "before"))
        }

        @Test
        fun `given an OffsetTime at the same instant in another offset should report it`() {
            val reference = OffsetTime.of(7, 0, 0, 0, ZoneOffset.ofHours(-3))

            val node = ValidationNode("startsAt", reference.withOffsetSameInstant(UTC)).apply { before(reference) }

            node.validate() shouldBe listOf(Violation("startsAt", "must be before 07:00-03:00", "before"))
        }
    }

    @Nested
    @DisplayName("When afterOrEqual is called")
    inner class AfterOrEqual {
        @Test
        fun `given a LocalDate before the reference should report it`() {
            val reference = LocalDate.of(2026, 1, 10)

            val node = ValidationNode("checkOut", LocalDate.of(2026, 1, 9)).apply { afterOrEqual(reference) }

            node.validate() shouldBe listOf(Violation("checkOut", "must be after or equal to 2026-01-10", "afterOrEqual"))
        }

        @Test
        fun `given a LocalDate equal to the reference should accept it`() {
            val reference = LocalDate.of(2026, 1, 10)

            val node = ValidationNode("checkOut", LocalDate.of(2026, 1, 10)).apply { afterOrEqual(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for a LocalDate should skip the check`() {
            val reference: LocalDate? = null

            val node = ValidationNode("checkOut", LocalDate.of(2026, 1, 9)).apply { afterOrEqual(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a LocalDate should build it from the value and the reference`() {
            val reference = LocalDate.of(2026, 1, 10)

            val node = ValidationNode("checkOut", LocalDate.of(2026, 1, 9)).apply {
                afterOrEqual(reference) { other -> "$this is before $other" }
            }

            node.validate() shouldBe listOf(Violation("checkOut", "2026-01-09 is before 2026-01-10", "afterOrEqual"))
        }

        @Test
        fun `given a LocalDateTime before the reference should report it`() {
            val reference = LocalDateTime.of(2026, 1, 10, 10, 0)

            val node = ValidationNode("checkOut", LocalDateTime.of(2026, 1, 10, 9, 0)).apply { afterOrEqual(reference) }

            node.validate() shouldBe listOf(Violation("checkOut", "must be after or equal to 2026-01-10T10:00", "afterOrEqual"))
        }

        @Test
        fun `given a LocalDateTime equal to the reference should accept it`() {
            val reference = LocalDateTime.of(2026, 1, 10, 10, 0)

            val node = ValidationNode("checkOut", LocalDateTime.of(2026, 1, 10, 10, 0)).apply {
                afterOrEqual(reference)
            }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for a LocalDateTime should skip the check`() {
            val reference: LocalDateTime? = null

            val node = ValidationNode("checkOut", LocalDateTime.of(2026, 1, 10, 9, 0)).apply { afterOrEqual(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a LocalDateTime should build it from the value and the reference`() {
            val reference = LocalDateTime.of(2026, 1, 10, 10, 0)

            val node = ValidationNode("checkOut", LocalDateTime.of(2026, 1, 10, 9, 0)).apply {
                afterOrEqual(reference) { other -> "$this is before $other" }
            }

            node.validate() shouldBe listOf(Violation("checkOut", "2026-01-10T09:00 is before 2026-01-10T10:00", "afterOrEqual"))
        }

        @Test
        fun `given a LocalTime before the reference should report it`() {
            val reference = LocalTime.of(10, 0)

            val node = ValidationNode("checkOut", LocalTime.of(9, 0)).apply { afterOrEqual(reference) }

            node.validate() shouldBe listOf(Violation("checkOut", "must be after or equal to 10:00", "afterOrEqual"))
        }

        @Test
        fun `given a LocalTime equal to the reference should accept it`() {
            val reference = LocalTime.of(10, 0)

            val node = ValidationNode("checkOut", LocalTime.of(10, 0)).apply { afterOrEqual(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for a LocalTime should skip the check`() {
            val reference: LocalTime? = null

            val node = ValidationNode("checkOut", LocalTime.of(9, 0)).apply { afterOrEqual(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a LocalTime should build it from the value and the reference`() {
            val reference = LocalTime.of(10, 0)

            val node = ValidationNode("checkOut", LocalTime.of(9, 0)).apply {
                afterOrEqual(reference) { other -> "$this is before $other" }
            }

            node.validate() shouldBe listOf(Violation("checkOut", "09:00 is before 10:00", "afterOrEqual"))
        }

        @Test
        fun `given an OffsetTime before the reference should report it`() {
            val reference = OffsetTime.of(10, 0, 0, 0, UTC)

            val node = ValidationNode("checkOut", OffsetTime.of(9, 0, 0, 0, UTC)).apply { afterOrEqual(reference) }

            node.validate() shouldBe listOf(Violation("checkOut", "must be after or equal to 10:00Z", "afterOrEqual"))
        }

        @Test
        fun `given an OffsetTime equal to the reference should accept it`() {
            val reference = OffsetTime.of(10, 0, 0, 0, UTC)

            val node = ValidationNode("checkOut", OffsetTime.of(10, 0, 0, 0, UTC)).apply { afterOrEqual(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for an OffsetTime should skip the check`() {
            val reference: OffsetTime? = null

            val node = ValidationNode("checkOut", OffsetTime.of(9, 0, 0, 0, UTC)).apply { afterOrEqual(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for an OffsetTime should build it from the value and the reference`() {
            val reference = OffsetTime.of(10, 0, 0, 0, UTC)

            val node = ValidationNode("checkOut", OffsetTime.of(9, 0, 0, 0, UTC)).apply {
                afterOrEqual(reference) { other -> "$this is before $other" }
            }

            node.validate() shouldBe listOf(Violation("checkOut", "09:00Z is before 10:00Z", "afterOrEqual"))
        }

        @Test
        fun `given a ZonedDateTime before the reference should report it`() {
            val reference = ZonedDateTime.of(2026, 1, 10, 10, 0, 0, 0, UTC)

            val node = ValidationNode("checkOut", ZonedDateTime.of(2026, 1, 10, 9, 0, 0, 0, UTC)).apply {
                afterOrEqual(reference)
            }

            node.validate() shouldBe listOf(Violation("checkOut", "must be after or equal to 2026-01-10T10:00Z", "afterOrEqual"))
        }

        @Test
        fun `given a ZonedDateTime equal to the reference should accept it`() {
            val reference = ZonedDateTime.of(2026, 1, 10, 10, 0, 0, 0, UTC)

            val node = ValidationNode("checkOut", ZonedDateTime.of(2026, 1, 10, 10, 0, 0, 0, UTC)).apply {
                afterOrEqual(reference)
            }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for a ZonedDateTime should skip the check`() {
            val reference: ZonedDateTime? = null

            val node = ValidationNode("checkOut", ZonedDateTime.of(2026, 1, 10, 9, 0, 0, 0, UTC)).apply {
                afterOrEqual(reference)
            }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a ZonedDateTime should build it from the value and the reference`() {
            val reference = ZonedDateTime.of(2026, 1, 10, 10, 0, 0, 0, UTC)

            val node = ValidationNode("checkOut", ZonedDateTime.of(2026, 1, 10, 9, 0, 0, 0, UTC)).apply {
                afterOrEqual(reference) { other -> "$this is before $other" }
            }

            node.validate() shouldBe listOf(Violation("checkOut", "2026-01-10T09:00Z is before 2026-01-10T10:00Z", "afterOrEqual"))
        }

        @Test
        fun `given an OffsetDateTime before the reference should report it`() {
            val reference = OffsetDateTime.of(2026, 1, 10, 10, 0, 0, 0, UTC)

            val node = ValidationNode("checkOut", OffsetDateTime.of(2026, 1, 10, 9, 0, 0, 0, UTC)).apply {
                afterOrEqual(reference)
            }

            node.validate() shouldBe listOf(Violation("checkOut", "must be after or equal to 2026-01-10T10:00Z", "afterOrEqual"))
        }

        @Test
        fun `given an OffsetDateTime equal to the reference should accept it`() {
            val reference = OffsetDateTime.of(2026, 1, 10, 10, 0, 0, 0, UTC)

            val node = ValidationNode("checkOut", OffsetDateTime.of(2026, 1, 10, 10, 0, 0, 0, UTC)).apply {
                afterOrEqual(reference)
            }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for an OffsetDateTime should skip the check`() {
            val reference: OffsetDateTime? = null

            val node = ValidationNode("checkOut", OffsetDateTime.of(2026, 1, 10, 9, 0, 0, 0, UTC)).apply {
                afterOrEqual(reference)
            }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for an OffsetDateTime should build it from the value and the reference`() {
            val reference = OffsetDateTime.of(2026, 1, 10, 10, 0, 0, 0, UTC)

            val node = ValidationNode("checkOut", OffsetDateTime.of(2026, 1, 10, 9, 0, 0, 0, UTC)).apply {
                afterOrEqual(reference) { other -> "$this is before $other" }
            }

            node.validate() shouldBe listOf(Violation("checkOut", "2026-01-10T09:00Z is before 2026-01-10T10:00Z", "afterOrEqual"))
        }

        @Test
        fun `given an Instant before the reference should report it`() {
            val reference = Instant.parse("2026-01-10T10:00:00Z")

            val node = ValidationNode("checkOut", Instant.parse("2026-01-10T09:00:00Z")).apply {
                afterOrEqual(reference)
            }

            node.validate() shouldBe listOf(Violation("checkOut", "must be after or equal to 2026-01-10T10:00:00Z", "afterOrEqual"))
        }

        @Test
        fun `given an Instant equal to the reference should accept it`() {
            val reference = Instant.parse("2026-01-10T10:00:00Z")

            val node = ValidationNode("checkOut", Instant.parse("2026-01-10T10:00:00Z")).apply {
                afterOrEqual(reference)
            }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for an Instant should skip the check`() {
            val reference: Instant? = null

            val node = ValidationNode("checkOut", Instant.parse("2026-01-10T09:00:00Z")).apply {
                afterOrEqual(reference)
            }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for an Instant should build it from the value and the reference`() {
            val reference = Instant.parse("2026-01-10T10:00:00Z")

            val node = ValidationNode("checkOut", Instant.parse("2026-01-10T09:00:00Z")).apply {
                afterOrEqual(reference) { other -> "$this is before $other" }
            }

            node.validate() shouldBe listOf(
                Violation("checkOut", "2026-01-10T09:00:00Z is before 2026-01-10T10:00:00Z", "afterOrEqual")
            )
        }

        @Test
        fun `given a YearMonth before the reference should report it`() {
            val reference = YearMonth.of(2026, 10)

            val node = ValidationNode("checkOut", YearMonth.of(2026, 9)).apply { afterOrEqual(reference) }

            node.validate() shouldBe listOf(Violation("checkOut", "must be after or equal to 2026-10", "afterOrEqual"))
        }

        @Test
        fun `given a YearMonth equal to the reference should accept it`() {
            val reference = YearMonth.of(2026, 10)

            val node = ValidationNode("checkOut", YearMonth.of(2026, 10)).apply { afterOrEqual(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for a YearMonth should skip the check`() {
            val reference: YearMonth? = null

            val node = ValidationNode("checkOut", YearMonth.of(2026, 9)).apply { afterOrEqual(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a YearMonth should build it from the value and the reference`() {
            val reference = YearMonth.of(2026, 10)

            val node = ValidationNode("checkOut", YearMonth.of(2026, 9)).apply {
                afterOrEqual(reference) { other -> "$this is before $other" }
            }

            node.validate() shouldBe listOf(Violation("checkOut", "2026-09 is before 2026-10", "afterOrEqual"))
        }

        @Test
        fun `given a Year before the reference should report it`() {
            val reference = Year.of(2024)

            val node = ValidationNode("checkOut", Year.of(2023)).apply { afterOrEqual(reference) }

            node.validate() shouldBe listOf(Violation("checkOut", "must be after or equal to 2024", "afterOrEqual"))
        }

        @Test
        fun `given a Year equal to the reference should accept it`() {
            val reference = Year.of(2024)

            val node = ValidationNode("checkOut", Year.of(2024)).apply { afterOrEqual(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for a Year should skip the check`() {
            val reference: Year? = null

            val node = ValidationNode("checkOut", Year.of(2023)).apply { afterOrEqual(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a Year should build it from the value and the reference`() {
            val reference = Year.of(2024)

            val node = ValidationNode("checkOut", Year.of(2023)).apply {
                afterOrEqual(reference) { other -> "$this is before $other" }
            }

            node.validate() shouldBe listOf(Violation("checkOut", "2023 is before 2024", "afterOrEqual"))
        }

        @Test
        fun `given a Date before the reference should report it`() {
            val reference = Date.from(Instant.parse("2026-01-10T10:00:00Z"))

            val node = ValidationNode("checkOut", Date.from(Instant.parse("2026-01-10T09:00:00Z"))).apply {
                afterOrEqual(reference)
            }

            node.validate() shouldBe listOf(Violation("checkOut", "must be after or equal to $reference", "afterOrEqual"))
        }

        @Test
        fun `given a Date equal to the reference should accept it`() {
            val reference = Date.from(Instant.parse("2026-01-10T10:00:00Z"))

            val node = ValidationNode("checkOut", Date.from(Instant.parse("2026-01-10T10:00:00Z"))).apply {
                afterOrEqual(reference)
            }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for a Date should skip the check`() {
            val reference: Date? = null

            val node = ValidationNode("checkOut", Date.from(Instant.parse("2026-01-10T09:00:00Z"))).apply {
                afterOrEqual(reference)
            }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a Date should build it from the value and the reference`() {
            val value = Date.from(Instant.parse("2026-01-10T09:00:00Z"))
            val reference = Date.from(Instant.parse("2026-01-10T10:00:00Z"))

            val node = ValidationNode("checkOut", value).apply {
                afterOrEqual(reference) { other -> "$this is before $other" }
            }

            node.validate() shouldBe listOf(Violation("checkOut", "$value is before $reference", "afterOrEqual"))
        }
    }

    @Nested
    @DisplayName("When beforeOrEqual is called")
    inner class BeforeOrEqual {
        @Test
        fun `given a LocalDate after the reference should report it`() {
            val reference = LocalDate.of(2026, 1, 10)

            val node = ValidationNode("checkIn", LocalDate.of(2026, 1, 11)).apply { beforeOrEqual(reference) }

            node.validate() shouldBe listOf(Violation("checkIn", "must be before or equal to 2026-01-10", "beforeOrEqual"))
        }

        @Test
        fun `given a LocalDate equal to the reference should accept it`() {
            val reference = LocalDate.of(2026, 1, 10)

            val node = ValidationNode("checkIn", LocalDate.of(2026, 1, 10)).apply { beforeOrEqual(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for a LocalDate should skip the check`() {
            val reference: LocalDate? = null

            val node = ValidationNode("checkIn", LocalDate.of(2026, 1, 11)).apply { beforeOrEqual(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a LocalDate should build it from the value and the reference`() {
            val reference = LocalDate.of(2026, 1, 10)

            val node = ValidationNode("checkIn", LocalDate.of(2026, 1, 11)).apply {
                beforeOrEqual(reference) { other -> "$this is after $other" }
            }

            node.validate() shouldBe listOf(Violation("checkIn", "2026-01-11 is after 2026-01-10", "beforeOrEqual"))
        }

        @Test
        fun `given a LocalDateTime after the reference should report it`() {
            val reference = LocalDateTime.of(2026, 1, 10, 10, 0)

            val node = ValidationNode("checkIn", LocalDateTime.of(2026, 1, 10, 11, 0)).apply {
                beforeOrEqual(reference)
            }

            node.validate() shouldBe listOf(Violation("checkIn", "must be before or equal to 2026-01-10T10:00", "beforeOrEqual"))
        }

        @Test
        fun `given a LocalDateTime equal to the reference should accept it`() {
            val reference = LocalDateTime.of(2026, 1, 10, 10, 0)

            val node = ValidationNode("checkIn", LocalDateTime.of(2026, 1, 10, 10, 0)).apply {
                beforeOrEqual(reference)
            }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for a LocalDateTime should skip the check`() {
            val reference: LocalDateTime? = null

            val node = ValidationNode("checkIn", LocalDateTime.of(2026, 1, 10, 11, 0)).apply {
                beforeOrEqual(reference)
            }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a LocalDateTime should build it from the value and the reference`() {
            val reference = LocalDateTime.of(2026, 1, 10, 10, 0)

            val node = ValidationNode("checkIn", LocalDateTime.of(2026, 1, 10, 11, 0)).apply {
                beforeOrEqual(reference) { other -> "$this is after $other" }
            }

            node.validate() shouldBe listOf(Violation("checkIn", "2026-01-10T11:00 is after 2026-01-10T10:00", "beforeOrEqual"))
        }

        @Test
        fun `given a LocalTime after the reference should report it`() {
            val reference = LocalTime.of(10, 0)

            val node = ValidationNode("checkIn", LocalTime.of(11, 0)).apply { beforeOrEqual(reference) }

            node.validate() shouldBe listOf(Violation("checkIn", "must be before or equal to 10:00", "beforeOrEqual"))
        }

        @Test
        fun `given a LocalTime equal to the reference should accept it`() {
            val reference = LocalTime.of(10, 0)

            val node = ValidationNode("checkIn", LocalTime.of(10, 0)).apply { beforeOrEqual(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for a LocalTime should skip the check`() {
            val reference: LocalTime? = null

            val node = ValidationNode("checkIn", LocalTime.of(11, 0)).apply { beforeOrEqual(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a LocalTime should build it from the value and the reference`() {
            val reference = LocalTime.of(10, 0)

            val node = ValidationNode("checkIn", LocalTime.of(11, 0)).apply {
                beforeOrEqual(reference) { other -> "$this is after $other" }
            }

            node.validate() shouldBe listOf(Violation("checkIn", "11:00 is after 10:00", "beforeOrEqual"))
        }

        @Test
        fun `given an OffsetTime after the reference should report it`() {
            val reference = OffsetTime.of(10, 0, 0, 0, UTC)

            val node = ValidationNode("checkIn", OffsetTime.of(11, 0, 0, 0, UTC)).apply { beforeOrEqual(reference) }

            node.validate() shouldBe listOf(Violation("checkIn", "must be before or equal to 10:00Z", "beforeOrEqual"))
        }

        @Test
        fun `given an OffsetTime equal to the reference should accept it`() {
            val reference = OffsetTime.of(10, 0, 0, 0, UTC)

            val node = ValidationNode("checkIn", OffsetTime.of(10, 0, 0, 0, UTC)).apply { beforeOrEqual(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for an OffsetTime should skip the check`() {
            val reference: OffsetTime? = null

            val node = ValidationNode("checkIn", OffsetTime.of(11, 0, 0, 0, UTC)).apply { beforeOrEqual(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for an OffsetTime should build it from the value and the reference`() {
            val reference = OffsetTime.of(10, 0, 0, 0, UTC)

            val node = ValidationNode("checkIn", OffsetTime.of(11, 0, 0, 0, UTC)).apply {
                beforeOrEqual(reference) { other -> "$this is after $other" }
            }

            node.validate() shouldBe listOf(Violation("checkIn", "11:00Z is after 10:00Z", "beforeOrEqual"))
        }

        @Test
        fun `given a ZonedDateTime after the reference should report it`() {
            val reference = ZonedDateTime.of(2026, 1, 10, 10, 0, 0, 0, UTC)

            val node = ValidationNode("checkIn", ZonedDateTime.of(2026, 1, 10, 11, 0, 0, 0, UTC)).apply {
                beforeOrEqual(reference)
            }

            node.validate() shouldBe listOf(Violation("checkIn", "must be before or equal to 2026-01-10T10:00Z", "beforeOrEqual"))
        }

        @Test
        fun `given a ZonedDateTime equal to the reference should accept it`() {
            val reference = ZonedDateTime.of(2026, 1, 10, 10, 0, 0, 0, UTC)

            val node = ValidationNode("checkIn", ZonedDateTime.of(2026, 1, 10, 10, 0, 0, 0, UTC)).apply {
                beforeOrEqual(reference)
            }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for a ZonedDateTime should skip the check`() {
            val reference: ZonedDateTime? = null

            val node = ValidationNode("checkIn", ZonedDateTime.of(2026, 1, 10, 11, 0, 0, 0, UTC)).apply {
                beforeOrEqual(reference)
            }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a ZonedDateTime should build it from the value and the reference`() {
            val reference = ZonedDateTime.of(2026, 1, 10, 10, 0, 0, 0, UTC)

            val node = ValidationNode("checkIn", ZonedDateTime.of(2026, 1, 10, 11, 0, 0, 0, UTC)).apply {
                beforeOrEqual(reference) { other -> "$this is after $other" }
            }

            node.validate() shouldBe listOf(Violation("checkIn", "2026-01-10T11:00Z is after 2026-01-10T10:00Z", "beforeOrEqual"))
        }

        @Test
        fun `given an OffsetDateTime after the reference should report it`() {
            val reference = OffsetDateTime.of(2026, 1, 10, 10, 0, 0, 0, UTC)

            val node = ValidationNode("checkIn", OffsetDateTime.of(2026, 1, 10, 11, 0, 0, 0, UTC)).apply {
                beforeOrEqual(reference)
            }

            node.validate() shouldBe listOf(Violation("checkIn", "must be before or equal to 2026-01-10T10:00Z", "beforeOrEqual"))
        }

        @Test
        fun `given an OffsetDateTime equal to the reference should accept it`() {
            val reference = OffsetDateTime.of(2026, 1, 10, 10, 0, 0, 0, UTC)

            val node = ValidationNode("checkIn", OffsetDateTime.of(2026, 1, 10, 10, 0, 0, 0, UTC)).apply {
                beforeOrEqual(reference)
            }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for an OffsetDateTime should skip the check`() {
            val reference: OffsetDateTime? = null

            val node = ValidationNode("checkIn", OffsetDateTime.of(2026, 1, 10, 11, 0, 0, 0, UTC)).apply {
                beforeOrEqual(reference)
            }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for an OffsetDateTime should build it from the value and the reference`() {
            val reference = OffsetDateTime.of(2026, 1, 10, 10, 0, 0, 0, UTC)

            val node = ValidationNode("checkIn", OffsetDateTime.of(2026, 1, 10, 11, 0, 0, 0, UTC)).apply {
                beforeOrEqual(reference) { other -> "$this is after $other" }
            }

            node.validate() shouldBe listOf(Violation("checkIn", "2026-01-10T11:00Z is after 2026-01-10T10:00Z", "beforeOrEqual"))
        }

        @Test
        fun `given an Instant after the reference should report it`() {
            val reference = Instant.parse("2026-01-10T10:00:00Z")

            val node = ValidationNode("checkIn", Instant.parse("2026-01-10T11:00:00Z")).apply {
                beforeOrEqual(reference)
            }

            node.validate() shouldBe listOf(Violation("checkIn", "must be before or equal to 2026-01-10T10:00:00Z", "beforeOrEqual"))
        }

        @Test
        fun `given an Instant equal to the reference should accept it`() {
            val reference = Instant.parse("2026-01-10T10:00:00Z")

            val node = ValidationNode("checkIn", Instant.parse("2026-01-10T10:00:00Z")).apply {
                beforeOrEqual(reference)
            }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for an Instant should skip the check`() {
            val reference: Instant? = null

            val node = ValidationNode("checkIn", Instant.parse("2026-01-10T11:00:00Z")).apply {
                beforeOrEqual(reference)
            }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for an Instant should build it from the value and the reference`() {
            val reference = Instant.parse("2026-01-10T10:00:00Z")

            val node = ValidationNode("checkIn", Instant.parse("2026-01-10T11:00:00Z")).apply {
                beforeOrEqual(reference) { other -> "$this is after $other" }
            }

            node.validate() shouldBe listOf(Violation("checkIn", "2026-01-10T11:00:00Z is after 2026-01-10T10:00:00Z", "beforeOrEqual"))
        }

        @Test
        fun `given a YearMonth after the reference should report it`() {
            val reference = YearMonth.of(2026, 10)

            val node = ValidationNode("checkIn", YearMonth.of(2026, 11)).apply { beforeOrEqual(reference) }

            node.validate() shouldBe listOf(Violation("checkIn", "must be before or equal to 2026-10", "beforeOrEqual"))
        }

        @Test
        fun `given a YearMonth equal to the reference should accept it`() {
            val reference = YearMonth.of(2026, 10)

            val node = ValidationNode("checkIn", YearMonth.of(2026, 10)).apply { beforeOrEqual(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for a YearMonth should skip the check`() {
            val reference: YearMonth? = null

            val node = ValidationNode("checkIn", YearMonth.of(2026, 11)).apply { beforeOrEqual(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a YearMonth should build it from the value and the reference`() {
            val reference = YearMonth.of(2026, 10)

            val node = ValidationNode("checkIn", YearMonth.of(2026, 11)).apply {
                beforeOrEqual(reference) { other -> "$this is after $other" }
            }

            node.validate() shouldBe listOf(Violation("checkIn", "2026-11 is after 2026-10", "beforeOrEqual"))
        }

        @Test
        fun `given a Year after the reference should report it`() {
            val reference = Year.of(2024)

            val node = ValidationNode("checkIn", Year.of(2025)).apply { beforeOrEqual(reference) }

            node.validate() shouldBe listOf(Violation("checkIn", "must be before or equal to 2024", "beforeOrEqual"))
        }

        @Test
        fun `given a Year equal to the reference should accept it`() {
            val reference = Year.of(2024)

            val node = ValidationNode("checkIn", Year.of(2024)).apply { beforeOrEqual(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for a Year should skip the check`() {
            val reference: Year? = null

            val node = ValidationNode("checkIn", Year.of(2025)).apply { beforeOrEqual(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a Year should build it from the value and the reference`() {
            val reference = Year.of(2024)

            val node = ValidationNode("checkIn", Year.of(2025)).apply {
                beforeOrEqual(reference) { other -> "$this is after $other" }
            }

            node.validate() shouldBe listOf(Violation("checkIn", "2025 is after 2024", "beforeOrEqual"))
        }

        @Test
        fun `given a Date after the reference should report it`() {
            val reference = Date.from(Instant.parse("2026-01-10T10:00:00Z"))

            val node = ValidationNode("checkIn", Date.from(Instant.parse("2026-01-10T11:00:00Z"))).apply {
                beforeOrEqual(reference)
            }

            node.validate() shouldBe listOf(Violation("checkIn", "must be before or equal to $reference", "beforeOrEqual"))
        }

        @Test
        fun `given a Date equal to the reference should accept it`() {
            val reference = Date.from(Instant.parse("2026-01-10T10:00:00Z"))

            val node = ValidationNode("checkIn", Date.from(Instant.parse("2026-01-10T10:00:00Z"))).apply {
                beforeOrEqual(reference)
            }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for a Date should skip the check`() {
            val reference: Date? = null

            val node = ValidationNode("checkIn", Date.from(Instant.parse("2026-01-10T11:00:00Z"))).apply {
                beforeOrEqual(reference)
            }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a Date should build it from the value and the reference`() {
            val value = Date.from(Instant.parse("2026-01-10T11:00:00Z"))
            val reference = Date.from(Instant.parse("2026-01-10T10:00:00Z"))

            val node = ValidationNode("checkIn", value).apply {
                beforeOrEqual(reference) { other -> "$this is after $other" }
            }

            node.validate() shouldBe listOf(Violation("checkIn", "$value is after $reference", "beforeOrEqual"))
        }
    }

    @Nested
    @DisplayName("When min is called")
    inner class Min {
        @Test
        fun `given a Duration below the minimum should report it`() {
            val minimum = Duration.ofMinutes(10)

            val node = ValidationNode("timeout", Duration.ofMinutes(9)).apply { min(minimum) }

            node.validate() shouldBe listOf(Violation("timeout", "must be at least PT10M", "min"))
        }

        @Test
        fun `given a Duration at the minimum should accept it`() {
            val minimum = Duration.ofMinutes(10)

            val node = ValidationNode("timeout", Duration.ofMinutes(10)).apply { min(minimum) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no minimum for a Duration should skip the check`() {
            val minimum: Duration? = null

            val node = ValidationNode("timeout", Duration.ofMinutes(9)).apply { min(minimum) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a Duration should build it from the value and the minimum`() {
            val minimum = Duration.ofMinutes(10)

            val node = ValidationNode("timeout", Duration.ofMinutes(9)).apply {
                min(minimum) { min -> "$this is below $min" }
            }

            node.validate() shouldBe listOf(Violation("timeout", "PT9M is below PT10M", "min"))
        }
    }

    @Nested
    @DisplayName("When max is called")
    inner class Max {
        @Test
        fun `given a Duration above the maximum should report it`() {
            val maximum = Duration.ofMinutes(10)

            val node = ValidationNode("timeout", Duration.ofMinutes(11)).apply { max(maximum) }

            node.validate() shouldBe listOf(Violation("timeout", "must be at most PT10M", "max"))
        }

        @Test
        fun `given a Duration at the maximum should accept it`() {
            val maximum = Duration.ofMinutes(10)

            val node = ValidationNode("timeout", Duration.ofMinutes(10)).apply { max(maximum) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no maximum for a Duration should skip the check`() {
            val maximum: Duration? = null

            val node = ValidationNode("timeout", Duration.ofMinutes(11)).apply { max(maximum) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a Duration should build it from the value and the maximum`() {
            val maximum = Duration.ofMinutes(10)

            val node = ValidationNode("timeout", Duration.ofMinutes(11)).apply {
                max(maximum) { max -> "$this is above $max" }
            }

            node.validate() shouldBe listOf(Violation("timeout", "PT11M is above PT10M", "max"))
        }
    }

    @Nested
    @DisplayName("When greaterThan is called")
    inner class GreaterThan {
        @Test
        fun `given a Duration equal to the reference should report it`() {
            val reference = Duration.ofMinutes(10)

            val node = ValidationNode("timeout", Duration.ofMinutes(10)).apply { greaterThan(reference) }

            node.validate() shouldBe listOf(Violation("timeout", "must be greater than PT10M", "greaterThan"))
        }

        @Test
        fun `given a Duration above the reference should accept it`() {
            val reference = Duration.ofMinutes(10)

            val node = ValidationNode("timeout", Duration.ofMinutes(11)).apply { greaterThan(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for a Duration should skip the check`() {
            val reference: Duration? = null

            val node = ValidationNode("timeout", Duration.ofMinutes(10)).apply { greaterThan(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a Duration should build it from the value and the reference`() {
            val reference = Duration.ofMinutes(10)

            val node = ValidationNode("timeout", Duration.ofMinutes(10)).apply {
                greaterThan(reference) { other -> "$this is not greater than $other" }
            }

            node.validate() shouldBe listOf(Violation("timeout", "PT10M is not greater than PT10M", "greaterThan"))
        }
    }

    @Nested
    @DisplayName("When lessThan is called")
    inner class LessThan {
        @Test
        fun `given a Duration equal to the reference should report it`() {
            val reference = Duration.ofMinutes(10)

            val node = ValidationNode("timeout", Duration.ofMinutes(10)).apply { lessThan(reference) }

            node.validate() shouldBe listOf(Violation("timeout", "must be less than PT10M", "lessThan"))
        }

        @Test
        fun `given a Duration below the reference should accept it`() {
            val reference = Duration.ofMinutes(10)

            val node = ValidationNode("timeout", Duration.ofMinutes(9)).apply { lessThan(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no reference for a Duration should skip the check`() {
            val reference: Duration? = null

            val node = ValidationNode("timeout", Duration.ofMinutes(10)).apply { lessThan(reference) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a Duration should build it from the value and the reference`() {
            val reference = Duration.ofMinutes(10)

            val node = ValidationNode("timeout", Duration.ofMinutes(10)).apply {
                lessThan(reference) { other -> "$this is not less than $other" }
            }

            node.validate() shouldBe listOf(Violation("timeout", "PT10M is not less than PT10M", "lessThan"))
        }
    }

    @Nested
    @DisplayName("When between is called")
    inner class Between {
        @Test
        fun `given a LocalDate below the range should report it`() {
            val start = LocalDate.of(2026, 1, 9)
            val end = LocalDate.of(2026, 1, 11)

            val node = ValidationNode("arrivedAt", LocalDate.of(2026, 1, 8)).apply { between(start, end) }

            node.validate() shouldBe listOf(Violation("arrivedAt", "must be between 2026-01-09 and 2026-01-11", "between"))
        }

        @Test
        fun `given a LocalDate above the range should report it`() {
            val start = LocalDate.of(2026, 1, 9)
            val end = LocalDate.of(2026, 1, 11)

            val node = ValidationNode("arrivedAt", LocalDate.of(2026, 1, 12)).apply { between(start, end) }

            node.validate() shouldBe listOf(Violation("arrivedAt", "must be between 2026-01-09 and 2026-01-11", "between"))
        }

        @Test
        fun `given a LocalDate at the lower limit should accept it`() {
            val start = LocalDate.of(2026, 1, 9)
            val end = LocalDate.of(2026, 1, 11)

            val node = ValidationNode("arrivedAt", LocalDate.of(2026, 1, 9)).apply { between(start, end) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a LocalDate inside the range should accept it`() {
            val start = LocalDate.of(2026, 1, 9)
            val end = LocalDate.of(2026, 1, 11)

            val node = ValidationNode("arrivedAt", LocalDate.of(2026, 1, 10)).apply { between(start, end) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a LocalDate at the upper limit should accept it`() {
            val start = LocalDate.of(2026, 1, 9)
            val end = LocalDate.of(2026, 1, 11)

            val node = ValidationNode("arrivedAt", LocalDate.of(2026, 1, 11)).apply { between(start, end) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no start for a LocalDate should skip the check`() {
            val start: LocalDate? = null
            val end = LocalDate.of(2026, 1, 11)

            val node = ValidationNode("arrivedAt", LocalDate.of(2026, 1, 12)).apply { between(start, end) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a LocalDate should build it from the value and the limits`() {
            val start = LocalDate.of(2026, 1, 9)
            val end = LocalDate.of(2026, 1, 11)

            val node = ValidationNode("arrivedAt", LocalDate.of(2026, 1, 12)).apply {
                between(start, end) { from, to -> "$this is not from $from to $to" }
            }

            node.validate() shouldBe listOf(Violation("arrivedAt", "2026-01-12 is not from 2026-01-09 to 2026-01-11", "between"))
        }

        @Test
        fun `given a LocalDateTime below the range should report it`() {
            val start = LocalDateTime.of(2026, 1, 10, 9, 0)
            val end = LocalDateTime.of(2026, 1, 10, 11, 0)

            val node = ValidationNode("arrivedAt", LocalDateTime.of(2026, 1, 10, 8, 0)).apply { between(start, end) }

            node.validate() shouldBe listOf(
                Violation("arrivedAt", "must be between 2026-01-10T09:00 and 2026-01-10T11:00", "between")
            )
        }

        @Test
        fun `given a LocalDateTime above the range should report it`() {
            val start = LocalDateTime.of(2026, 1, 10, 9, 0)
            val end = LocalDateTime.of(2026, 1, 10, 11, 0)

            val node = ValidationNode("arrivedAt", LocalDateTime.of(2026, 1, 10, 12, 0)).apply { between(start, end) }

            node.validate() shouldBe listOf(
                Violation("arrivedAt", "must be between 2026-01-10T09:00 and 2026-01-10T11:00", "between")
            )
        }

        @Test
        fun `given a LocalDateTime at the lower limit should accept it`() {
            val start = LocalDateTime.of(2026, 1, 10, 9, 0)
            val end = LocalDateTime.of(2026, 1, 10, 11, 0)

            val node = ValidationNode("arrivedAt", LocalDateTime.of(2026, 1, 10, 9, 0)).apply { between(start, end) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a LocalDateTime inside the range should accept it`() {
            val start = LocalDateTime.of(2026, 1, 10, 9, 0)
            val end = LocalDateTime.of(2026, 1, 10, 11, 0)

            val node = ValidationNode("arrivedAt", LocalDateTime.of(2026, 1, 10, 10, 0)).apply { between(start, end) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a LocalDateTime at the upper limit should accept it`() {
            val start = LocalDateTime.of(2026, 1, 10, 9, 0)
            val end = LocalDateTime.of(2026, 1, 10, 11, 0)

            val node = ValidationNode("arrivedAt", LocalDateTime.of(2026, 1, 10, 11, 0)).apply { between(start, end) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no start for a LocalDateTime should skip the check`() {
            val start: LocalDateTime? = null
            val end = LocalDateTime.of(2026, 1, 10, 11, 0)

            val node = ValidationNode("arrivedAt", LocalDateTime.of(2026, 1, 10, 12, 0)).apply { between(start, end) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a LocalDateTime should build it from the value and the limits`() {
            val start = LocalDateTime.of(2026, 1, 10, 9, 0)
            val end = LocalDateTime.of(2026, 1, 10, 11, 0)

            val node = ValidationNode("arrivedAt", LocalDateTime.of(2026, 1, 10, 12, 0)).apply {
                between(start, end) { from, to -> "$this is not from $from to $to" }
            }

            node.validate() shouldBe listOf(
                Violation("arrivedAt", "2026-01-10T12:00 is not from 2026-01-10T09:00 to 2026-01-10T11:00", "between")
            )
        }

        @Test
        fun `given a LocalTime below the range should report it`() {
            val start = LocalTime.of(9, 0)
            val end = LocalTime.of(11, 0)

            val node = ValidationNode("arrivedAt", LocalTime.of(8, 0)).apply { between(start, end) }

            node.validate() shouldBe listOf(Violation("arrivedAt", "must be between 09:00 and 11:00", "between"))
        }

        @Test
        fun `given a LocalTime above the range should report it`() {
            val start = LocalTime.of(9, 0)
            val end = LocalTime.of(11, 0)

            val node = ValidationNode("arrivedAt", LocalTime.of(12, 0)).apply { between(start, end) }

            node.validate() shouldBe listOf(Violation("arrivedAt", "must be between 09:00 and 11:00", "between"))
        }

        @Test
        fun `given a LocalTime at the lower limit should accept it`() {
            val start = LocalTime.of(9, 0)
            val end = LocalTime.of(11, 0)

            val node = ValidationNode("arrivedAt", LocalTime.of(9, 0)).apply { between(start, end) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a LocalTime inside the range should accept it`() {
            val start = LocalTime.of(9, 0)
            val end = LocalTime.of(11, 0)

            val node = ValidationNode("arrivedAt", LocalTime.of(10, 0)).apply { between(start, end) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a LocalTime at the upper limit should accept it`() {
            val start = LocalTime.of(9, 0)
            val end = LocalTime.of(11, 0)

            val node = ValidationNode("arrivedAt", LocalTime.of(11, 0)).apply { between(start, end) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no start for a LocalTime should skip the check`() {
            val start: LocalTime? = null
            val end = LocalTime.of(11, 0)

            val node = ValidationNode("arrivedAt", LocalTime.of(12, 0)).apply { between(start, end) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a LocalTime should build it from the value and the limits`() {
            val start = LocalTime.of(9, 0)
            val end = LocalTime.of(11, 0)

            val node = ValidationNode("arrivedAt", LocalTime.of(12, 0)).apply {
                between(start, end) { from, to -> "$this is not from $from to $to" }
            }

            node.validate() shouldBe listOf(Violation("arrivedAt", "12:00 is not from 09:00 to 11:00", "between"))
        }

        @Test
        fun `given an OffsetTime below the range should report it`() {
            val start = OffsetTime.of(9, 0, 0, 0, UTC)
            val end = OffsetTime.of(11, 0, 0, 0, UTC)

            val node = ValidationNode("arrivedAt", OffsetTime.of(8, 0, 0, 0, UTC)).apply { between(start, end) }

            node.validate() shouldBe listOf(Violation("arrivedAt", "must be between 09:00Z and 11:00Z", "between"))
        }

        @Test
        fun `given an OffsetTime above the range should report it`() {
            val start = OffsetTime.of(9, 0, 0, 0, UTC)
            val end = OffsetTime.of(11, 0, 0, 0, UTC)

            val node = ValidationNode("arrivedAt", OffsetTime.of(12, 0, 0, 0, UTC)).apply { between(start, end) }

            node.validate() shouldBe listOf(Violation("arrivedAt", "must be between 09:00Z and 11:00Z", "between"))
        }

        @Test
        fun `given an OffsetTime at the lower limit should accept it`() {
            val start = OffsetTime.of(9, 0, 0, 0, UTC)
            val end = OffsetTime.of(11, 0, 0, 0, UTC)

            val node = ValidationNode("arrivedAt", OffsetTime.of(9, 0, 0, 0, UTC)).apply { between(start, end) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given an OffsetTime inside the range should accept it`() {
            val start = OffsetTime.of(9, 0, 0, 0, UTC)
            val end = OffsetTime.of(11, 0, 0, 0, UTC)

            val node = ValidationNode("arrivedAt", OffsetTime.of(10, 0, 0, 0, UTC)).apply { between(start, end) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given an OffsetTime at the upper limit should accept it`() {
            val start = OffsetTime.of(9, 0, 0, 0, UTC)
            val end = OffsetTime.of(11, 0, 0, 0, UTC)

            val node = ValidationNode("arrivedAt", OffsetTime.of(11, 0, 0, 0, UTC)).apply { between(start, end) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no start for an OffsetTime should skip the check`() {
            val start: OffsetTime? = null
            val end = OffsetTime.of(11, 0, 0, 0, UTC)

            val node = ValidationNode("arrivedAt", OffsetTime.of(12, 0, 0, 0, UTC)).apply { between(start, end) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for an OffsetTime should build it from the value and the limits`() {
            val start = OffsetTime.of(9, 0, 0, 0, UTC)
            val end = OffsetTime.of(11, 0, 0, 0, UTC)

            val node = ValidationNode("arrivedAt", OffsetTime.of(12, 0, 0, 0, UTC)).apply {
                between(start, end) { from, to -> "$this is not from $from to $to" }
            }

            node.validate() shouldBe listOf(Violation("arrivedAt", "12:00Z is not from 09:00Z to 11:00Z", "between"))
        }

        @Test
        fun `given a ZonedDateTime below the range should report it`() {
            val start = ZonedDateTime.of(2026, 1, 10, 9, 0, 0, 0, UTC)
            val end = ZonedDateTime.of(2026, 1, 10, 11, 0, 0, 0, UTC)

            val node = ValidationNode("arrivedAt", ZonedDateTime.of(2026, 1, 10, 8, 0, 0, 0, UTC)).apply {
                between(start, end)
            }

            node.validate() shouldBe listOf(
                Violation("arrivedAt", "must be between 2026-01-10T09:00Z and 2026-01-10T11:00Z", "between")
            )
        }

        @Test
        fun `given a ZonedDateTime above the range should report it`() {
            val start = ZonedDateTime.of(2026, 1, 10, 9, 0, 0, 0, UTC)
            val end = ZonedDateTime.of(2026, 1, 10, 11, 0, 0, 0, UTC)

            val node = ValidationNode("arrivedAt", ZonedDateTime.of(2026, 1, 10, 12, 0, 0, 0, UTC)).apply {
                between(start, end)
            }

            node.validate() shouldBe listOf(
                Violation("arrivedAt", "must be between 2026-01-10T09:00Z and 2026-01-10T11:00Z", "between")
            )
        }

        @Test
        fun `given a ZonedDateTime at the lower limit should accept it`() {
            val start = ZonedDateTime.of(2026, 1, 10, 9, 0, 0, 0, UTC)
            val end = ZonedDateTime.of(2026, 1, 10, 11, 0, 0, 0, UTC)

            val node = ValidationNode("arrivedAt", ZonedDateTime.of(2026, 1, 10, 9, 0, 0, 0, UTC)).apply {
                between(start, end)
            }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a ZonedDateTime inside the range should accept it`() {
            val start = ZonedDateTime.of(2026, 1, 10, 9, 0, 0, 0, UTC)
            val end = ZonedDateTime.of(2026, 1, 10, 11, 0, 0, 0, UTC)

            val node = ValidationNode("arrivedAt", ZonedDateTime.of(2026, 1, 10, 10, 0, 0, 0, UTC)).apply {
                between(start, end)
            }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a ZonedDateTime at the upper limit should accept it`() {
            val start = ZonedDateTime.of(2026, 1, 10, 9, 0, 0, 0, UTC)
            val end = ZonedDateTime.of(2026, 1, 10, 11, 0, 0, 0, UTC)

            val node = ValidationNode("arrivedAt", ZonedDateTime.of(2026, 1, 10, 11, 0, 0, 0, UTC)).apply {
                between(start, end)
            }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no start for a ZonedDateTime should skip the check`() {
            val start: ZonedDateTime? = null
            val end = ZonedDateTime.of(2026, 1, 10, 11, 0, 0, 0, UTC)

            val node = ValidationNode("arrivedAt", ZonedDateTime.of(2026, 1, 10, 12, 0, 0, 0, UTC)).apply {
                between(start, end)
            }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a ZonedDateTime should build it from the value and the limits`() {
            val start = ZonedDateTime.of(2026, 1, 10, 9, 0, 0, 0, UTC)
            val end = ZonedDateTime.of(2026, 1, 10, 11, 0, 0, 0, UTC)

            val node = ValidationNode("arrivedAt", ZonedDateTime.of(2026, 1, 10, 12, 0, 0, 0, UTC)).apply {
                between(start, end) { from, to -> "$this is not from $from to $to" }
            }

            node.validate() shouldBe listOf(
                Violation("arrivedAt", "2026-01-10T12:00Z is not from 2026-01-10T09:00Z to 2026-01-10T11:00Z", "between")
            )
        }

        @Test
        fun `given an OffsetDateTime below the range should report it`() {
            val start = OffsetDateTime.of(2026, 1, 10, 9, 0, 0, 0, UTC)
            val end = OffsetDateTime.of(2026, 1, 10, 11, 0, 0, 0, UTC)

            val node = ValidationNode("arrivedAt", OffsetDateTime.of(2026, 1, 10, 8, 0, 0, 0, UTC)).apply {
                between(start, end)
            }

            node.validate() shouldBe listOf(
                Violation("arrivedAt", "must be between 2026-01-10T09:00Z and 2026-01-10T11:00Z", "between")
            )
        }

        @Test
        fun `given an OffsetDateTime above the range should report it`() {
            val start = OffsetDateTime.of(2026, 1, 10, 9, 0, 0, 0, UTC)
            val end = OffsetDateTime.of(2026, 1, 10, 11, 0, 0, 0, UTC)

            val node = ValidationNode("arrivedAt", OffsetDateTime.of(2026, 1, 10, 12, 0, 0, 0, UTC)).apply {
                between(start, end)
            }

            node.validate() shouldBe listOf(
                Violation("arrivedAt", "must be between 2026-01-10T09:00Z and 2026-01-10T11:00Z", "between")
            )
        }

        @Test
        fun `given an OffsetDateTime at the lower limit should accept it`() {
            val start = OffsetDateTime.of(2026, 1, 10, 9, 0, 0, 0, UTC)
            val end = OffsetDateTime.of(2026, 1, 10, 11, 0, 0, 0, UTC)

            val node = ValidationNode("arrivedAt", OffsetDateTime.of(2026, 1, 10, 9, 0, 0, 0, UTC)).apply {
                between(start, end)
            }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given an OffsetDateTime inside the range should accept it`() {
            val start = OffsetDateTime.of(2026, 1, 10, 9, 0, 0, 0, UTC)
            val end = OffsetDateTime.of(2026, 1, 10, 11, 0, 0, 0, UTC)

            val node = ValidationNode("arrivedAt", OffsetDateTime.of(2026, 1, 10, 10, 0, 0, 0, UTC)).apply {
                between(start, end)
            }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given an OffsetDateTime at the upper limit should accept it`() {
            val start = OffsetDateTime.of(2026, 1, 10, 9, 0, 0, 0, UTC)
            val end = OffsetDateTime.of(2026, 1, 10, 11, 0, 0, 0, UTC)

            val node = ValidationNode("arrivedAt", OffsetDateTime.of(2026, 1, 10, 11, 0, 0, 0, UTC)).apply {
                between(start, end)
            }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no start for an OffsetDateTime should skip the check`() {
            val start: OffsetDateTime? = null
            val end = OffsetDateTime.of(2026, 1, 10, 11, 0, 0, 0, UTC)

            val node = ValidationNode("arrivedAt", OffsetDateTime.of(2026, 1, 10, 12, 0, 0, 0, UTC)).apply {
                between(start, end)
            }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for an OffsetDateTime should build it from the value and the limits`() {
            val start = OffsetDateTime.of(2026, 1, 10, 9, 0, 0, 0, UTC)
            val end = OffsetDateTime.of(2026, 1, 10, 11, 0, 0, 0, UTC)

            val node = ValidationNode("arrivedAt", OffsetDateTime.of(2026, 1, 10, 12, 0, 0, 0, UTC)).apply {
                between(start, end) { from, to -> "$this is not from $from to $to" }
            }

            node.validate() shouldBe listOf(
                Violation("arrivedAt", "2026-01-10T12:00Z is not from 2026-01-10T09:00Z to 2026-01-10T11:00Z", "between")
            )
        }

        @Test
        fun `given an Instant below the range should report it`() {
            val start = Instant.parse("2026-01-10T09:00:00Z")
            val end = Instant.parse("2026-01-10T11:00:00Z")

            val node = ValidationNode("arrivedAt", Instant.parse("2026-01-10T08:00:00Z")).apply { between(start, end) }

            node.validate() shouldBe listOf(
                Violation("arrivedAt", "must be between 2026-01-10T09:00:00Z and 2026-01-10T11:00:00Z", "between")
            )
        }

        @Test
        fun `given an Instant above the range should report it`() {
            val start = Instant.parse("2026-01-10T09:00:00Z")
            val end = Instant.parse("2026-01-10T11:00:00Z")

            val node = ValidationNode("arrivedAt", Instant.parse("2026-01-10T12:00:00Z")).apply { between(start, end) }

            node.validate() shouldBe listOf(
                Violation("arrivedAt", "must be between 2026-01-10T09:00:00Z and 2026-01-10T11:00:00Z", "between")
            )
        }

        @Test
        fun `given an Instant at the lower limit should accept it`() {
            val start = Instant.parse("2026-01-10T09:00:00Z")
            val end = Instant.parse("2026-01-10T11:00:00Z")

            val node = ValidationNode("arrivedAt", Instant.parse("2026-01-10T09:00:00Z")).apply { between(start, end) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given an Instant inside the range should accept it`() {
            val start = Instant.parse("2026-01-10T09:00:00Z")
            val end = Instant.parse("2026-01-10T11:00:00Z")

            val node = ValidationNode("arrivedAt", Instant.parse("2026-01-10T10:00:00Z")).apply { between(start, end) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given an Instant at the upper limit should accept it`() {
            val start = Instant.parse("2026-01-10T09:00:00Z")
            val end = Instant.parse("2026-01-10T11:00:00Z")

            val node = ValidationNode("arrivedAt", Instant.parse("2026-01-10T11:00:00Z")).apply { between(start, end) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no start for an Instant should skip the check`() {
            val start: Instant? = null
            val end = Instant.parse("2026-01-10T11:00:00Z")

            val node = ValidationNode("arrivedAt", Instant.parse("2026-01-10T12:00:00Z")).apply { between(start, end) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for an Instant should build it from the value and the limits`() {
            val start = Instant.parse("2026-01-10T09:00:00Z")
            val end = Instant.parse("2026-01-10T11:00:00Z")

            val node = ValidationNode("arrivedAt", Instant.parse("2026-01-10T12:00:00Z")).apply {
                between(start, end) { from, to -> "$this is not from $from to $to" }
            }

            node.validate() shouldBe listOf(
                Violation("arrivedAt", "2026-01-10T12:00:00Z is not from 2026-01-10T09:00:00Z to 2026-01-10T11:00:00Z", "between")
            )
        }

        @Test
        fun `given a YearMonth below the range should report it`() {
            val start = YearMonth.of(2026, 9)
            val end = YearMonth.of(2026, 11)

            val node = ValidationNode("arrivedAt", YearMonth.of(2026, 8)).apply { between(start, end) }

            node.validate() shouldBe listOf(Violation("arrivedAt", "must be between 2026-09 and 2026-11", "between"))
        }

        @Test
        fun `given a YearMonth above the range should report it`() {
            val start = YearMonth.of(2026, 9)
            val end = YearMonth.of(2026, 11)

            val node = ValidationNode("arrivedAt", YearMonth.of(2026, 12)).apply { between(start, end) }

            node.validate() shouldBe listOf(Violation("arrivedAt", "must be between 2026-09 and 2026-11", "between"))
        }

        @Test
        fun `given a YearMonth at the lower limit should accept it`() {
            val start = YearMonth.of(2026, 9)
            val end = YearMonth.of(2026, 11)

            val node = ValidationNode("arrivedAt", YearMonth.of(2026, 9)).apply { between(start, end) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a YearMonth inside the range should accept it`() {
            val start = YearMonth.of(2026, 9)
            val end = YearMonth.of(2026, 11)

            val node = ValidationNode("arrivedAt", YearMonth.of(2026, 10)).apply { between(start, end) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a YearMonth at the upper limit should accept it`() {
            val start = YearMonth.of(2026, 9)
            val end = YearMonth.of(2026, 11)

            val node = ValidationNode("arrivedAt", YearMonth.of(2026, 11)).apply { between(start, end) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no start for a YearMonth should skip the check`() {
            val start: YearMonth? = null
            val end = YearMonth.of(2026, 11)

            val node = ValidationNode("arrivedAt", YearMonth.of(2026, 12)).apply { between(start, end) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a YearMonth should build it from the value and the limits`() {
            val start = YearMonth.of(2026, 9)
            val end = YearMonth.of(2026, 11)

            val node = ValidationNode("arrivedAt", YearMonth.of(2026, 12)).apply {
                between(start, end) { from, to -> "$this is not from $from to $to" }
            }

            node.validate() shouldBe listOf(Violation("arrivedAt", "2026-12 is not from 2026-09 to 2026-11", "between"))
        }

        @Test
        fun `given a Year below the range should report it`() {
            val start = Year.of(2023)
            val end = Year.of(2025)

            val node = ValidationNode("arrivedAt", Year.of(2022)).apply { between(start, end) }

            node.validate() shouldBe listOf(Violation("arrivedAt", "must be between 2023 and 2025", "between"))
        }

        @Test
        fun `given a Year above the range should report it`() {
            val start = Year.of(2023)
            val end = Year.of(2025)

            val node = ValidationNode("arrivedAt", Year.of(2026)).apply { between(start, end) }

            node.validate() shouldBe listOf(Violation("arrivedAt", "must be between 2023 and 2025", "between"))
        }

        @Test
        fun `given a Year at the lower limit should accept it`() {
            val start = Year.of(2023)
            val end = Year.of(2025)

            val node = ValidationNode("arrivedAt", Year.of(2023)).apply { between(start, end) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a Year inside the range should accept it`() {
            val start = Year.of(2023)
            val end = Year.of(2025)

            val node = ValidationNode("arrivedAt", Year.of(2024)).apply { between(start, end) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a Year at the upper limit should accept it`() {
            val start = Year.of(2023)
            val end = Year.of(2025)

            val node = ValidationNode("arrivedAt", Year.of(2025)).apply { between(start, end) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no start for a Year should skip the check`() {
            val start: Year? = null
            val end = Year.of(2025)

            val node = ValidationNode("arrivedAt", Year.of(2026)).apply { between(start, end) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a Year should build it from the value and the limits`() {
            val start = Year.of(2023)
            val end = Year.of(2025)

            val node = ValidationNode("arrivedAt", Year.of(2026)).apply {
                between(start, end) { from, to -> "$this is not from $from to $to" }
            }

            node.validate() shouldBe listOf(Violation("arrivedAt", "2026 is not from 2023 to 2025", "between"))
        }

        @Test
        fun `given a Date below the range should report it`() {
            val start = Date.from(Instant.parse("2026-01-10T09:00:00Z"))
            val end = Date.from(Instant.parse("2026-01-10T11:00:00Z"))

            val node = ValidationNode("arrivedAt", Date.from(Instant.parse("2026-01-10T08:00:00Z"))).apply {
                between(start, end)
            }

            node.validate() shouldBe listOf(Violation("arrivedAt", "must be between $start and $end", "between"))
        }

        @Test
        fun `given a Date above the range should report it`() {
            val start = Date.from(Instant.parse("2026-01-10T09:00:00Z"))
            val end = Date.from(Instant.parse("2026-01-10T11:00:00Z"))

            val node = ValidationNode("arrivedAt", Date.from(Instant.parse("2026-01-10T12:00:00Z"))).apply {
                between(start, end)
            }

            node.validate() shouldBe listOf(Violation("arrivedAt", "must be between $start and $end", "between"))
        }

        @Test
        fun `given a Date at the lower limit should accept it`() {
            val start = Date.from(Instant.parse("2026-01-10T09:00:00Z"))
            val end = Date.from(Instant.parse("2026-01-10T11:00:00Z"))

            val node = ValidationNode("arrivedAt", Date.from(Instant.parse("2026-01-10T09:00:00Z"))).apply {
                between(start, end)
            }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a Date inside the range should accept it`() {
            val start = Date.from(Instant.parse("2026-01-10T09:00:00Z"))
            val end = Date.from(Instant.parse("2026-01-10T11:00:00Z"))

            val node = ValidationNode("arrivedAt", Date.from(Instant.parse("2026-01-10T10:00:00Z"))).apply {
                between(start, end)
            }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a Date at the upper limit should accept it`() {
            val start = Date.from(Instant.parse("2026-01-10T09:00:00Z"))
            val end = Date.from(Instant.parse("2026-01-10T11:00:00Z"))

            val node = ValidationNode("arrivedAt", Date.from(Instant.parse("2026-01-10T11:00:00Z"))).apply {
                between(start, end)
            }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no start for a Date should skip the check`() {
            val start: Date? = null
            val end = Date.from(Instant.parse("2026-01-10T11:00:00Z"))

            val node = ValidationNode("arrivedAt", Date.from(Instant.parse("2026-01-10T12:00:00Z"))).apply {
                between(start, end)
            }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a Date should build it from the value and the limits`() {
            val value = Date.from(Instant.parse("2026-01-10T12:00:00Z"))
            val start = Date.from(Instant.parse("2026-01-10T09:00:00Z"))
            val end = Date.from(Instant.parse("2026-01-10T11:00:00Z"))

            val node = ValidationNode("arrivedAt", value).apply {
                between(start, end) { from, to -> "$this is not from $from to $to" }
            }

            node.validate() shouldBe listOf(Violation("arrivedAt", "$value is not from $start to $end", "between"))
        }

        @Test
        fun `given a Duration below the range should report it`() {
            val min = Duration.ofMinutes(9)
            val max = Duration.ofMinutes(11)

            val node = ValidationNode("timeout", Duration.ofMinutes(8)).apply { between(min, max) }

            node.validate() shouldBe listOf(Violation("timeout", "must be between PT9M and PT11M", "between"))
        }

        @Test
        fun `given a Duration above the range should report it`() {
            val min = Duration.ofMinutes(9)
            val max = Duration.ofMinutes(11)

            val node = ValidationNode("timeout", Duration.ofMinutes(12)).apply { between(min, max) }

            node.validate() shouldBe listOf(Violation("timeout", "must be between PT9M and PT11M", "between"))
        }

        @Test
        fun `given a Duration at the lower limit should accept it`() {
            val min = Duration.ofMinutes(9)
            val max = Duration.ofMinutes(11)

            val node = ValidationNode("timeout", Duration.ofMinutes(9)).apply { between(min, max) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a Duration inside the range should accept it`() {
            val min = Duration.ofMinutes(9)
            val max = Duration.ofMinutes(11)

            val node = ValidationNode("timeout", Duration.ofMinutes(10)).apply { between(min, max) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a Duration at the upper limit should accept it`() {
            val min = Duration.ofMinutes(9)
            val max = Duration.ofMinutes(11)

            val node = ValidationNode("timeout", Duration.ofMinutes(11)).apply { between(min, max) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no minimum for a Duration should skip the check`() {
            val min: Duration? = null
            val max = Duration.ofMinutes(11)

            val node = ValidationNode("timeout", Duration.ofMinutes(12)).apply { between(min, max) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a Duration should build it from the value and the limits`() {
            val min = Duration.ofMinutes(9)
            val max = Duration.ofMinutes(11)

            val node = ValidationNode("timeout", Duration.ofMinutes(12)).apply {
                between(min, max) { from, to -> "$this is not from $from to $to" }
            }

            node.validate() shouldBe listOf(Violation("timeout", "PT12M is not from PT9M to PT11M", "between"))
        }
    }
}
