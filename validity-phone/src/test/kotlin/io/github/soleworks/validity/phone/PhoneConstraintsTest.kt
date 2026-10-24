package io.github.soleworks.validity.phone

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.github.soleworks.validity.phone.samples.readme.Contact
import io.github.soleworks.validity.validate
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
        fun `given a complete number with or without the plus sign should accept it`(
            phone: String
        ) {
            val node = ValidationNode(path = "phone", value = phone).apply { phone() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["(11) 99712-3931", "11997123931", "99712-3931"])
        fun `given a number without country code and nothing else should accept it when some format fits`(
            phone: String
        ) {
            val node = ValidationNode(path = "phone", value = phone).apply { phone() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["+55 11 99712-3931", "11 99712-3931", "99712-3931"])
        fun `given a Brazilian number when the country code is 55 should accept it`(
            phone: String
        ) {
            val node = ValidationNode(path = "phone", value = phone).apply { phone(countryCode = "55") }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["+55 11 99712-3931", "(11) 99712-3931"])
        fun `given a number from the area code of another field should accept it`(
            phone: String
        ) {
            val node = ValidationNode(
                path = "phone",
                value = phone
            ).apply { phone(countryCode = "55", areaCode = "11") }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given an area code from another field with the trunk prefix should accept the number from it`() {
            val node = ValidationNode(
                path = "phone",
                value = "+44 20 7946 0018"
            ).apply { phone(countryCode = "44", areaCode = "020") }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["1", "1234567890123456789", "+55 11 99712-39311234"])
        fun `given a text that fits no format in any country should report it`(
            phone: String
        ) {
            val node = ValidationNode(path = "phone", value = phone).apply { phone() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "phone", message = "must be a valid phone number", code = "phone")
            )
        }

        @Test
        fun `given a number from another country when the country code is 55 should report it`() {
            val node = ValidationNode(path = "phone", value = "+44 20 7946 0018").apply { phone(countryCode = "55") }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "phone", message = "must be a valid phone number", code = "phone")
            )
        }

        @Test
        fun `given an area code that does not exist in the country should report it`() {
            val node = ValidationNode(path = "phone", value = "(10) 99712-3931").apply {
                phone(countryCode = "55", format = PhoneFormat.AREA_CODE_AND_NUMBER)
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "phone", message = "must be a valid phone number", code = "phone")
            )
        }

        @Test
        fun `given the country and area codes from other fields should validate the whole number`() {
            val countryCode = "55"
            val areaCode = "11"
            val node = ValidationNode(path = "phoneNumber", value = "99712-3931").apply {
                phone(countryCode = countryCode, areaCode = areaCode)
            }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given an area code from another field that does not exist should report the number`() {
            val countryCode = "55"
            val areaCode = "10"
            val node = ValidationNode(path = "phoneNumber", value = "99712-3931").apply {
                phone(countryCode = countryCode, areaCode = areaCode)
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "phoneNumber", message = "must be a valid phone number", code = "phone")
            )
        }

        @ParameterizedTest
        @ValueSource(strings = ["+55 21 98765-4321", "(21) 98765-4321"])
        fun `given a number from another area than the area code from another field should report it`(
            phone: String
        ) {
            val node = ValidationNode(
                path = "phone",
                value = phone
            ).apply { phone(countryCode = "55", areaCode = "11") }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "phone", message = "must be a valid phone number", code = "phone")
            )
        }

        @Test
        fun `given a number with country code when only area code and number are allowed should report it`() {
            val node = ValidationNode(path = "phone", value = "+55 11 99712-3931").apply {
                phone(format = PhoneFormat.AREA_CODE_AND_NUMBER)
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "phone", message = "must be a valid phone number", code = "phone")
            )
        }

        @Test
        fun `given a number without country code when only complete numbers are allowed should report it`() {
            val node = ValidationNode(
                path = "phone",
                value = "(11) 99712-3931"
            ).apply { phone(format = PhoneFormat.COMPLETE) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "phone", message = "must be a valid phone number", code = "phone")
            )
        }

        @Test
        fun `given a message function should build it from the value and the parameters`() {
            val node = ValidationNode(path = "phone", value = "+44 20 7946 0018").apply {
                phone(countryCode = "55") { countryCode, _, _ -> "$this is not a phone from +${countryCode.orEmpty()}" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "phone", message = "+44 20 7946 0018 is not a phone from +55", code = "phone")
            )
        }

        @Test
        fun `given a message with the parameter placeholders should replace them`() {
            val node = ValidationNode(path = "phone", value = "1").apply {
                phone(
                    countryCode = "55",
                    format = PhoneFormat.COMPLETE,
                    message = "not a +{countryCode} {format} number"
                )
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "phone", message = "not a +55 COMPLETE number", code = "phone")
            )
        }
    }

    @Nested
    @DisplayName("When mobilePhone is called")
    inner class MobilePhone {
        @ParameterizedTest
        @ValueSource(strings = ["+55 11 99712-3931", "+44 7400 123456", "+1 415 555 2671"])
        fun `given a complete mobile number should accept it`(
            phone: String
        ) {
            val node = ValidationNode(path = "phone", value = phone).apply { mobilePhone() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a mobile number with area code when the country code is 55 should accept it`() {
            val node = ValidationNode(path = "phone", value = "(11) 99712-3931").apply {
                mobilePhone(countryCode = "55", format = PhoneFormat.AREA_CODE_AND_NUMBER)
            }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given the country and area codes from other fields should accept a mobile number`() {
            val countryCode = "55"
            val areaCode = "11"
            val node = ValidationNode(path = "phoneNumber", value = "99712-3931").apply {
                mobilePhone(countryCode = countryCode, areaCode = areaCode)
            }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a number without area code should accept it when its length fits a mobile number`() {
            val node = ValidationNode(
                path = "phoneNumber",
                value = "99712-3931"
            ).apply { mobilePhone(countryCode = "55") }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["+55 11 3333-4444", "+44 20 7946 0018"])
        fun `given a complete landline number should report it`(
            phone: String
        ) {
            val node = ValidationNode(path = "phone", value = phone).apply { mobilePhone() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "phone", message = "must be a valid mobile phone number", code = "mobilePhone")
            )
        }

        @Test
        fun `given a landline with area code when the country code is 55 should report it`() {
            val node = ValidationNode(path = "phone", value = "(11) 3333-4444").apply {
                mobilePhone(countryCode = "55", format = PhoneFormat.AREA_CODE_AND_NUMBER)
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "phone", message = "must be a valid mobile phone number", code = "mobilePhone")
            )
        }

        @Test
        fun `given the country and area codes from other fields should report a landline`() {
            val countryCode = "55"
            val areaCode = "11"
            val node = ValidationNode(path = "phoneNumber", value = "3333-4444").apply {
                mobilePhone(countryCode = countryCode, areaCode = areaCode)
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "phoneNumber", message = "must be a valid mobile phone number", code = "mobilePhone")
            )
        }

        @Test
        fun `given a contact whose mobile is a landline should report it`() {
            val contact = Contact(countryCode = "55", areaCode = "11", mobile = "3333-4444")

            val violations = contact.validate().violations

            violations shouldBe listOf(
                Violation(path = "mobile", message = "must be a valid mobile phone number", code = "mobilePhone")
            )
        }

        @Test
        fun `given a message function should build it from the value and the parameters`() {
            val node = ValidationNode(path = "phone", value = "+55 11 3333-4444").apply {
                mobilePhone(
                    countryCode = "55"
                ) { countryCode, _, _ -> "$this is not a mobile from +${countryCode.orEmpty()}" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "phone", message = "+55 11 3333-4444 is not a mobile from +55", code = "mobilePhone")
            )
        }
    }

    @Nested
    @DisplayName("When countryCode is called")
    inner class CountryCode {
        @ParameterizedTest
        @ValueSource(strings = ["55", "+55", "1", "44"])
        fun `given an existing country calling code should accept it`(
            countryCode: String
        ) {
            val node = ValidationNode(path = "countryCode", value = countryCode).apply { countryCode() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["999", "0", "BR", "", "++55", "+055", "055", "٥٥"])
        fun `given a country calling code that does not exist should report it`(
            countryCode: String
        ) {
            val node = ValidationNode(path = "countryCode", value = countryCode).apply { countryCode() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "countryCode", message = "must be a valid country calling code", code = "countryCode")
            )
        }
    }

    @Nested
    @DisplayName("When areaCode is called")
    inner class AreaCode {
        @ParameterizedTest
        @ValueSource(strings = ["11", "61", "21"])
        fun `given a Brazilian area code that exists should accept it`(
            areaCode: String
        ) {
            val node = ValidationNode(path = "areaCode", value = areaCode).apply { areaCode(countryCode = "55") }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["415", "212"])
        fun `given a North American area code that exists should accept it`(
            areaCode: String
        ) {
            val node = ValidationNode(path = "areaCode", value = areaCode).apply { areaCode(countryCode = "1") }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["20", "121", "17687"])
        fun `given an area code of a country whose area codes vary in length should accept up to 5 digits`(
            areaCode: String
        ) {
            val node = ValidationNode(path = "areaCode", value = areaCode).apply { areaCode(countryCode = "44") }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["11", "415"])
        fun `given an area code without country should accept it when it exists somewhere`(
            areaCode: String
        ) {
            val node = ValidationNode(path = "areaCode", value = areaCode).apply { areaCode() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["10", "20", "1", "111", "ab"])
        fun `given a Brazilian area code that does not exist should report it`(
            areaCode: String
        ) {
            val node = ValidationNode(path = "areaCode", value = areaCode).apply { areaCode(countryCode = "55") }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "areaCode", message = "must be a valid area code", code = "areaCode")
            )
        }

        @Test
        fun `given a North American area code that does not exist should report it`() {
            val node = ValidationNode(path = "areaCode", value = "999").apply { areaCode(countryCode = "1") }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "areaCode", message = "must be a valid area code", code = "areaCode")
            )
        }

        @ParameterizedTest
        @ValueSource(strings = ["123456", "2a"])
        fun `given an area code that is not up to 5 digits should report it`(
            areaCode: String
        ) {
            val node = ValidationNode(path = "areaCode", value = areaCode).apply { areaCode(countryCode = "44") }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "areaCode", message = "must be a valid area code", code = "areaCode")
            )
        }

        @ParameterizedTest
        @ValueSource(strings = ["44", "1"])
        fun `given an area code in non-ASCII digits should report it`(
            countryCode: String
        ) {
            val node = ValidationNode(path = "areaCode", value = "٢١٢").apply { areaCode(countryCode = countryCode) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "areaCode", message = "must be a valid area code", code = "areaCode")
            )
        }

        @Test
        fun `given a message function should build it from the value and the country code`() {
            val node = ValidationNode(path = "areaCode", value = "10").apply {
                areaCode(countryCode = "55") { countryCode -> "$this is not an area code of +${countryCode.orEmpty()}" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "areaCode", message = "10 is not an area code of +55", code = "areaCode")
            )
        }
    }
}
