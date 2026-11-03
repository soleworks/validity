package io.github.soleworks.validity

import io.github.soleworks.validity.constraints.digits
import io.github.soleworks.validity.samples.Booking
import io.github.soleworks.validity.samples.Contact
import io.github.soleworks.validity.samples.Customer
import io.github.soleworks.validity.samples.Subscription
import io.github.soleworks.validity.samples.Threads
import io.github.soleworks.validity.samples.docs.Account
import io.github.soleworks.validity.samples.docs.Address
import io.github.soleworks.validity.samples.docs.Cart
import io.github.soleworks.validity.samples.docs.CartItem
import io.github.soleworks.validity.samples.docs.Client
import io.github.soleworks.validity.samples.docs.Delivery
import io.github.soleworks.validity.samples.docs.Newsletter
import io.github.soleworks.validity.samples.docs.Order
import io.github.soleworks.validity.samples.docs.Product
import io.github.soleworks.validity.samples.docs.SearchQuery
import io.github.soleworks.validity.samples.docs.Seller
import io.github.soleworks.validity.samples.docs.ShippingAddress
import io.github.soleworks.validity.samples.docs.Stay
import io.github.soleworks.validity.samples.docs.Transfer
import io.github.soleworks.validity.samples.docs.Trial
import io.github.soleworks.validity.samples.docs.Voucher
import io.github.soleworks.validity.samples.docs.Withdrawal
import io.github.soleworks.validity.samples.readme.Destination
import io.github.soleworks.validity.samples.readme.Payment
import io.github.soleworks.validity.samples.readme.Registration
import io.github.soleworks.validity.samples.readme.User
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.matchers.shouldBe
import java.time.Clock
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset.UTC
import kotlinx.coroutines.runBlocking
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

            val result = runBlocking { user.validate() }

            result.isValid shouldBe true
        }

        @Test
        fun `given a Validity with a clock in 2000 should read it in a rule that resumes on another thread`() {
            val validity = Validity { clock = Clock.fixed(Instant.parse("2000-06-15T12:00:00Z"), UTC) }
            val booking = Booking(date = LocalDate.of(2010, 1, 1))

            val result = runBlocking(Threads.caller) { booking.validate(validity) }

            result.isValid shouldBe true
        }

        @Test
        fun `given a Validity with a clock in 2020 should report a date of 2010 read on another thread`() {
            val validity = Validity { clock = Clock.fixed(Instant.parse("2020-06-15T12:00:00Z"), UTC) }
            val booking = Booking(date = LocalDate.of(2010, 1, 1))

            val result = runBlocking(Threads.caller) { booking.validate(validity) }

            result.violations shouldBe listOf(
                Violation(path = "date", message = "must not be in the past", code = "")
            )
        }
    }

    @Nested
    @DisplayName("When validateBlocking is called")
    inner class ValidateBlocking {
        @Test
        fun `given a valid user should be valid`() {
            val user = User(name = "Ana", email = "ana@mail.com", age = null)

            val result = user.validateBlocking()

            result.isValid shouldBe true
        }

        @Test
        fun `given a SEPA payment with a bad amount and currency should report every violation at once`() {
            val payment = Payment(method = "SEPA", amount = "-9.999".toBigDecimal(), currency = "EURO")

            val violations = payment.validateBlocking().violations

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

            val violations = user.validateBlocking().violations

            violations shouldBe listOf(
                Violation(path = "name", message = "must have at least 2 characters", code = "minLength"),
                Violation(path = "email", message = "must be a valid email", code = "email"),
                Violation(path = "age", message = "must be at least 18", code = "min")
            )
        }

        @Test
        fun `given a registration with an invalid email should report only the format`() {
            val registration = Registration(email = "ana@")

            val violations = registration.validateBlocking().violations

            violations shouldBe listOf(
                Violation(path = "email", message = "must be a valid email", code = "email")
            )
        }

        @Test
        fun `given a registration with a taken email should report the lookup`() {
            val registration = Registration(email = "ana@mail.com")

            val violations = registration.validateBlocking().violations

            violations shouldBe listOf(
                Violation(path = "email", message = "is already registered", code = "registered")
            )
        }

        @Test
        fun `given a Validity with a clock in 2000 should treat 2010 as the future`() {
            val validity = Validity { clock = Clock.fixed(Instant.parse("2000-06-15T12:00:00Z"), UTC) }
            val subscription = Subscription(startsOn = LocalDate.of(2010, 1, 1))

            val result = subscription.validateBlocking(validity)

            result.isValid shouldBe true
        }

        @Test
        fun `given a Validity with a clock in 2020 should report 2010 as not in the future`() {
            val validity = Validity { clock = Clock.fixed(Instant.parse("2020-06-15T12:00:00Z"), UTC) }
            val subscription = Subscription(startsOn = LocalDate.of(2010, 1, 1))

            val result = subscription.validateBlocking(validity)

            result.violations shouldBe listOf(
                Violation(path = "startsOn", message = "must be in the future", code = "future")
            )
        }

        @Test
        fun `given a Validity with a clock in 2000 should read it in a rule that runs on another thread`() {
            val validity = Validity { clock = Clock.fixed(Instant.parse("2000-06-15T12:00:00Z"), UTC) }
            val booking = Booking(date = LocalDate.of(2010, 1, 1))

            val result = booking.validateBlocking(validity)

            result.isValid shouldBe true
        }

        @Test
        fun `given a Validity with messages should report them in nested objects too`() {
            val validity = Validity { messages { minLength = "deve ter pelo menos {min} caracteres" } }
            val customer = Customer(name = "A", contact = Contact(phone = "123"))

            val result = customer.validateBlocking(validity)

            result.violations shouldBe listOf(
                Violation(path = "name", message = "deve ter pelo menos 2 caracteres", code = "minLength"),
                Violation(path = "contact.phone", message = "deve ter pelo menos 8 caracteres", code = "minLength")
            )
        }

        @Test
        fun `given an account with a short username and a weak password should report each rule they break`() {
            val account = Account(username = "a!", password = "secret")

            val violations = account.validateBlocking().violations

            violations shouldBe listOf(
                Violation(path = "username", message = "must have between 3 and 20 characters", code = "lengthBetween"),
                Violation(path = "username", message = "must contain only letters or digits", code = "lettersOrDigits"),
                Violation(path = "password", message = "must have at least 8 characters", code = "minLength"),
                Violation(path = "password", message = "must contain a digit", code = "containsDigit")
            )
        }

        @Test
        fun `given a delivery to a blank street in the past should report the address rules under its path`() {
            val validity = Validity { clock = Clock.fixed(Instant.parse("2026-11-02T12:00:00Z"), UTC) }
            val delivery = Delivery(
                address = Address(street = " ", zipCode = "1234", country = "US"),
                scheduledFor = LocalDate.of(2026, 10, 1)
            )

            val violations = delivery.validateBlocking(validity).violations

            violations shouldBe listOf(
                Violation(path = "address.street", message = "must not be blank", code = "notBlank"),
                Violation(
                    path = "address.zipCode",
                    message = "must be a valid postal code for US",
                    code = "postalCode"
                ),
                Violation(path = "scheduledFor", message = "must be in the future", code = "future")
            )
        }

        @Test
        fun `given a company client with no company name and no email should report what null means for each field`() {
            val client = Client(type = "COMPANY", name = "", nickname = "Al")

            val violations = client.validateBlocking().violations

            violations shouldBe listOf(
                Violation(path = "name", message = "must not be blank", code = "notBlank"),
                Violation(path = "nickname", message = "must have at least 3 characters", code = "minLength"),
                Violation(path = "email", message = "we need an email to send the receipt", code = "required"),
                Violation(path = "companyName", message = "is required", code = "required")
            )
        }

        @Test
        fun `given a person client with a company name should report it as forbidden`() {
            val client = Client(type = "PERSON", name = "Ana", companyName = "Acme", email = "ana@mail.com")

            val violations = client.validateBlocking().violations

            violations shouldBe listOf(
                Violation(path = "companyName", message = "must be null", code = "forbidden")
            )
        }

        @Test
        fun `given an order with a blank id and a broken shipping address should report the nested paths`() {
            val order = Order(id = "", shipping = ShippingAddress(street = "", zipCode = "ABC"))

            val violations = order.validateBlocking().violations

            violations shouldBe listOf(
                Violation(path = "id", message = "must not be blank", code = "notBlank"),
                Violation(path = "shipping.street", message = "must not be blank", code = "notBlank"),
                Violation(
                    path = "shipping.zipCode",
                    message = "must be a valid postal code for US",
                    code = "postalCode"
                )
            )
        }

        @Test
        fun `given a cart with a bad item, repeated coupons and a bad price should report each element by its path`() {
            val cart = Cart(
                items = listOf(CartItem(sku = "abc-1", quantity = 0), CartItem(sku = "XYZ-1234", quantity = 3)),
                coupons = listOf("SUMMER24", "SUMMER24", "OFF"),
                prices = mapOf("USD" to "10.00".toBigDecimal(), "EURO" to "-1".toBigDecimal())
            )

            val violations = cart.validateBlocking().violations

            violations shouldBe listOf(
                Violation(path = "items[0].sku", message = """must match [A-Z]{3}-\d{4}""", code = "matches"),
                Violation(path = "items[0].quantity", message = "must be between 1 and 99", code = "between"),
                Violation(path = "coupons", message = "must not contain duplicates", code = "distinct"),
                Violation(path = "coupons[2]", message = "must have exactly 8 characters", code = "length"),
                Violation(
                    path = "prices[EURO]",
                    message = "must be a valid ISO 4217 currency code",
                    code = "currencyCode"
                ),
                Violation(path = "prices[EURO]", message = "must be positive", code = "positive")
            )
        }

        @Test
        fun `given a transfer by IBAN with no BIC and a wrong total should report the rules across fields`() {
            val transfer = Transfer(
                iban = "DE89370400440532013000",
                amount = "100.00".toBigDecimal(),
                fee = "2.50".toBigDecimal(),
                total = "100.00".toBigDecimal()
            )

            val violations = transfer.validateBlocking().violations

            violations shouldBe listOf(
                Violation(
                    path = "",
                    message = "iban, bic must be filled together",
                    code = "allOrNoneOf",
                    metadata = mapOf("fields" to listOf("bic"))
                ),
                Violation(path = "total", message = "must be the amount plus the fee", code = "total")
            )
        }

        @Test
        fun `given a transfer by pix key and by IBAN should report the fields to clear`() {
            val transfer = Transfer(
                pixKey = "ana@mail.com",
                iban = "DE89370400440532013000",
                bic = "DEUTDEFF",
                amount = "100.00".toBigDecimal(),
                fee = "0".toBigDecimal(),
                total = "100".toBigDecimal()
            )

            val violations = transfer.validateBlocking().violations

            violations shouldBe listOf(
                Violation(
                    path = "",
                    message = "exactly one of pixKey, iban must be filled",
                    code = "exactlyOneOf",
                    metadata = mapOf("fields" to listOf("pixKey", "iban"))
                )
            )
        }

        @Test
        fun `given a stay that ends before it starts should report it on the check-out`() {
            val stay = Stay(checkIn = LocalDate.of(2026, 5, 10), checkOut = LocalDate.of(2026, 5, 8))

            val violations = stay.validateBlocking().violations

            violations shouldBe listOf(
                Violation(path = "checkOut", message = "must be after the check-in", code = "stay")
            )
        }

        @Test
        fun `given a product with a bad sku, a short name and a loose stock should report the custom rules`() {
            val product = Product(sku = "abc", name = "TV", stock = 13)

            val violations = product.validateBlocking().violations

            violations shouldBe listOf(
                Violation(path = "sku", message = "must be a SKU such as ABC-1234", code = "sku"),
                Violation(path = "name", message = "has 2 characters, needs 3", code = "minLength"),
                Violation(path = "stock", message = "must be a multiple of the box size", code = "box")
            )
        }

        @Test
        fun `given a seller that breaks every combined rule should report one violation per combination`() {
            val seller = Seller(document = "123", storeCode = "ST-1", username = "ana", password = "ana2024")

            val violations = seller.validateBlocking().violations

            violations shouldBe listOf(
                Violation(path = "document", message = "must be a valid CPF or must be a valid CNPJ", code = "or"),
                Violation(path = "storeCode", message = "must be ST- and 5 more characters", code = "and"),
                Violation(path = "password", message = "must not contain the username", code = "not")
            )
        }

        @Test
        fun `given a voucher code that fits no branch should report the group of the and in parentheses`() {
            val voucher = Voucher(code = "ABC")

            val violations = voucher.validateBlocking().violations

            violations shouldBe listOf(
                Violation(
                    path = "code",
                    message = "must have exactly 8 characters or " +
                        "(must start with X and must have exactly 10 characters)",
                    code = "or"
                )
            )
        }

        @Test
        fun `given a newsletter with an invalid email should report only the format`() {
            val newsletter = Newsletter(email = "ana@")

            val violations = newsletter.validateBlocking().violations

            violations shouldBe listOf(
                Violation(path = "email", message = "must be a valid email", code = "email")
            )
        }

        @Test
        fun `given a newsletter with a subscribed email should report the lookup`() {
            val newsletter = Newsletter(email = "ana@mail.com")

            val violations = newsletter.validateBlocking().violations

            violations shouldBe listOf(
                Violation(path = "email", message = "is already subscribed", code = "subscribed")
            )
        }

        @Test
        fun `given a search query with text that does not convert should report each conversion`() {
            val validity = Validity { clock = Clock.fixed(Instant.parse("2026-11-02T12:00:00Z"), UTC) }
            val query = SearchQuery(page = "0", size = "ten", since = "2999-01-01", sort = "price")

            val violations = query.validateBlocking(validity).violations

            violations shouldBe listOf(
                Violation(path = "page", message = "must be at least 1", code = "min"),
                Violation(path = "size", message = "must be a valid integer", code = "convertToInt"),
                Violation(path = "since", message = "must be in the past or present", code = "pastOrPresent"),
                Violation(
                    path = "sort",
                    message = "must be one of NEWEST, CHEAPEST, BEST_RATED",
                    code = "convertToEnum"
                )
            )
        }

        @Test
        fun `given a withdrawal of zero with a long note should report the metadata of each block`() {
            val withdrawal = Withdrawal(accountId = "acc-1", amount = "0".toBigDecimal(), note = "a".repeat(141))

            val violations = withdrawal.validateBlocking().violations

            violations shouldBe listOf(
                Violation(
                    path = "amount",
                    message = "must be positive",
                    code = "positive",
                    metadata = mapOf("accountId" to "acc-1")
                ),
                Violation(
                    path = "note",
                    message = "must have at most 140 characters",
                    code = "maxLength",
                    metadata = mapOf("accountId" to "acc-1", "severity" to "warning")
                )
            )
        }

        @Test
        fun `given a Validity with a fixed clock should report a trial for a minor that starts next year`() {
            val validity = Validity { clock = Clock.fixed(Instant.parse("2026-11-02T12:00:00Z"), UTC) }
            val trial = Trial(birthDate = LocalDate.of(2010, 1, 1), startsOn = LocalDate.of(2027, 1, 10))

            val violations = trial.validateBlocking(validity).violations

            violations shouldBe listOf(
                Violation(path = "birthDate", message = "must be at least 18 years old", code = "minAge"),
                Violation(path = "startsOn", message = "must be within the next 30d", code = "withinNext"),
                Violation(path = "startsOn", message = "must start this year", code = "thisYear")
            )
        }

        @Test
        fun `given a Validity with messages in Portuguese should report the quick start user in Portuguese`() {
            val portuguese = Validity {
                messages {
                    minLength = "deve ter pelo menos {min} caracteres"
                    email = "deve ser um e-mail válido"
                }
            }
            val user = User(name = "A", email = "ana@", age = null)

            val violations = user.validateBlocking(portuguese).violations

            violations shouldBe listOf(
                Violation(path = "name", message = "deve ter pelo menos 2 caracteres", code = "minLength"),
                Violation(path = "email", message = "deve ser um e-mail válido", code = "email")
            )
        }

        @Test
        fun `given a Validity with the messages of a country should report them inside an or`() {
            val validity = Validity { messages { brazil { cpf = "CPF inválido" } } }
            val seller = Seller(document = "123", storeCode = "ST-12345", username = "ana", password = "s3cret!!")

            val violations = seller.validateBlocking(validity).violations

            violations shouldBe listOf(
                Violation(path = "document", message = "CPF inválido or must be a valid CNPJ", code = "or")
            )
        }
    }

    @Nested
    @DisplayName("When validateOrThrow is called")
    inner class ValidateOrThrow {
        @Test
        fun `given a valid object should not throw`() {
            val customer = Customer()

            shouldNotThrowAny { runBlocking { customer.validateOrThrow() } }
        }

        @Test
        fun `given an invalid object should throw its violations`() {
            val customer = Customer(name = "A")

            val error = shouldThrowExactly<ValidationException> { runBlocking { customer.validateOrThrow() } }

            error.violations shouldBe listOf(
                Violation(path = "name", message = "must have at least 2 characters", code = "minLength")
            )
        }
    }

    @Nested
    @DisplayName("When validateOrThrowBlocking is called")
    inner class ValidateOrThrowBlocking {
        @Test
        fun `given a valid object should not throw`() {
            val customer = Customer()

            shouldNotThrowAny { customer.validateOrThrowBlocking() }
        }

        @Test
        fun `given an invalid object should throw its violations`() {
            val customer = Customer(name = "A")

            val error = shouldThrowExactly<ValidationException> { customer.validateOrThrowBlocking() }

            error.violations shouldBe listOf(
                Violation(path = "name", message = "must have at least 2 characters", code = "minLength")
            )
        }

        @Test
        fun `given a Validity with messages should throw its violations with them`() {
            val validity = Validity { messages { minLength = "deve ter pelo menos {min} caracteres" } }
            val customer = Customer(name = "A")

            val error = shouldThrowExactly<ValidationException> { customer.validateOrThrowBlocking(validity) }

            error.violations shouldBe listOf(
                Violation(path = "name", message = "deve ter pelo menos 2 caracteres", code = "minLength")
            )
        }
    }

    @Nested
    @DisplayName("When validatable is called")
    inner class Validatable {
        @Test
        fun `given a valid destination should be valid`() {
            val destination = Destination(street = "Rua das Flores", zipCode = "01310")

            val result = validatable(destination) { ::zipCode required { digits() } }.validateBlocking()

            result.isValid shouldBe true
        }

        @Test
        fun `given a zip code with letters should report it at the property`() {
            val destination = Destination(street = "Rua das Flores", zipCode = "0131A")

            val result = validatable(destination) { ::zipCode required { digits() } }.validateBlocking()

            result.violations shouldBe listOf(
                Violation(path = "zipCode", message = "must contain only digits", code = "digits")
            )
        }
    }
}
