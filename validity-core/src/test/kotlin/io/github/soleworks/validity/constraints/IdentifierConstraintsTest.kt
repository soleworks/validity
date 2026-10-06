package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class IdentifierConstraintsTest {
    @Nested
    @DisplayName("When uuid is called")
    inner class Uuid {
        @ParameterizedTest
        @ValueSource(strings = ["123e4567-e89b-12d3-a456-426614174000", "00000000-0000-0000-0000-000000000000"])
        fun `given a valid UUID should accept it`(id: String) {
            val node = ValidationNode("id", id).apply { uuid() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["123e4567e89b12d3a456426614174000", "123e4567-e89b-12d3-a456-42661417400Z"])
        fun `given an invalid UUID should report it`(id: String) {
            val node = ValidationNode("id", id).apply { uuid() }

            node.validate() shouldBe listOf(Violation("id", "must be a valid UUID"))
        }

        @Test
        fun `given a UUID of the expected version should accept it`() {
            val node = ValidationNode("id", "9b2c5d3e-1f4a-4c8b-9d7e-6a5b4c3d2e1f").apply { uuid(4) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a UUID of another version should report it`() {
            val node = ValidationNode("id", "123e4567-e89b-12d3-a456-426614174000").apply { uuid(4) }

            node.validate() shouldBe listOf(Violation("id", "must be a valid UUID"))
        }

        @Test
        fun `given a message function should build it from the value and the version`() {
            val node = ValidationNode("id", "123e4567-e89b-12d3-a456-426614174000").apply {
                uuid(4) { version -> "$this is not a version $version UUID" }
            }

            node.validate() shouldBe listOf(
                Violation("id", "123e4567-e89b-12d3-a456-426614174000 is not a version 4 UUID")
            )
        }
    }

    @Nested
    @DisplayName("When ulid is called")
    inner class Ulid {
        @ParameterizedTest
        @ValueSource(strings = ["01ARZ3NDEKTSV4RRFFQ69G5FAV"])
        fun `given a valid ULID should accept it`(id: String) {
            val node = ValidationNode("id", id).apply { ulid() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(
            strings = [
                "01ARZ3NDEKTSV4RRFFQ69G5FA",
                "81ARZ3NDEKTSV4RRFFQ69G5FAV",
                "01ARZ3NDEKTSV4RRFFQ69G5FAU"
            ]
        )
        fun `given an invalid ULID should report it`(id: String) {
            val node = ValidationNode("id", id).apply { ulid() }

            node.validate() shouldBe listOf(Violation("id", "must be a valid ULID"))
        }
    }

    @Nested
    @DisplayName("When objectId is called")
    inner class ObjectId {
        @ParameterizedTest
        @ValueSource(strings = ["507f1f77bcf86cd799439011"])
        fun `given a valid ObjectId should accept it`(id: String) {
            val node = ValidationNode("id", id).apply { objectId() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["507f1f77bcf86cd79943901", "507f1f77bcf86cd79943901z"])
        fun `given an invalid ObjectId should report it`(id: String) {
            val node = ValidationNode("id", id).apply { objectId() }

            node.validate() shouldBe listOf(Violation("id", "must be a valid ObjectId"))
        }
    }

    @Nested
    @DisplayName("When semver is called")
    inner class Semver {
        @ParameterizedTest
        @ValueSource(strings = ["1.0.0", "1.2.3-alpha.1", "1.0.0+build.5", "0.0.0"])
        fun `given a valid semantic version should accept it`(version: String) {
            val node = ValidationNode("version", version).apply { semver() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["1.0", "01.0.0", "1.0.0-", "v1.0.0"])
        fun `given an invalid semantic version should report it`(version: String) {
            val node = ValidationNode("version", version).apply { semver() }

            node.validate() shouldBe listOf(Violation("version", "must be a valid semantic version"))
        }
    }

    @Nested
    @DisplayName("When isbn is called")
    inner class Isbn {
        @ParameterizedTest
        @ValueSource(strings = ["0306406152", "9780306406157"])
        fun `given a valid ISBN should accept it`(isbn: String) {
            val node = ValidationNode("isbn", isbn).apply { isbn() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["0306406153", "1234567890128"])
        fun `given an invalid ISBN should report it`(isbn: String) {
            val node = ValidationNode("isbn", isbn).apply { isbn() }

            node.validate() shouldBe listOf(Violation("isbn", "must be a valid ISBN"))
        }
    }

    @Nested
    @DisplayName("When isbn10 is called")
    inner class Isbn10 {
        @ParameterizedTest
        @ValueSource(strings = ["0306406152", "080442957X", "0-306-40615-2"])
        fun `given a valid ISBN-10 should accept it`(isbn: String) {
            val node = ValidationNode("isbn", isbn).apply { isbn10() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["0306406153", "9780306406157", "12345"])
        fun `given an invalid ISBN-10 should report it`(isbn: String) {
            val node = ValidationNode("isbn", isbn).apply { isbn10() }

            node.validate() shouldBe listOf(Violation("isbn", "must be a valid ISBN-10"))
        }
    }

    @Nested
    @DisplayName("When isbn13 is called")
    inner class Isbn13 {
        @ParameterizedTest
        @ValueSource(strings = ["9780306406157", "978-0-306-40615-7"])
        fun `given a valid ISBN-13 should accept it`(isbn: String) {
            val node = ValidationNode("isbn", isbn).apply { isbn13() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["9780306406158", "1234567890128", "0306406152"])
        fun `given an invalid ISBN-13 should report it`(isbn: String) {
            val node = ValidationNode("isbn", isbn).apply { isbn13() }

            node.validate() shouldBe listOf(Violation("isbn", "must be a valid ISBN-13"))
        }
    }

    @Nested
    @DisplayName("When issn is called")
    inner class Issn {
        @ParameterizedTest
        @ValueSource(strings = ["0317-8471", "03178471", "2434-561X"])
        fun `given a valid ISSN should accept it`(issn: String) {
            val node = ValidationNode("issn", issn).apply { issn() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["0317-8472", "317-8471", "0317-847"])
        fun `given an invalid ISSN should report it`(issn: String) {
            val node = ValidationNode("issn", issn).apply { issn() }

            node.validate() shouldBe listOf(Violation("issn", "must be a valid ISSN"))
        }
    }

    @Nested
    @DisplayName("When ean is called")
    inner class Ean {
        @ParameterizedTest
        @ValueSource(strings = ["96385074", "4006381333931", "1234567890128"])
        fun `given a valid EAN should accept it`(barcode: String) {
            val node = ValidationNode("barcode", barcode).apply { ean() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["96385075", "123456789"])
        fun `given an invalid EAN should report it`(barcode: String) {
            val node = ValidationNode("barcode", barcode).apply { ean() }

            node.validate() shouldBe listOf(Violation("barcode", "must be a valid EAN"))
        }
    }

    @Nested
    @DisplayName("When isrc is called")
    inner class Isrc {
        @ParameterizedTest
        @ValueSource(strings = ["USRC17607839"])
        fun `given a valid ISRC should accept it`(isrc: String) {
            val node = ValidationNode("isrc", isrc).apply { isrc() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["USRC1760783", "usrc17607839"])
        fun `given an invalid ISRC should report it`(isrc: String) {
            val node = ValidationNode("isrc", isrc).apply { isrc() }

            node.validate() shouldBe listOf(Violation("isrc", "must be a valid ISRC"))
        }
    }

    @Nested
    @DisplayName("When imei is called")
    inner class Imei {
        @ParameterizedTest
        @ValueSource(strings = ["490154203237518"])
        fun `given a valid IMEI should accept it`(imei: String) {
            val node = ValidationNode("imei", imei).apply { imei() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["490154203237519", "49015420323751"])
        fun `given an invalid IMEI should report it`(imei: String) {
            val node = ValidationNode("imei", imei).apply { imei() }

            node.validate() shouldBe listOf(Violation("imei", "must be a valid IMEI"))
        }
    }

    @Nested
    @DisplayName("When luhn is called")
    inner class Luhn {
        @ParameterizedTest
        @ValueSource(strings = ["79927398713"])
        fun `given a valid Luhn number should accept it`(number: String) {
            val node = ValidationNode("number", number).apply { luhn() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["79927398710", "7992739871a"])
        fun `given an invalid Luhn number should report it`(number: String) {
            val node = ValidationNode("number", number).apply { luhn() }

            node.validate() shouldBe listOf(Violation("number", "must have a valid Luhn check digit"))
        }
    }

    @Nested
    @DisplayName("When hash is called")
    inner class Hash {
        @ParameterizedTest
        @ValueSource(strings = ["e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855"])
        fun `given a valid SHA-256 hash should accept it`(checksum: String) {
            val node = ValidationNode("checksum", checksum).apply { hash(HashAlgorithm.SHA256) }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(
            strings = [
                "d41d8cd98f00b204e9800998ecf8427e",
                "z3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855"
            ]
        )
        fun `given an invalid SHA-256 hash should report it`(checksum: String) {
            val node = ValidationNode("checksum", checksum).apply { hash(HashAlgorithm.SHA256) }

            node.validate() shouldBe listOf(Violation("checksum", "must be a valid SHA256 hash"))
        }

        @Test
        fun `given a message function should build it from the value and the algorithm`() {
            val node = ValidationNode("checksum", "abc").apply {
                hash(HashAlgorithm.MD5) { algorithm -> "$this is not $algorithm" }
            }

            node.validate() shouldBe listOf(Violation("checksum", "abc is not MD5"))
        }
    }
}
