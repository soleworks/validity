package io.github.soleworks.validity.constraints.iran

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class IranConstraintsTest {
    @Nested
    @DisplayName("When codeMelli is called")
    inner class CodeMelli {
        @ParameterizedTest
        @ValueSource(strings = ["0499370899", "0790419904", "0084575948", "1583250689"])
        fun `given a valid national identity code should accept it`(value: String) {
            val node = ValidationNode("codeMelli", value).apply { codeMelli() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["1260293040", "0000000001", "1999999999", "AAAAAAAAAA", "0684159415"])
        fun `given an invalid national identity code should report it`(value: String) {
            val node = ValidationNode("codeMelli", value).apply { codeMelli() }

            node.validate() shouldBe listOf(Violation("codeMelli", "must be a valid national identity code"))
        }
    }
}
