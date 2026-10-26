package io.github.soleworks.validity

import io.github.soleworks.validity.constraints.after
import io.github.soleworks.validity.constraints.before
import io.github.soleworks.validity.constraints.isTrue
import io.github.soleworks.validity.constraints.max
import io.github.soleworks.validity.constraints.maxDecimalPlaces
import io.github.soleworks.validity.constraints.min
import io.github.soleworks.validity.constraints.notEqualTo
import io.github.soleworks.validity.constraints.positive
import io.github.soleworks.validity.constraints.validate
import io.github.soleworks.validity.samples.Address
import io.github.soleworks.validity.samples.Contact
import io.github.soleworks.validity.samples.Customer
import io.github.soleworks.validity.samples.Invoice
import io.github.soleworks.validity.samples.Ledger
import io.github.soleworks.validity.samples.Order
import io.github.soleworks.validity.samples.PaymentMethod
import io.kotest.matchers.shouldBe
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.YearMonth
import java.util.UUID
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class AccessorsTest {
    @Nested
    @DisplayName("When each is called")
    inner class Each {
        @Test
        fun `given a short tag should report it with its index`() {
            val order = Order(tags = listOf("gift", "x"))

            val result = order.validateBlocking()

            result.violations shouldBe listOf(
                Violation(path = "tags[1]", message = "must have at least 3 characters", code = "minLength")
            )
        }

        @Test
        fun `given a list rule and item rules should report the list before its items`() {
            val order = Order(tags = listOf("a", "b", "gift", "sale"))

            val result = order.validateBlocking()

            result.violations shouldBe listOf(
                Violation(path = "tags", message = "must have at most 3 tags", code = ""),
                Violation(path = "tags[0]", message = "must have at least 3 characters", code = "minLength"),
                Violation(path = "tags[1]", message = "must have at least 3 characters", code = "minLength")
            )
        }

        @Test
        fun `given boxes of products should report the product with both indexes`() {
            val order = Order(boxes = listOf(listOf("SKU-1", "S2")))

            val result = order.validateBlocking()

            result.violations shouldBe listOf(
                Violation(path = "boxes[0][1]", message = "must have at least 5 characters", code = "minLength")
            )
        }

        @Test
        fun `given a short coupon code in an array should report it with its index`() {
            val order = Order(couponCodes = arrayOf("PROMO10", "OFF"))

            val result = order.validateBlocking()

            result.violations shouldBe listOf(
                Violation(path = "couponCodes[1]", message = "must have at least 5 characters", code = "minLength")
            )
        }

        @Test
        fun `given a rule on a price entry should report it with its currency`() {
            val order = Order(prices = mapOf("BRL" to 50))

            val result = order.validateBlocking()

            result.violations shouldBe listOf(
                Violation(path = "prices[BRL]", message = "must be at least 100 in BRL", code = "")
            )
        }
    }

    @Nested
    @DisplayName("When eachKey is called")
    inner class EachKey {
        @Test
        fun `given a currency code that is too short should report it with the key`() {
            val order = Order(prices = mapOf("US" to 1000))

            val result = order.validateBlocking()

            result.violations shouldBe listOf(
                Violation(path = "prices[US]", message = "must have at least 3 characters", code = "minLength")
            )
        }
    }

    @Nested
    @DisplayName("When eachValue is called")
    inner class EachValue {
        @Test
        fun `given a missing price should report it with its currency`() {
            val order = Order(prices = mapOf("USD" to null))

            val result = order.validateBlocking()

            result.violations shouldBe listOf(
                Violation(path = "prices[USD]", message = "is required", code = "notNull")
            )
        }
    }

    @Nested
    @DisplayName("When valid is called")
    inner class Valid {
        @Test
        fun `given an address without a street should report it under the address`() {
            val customer = Customer(address = Address(street = null))

            val result = customer.validateBlocking()

            result.violations shouldBe listOf(
                Violation(path = "address.street", message = "is required", code = "required")
            )
        }

        @Test
        fun `given a list of addresses should report each one under its index`() {
            val customer = Customer(addresses = listOf(Address(), Address(street = null)))

            val result = customer.validateBlocking()

            result.violations shouldBe listOf(
                Violation(path = "addresses[1].street", message = "is required", code = "required")
            )
        }

        @Test
        fun `given an address with a short phone should report it with the full path`() {
            val customer = Customer(addresses = listOf(Address(phones = listOf("123"))))

            val result = customer.validateBlocking()

            result.violations shouldBe listOf(
                Violation(
                    path = "addresses[0].phones[0]",
                    message = "must have at least 8 characters",
                    code = "minLength"
                )
            )
        }

        @Test
        fun `given a customer without an address should report only that it is required`() {
            val customer = Customer(address = null)

            val result = customer.validateBlocking()

            result.violations shouldBe listOf(
                Violation(path = "address", message = "is required", code = "required")
            )
        }

        @Test
        fun `given a contact with a short phone should report it under the contact`() {
            val customer = Customer(contact = Contact(phone = "123"))

            val result = customer.validateBlocking()

            result.violations shouldBe listOf(
                Violation(path = "contact.phone", message = "must have at least 8 characters", code = "minLength")
            )
        }

        @Test
        fun `given a list of contacts should report each one under its index`() {
            val customer = Customer(contacts = listOf(Contact(), Contact(phone = null)))

            val result = customer.validateBlocking()

            result.violations shouldBe listOf(
                Violation(path = "contacts[1].phone", message = "is required", code = "required")
            )
        }

        @Test
        fun `given a customer without a contact should report only that it is required`() {
            val customer = Customer(contact = null)

            val result = customer.validateBlocking()

            result.violations shouldBe listOf(
                Violation(path = "contact", message = "is required", code = "required")
            )
        }

        @Test
        fun `given an invoice that does not add up should report it at the invoice`() {
            val ledger = Ledger(invoice = Invoice(total = 5))

            val result = ledger.validateBlocking()

            result.violations shouldBe listOf(
                Violation(path = "invoice", message = "items must add up to the total", code = "total")
            )
        }

        @Test
        fun `given a list of invoices should report each one at its index`() {
            val ledger = Ledger(invoices = listOf(Invoice(), Invoice(total = 5)))

            val result = ledger.validateBlocking()

            result.violations shouldBe listOf(
                Violation(path = "invoices[1]", message = "items must add up to the total", code = "total")
            )
        }
    }

    @Nested
    @DisplayName("When convert is called")
    inner class Convert {
        @Test
        fun `given a price the converter reads should check the converted value`() {
            val node = ValidationNode(path = "price", value = "R$ 10.00").apply {
                convert({ text -> text.removePrefix("R$ ").toBigDecimalOrNull() }) { positive() }
            }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a price the converter cannot read should report it`() {
            val node = ValidationNode(path = "price", value = "10 reais").apply {
                convert({ text -> text.removePrefix("R$ ").toBigDecimalOrNull() }) { positive() }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "price", message = "has an invalid format", code = "convert")
            )
        }

        @Test
        fun `given a converted value that breaks a rule should report it at the same path`() {
            val node = ValidationNode(path = "createdAt", value = 2_000_000_000_000L).apply {
                convert({ millis -> Instant.ofEpochMilli(millis) }) { before(Instant.parse("2030-01-01T00:00:00Z")) }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "createdAt", message = "must be before 2030-01-01T00:00:00Z", code = "before")
            )
        }

        @Test
        fun `given a custom message should report it`() {
            val node = ValidationNode(path = "price", value = "10 reais").apply {
                convert({ text -> text.toBigDecimalOrNull() }, message = "must be a price like 10.00") { positive() }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "price", message = "must be a price like 10.00", code = "convert")
            )
        }
    }

    @Nested
    @DisplayName("When convertToBigDecimal is called")
    inner class ConvertToBigDecimal {
        @Test
        fun `given an amount written as a number should check it as a BigDecimal`() {
            val node = ValidationNode(path = "value", value = "10.50").apply {
                convertToBigDecimal {
                    positive()
                    maxDecimalPlaces(2)
                }
            }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["abc", "10,50", "", " "])
        fun `given an amount that is not a number should report it`(
            amount: String
        ) {
            val node = ValidationNode(path = "value", value = amount).apply { convertToBigDecimal { positive() } }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "value", message = "must be a valid number", code = "convertToBigDecimal")
            )
        }

        @Test
        fun `given a number that breaks a BigDecimal rule should report it at the same path`() {
            val node = ValidationNode(path = "value", value = "-1").apply { convertToBigDecimal { positive() } }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "value", message = "must be positive", code = "positive")
            )
        }

        @Test
        fun `given a custom message should report it`() {
            val node = ValidationNode(path = "value", value = "abc").apply {
                convertToBigDecimal("must be an amount like 10.50") { positive() }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "value", message = "must be an amount like 10.50", code = "convertToBigDecimal")
            )
        }
    }

    @Nested
    @DisplayName("When convertToInt is called")
    inner class ConvertToInt {
        @Test
        fun `given a quantity written as an integer should check it as an Int`() {
            val node = ValidationNode(path = "quantity", value = "42").apply { convertToInt { min(1) } }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["4.2", "abc", "99999999999"])
        fun `given a quantity that is not an Int should report it`(
            quantity: String
        ) {
            val node = ValidationNode(path = "quantity", value = quantity).apply { convertToInt { min(1) } }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be a valid integer", code = "convertToInt")
            )
        }

        @Test
        fun `given an integer that breaks an Int rule should report it at the same path`() {
            val node = ValidationNode(path = "quantity", value = "0").apply { convertToInt { min(1) } }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be at least 1", code = "min")
            )
        }
    }

    @Nested
    @DisplayName("When convertToLong is called")
    inner class ConvertToLong {
        @Test
        fun `given an id written as a long integer should check it as a Long`() {
            val node = ValidationNode(path = "id", value = "9000000000").apply { convertToLong { min(1L) } }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given an id that is not a Long should report it`() {
            val node = ValidationNode(path = "id", value = "9e9").apply { convertToLong { min(1L) } }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "id", message = "must be a valid integer", code = "convertToLong")
            )
        }
    }

    @Nested
    @DisplayName("When convertToLocalDate is called")
    inner class ConvertToLocalDate {
        @Test
        fun `given a birth date written in ISO 8601 should check it as a LocalDate`() {
            val node = ValidationNode(path = "birthDate", value = "1990-05-20").apply {
                convertToLocalDate { before(LocalDate.of(2000, 1, 1)) }
            }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["20/05/1990", "1990-02-30", ""])
        fun `given a birth date that is not an ISO 8601 date should report it`(
            birthDate: String
        ) {
            val node = ValidationNode(path = "birthDate", value = birthDate).apply {
                convertToLocalDate { before(LocalDate.of(2000, 1, 1)) }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "birthDate", message = "must be a valid ISO 8601 date", code = "convertToLocalDate")
            )
        }

        @Test
        fun `given a date that breaks a LocalDate rule should report it at the same path`() {
            val node = ValidationNode(path = "birthDate", value = "2010-05-20").apply {
                convertToLocalDate { before(LocalDate.of(2000, 1, 1)) }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "birthDate", message = "must be before 2000-01-01", code = "before")
            )
        }
    }

    @Nested
    @DisplayName("When convertToBigInteger is called")
    inner class ConvertToBigInteger {
        @Test
        fun `given a balance in the expected format should check the converted value`() {
            val node = ValidationNode(path = "balance", value = "123456789012345678901234567890").apply {
                convertToBigInteger { positive() }
            }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["1.5", "abc", ""])
        fun `given a balance in another format should report it`(
            balance: String
        ) {
            val node = ValidationNode(path = "balance", value = balance).apply {
                convertToBigInteger { positive() }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "balance", message = "must be a valid integer", code = "convertToBigInteger")
            )
        }
    }

    @Nested
    @DisplayName("When convertToDouble is called")
    inner class ConvertToDouble {
        @Test
        fun `given a weight in the expected format should check the converted value`() {
            val node = ValidationNode(path = "weight", value = "1.5").apply {
                convertToDouble { positive() }
            }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["abc", "1,5", ""])
        fun `given a weight in another format should report it`(
            weight: String
        ) {
            val node = ValidationNode(path = "weight", value = weight).apply {
                convertToDouble { positive() }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "weight", message = "must be a valid number", code = "convertToDouble")
            )
        }
    }

    @Nested
    @DisplayName("When convertToBoolean is called")
    inner class ConvertToBoolean {
        @Test
        fun `given a terms in the expected format should check the converted value`() {
            val node = ValidationNode(path = "terms", value = "true").apply {
                convertToBoolean { isTrue() }
            }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["yes", "TRUE", "1", ""])
        fun `given a terms in another format should report it`(
            terms: String
        ) {
            val node = ValidationNode(path = "terms", value = terms).apply {
                convertToBoolean { isTrue() }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "terms", message = "must be true or false", code = "convertToBoolean")
            )
        }

        @Test
        fun `given a converted terms that breaks a rule should report it at the same path`() {
            val node = ValidationNode(path = "terms", value = "false").apply {
                convertToBoolean { isTrue() }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "terms", message = "must be true", code = "isTrue")
            )
        }
    }

    @Nested
    @DisplayName("When convertToUuid is called")
    inner class ConvertToUuid {
        @Test
        fun `given a orderId in the expected format should check the converted value`() {
            val node = ValidationNode(path = "orderId", value = "123e4567-e89b-12d3-a456-426614174000").apply {
                convertToUuid { notEqualTo(UUID(0, 0)) }
            }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["1-1-1-1-1", "123e4567e89b12d3a456426614174000", "abc", ""])
        fun `given a orderId in another format should report it`(
            orderId: String
        ) {
            val node = ValidationNode(path = "orderId", value = orderId).apply {
                convertToUuid { notEqualTo(UUID(0, 0)) }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "orderId", message = "must be a valid UUID", code = "convertToUuid")
            )
        }
    }

    @Nested
    @DisplayName("When convertToLocalDateTime is called")
    inner class ConvertToLocalDateTime {
        @Test
        fun `given a startsAt in the expected format should check the converted value`() {
            val node = ValidationNode(path = "startsAt", value = "2026-10-25T10:15:30").apply {
                convertToLocalDateTime { before(LocalDateTime.of(2030, 1, 1, 0, 0)) }
            }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["2026-10-25 10:15", "2026-10-25", ""])
        fun `given a startsAt in another format should report it`(
            startsAt: String
        ) {
            val node = ValidationNode(path = "startsAt", value = startsAt).apply {
                convertToLocalDateTime { before(LocalDateTime.of(2030, 1, 1, 0, 0)) }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(
                    path = "startsAt",
                    message = "must be a valid ISO 8601 date and time",
                    code = "convertToLocalDateTime"
                )
            )
        }
    }

    @Nested
    @DisplayName("When convertToLocalTime is called")
    inner class ConvertToLocalTime {
        @Test
        fun `given a opensAt in the expected format should check the converted value`() {
            val node = ValidationNode(path = "opensAt", value = "10:15").apply {
                convertToLocalTime { after(LocalTime.of(8, 0)) }
            }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["25:00", "10h15", ""])
        fun `given a opensAt in another format should report it`(
            opensAt: String
        ) {
            val node = ValidationNode(path = "opensAt", value = opensAt).apply {
                convertToLocalTime { after(LocalTime.of(8, 0)) }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "opensAt", message = "must be a valid ISO 8601 time", code = "convertToLocalTime")
            )
        }

        @Test
        fun `given a converted opensAt that breaks a rule should report it at the same path`() {
            val node = ValidationNode(path = "opensAt", value = "07:30").apply {
                convertToLocalTime { after(LocalTime.of(8, 0)) }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "opensAt", message = "must be after 08:00", code = "after")
            )
        }
    }

    @Nested
    @DisplayName("When convertToOffsetDateTime is called")
    inner class ConvertToOffsetDateTime {
        @Test
        fun `given a paidAt in the expected format should check the converted value`() {
            val node = ValidationNode(path = "paidAt", value = "2026-10-25T10:15:30-03:00").apply {
                convertToOffsetDateTime { before(OffsetDateTime.parse("2030-01-01T00:00Z")) }
            }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["2026-10-25T10:15:30", "2026-10-25", ""])
        fun `given a paidAt in another format should report it`(
            paidAt: String
        ) {
            val node = ValidationNode(path = "paidAt", value = paidAt).apply {
                convertToOffsetDateTime { before(OffsetDateTime.parse("2030-01-01T00:00Z")) }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(
                    path = "paidAt",
                    message = "must be a valid ISO 8601 date and time with offset",
                    code = "convertToOffsetDateTime"
                )
            )
        }
    }

    @Nested
    @DisplayName("When convertToInstant is called")
    inner class ConvertToInstant {
        @Test
        fun `given a createdAt in the expected format should check the converted value`() {
            val node = ValidationNode(path = "createdAt", value = "2026-10-25T13:15:30Z").apply {
                convertToInstant { before(Instant.parse("2030-01-01T00:00:00Z")) }
            }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["2026-10-25T13:15:30", "1761398130", ""])
        fun `given a createdAt in another format should report it`(
            createdAt: String
        ) {
            val node = ValidationNode(path = "createdAt", value = createdAt).apply {
                convertToInstant { before(Instant.parse("2030-01-01T00:00:00Z")) }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "createdAt", message = "must be a valid ISO 8601 instant", code = "convertToInstant")
            )
        }
    }

    @Nested
    @DisplayName("When convertToYearMonth is called")
    inner class ConvertToYearMonth {
        @Test
        fun `given a cardExpiry in the expected format should check the converted value`() {
            val node = ValidationNode(path = "cardExpiry", value = "2026-10").apply {
                convertToYearMonth { after(YearMonth.of(2020, 1)) }
            }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["10/2026", "2026-13", ""])
        fun `given a cardExpiry in another format should report it`(
            cardExpiry: String
        ) {
            val node = ValidationNode(path = "cardExpiry", value = cardExpiry).apply {
                convertToYearMonth { after(YearMonth.of(2020, 1)) }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(
                    path = "cardExpiry",
                    message = "must be a valid ISO 8601 year and month",
                    code = "convertToYearMonth"
                )
            )
        }
    }

    @Nested
    @DisplayName("When convertToDuration is called")
    inner class ConvertToDuration {
        @Test
        fun `given a timeout in the expected format should check the converted value`() {
            val node = ValidationNode(path = "timeout", value = "PT15M").apply {
                convertToDuration { max(Duration.ofHours(1)) }
            }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["15 minutes", "15m", ""])
        fun `given a timeout in another format should report it`(
            timeout: String
        ) {
            val node = ValidationNode(path = "timeout", value = timeout).apply {
                convertToDuration { max(Duration.ofHours(1)) }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "timeout", message = "must be a valid ISO 8601 duration", code = "convertToDuration")
            )
        }

        @Test
        fun `given a converted timeout that breaks a rule should report it at the same path`() {
            val node = ValidationNode(path = "timeout", value = "PT2H").apply {
                convertToDuration { max(Duration.ofHours(1)) }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "timeout", message = "must be at most PT1H", code = "max")
            )
        }
    }

    @Nested
    @DisplayName("When convertToEnum is called")
    inner class ConvertToEnum {
        @Test
        fun `given the name of a payment method should check it as the enum constant`() {
            val node = ValidationNode(path = "method", value = "PIX").apply {
                convertToEnum<PaymentMethod> { notEqualTo(PaymentMethod.BOLETO) }
            }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given the name in another case and ignoreCase should accept it`() {
            val node = ValidationNode(path = "method", value = "pix").apply {
                convertToEnum<PaymentMethod>(ignoreCase = true) { notEqualTo(PaymentMethod.BOLETO) }
            }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["pix", "DOC", ""])
        fun `given a text that is not the name of a payment method should report it with the names`(
            method: String
        ) {
            val node = ValidationNode(path = "method", value = method).apply {
                convertToEnum<PaymentMethod> { notEqualTo(PaymentMethod.BOLETO) }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "method", message = "must be one of PIX, TED, BOLETO", code = "convertToEnum")
            )
        }

        @Test
        fun `given a converted payment method that breaks a rule should report it at the same path`() {
            val node = ValidationNode(path = "method", value = "BOLETO").apply {
                convertToEnum<PaymentMethod> { notEqualTo(PaymentMethod.BOLETO) }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "method", message = "must not be equal to Boleto", code = "notEqualTo")
            )
        }

        @Test
        fun `given a custom message with the values placeholder should replace it`() {
            val node = ValidationNode(path = "method", value = "DOC").apply {
                convertToEnum<PaymentMethod>(message = "use {values}") { notEqualTo(PaymentMethod.BOLETO) }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "method", message = "use PIX, TED, BOLETO", code = "convertToEnum")
            )
        }
    }
}
