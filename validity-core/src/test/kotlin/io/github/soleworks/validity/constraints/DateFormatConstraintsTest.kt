package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class DateFormatConstraintsTest {
    @Nested
    @DisplayName("When isoDate is called")
    inner class IsoDate {
        @ParameterizedTest
        @ValueSource(strings = ["2026-01-10", "2024-02-29"])
        fun `given a valid ISO date should accept it`(birthDate: String) {
            val node = ValidationNode("birthDate", birthDate).apply { isoDate() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["2026-02-29", "2026-1-10", "10/01/2026"])
        fun `given an invalid ISO date should report it`(birthDate: String) {
            val node = ValidationNode("birthDate", birthDate).apply { isoDate() }

            node.validate() shouldBe listOf(Violation("birthDate", "must be an ISO 8601 date"))
        }
    }

    @Nested
    @DisplayName("When isoTime is called")
    inner class IsoTime {
        @ParameterizedTest
        @ValueSource(strings = ["10:15", "10:15:30", "10:15:30.123"])
        fun `given a valid ISO time should accept it`(openingTime: String) {
            val node = ValidationNode("openingTime", openingTime).apply { isoTime() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["24:00", "10:60", "10h15"])
        fun `given an invalid ISO time should report it`(openingTime: String) {
            val node = ValidationNode("openingTime", openingTime).apply { isoTime() }

            node.validate() shouldBe listOf(Violation("openingTime", "must be an ISO 8601 time"))
        }
    }

    @Nested
    @DisplayName("When isoDateTime is called")
    inner class IsoDateTime {
        @ParameterizedTest
        @ValueSource(strings = ["2026-01-10T10:15:30Z", "2026-01-10T10:15:30-03:00"])
        fun `given a valid ISO date and time should accept it`(createdAt: String) {
            val node = ValidationNode("createdAt", createdAt).apply { isoDateTime() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["2026-01-10T10:15:30", "2026-01-10 10:15:30Z"])
        fun `given an invalid ISO date and time should report it`(createdAt: String) {
            val node = ValidationNode("createdAt", createdAt).apply { isoDateTime() }

            node.validate() shouldBe listOf(Violation("createdAt", "must be an ISO 8601 date and time with offset"))
        }
    }

    @Nested
    @DisplayName("When isoDuration is called")
    inner class IsoDuration {
        @ParameterizedTest
        @ValueSource(strings = ["P1Y2M3DT4H5M6S", "PT30M", "P2W", "PT0.5S"])
        fun `given a valid ISO duration should accept it`(timeout: String) {
            val node = ValidationNode("timeout", timeout).apply { isoDuration() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["P", "PT", "1Y", "P1H"])
        fun `given an invalid ISO duration should report it`(timeout: String) {
            val node = ValidationNode("timeout", timeout).apply { isoDuration() }

            node.validate() shouldBe listOf(Violation("timeout", "must be an ISO 8601 duration"))
        }
    }

    @Nested
    @DisplayName("When dateFormat is called")
    inner class DateFormat {
        @ParameterizedTest
        @ValueSource(strings = ["10/01/2026", "29/02/2024"])
        fun `given a valid date in the format should accept it`(birthDate: String) {
            val node = ValidationNode("birthDate", birthDate).apply { dateFormat("dd/MM/yyyy") }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["31/02/2026", "2026-01-10", "1/1/2026"])
        fun `given an invalid date in the format should report it`(birthDate: String) {
            val node = ValidationNode("birthDate", birthDate).apply { dateFormat("dd/MM/yyyy") }

            node.validate() shouldBe listOf(Violation("birthDate", "must match the date format dd/MM/yyyy"))
        }

        @Test
        fun `given a message function should build it from the value and the pattern`() {
            val node = ValidationNode("birthDate", "2026-01-10").apply {
                dateFormat("dd/MM/yyyy") { pattern -> "$this is not $pattern" }
            }

            node.validate() shouldBe listOf(Violation("birthDate", "2026-01-10 is not dd/MM/yyyy"))
        }
    }
}
