package io.github.soleworks.validity.constraints.hongkong

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class HongKongConstraintsTest {
    @Nested
    @DisplayName("When hkid is called")
    inner class Hkid {
        @ParameterizedTest
        @ValueSource(strings = ["OV290326[A]", "Q803337[0]", "Z0977986", "W520128(7)", "A494866(4)", "ag293013(9)"])
        fun `given a valid HKID should accept it`(value: String) {
            val node = ValidationNode("hkid", value).apply { hkid() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["A1234567890", "98765432", "O962472(9)", "M4578601", "X731324[8]", "RH265886(3)"])
        fun `given an invalid HKID should report it`(value: String) {
            val node = ValidationNode("hkid", value).apply { hkid() }

            node.validate() shouldBe listOf(Violation("hkid", "must be a valid HKID"))
        }
    }
}
