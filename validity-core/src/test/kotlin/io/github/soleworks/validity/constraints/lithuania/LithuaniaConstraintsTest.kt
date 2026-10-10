package io.github.soleworks.validity.constraints.lithuania

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class LithuaniaConstraintsTest {
    @Nested
    @DisplayName("When asmensKodas is called")
    inner class AsmensKodas {
        @ParameterizedTest
        @ValueSource(strings = ["33309240064", "10001010080", "46304280206", "37102250382"])
        fun `given an asmens kodas with a valid date and check digit should accept it`(value: String) {
            val node = ValidationNode("asmensKodas", value).apply { asmensKodas() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["46304280205", "61002293333", "4-6304 28/0206", "4630428020", "463042802066"])
        fun `given an asmens kodas with an impossible date, a wrong check digit or another format should report it`(
            value: String
        ) {
            val node = ValidationNode("asmensKodas", value).apply { asmensKodas() }

            node.validate() shouldBe listOf(Violation("asmensKodas", "must be a valid asmens kodas", "asmensKodas"))
        }
    }
}
