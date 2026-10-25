package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.github.soleworks.validity.samples.PaymentMethod
import io.kotest.matchers.shouldBe
import java.text.Normalizer
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
        @ValueSource(strings = ["Al", "Ana"])
        fun `given a name with at least the minimum should accept it`(
            name: String
        ) {
            val node = ValidationNode(path = "name", value = name).apply { minLength(2) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["", "A"])
        fun `given a name shorter than the minimum should report it`(
            name: String
        ) {
            val node = ValidationNode(path = "name", value = name).apply { minLength(2) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "name", message = "must have at least 2 characters", code = "minLength")
            )
        }

        @Test
        fun `given a custom message with the minimum placeholder should replace it`() {
            val node = ValidationNode(
                path = "sku",
                value = "S1"
            ).apply { minLength(5, "must have at least {min} characters, like SKU-1") }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "sku", message = "must have at least 5 characters, like SKU-1", code = "minLength")
            )
        }

        @Test
        fun `given a message function should build it from the value and the minimum`() {
            val node = ValidationNode(path = "description", value = "Shirt").apply {
                minLength(10) { min -> "'$this' is too short, it needs at least $min characters" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(
                    path = "description",
                    message = "'Shirt' is too short, it needs at least 10 characters",
                    code = "minLength"
                )
            )
        }
    }

    @Nested
    @DisplayName("When maxLength is called")
    inner class MaxLength {
        @ParameterizedTest
        @ValueSource(strings = ["Ana", "Maximilian"])
        fun `given a nickname with at most the maximum should accept it`(
            nickname: String
        ) {
            val node = ValidationNode(path = "nickname", value = nickname).apply { maxLength(10) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["Christopher", "Maximiliano Augusto"])
        fun `given a nickname longer than the maximum should report it`(
            nickname: String
        ) {
            val node = ValidationNode(path = "nickname", value = nickname).apply { maxLength(10) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "nickname", message = "must have at most 10 characters", code = "maxLength")
            )
        }

        @Test
        fun `given a message function should build it from the value and the maximum`() {
            val node = ValidationNode(path = "nickname", value = "Christopher").apply {
                maxLength(10) { max -> "'$this' passes $max characters" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "nickname", message = "'Christopher' passes 10 characters", code = "maxLength")
            )
        }
    }

    @Nested
    @DisplayName("When length is called")
    inner class Length {
        @Test
        fun `given a zip code with exactly 8 characters should accept it`() {
            val node = ValidationNode(path = "zipCode", value = "01310100").apply { length(8) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["0131010", "013101000"])
        fun `given a zip code without exactly 8 characters should report it`(
            zipCode: String
        ) {
            val node = ValidationNode(path = "zipCode", value = zipCode).apply { length(8) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "zipCode", message = "must have exactly 8 characters", code = "length")
            )
        }

        @Test
        fun `given a message function should build it from the value and the length`() {
            val node = ValidationNode(path = "zipCode", value = "0131010").apply {
                length(8) { length -> "'$this' needs $length characters" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "zipCode", message = "'0131010' needs 8 characters", code = "length")
            )
        }
    }

    @Nested
    @DisplayName("When lengthBetween is called")
    inner class LengthBetween {
        @ParameterizedTest
        @ValueSource(strings = ["ana", "ana.maria.da.silva.1"])
        fun `given a username inside the length range should accept it`(
            username: String
        ) {
            val node = ValidationNode(path = "username", value = username).apply { lengthBetween(min = 3, max = 20) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["an", "ana.maria.da.silva.01"])
        fun `given a username outside the length range should report it`(
            username: String
        ) {
            val node = ValidationNode(path = "username", value = username).apply { lengthBetween(min = 3, max = 20) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "username", message = "must have between 3 and 20 characters", code = "lengthBetween")
            )
        }

        @Test
        fun `given a message function should build it from the value and the limits`() {
            val node = ValidationNode(path = "username", value = "an").apply {
                lengthBetween(min = 3, max = 20) { min, max -> "'$this' needs from $min to $max characters" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "username", message = "'an' needs from 3 to 20 characters", code = "lengthBetween")
            )
        }
    }

    @Nested
    @DisplayName("When notEmpty is called")
    inner class NotEmpty {
        @ParameterizedTest
        @ValueSource(strings = [" ", "Cotton t-shirt"])
        fun `given a description with any character should accept it`(
            description: String
        ) {
            val node = ValidationNode(path = "description", value = description).apply { notEmpty() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given an empty description should report it`() {
            val node = ValidationNode(path = "description", value = "").apply { notEmpty() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "description", message = "must not be empty", code = "notEmpty")
            )
        }
    }

    @Nested
    @DisplayName("When empty is called")
    inner class Empty {
        @Test
        fun `given an empty honeypot field should accept it`() {
            val node = ValidationNode(path = "website", value = "").apply { empty() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = [" ", "http://spam.example"])
        fun `given a filled honeypot field should report it`(
            website: String
        ) {
            val node = ValidationNode(path = "website", value = website).apply { empty() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "website", message = "must be empty", code = "empty")
            )
        }
    }

    @Nested
    @DisplayName("When notBlank is called")
    inner class NotBlank {
        @ParameterizedTest
        @ValueSource(strings = ["Ana", " Ana "])
        fun `given a name with visible characters should accept it`(
            name: String
        ) {
            val node = ValidationNode(path = "name", value = name).apply { notBlank() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["", "   "])
        fun `given a blank name should report it`(
            name: String
        ) {
            val node = ValidationNode(path = "name", value = name).apply { notBlank() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "name", message = "must not be blank", code = "notBlank")
            )
        }
    }

    @Nested
    @DisplayName("When blank is called")
    inner class Blank {
        @ParameterizedTest
        @ValueSource(strings = ["", "   "])
        fun `given a note without visible characters should accept it`(
            note: String
        ) {
            val node = ValidationNode(path = "note", value = note).apply { blank() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a note with visible characters should report it`() {
            val node = ValidationNode(path = "note", value = " gift ").apply { blank() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "note", message = "must be blank", code = "blank")
            )
        }
    }

    @Nested
    @DisplayName("When trimmed is called")
    inner class Trimmed {
        @ParameterizedTest
        @ValueSource(strings = ["", "Ana", "Ana Souza"])
        fun `given a name without surrounding whitespace should accept it`(
            name: String
        ) {
            val node = ValidationNode(path = "name", value = name).apply { trimmed() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = [" Ana", "Ana ", " "])
        fun `given a name with surrounding whitespace should report it`(
            name: String
        ) {
            val node = ValidationNode(path = "name", value = name).apply { trimmed() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "name", message = "must not start or end with whitespace", code = "trimmed")
            )
        }
    }

    @Nested
    @DisplayName("When matches is called")
    inner class Matches {
        @Test
        fun `given a currency that matches the pattern should accept it`() {
            val node = ValidationNode(path = "currency", value = "USD").apply { matches(Regex("[A-Z]{3}")) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["usd", "US", "USDT"])
        fun `given a currency that does not match the pattern should report it`(
            currency: String
        ) {
            val node = ValidationNode(path = "currency", value = currency).apply { matches(Regex("[A-Z]{3}")) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "currency", message = "must match [A-Z]{3}", code = "matches")
            )
        }

        @Test
        fun `given a message function should build it from the value and the pattern`() {
            val node = ValidationNode(path = "currency", value = "usd").apply {
                matches(Regex("[A-Z]{3}")) { regex -> "'$this' is not like $regex" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "currency", message = "'usd' is not like [A-Z]{3}", code = "matches")
            )
        }
    }

    @Nested
    @DisplayName("When notMatches is called")
    inner class NotMatches {
        @Test
        fun `given a name that does not match the forbidden pattern should accept it`() {
            val node = ValidationNode(path = "name", value = "Ana").apply { notMatches(Regex(""".*\d.*""")) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["Ana1", "4na"])
        fun `given a name that matches the forbidden pattern should report it`(
            name: String
        ) {
            val node = ValidationNode(path = "name", value = name).apply { notMatches(Regex(""".*\d.*""")) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "name", message = """must not match .*\d.*""", code = "notMatches")
            )
        }

        @Test
        fun `given a message function should build it from the value and the pattern`() {
            val node = ValidationNode(path = "name", value = "Ana1").apply {
                notMatches(Regex(""".*\d.*""")) { regex -> "'$this' matches $regex" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "name", message = """'Ana1' matches .*\d.*""", code = "notMatches")
            )
        }
    }

    @Nested
    @DisplayName("When contains is called")
    inner class Contains {
        @Test
        fun `given an email with the at sign should accept it`() {
            val node = ValidationNode(path = "email", value = "ana@mail.com").apply { contains("@") }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given an email with the domain in another case and ignoreCase should accept it`() {
            val node = ValidationNode(path = "email", value = "ana@Mail.com").apply {
                contains("mail.com", ignoreCase = true)
            }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given an email without the at sign should report it`() {
            val node = ValidationNode(path = "email", value = "ana.mail.com").apply { contains("@") }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "email", message = "must contain @", code = "contains")
            )
        }

        @Test
        fun `given no text to look for should skip the check`() {
            val domain: String? = null
            val node = ValidationNode(path = "email", value = "ana@mail.com").apply { contains(domain) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a message function should build it from the value and the text`() {
            val node = ValidationNode(
                path = "email",
                value = "ana.mail.com"
            ).apply { contains("@") { text -> "'$this' lacks $text" } }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "email", message = "'ana.mail.com' lacks @", code = "contains")
            )
        }
    }

    @Nested
    @DisplayName("When notContains is called")
    inner class NotContains {
        @Test
        fun `given a password without the username should accept it`() {
            val username = "ana"
            val node = ValidationNode(path = "password", value = "s3cret!x").apply { notContains(username) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a password with the username should report it`() {
            val username = "ana"
            val node = ValidationNode(path = "password", value = "ana12345").apply { notContains(username) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "password", message = "must not contain ana", code = "notContains")
            )
        }

        @Test
        fun `given a password with the username in another case and ignoreCase should report it`() {
            val node = ValidationNode(path = "password", value = "ANA12345").apply {
                notContains("ana", ignoreCase = true)
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "password", message = "must not contain ana", code = "notContains")
            )
        }

        @Test
        fun `given an account without a username should skip the check`() {
            val username: String? = null
            val node = ValidationNode(path = "password", value = "ana12345").apply { notContains(username) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a message function should build it from the value and the text`() {
            val node = ValidationNode(path = "password", value = "ana12345").apply {
                notContains("ana") { text -> "the password has the username $text" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "password", message = "the password has the username ana", code = "notContains")
            )
        }
    }

    @Nested
    @DisplayName("When startsWith is called")
    inner class StartsWith {
        @Test
        fun `given a Brazilian IBAN should accept it`() {
            val node = ValidationNode(path = "iban", value = "BR1500000000000010932840814P2").apply { startsWith("BR") }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a lowercase Brazilian IBAN and ignoreCase should accept it`() {
            val node = ValidationNode(path = "iban", value = "br1500000000000010932840814p2").apply {
                startsWith("BR", ignoreCase = true)
            }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a foreign IBAN should report it`() {
            val node = ValidationNode(path = "iban", value = "DE89370400440532013000").apply { startsWith("BR") }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "iban", message = "must start with BR", code = "startsWith")
            )
        }

        @Test
        fun `given no prefix should skip the check`() {
            val countryCode: String? = null
            val node = ValidationNode(path = "iban", value = "DE89370400440532013000").apply { startsWith(countryCode) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a message function should build it from the value and the prefix`() {
            val node = ValidationNode(path = "iban", value = "DE89370400440532013000").apply {
                startsWith("BR") { prefix -> "$this is not from $prefix" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "iban", message = "DE89370400440532013000 is not from BR", code = "startsWith")
            )
        }
    }

    @Nested
    @DisplayName("When notStartsWith is called")
    inner class NotStartsWith {
        @Test
        fun `given an account number without a leading zero should accept it`() {
            val node = ValidationNode(path = "accountNumber", value = "1234").apply { notStartsWith("0") }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given an account number with a leading zero should report it`() {
            val node = ValidationNode(path = "accountNumber", value = "0123").apply { notStartsWith("0") }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "accountNumber", message = "must not start with 0", code = "notStartsWith")
            )
        }

        @Test
        fun `given an account number with a lowercase test prefix and ignoreCase should report it`() {
            val node = ValidationNode(path = "accountNumber", value = "test-123").apply {
                notStartsWith("TEST-", ignoreCase = true)
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "accountNumber", message = "must not start with TEST-", code = "notStartsWith")
            )
        }

        @Test
        fun `given no prefix should skip the check`() {
            val forbiddenPrefix: String? = null
            val node = ValidationNode(path = "accountNumber", value = "0123").apply { notStartsWith(forbiddenPrefix) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a message function should build it from the value and the prefix`() {
            val node = ValidationNode(path = "accountNumber", value = "0123").apply {
                notStartsWith("0") { prefix -> "$this starts with $prefix" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "accountNumber", message = "0123 starts with 0", code = "notStartsWith")
            )
        }
    }

    @Nested
    @DisplayName("When endsWith is called")
    inner class EndsWith {
        @Test
        fun `given a PDF file should accept it`() {
            val node = ValidationNode(path = "fileName", value = "contract.pdf").apply { endsWith(".pdf") }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given an uppercase PDF extension and ignoreCase should accept it`() {
            val node = ValidationNode(path = "fileName", value = "contract.PDF").apply {
                endsWith(".pdf", ignoreCase = true)
            }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a file that is not a PDF should report it`() {
            val node = ValidationNode(path = "fileName", value = "contract.docx").apply { endsWith(".pdf") }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "fileName", message = "must end with .pdf", code = "endsWith")
            )
        }

        @Test
        fun `given no suffix should skip the check`() {
            val extension: String? = null
            val node = ValidationNode(path = "fileName", value = "contract.docx").apply { endsWith(extension) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a message function should build it from the value and the suffix`() {
            val node = ValidationNode(path = "fileName", value = "contract.docx").apply {
                endsWith(".pdf") { suffix -> "$this is not a $suffix file" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "fileName", message = "contract.docx is not a .pdf file", code = "endsWith")
            )
        }
    }

    @Nested
    @DisplayName("When notEndsWith is called")
    inner class NotEndsWith {
        @Test
        fun `given a file that is not executable should accept it`() {
            val node = ValidationNode(path = "fileName", value = "setup.zip").apply { notEndsWith(".exe") }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given an executable file should report it`() {
            val node = ValidationNode(path = "fileName", value = "setup.exe").apply { notEndsWith(".exe") }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "fileName", message = "must not end with .exe", code = "notEndsWith")
            )
        }

        @Test
        fun `given an uppercase executable extension and ignoreCase should report it`() {
            val node = ValidationNode(path = "fileName", value = "setup.EXE").apply {
                notEndsWith(".exe", ignoreCase = true)
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "fileName", message = "must not end with .exe", code = "notEndsWith")
            )
        }

        @Test
        fun `given no suffix should skip the check`() {
            val forbiddenExtension: String? = null
            val node = ValidationNode(path = "fileName", value = "setup.exe").apply { notEndsWith(forbiddenExtension) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a message function should build it from the value and the suffix`() {
            val node = ValidationNode(path = "fileName", value = "setup.exe").apply {
                notEndsWith(".exe") { suffix -> "$this is a $suffix file" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "fileName", message = "setup.exe is a .exe file", code = "notEndsWith")
            )
        }
    }

    @Nested
    @DisplayName("When uppercase is called")
    inner class Uppercase {
        @ParameterizedTest
        @ValueSource(strings = ["USD", "BRL-2"])
        fun `given a currency without lowercase letters should accept it`(
            currency: String
        ) {
            val node = ValidationNode(path = "currency", value = currency).apply { uppercase() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["usd", "Usd"])
        fun `given a currency with lowercase letters should report it`(
            currency: String
        ) {
            val node = ValidationNode(path = "currency", value = currency).apply { uppercase() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "currency", message = "must be uppercase", code = "uppercase")
            )
        }
    }

    @Nested
    @DisplayName("When lowercase is called")
    inner class Lowercase {
        @ParameterizedTest
        @ValueSource(strings = ["my-post", "my-post-2"])
        fun `given a slug without uppercase letters should accept it`(
            slug: String
        ) {
            val node = ValidationNode(path = "slug", value = slug).apply { lowercase() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["My-Post", "MY-POST"])
        fun `given a slug with uppercase letters should report it`(
            slug: String
        ) {
            val node = ValidationNode(path = "slug", value = slug).apply { lowercase() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "slug", message = "must be lowercase", code = "lowercase")
            )
        }
    }

    @Nested
    @DisplayName("When letters is called")
    inner class Letters {
        @ParameterizedTest
        @ValueSource(strings = ["Ana", "José"])
        fun `given a first name with only letters should accept it`(
            firstName: String
        ) {
            val node = ValidationNode(path = "firstName", value = firstName).apply { letters() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["Ana1", "Ana Maria"])
        fun `given a first name with something other than letters should report it`(
            firstName: String
        ) {
            val node = ValidationNode(path = "firstName", value = firstName).apply { letters() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "firstName", message = "must contain only letters", code = "letters")
            )
        }

        @Test
        fun `given an empty first name should report it`() {
            val node = ValidationNode(path = "firstName", value = "").apply { letters() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "firstName", message = "must contain only letters", code = "letters")
            )
        }
    }

    @Nested
    @DisplayName("When digits is called")
    inner class Digits {
        @Test
        fun `given a zip code with only digits should accept it`() {
            val node = ValidationNode(path = "zipCode", value = "01310100").apply { digits() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["01310-100", "0131010A"])
        fun `given a zip code with something other than digits should report it`(
            zipCode: String
        ) {
            val node = ValidationNode(path = "zipCode", value = zipCode).apply { digits() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "zipCode", message = "must contain only digits", code = "digits")
            )
        }

        @ParameterizedTest
        @ValueSource(strings = ["０１３１０１００", "٠١٣١٠١٠٠"])
        fun `given a zip code with non-ASCII digits should report it`(
            zipCode: String
        ) {
            val node = ValidationNode(path = "zipCode", value = zipCode).apply { digits() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "zipCode", message = "must contain only digits", code = "digits")
            )
        }

        @Test
        fun `given an empty zip code should report it`() {
            val node = ValidationNode(path = "zipCode", value = "").apply { digits() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "zipCode", message = "must contain only digits", code = "digits")
            )
        }
    }

    @Nested
    @DisplayName("When lettersOrDigits is called")
    inner class LettersOrDigits {
        @ParameterizedTest
        @ValueSource(strings = ["ana1", "joão2"])
        fun `given a username with only letters and digits should accept it`(
            username: String
        ) {
            val node = ValidationNode(path = "username", value = username).apply { lettersOrDigits() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["ana_1", "ana 1"])
        fun `given a username with symbols or spaces should report it`(
            username: String
        ) {
            val node = ValidationNode(path = "username", value = username).apply { lettersOrDigits() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "username", message = "must contain only letters or digits", code = "lettersOrDigits")
            )
        }

        @ParameterizedTest
        @ValueSource(strings = ["ana１", "ana١"])
        fun `given a username with a non-ASCII digit should report it`(
            username: String
        ) {
            val node = ValidationNode(path = "username", value = username).apply { lettersOrDigits() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "username", message = "must contain only letters or digits", code = "lettersOrDigits")
            )
        }

        @Test
        fun `given an empty username should report it`() {
            val node = ValidationNode(path = "username", value = "").apply { lettersOrDigits() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "username", message = "must contain only letters or digits", code = "lettersOrDigits")
            )
        }
    }

    @Nested
    @DisplayName("When ascii is called")
    inner class Ascii {
        @ParameterizedTest
        @ValueSource(strings = ["joao", "ana.maria_1"])
        fun `given a username with only ASCII characters should accept it`(
            username: String
        ) {
            val node = ValidationNode(path = "username", value = username).apply { ascii() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["joão", "ana™"])
        fun `given a username with characters outside ASCII should report it`(
            username: String
        ) {
            val node = ValidationNode(path = "username", value = username).apply { ascii() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "username", message = "must contain only ASCII characters", code = "ascii")
            )
        }

        @Test
        fun `given an empty username should report it`() {
            val node = ValidationNode(path = "username", value = "").apply { ascii() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "username", message = "must contain only ASCII characters", code = "ascii")
            )
        }
    }

    @Nested
    @DisplayName("When numeric is called")
    inner class Numeric {
        @ParameterizedTest
        @ValueSource(strings = ["10", "-10.50", "+3.14"])
        fun `given a number written as text should accept it`(
            amount: String
        ) {
            val node = ValidationNode(path = "amount", value = amount).apply { numeric() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["1e5", "10.", ".5", "1,5", "abc"])
        fun `given text without it should report it`(
            amount: String
        ) {
            val node = ValidationNode(path = "amount", value = amount).apply { numeric() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "amount", message = "must be numeric", code = "numeric")
            )
        }
    }

    @Nested
    @DisplayName("When integer is called")
    inner class Integer {
        @ParameterizedTest
        @ValueSource(strings = ["10", "-7", "+3"])
        fun `given an integer written as text should accept it`(
            quantity: String
        ) {
            val node = ValidationNode(path = "quantity", value = quantity).apply { integer() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["10.0", "1e3", ""])
        fun `given text without it should report it`(
            quantity: String
        ) {
            val node = ValidationNode(path = "quantity", value = quantity).apply { integer() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "quantity", message = "must be an integer", code = "integer")
            )
        }
    }

    @Nested
    @DisplayName("When containsUppercase is called")
    inner class ContainsUppercase {
        @ParameterizedTest
        @ValueSource(strings = ["passWord"])
        fun `given a password with an uppercase letter should accept it`(
            password: String
        ) {
            val node = ValidationNode(path = "password", value = password).apply { containsUppercase() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["password"])
        fun `given text without it should report it`(
            password: String
        ) {
            val node = ValidationNode(path = "password", value = password).apply { containsUppercase() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "password", message = "must contain an uppercase letter", code = "containsUppercase")
            )
        }
    }

    @Nested
    @DisplayName("When containsLowercase is called")
    inner class ContainsLowercase {
        @ParameterizedTest
        @ValueSource(strings = ["PASSWORd"])
        fun `given a password with a lowercase letter should accept it`(
            password: String
        ) {
            val node = ValidationNode(path = "password", value = password).apply { containsLowercase() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["PASSWORD"])
        fun `given text without it should report it`(
            password: String
        ) {
            val node = ValidationNode(path = "password", value = password).apply { containsLowercase() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "password", message = "must contain a lowercase letter", code = "containsLowercase")
            )
        }
    }

    @Nested
    @DisplayName("When containsDigit is called")
    inner class ContainsDigit {
        @ParameterizedTest
        @ValueSource(strings = ["pass1"])
        fun `given a password with a digit should accept it`(
            password: String
        ) {
            val node = ValidationNode(path = "password", value = password).apply { containsDigit() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["pass", "pass１", "pass١"])
        fun `given text without it should report it`(
            password: String
        ) {
            val node = ValidationNode(path = "password", value = password).apply { containsDigit() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "password", message = "must contain a digit", code = "containsDigit")
            )
        }
    }

    @Nested
    @DisplayName("When containsSymbol is called")
    inner class ContainsSymbol {
        @ParameterizedTest
        @ValueSource(strings = ["pass!", "pass word#"])
        fun `given a password with a symbol should accept it`(
            password: String
        ) {
            val node = ValidationNode(path = "password", value = password).apply { containsSymbol() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["pass word", "pass1"])
        fun `given text without it should report it`(
            password: String
        ) {
            val node = ValidationNode(path = "password", value = password).apply { containsSymbol() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "password", message = "must contain a symbol", code = "containsSymbol")
            )
        }
    }

    @Nested
    @DisplayName("When equalTo is called")
    inner class EqualTo {
        @ParameterizedTest
        @ValueSource(strings = ["active", "ACTIVE", "Active"])
        fun `given the status in any case and ignoreCase should accept it`(
            status: String
        ) {
            val node = ValidationNode(path = "status", value = status).apply { equalTo("active", ignoreCase = true) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given the status in another case without ignoreCase should report it`() {
            val node = ValidationNode(path = "status", value = "ACTIVE").apply { equalTo("active", ignoreCase = false) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "status", message = "must be equal to active", code = "equalTo")
            )
        }

        @Test
        fun `given another status and ignoreCase should report it`() {
            val node = ValidationNode(path = "status", value = "blocked").apply { equalTo("active", ignoreCase = true) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "status", message = "must be equal to active", code = "equalTo")
            )
        }

        @Test
        fun `given no expected status should skip the check`() {
            val expected: String? = null
            val node = ValidationNode(path = "status", value = "blocked").apply { equalTo(expected, ignoreCase = true) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a message function should build it from the value and the expected status`() {
            val node = ValidationNode(path = "status", value = "blocked").apply {
                equalTo("active", ignoreCase = true) { other -> "$this is not $other" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "status", message = "blocked is not active", code = "equalTo")
            )
        }
    }

    @Nested
    @DisplayName("When oneOf is called")
    inner class OneOf {
        @ParameterizedTest
        @ValueSource(strings = ["br", "BR", "Pt"])
        fun `given a listed country in any case and ignoreCase should accept it`(
            country: String
        ) {
            val node = ValidationNode(path = "country", value = country).apply {
                oneOf(listOf("BR", "PT"), ignoreCase = true)
            }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a listed country in another case without ignoreCase should report it`() {
            val node = ValidationNode(path = "country", value = "br").apply {
                oneOf(listOf("BR", "PT"), ignoreCase = false)
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "country", message = "must be one of BR, PT", code = "oneOf")
            )
        }

        @Test
        fun `given a country that is not listed and ignoreCase should report it`() {
            val node = ValidationNode(path = "country", value = "us").apply {
                oneOf(listOf("BR", "PT"), ignoreCase = true)
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "country", message = "must be one of BR, PT", code = "oneOf")
            )
        }

        @Test
        fun `given no list of countries should skip the check`() {
            val countries: List<String>? = null
            val node = ValidationNode(path = "country", value = "us").apply { oneOf(countries, ignoreCase = true) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a message function should build it from the value and the countries`() {
            val node = ValidationNode(path = "country", value = "us").apply {
                oneOf(listOf("BR", "PT"), ignoreCase = true) { values -> "$this is not in $values" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "country", message = "us is not in [BR, PT]", code = "oneOf")
            )
        }
    }

    @Nested
    @DisplayName("When enum is called")
    inner class Enum {
        @ParameterizedTest
        @ValueSource(strings = ["PIX", "TED", "BOLETO"])
        fun `given the name of a constant should accept it`(
            method: String
        ) {
            val node = ValidationNode(path = "method", value = method).apply { enum<PaymentMethod>() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given the name of a constant in another case and ignoreCase should accept it`() {
            val node = ValidationNode(path = "method", value = "pix").apply { enum<PaymentMethod>(ignoreCase = true) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["pix", "DOC", ""])
        fun `given another text should report it with the names of the constants`(
            method: String
        ) {
            val node = ValidationNode(path = "method", value = method).apply { enum<PaymentMethod>() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "method", message = "must be one of PIX, TED, BOLETO", code = "enum")
            )
        }

        @Test
        fun `given a custom message with the values placeholder should replace it`() {
            val node = ValidationNode(path = "method", value = "DOC").apply {
                enum<PaymentMethod>(message = "use {values}")
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "method", message = "use PIX, TED, BOLETO", code = "enum")
            )
        }

        @Test
        fun `given a message function should build it from the value and the constants`() {
            val node = ValidationNode(path = "method", value = "DOC").apply {
                enum<PaymentMethod> { methods -> "$this is not one of ${methods.size} methods" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "method", message = "DOC is not one of 3 methods", code = "enum")
            )
        }
    }

    @Nested
    @DisplayName("When minBytes is called")
    inner class MinBytes {
        @Test
        fun `given a note whose UTF-8 bytes reach the minimum should accept it`() {
            val node = ValidationNode(path = "note", value = "é").apply { minBytes(2) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a note with fewer bytes than the minimum should report it`() {
            val node = ValidationNode(path = "note", value = "ab").apply { minBytes(3) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "note", message = "must have at least 3 bytes", code = "minBytes")
            )
        }

        @Test
        fun `given a note measured in ISO-8859-1 with fewer bytes than the minimum should report it`() {
            val node = ValidationNode(path = "note", value = "éé").apply { minBytes(3, Charsets.ISO_8859_1) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "note", message = "must have at least 3 bytes", code = "minBytes")
            )
        }

        @Test
        fun `given a message function should build it from the value and the minimum`() {
            val node = ValidationNode(path = "note", value = "ab").apply {
                minBytes(3) { min -> "'$this' needs $min bytes" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "note", message = "'ab' needs 3 bytes", code = "minBytes")
            )
        }
    }

    @Nested
    @DisplayName("When maxBytes is called")
    inner class MaxBytes {
        @Test
        fun `given a bio within the maximum bytes should accept it`() {
            val node = ValidationNode(path = "bio", value = "abc").apply { maxBytes(3) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a bio measured in ISO-8859-1 within the maximum bytes should accept it`() {
            val node = ValidationNode(path = "bio", value = "café").apply { maxBytes(4, Charsets.ISO_8859_1) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a bio whose UTF-8 bytes pass the maximum should report it`() {
            val node = ValidationNode(path = "bio", value = "café").apply { maxBytes(4) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "bio", message = "must have at most 4 bytes", code = "maxBytes")
            )
        }

        @Test
        fun `given a message function should build it from the value and the maximum`() {
            val node = ValidationNode(path = "bio", value = "café").apply {
                maxBytes(4) { max -> "'$this' passes $max bytes" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "bio", message = "'café' passes 4 bytes", code = "maxBytes")
            )
        }
    }

    @Nested
    @DisplayName("When containsOnly is called")
    inner class ContainsOnly {
        @ParameterizedTest
        @ValueSource(strings = ["01310-100", "01310100"])
        fun `given a zip code with only digits and dashes should accept it`(
            zipCode: String
        ) {
            val node = ValidationNode(path = "zipCode", value = zipCode).apply { containsOnly("0123456789-") }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["01310 100", "abc", ""])
        fun `given a zip code with other characters or empty should report it`(
            zipCode: String
        ) {
            val node = ValidationNode(path = "zipCode", value = zipCode).apply { containsOnly("0123456789-") }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(
                    path = "zipCode",
                    message = "must contain only the characters 0123456789-",
                    code = "containsOnly"
                )
            )
        }

        @Test
        fun `given no allowed characters should skip the check`() {
            val allowed: String? = null
            val node = ValidationNode(path = "zipCode", value = "abc").apply { containsOnly(allowed) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a message function should build it from the value and the characters`() {
            val node = ValidationNode(path = "zipCode", value = "abc").apply {
                containsOnly("0123456789-") { characters -> "'$this' has characters outside $characters" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "zipCode", message = "'abc' has characters outside 0123456789-", code = "containsOnly")
            )
        }
    }

    @Nested
    @DisplayName("When normalized is called")
    inner class Normalized {
        @Test
        fun `given a composed name should accept it`() {
            val name = Normalizer.normalize("José", Normalizer.Form.NFC)
            val node = ValidationNode(path = "name", value = name).apply { normalized() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a decomposed name checked for NFD should accept it`() {
            val name = Normalizer.normalize("José", Normalizer.Form.NFD)
            val node = ValidationNode(path = "name", value = name).apply { normalized(Normalizer.Form.NFD) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a decomposed name should report it`() {
            val name = Normalizer.normalize("José", Normalizer.Form.NFD)
            val node = ValidationNode(path = "name", value = name).apply { normalized() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "name", message = "must be in Unicode NFC form", code = "normalized")
            )
        }

        @Test
        fun `given a message function should build it from the value and the form`() {
            val name = Normalizer.normalize("José", Normalizer.Form.NFD)
            val node = ValidationNode(path = "name", value = name).apply {
                normalized { form -> "is not in $form" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "name", message = "is not in NFC", code = "normalized")
            )
        }
    }

    @Nested
    @DisplayName("When boolean is called")
    inner class Boolean {
        @ParameterizedTest
        @ValueSource(strings = ["true", "false"])
        fun `given a flag written as true or false should accept it`(
            flag: String
        ) {
            val node = ValidationNode(path = "newsletter", value = flag).apply { boolean() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["TRUE", "yes", "1", ""])
        fun `given a flag written another way should report it`(
            flag: String
        ) {
            val node = ValidationNode(path = "newsletter", value = flag).apply { boolean() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "newsletter", message = "must be true or false", code = "boolean")
            )
        }
    }
}
