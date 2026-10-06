package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class FinanceConstraintsTest {
    @Nested
    @DisplayName("When creditCard is called")
    inner class CreditCard {
        @ParameterizedTest
        @ValueSource(strings = ["4111111111111111", "5555555555554444"])
        fun `given a valid card number should accept it`(cardNumber: String) {
            val node = ValidationNode("cardNumber", cardNumber).apply { creditCard() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["4111111111111112", "4111 1111 1111 1111", "41111111111"])
        fun `given an invalid card number should report it`(cardNumber: String) {
            val node = ValidationNode("cardNumber", cardNumber).apply { creditCard() }

            node.validate() shouldBe listOf(Violation("cardNumber", "must be a valid credit card number"))
        }
    }

    @Nested
    @DisplayName("When iban is called")
    inner class Iban {
        @ParameterizedTest
        @ValueSource(strings = ["DE89370400440532013000", "GB82WEST12345698765432", "BR1500000000000010932840814P2"])
        fun `given a valid IBAN should accept it`(iban: String) {
            val node = ValidationNode("iban", iban).apply { iban() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(
            strings = [
                "DE89370400440532013001",
                "DE8937040044053201300",
                "XX89370400440532013000",
                "de89370400440532013000"
            ]
        )
        fun `given an invalid IBAN should report it`(iban: String) {
            val node = ValidationNode("iban", iban).apply { iban() }

            node.validate() shouldBe listOf(Violation("iban", "must be a valid IBAN"))
        }
    }

    @Nested
    @DisplayName("When bic is called")
    inner class Bic {
        @ParameterizedTest
        @ValueSource(strings = ["DEUTDEFF", "DEUTDEFF500", "NEDSZAJJXXX"])
        fun `given a valid BIC should accept it`(bic: String) {
            val node = ValidationNode("bic", bic).apply { bic() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["DEUTXXFF", "DEUTDEF", "deutdeff"])
        fun `given an invalid BIC should report it`(bic: String) {
            val node = ValidationNode("bic", bic).apply { bic() }

            node.validate() shouldBe listOf(Violation("bic", "must be a valid BIC"))
        }
    }

    @Nested
    @DisplayName("When isin is called")
    inner class Isin {
        @ParameterizedTest
        @ValueSource(strings = ["US0378331005", "BRPETRACNPR6"])
        fun `given a valid ISIN should accept it`(isin: String) {
            val node = ValidationNode("isin", isin).apply { isin() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["US0378331006", "US037833100"])
        fun `given an invalid ISIN should report it`(isin: String) {
            val node = ValidationNode("isin", isin).apply { isin() }

            node.validate() shouldBe listOf(Violation("isin", "must be a valid ISIN"))
        }
    }

    @Nested
    @DisplayName("When currencyCode is called")
    inner class CurrencyCode {
        @ParameterizedTest
        @ValueSource(strings = ["BRL", "USD", "EUR"])
        fun `given a valid currency code should accept it`(currency: String) {
            val node = ValidationNode("currency", currency).apply { currencyCode() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["XYZ", "brl", "BR"])
        fun `given an invalid currency code should report it`(currency: String) {
            val node = ValidationNode("currency", currency).apply { currencyCode() }

            node.validate() shouldBe listOf(Violation("currency", "must be a valid ISO 4217 currency code"))
        }
    }

    @Nested
    @DisplayName("When bitcoinAddress is called")
    inner class BitcoinAddress {
        @ParameterizedTest
        @ValueSource(
            strings = [
                "1BoatSLRHtKNngkdXEeobR76b53LETtpyT",
                "3J98t1WpEZ73CNmQviecrnyiWrnqRhWNLy",
                "bc1qar0srrr7xfkvy5l643lydnw9re59gtzzwf5mdq"
            ]
        )
        fun `given a valid Bitcoin address should accept it`(wallet: String) {
            val node = ValidationNode("wallet", wallet).apply { bitcoinAddress() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["0BoatSLRHtKNngkdXEeobR76b53LETtpyT", "1Boat"])
        fun `given an invalid Bitcoin address should report it`(wallet: String) {
            val node = ValidationNode("wallet", wallet).apply { bitcoinAddress() }

            node.validate() shouldBe listOf(Violation("wallet", "must be a valid Bitcoin address"))
        }
    }

    @Nested
    @DisplayName("When ethereumAddress is called")
    inner class EthereumAddress {
        @ParameterizedTest
        @ValueSource(strings = ["0x52908400098527886E0F7030069857D2E4169EE7"])
        fun `given a valid Ethereum address should accept it`(wallet: String) {
            val node = ValidationNode("wallet", wallet).apply { ethereumAddress() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(
            strings = [
                "52908400098527886E0F7030069857D2E4169EE7",
                "0x52908400098527886E0F7030069857D2E4169EE"
            ]
        )
        fun `given an invalid Ethereum address should report it`(wallet: String) {
            val node = ValidationNode("wallet", wallet).apply { ethereumAddress() }

            node.validate() shouldBe listOf(Violation("wallet", "must be a valid Ethereum address"))
        }
    }
}
