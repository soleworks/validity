package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import java.math.BigDecimal
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
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
}
