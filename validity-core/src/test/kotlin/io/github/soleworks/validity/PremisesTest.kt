package io.github.soleworks.validity

import io.github.soleworks.validity.constraints.brazil.cpf
import io.github.soleworks.validity.constraints.min
import io.github.soleworks.validity.samples.BankAccount
import io.github.soleworks.validity.samples.Customer
import io.github.soleworks.validity.samples.Login
import io.github.soleworks.validity.samples.Recipient
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class PremisesTest {
    @Nested
    @DisplayName("When required is called")
    inner class Required {
        @Test
        fun `given a customer without a name should report only that it is required`() {
            val customer = Customer(name = null)

            customer.validate().violations shouldBe listOf(Violation("name", "is required", "required"))
        }

        @Test
        fun `given a customer with a short name should run the rules of the name`() {
            val customer = Customer(name = "A")

            customer.validate().violations shouldBe listOf(Violation("name", "must have at least 2 characters", "minLength"))
        }

        @Test
        fun `given a custom message should report it when the email is missing`() {
            val customer = Customer(email = null)

            customer.validate().violations shouldBe listOf(Violation("email", "email is mandatory", "required"))
        }
    }

    @Nested
    @DisplayName("When ifPresent is called")
    inner class IfPresent {
        @Test
        fun `given a customer without a nickname should not validate it`() {
            val customer = Customer(nickname = null)

            customer.validate().violations shouldBe emptyList()
        }

        @Test
        fun `given a short nickname should run its rules`() {
            val customer = Customer(nickname = "Al")

            customer.validate().violations shouldBe listOf(Violation("nickname", "must have at least 3 characters", "minLength"))
        }
    }

    @Nested
    @DisplayName("When forbidden is called")
    inner class Forbidden {
        @Test
        fun `given a CPF and a company name should report the company name`() {
            val recipient = Recipient(companyName = "Ana LTDA")

            recipient.validate().violations shouldBe listOf(Violation("companyName", "must be null", "forbidden"))
        }

        @Test
        fun `given a CPF without a company name should accept it`() {
            val recipient = Recipient(companyName = null)

            recipient.validate().violations shouldBe emptyList()
        }

        @Test
        fun `given a company name without a CPF should accept it`() {
            val recipient = Recipient(cpf = null, cnpj = "11222333000181", companyName = "Ana LTDA")

            recipient.validate().violations shouldBe emptyList()
        }

        @Test
        fun `given a custom message should report it`() {
            val login = Login(email = null, phone = "11999999999", password = "secret")

            login.validate().violations shouldBe listOf(Violation("password", "password needs an email", "forbidden"))
        }
    }

    @Nested
    @DisplayName("When atLeastOneOf is called")
    inner class AtLeastOneOf {
        @Test
        fun `given neither an email nor a phone should report both fields`() {
            val recipient = Recipient(email = null, phone = null)

            recipient.validate().violations shouldBe listOf(
                Violation("email", "at least one of email, phone is required", "atLeastOneOf"),
                Violation("phone", "at least one of email, phone is required", "atLeastOneOf")
            )
        }

        @Test
        fun `given only a phone should accept it`() {
            val recipient = Recipient(email = null, phone = "11999999999")

            recipient.validate().violations shouldBe emptyList()
        }

        @Test
        fun `given both an email and a phone should accept them`() {
            val recipient = Recipient(email = "ana@mail.com", phone = "11999999999")

            recipient.validate().violations shouldBe emptyList()
        }

        @Test
        fun `given a custom message should replace the fields placeholder`() {
            val login = Login(email = null, phone = null)

            login.validate().violations shouldBe listOf(
                Violation("email", "informe email, phone", "atLeastOneOf"),
                Violation("phone", "informe email, phone", "atLeastOneOf")
            )
        }
    }

    @Nested
    @DisplayName("When atMostOneOf is called")
    inner class AtMostOneOf {
        @Test
        fun `given a CPF and a CNPJ should report only the filled fields`() {
            val recipient = Recipient(cpf = "52998224725", cnpj = "11222333000181")

            recipient.validate().violations shouldBe listOf(
                Violation("cpf", "at most one of cpf, cnpj, passport can be filled", "atMostOneOf"),
                Violation("cnpj", "at most one of cpf, cnpj, passport can be filled", "atMostOneOf")
            )
        }

        @Test
        fun `given only a CNPJ should accept it`() {
            val recipient = Recipient(cpf = null, cnpj = "11222333000181")

            recipient.validate().violations shouldBe emptyList()
        }

        @Test
        fun `given neither a CPF nor a CNPJ should accept it`() {
            val recipient = Recipient(cpf = null, cnpj = null)

            recipient.validate().violations shouldBe emptyList()
        }
    }

    @Nested
    @DisplayName("When exactlyOneOf is called")
    inner class ExactlyOneOf {
        @Test
        fun `given no way to pay should report every field`() {
            val recipient = Recipient(pixKey = null, bankAccount = null, boleto = null)

            recipient.validate().violations shouldBe listOf(
                Violation("pixKey", "exactly one of pixKey, bankAccount, boleto must be filled", "exactlyOneOf"),
                Violation("bankAccount", "exactly one of pixKey, bankAccount, boleto must be filled", "exactlyOneOf"),
                Violation("boleto", "exactly one of pixKey, bankAccount, boleto must be filled", "exactlyOneOf")
            )
        }

        @Test
        fun `given a pix key and a bank account should report only the filled fields`() {
            val recipient = Recipient(pixKey = "ana@mail.com", bankAccount = "0001-12345")

            recipient.validate().violations shouldBe listOf(
                Violation("pixKey", "exactly one of pixKey, bankAccount, boleto must be filled", "exactlyOneOf"),
                Violation("bankAccount", "exactly one of pixKey, bankAccount, boleto must be filled", "exactlyOneOf")
            )
        }

        @Test
        fun `given only a bank account should accept it`() {
            val recipient = Recipient(pixKey = null, bankAccount = "0001-12345")

            recipient.validate().violations shouldBe emptyList()
        }
    }

    @Nested
    @DisplayName("When allOrNoneOf is called")
    inner class AllOrNoneOf {
        @Test
        fun `given only an agency should report the empty fields`() {
            val account = BankAccount(agency = "0001")

            account.validate().violations shouldBe listOf(
                Violation("bank", "bank, agency, account must be filled together", "allOrNoneOf"),
                Violation("account", "bank, agency, account must be filled together", "allOrNoneOf")
            )
        }

        @Test
        fun `given every field should accept them`() {
            val account = BankAccount(bank = "341", agency = "0001", account = "12345-6")

            account.validate().violations shouldBe emptyList()
        }

        @Test
        fun `given no field should accept it`() {
            val account = BankAccount()

            account.validate().violations shouldBe emptyList()
        }
    }

    @Nested
    @DisplayName("When required is called with a value")
    inner class RequiredValue {
        @Test
        fun `given a missing value should report only that it is required`() {
            val cpf: String? = null

            val result = validation { required("cpf", cpf) { cpf() } }.validate()

            result.violations shouldBe listOf(Violation("cpf", "is required", "required"))
        }

        @Test
        fun `given an invalid value should run its rules under the name`() {
            val result = validation { required("cpf", "123") { cpf() } }.validate()

            result.violations shouldBe listOf(Violation("cpf", "must be a valid CPF", "cpf"))
        }

        @Test
        fun `given a valid value should accept it`() {
            val result = validation { required("cpf", "52998224725") { cpf() } }.validate()

            result.violations shouldBe emptyList()
        }

        @Test
        fun `given a custom message should report it when the value is missing`() {
            val cpf: String? = null

            val result = validation { required("cpf", cpf, "informe o CPF") { cpf() } }.validate()

            result.violations shouldBe listOf(Violation("cpf", "informe o CPF", "required"))
        }

        @Test
        fun `given several values should report each one under its own name`() {
            val cpf: String? = null
            val page = 0

            val result = validation {
                required("cpf", cpf) { cpf() }
                required("page", page) { min(1) }
            }.validate()

            result.violations shouldBe listOf(
                Violation("cpf", "is required", "required"),
                Violation("page", "must be at least 1", "min")
            )
        }
    }

    @Nested
    @DisplayName("When ifPresent is called with a value")
    inner class IfPresentValue {
        @Test
        fun `given a missing value should accept it`() {
            val page: Int? = null

            val result = validation { ifPresent("page", page) { min(1) } }.validate()

            result.violations shouldBe emptyList()
        }

        @Test
        fun `given a present value should run its rules under the name`() {
            val result = validation { ifPresent("page", 0) { min(1) } }.validate()

            result.violations shouldBe listOf(Violation("page", "must be at least 1", "min"))
        }
    }
}
