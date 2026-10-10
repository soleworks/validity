package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Validity
import io.github.soleworks.validity.Violation
import io.github.soleworks.validity.constraints.brazil.cnpj
import io.github.soleworks.validity.constraints.brazil.cpf
import io.github.soleworks.validity.each
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class LogicalConstraintsTest {
    @AfterEach
    fun reset() {
        Validity.configure { }
    }

    @Nested
    @DisplayName("When or is called")
    inner class Or {
        @ParameterizedTest
        @ValueSource(strings = ["529.982.247-25", "11.222.333/0001-81"])
        fun `given a valid CPF or a valid CNPJ should accept it`(document: String) {
            val node = ValidationNode("document", document).apply {
                or {
                    cpf()
                    cnpj()
                }
            }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given neither a CPF nor a CNPJ should report one violation with both messages`() {
            val node = ValidationNode("document", "123").apply {
                or {
                    cpf()
                    cnpj()
                }
            }

            node.validate() shouldBe listOf(
                Violation("document", "must be a valid CPF or must be a valid CNPJ", "or")
            )
        }

        @Test
        fun `given three rules failing should join the three messages`() {
            val node = ValidationNode("login", "123").apply {
                or {
                    cpf()
                    cnpj()
                    email()
                }
            }

            node.validate() shouldBe listOf(
                Violation("login", "must be a valid CPF or must be a valid CNPJ or must be a valid email", "or")
            )
        }

        @Test
        fun `given a custom message should report it instead of the messages of the rules`() {
            val node = ValidationNode("document", "123").apply {
                or("must be a CPF or a CNPJ") {
                    cpf()
                    cnpj()
                }
            }

            node.validate() shouldBe listOf(Violation("document", "must be a CPF or a CNPJ", "or"))
        }

        @Test
        fun `given a configured or message should join the messages with it`() {
            Validity.configure {
                messages {
                    or = "{left} ou {right}"
                }
            }

            val node = ValidationNode("document", "123").apply {
                or {
                    cpf()
                    cnpj()
                }
            }

            node.validate() shouldBe listOf(
                Violation("document", "must be a valid CPF ou must be a valid CNPJ", "or")
            )
        }

        @Test
        fun `given an and inside should accept a value that passes every rule of the and`() {
            val node = ValidationNode("document", "ID-12345").apply {
                or {
                    cpf()
                    and {
                        startsWith("ID-")
                        length(8)
                    }
                }
            }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given an and inside failing should report only the failing rules of the and`() {
            val node = ValidationNode("document", "ID-1").apply {
                or {
                    cpf()
                    and {
                        startsWith("ID-")
                        length(8)
                    }
                }
            }

            node.validate() shouldBe listOf(
                Violation("document", "must be a valid CPF or must have exactly 8 characters", "or")
            )
        }

        @Test
        fun `given each document of a list should report the one that is neither under its index`() {
            val node = ValidationNode("documents", listOf("529.982.247-25", "123")).apply {
                each {
                    or {
                        cpf()
                        cnpj()
                    }
                }
            }

            node.validate() shouldBe listOf(
                Violation("documents[1]", "must be a valid CPF or must be a valid CNPJ", "or")
            )
        }

        @Test
        fun `given rules on the items inside should count each item as one of the rules`() {
            val node = ValidationNode("tags", listOf("ab", "gift")).apply {
                or {
                    each { minLength(3) }
                }
            }

            node.validate() shouldBe emptyList()
        }
    }

    @Nested
    @DisplayName("When and is called")
    inner class And {
        @Test
        fun `given a value that passes every rule should accept it`() {
            val node = ValidationNode("code", "12345").apply {
                and {
                    length(5)
                    digits()
                }
            }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given failing rules should report one violation with only their messages`() {
            val node = ValidationNode("code", "abcdefg").apply {
                and {
                    minLength(3)
                    maxLength(5)
                    digits()
                }
            }

            node.validate() shouldBe listOf(
                Violation("code", "must have at most 5 characters and must contain only digits", "and")
            )
        }

        @Test
        fun `given a custom message should report it instead of the messages of the rules`() {
            val node = ValidationNode("code", "abcdefg").apply {
                and("must be a code of 5 digits") {
                    length(5)
                    digits()
                }
            }

            node.validate() shouldBe listOf(Violation("code", "must be a code of 5 digits", "and"))
        }

        @Test
        fun `given a configured and message should join the messages with it`() {
            Validity.configure {
                messages {
                    and = "{left} e {right}"
                }
            }

            val node = ValidationNode("code", "abcdefg").apply {
                and {
                    length(5)
                    digits()
                }
            }

            node.validate() shouldBe listOf(
                Violation("code", "must have exactly 5 characters e must contain only digits", "and")
            )
        }
    }

    @Nested
    @DisplayName("When not is called")
    inner class Not {
        @Test
        fun `given a value that passes the rule should report it`() {
            val node = ValidationNode("username", "admin").apply {
                not { equalTo("admin") }
            }

            node.validate() shouldBe listOf(Violation("username", "is not allowed", "not"))
        }

        @Test
        fun `given a value that fails the rule should accept it`() {
            val node = ValidationNode("username", "ana").apply {
                not { equalTo("admin") }
            }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["admin", "root"])
        fun `given several rules should report a value that passes any of them`(username: String) {
            val node = ValidationNode("username", username).apply {
                not {
                    equalTo("admin")
                    equalTo("root")
                }
            }

            node.validate() shouldBe listOf(Violation("username", "is not allowed", "not"))
        }

        @Test
        fun `given several rules should accept a value that fails all of them`() {
            val node = ValidationNode("username", "ana").apply {
                not {
                    equalTo("admin")
                    equalTo("root")
                }
            }

            node.validate() shouldBe emptyList()
        }

        @Test
        fun `given a custom message should report it`() {
            val node = ValidationNode("username", "admin").apply {
                not("admin is reserved") { equalTo("admin") }
            }

            node.validate() shouldBe listOf(Violation("username", "admin is reserved", "not"))
        }

        @Test
        fun `given a configured not message should report it`() {
            Validity.configure {
                messages {
                    not = "não é permitido"
                }
            }

            val node = ValidationNode("username", "admin").apply {
                not { equalTo("admin") }
            }

            node.validate() shouldBe listOf(Violation("username", "não é permitido", "not"))
        }
    }
}
