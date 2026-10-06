package io.github.soleworks.validity.constraints.sweden

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class SwedenConstraintsTest {
    @Nested
    @DisplayName("When personnummer is called")
    inner class Personnummer {
        @ParameterizedTest
        @ValueSource(strings = ["640823-3234", "19640823-3233", "196408233233", "200228+5266", "20180101-5581"])
        fun `given a valid personnummer should accept it`(value: String) {
            val node = ValidationNode("personnummer", value).apply { personnummer() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(
            strings = [
                "640823+3234",
                "160230-3231",
                "160260-3231",
                "160260-323",
                "160260323",
                "640823+323",
                "640823323",
                "640823+32344",
                "64082332344",
                "19640823-32333",
                "1964082332333"
            ]
        )
        fun `given an invalid personnummer should report it`(value: String) {
            val node = ValidationNode("personnummer", value).apply { personnummer() }

            node.validate() shouldBe listOf(Violation("personnummer", "must be a valid personnummer"))
        }
    }

    @Nested
    @DisplayName("When samordningsnummer is called")
    inner class Samordningsnummer {
        @ParameterizedTest
        @ValueSource(strings = ["640883-3231", "6408833231", "19640883-3230"])
        fun `given a valid samordningsnummer should accept it`(value: String) {
            val node = ValidationNode("samordningsnummer", value).apply { samordningsnummer() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["160260-3231", "160230-3231", "640823+323", "640823323"])
        fun `given an invalid samordningsnummer should report it`(value: String) {
            val node = ValidationNode("samordningsnummer", value).apply { samordningsnummer() }

            node.validate() shouldBe listOf(Violation("samordningsnummer", "must be a valid samordningsnummer"))
        }
    }
}
