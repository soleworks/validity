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
        fun `given a valid UUID should accept it`(
            id: String
        ) {
            val node = ValidationNode(path = "id", value = id).apply { uuid() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a UUID of the expected version should accept it`() {
            val node = ValidationNode(path = "id", value = "9b2c5d3e-1f4a-4c8b-9d7e-6a5b4c3d2e1f").apply { uuid(4) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["123e4567e89b12d3a456426614174000", "123e4567-e89b-12d3-a456-42661417400Z"])
        fun `given an invalid UUID should report it`(
            id: String
        ) {
            val node = ValidationNode(path = "id", value = id).apply { uuid() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "id", message = "must be a valid UUID", code = "uuid")
            )
        }

        @Test
        fun `given a UUID of another version should report it`() {
            val node = ValidationNode(path = "id", value = "123e4567-e89b-12d3-a456-426614174000").apply { uuid(4) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "id", message = "must be a valid UUID", code = "uuid")
            )
        }

        @Test
        fun `given a message function should build it from the value and the version`() {
            val node = ValidationNode(path = "id", value = "123e4567-e89b-12d3-a456-426614174000").apply {
                uuid(4) { version -> "$this is not a version ${version ?: "any"} UUID" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(
                    path = "id",
                    message = "123e4567-e89b-12d3-a456-426614174000 is not a version 4 UUID",
                    code = "uuid"
                )
            )
        }
    }

    @Nested
    @DisplayName("When ulid is called")
    inner class Ulid {
        @ParameterizedTest
        @ValueSource(strings = ["01ARZ3NDEKTSV4RRFFQ69G5FAV"])
        fun `given a valid ULID should accept it`(
            id: String
        ) {
            val node = ValidationNode(path = "id", value = id).apply { ulid() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(
            strings = [
                "01ARZ3NDEKTSV4RRFFQ69G5FA",
                "81ARZ3NDEKTSV4RRFFQ69G5FAV",
                "01ARZ3NDEKTSV4RRFFQ69G5FAU"
            ]
        )
        fun `given an invalid ULID should report it`(
            id: String
        ) {
            val node = ValidationNode(path = "id", value = id).apply { ulid() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "id", message = "must be a valid ULID", code = "ulid")
            )
        }
    }

    @Nested
    @DisplayName("When objectId is called")
    inner class ObjectId {
        @ParameterizedTest
        @ValueSource(strings = ["507f1f77bcf86cd799439011"])
        fun `given a valid ObjectId should accept it`(
            id: String
        ) {
            val node = ValidationNode(path = "id", value = id).apply { objectId() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["507f1f77bcf86cd79943901", "507f1f77bcf86cd79943901z"])
        fun `given an invalid ObjectId should report it`(
            id: String
        ) {
            val node = ValidationNode(path = "id", value = id).apply { objectId() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "id", message = "must be a valid ObjectId", code = "objectId")
            )
        }
    }

    @Nested
    @DisplayName("When semver is called")
    inner class Semver {
        @ParameterizedTest
        @ValueSource(strings = ["1.0.0", "1.2.3-alpha.1", "1.0.0+build.5", "0.0.0"])
        fun `given a valid semantic version should accept it`(
            version: String
        ) {
            val node = ValidationNode(path = "version", value = version).apply { semver() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["1.0", "01.0.0", "1.0.0-", "v1.0.0"])
        fun `given an invalid semantic version should report it`(
            version: String
        ) {
            val node = ValidationNode(path = "version", value = version).apply { semver() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "version", message = "must be a valid semantic version", code = "semver")
            )
        }
    }

    @Nested
    @DisplayName("When isbn is called")
    inner class Isbn {
        @ParameterizedTest
        @ValueSource(strings = ["0306406152", "9780306406157"])
        fun `given a valid ISBN should accept it`(
            isbn: String
        ) {
            val node = ValidationNode(path = "isbn", value = isbn).apply { isbn() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["0306406153", "1234567890128"])
        fun `given an invalid ISBN should report it`(
            isbn: String
        ) {
            val node = ValidationNode(path = "isbn", value = isbn).apply { isbn() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "isbn", message = "must be a valid ISBN", code = "isbn")
            )
        }
    }

    @Nested
    @DisplayName("When isbn10 is called")
    inner class Isbn10 {
        @ParameterizedTest
        @ValueSource(strings = ["0306406152", "080442957X", "0-306-40615-2"])
        fun `given a valid ISBN-10 should accept it`(
            isbn: String
        ) {
            val node = ValidationNode(path = "isbn", value = isbn).apply { isbn10() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["0306406153", "9780306406157", "12345"])
        fun `given an invalid ISBN-10 should report it`(
            isbn: String
        ) {
            val node = ValidationNode(path = "isbn", value = isbn).apply { isbn10() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "isbn", message = "must be a valid ISBN-10", code = "isbn10")
            )
        }
    }

    @Nested
    @DisplayName("When isbn13 is called")
    inner class Isbn13 {
        @ParameterizedTest
        @ValueSource(strings = ["9780306406157", "978-0-306-40615-7"])
        fun `given a valid ISBN-13 should accept it`(
            isbn: String
        ) {
            val node = ValidationNode(path = "isbn", value = isbn).apply { isbn13() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["9780306406158", "1234567890128", "0306406152"])
        fun `given an invalid ISBN-13 should report it`(
            isbn: String
        ) {
            val node = ValidationNode(path = "isbn", value = isbn).apply { isbn13() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "isbn", message = "must be a valid ISBN-13", code = "isbn13")
            )
        }
    }

    @Nested
    @DisplayName("When issn is called")
    inner class Issn {
        @ParameterizedTest
        @ValueSource(strings = ["0317-8471", "03178471", "2434-561X"])
        fun `given a valid ISSN should accept it`(
            issn: String
        ) {
            val node = ValidationNode(path = "issn", value = issn).apply { issn() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["0317-8472", "317-8471", "0317-847"])
        fun `given an invalid ISSN should report it`(
            issn: String
        ) {
            val node = ValidationNode(path = "issn", value = issn).apply { issn() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "issn", message = "must be a valid ISSN", code = "issn")
            )
        }
    }

    @Nested
    @DisplayName("When ean is called")
    inner class Ean {
        @ParameterizedTest
        @ValueSource(strings = ["96385074", "4006381333931", "1234567890128"])
        fun `given a valid EAN should accept it`(
            barcode: String
        ) {
            val node = ValidationNode(path = "barcode", value = barcode).apply { ean() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["96385075", "123456789"])
        fun `given an invalid EAN should report it`(
            barcode: String
        ) {
            val node = ValidationNode(path = "barcode", value = barcode).apply { ean() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "barcode", message = "must be a valid EAN", code = "ean")
            )
        }
    }

    @Nested
    @DisplayName("When isrc is called")
    inner class Isrc {
        @ParameterizedTest
        @ValueSource(strings = ["USRC17607839"])
        fun `given a valid ISRC should accept it`(
            isrc: String
        ) {
            val node = ValidationNode(path = "isrc", value = isrc).apply { isrc() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["USRC1760783", "usrc17607839"])
        fun `given an invalid ISRC should report it`(
            isrc: String
        ) {
            val node = ValidationNode(path = "isrc", value = isrc).apply { isrc() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "isrc", message = "must be a valid ISRC", code = "isrc")
            )
        }
    }

    @Nested
    @DisplayName("When imei is called")
    inner class Imei {
        @ParameterizedTest
        @ValueSource(strings = ["490154203237518"])
        fun `given a valid IMEI should accept it`(
            imei: String
        ) {
            val node = ValidationNode(path = "imei", value = imei).apply { imei() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["490154203237519", "49015420323751"])
        fun `given an invalid IMEI should report it`(
            imei: String
        ) {
            val node = ValidationNode(path = "imei", value = imei).apply { imei() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "imei", message = "must be a valid IMEI", code = "imei")
            )
        }
    }

    @Nested
    @DisplayName("When luhn is called")
    inner class Luhn {
        @ParameterizedTest
        @ValueSource(strings = ["79927398713"])
        fun `given a valid Luhn number should accept it`(
            number: String
        ) {
            val node = ValidationNode(path = "number", value = number).apply { luhn() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["79927398710", "7992739871a"])
        fun `given an invalid Luhn number should report it`(
            number: String
        ) {
            val node = ValidationNode(path = "number", value = number).apply { luhn() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "number", message = "must have a valid Luhn check digit", code = "luhn")
            )
        }
    }

    @Nested
    @DisplayName("When hash is called")
    inner class Hash {
        @ParameterizedTest
        @ValueSource(strings = ["e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855"])
        fun `given a valid SHA-256 hash should accept it`(
            checksum: String
        ) {
            val node = ValidationNode(path = "checksum", value = checksum).apply { hash(HashAlgorithm.SHA256) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(
            strings = [
                "d41d8cd98f00b204e9800998ecf8427e",
                "z3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855"
            ]
        )
        fun `given an invalid SHA-256 hash should report it`(
            checksum: String
        ) {
            val node = ValidationNode(path = "checksum", value = checksum).apply { hash(HashAlgorithm.SHA256) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "checksum", message = "must be a valid SHA256 hash", code = "hash")
            )
        }

        @Test
        fun `given a message function should build it from the value and the algorithm`() {
            val node = ValidationNode(path = "checksum", value = "abc").apply {
                hash(HashAlgorithm.MD5) { algorithm -> "$this is not $algorithm" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "checksum", message = "abc is not MD5", code = "hash")
            )
        }
    }
}
