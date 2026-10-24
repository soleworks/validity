package io.github.soleworks.validity

import io.github.soleworks.validity.samples.Customer
import io.github.soleworks.validity.samples.readme.Account
import io.github.soleworks.validity.samples.readme.Address
import io.github.soleworks.validity.samples.readme.Batch
import io.github.soleworks.validity.samples.readme.Credentials
import io.github.soleworks.validity.samples.readme.Employment
import io.github.soleworks.validity.samples.readme.Item
import io.github.soleworks.validity.samples.readme.Operation
import io.github.soleworks.validity.samples.readme.Order
import io.github.soleworks.validity.samples.readme.Payment
import io.github.soleworks.validity.samples.readme.Product
import io.github.soleworks.validity.samples.readme.Profile
import io.github.soleworks.validity.samples.readme.Recipient
import io.github.soleworks.validity.samples.readme.Signup
import io.github.soleworks.validity.samples.readme.User
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import java.math.BigDecimal
import java.time.LocalDate
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ValidatableTest {
    @Nested
    @DisplayName("When validate is called")
    inner class Validate {
        @Test
        fun `given a valid user should be valid`() {
            val user = User(name = "Ana", email = "ana@mail.com", age = null)

            val result = user.validate()

            result.isValid shouldBe true
        }

        @Test
        fun `given a SEPA payment with a bad amount and currency should report every violation at once`() {
            val payment = Payment(method = "SEPA", amount = "-9.999".toBigDecimal(), currency = "EURO")

            val violations = payment.validate().violations

            violations shouldBe listOf(
                Violation(path = "amount", message = "must be positive", code = "positive"),
                Violation(path = "amount", message = "must have at most 2 decimal places", code = "maxDecimalPlaces"),
                Violation(path = "currency", message = "must be a valid ISO 4217 currency code", code = "currencyCode"),
                Violation(path = "iban", message = "is required", code = "required")
            )
        }

        @Test
        fun `given a user with a short name, an invalid email and an age under 18 should report the three of them`() {
            val user = User(name = "A", email = "ana@", age = 16)

            val violations = user.validate().violations

            violations shouldBe listOf(
                Violation(path = "name", message = "must have at least 2 characters", code = "minLength"),
                Violation(path = "email", message = "must be a valid email", code = "email"),
                Violation(path = "age", message = "must be at least 18", code = "min")
            )
        }

        @Test
        fun `given a personal profile without a name and with a company should report each premise`() {
            val profile = Profile(name = null, nickname = "Al", isPersonal = true, companyName = "ACME")

            val violations = profile.validate().violations

            violations shouldBe listOf(
                Violation(path = "name", message = "tell us your name", code = "required"),
                Violation(path = "nickname", message = "must have at least 3 characters", code = "minLength"),
                Violation(path = "companyName", message = "must be null", code = "forbidden")
            )
        }

        @Test
        fun `given a recipient with no contact and two payment means should report the fields of both rules`() {
            val recipient = Recipient(
                email = null,
                phone = null,
                iban = "DE89370400440532013000",
                cardNumber = "4111111111111111"
            )

            val violations = recipient.validate().violations

            violations shouldBe listOf(
                Violation(path = "email", message = "at least one of email, phone is required", code = "atLeastOneOf"),
                Violation(path = "phone", message = "at least one of email, phone is required", code = "atLeastOneOf"),
                Violation(
                    path = "iban",
                    message = "exactly one of iban, cardNumber must be filled",
                    code = "exactlyOneOf"
                ),
                Violation(
                    path = "cardNumber",
                    message = "exactly one of iban, cardNumber must be filled",
                    code = "exactlyOneOf"
                )
            )
        }

        @Test
        fun `given an employment that ends before it starts should report the end date`() {
            val employment = Employment(hiredOn = LocalDate.of(2024, 3, 1), leftOn = LocalDate.of(2023, 12, 31))

            val violations = employment.validate().violations

            violations shouldBe listOf(
                Violation(path = "leftOn", message = "must be after 2024-03-01", code = "after")
            )
        }

        @Test
        fun `given a password that contains the username should report the rule of the class`() {
            val credentials = Credentials(username = "ana", password = "ana-and-a-long-secret")

            val violations = credentials.validate().violations

            violations shouldBe listOf(
                Violation(path = "password", message = "must not contain the username", code = "username")
            )
        }

        @Test
        fun `given a product with a malformed SKU should report the reusable constraint`() {
            val product = Product(sku = "abc-12")

            val violations = product.validate().violations

            violations shouldBe listOf(
                Violation(path = "sku", message = "must look like ABC-1234", code = "sku")
            )
        }

        @Test
        fun `given an invalid tax id and a reserved username should report one violation per operator`() {
            val account = Account(taxId = "123", username = "root")

            val violations = account.validate().violations

            violations shouldBe listOf(
                Violation(path = "taxId", message = "must be a valid CPF or must be a valid CNPJ", code = "or"),
                Violation(path = "username", message = "is not allowed", code = "not")
            )
        }

        @Test
        fun `given an order with invalid nested objects should report each one at its path`() {
            val order = Order(
                shipping = Address(street = "", zipCode = "1234", country = "US"),
                items = listOf(Item(sku = "ABC-1234", quantity = 2), Item(sku = " ", quantity = 0)),
                prices = mapOf("USD" to "9.90".toBigDecimal(), "EURO" to "-1".toBigDecimal())
            )

            val violations = order.validate().violations

            violations shouldBe listOf(
                Violation(path = "shipping.street", message = "must not be blank", code = "notBlank"),
                Violation(
                    path = "shipping.zipCode",
                    message = "must be a valid postal code for US",
                    code = "postalCode"
                ),
                Violation(path = "items[1].sku", message = "must not be blank", code = "notBlank"),
                Violation(path = "items[1].quantity", message = "must be between 1 and 99", code = "between"),
                Violation(
                    path = "prices[EURO]",
                    message = "must be a valid ISO 4217 currency code",
                    code = "currencyCode"
                ),
                Violation(path = "prices[EURO]", message = "must be positive", code = "positive")
            )
        }

        @Test
        fun `given a signup with a short username and password should report the messages of the class`() {
            val signup = Signup(username = "al", password = "hunter2")

            val violations = signup.validate().violations

            violations shouldBe listOf(
                Violation(
                    path = "username",
                    message = "'al' is too short, use at least 3 characters",
                    code = "minLength"
                ),
                Violation(path = "password", message = "password needs 12 characters or more", code = "minLength")
            )
        }

        @Test
        fun `given an operation of a batch should report it with the entries of both blocks`() {
            val batch = Batch(clientId = "c-9", operations = listOf(Operation(id = "op-7", amount = BigDecimal.ZERO)))

            val violations = batch.validate().violations

            violations shouldBe listOf(
                Violation(
                    path = "operations[0].amount",
                    message = "must be positive",
                    code = "positive",
                    metadata = mapOf("clientId" to "c-9", "operationId" to "op-7")
                )
            )
        }
    }

    @Nested
    @DisplayName("When validateOrThrow is called")
    inner class ValidateOrThrow {
        @Test
        fun `given a valid object should not throw`() {
            val customer = Customer()

            shouldNotThrowAny { customer.validateOrThrow() }
        }

        @Test
        fun `given an invalid object should throw its violations`() {
            val customer = Customer(name = "A")

            val error = shouldThrow<ValidationException> { customer.validateOrThrow() }

            error.violations shouldBe listOf(
                Violation(path = "name", message = "must have at least 2 characters", code = "minLength")
            )
        }

        @Test
        fun `given an invalid user should throw an error whose message lists every violation`() {
            val user = User(name = "A", email = "ana@", age = 16)

            val error = shouldThrow<ValidationException> { user.validateOrThrow() }

            error.message shouldBe
                "name: must have at least 2 characters, email: must be a valid email, age: must be at least 18"
        }
    }
}
