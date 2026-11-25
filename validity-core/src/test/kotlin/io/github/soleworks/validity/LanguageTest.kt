package io.github.soleworks.validity

import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class LanguageTest {
    @Nested
    @DisplayName("When of is called")
    inner class Of {
        @Test
        fun `given the tag of Brazilian Portuguese should accept it as PT_BR`() {
            val result = Language.of("pt-BR")

            result shouldBe Language.PT_BR
        }

        @Test
        fun `given the tag of Spanish from Argentina should accept it as ES`() {
            val result = Language.of("es-AR")

            result shouldBe Language.ES
        }

        @Test
        fun `given a tag in upper case with an underscore should accept it by its language`() {
            val result = Language.of("EN_US")

            result shouldBe Language.EN
        }

        @Test
        fun `given the tag of a language without messages should throw naming the languages there are`() {
            val error = shouldThrowExactly<IllegalArgumentException> { Language.of("fr-FR") }

            error.message shouldBe "Language.of has no language for 'fr-FR': the languages are en, es, pt-BR"
        }

        @Test
        fun `given a blank tag should throw naming the languages there are`() {
            val error = shouldThrowExactly<IllegalArgumentException> { Language.of("") }

            error.message shouldBe "Language.of has no language for '': the languages are en, es, pt-BR"
        }
    }
}
