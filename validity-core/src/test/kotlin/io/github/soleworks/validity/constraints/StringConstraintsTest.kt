package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.github.soleworks.validity.samples.Customer
import io.github.soleworks.validity.samples.Product
import io.github.soleworks.validity.validate
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class StringConstraintsTest {
    @Nested
    @DisplayName("When minLength is called")
    inner class MinLength {
        @ParameterizedTest
        @ValueSource(strings = ["", "A"])
        fun `given a name shorter than the minimum should report it`(name: String) {
            val customer = Customer(name = name)

            customer.validate().violations shouldBe listOf(Violation("name", "must have at least 2 characters"))
        }

        @ParameterizedTest
        @ValueSource(strings = ["Al", "Ana"])
        fun `given a name with at least the minimum should accept it`(name: String) {
            val customer = Customer(name = name)

            customer.validate().violations shouldBe emptyList()
        }

        @Test
        fun `given a custom message with the minimum placeholder should replace it`() {
            val product = Product(sku = "S1")

            product.validate().violations shouldBe listOf(
                Violation("sku", "must have at least 5 characters, like SKU-1")
            )
        }

        @Test
        fun `given a message function should build it from the value and the minimum`() {
            val product = Product(description = "Shirt")

            product.validate().violations shouldBe listOf(
                Violation("description", "'Shirt' is too short, it needs at least 10 characters")
            )
        }
    }

    @Nested
    @DisplayName("When maxLength is called")
    inner class MaxLength {
        @ParameterizedTest
        @ValueSource(strings = ["Christopher", "Maximiliano Augusto"])
        fun `given a nickname longer than the maximum should report it`(nickname: String) {
            val node = ValidationNode("nickname", nickname).apply { maxLength(10) }

            node.validate() shouldBe listOf(Violation("nickname", "must have at most 10 characters"))
        }

        @ParameterizedTest
        @ValueSource(strings = ["Ana", "Maximilian"])
        fun `given a nickname with at most the maximum should accept it`(nickname: String) {
            val node = ValidationNode("nickname", nickname).apply { maxLength(10) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function should build it from the value and the maximum`() {
            val node = ValidationNode("nickname", "Christopher").apply {
                maxLength(10) { max -> "'$this' passes $max characters" }
            }

            node.validate() shouldBe listOf(Violation("nickname", "'Christopher' passes 10 characters"))
        }
    }

    @Nested
    @DisplayName("When length is called")
    inner class Length {
        @ParameterizedTest
        @ValueSource(strings = ["0131010", "013101000"])
        fun `given a zip code without exactly 8 characters should report it`(zipCode: String) {
            val node = ValidationNode("zipCode", zipCode).apply { length(8) }

            node.validate() shouldBe listOf(Violation("zipCode", "must have exactly 8 characters"))
        }

        @Test
        fun `given a zip code with exactly 8 characters should accept it`() {
            val node = ValidationNode("zipCode", "01310100").apply { length(8) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function should build it from the value and the length`() {
            val node = ValidationNode("zipCode", "0131010").apply {
                length(8) { length -> "'$this' needs $length characters" }
            }

            node.validate() shouldBe listOf(Violation("zipCode", "'0131010' needs 8 characters"))
        }
    }

    @Nested
    @DisplayName("When lengthBetween is called")
    inner class LengthBetween {
        @ParameterizedTest
        @ValueSource(strings = ["an", "ana.maria.da.silva.01"])
        fun `given a username outside the length range should report it`(username: String) {
            val node = ValidationNode("username", username).apply { lengthBetween(3, 20) }

            node.validate() shouldBe listOf(Violation("username", "must have between 3 and 20 characters"))
        }

        @ParameterizedTest
        @ValueSource(strings = ["ana", "ana.maria.da.silva.1"])
        fun `given a username inside the length range should accept it`(username: String) {
            val node = ValidationNode("username", username).apply { lengthBetween(3, 20) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function should build it from the value and the limits`() {
            val node = ValidationNode("username", "an").apply {
                lengthBetween(3, 20) { min, max -> "'$this' needs from $min to $max characters" }
            }

            node.validate() shouldBe listOf(Violation("username", "'an' needs from 3 to 20 characters"))
        }
    }

    @Nested
    @DisplayName("When notEmpty is called")
    inner class NotEmpty {
        @Test
        fun `given an empty description should report it`() {
            val node = ValidationNode("description", "").apply { notEmpty() }

            node.validate() shouldBe listOf(Violation("description", "must not be empty"))
        }

        @ParameterizedTest
        @ValueSource(strings = [" ", "Cotton t-shirt"])
        fun `given a description with any character should accept it`(description: String) {
            val node = ValidationNode("description", description).apply { notEmpty() }

            node.validate() shouldBe emptyList()
        }
    }

    @Nested
    @DisplayName("When notBlank is called")
    inner class NotBlank {
        @ParameterizedTest
        @ValueSource(strings = ["", "   "])
        fun `given a blank name should report it`(name: String) {
            val node = ValidationNode("name", name).apply { notBlank() }

            node.validate() shouldBe listOf(Violation("name", "must not be blank"))
        }

        @ParameterizedTest
        @ValueSource(strings = ["Ana", " Ana "])
        fun `given a name with visible characters should accept it`(name: String) {
            val node = ValidationNode("name", name).apply { notBlank() }

            node.validate() shouldBe emptyList()
        }
    }

    @Nested
    @DisplayName("When matches is called")
    inner class Matches {
        @ParameterizedTest
        @ValueSource(strings = ["usd", "US", "USDT"])
        fun `given a currency that does not match the pattern should report it`(currency: String) {
            val node = ValidationNode("currency", currency).apply { matches(Regex("[A-Z]{3}")) }

            node.validate() shouldBe listOf(Violation("currency", "must match [A-Z]{3}"))
        }

        @Test
        fun `given a currency that matches the pattern should accept it`() {
            val node = ValidationNode("currency", "USD").apply { matches(Regex("[A-Z]{3}")) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function should build it from the value and the pattern`() {
            val node = ValidationNode("currency", "usd").apply {
                matches(Regex("[A-Z]{3}")) { regex -> "'$this' is not like $regex" }
            }

            node.validate() shouldBe listOf(Violation("currency", "'usd' is not like [A-Z]{3}"))
        }
    }

    @Nested
    @DisplayName("When notMatches is called")
    inner class NotMatches {
        @ParameterizedTest
        @ValueSource(strings = ["Ana1", "4na"])
        fun `given a name that matches the forbidden pattern should report it`(name: String) {
            val node = ValidationNode("name", name).apply { notMatches(Regex(".*\\d.*")) }

            node.validate() shouldBe listOf(Violation("name", "must not match .*\\d.*"))
        }

        @Test
        fun `given a name that does not match the forbidden pattern should accept it`() {
            val node = ValidationNode("name", "Ana").apply { notMatches(Regex(".*\\d.*")) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function should build it from the value and the pattern`() {
            val node = ValidationNode("name", "Ana1").apply {
                notMatches(Regex(".*\\d.*")) { regex -> "'$this' matches $regex" }
            }

            node.validate() shouldBe listOf(Violation("name", "'Ana1' matches .*\\d.*"))
        }
    }

    @Nested
    @DisplayName("When contains is called")
    inner class Contains {
        @Test
        fun `given an email without the at sign should report it`() {
            val node = ValidationNode("email", "ana.mail.com").apply { contains("@") }

            node.validate() shouldBe listOf(Violation("email", "must contain @"))
        }

        @Test
        fun `given an email with the at sign should accept it`() {
            val node = ValidationNode("email", "ana@mail.com").apply { contains("@") }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no text to look for should skip the check`() {
            val domain: String? = null

            val node = ValidationNode("email", "ana@mail.com").apply { contains(domain) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function should build it from the value and the text`() {
            val node = ValidationNode("email", "ana.mail.com").apply { contains("@") { text -> "'$this' lacks $text" } }

            node.validate() shouldBe listOf(Violation("email", "'ana.mail.com' lacks @"))
        }
    }

    @Nested
    @DisplayName("When notContains is called")
    inner class NotContains {
        @Test
        fun `given a password with the username should report it`() {
            val username = "ana"

            val node = ValidationNode("password", "ana12345").apply { notContains(username) }

            node.validate() shouldBe listOf(Violation("password", "must not contain ana"))
        }

        @Test
        fun `given a password without the username should accept it`() {
            val username = "ana"

            val node = ValidationNode("password", "s3cret!x").apply { notContains(username) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given an account without a username should skip the check`() {
            val username: String? = null

            val node = ValidationNode("password", "ana12345").apply { notContains(username) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function should build it from the value and the text`() {
            val node = ValidationNode("password", "ana12345").apply {
                notContains("ana") { text -> "the password has the username $text" }
            }

            node.validate() shouldBe listOf(Violation("password", "the password has the username ana"))
        }
    }

    @Nested
    @DisplayName("When startsWith is called")
    inner class StartsWith {
        @Test
        fun `given a foreign IBAN should report it`() {
            val node = ValidationNode("iban", "DE89370400440532013000").apply { startsWith("BR") }

            node.validate() shouldBe listOf(Violation("iban", "must start with BR"))
        }

        @Test
        fun `given a Brazilian IBAN should accept it`() {
            val node = ValidationNode("iban", "BR1500000000000010932840814P2").apply { startsWith("BR") }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no prefix should skip the check`() {
            val countryCode: String? = null

            val node = ValidationNode("iban", "DE89370400440532013000").apply { startsWith(countryCode) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function should build it from the value and the prefix`() {
            val node = ValidationNode("iban", "DE89370400440532013000").apply {
                startsWith("BR") { prefix -> "$this is not from $prefix" }
            }

            node.validate() shouldBe listOf(Violation("iban", "DE89370400440532013000 is not from BR"))
        }
    }

    @Nested
    @DisplayName("When notStartsWith is called")
    inner class NotStartsWith {
        @Test
        fun `given an account number with a leading zero should report it`() {
            val node = ValidationNode("accountNumber", "0123").apply { notStartsWith("0") }

            node.validate() shouldBe listOf(Violation("accountNumber", "must not start with 0"))
        }

        @Test
        fun `given an account number without a leading zero should accept it`() {
            val node = ValidationNode("accountNumber", "1234").apply { notStartsWith("0") }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no prefix should skip the check`() {
            val forbiddenPrefix: String? = null

            val node = ValidationNode("accountNumber", "0123").apply { notStartsWith(forbiddenPrefix) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function should build it from the value and the prefix`() {
            val node = ValidationNode("accountNumber", "0123").apply {
                notStartsWith("0") { prefix -> "$this starts with $prefix" }
            }

            node.validate() shouldBe listOf(Violation("accountNumber", "0123 starts with 0"))
        }
    }

    @Nested
    @DisplayName("When endsWith is called")
    inner class EndsWith {
        @Test
        fun `given a file that is not a PDF should report it`() {
            val node = ValidationNode("fileName", "contract.docx").apply { endsWith(".pdf") }

            node.validate() shouldBe listOf(Violation("fileName", "must end with .pdf"))
        }

        @Test
        fun `given a PDF file should accept it`() {
            val node = ValidationNode("fileName", "contract.pdf").apply { endsWith(".pdf") }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no suffix should skip the check`() {
            val extension: String? = null

            val node = ValidationNode("fileName", "contract.docx").apply { endsWith(extension) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function should build it from the value and the suffix`() {
            val node = ValidationNode("fileName", "contract.docx").apply {
                endsWith(".pdf") { suffix -> "$this is not a $suffix file" }
            }

            node.validate() shouldBe listOf(Violation("fileName", "contract.docx is not a .pdf file"))
        }
    }

    @Nested
    @DisplayName("When notEndsWith is called")
    inner class NotEndsWith {
        @Test
        fun `given an executable file should report it`() {
            val node = ValidationNode("fileName", "setup.exe").apply { notEndsWith(".exe") }

            node.validate() shouldBe listOf(Violation("fileName", "must not end with .exe"))
        }

        @Test
        fun `given a file that is not executable should accept it`() {
            val node = ValidationNode("fileName", "setup.zip").apply { notEndsWith(".exe") }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given no suffix should skip the check`() {
            val forbiddenExtension: String? = null

            val node = ValidationNode("fileName", "setup.exe").apply { notEndsWith(forbiddenExtension) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function should build it from the value and the suffix`() {
            val node = ValidationNode("fileName", "setup.exe").apply {
                notEndsWith(".exe") { suffix -> "$this is a $suffix file" }
            }

            node.validate() shouldBe listOf(Violation("fileName", "setup.exe is a .exe file"))
        }
    }

    @Nested
    @DisplayName("When uppercase is called")
    inner class Uppercase {
        @ParameterizedTest
        @ValueSource(strings = ["usd", "Usd"])
        fun `given a currency with lowercase letters should report it`(currency: String) {
            val node = ValidationNode("currency", currency).apply { uppercase() }

            node.validate() shouldBe listOf(Violation("currency", "must be uppercase"))
        }

        @ParameterizedTest
        @ValueSource(strings = ["USD", "BRL-2"])
        fun `given a currency without lowercase letters should accept it`(currency: String) {
            val node = ValidationNode("currency", currency).apply { uppercase() }

            node.validate() shouldBe emptyList()
        }
    }

    @Nested
    @DisplayName("When lowercase is called")
    inner class Lowercase {
        @ParameterizedTest
        @ValueSource(strings = ["My-Post", "MY-POST"])
        fun `given a slug with uppercase letters should report it`(slug: String) {
            val node = ValidationNode("slug", slug).apply { lowercase() }

            node.validate() shouldBe listOf(Violation("slug", "must be lowercase"))
        }

        @ParameterizedTest
        @ValueSource(strings = ["my-post", "my-post-2"])
        fun `given a slug without uppercase letters should accept it`(slug: String) {
            val node = ValidationNode("slug", slug).apply { lowercase() }

            node.validate() shouldBe emptyList()
        }
    }

    @Nested
    @DisplayName("When letters is called")
    inner class Letters {
        @ParameterizedTest
        @ValueSource(strings = ["Ana1", "Ana Maria"])
        fun `given a first name with something other than letters should report it`(firstName: String) {
            val node = ValidationNode("firstName", firstName).apply { letters() }

            node.validate() shouldBe listOf(Violation("firstName", "must contain only letters"))
        }

        @ParameterizedTest
        @ValueSource(strings = ["Ana", "José"])
        fun `given a first name with only letters should accept it`(firstName: String) {
            val node = ValidationNode("firstName", firstName).apply { letters() }

            node.validate() shouldBe emptyList()
        }
    }

    @Nested
    @DisplayName("When digits is called")
    inner class Digits {
        @ParameterizedTest
        @ValueSource(strings = ["01310-100", "0131010A"])
        fun `given a zip code with something other than digits should report it`(zipCode: String) {
            val node = ValidationNode("zipCode", zipCode).apply { digits() }

            node.validate() shouldBe listOf(Violation("zipCode", "must contain only digits"))
        }

        @Test
        fun `given a zip code with only digits should accept it`() {
            val node = ValidationNode("zipCode", "01310100").apply { digits() }

            node.validate() shouldBe emptyList()
        }
    }

    @Nested
    @DisplayName("When lettersOrDigits is called")
    inner class LettersOrDigits {
        @ParameterizedTest
        @ValueSource(strings = ["ana_1", "ana 1"])
        fun `given a username with symbols or spaces should report it`(username: String) {
            val node = ValidationNode("username", username).apply { lettersOrDigits() }

            node.validate() shouldBe listOf(Violation("username", "must contain only letters or digits"))
        }

        @ParameterizedTest
        @ValueSource(strings = ["ana1", "joão2"])
        fun `given a username with only letters and digits should accept it`(username: String) {
            val node = ValidationNode("username", username).apply { lettersOrDigits() }

            node.validate() shouldBe emptyList()
        }
    }

    @Nested
    @DisplayName("When ascii is called")
    inner class Ascii {
        @ParameterizedTest
        @ValueSource(strings = ["joão", "ana™"])
        fun `given a username with characters outside ASCII should report it`(username: String) {
            val node = ValidationNode("username", username).apply { ascii() }

            node.validate() shouldBe listOf(Violation("username", "must contain only ASCII characters"))
        }

        @ParameterizedTest
        @ValueSource(strings = ["joao", "ana.maria_1"])
        fun `given a username with only ASCII characters should accept it`(username: String) {
            val node = ValidationNode("username", username).apply { ascii() }

            node.validate() shouldBe emptyList()
        }
    }
}
