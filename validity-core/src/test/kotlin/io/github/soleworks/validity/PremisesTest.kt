package io.github.soleworks.validity

import io.github.soleworks.validity.constraints.brazil.cpf
import io.github.soleworks.validity.constraints.min
import io.github.soleworks.validity.samples.BankAccount
import io.github.soleworks.validity.samples.Customer
import io.github.soleworks.validity.samples.Login
import io.github.soleworks.validity.samples.Recipient
import io.github.soleworks.validity.samples.Shipment
import io.github.soleworks.validity.samples.Signup
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class PremisesTest {
    @Nested
    @DisplayName("When required is called")
    inner class Required {
        @Test
        fun `given a valid value should accept it`() {
            val rules = validation { required(path = "cpf", value = "52998224725") { cpf() } }

            val result = rules.validate()

            result.violations shouldBe emptyList()
        }

        @Test
        fun `given a customer without a name should report only that it is required`() {
            val customer = Customer(name = null)

            val result = customer.validate()

            result.violations shouldBe listOf(
                Violation(path = "name", message = "is required", code = "required")
            )
        }

        @Test
        fun `given a customer with a short name should run the rules of the name`() {
            val customer = Customer(name = "A")

            val result = customer.validate()

            result.violations shouldBe listOf(
                Violation(path = "name", message = "must have at least 2 characters", code = "minLength")
            )
        }

        @Test
        fun `given a custom message should report it when the email is missing`() {
            val customer = Customer(email = null)

            val result = customer.validate()

            result.violations shouldBe listOf(
                Violation(path = "email", message = "email is mandatory", code = "required")
            )
        }

        @Test
        fun `given a missing value should report only that it is required`() {
            val cpf: String? = null
            val rules = validation { required(path = "cpf", value = cpf) { cpf() } }

            val result = rules.validate()

            result.violations shouldBe listOf(
                Violation(path = "cpf", message = "is required", code = "required")
            )
        }

        @Test
        fun `given an invalid value should run its rules under the path`() {
            val rules = validation { required(path = "cpf", value = "123") { cpf() } }

            val result = rules.validate()

            result.violations shouldBe listOf(
                Violation(path = "cpf", message = "must be a valid CPF", code = "cpf")
            )
        }

        @Test
        fun `given a custom message should report it when the value is missing`() {
            val cpf: String? = null
            val rules = validation { required(path = "cpf", value = cpf, message = "informe o CPF") { cpf() } }

            val result = rules.validate()

            result.violations shouldBe listOf(
                Violation(path = "cpf", message = "informe o CPF", code = "required")
            )
        }

        @Test
        fun `given several values should report each one under its own path`() {
            val cpf: String? = null
            val page = 0
            val rules = validation {
                required(path = "cpf", value = cpf) { cpf() }
                required(path = "page", value = page) { min(1) }
            }

            val result = rules.validate()

            result.violations shouldBe listOf(
                Violation(path = "cpf", message = "is required", code = "required"),
                Violation(path = "page", message = "must be at least 1", code = "min")
            )
        }
    }

    @Nested
    @DisplayName("When ifPresent is called")
    inner class IfPresent {
        @Test
        fun `given a missing value should accept it`() {
            val page: Int? = null
            val rules = validation { ifPresent(path = "page", value = page) { min(1) } }

            val result = rules.validate()

            result.violations shouldBe emptyList()
        }

        @Test
        fun `given a short nickname should run its rules`() {
            val customer = Customer(nickname = "Al")

            val result = customer.validate()

            result.violations shouldBe listOf(
                Violation(path = "nickname", message = "must have at least 3 characters", code = "minLength")
            )
        }

        @Test
        fun `given a present value should run its rules under the path`() {
            val rules = validation { ifPresent(path = "page", value = 0) { min(1) } }

            val result = rules.validate()

            result.violations shouldBe listOf(
                Violation(path = "page", message = "must be at least 1", code = "min")
            )
        }

        @Test
        fun `given a customer without a nickname should not validate it`() {
            val customer = Customer(nickname = null)

            val result = customer.validate()

            result.violations shouldBe emptyList()
        }
    }

    @Nested
    @DisplayName("When forbidden is called")
    inner class Forbidden {
        @Test
        fun `given a CPF without a company name should accept it`() {
            val recipient = Recipient(companyName = null)

            val result = recipient.validate()

            result.violations shouldBe emptyList()
        }

        @Test
        fun `given a CPF and a company name should report the company name`() {
            val recipient = Recipient(companyName = "Ana LTDA")

            val result = recipient.validate()

            result.violations shouldBe listOf(
                Violation(path = "companyName", message = "must be null", code = "forbidden")
            )
        }

        @Test
        fun `given a custom message should report it`() {
            val login = Login(email = null, phone = "11999999999", password = "secret")

            val result = login.validate()

            result.violations shouldBe listOf(
                Violation(path = "password", message = "password needs an email", code = "forbidden")
            )
        }
    }

    @Nested
    @DisplayName("When atLeastOneOf is called")
    inner class AtLeastOneOf {
        @Test
        fun `given only a phone should accept it`() {
            val recipient = Recipient(email = null, phone = "11999999999")

            val result = recipient.validate()

            result.violations shouldBe emptyList()
        }

        @Test
        fun `given both an email and a phone should accept them`() {
            val recipient = Recipient(email = "ana@mail.com", phone = "11999999999")

            val result = recipient.validate()

            result.violations shouldBe emptyList()
        }

        @Test
        fun `given neither an email nor a phone should report it once with both fields`() {
            val recipient = Recipient(email = null, phone = null)

            val result = recipient.validate()

            result.violations shouldBe listOf(
                Violation(
                    path = "",
                    message = "at least one of email, phone is required",
                    code = "atLeastOneOf",
                    metadata = mapOf("fields" to listOf("email", "phone"))
                )
            )
        }

        @Test
        fun `given a custom message should replace the fields placeholder`() {
            val login = Login(email = null, phone = null)

            val result = login.validate()

            result.violations shouldBe listOf(
                Violation(
                    path = "",
                    message = "informe email, phone",
                    code = "atLeastOneOf",
                    metadata = mapOf("fields" to listOf("email", "phone"))
                )
            )
        }
    }

    @Nested
    @DisplayName("When atMostOneOf is called")
    inner class AtMostOneOf {
        @Test
        fun `given only a CNPJ should accept it`() {
            val recipient = Recipient(cpf = null, cnpj = "11222333000181")

            val result = recipient.validate()

            result.violations shouldBe emptyList()
        }

        @Test
        fun `given neither a CPF nor a CNPJ should accept it`() {
            val recipient = Recipient(cpf = null, cnpj = null)

            val result = recipient.validate()

            result.violations shouldBe emptyList()
        }

        @Test
        fun `given a CPF and a CNPJ should report it once with the filled fields`() {
            val recipient = Recipient(cpf = "52998224725", cnpj = "11222333000181")

            val result = recipient.validate()

            result.violations shouldBe listOf(
                Violation(
                    path = "",
                    message = "at most one of cpf, cnpj, passport can be filled",
                    code = "atMostOneOf",
                    metadata = mapOf("fields" to listOf("cpf", "cnpj"))
                )
            )
        }
    }

    @Nested
    @DisplayName("When exactlyOneOf is called")
    inner class ExactlyOneOf {
        @Test
        fun `given only a bank account should accept it`() {
            val recipient = Recipient(pixKey = null, bankAccount = "0001-12345")

            val result = recipient.validate()

            result.violations shouldBe emptyList()
        }

        @Test
        fun `given no way to pay should report it at the path with every field`() {
            val recipient = Recipient(pixKey = null, bankAccount = null, boleto = null)

            val result = recipient.validate()

            result.violations shouldBe listOf(
                Violation(
                    path = "payment",
                    message = "exactly one of pixKey, bankAccount, boleto must be filled",
                    code = "exactlyOneOf",
                    metadata = mapOf("fields" to listOf("pixKey", "bankAccount", "boleto"))
                )
            )
        }

        @Test
        fun `given a pix key and a bank account should report it at the path with the filled fields`() {
            val recipient = Recipient(pixKey = "ana@mail.com", bankAccount = "0001-12345")

            val result = recipient.validate()

            result.violations shouldBe listOf(
                Violation(
                    path = "payment",
                    message = "exactly one of pixKey, bankAccount, boleto must be filled",
                    code = "exactlyOneOf",
                    metadata = mapOf("fields" to listOf("pixKey", "bankAccount"))
                )
            )
        }

        @Test
        fun `given the same property twice should count it once`() {
            val shipment = Shipment(trackingCode = "BR123", pickupCode = null)

            val result = shipment.validate()

            result.violations shouldBe emptyList()
        }

        @Test
        fun `given no properties should fail when the rules are declared`() {
            val error = shouldThrow<IllegalArgumentException> { validation { exactlyOneOf() } }

            error.message shouldBe "exactlyOneOf needs at least one property"
        }
    }

    @Nested
    @DisplayName("When allOrNoneOf is called")
    inner class AllOrNoneOf {
        @Test
        fun `given every field should accept them`() {
            val account = BankAccount(bank = "341", agency = "0001", account = "12345-6")

            val result = account.validate()

            result.violations shouldBe emptyList()
        }

        @Test
        fun `given no field should accept it`() {
            val account = BankAccount()

            val result = account.validate()

            result.violations shouldBe emptyList()
        }

        @Test
        fun `given only an agency should report it once with the empty fields`() {
            val account = BankAccount(agency = "0001")

            val result = account.validate()

            result.violations shouldBe listOf(
                Violation(
                    path = "",
                    message = "bank, agency, account must be filled together",
                    code = "allOrNoneOf",
                    metadata = mapOf("fields" to listOf("bank", "account"))
                )
            )
        }
    }

    @Nested
    @DisplayName("When atLeastOf is called")
    inner class AtLeastOf {
        @Test
        fun `given two channels should accept them`() {
            val signup = Signup()

            val result = signup.validate()

            result.violations shouldBe emptyList()
        }

        @Test
        fun `given only an email should report it at the path with the empty fields`() {
            val signup = Signup(phone = null)

            val result = signup.validate()

            result.violations shouldBe listOf(
                Violation(
                    path = "contactInformation",
                    message = "at least 2 of email, phone, whatsapp must be filled",
                    code = "atLeastOf",
                    metadata = mapOf("fields" to listOf("phone", "whatsapp"))
                )
            )
        }

        @Test
        fun `given a count larger than the properties should fail when the rules are declared`() {
            val error = shouldThrow<IllegalArgumentException> {
                Signup().run { validation { atLeastOf(4, ::email, ::phone, ::whatsapp) } }
            }

            error.message shouldBe "atLeastOf needs a count between 0 and the number of properties"
        }
    }

    @Nested
    @DisplayName("When atMostOf is called")
    inner class AtMostOf {
        @Test
        fun `given two discounts should accept them`() {
            val signup = Signup(coupon = "PROMO10", giftCard = "GIFT-1")

            val result = signup.validate()

            result.violations shouldBe emptyList()
        }

        @Test
        fun `given three discounts should report it once with the filled fields`() {
            val signup = Signup(coupon = "PROMO10", giftCard = "GIFT-1", cashback = "CB-5")

            val result = signup.validate()

            result.violations shouldBe listOf(
                Violation(
                    path = "",
                    message = "at most 2 of coupon, giftCard, cashback can be filled",
                    code = "atMostOf",
                    metadata = mapOf("fields" to listOf("coupon", "giftCard", "cashback"))
                )
            )
        }
    }

    @Nested
    @DisplayName("When exactlyOf is called")
    inner class ExactlyOf {
        @Test
        fun `given two references should accept them`() {
            val signup = Signup()

            val result = signup.validate()

            result.violations shouldBe emptyList()
        }

        @Test
        fun `given one reference should report it once with the empty fields`() {
            val signup = Signup(secondReference = null)

            val result = signup.validate()

            result.violations shouldBe listOf(
                Violation(
                    path = "",
                    message = "exactly 2 of firstReference, secondReference, thirdReference must be filled",
                    code = "exactlyOf",
                    metadata = mapOf("fields" to listOf("secondReference", "thirdReference"))
                )
            )
        }

        @Test
        fun `given three references should report it once with the filled fields`() {
            val signup = Signup(thirdReference = "Duda")

            val result = signup.validate()

            result.violations shouldBe listOf(
                Violation(
                    path = "",
                    message = "exactly 2 of firstReference, secondReference, thirdReference must be filled",
                    code = "exactlyOf",
                    metadata = mapOf("fields" to listOf("firstReference", "secondReference", "thirdReference"))
                )
            )
        }
    }
}
