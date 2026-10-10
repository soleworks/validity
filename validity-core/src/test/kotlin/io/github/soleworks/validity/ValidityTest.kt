package io.github.soleworks.validity

import io.github.soleworks.validity.constraints.brazil.cpf
import io.github.soleworks.validity.constraints.china.residentId
import io.github.soleworks.validity.constraints.future
import io.github.soleworks.validity.constraints.futureOrPresent
import io.github.soleworks.validity.constraints.past
import io.github.soleworks.validity.constraints.pastOrPresent
import io.github.soleworks.validity.samples.Customer
import io.github.soleworks.validity.samples.Recipient
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.Clock
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.Year
import java.time.YearMonth
import java.time.ZoneOffset.UTC
import java.time.ZonedDateTime
import java.util.Date

class ValidityTest {
    @AfterEach
    fun restoreDefaultMessages() {
        Validity.configure { }
    }

    @Nested
    @DisplayName("When configure is called")
    inner class Configure {
        @Test
        fun `given no configuration should report the default message in English`() {
            val customer = Customer(name = "A")

            customer.validate().violations shouldBe listOf(Violation("name", "must have at least 2 characters", "minLength"))
        }

        @Test
        fun `given a message with a placeholder should fill it with the value of the rule`() {
            Validity.configure {
                messages {
                    minLength = "deve ter pelo menos {min} caracteres"
                }
            }

            val customer = Customer(name = "A")

            customer.validate().violations shouldBe listOf(Violation("name", "deve ter pelo menos 2 caracteres", "minLength"))
        }

        @Test
        fun `given the required message should report it for a missing field`() {
            Validity.configure {
                messages {
                    required = "é obrigatório"
                }
            }

            val customer = Customer(name = null)

            customer.validate().violations shouldBe listOf(Violation("name", "é obrigatório", "required"))
        }

        @Test
        fun `given a message passed to the function should keep it over the configured one`() {
            Validity.configure {
                messages {
                    required = "é obrigatório"
                }
            }

            val customer = Customer(email = null)

            customer.validate().violations shouldBe listOf(Violation("email", "email is mandatory", "required"))
        }

        @Test
        fun `given a message of a country should report it`() {
            Validity.configure {
                messages {
                    brazil {
                        cpf = "CPF inválido"
                    }
                }
            }

            val node = ValidationNode("cpf", "123").apply { cpf() }

            node.validate() shouldBe listOf(Violation("cpf", "CPF inválido", "cpf"))
        }

        @Test
        fun `given a second configuration should replace the first one`() {
            Validity.configure {
                messages {
                    minLength = "deve ter pelo menos {min} caracteres"
                }
            }
            Validity.configure {
                messages {
                    required = "é obrigatório"
                }
            }

            val customer = Customer(name = "A")

            customer.validate().violations shouldBe listOf(Violation("name", "must have at least 2 characters", "minLength"))
        }

        @Test
        fun `given the presence messages should report them with the fields`() {
            Validity.configure {
                messages {
                    atLeastOneOf = "informe {fields}"
                    forbidden = "deve ficar vazio"
                }
            }

            val recipient = Recipient(email = null, phone = null, companyName = "Ana LTDA")

            recipient.validate().violations shouldBe listOf(
                Violation("email", "informe email, phone", "atLeastOneOf"),
                Violation("phone", "informe email, phone", "atLeastOneOf"),
                Violation("companyName", "deve ficar vazio", "forbidden")
            )
        }

        @Test
        fun `given a clock in 2000 should treat 2010 as the future in past`() {
            Validity.configure {
                clock = Clock.fixed(Instant.parse("2000-06-15T12:00:00Z"), UTC)
            }

            val nodes = listOf(
                ValidationNode("localDate", LocalDate.of(2010, 1, 1)).apply { past() },
                ValidationNode("localDateTime", LocalDateTime.of(2010, 1, 1, 0, 0)).apply { past() },
                ValidationNode("zonedDateTime", ZonedDateTime.of(2010, 1, 1, 0, 0, 0, 0, UTC)).apply { past() },
                ValidationNode("offsetDateTime", OffsetDateTime.of(2010, 1, 1, 0, 0, 0, 0, UTC)).apply { past() },
                ValidationNode("instant", Instant.parse("2010-01-01T00:00:00Z")).apply { past() },
                ValidationNode("yearMonth", YearMonth.of(2010, 1)).apply { past() },
                ValidationNode("year", Year.of(2010)).apply { past() },
                ValidationNode("date", Date.from(Instant.parse("2010-01-01T00:00:00Z"))).apply { past() }
            )

            nodes.flatMap { it.validate() }.map { it.path } shouldBe listOf(
                "localDate", "localDateTime", "zonedDateTime", "offsetDateTime", "instant", "yearMonth", "year", "date"
            )
        }

        @Test
        fun `given a clock in 2000 should treat 2010 as the future in pastOrPresent`() {
            Validity.configure {
                clock = Clock.fixed(Instant.parse("2000-06-15T12:00:00Z"), UTC)
            }

            val nodes = listOf(
                ValidationNode("localDate", LocalDate.of(2010, 1, 1)).apply { pastOrPresent() },
                ValidationNode("localDateTime", LocalDateTime.of(2010, 1, 1, 0, 0)).apply { pastOrPresent() },
                ValidationNode("zonedDateTime", ZonedDateTime.of(2010, 1, 1, 0, 0, 0, 0, UTC)).apply { pastOrPresent() },
                ValidationNode("offsetDateTime", OffsetDateTime.of(2010, 1, 1, 0, 0, 0, 0, UTC)).apply { pastOrPresent() },
                ValidationNode("instant", Instant.parse("2010-01-01T00:00:00Z")).apply { pastOrPresent() },
                ValidationNode("yearMonth", YearMonth.of(2010, 1)).apply { pastOrPresent() },
                ValidationNode("year", Year.of(2010)).apply { pastOrPresent() },
                ValidationNode("date", Date.from(Instant.parse("2010-01-01T00:00:00Z"))).apply { pastOrPresent() }
            )

            nodes.flatMap { it.validate() }.map { it.path } shouldBe listOf(
                "localDate", "localDateTime", "zonedDateTime", "offsetDateTime", "instant", "yearMonth", "year", "date"
            )
        }

        @Test
        fun `given a clock in 2000 should accept 2010 in future and futureOrPresent`() {
            Validity.configure {
                clock = Clock.fixed(Instant.parse("2000-06-15T12:00:00Z"), UTC)
            }

            val nodes = listOf(
                ValidationNode("localDate", LocalDate.of(2010, 1, 1)).apply { future(); futureOrPresent() },
                ValidationNode("localDateTime", LocalDateTime.of(2010, 1, 1, 0, 0)).apply { future(); futureOrPresent() },
                ValidationNode("zonedDateTime", ZonedDateTime.of(2010, 1, 1, 0, 0, 0, 0, UTC)).apply {
                    future()
                    futureOrPresent()
                },
                ValidationNode("offsetDateTime", OffsetDateTime.of(2010, 1, 1, 0, 0, 0, 0, UTC)).apply {
                    future()
                    futureOrPresent()
                },
                ValidationNode("instant", Instant.parse("2010-01-01T00:00:00Z")).apply { future(); futureOrPresent() },
                ValidationNode("yearMonth", YearMonth.of(2010, 1)).apply { future(); futureOrPresent() },
                ValidationNode("year", Year.of(2010)).apply { future(); futureOrPresent() },
                ValidationNode("date", Date.from(Instant.parse("2010-01-01T00:00:00Z"))).apply { future(); futureOrPresent() }
            )

            nodes.flatMap { it.validate() } shouldBe emptyList()
        }

        @Test
        fun `given a clock should treat its own day as the present`() {
            Validity.configure {
                clock = Clock.fixed(Instant.parse("2000-06-15T12:00:00Z"), UTC)
            }

            val node = ValidationNode("date", LocalDate.of(2000, 6, 15)).apply {
                past()
                pastOrPresent()
                future()
                futureOrPresent()
            }

            node.validate() shouldBe listOf(
                Violation("date", "must be in the past", "past"),
                Violation("date", "must be in the future", "future")
            )
        }

        @Test
        fun `given a clock before a birth date should reject the document born after it`() {
            Validity.configure {
                clock = Clock.fixed(Instant.parse("1970-01-01T00:00:00Z"), UTC)
            }

            val node = ValidationNode("residentId", "520323197806058856").apply { residentId() }

            node.validate() shouldBe listOf(
                Violation("residentId", "must be a valid resident identity card number", "residentId")
            )
        }
    }
}
