package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class EncodingConstraintsTest {
    @Nested
    @DisplayName("When base64 is called")
    inner class Base64 {
        @ParameterizedTest
        @ValueSource(strings = ["SGVsbG8=", "SGVsbG8gV29ybGQ=", "YQ=="])
        fun `given a valid Base64 text should accept it`(content: String) {
            val node = ValidationNode("content", content).apply { base64() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["SGVsbG8", "SGVsbG8==", "SGV-bG8="])
        fun `given an invalid Base64 text should report it`(content: String) {
            val node = ValidationNode("content", content).apply { base64() }

            node.validate() shouldBe listOf(Violation("content", "must be valid Base64", "base64"))
        }
    }

    @Nested
    @DisplayName("When base64Url is called")
    inner class Base64Url {
        @ParameterizedTest
        @ValueSource(strings = ["SGVsbG8_LQ", "abc-_"])
        fun `given a valid URL-safe Base64 text should accept it`(content: String) {
            val node = ValidationNode("content", content).apply { base64Url() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["SGVsbG8+", "SGVsbG8/"])
        fun `given an invalid URL-safe Base64 text should report it`(content: String) {
            val node = ValidationNode("content", content).apply { base64Url() }

            node.validate() shouldBe listOf(Violation("content", "must be valid URL-safe Base64", "base64Url"))
        }
    }

    @Nested
    @DisplayName("When base32 is called")
    inner class Base32 {
        @ParameterizedTest
        @ValueSource(strings = ["JBSWY3DP", "JBSWY3DPEE======"])
        fun `given a valid Base32 text should accept it`(secret: String) {
            val node = ValidationNode("secret", secret).apply { base32() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["JBSWY3D1", "jbswy3dp", "JBSWY3DPE"])
        fun `given an invalid Base32 text should report it`(secret: String) {
            val node = ValidationNode("secret", secret).apply { base32() }

            node.validate() shouldBe listOf(Violation("secret", "must be valid Base32", "base32"))
        }
    }

    @Nested
    @DisplayName("When base58 is called")
    inner class Base58 {
        @ParameterizedTest
        @ValueSource(strings = ["3mJr7AoUXx2Wqd"])
        fun `given a valid Base58 text should accept it`(address: String) {
            val node = ValidationNode("address", address).apply { base58() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["3mJr7AoUXx2Wq0", "OIl"])
        fun `given an invalid Base58 text should report it`(address: String) {
            val node = ValidationNode("address", address).apply { base58() }

            node.validate() shouldBe listOf(Violation("address", "must be valid Base58", "base58"))
        }
    }

    @Nested
    @DisplayName("When hexadecimal is called")
    inner class Hexadecimal {
        @ParameterizedTest
        @ValueSource(strings = ["deadBEEF", "0123"])
        fun `given a valid hexadecimal text should accept it`(fingerprint: String) {
            val node = ValidationNode("fingerprint", fingerprint).apply { hexadecimal() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["0x1F", "xyz"])
        fun `given an invalid hexadecimal text should report it`(fingerprint: String) {
            val node = ValidationNode("fingerprint", fingerprint).apply { hexadecimal() }

            node.validate() shouldBe listOf(Violation("fingerprint", "must be hexadecimal", "hexadecimal"))
        }
    }

    @Nested
    @DisplayName("When json is called")
    inner class Json {
        @ParameterizedTest
        @ValueSource(
            strings = [
                "{\"name\":\"Ana\",\"tags\":[1,2.5,-3e2,true,null]}",
                "[]",
                "\"text\"",
                "42",
                " { } ",
                "{\"text\":\"line\\nbreak \\u00e9\"}"
            ]
        )
        fun `given a valid JSON should accept it`(payload: String) {
            val node = ValidationNode("payload", payload).apply { json() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(
            strings = [
                "{name: \"Ana\"}",
                "[1,2,]",
                "{\"a\":1,}",
                "\"unterminated",
                "01",
                "{\"a\" 1}",
                "tru",
                "{\"a\":1}}",
                "\"bad \\x escape\"",
                "\"tab\there\""
            ]
        )
        fun `given an invalid JSON should report it`(payload: String) {
            val node = ValidationNode("payload", payload).apply { json() }

            node.validate() shouldBe listOf(Violation("payload", "must be valid JSON", "json"))
        }
    }
}
