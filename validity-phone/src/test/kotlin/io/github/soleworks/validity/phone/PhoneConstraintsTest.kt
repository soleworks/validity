package io.github.soleworks.validity.phone

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class PhoneConstraintsTest {
    @Nested
    @DisplayName("When phone is called")
    inner class Phone {
        @ParameterizedTest
        @ValueSource(strings = ["+55 11 99712-3931", "5511997123931", "+1 415 555 2671", "+44 20 7946 0018"])
        fun `given a complete number with or without the plus sign should accept it`(phone: String) {
            val node = ValidationNode("phone", phone).apply { phone() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["(11) 99712-3931", "11997123931", "99712-3931"])
        fun `given a number without country code and nothing else should accept it when some format fits`(
            phone: String
        ) {
            val node = ValidationNode("phone", phone).apply { phone() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["1", "1234567890123456789", "+55 11 99712-39311234"])
        fun `given a text that fits no format in any country should report it`(phone: String) {
            val node = ValidationNode("phone", phone).apply { phone() }

            node.validate() shouldBe listOf(Violation("phone", "must be a valid phone number", "phone"))
        }

        @ParameterizedTest
        @ValueSource(strings = ["+55 11 99712-3931", "11 99712-3931", "99712-3931"])
        fun `given a Brazilian number when the country code is 55 should accept it`(phone: String) {
            val node = ValidationNode("phone", phone).apply { phone(countryCode = "55") }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a number from another country when the country code is 55 should report it`() {
            val node = ValidationNode("phone", "+44 20 7946 0018").apply { phone(countryCode = "55") }

            node.validate() shouldBe listOf(Violation("phone", "must be a valid phone number", "phone"))
        }

        @Test
        fun `given an area code that does not exist in the country should report it`() {
            val node = ValidationNode("phone", "(10) 99712-3931").apply {
                phone(countryCode = "55", format = PhoneFormat.AREA_CODE_AND_NUMBER)
            }

            node.validate() shouldBe listOf(Violation("phone", "must be a valid phone number", "phone"))
        }

        @Test
        fun `given the country and area codes from other fields should validate the whole number`() {
            val countryCode = "55"
            val areaCode = "11"

            val node = ValidationNode("phoneNumber", "99712-3931").apply {
                phone(countryCode = countryCode, areaCode = areaCode)
            }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given an area code from another field that does not exist should report the number`() {
            val countryCode = "55"
            val areaCode = "10"

            val node = ValidationNode("phoneNumber", "99712-3931").apply {
                phone(countryCode = countryCode, areaCode = areaCode)
            }

            node.validate() shouldBe listOf(Violation("phoneNumber", "must be a valid phone number", "phone"))
        }

        @Test
        fun `given a number with country code when only area code and number are allowed should report it`() {
            val node = ValidationNode("phone", "+55 11 99712-3931").apply {
                phone(format = PhoneFormat.AREA_CODE_AND_NUMBER)
            }

            node.validate() shouldBe listOf(Violation("phone", "must be a valid phone number", "phone"))
        }

        @Test
        fun `given a number without country code when only complete numbers are allowed should report it`() {
            val node = ValidationNode("phone", "(11) 99712-3931").apply { phone(format = PhoneFormat.COMPLETE) }

            node.validate() shouldBe listOf(Violation("phone", "must be a valid phone number", "phone"))
        }

        @Test
        fun `given a message function should build it from the value and the parameters`() {
            val node = ValidationNode("phone", "+44 20 7946 0018").apply {
                phone(countryCode = "55") { countryCode, _, _ -> "$this is not a phone from +$countryCode" }
            }

            node.validate() shouldBe listOf(Violation("phone", "+44 20 7946 0018 is not a phone from +55", "phone"))
        }
    }

    @Nested
    @DisplayName("When mobilePhone is called")
    inner class MobilePhone {
        @ParameterizedTest
        @ValueSource(strings = ["+55 11 99712-3931", "+44 7400 123456", "+1 415 555 2671"])
        fun `given a complete mobile number should accept it`(phone: String) {
            val node = ValidationNode("phone", phone).apply { mobilePhone() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["+55 11 3333-4444", "+44 20 7946 0018"])
        fun `given a complete landline number should report it`(phone: String) {
            val node = ValidationNode("phone", phone).apply { mobilePhone() }

            node.validate() shouldBe listOf(Violation("phone", "must be a valid mobile phone number", "mobilePhone"))
        }

        @Test
        fun `given a mobile number with area code when the country code is 55 should accept it`() {
            val node = ValidationNode("phone", "(11) 99712-3931").apply {
                mobilePhone(countryCode = "55", format = PhoneFormat.AREA_CODE_AND_NUMBER)
            }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a landline with area code when the country code is 55 should report it`() {
            val node = ValidationNode("phone", "(11) 3333-4444").apply {
                mobilePhone(countryCode = "55", format = PhoneFormat.AREA_CODE_AND_NUMBER)
            }

            node.validate() shouldBe listOf(Violation("phone", "must be a valid mobile phone number", "mobilePhone"))
        }

        @Test
        fun `given the country and area codes from other fields should accept a mobile number`() {
            val countryCode = "55"
            val areaCode = "11"

            val node = ValidationNode("phoneNumber", "99712-3931").apply {
                mobilePhone(countryCode = countryCode, areaCode = areaCode)
            }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given the country and area codes from other fields should report a landline`() {
            val countryCode = "55"
            val areaCode = "11"

            val node = ValidationNode("phoneNumber", "3333-4444").apply {
                mobilePhone(countryCode = countryCode, areaCode = areaCode)
            }

            node.validate() shouldBe listOf(Violation("phoneNumber", "must be a valid mobile phone number", "mobilePhone"))
        }

        @Test
        fun `given a number without area code should accept it when its length fits a mobile number`() {
            val node = ValidationNode("phoneNumber", "99712-3931").apply { mobilePhone(countryCode = "55") }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function should build it from the value and the parameters`() {
            val node = ValidationNode("phone", "+55 11 3333-4444").apply {
                mobilePhone(countryCode = "55") { countryCode, _, _ -> "$this is not a mobile from +$countryCode" }
            }

            node.validate() shouldBe listOf(Violation("phone", "+55 11 3333-4444 is not a mobile from +55", "mobilePhone"))
        }
    }

    @Nested
    @DisplayName("When countryCode is called")
    inner class CountryCode {
        @ParameterizedTest
        @ValueSource(strings = ["55", "+55", "1", "44"])
        fun `given an existing country calling code should accept it`(countryCode: String) {
            val node = ValidationNode("countryCode", countryCode).apply { countryCode() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["999", "0", "BR", ""])
        fun `given a country calling code that does not exist should report it`(countryCode: String) {
            val node = ValidationNode("countryCode", countryCode).apply { countryCode() }

            node.validate() shouldBe listOf(Violation("countryCode", "must be a valid country calling code", "countryCode"))
        }
    }

    @Nested
    @DisplayName("When areaCode is called")
    inner class AreaCode {
        @ParameterizedTest
        @ValueSource(strings = ["11", "61", "21"])
        fun `given a Brazilian area code that exists should accept it`(areaCode: String) {
            val node = ValidationNode("areaCode", areaCode).apply { areaCode(countryCode = "55") }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["10", "20", "1", "111", "ab"])
        fun `given a Brazilian area code that does not exist should report it`(areaCode: String) {
            val node = ValidationNode("areaCode", areaCode).apply { areaCode(countryCode = "55") }

            node.validate() shouldBe listOf(Violation("areaCode", "must be a valid area code", "areaCode"))
        }

        @ParameterizedTest
        @ValueSource(strings = ["415", "212"])
        fun `given a North American area code that exists should accept it`(areaCode: String) {
            val node = ValidationNode("areaCode", areaCode).apply { areaCode(countryCode = "1") }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a North American area code that does not exist should report it`() {
            val node = ValidationNode("areaCode", "999").apply { areaCode(countryCode = "1") }

            node.validate() shouldBe listOf(Violation("areaCode", "must be a valid area code", "areaCode"))
        }

        @ParameterizedTest
        @ValueSource(strings = ["20", "121", "17687"])
        fun `given an area code of a country whose area codes vary in length should accept up to 5 digits`(
            areaCode: String
        ) {
            val node = ValidationNode("areaCode", areaCode).apply { areaCode(countryCode = "44") }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["123456", "2a"])
        fun `given an area code that is not up to 5 digits should report it`(areaCode: String) {
            val node = ValidationNode("areaCode", areaCode).apply { areaCode(countryCode = "44") }

            node.validate() shouldBe listOf(Violation("areaCode", "must be a valid area code", "areaCode"))
        }

        @ParameterizedTest
        @ValueSource(strings = ["11", "415"])
        fun `given an area code without country should accept it when it exists somewhere`(areaCode: String) {
            val node = ValidationNode("areaCode", areaCode).apply { areaCode() }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function should build it from the value and the country code`() {
            val node = ValidationNode("areaCode", "10").apply {
                areaCode(countryCode = "55") { countryCode -> "$this is not an area code of +$countryCode" }
            }

            node.validate() shouldBe listOf(Violation("areaCode", "10 is not an area code of +55", "areaCode"))
        }
    }
}
