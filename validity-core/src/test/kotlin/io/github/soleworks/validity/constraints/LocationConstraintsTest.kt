package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import java.math.BigDecimal
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource

class LocationConstraintsTest {
    @Nested
    @DisplayName("When isoCountryCode is called")
    inner class IsoCountryCode {
        @ParameterizedTest
        @ValueSource(strings = ["BR", "US"])
        fun `given a valid country code should accept it`(country: String) {
            val node = ValidationNode("country", country).apply { isoCountryCode() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["BRA", "XX", "br"])
        fun `given an invalid country code should report it`(country: String) {
            val node = ValidationNode("country", country).apply { isoCountryCode() }

            node.validate() shouldBe listOf(Violation("country", "must be a valid ISO 3166-1 alpha-2 country code", "isoCountryCode"))
        }
    }

    @Nested
    @DisplayName("When isoCountryCodeAlpha3 is called")
    inner class IsoCountryCodeAlpha3 {
        @ParameterizedTest
        @ValueSource(strings = ["BRA", "USA"])
        fun `given a valid country code should accept it`(country: String) {
            val node = ValidationNode("country", country).apply { isoCountryCodeAlpha3() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["BR", "XXX"])
        fun `given an invalid country code should report it`(country: String) {
            val node = ValidationNode("country", country).apply { isoCountryCodeAlpha3() }

            node.validate() shouldBe listOf(Violation("country", "must be a valid ISO 3166-1 alpha-3 country code", "isoCountryCodeAlpha3"))
        }
    }

    @Nested
    @DisplayName("When languageCode is called")
    inner class LanguageCode {
        @ParameterizedTest
        @ValueSource(strings = ["pt", "en"])
        fun `given a valid language code should accept it`(language: String) {
            val node = ValidationNode("language", language).apply { languageCode() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["por", "xx", "PT"])
        fun `given an invalid language code should report it`(language: String) {
            val node = ValidationNode("language", language).apply { languageCode() }

            node.validate() shouldBe listOf(Violation("language", "must be a valid ISO 639-1 language code", "languageCode"))
        }
    }

    @Nested
    @DisplayName("When locale is called")
    inner class Locale {
        @ParameterizedTest
        @ValueSource(strings = ["pt-BR", "en", "zh-Hant-TW"])
        fun `given a valid locale should accept it`(locale: String) {
            val node = ValidationNode("locale", locale).apply { locale() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["pt_BR", "", "pt-BR-"])
        fun `given an invalid locale should report it`(locale: String) {
            val node = ValidationNode("locale", locale).apply { locale() }

            node.validate() shouldBe listOf(Violation("locale", "must be a valid locale", "locale"))
        }
    }

    @Nested
    @DisplayName("When timeZone is called")
    inner class TimeZone {
        @ParameterizedTest
        @ValueSource(strings = ["America/Sao_Paulo", "UTC", "Europe/Lisbon"])
        fun `given a valid time zone should accept it`(timeZone: String) {
            val node = ValidationNode("timeZone", timeZone).apply { timeZone() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["America/Sao Paulo", "Mars/Olympus"])
        fun `given an invalid time zone should report it`(timeZone: String) {
            val node = ValidationNode("timeZone", timeZone).apply { timeZone() }

            node.validate() shouldBe listOf(Violation("timeZone", "must be a valid time zone", "timeZone"))
        }
    }

    @Nested
    @DisplayName("When latitude is called")
    inner class Latitude {
        @ParameterizedTest
        @ValueSource(doubles = [-90.0, 0.0, 90.0])
        fun `given a valid latitude as Double should accept it`(latitude: Double) {
            val node = ValidationNode("latitude", latitude).apply { latitude() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(doubles = [-90.1, 90.1, Double.NaN])
        fun `given an invalid latitude as Double should report it`(latitude: Double) {
            val node = ValidationNode("latitude", latitude).apply { latitude() }

            node.validate() shouldBe listOf(Violation("latitude", "must be a valid latitude", "latitude"))
        }

        @ParameterizedTest
        @ValueSource(floats = [45f])
        fun `given a valid latitude as Float should accept it`(latitude: Float) {
            val node = ValidationNode("latitude", latitude).apply { latitude() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(floats = [91f])
        fun `given an invalid latitude as Float should report it`(latitude: Float) {
            val node = ValidationNode("latitude", latitude).apply { latitude() }

            node.validate() shouldBe listOf(Violation("latitude", "must be a valid latitude", "latitude"))
        }

        @ParameterizedTest
        @ValueSource(strings = ["-23.5505"])
        fun `given a valid latitude as BigDecimal should accept it`(latitude: String) {
            val node = ValidationNode("latitude", BigDecimal(latitude)).apply { latitude() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["90.0001"])
        fun `given an invalid latitude as BigDecimal should report it`(latitude: String) {
            val node = ValidationNode("latitude", BigDecimal(latitude)).apply { latitude() }

            node.validate() shouldBe listOf(Violation("latitude", "must be a valid latitude", "latitude"))
        }

        @ParameterizedTest
        @ValueSource(strings = ["-23.5505", "+45"])
        fun `given a valid latitude as String should accept it`(latitude: String) {
            val node = ValidationNode("latitude", latitude).apply { latitude() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["91", "abc", "1e2"])
        fun `given an invalid latitude as String should report it`(latitude: String) {
            val node = ValidationNode("latitude", latitude).apply { latitude() }

            node.validate() shouldBe listOf(Violation("latitude", "must be a valid latitude", "latitude"))
        }
    }

    @Nested
    @DisplayName("When longitude is called")
    inner class Longitude {
        @ParameterizedTest
        @ValueSource(doubles = [-180.0, 0.0, 180.0])
        fun `given a valid longitude as Double should accept it`(longitude: Double) {
            val node = ValidationNode("longitude", longitude).apply { longitude() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(doubles = [-180.1, 180.1, Double.NaN])
        fun `given an invalid longitude as Double should report it`(longitude: Double) {
            val node = ValidationNode("longitude", longitude).apply { longitude() }

            node.validate() shouldBe listOf(Violation("longitude", "must be a valid longitude", "longitude"))
        }

        @ParameterizedTest
        @ValueSource(floats = [120f])
        fun `given a valid longitude as Float should accept it`(longitude: Float) {
            val node = ValidationNode("longitude", longitude).apply { longitude() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(floats = [181f])
        fun `given an invalid longitude as Float should report it`(longitude: Float) {
            val node = ValidationNode("longitude", longitude).apply { longitude() }

            node.validate() shouldBe listOf(Violation("longitude", "must be a valid longitude", "longitude"))
        }

        @ParameterizedTest
        @ValueSource(strings = ["-46.6333"])
        fun `given a valid longitude as BigDecimal should accept it`(longitude: String) {
            val node = ValidationNode("longitude", BigDecimal(longitude)).apply { longitude() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["180.0001"])
        fun `given an invalid longitude as BigDecimal should report it`(longitude: String) {
            val node = ValidationNode("longitude", BigDecimal(longitude)).apply { longitude() }

            node.validate() shouldBe listOf(Violation("longitude", "must be a valid longitude", "longitude"))
        }

        @ParameterizedTest
        @ValueSource(strings = ["-46.6333", "+120"])
        fun `given a valid longitude as String should accept it`(longitude: String) {
            val node = ValidationNode("longitude", longitude).apply { longitude() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["181", "abc", "1e2"])
        fun `given an invalid longitude as String should report it`(longitude: String) {
            val node = ValidationNode("longitude", longitude).apply { longitude() }

            node.validate() shouldBe listOf(Violation("longitude", "must be a valid longitude", "longitude"))
        }
    }

    @Nested
    @DisplayName("When postalCode is called")
    inner class PostalCode {
        @ParameterizedTest
        @CsvSource(
            value = [
                "AR,'C1000WAM'",
                "AR,'B1900ABC'",
                "AR,'X5000XYZ'",
                "AR,'c1425cla'",
                "AR,'1000'",
                "AR,'9120'",
                "AU,'4000'",
                "AU,'2620'",
                "AU,'3000'",
                "AU,'2017'",
                "AU,'0800'",
                "BD,'1000'",
                "BD,'1200'",
                "BD,'1300'",
                "BD,'1400'",
                "BD,'1500'",
                "BD,'2000'",
                "BD,'3000'",
                "BD,'4000'",
                "BD,'5000'",
                "BD,'6000'",
                "BD,'7000'",
                "BD,'8000'",
                "BD,'9000'",
                "BD,'9400'",
                "BD,'9499'",
                "BY,'225320'",
                "BY,'211120'",
                "BY,'247710'",
                "BY,'231960'",
                "CA,'L4T 0A5'",
                "CA,'G1A-0A2'",
                "CA,'A1A 1A1'",
                "CA,'X0A-0H0'",
                "CA,'V5K 0A1'",
                "CA,'A1C 3S4'",
                "CA,'A1C3S4'",
                "CA,'a1c 3s4'",
                "CA,'V9A 7N2'",
                "CA,'B3K 5X5'",
                "CA,'K8N 5W6'",
                "CA,'K1A 0B1'",
                "CA,'B1Z 0B9'",
                "CO,'050034'",
                "CO,'110221'",
                "CO,'441029'",
                "CO,'910001'",
                "ES,'01001'",
                "ES,'52999'",
                "ES,'27880'",
                "JP,'135-0000'",
                "JP,'874-8577'",
                "JP,'669-1161'",
                "JP,'470-0156'",
                "JP,'672-8031'",
                "GR,'022 93'",
                "GR,'29934'",
                "GR,'90293'",
                "GR,'299 42'",
                "GR,'94944'",
                "GB,'TW8 9GS'",
                "GB,'BS98 1TL'",
                "GB,'DE99 3GG'",
                "GB,'DE55 4SW'",
                "GB,'DH98 1BT'",
                "GB,'DH99 1NS'",
                "GB,'GIR0aa'",
                "GB,'SA99'",
                "GB,'W1N 4DJ'",
                "GB,'AA9A 9AA'",
                "GB,'AA99 9AA'",
                "GB,'BS98 1TL'",
                "GB,'DE993GG'",
                "FR,'75008'",
                "FR,'44522'",
                "FR,'38499'",
                "FR,'39940'",
                "FR,'01000'",
                "ID,'10210'",
                "ID,'40181'",
                "ID,'55161'",
                "ID,'60233'",
                "IE,'A65 TF12'",
                "IE,'A6W U9U9'",
                "IN,'364240'",
                "IN,'360005'",
                "IL,'10200'",
                "IL,'10292'",
                "IL,'10300'",
                "IL,'10329'",
                "IL,'3885500'",
                "IL,'4290500'",
                "IL,'4286000'",
                "IL,'7080000'",
                "BG,'1000'",
                "IR,'4351666456'",
                "IR,'5614736867'",
                "CZ,'20134'",
                "CZ,'392 90'",
                "CZ,'39919'",
                "CZ,'938 29'",
                "CZ,'39949'",
                "NL,'1012 SZ'",
                "NL,'3432FE'",
                "NL,'1118 BH'",
                "NL,'3950IO'",
                "NL,'3997 GH'",
                "NP,'10811'",
                "NP,'32600'",
                "NP,'56806'",
                "NP,'977'",
                "PL,'47-260'",
                "PL,'12-930'",
                "PL,'78-399'",
                "PL,'39-490'",
                "PL,'38-483'",
                "PL,'05-800'",
                "PL,'54-060'",
                "TW,'360'",
                "TW,'90312'",
                "TW,'399'",
                "TW,'935'",
                "TW,'38842'",
                "TW,'546023'",
                "LI,'9485'",
                "LI,'9497'",
                "LI,'9491'",
                "LI,'9489'",
                "LI,'9496'",
                "PT,'4829-489'",
                "PT,'0294-348'",
                "PT,'8156-392'",
                "SE,'12994'",
                "SE,'284 39'",
                "SE,'39556'",
                "SE,'489 39'",
                "SE,'499 49'",
                "AD,'AD100'",
                "AD,'AD200'",
                "AD,'AD300'",
                "AD,'AD400'",
                "AD,'AD500'",
                "AD,'AD600'",
                "AD,'AD700'",
                "UA,'65000'",
                "UA,'65080'",
                "UA,'01000'",
                "UA,'51901'",
                "UA,'51909'",
                "UA,'49125'",
                "BR,'39100-000'",
                "BR,'22040-020'",
                "BR,'39400-152'",
                "BR,'39100000'",
                "BR,'22040020'",
                "BR,'39400152'",
                "NZ,'7843'",
                "NZ,'3581'",
                "NZ,'0449'",
                "NZ,'0984'",
                "NZ,'4144'",
                "PK,'25000'",
                "PK,'44000'",
                "PK,'54810'",
                "PK,'74200'",
                "JO,'11110'",
                "JO,'11937'",
                "JO,'21110'",
                "JO,'77110'",
                "MG,'101'",
                "MG,'303'",
                "MG,'407'",
                "MG,'512'",
                "MT,'VLT2345'",
                "MT,'VLT 2345'",
                "MT,'ATD1234'",
                "MT,'MSK8723'",
                "MY,'56000'",
                "MY,'12000'",
                "MY,'79502'",
                "PR,'00979'",
                "PR,'00631'",
                "PR,'00786'",
                "PR,'00987'",
                "AZ,'AZ0100'",
                "AZ,'AZ0121'",
                "AZ,'AZ3500'",
                "DO,'12345'",
                "HT,'HT1234'",
                "TH,'10250'",
                "TH,'72170'",
                "TH,'12140'",
                "SG,'308215'",
                "SG,'546080'",
                "CN,'150237'",
                "CN,'100000'",
                "KR,'17008'",
                "KR,'339012'",
                "LK,'11500'",
                "LK,'22200'",
                "LK,'10370'",
                "LK,'43000'",
                "BA,'76300'",
                "BA,'71000'",
                "BA,'75412'",
                "BA,'76100'",
                "BA,'88202'",
                "BA,'88313'",
                "MC,'98000'",
                "MC,'98025'",
                "AT,'1010'",
                "BE,'1000'",
                "CH,'8001'",
                "DE,'10115'",
                "DK,'1050'",
                "DZ,'16000'",
                "EE,'10111'",
                "FI,'00100'",
                "HR,'10000'",
                "HU,'1051'",
                "IS,'101'",
                "IT,'00118'",
                "KE,'00100'",
                "LT,'LT-01100'",
                "LU,'1111'",
                "LV,'LV-1050'",
                "MX,'06600'",
                "NO,'0150'",
                "RO,'010011'",
                "RU,'101000'",
                "SA,'11564'",
                "SI,'1000'",
                "SK,'811 01'",
                "SK,'81101'",
                "TN,'1000'",
                "US,'90210'",
                "US,'10001-1234'",
                "ZA,'2000'",
                "ZM,'10101'"
            ]
        )
        fun `given a postal code in the format of its country should accept it`(country: String, postalCode: String) {
            val node = ValidationNode("postalCode", postalCode).apply { postalCode(country) }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @CsvSource(
            value = [
                "AR,'C1000'",
                "AR,'C1000WA'",
                "AR,'C1000WAMZ'",
                "AR,'I1000ABC'",
                "AR,'O1000ABC'",
                "AR,'C1000 ABC'",
                "AR,'0123'",
                "AR,'123'",
                "AR,'12345'",
                "BD,'0999'",
                "BD,'9500'",
                "BD,'10000'",
                "BD,'12345'",
                "BD,'123'",
                "BD,'123456'",
                "BD,'abcd'",
                "BD,'123a'",
                "BD,'a123'",
                "BD,'12 34'",
                "BD,'12-34'",
                "BY,'test 225320'",
                "BY,'211120 test'",
                "BY,'317543'",
                "BY,'267946'",
                "CA,'        '",
                "CA,'invalid value'",
                "CA,'a1a1a'",
                "CA,'A1A  1A1'",
                "CA,'K1A 0D1'",
                "CA,'W1A 0B1'",
                "CA,'Z1A 0B1'",
                "CO,'11001'",
                "CO,'000000'",
                "CO,'109999'",
                "CO,'329999'",
                "ES,'123'",
                "ES,'1234'",
                "ES,'53000'",
                "ES,'052999'",
                "ES,'0123'",
                "ES,'abcde'",
                "FR,'44 522'",
                "FR,'38 499'",
                "FR,'96000'",
                "FR,'98025'",
                "IE,'123'",
                "IE,'75690HG'",
                "IE,'AW5  TF12'",
                "IE,'AW5 TF12'",
                "IE,'756  90HG'",
                "IE,'A65T F12'",
                "IE,'O62 O1O2'",
                "IN,'123'",
                "IN,'012345'",
                "IN,'011111'",
                "IN,'101123'",
                "IN,'291123'",
                "IN,'351123'",
                "IN,'541123'",
                "IN,'551123'",
                "IN,'651123'",
                "IN,'661123'",
                "IN,'861123'",
                "IN,'871123'",
                "IN,'881123'",
                "IN,'891123'",
                "IL,'123'",
                "IL,'012345'",
                "IL,'011111'",
                "IL,'101123'",
                "IL,'291123'",
                "IL,'351123'",
                "IL,'541123'",
                "IL,'551123'",
                "IL,'651123'",
                "IL,'661123'",
                "IL,'861123'",
                "IL,'871123'",
                "IL,'881123'",
                "IL,'891123'",
                "IR,'43516 6456'",
                "IR,'123443516 6456'",
                "IR,'891123'",
                "IR,'test 4351666456'",
                "IR,'4351666456 test'",
                "IR,'test 4351666456 test'",
                "NL,'1234'",
                "NL,'0603 JV'",
                "NL,'5194SA'",
                "NL,'9164 SD'",
                "NL,'1841SS'",
                "NP,'11977'",
                "NP,'asds'",
                "NP,'13 32'",
                "NP,'-977'",
                "NP,'97765'",
                "BR,'79800A12'",
                "BR,'13165-00'",
                "BR,'38175-abc'",
                "BR,'81470-2763'",
                "BR,'78908'",
                "BR,'13010|111'",
                "PK,'5400'",
                "PK,'540000'",
                "PK,'NY540'",
                "PK,'540CA'",
                "PK,'540-0'",
                "JO,'1234'",
                "JO,'123456'",
                "JO,'abcd'",
                "JO,'1111A'",
                "JO,'11 110'",
                "JO,'11-110'",
                "AZ,''",
                "AZ,' AZ0100'",
                "AZ,'AZ100'",
                "AZ,'AZ34340'",
                "AZ,'EN2020'",
                "AZ,'AY3030'",
                "DO,'A1234'",
                "DO,'123'",
                "DO,'123456'",
                "HT,'HT123'",
                "HT,'HT12345'",
                "HT,'AA1234'",
                "TH,'T1025'",
                "TH,'T72170'",
                "TH,'12140TH'",
                "CN,'141234'",
                "CN,'386789'",
                "CN,'ab1234'",
                "KR,'1412347'",
                "KR,'ab1234'",
                "LK,'1234'",
                "LK,'789389'",
                "LK,'982'",
                "BA,'1234'",
                "BA,'789389'",
                "BA,'98212'",
                "BA,'11000'",
                "MC,'123412'",
                "MC,'ab1234'",
                "US,'9021'",
                "US,'90210-12'",
                "LT,'01100'",
                "LV,'1050'",
                "SK,'8110'",
                "DE,'1011'"
            ]
        )
        fun `given a postal code out of the format of its country should report it`(country: String, postalCode: String) {
            val node = ValidationNode("postalCode", postalCode).apply { postalCode(country) }

            node.validate() shouldBe listOf(
                Violation("postalCode", "must be a valid postal code for $country", "postalCode")
            )
        }

        @Test
        fun `given a country in lowercase should use the format of that country`() {
            val node = ValidationNode("postalCode", "1234").apply { postalCode("br") }

            node.validate() shouldBe listOf(Violation("postalCode", "must be a valid postal code for br", "postalCode"))
        }

        @Test
        fun `given no country should skip the check`() {
            val node = ValidationNode("postalCode", "anything").apply { postalCode(null) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a country without a known format should skip the check`() {
            val node = ValidationNode("postalCode", "anything").apply { postalCode("PE") }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function should build it from the value and the country`() {
            val node = ValidationNode("postalCode", "1234").apply {
                postalCode("BR") { country -> "$this is not a postal code of $country" }
            }

            node.validate() shouldBe listOf(Violation("postalCode", "1234 is not a postal code of BR", "postalCode"))
        }
    }
}
