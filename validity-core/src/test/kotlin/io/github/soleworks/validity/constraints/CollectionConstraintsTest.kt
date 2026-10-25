package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class CollectionConstraintsTest {
    @Nested
    @DisplayName("When minSize is called")
    inner class MinSize {
        @Test
        fun `given a file with at least the minimum bytes should accept it`() {
            val node = ValidationNode(path = "file", value = ByteArray(2)).apply { minSize(2) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a list with at least the minimum of tags should accept it`() {
            val node = ValidationNode(path = "tags", value = listOf("gift", "sale")).apply { minSize(2) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given an array with at least the minimum of tags should accept it`() {
            val node = ValidationNode(path = "tags", value = arrayOf("gift", "sale")).apply { minSize(2) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a list with fewer tags than the minimum should report it`() {
            val node = ValidationNode(path = "tags", value = listOf("gift")).apply { minSize(2) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "tags", message = "must have at least 2 items", code = "minSize")
            )
        }

        @Test
        fun `given an array with fewer tags than the minimum should report it`() {
            val node = ValidationNode(path = "tags", value = arrayOf("gift")).apply { minSize(2) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "tags", message = "must have at least 2 items", code = "minSize")
            )
        }

        @Test
        fun `given a file with fewer bytes than the minimum should report it`() {
            val node = ValidationNode(path = "file", value = ByteArray(1)).apply { minSize(2) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "file", message = "must have at least 2 items", code = "minSize")
            )
        }

        @Test
        fun `given a message function for a list should build it from the value and the parameters`() {
            val node = ValidationNode(path = "tags", value = listOf("gift")).apply {
                minSize(2) { min -> "has $size tags, needs at least $min" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "tags", message = "has 1 tags, needs at least 2", code = "minSize")
            )
        }

        @Test
        fun `given a message function for an array should build it from the value and the parameters`() {
            val node = ValidationNode(path = "tags", value = arrayOf("gift")).apply {
                minSize(2) { min -> "has $size tags, needs at least $min" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "tags", message = "has 1 tags, needs at least 2", code = "minSize")
            )
        }
    }

    @Nested
    @DisplayName("When maxSize is called")
    inner class MaxSize {
        @Test
        fun `given a file within the maximum bytes should accept it`() {
            val node = ValidationNode(path = "file", value = ByteArray(2)).apply { maxSize(2) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a list with at most the maximum of tags should accept it`() {
            val node = ValidationNode(path = "tags", value = listOf("gift", "sale", "new")).apply { maxSize(3) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given an array with at most the maximum of tags should accept it`() {
            val node = ValidationNode(path = "tags", value = arrayOf("gift", "sale", "new")).apply { maxSize(3) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a list with more tags than the maximum should report it`() {
            val node = ValidationNode(
                path = "tags",
                value = listOf("gift", "sale", "new", "summer")
            ).apply { maxSize(3) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "tags", message = "must have at most 3 items", code = "maxSize")
            )
        }

        @Test
        fun `given an array with more tags than the maximum should report it`() {
            val node = ValidationNode(
                path = "tags",
                value = arrayOf("gift", "sale", "new", "summer")
            ).apply { maxSize(3) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "tags", message = "must have at most 3 items", code = "maxSize")
            )
        }

        @Test
        fun `given a file with more bytes than the maximum should report it`() {
            val node = ValidationNode(path = "file", value = ByteArray(3)).apply { maxSize(2) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "file", message = "must have at most 2 items", code = "maxSize")
            )
        }

        @Test
        fun `given a message function for a list should build it from the value and the parameters`() {
            val node = ValidationNode(path = "tags", value = listOf("gift", "sale", "new", "summer")).apply {
                maxSize(3) { max -> "has $size tags, allows at most $max" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "tags", message = "has 4 tags, allows at most 3", code = "maxSize")
            )
        }

        @Test
        fun `given a message function for an array should build it from the value and the parameters`() {
            val node = ValidationNode(path = "tags", value = arrayOf("gift", "sale", "new", "summer")).apply {
                maxSize(3) { max -> "has $size tags, allows at most $max" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "tags", message = "has 4 tags, allows at most 3", code = "maxSize")
            )
        }
    }

    @Nested
    @DisplayName("When size is called")
    inner class Size {
        @Test
        fun `given a file with exactly the expected bytes should accept it`() {
            val node = ValidationNode(path = "file", value = ByteArray(16)).apply { size(16) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a list of coordinates with exactly 2 values should accept it`() {
            val node = ValidationNode(path = "coordinates", value = listOf(-23.55, -46.63)).apply { size(2) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given an array of coordinates with exactly 2 values should accept it`() {
            val node = ValidationNode(path = "coordinates", value = arrayOf("-23.55", "-46.63")).apply { size(2) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a list of coordinates without exactly 2 values should report it`() {
            val node = ValidationNode(path = "coordinates", value = listOf(-23.55)).apply { size(2) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "coordinates", message = "must have exactly 2 items", code = "size")
            )
        }

        @Test
        fun `given an array of coordinates without exactly 2 values should report it`() {
            val node = ValidationNode(path = "coordinates", value = arrayOf("-23.55")).apply { size(2) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "coordinates", message = "must have exactly 2 items", code = "size")
            )
        }

        @Test
        fun `given a file with other than the expected bytes should report it`() {
            val node = ValidationNode(path = "file", value = ByteArray(15)).apply { size(16) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "file", message = "must have exactly 16 items", code = "size")
            )
        }

        @Test
        fun `given a message function for a list should build it from the value and the parameters`() {
            val node = ValidationNode(path = "coordinates", value = listOf(-23.55)).apply {
                size(2) { size -> "has ${this.size} values, needs $size" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "coordinates", message = "has 1 values, needs 2", code = "size")
            )
        }

        @Test
        fun `given a message function for an array should build it from the value and the parameters`() {
            val node = ValidationNode(path = "coordinates", value = arrayOf("-23.55")).apply {
                size(2) { size -> "has ${this.size} values, needs $size" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "coordinates", message = "has 1 values, needs 2", code = "size")
            )
        }
    }

    @Nested
    @DisplayName("When sizeBetween is called")
    inner class SizeBetween {
        @Test
        fun `given a file with bytes within the range should accept it`() {
            val node = ValidationNode(path = "file", value = ByteArray(2)).apply { sizeBetween(min = 1, max = 3) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a list of phones at the lower limit should accept it`() {
            val node = ValidationNode(
                path = "phones",
                value = listOf("11999990000")
            ).apply { sizeBetween(min = 1, max = 3) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a list of phones at the upper limit should accept it`() {
            val phones = listOf("11999990000", "1133330000", "11988880000")
            val node = ValidationNode(path = "phones", value = phones).apply { sizeBetween(min = 1, max = 3) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given an array of phones at the lower limit should accept it`() {
            val node = ValidationNode(
                path = "phones",
                value = arrayOf("11999990000")
            ).apply { sizeBetween(min = 1, max = 3) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given an array of phones at the upper limit should accept it`() {
            val phones = arrayOf("11999990000", "1133330000", "11988880000")
            val node = ValidationNode(path = "phones", value = phones).apply { sizeBetween(min = 1, max = 3) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a list of phones below the range should report it`() {
            val node = ValidationNode(
                path = "phones",
                value = emptyList<String>()
            ).apply { sizeBetween(min = 1, max = 3) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "phones", message = "must have between 1 and 3 items", code = "sizeBetween")
            )
        }

        @Test
        fun `given a list of phones above the range should report it`() {
            val phones = listOf("11999990000", "1133330000", "11988880000", "1144440000")
            val node = ValidationNode(path = "phones", value = phones).apply { sizeBetween(min = 1, max = 3) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "phones", message = "must have between 1 and 3 items", code = "sizeBetween")
            )
        }

        @Test
        fun `given an array of phones below the range should report it`() {
            val node = ValidationNode(
                path = "phones",
                value = emptyArray<String>()
            ).apply { sizeBetween(min = 1, max = 3) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "phones", message = "must have between 1 and 3 items", code = "sizeBetween")
            )
        }

        @Test
        fun `given an array of phones above the range should report it`() {
            val phones = arrayOf("11999990000", "1133330000", "11988880000", "1144440000")
            val node = ValidationNode(path = "phones", value = phones).apply { sizeBetween(min = 1, max = 3) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "phones", message = "must have between 1 and 3 items", code = "sizeBetween")
            )
        }

        @Test
        fun `given a file with bytes out of the range should report it`() {
            val node = ValidationNode(path = "file", value = ByteArray(4)).apply { sizeBetween(min = 1, max = 3) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "file", message = "must have between 1 and 3 items", code = "sizeBetween")
            )
        }

        @Test
        fun `given a message function for a list should build it from the value and the parameters`() {
            val node = ValidationNode(path = "phones", value = emptyList<String>()).apply {
                sizeBetween(min = 1, max = 3) { min, max -> "has $size phones, needs from $min to $max" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "phones", message = "has 0 phones, needs from 1 to 3", code = "sizeBetween")
            )
        }

        @Test
        fun `given a message function for an array should build it from the value and the parameters`() {
            val node = ValidationNode(path = "phones", value = emptyArray<String>()).apply {
                sizeBetween(min = 1, max = 3) { min, max -> "has $size phones, needs from $min to $max" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "phones", message = "has 0 phones, needs from 1 to 3", code = "sizeBetween")
            )
        }
    }

    @Nested
    @DisplayName("When notEmpty is called")
    inner class NotEmpty {
        @Test
        fun `given a file with bytes should accept it`() {
            val node = ValidationNode(path = "file", value = ByteArray(1)).apply { notEmpty() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a list with one item should accept it`() {
            val node = ValidationNode(path = "items", value = listOf("SKU-123")).apply { notEmpty() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given an array with one item should accept it`() {
            val node = ValidationNode(path = "items", value = arrayOf("SKU-123")).apply { notEmpty() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a list without items should report it`() {
            val node = ValidationNode(path = "items", value = emptyList<String>()).apply { notEmpty() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "items", message = "must not be empty", code = "notEmpty")
            )
        }

        @Test
        fun `given an array without items should report it`() {
            val node = ValidationNode(path = "items", value = emptyArray<String>()).apply { notEmpty() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "items", message = "must not be empty", code = "notEmpty")
            )
        }

        @Test
        fun `given a file without bytes should report it`() {
            val node = ValidationNode(path = "file", value = ByteArray(0)).apply { notEmpty() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "file", message = "must not be empty", code = "notEmpty")
            )
        }
    }

    @Nested
    @DisplayName("When empty is called")
    inner class Empty {
        @Test
        fun `given a file without bytes should accept it`() {
            val node = ValidationNode(path = "file", value = ByteArray(0)).apply { empty() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a list without warnings should accept it`() {
            val node = ValidationNode(path = "warnings", value = emptyList<String>()).apply { empty() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given an array without warnings should accept it`() {
            val node = ValidationNode(path = "warnings", value = emptyArray<String>()).apply { empty() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a list with a warning should report it`() {
            val node = ValidationNode(path = "warnings", value = listOf("low stock")).apply { empty() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "warnings", message = "must be empty", code = "empty")
            )
        }

        @Test
        fun `given an array with a warning should report it`() {
            val node = ValidationNode(path = "warnings", value = arrayOf("low stock")).apply { empty() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "warnings", message = "must be empty", code = "empty")
            )
        }

        @Test
        fun `given a file with bytes should report it`() {
            val node = ValidationNode(path = "file", value = ByteArray(1)).apply { empty() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "file", message = "must be empty", code = "empty")
            )
        }
    }

    @Nested
    @DisplayName("When distinct is called")
    inner class Distinct {
        @Test
        fun `given a list without repeated tags should accept it`() {
            val node = ValidationNode(path = "tags", value = listOf("gift", "sale")).apply { distinct() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given an array without repeated tags should accept it`() {
            val node = ValidationNode(path = "tags", value = arrayOf("gift", "sale")).apply { distinct() }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a list with a repeated tag should report it`() {
            val node = ValidationNode(path = "tags", value = listOf("gift", "gift")).apply { distinct() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "tags", message = "must not contain duplicates", code = "distinct")
            )
        }

        @Test
        fun `given an array with a repeated tag should report it`() {
            val node = ValidationNode(path = "tags", value = arrayOf("gift", "gift")).apply { distinct() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "tags", message = "must not contain duplicates", code = "distinct")
            )
        }
    }

    @Nested
    @DisplayName("When distinctBy is called")
    inner class DistinctBy {
        @Test
        fun `given a list with different emails should accept it`() {
            val emails = listOf("ana@mail.com", "bia@mail.com")
            val node = ValidationNode(path = "emails", value = emails).apply { distinctBy(String::lowercase) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a set with different emails should accept it`() {
            val emails = setOf("ana@mail.com", "bia@mail.com")
            val node = ValidationNode(path = "emails", value = emails).apply { distinctBy(String::lowercase) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given an array with different emails should accept it`() {
            val emails = arrayOf("ana@mail.com", "bia@mail.com")
            val node = ValidationNode(path = "emails", value = emails).apply { distinctBy(String::lowercase) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a list with the same email in another case should report it`() {
            val emails = listOf("ana@mail.com", "ANA@mail.com")
            val node = ValidationNode(path = "emails", value = emails).apply { distinctBy(String::lowercase) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "emails", message = "must not contain duplicates", code = "distinctBy")
            )
        }

        @Test
        fun `given a set with the same email in another case should report it`() {
            val emails = setOf("ana@mail.com", "ANA@mail.com")
            val node = ValidationNode(path = "emails", value = emails).apply { distinctBy(String::lowercase) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "emails", message = "must not contain duplicates", code = "distinctBy")
            )
        }

        @Test
        fun `given an array with the same email in another case should report it`() {
            val emails = arrayOf("ana@mail.com", "ANA@mail.com")
            val node = ValidationNode(path = "emails", value = emails).apply { distinctBy(String::lowercase) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "emails", message = "must not contain duplicates", code = "distinctBy")
            )
        }

        @Test
        fun `given a message and the selector as a trailing lambda should report the message`() {
            val emails = setOf("ana@mail.com", "ANA@mail.com")
            val node = ValidationNode(
                path = "emails",
                value = emails
            ).apply { distinctBy("emails must be unique") { it.lowercase() } }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "emails", message = "emails must be unique", code = "distinctBy")
            )
        }

        @Test
        fun `given a message for an array should report it`() {
            val emails = arrayOf("ana@mail.com", "ANA@mail.com")
            val node = ValidationNode(
                path = "emails",
                value = emails
            ).apply { distinctBy("emails must be unique", String::lowercase) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "emails", message = "emails must be unique", code = "distinctBy")
            )
        }

        @Test
        fun `given the selector as a trailing lambda should use it`() {
            val emails = listOf("ana@mail.com", "ANA@mail.com")
            val node = ValidationNode(path = "emails", value = emails).apply { distinctBy { it.lowercase() } }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "emails", message = "must not contain duplicates", code = "distinctBy")
            )
        }
    }

    @Nested
    @DisplayName("When contains is called")
    inner class Contains {
        @Test
        fun `given a list of roles with the admin role should accept it`() {
            val node = ValidationNode(path = "roles", value = listOf("USER", "ADMIN")).apply { contains("ADMIN") }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a set of roles with the admin role should accept it`() {
            val node = ValidationNode(path = "roles", value = setOf("USER", "ADMIN")).apply { contains("ADMIN") }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given an array of roles with the admin role should accept it`() {
            val node = ValidationNode(path = "roles", value = arrayOf("USER", "ADMIN")).apply { contains("ADMIN") }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a list of roles without the admin role should report it`() {
            val node = ValidationNode(path = "roles", value = listOf("USER")).apply { contains("ADMIN") }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "roles", message = "must contain ADMIN", code = "contains")
            )
        }

        @Test
        fun `given a set of roles without the admin role should report it`() {
            val node = ValidationNode(path = "roles", value = setOf("USER")).apply { contains("ADMIN") }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "roles", message = "must contain ADMIN", code = "contains")
            )
        }

        @Test
        fun `given an array of roles without the admin role should report it`() {
            val node = ValidationNode(path = "roles", value = arrayOf("USER")).apply { contains("ADMIN") }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "roles", message = "must contain ADMIN", code = "contains")
            )
        }

        @Test
        fun `given a list checked without a required role should skip the check`() {
            val requiredRole: String? = null
            val node = ValidationNode(path = "roles", value = listOf("USER")).apply { contains(requiredRole) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a set checked without a required role should skip the check`() {
            val requiredRole: String? = null
            val node = ValidationNode(path = "roles", value = setOf("USER")).apply { contains(requiredRole) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given an array checked without a required role should skip the check`() {
            val requiredRole: String? = null
            val node = ValidationNode(path = "roles", value = arrayOf("USER")).apply { contains(requiredRole) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a message function for a list should build it from the value and the parameters`() {
            val node = ValidationNode(path = "roles", value = listOf("USER")).apply {
                contains("ADMIN") { element -> "has no $element among $size roles" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "roles", message = "has no ADMIN among 1 roles", code = "contains")
            )
        }

        @Test
        fun `given a message function for a set should build it from the value and the parameters`() {
            val node = ValidationNode(path = "roles", value = setOf("USER")).apply {
                contains("ADMIN") { element -> "has no $element among $size roles" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "roles", message = "has no ADMIN among 1 roles", code = "contains")
            )
        }

        @Test
        fun `given a message function for an array should build it from the value and the parameters`() {
            val node = ValidationNode(path = "roles", value = arrayOf("USER")).apply {
                contains("ADMIN") { element -> "has no $element among $size roles" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "roles", message = "has no ADMIN among 1 roles", code = "contains")
            )
        }
    }

    @Nested
    @DisplayName("When notContains is called")
    inner class NotContains {
        @Test
        fun `given a list of roles without the root role should accept it`() {
            val node = ValidationNode(path = "roles", value = listOf("USER", "ADMIN")).apply { notContains("ROOT") }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a set of roles without the root role should accept it`() {
            val node = ValidationNode(path = "roles", value = setOf("USER", "ADMIN")).apply { notContains("ROOT") }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given an array of roles without the root role should accept it`() {
            val node = ValidationNode(path = "roles", value = arrayOf("USER", "ADMIN")).apply { notContains("ROOT") }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a list of roles with the root role should report it`() {
            val node = ValidationNode(path = "roles", value = listOf("USER", "ROOT")).apply { notContains("ROOT") }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "roles", message = "must not contain ROOT", code = "notContains")
            )
        }

        @Test
        fun `given a set of roles with the root role should report it`() {
            val node = ValidationNode(path = "roles", value = setOf("USER", "ROOT")).apply { notContains("ROOT") }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "roles", message = "must not contain ROOT", code = "notContains")
            )
        }

        @Test
        fun `given an array of roles with the root role should report it`() {
            val node = ValidationNode(path = "roles", value = arrayOf("USER", "ROOT")).apply { notContains("ROOT") }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "roles", message = "must not contain ROOT", code = "notContains")
            )
        }

        @Test
        fun `given a list checked without a forbidden role should skip the check`() {
            val forbiddenRole: String? = null
            val node = ValidationNode(path = "roles", value = listOf("ROOT")).apply { notContains(forbiddenRole) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a set checked without a forbidden role should skip the check`() {
            val forbiddenRole: String? = null
            val node = ValidationNode(path = "roles", value = setOf("ROOT")).apply { notContains(forbiddenRole) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given an array checked without a forbidden role should skip the check`() {
            val forbiddenRole: String? = null
            val node = ValidationNode(path = "roles", value = arrayOf("ROOT")).apply { notContains(forbiddenRole) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a message function for a list should build it from the value and the parameters`() {
            val node = ValidationNode(path = "roles", value = listOf("ROOT")).apply {
                notContains("ROOT") { element -> "has $element among $size roles" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "roles", message = "has ROOT among 1 roles", code = "notContains")
            )
        }

        @Test
        fun `given a message function for a set should build it from the value and the parameters`() {
            val node = ValidationNode(path = "roles", value = setOf("ROOT")).apply {
                notContains("ROOT") { element -> "has $element among $size roles" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "roles", message = "has ROOT among 1 roles", code = "notContains")
            )
        }

        @Test
        fun `given a message function for an array should build it from the value and the parameters`() {
            val node = ValidationNode(path = "roles", value = arrayOf("ROOT")).apply {
                notContains("ROOT") { element -> "has $element among $size roles" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "roles", message = "has ROOT among 1 roles", code = "notContains")
            )
        }
    }

    @Nested
    @DisplayName("When containsAll is called")
    inner class ContainsAll {
        @Test
        fun `given a list of permissions with all the required ones should accept it`() {
            val permissions = listOf("READ", "WRITE", "DELETE")
            val node = ValidationNode(
                path = "permissions",
                value = permissions
            ).apply { containsAll(listOf("READ", "WRITE")) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a set of permissions with all the required ones should accept it`() {
            val permissions = setOf("READ", "WRITE", "DELETE")
            val node = ValidationNode(
                path = "permissions",
                value = permissions
            ).apply { containsAll(listOf("READ", "WRITE")) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given an array of permissions with all the required ones should accept it`() {
            val permissions = arrayOf("READ", "WRITE", "DELETE")
            val node = ValidationNode(
                path = "permissions",
                value = permissions
            ).apply { containsAll(listOf("READ", "WRITE")) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a list of permissions missing one of the required ones should report it`() {
            val node = ValidationNode(
                path = "permissions",
                value = listOf("READ")
            ).apply { containsAll(listOf("READ", "WRITE")) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "permissions", message = "must contain all of READ, WRITE", code = "containsAll")
            )
        }

        @Test
        fun `given a set of permissions missing one of the required ones should report it`() {
            val node = ValidationNode(
                path = "permissions",
                value = setOf("READ")
            ).apply { containsAll(listOf("READ", "WRITE")) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "permissions", message = "must contain all of READ, WRITE", code = "containsAll")
            )
        }

        @Test
        fun `given an array of permissions missing one of the required ones should report it`() {
            val node = ValidationNode(
                path = "permissions",
                value = arrayOf("READ")
            ).apply { containsAll(listOf("READ", "WRITE")) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "permissions", message = "must contain all of READ, WRITE", code = "containsAll")
            )
        }

        @Test
        fun `given a list checked without required permissions should skip the check`() {
            val requiredPermissions: List<String>? = null
            val node = ValidationNode(
                path = "permissions",
                value = listOf("READ")
            ).apply { containsAll(requiredPermissions) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a set checked without required permissions should skip the check`() {
            val requiredPermissions: List<String>? = null
            val node = ValidationNode(
                path = "permissions",
                value = setOf("READ")
            ).apply { containsAll(requiredPermissions) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given an array checked without required permissions should skip the check`() {
            val requiredPermissions: List<String>? = null
            val node = ValidationNode(
                path = "permissions",
                value = arrayOf("READ")
            ).apply { containsAll(requiredPermissions) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a message function for a list should build it from the value and the parameters`() {
            val node = ValidationNode(path = "permissions", value = listOf("READ")).apply {
                containsAll(listOf("READ", "WRITE")) { elements -> "needs all of $elements" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "permissions", message = "needs all of [READ, WRITE]", code = "containsAll")
            )
        }

        @Test
        fun `given a message function for a set should build it from the value and the parameters`() {
            val node = ValidationNode(path = "permissions", value = setOf("READ")).apply {
                containsAll(listOf("READ", "WRITE")) { elements -> "needs all of $elements" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "permissions", message = "needs all of [READ, WRITE]", code = "containsAll")
            )
        }

        @Test
        fun `given a message function for an array should build it from the value and the parameters`() {
            val node = ValidationNode(path = "permissions", value = arrayOf("READ")).apply {
                containsAll(listOf("READ", "WRITE")) { elements -> "needs all of $elements" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "permissions", message = "needs all of [READ, WRITE]", code = "containsAll")
            )
        }
    }

    @Nested
    @DisplayName("When containsAny is called")
    inner class ContainsAny {
        @Test
        fun `given a list of payment methods with an instant one should accept it`() {
            val paymentMethods = listOf("BOLETO", "PIX")
            val node = ValidationNode(
                path = "paymentMethods",
                value = paymentMethods
            ).apply { containsAny(listOf("PIX", "TED")) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a set of payment methods with an instant one should accept it`() {
            val paymentMethods = setOf("BOLETO", "PIX")
            val node = ValidationNode(
                path = "paymentMethods",
                value = paymentMethods
            ).apply { containsAny(listOf("PIX", "TED")) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given an array of payment methods with an instant one should accept it`() {
            val paymentMethods = arrayOf("BOLETO", "PIX")
            val node = ValidationNode(
                path = "paymentMethods",
                value = paymentMethods
            ).apply { containsAny(listOf("PIX", "TED")) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a list of payment methods without an instant one should report it`() {
            val node = ValidationNode(
                path = "paymentMethods",
                value = listOf("BOLETO")
            ).apply { containsAny(listOf("PIX", "TED")) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "paymentMethods", message = "must contain any of PIX, TED", code = "containsAny")
            )
        }

        @Test
        fun `given a set of payment methods without an instant one should report it`() {
            val node = ValidationNode(
                path = "paymentMethods",
                value = setOf("BOLETO")
            ).apply { containsAny(listOf("PIX", "TED")) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "paymentMethods", message = "must contain any of PIX, TED", code = "containsAny")
            )
        }

        @Test
        fun `given an array of payment methods without an instant one should report it`() {
            val node = ValidationNode(
                path = "paymentMethods",
                value = arrayOf("BOLETO")
            ).apply { containsAny(listOf("PIX", "TED")) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "paymentMethods", message = "must contain any of PIX, TED", code = "containsAny")
            )
        }

        @Test
        fun `given a list checked without instant methods should skip the check`() {
            val instantMethods: List<String>? = null
            val node = ValidationNode(
                path = "paymentMethods",
                value = listOf("BOLETO")
            ).apply { containsAny(instantMethods) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a set checked without instant methods should skip the check`() {
            val instantMethods: List<String>? = null
            val node = ValidationNode(
                path = "paymentMethods",
                value = setOf("BOLETO")
            ).apply { containsAny(instantMethods) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given an array checked without instant methods should skip the check`() {
            val instantMethods: List<String>? = null
            val node = ValidationNode(
                path = "paymentMethods",
                value = arrayOf("BOLETO")
            ).apply { containsAny(instantMethods) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a message function for a list should build it from the value and the parameters`() {
            val node = ValidationNode(path = "paymentMethods", value = listOf("BOLETO")).apply {
                containsAny(listOf("PIX", "TED")) { elements -> "needs any of $elements" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "paymentMethods", message = "needs any of [PIX, TED]", code = "containsAny")
            )
        }

        @Test
        fun `given a message function for a set should build it from the value and the parameters`() {
            val node = ValidationNode(path = "paymentMethods", value = setOf("BOLETO")).apply {
                containsAny(listOf("PIX", "TED")) { elements -> "needs any of $elements" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "paymentMethods", message = "needs any of [PIX, TED]", code = "containsAny")
            )
        }

        @Test
        fun `given a message function for an array should build it from the value and the parameters`() {
            val node = ValidationNode(path = "paymentMethods", value = arrayOf("BOLETO")).apply {
                containsAny(listOf("PIX", "TED")) { elements -> "needs any of $elements" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "paymentMethods", message = "needs any of [PIX, TED]", code = "containsAny")
            )
        }
    }

    @Nested
    @DisplayName("When containsNone is called")
    inner class ContainsNone {
        @Test
        fun `given a list of tags without blocked tags should accept it`() {
            val node = ValidationNode(path = "tags", value = listOf("gift", "sale")).apply {
                containsNone(listOf("spam", "scam"))
            }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a set of tags without blocked tags should accept it`() {
            val node = ValidationNode(path = "tags", value = setOf("gift", "sale")).apply {
                containsNone(listOf("spam", "scam"))
            }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given an array of tags without blocked tags should accept it`() {
            val node = ValidationNode(path = "tags", value = arrayOf("gift", "sale")).apply {
                containsNone(listOf("spam", "scam"))
            }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a list of tags with a blocked tag should report it`() {
            val node = ValidationNode(path = "tags", value = listOf("gift", "spam")).apply {
                containsNone(listOf("spam", "scam"))
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "tags", message = "must not contain any of spam, scam", code = "containsNone")
            )
        }

        @Test
        fun `given a set of tags with a blocked tag should report it`() {
            val node = ValidationNode(path = "tags", value = setOf("gift", "spam")).apply {
                containsNone(listOf("spam", "scam"))
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "tags", message = "must not contain any of spam, scam", code = "containsNone")
            )
        }

        @Test
        fun `given an array of tags with a blocked tag should report it`() {
            val node = ValidationNode(path = "tags", value = arrayOf("gift", "spam")).apply {
                containsNone(listOf("spam", "scam"))
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "tags", message = "must not contain any of spam, scam", code = "containsNone")
            )
        }

        @Test
        fun `given a list checked without blocked tags should skip the check`() {
            val blockedTags: List<String>? = null
            val node = ValidationNode(path = "tags", value = listOf("spam")).apply { containsNone(blockedTags) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a set checked without blocked tags should skip the check`() {
            val blockedTags: List<String>? = null
            val node = ValidationNode(path = "tags", value = setOf("spam")).apply { containsNone(blockedTags) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given an array checked without blocked tags should skip the check`() {
            val blockedTags: List<String>? = null
            val node = ValidationNode(path = "tags", value = arrayOf("spam")).apply { containsNone(blockedTags) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a message function for a list should build it from the value and the parameters`() {
            val node = ValidationNode(path = "tags", value = listOf("spam")).apply {
                containsNone(listOf("spam", "scam")) { elements -> "has one of $elements among $size tags" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "tags", message = "has one of [spam, scam] among 1 tags", code = "containsNone")
            )
        }

        @Test
        fun `given a message function for a set should build it from the value and the parameters`() {
            val node = ValidationNode(path = "tags", value = setOf("spam")).apply {
                containsNone(listOf("spam", "scam")) { elements -> "has one of $elements among $size tags" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "tags", message = "has one of [spam, scam] among 1 tags", code = "containsNone")
            )
        }

        @Test
        fun `given a message function for an array should build it from the value and the parameters`() {
            val node = ValidationNode(path = "tags", value = arrayOf("spam")).apply {
                containsNone(listOf("spam", "scam")) { elements -> "has one of $elements among $size tags" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "tags", message = "has one of [spam, scam] among 1 tags", code = "containsNone")
            )
        }
    }
}
