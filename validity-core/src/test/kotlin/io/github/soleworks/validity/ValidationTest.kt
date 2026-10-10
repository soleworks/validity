package io.github.soleworks.validity

import io.github.soleworks.validity.samples.Coupon
import io.github.soleworks.validity.samples.validation
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ValidationTest {
    @Nested
    @DisplayName("When validate is called")
    inner class Validate {
        @Test
        fun `given a coupon with a short code should report it at the root`() {
            val coupon = Coupon(code = "OFF")

            coupon.validation().validate().violations shouldBe listOf(
                Violation("code", "must have at least 5 characters")
            )
        }

        @Test
        fun `given a valid coupon should be valid`() {
            val coupon = Coupon()

            coupon.validation().validate().isValid shouldBe true
        }
    }
}
