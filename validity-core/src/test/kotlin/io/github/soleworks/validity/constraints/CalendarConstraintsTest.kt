package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Validity
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import java.time.Clock
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZoneOffset.UTC
import java.time.ZonedDateTime
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class CalendarConstraintsTest {
    private val monday = Instant.parse("2026-10-12T12:00:00Z")
    private val today = LocalDate.of(2026, 10, 12)
    private val now = LocalDateTime.of(2026, 10, 12, 12, 0)

    @BeforeEach
    fun fixClock() {
        Validity.configure { clock = Clock.fixed(monday, UTC) }
    }

    @AfterEach
    fun restoreClock() {
        Validity.configure { }
    }

    @Nested
    @DisplayName("When minAge is called")
    inner class MinAge {
        @Test
        fun `given someone who turns 18 today should accept it`() {
            val node = ValidationNode(path = "birthDate", value = LocalDate.of(2008, 10, 12)).apply { minAge(18) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given someone who turns 18 tomorrow should report it`() {
            val node = ValidationNode(path = "birthDate", value = LocalDate.of(2008, 10, 13)).apply { minAge(18) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "birthDate", message = "must be at least 18 years old", code = "minAge")
            )
        }

        @Test
        fun `given no minimum should skip the check`() {
            val node = ValidationNode(path = "birthDate", value = today).apply { minAge(null) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a message function should build it from the value and the minimum`() {
            val node = ValidationNode(path = "birthDate", value = LocalDate.of(2010, 1, 1)).apply {
                minAge(18) { min -> "born on $this, not $min yet" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "birthDate", message = "born on 2010-01-01, not 18 yet", code = "minAge")
            )
        }
    }

    @Nested
    @DisplayName("When maxAge is called")
    inner class MaxAge {
        @Test
        fun `given someone who turned 120 today should accept it`() {
            val node = ValidationNode(path = "birthDate", value = LocalDate.of(1906, 10, 12)).apply { maxAge(120) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given someone who turned 121 today should report it`() {
            val node = ValidationNode(path = "birthDate", value = LocalDate.of(1905, 10, 12)).apply { maxAge(120) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "birthDate", message = "must be at most 120 years old", code = "maxAge")
            )
        }

        @Test
        fun `given no maximum should skip the check`() {
            val node = ValidationNode(path = "birthDate", value = LocalDate.of(1800, 1, 1)).apply { maxAge(null) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a message function should build it from the value and the maximum`() {
            val node = ValidationNode(path = "birthDate", value = LocalDate.of(1900, 1, 1)).apply {
                maxAge(120) { max -> "born on $this, older than $max" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "birthDate", message = "born on 1900-01-01, older than 120", code = "maxAge")
            )
        }
    }

    @Nested
    @DisplayName("When withinNext is called")
    inner class WithinNext {
        @Test
        fun `given today and the last day of the window as LocalDates should accept them`() {
            val nodes = listOf(today, today.plusDays(30)).map { date ->
                ValidationNode(path = "appointment", value = date).apply { withinNext(30.days) }
            }

            val violations = nodes.flatMap { node -> node.validate() }

            violations shouldBe emptyList()
        }

        @Test
        fun `given a time inside the window of every date and time should accept it`() {
            val nodes = listOf(
                ValidationNode(path = "localDateTime", value = now.plusHours(1)).apply { withinNext(12.hours) },
                ValidationNode(
                    path = "zonedDateTime",
                    value = ZonedDateTime.of(now.plusHours(1), UTC)
                ).apply { withinNext(12.hours) },
                ValidationNode(
                    path = "offsetDateTime",
                    value = OffsetDateTime.of(now.plusHours(1), UTC)
                ).apply { withinNext(12.hours) },
                ValidationNode(path = "instant", value = monday.plusSeconds(3600)).apply { withinNext(12.hours) }
            )

            val violations = nodes.flatMap { node -> node.validate() }

            violations shouldBe emptyList()
        }

        @Test
        fun `given a LocalDate after the window should report it`() {
            val node = ValidationNode(path = "appointment", value = today.plusDays(31)).apply { withinNext(30.days) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "appointment", message = "must be within the next 30d", code = "withinNext")
            )
        }

        @Test
        fun `given a LocalDate before today should report it`() {
            val node = ValidationNode(path = "appointment", value = today.minusDays(1)).apply { withinNext(30.days) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "appointment", message = "must be within the next 30d", code = "withinNext")
            )
        }

        @Test
        fun `given a time outside the window of every date and time should report it`() {
            val nodes = listOf(
                ValidationNode(path = "localDateTime", value = now.plusHours(13)).apply { withinNext(12.hours) },
                ValidationNode(
                    path = "zonedDateTime",
                    value = ZonedDateTime.of(now.minusHours(1), UTC)
                ).apply { withinNext(12.hours) },
                ValidationNode(
                    path = "offsetDateTime",
                    value = OffsetDateTime.of(now.plusHours(13), UTC)
                ).apply { withinNext(12.hours) },
                ValidationNode(path = "instant", value = monday.minusSeconds(1)).apply { withinNext(12.hours) }
            )

            val violations = nodes.flatMap { node -> node.validate() }

            violations shouldBe listOf("localDateTime", "zonedDateTime", "offsetDateTime", "instant").map { path ->
                Violation(path = path, message = "must be within the next 12h", code = "withinNext")
            }
        }

        @Test
        fun `given no duration should skip the check of every date and time`() {
            val nodes = listOf(
                ValidationNode(path = "localDate", value = today.minusDays(1)).apply { withinNext(null) },
                ValidationNode(path = "localDateTime", value = now.minusHours(1)).apply { withinNext(null) },
                ValidationNode(
                    path = "zonedDateTime",
                    value = ZonedDateTime.of(now.minusHours(1), UTC)
                ).apply { withinNext(null) },
                ValidationNode(
                    path = "offsetDateTime",
                    value = OffsetDateTime.of(now.minusHours(1), UTC)
                ).apply { withinNext(null) },
                ValidationNode(path = "instant", value = monday.minusSeconds(1)).apply { withinNext(null) }
            )

            val violations = nodes.flatMap { node -> node.validate() }

            violations shouldBe emptyList()
        }

        @Test
        fun `given a message function should build it from the value and the duration`() {
            val node = ValidationNode(path = "appointment", value = today.plusDays(31)).apply {
                withinNext(30.days) { duration -> "$this is more than $duration away" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "appointment", message = "2026-11-12 is more than 30d away", code = "withinNext")
            )
        }
    }

    @Nested
    @DisplayName("When withinLast is called")
    inner class WithinLast {
        @Test
        fun `given today and the first day of the window as LocalDates should accept them`() {
            val nodes = listOf(today, today.minusDays(30)).map { date ->
                ValidationNode(path = "createdOn", value = date).apply { withinLast(30.days) }
            }

            val violations = nodes.flatMap { node -> node.validate() }

            violations shouldBe emptyList()
        }

        @Test
        fun `given a time inside the window of every date and time should accept it`() {
            val nodes = listOf(
                ValidationNode(path = "localDateTime", value = now.minusHours(1)).apply { withinLast(12.hours) },
                ValidationNode(
                    path = "zonedDateTime",
                    value = ZonedDateTime.of(now.minusHours(1), UTC)
                ).apply { withinLast(12.hours) },
                ValidationNode(
                    path = "offsetDateTime",
                    value = OffsetDateTime.of(now.minusHours(1), UTC)
                ).apply { withinLast(12.hours) },
                ValidationNode(path = "instant", value = monday.minusSeconds(3600)).apply { withinLast(12.hours) }
            )

            val violations = nodes.flatMap { node -> node.validate() }

            violations shouldBe emptyList()
        }

        @Test
        fun `given a LocalDate before the window should report it`() {
            val node = ValidationNode(path = "createdOn", value = today.minusDays(31)).apply { withinLast(30.days) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "createdOn", message = "must be within the last 30d", code = "withinLast")
            )
        }

        @Test
        fun `given a time outside the window of every date and time should report it`() {
            val nodes = listOf(
                ValidationNode(path = "localDateTime", value = now.minusHours(13)).apply { withinLast(12.hours) },
                ValidationNode(
                    path = "zonedDateTime",
                    value = ZonedDateTime.of(now.plusHours(1), UTC)
                ).apply { withinLast(12.hours) },
                ValidationNode(
                    path = "offsetDateTime",
                    value = OffsetDateTime.of(now.minusHours(13), UTC)
                ).apply { withinLast(12.hours) },
                ValidationNode(path = "instant", value = monday.plusSeconds(1)).apply { withinLast(12.hours) }
            )

            val violations = nodes.flatMap { node -> node.validate() }

            violations shouldBe listOf("localDateTime", "zonedDateTime", "offsetDateTime", "instant").map { path ->
                Violation(path = path, message = "must be within the last 12h", code = "withinLast")
            }
        }

        @Test
        fun `given no duration should skip the check of every date and time`() {
            val nodes = listOf(
                ValidationNode(path = "localDate", value = today.plusDays(1)).apply { withinLast(null) },
                ValidationNode(path = "localDateTime", value = now.plusHours(1)).apply { withinLast(null) },
                ValidationNode(
                    path = "zonedDateTime",
                    value = ZonedDateTime.of(now.plusHours(1), UTC)
                ).apply { withinLast(null) },
                ValidationNode(
                    path = "offsetDateTime",
                    value = OffsetDateTime.of(now.plusHours(1), UTC)
                ).apply { withinLast(null) },
                ValidationNode(path = "instant", value = monday.plusSeconds(1)).apply { withinLast(null) }
            )

            val violations = nodes.flatMap { node -> node.validate() }

            violations shouldBe emptyList()
        }

        @Test
        fun `given a message function should build it from the value and the duration`() {
            val node = ValidationNode(path = "createdOn", value = today.plusDays(1)).apply {
                withinLast(30.days) { duration -> "$this is not in the last $duration" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "createdOn", message = "2026-10-13 is not in the last 30d", code = "withinLast")
            )
        }
    }

    @Nested
    @DisplayName("When today is called")
    inner class Today {
        @Test
        fun `given today as every date and time should accept it`() {
            val nodes = listOf(
                ValidationNode(path = "localDate", value = today).apply { today() },
                ValidationNode(path = "localDateTime", value = now.minusHours(11)).apply { today() },
                ValidationNode(path = "zonedDateTime", value = ZonedDateTime.of(now, UTC)).apply { today() },
                ValidationNode(path = "offsetDateTime", value = OffsetDateTime.of(now, UTC)).apply { today() }
            )

            val violations = nodes.flatMap { node -> node.validate() }

            violations shouldBe emptyList()
        }

        @Test
        fun `given a time already tomorrow in its own zone but today in the zone of the clock should accept it`() {
            val tokyo = ZonedDateTime.of(2026, 10, 13, 8, 0, 0, 0, ZoneId.of("Asia/Tokyo"))
            val node = ValidationNode(path = "receivedAt", value = tokyo).apply { today() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given yesterday as every date and time should report it`() {
            val nodes = listOf(
                ValidationNode(path = "localDate", value = today.minusDays(1)).apply { today() },
                ValidationNode(path = "localDateTime", value = now.minusDays(1)).apply { today() },
                ValidationNode(
                    path = "zonedDateTime",
                    value = ZonedDateTime.of(now.minusDays(1), UTC)
                ).apply { today() },
                ValidationNode(
                    path = "offsetDateTime",
                    value = OffsetDateTime.of(now.minusDays(1), UTC)
                ).apply { today() }
            )

            val violations = nodes.flatMap { node -> node.validate() }

            violations shouldBe listOf("localDate", "localDateTime", "zonedDateTime", "offsetDateTime").map { path ->
                Violation(path = path, message = "must be today", code = "today")
            }
        }
    }

    @Nested
    @DisplayName("When weekday is called")
    inner class Weekday {
        @Test
        fun `given a Monday as every date and time should accept it`() {
            val nodes = listOf(
                ValidationNode(path = "localDate", value = today).apply { weekday() },
                ValidationNode(path = "localDateTime", value = now).apply { weekday() },
                ValidationNode(path = "zonedDateTime", value = ZonedDateTime.of(now, UTC)).apply { weekday() },
                ValidationNode(path = "offsetDateTime", value = OffsetDateTime.of(now, UTC)).apply { weekday() }
            )

            val violations = nodes.flatMap { node -> node.validate() }

            violations shouldBe emptyList()
        }

        @Test
        fun `given a Saturday as every date and time should report it`() {
            val saturday = now.minusDays(2)
            val nodes = listOf(
                ValidationNode(path = "localDate", value = saturday.toLocalDate()).apply { weekday() },
                ValidationNode(path = "localDateTime", value = saturday).apply { weekday() },
                ValidationNode(path = "zonedDateTime", value = ZonedDateTime.of(saturday, UTC)).apply { weekday() },
                ValidationNode(path = "offsetDateTime", value = OffsetDateTime.of(saturday, UTC)).apply { weekday() }
            )

            val violations = nodes.flatMap { node -> node.validate() }

            violations shouldBe listOf("localDate", "localDateTime", "zonedDateTime", "offsetDateTime").map { path ->
                Violation(path = path, message = "must be a weekday", code = "weekday")
            }
        }
    }

    @Nested
    @DisplayName("When weekend is called")
    inner class Weekend {
        @Test
        fun `given a Saturday and a Sunday as every date and time should accept them`() {
            val nodes = listOf(now.minusDays(2), now.minusDays(1)).flatMap { day ->
                listOf(
                    ValidationNode(path = "localDate", value = day.toLocalDate()).apply { weekend() },
                    ValidationNode(path = "localDateTime", value = day).apply { weekend() },
                    ValidationNode(path = "zonedDateTime", value = ZonedDateTime.of(day, UTC)).apply { weekend() },
                    ValidationNode(path = "offsetDateTime", value = OffsetDateTime.of(day, UTC)).apply { weekend() }
                )
            }

            val violations = nodes.flatMap { node -> node.validate() }

            violations shouldBe emptyList()
        }

        @Test
        fun `given a Monday as every date and time should report it`() {
            val nodes = listOf(
                ValidationNode(path = "localDate", value = today).apply { weekend() },
                ValidationNode(path = "localDateTime", value = now).apply { weekend() },
                ValidationNode(path = "zonedDateTime", value = ZonedDateTime.of(now, UTC)).apply { weekend() },
                ValidationNode(path = "offsetDateTime", value = OffsetDateTime.of(now, UTC)).apply { weekend() }
            )

            val violations = nodes.flatMap { node -> node.validate() }

            violations shouldBe listOf("localDate", "localDateTime", "zonedDateTime", "offsetDateTime").map { path ->
                Violation(path = path, message = "must be on a weekend", code = "weekend")
            }
        }
    }
}
