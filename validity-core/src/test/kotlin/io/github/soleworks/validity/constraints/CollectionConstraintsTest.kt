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
        fun `given a list with fewer tags than the minimum should report it`() {
            val node = ValidationNode("tags", listOf("gift")).apply { minSize(2) }

            node.validate() shouldBe listOf(Violation("tags", "must have at least 2 items"))
        }

        @Test
        fun `given a list with at least the minimum of tags should accept it`() {
            val node = ValidationNode("tags", listOf("gift", "sale")).apply { minSize(2) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a list should build it from the value and the parameters`() {
            val node = ValidationNode("tags", listOf("gift")).apply {
                minSize(2) { min -> "has $size tags, needs at least $min" }
            }

            node.validate() shouldBe listOf(Violation("tags", "has 1 tags, needs at least 2"))
        }

        @Test
        fun `given an array with fewer tags than the minimum should report it`() {
            val node = ValidationNode("tags", arrayOf("gift")).apply { minSize(2) }

            node.validate() shouldBe listOf(Violation("tags", "must have at least 2 items"))
        }

        @Test
        fun `given an array with at least the minimum of tags should accept it`() {
            val node = ValidationNode("tags", arrayOf("gift", "sale")).apply { minSize(2) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for an array should build it from the value and the parameters`() {
            val node = ValidationNode("tags", arrayOf("gift")).apply {
                minSize(2) { min -> "has $size tags, needs at least $min" }
            }

            node.validate() shouldBe listOf(Violation("tags", "has 1 tags, needs at least 2"))
        }
    }

    @Nested
    @DisplayName("When maxSize is called")
    inner class MaxSize {
        @Test
        fun `given a list with more tags than the maximum should report it`() {
            val node = ValidationNode("tags", listOf("gift", "sale", "new", "summer")).apply { maxSize(3) }

            node.validate() shouldBe listOf(Violation("tags", "must have at most 3 items"))
        }

        @Test
        fun `given a list with at most the maximum of tags should accept it`() {
            val node = ValidationNode("tags", listOf("gift", "sale", "new")).apply { maxSize(3) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a list should build it from the value and the parameters`() {
            val node = ValidationNode("tags", listOf("gift", "sale", "new", "summer")).apply {
                maxSize(3) { max -> "has $size tags, allows at most $max" }
            }

            node.validate() shouldBe listOf(Violation("tags", "has 4 tags, allows at most 3"))
        }

        @Test
        fun `given an array with more tags than the maximum should report it`() {
            val node = ValidationNode("tags", arrayOf("gift", "sale", "new", "summer")).apply { maxSize(3) }

            node.validate() shouldBe listOf(Violation("tags", "must have at most 3 items"))
        }

        @Test
        fun `given an array with at most the maximum of tags should accept it`() {
            val node = ValidationNode("tags", arrayOf("gift", "sale", "new")).apply { maxSize(3) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for an array should build it from the value and the parameters`() {
            val node = ValidationNode("tags", arrayOf("gift", "sale", "new", "summer")).apply {
                maxSize(3) { max -> "has $size tags, allows at most $max" }
            }

            node.validate() shouldBe listOf(Violation("tags", "has 4 tags, allows at most 3"))
        }
    }

    @Nested
    @DisplayName("When size is called")
    inner class Size {
        @Test
        fun `given a list of coordinates without exactly 2 values should report it`() {
            val node = ValidationNode("coordinates", listOf(-23.55)).apply { size(2) }

            node.validate() shouldBe listOf(Violation("coordinates", "must have exactly 2 items"))
        }

        @Test
        fun `given a list of coordinates with exactly 2 values should accept it`() {
            val node = ValidationNode("coordinates", listOf(-23.55, -46.63)).apply { size(2) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a list should build it from the value and the parameters`() {
            val node = ValidationNode("coordinates", listOf(-23.55)).apply {
                size(2) { size -> "has ${this.size} values, needs $size" }
            }

            node.validate() shouldBe listOf(Violation("coordinates", "has 1 values, needs 2"))
        }

        @Test
        fun `given an array of coordinates without exactly 2 values should report it`() {
            val node = ValidationNode("coordinates", arrayOf(-23.55)).apply { size(2) }

            node.validate() shouldBe listOf(Violation("coordinates", "must have exactly 2 items"))
        }

        @Test
        fun `given an array of coordinates with exactly 2 values should accept it`() {
            val node = ValidationNode("coordinates", arrayOf(-23.55, -46.63)).apply { size(2) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for an array should build it from the value and the parameters`() {
            val node = ValidationNode("coordinates", arrayOf(-23.55)).apply {
                size(2) { size -> "has ${this.size} values, needs $size" }
            }

            node.validate() shouldBe listOf(Violation("coordinates", "has 1 values, needs 2"))
        }
    }

    @Nested
    @DisplayName("When sizeBetween is called")
    inner class SizeBetween {
        @Test
        fun `given a list of phones below the range should report it`() {
            val node = ValidationNode("phones", emptyList<String>()).apply { sizeBetween(1, 3) }

            node.validate() shouldBe listOf(Violation("phones", "must have between 1 and 3 items"))
        }

        @Test
        fun `given a list of phones above the range should report it`() {
            val phones = listOf("11999990000", "1133330000", "11988880000", "1144440000")

            val node = ValidationNode("phones", phones).apply { sizeBetween(1, 3) }

            node.validate() shouldBe listOf(Violation("phones", "must have between 1 and 3 items"))
        }

        @Test
        fun `given a list of phones at the lower limit should accept it`() {
            val node = ValidationNode("phones", listOf("11999990000")).apply { sizeBetween(1, 3) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a list of phones at the upper limit should accept it`() {
            val phones = listOf("11999990000", "1133330000", "11988880000")

            val node = ValidationNode("phones", phones).apply { sizeBetween(1, 3) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a list should build it from the value and the parameters`() {
            val node = ValidationNode("phones", emptyList<String>()).apply {
                sizeBetween(1, 3) { min, max -> "has $size phones, needs from $min to $max" }
            }

            node.validate() shouldBe listOf(Violation("phones", "has 0 phones, needs from 1 to 3"))
        }

        @Test
        fun `given an array of phones below the range should report it`() {
            val node = ValidationNode("phones", emptyArray<String>()).apply { sizeBetween(1, 3) }

            node.validate() shouldBe listOf(Violation("phones", "must have between 1 and 3 items"))
        }

        @Test
        fun `given an array of phones above the range should report it`() {
            val phones = arrayOf("11999990000", "1133330000", "11988880000", "1144440000")

            val node = ValidationNode("phones", phones).apply { sizeBetween(1, 3) }

            node.validate() shouldBe listOf(Violation("phones", "must have between 1 and 3 items"))
        }

        @Test
        fun `given an array of phones at the lower limit should accept it`() {
            val node = ValidationNode("phones", arrayOf("11999990000")).apply { sizeBetween(1, 3) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given an array of phones at the upper limit should accept it`() {
            val phones = arrayOf("11999990000", "1133330000", "11988880000")

            val node = ValidationNode("phones", phones).apply { sizeBetween(1, 3) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for an array should build it from the value and the parameters`() {
            val node = ValidationNode("phones", emptyArray<String>()).apply {
                sizeBetween(1, 3) { min, max -> "has $size phones, needs from $min to $max" }
            }

            node.validate() shouldBe listOf(Violation("phones", "has 0 phones, needs from 1 to 3"))
        }
    }

    @Nested
    @DisplayName("When notEmpty is called")
    inner class NotEmpty {
        @Test
        fun `given a list without items should report it`() {
            val node = ValidationNode("items", emptyList<String>()).apply { notEmpty() }

            node.validate() shouldBe listOf(Violation("items", "must not be empty"))
        }

        @Test
        fun `given a list with one item should accept it`() {
            val node = ValidationNode("items", listOf("SKU-123")).apply { notEmpty() }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given an array without items should report it`() {
            val node = ValidationNode("items", emptyArray<String>()).apply { notEmpty() }

            node.validate() shouldBe listOf(Violation("items", "must not be empty"))
        }

        @Test
        fun `given an array with one item should accept it`() {
            val node = ValidationNode("items", arrayOf("SKU-123")).apply { notEmpty() }

            node.validate() shouldBe emptyList()
        }
    }

    @Nested
    @DisplayName("When distinct is called")
    inner class Distinct {
        @Test
        fun `given a list with a repeated tag should report it`() {
            val node = ValidationNode("tags", listOf("gift", "gift")).apply { distinct() }

            node.validate() shouldBe listOf(Violation("tags", "must not contain duplicates"))
        }

        @Test
        fun `given a list without repeated tags should accept it`() {
            val node = ValidationNode("tags", listOf("gift", "sale")).apply { distinct() }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given an array with a repeated tag should report it`() {
            val node = ValidationNode("tags", arrayOf("gift", "gift")).apply { distinct() }

            node.validate() shouldBe listOf(Violation("tags", "must not contain duplicates"))
        }

        @Test
        fun `given an array without repeated tags should accept it`() {
            val node = ValidationNode("tags", arrayOf("gift", "sale")).apply { distinct() }

            node.validate() shouldBe emptyList()
        }
    }

    @Nested
    @DisplayName("When distinctBy is called")
    inner class DistinctBy {
        @Test
        fun `given a list with the same email in another case should report it`() {
            val emails = listOf("ana@mail.com", "ANA@mail.com")

            val node = ValidationNode("emails", emails).apply { distinctBy(String::lowercase) }

            node.validate() shouldBe listOf(Violation("emails", "must not contain duplicates"))
        }

        @Test
        fun `given a list with different emails should accept it`() {
            val emails = listOf("ana@mail.com", "bia@mail.com")

            val node = ValidationNode("emails", emails).apply { distinctBy(String::lowercase) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a set with the same email in another case should report it`() {
            val emails = setOf("ana@mail.com", "ANA@mail.com")

            val node = ValidationNode("emails", emails).apply { distinctBy(String::lowercase) }

            node.validate() shouldBe listOf(Violation("emails", "must not contain duplicates"))
        }

        @Test
        fun `given a set with different emails should accept it`() {
            val emails = setOf("ana@mail.com", "bia@mail.com")

            val node = ValidationNode("emails", emails).apply { distinctBy(String::lowercase) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given an array with the same email in another case should report it`() {
            val emails = arrayOf("ana@mail.com", "ANA@mail.com")

            val node = ValidationNode("emails", emails).apply { distinctBy(String::lowercase) }

            node.validate() shouldBe listOf(Violation("emails", "must not contain duplicates"))
        }

        @Test
        fun `given an array with different emails should accept it`() {
            val emails = arrayOf("ana@mail.com", "bia@mail.com")

            val node = ValidationNode("emails", emails).apply { distinctBy(String::lowercase) }

            node.validate() shouldBe emptyList()
        }
    }

    @Nested
    @DisplayName("When contains is called")
    inner class Contains {
        @Test
        fun `given a list of roles without the admin role should report it`() {
            val node = ValidationNode("roles", listOf("USER")).apply { contains("ADMIN") }

            node.validate() shouldBe listOf(Violation("roles", "must contain ADMIN"))
        }

        @Test
        fun `given a list of roles with the admin role should accept it`() {
            val node = ValidationNode("roles", listOf("USER", "ADMIN")).apply { contains("ADMIN") }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a list checked without a required role should skip the check`() {
            val requiredRole: String? = null

            val node = ValidationNode("roles", listOf("USER")).apply { contains(requiredRole) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a list should build it from the value and the parameters`() {
            val node = ValidationNode("roles", listOf("USER")).apply {
                contains("ADMIN") { element -> "has no $element among $size roles" }
            }

            node.validate() shouldBe listOf(Violation("roles", "has no ADMIN among 1 roles"))
        }

        @Test
        fun `given a set of roles without the admin role should report it`() {
            val node = ValidationNode("roles", setOf("USER")).apply { contains("ADMIN") }

            node.validate() shouldBe listOf(Violation("roles", "must contain ADMIN"))
        }

        @Test
        fun `given a set of roles with the admin role should accept it`() {
            val node = ValidationNode("roles", setOf("USER", "ADMIN")).apply { contains("ADMIN") }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a set checked without a required role should skip the check`() {
            val requiredRole: String? = null

            val node = ValidationNode("roles", setOf("USER")).apply { contains(requiredRole) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a set should build it from the value and the parameters`() {
            val node = ValidationNode("roles", setOf("USER")).apply {
                contains("ADMIN") { element -> "has no $element among $size roles" }
            }

            node.validate() shouldBe listOf(Violation("roles", "has no ADMIN among 1 roles"))
        }

        @Test
        fun `given an array of roles without the admin role should report it`() {
            val node = ValidationNode("roles", arrayOf("USER")).apply { contains("ADMIN") }

            node.validate() shouldBe listOf(Violation("roles", "must contain ADMIN"))
        }

        @Test
        fun `given an array of roles with the admin role should accept it`() {
            val node = ValidationNode("roles", arrayOf("USER", "ADMIN")).apply { contains("ADMIN") }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given an array checked without a required role should skip the check`() {
            val requiredRole: String? = null

            val node = ValidationNode("roles", arrayOf("USER")).apply { contains(requiredRole) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for an array should build it from the value and the parameters`() {
            val node = ValidationNode("roles", arrayOf("USER")).apply {
                contains("ADMIN") { element -> "has no $element among $size roles" }
            }

            node.validate() shouldBe listOf(Violation("roles", "has no ADMIN among 1 roles"))
        }
    }

    @Nested
    @DisplayName("When containsAll is called")
    inner class ContainsAll {
        @Test
        fun `given a list of permissions missing one of the required ones should report it`() {
            val node = ValidationNode("permissions", listOf("READ")).apply { containsAll(listOf("READ", "WRITE")) }

            node.validate() shouldBe listOf(Violation("permissions", "must contain all of READ, WRITE"))
        }

        @Test
        fun `given a list of permissions with all the required ones should accept it`() {
            val permissions = listOf("READ", "WRITE", "DELETE")

            val node = ValidationNode("permissions", permissions).apply { containsAll(listOf("READ", "WRITE")) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a list checked without required permissions should skip the check`() {
            val requiredPermissions: List<String>? = null

            val node = ValidationNode("permissions", listOf("READ")).apply { containsAll(requiredPermissions) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a list should build it from the value and the parameters`() {
            val node = ValidationNode("permissions", listOf("READ")).apply {
                containsAll(listOf("READ", "WRITE")) { elements -> "needs all of $elements" }
            }

            node.validate() shouldBe listOf(Violation("permissions", "needs all of [READ, WRITE]"))
        }

        @Test
        fun `given a set of permissions missing one of the required ones should report it`() {
            val node = ValidationNode("permissions", setOf("READ")).apply { containsAll(listOf("READ", "WRITE")) }

            node.validate() shouldBe listOf(Violation("permissions", "must contain all of READ, WRITE"))
        }

        @Test
        fun `given a set of permissions with all the required ones should accept it`() {
            val permissions = setOf("READ", "WRITE", "DELETE")

            val node = ValidationNode("permissions", permissions).apply { containsAll(listOf("READ", "WRITE")) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a set checked without required permissions should skip the check`() {
            val requiredPermissions: List<String>? = null

            val node = ValidationNode("permissions", setOf("READ")).apply { containsAll(requiredPermissions) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a set should build it from the value and the parameters`() {
            val node = ValidationNode("permissions", setOf("READ")).apply {
                containsAll(listOf("READ", "WRITE")) { elements -> "needs all of $elements" }
            }

            node.validate() shouldBe listOf(Violation("permissions", "needs all of [READ, WRITE]"))
        }

        @Test
        fun `given an array of permissions missing one of the required ones should report it`() {
            val node = ValidationNode("permissions", arrayOf("READ")).apply { containsAll(listOf("READ", "WRITE")) }

            node.validate() shouldBe listOf(Violation("permissions", "must contain all of READ, WRITE"))
        }

        @Test
        fun `given an array of permissions with all the required ones should accept it`() {
            val permissions = arrayOf("READ", "WRITE", "DELETE")

            val node = ValidationNode("permissions", permissions).apply { containsAll(listOf("READ", "WRITE")) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given an array checked without required permissions should skip the check`() {
            val requiredPermissions: List<String>? = null

            val node = ValidationNode("permissions", arrayOf("READ")).apply { containsAll(requiredPermissions) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for an array should build it from the value and the parameters`() {
            val node = ValidationNode("permissions", arrayOf("READ")).apply {
                containsAll(listOf("READ", "WRITE")) { elements -> "needs all of $elements" }
            }

            node.validate() shouldBe listOf(Violation("permissions", "needs all of [READ, WRITE]"))
        }
    }

    @Nested
    @DisplayName("When containsAny is called")
    inner class ContainsAny {
        @Test
        fun `given a list of payment methods without an instant one should report it`() {
            val node = ValidationNode("paymentMethods", listOf("BOLETO")).apply { containsAny(listOf("PIX", "TED")) }

            node.validate() shouldBe listOf(Violation("paymentMethods", "must contain any of PIX, TED"))
        }

        @Test
        fun `given a list of payment methods with an instant one should accept it`() {
            val paymentMethods = listOf("BOLETO", "PIX")

            val node = ValidationNode("paymentMethods", paymentMethods).apply { containsAny(listOf("PIX", "TED")) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a list checked without instant methods should skip the check`() {
            val instantMethods: List<String>? = null

            val node = ValidationNode("paymentMethods", listOf("BOLETO")).apply { containsAny(instantMethods) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a list should build it from the value and the parameters`() {
            val node = ValidationNode("paymentMethods", listOf("BOLETO")).apply {
                containsAny(listOf("PIX", "TED")) { elements -> "needs any of $elements" }
            }

            node.validate() shouldBe listOf(Violation("paymentMethods", "needs any of [PIX, TED]"))
        }

        @Test
        fun `given a set of payment methods without an instant one should report it`() {
            val node = ValidationNode("paymentMethods", setOf("BOLETO")).apply { containsAny(listOf("PIX", "TED")) }

            node.validate() shouldBe listOf(Violation("paymentMethods", "must contain any of PIX, TED"))
        }

        @Test
        fun `given a set of payment methods with an instant one should accept it`() {
            val paymentMethods = setOf("BOLETO", "PIX")

            val node = ValidationNode("paymentMethods", paymentMethods).apply { containsAny(listOf("PIX", "TED")) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a set checked without instant methods should skip the check`() {
            val instantMethods: List<String>? = null

            val node = ValidationNode("paymentMethods", setOf("BOLETO")).apply { containsAny(instantMethods) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for a set should build it from the value and the parameters`() {
            val node = ValidationNode("paymentMethods", setOf("BOLETO")).apply {
                containsAny(listOf("PIX", "TED")) { elements -> "needs any of $elements" }
            }

            node.validate() shouldBe listOf(Violation("paymentMethods", "needs any of [PIX, TED]"))
        }

        @Test
        fun `given an array of payment methods without an instant one should report it`() {
            val node = ValidationNode("paymentMethods", arrayOf("BOLETO")).apply { containsAny(listOf("PIX", "TED")) }

            node.validate() shouldBe listOf(Violation("paymentMethods", "must contain any of PIX, TED"))
        }

        @Test
        fun `given an array of payment methods with an instant one should accept it`() {
            val paymentMethods = arrayOf("BOLETO", "PIX")

            val node = ValidationNode("paymentMethods", paymentMethods).apply { containsAny(listOf("PIX", "TED")) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given an array checked without instant methods should skip the check`() {
            val instantMethods: List<String>? = null

            val node = ValidationNode("paymentMethods", arrayOf("BOLETO")).apply { containsAny(instantMethods) }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a message function for an array should build it from the value and the parameters`() {
            val node = ValidationNode("paymentMethods", arrayOf("BOLETO")).apply {
                containsAny(listOf("PIX", "TED")) { elements -> "needs any of $elements" }
            }

            node.validate() shouldBe listOf(Violation("paymentMethods", "needs any of [PIX, TED]"))
        }
    }
}
