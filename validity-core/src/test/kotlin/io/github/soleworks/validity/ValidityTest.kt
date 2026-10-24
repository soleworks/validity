package io.github.soleworks.validity

import io.github.soleworks.validity.constraints.brazil.cpf
import io.github.soleworks.validity.constraints.future
import io.github.soleworks.validity.constraints.futureOrPresent
import io.github.soleworks.validity.constraints.past
import io.github.soleworks.validity.constraints.pastOrPresent
import io.github.soleworks.validity.samples.Address
import io.github.soleworks.validity.samples.Customer
import io.github.soleworks.validity.samples.Recipient
import io.kotest.matchers.shouldBe
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
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ValidityTest {
    @AfterEach
    fun restoreDefaultMessages() {
        Validity.configure { }
    }

    @Nested
    @DisplayName("When configure is called")
    inner class Configure {
        @Test
        fun `given a message passed to the function should keep it over the configured one`() {
            Validity.configure {
                messages {
                    required = "é obrigatório"
                }
            }
            val customer = Customer(email = null)

            val result = customer.validate()

            result.violations shouldBe listOf(
                Violation(path = "email", message = "email is mandatory", code = "required")
            )
        }

        @Test
        fun `given a clock in 2000 should accept 2010 in future and futureOrPresent`() {
            Validity.configure {
                clock = Clock.fixed(Instant.parse("2000-06-15T12:00:00Z"), UTC)
            }
            val nodes = listOf(
                ValidationNode(
                    path = "localDate",
                    value = LocalDate.of(2010, 1, 1)
                ).apply {
                    future()
                    futureOrPresent()
                },
                ValidationNode(
                    path = "localDateTime",
                    value = LocalDateTime.of(2010, 1, 1, 0, 0)
                ).apply {
                    future()
                    futureOrPresent()
                },
                ValidationNode(path = "zonedDateTime", value = ZonedDateTime.of(2010, 1, 1, 0, 0, 0, 0, UTC)).apply {
                    future()
                    futureOrPresent()
                },
                ValidationNode(path = "offsetDateTime", value = OffsetDateTime.of(2010, 1, 1, 0, 0, 0, 0, UTC)).apply {
                    future()
                    futureOrPresent()
                },
                ValidationNode(
                    path = "instant",
                    value = Instant.parse("2010-01-01T00:00:00Z")
                ).apply {
                    future()
                    futureOrPresent()
                },
                ValidationNode(path = "yearMonth", value = YearMonth.of(2010, 1)).apply {
                    future()
                    futureOrPresent()
                },
                ValidationNode(path = "year", value = Year.of(2010)).apply {
                    future()
                    futureOrPresent()
                },
                ValidationNode(
                    path = "date",
                    value = Date.from(Instant.parse("2010-01-01T00:00:00Z"))
                ).apply {
                    future()
                    futureOrPresent()
                }
            )

            val violations = nodes.flatMap { it.validate() }

            violations shouldBe emptyList()
        }

        @Test
        fun `given no configuration should report the default message in English`() {
            val customer = Customer(name = "A")

            val result = customer.validate()

            result.violations shouldBe listOf(
                Violation(path = "name", message = "must have at least 2 characters", code = "minLength")
            )
        }

        @Test
        fun `given the required message should report it for a missing field`() {
            Validity.configure {
                messages {
                    required = "é obrigatório"
                }
            }
            val customer = Customer(name = null)

            val result = customer.validate()

            result.violations shouldBe listOf(
                Violation(path = "name", message = "é obrigatório", code = "required")
            )
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
            val node = ValidationNode(path = "cpf", value = "123").apply { cpf() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "cpf", message = "CPF inválido", code = "cpf")
            )
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

            val result = recipient.validate()

            result.violations shouldBe listOf(
                Violation(
                    path = "",
                    message = "informe email, phone",
                    code = "atLeastOneOf",
                    metadata = mapOf("fields" to listOf("email", "phone"))
                ),
                Violation(path = "companyName", message = "deve ficar vazio", code = "forbidden")
            )
        }

        @Test
        fun `given a clock in 2000 should treat 2010 as the future in past`() {
            Validity.configure {
                clock = Clock.fixed(Instant.parse("2000-06-15T12:00:00Z"), UTC)
            }
            val nodes = listOf(
                ValidationNode(path = "localDate", value = LocalDate.of(2010, 1, 1)).apply { past() },
                ValidationNode(path = "localDateTime", value = LocalDateTime.of(2010, 1, 1, 0, 0)).apply { past() },
                ValidationNode(
                    path = "zonedDateTime",
                    value = ZonedDateTime.of(2010, 1, 1, 0, 0, 0, 0, UTC)
                ).apply { past() },
                ValidationNode(
                    path = "offsetDateTime",
                    value = OffsetDateTime.of(2010, 1, 1, 0, 0, 0, 0, UTC)
                ).apply { past() },
                ValidationNode(path = "instant", value = Instant.parse("2010-01-01T00:00:00Z")).apply { past() },
                ValidationNode(path = "yearMonth", value = YearMonth.of(2010, 1)).apply { past() },
                ValidationNode(path = "year", value = Year.of(2010)).apply { past() },
                ValidationNode(path = "date", value = Date.from(Instant.parse("2010-01-01T00:00:00Z"))).apply { past() }
            )

            val violations = nodes.flatMap { it.validate() }

            violations.map { it.path } shouldBe listOf(
                "localDate",
                "localDateTime",
                "zonedDateTime",
                "offsetDateTime",
                "instant",
                "yearMonth",
                "year",
                "date"
            )
        }

        @Test
        fun `given a clock in 2000 should treat 2010 as the future in pastOrPresent`() {
            Validity.configure {
                clock = Clock.fixed(Instant.parse("2000-06-15T12:00:00Z"), UTC)
            }
            val nodes = listOf(
                ValidationNode(path = "localDate", value = LocalDate.of(2010, 1, 1)).apply { pastOrPresent() },
                ValidationNode(
                    path = "localDateTime",
                    value = LocalDateTime.of(2010, 1, 1, 0, 0)
                ).apply { pastOrPresent() },
                ValidationNode(
                    path = "zonedDateTime",
                    value = ZonedDateTime.of(2010, 1, 1, 0, 0, 0, 0, UTC)
                ).apply { pastOrPresent() },
                ValidationNode(
                    path = "offsetDateTime",
                    value = OffsetDateTime.of(2010, 1, 1, 0, 0, 0, 0, UTC)
                ).apply { pastOrPresent() },
                ValidationNode(
                    path = "instant",
                    value = Instant.parse("2010-01-01T00:00:00Z")
                ).apply { pastOrPresent() },
                ValidationNode(path = "yearMonth", value = YearMonth.of(2010, 1)).apply { pastOrPresent() },
                ValidationNode(path = "year", value = Year.of(2010)).apply { pastOrPresent() },
                ValidationNode(
                    path = "date",
                    value = Date.from(Instant.parse("2010-01-01T00:00:00Z"))
                ).apply { pastOrPresent() }
            )

            val violations = nodes.flatMap { it.validate() }

            violations.map { it.path } shouldBe listOf(
                "localDate",
                "localDateTime",
                "zonedDateTime",
                "offsetDateTime",
                "instant",
                "yearMonth",
                "year",
                "date"
            )
        }

        @Test
        fun `given a clock should treat its own day as the present`() {
            Validity.configure {
                clock = Clock.fixed(Instant.parse("2000-06-15T12:00:00Z"), UTC)
            }
            val node = ValidationNode(path = "date", value = LocalDate.of(2000, 6, 15)).apply {
                past()
                pastOrPresent()
                future()
                futureOrPresent()
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "date", message = "must be in the past", code = "past"),
                Violation(path = "date", message = "must be in the future", code = "future")
            )
        }

        @Test
        fun `given the at most and exactly one messages should report them with the fields`() {
            Validity.configure {
                messages {
                    atMostOneOf = "no máximo um de {fields}"
                    exactlyOneOf = "exatamente um de {fields}"
                }
            }
            val recipient = Recipient(pixKey = null, cnpj = "11222333000181")

            val result = recipient.validate()

            result.violations shouldBe listOf(
                Violation(
                    path = "payment",
                    message = "exatamente um de pixKey, bankAccount, boleto",
                    code = "exactlyOneOf",
                    metadata = mapOf("fields" to listOf("pixKey", "bankAccount", "boleto"))
                ),
                Violation(
                    path = "",
                    message = "no máximo um de cpf, cnpj, passport",
                    code = "atMostOneOf",
                    metadata = mapOf("fields" to listOf("cpf", "cnpj"))
                )
            )
        }

        @Test
        fun `given a message with a placeholder should fill it with the value of the rule`() {
            Validity.configure {
                messages {
                    minLength = "deve ter pelo menos {min} caracteres"
                }
            }
            val customer = Customer(name = "A")

            val result = customer.validate()

            result.violations shouldBe listOf(
                Violation(path = "name", message = "deve ter pelo menos 2 caracteres", code = "minLength")
            )
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

            val result = customer.validate()

            result.violations shouldBe listOf(
                Violation(path = "name", message = "must have at least 2 characters", code = "minLength")
            )
        }

        @Test
        fun `given the path and value placeholders should fill them in the configured messages`() {
            Validity.configure {
                messages {
                    minLength = "{path} precisa de pelo menos {min} caracteres, recebeu '{value}'"
                    required = "{path} é obrigatório"
                }
            }
            val customer = Customer(name = "A", address = Address(street = null))

            val result = customer.validate()

            result.violations shouldBe listOf(
                Violation(
                    path = "name",
                    message = "name precisa de pelo menos 2 caracteres, recebeu 'A'",
                    code = "minLength"
                ),
                Violation(path = "address.street", message = "address.street é obrigatório", code = "required")
            )
        }
    }
}
