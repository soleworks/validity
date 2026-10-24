package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Validity
import io.github.soleworks.validity.Violation
import io.github.soleworks.validity.constraints.brazil.cnpj
import io.github.soleworks.validity.constraints.brazil.cpf
import io.github.soleworks.validity.each
import io.github.soleworks.validity.samples.Address
import io.github.soleworks.validity.valid
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
        fun `given a valid CPF or a valid CNPJ should accept it`(
            document: String
        ) {
            val node = ValidationNode(path = "document", value = document).apply {
                or {
                    cpf()
                    cnpj()
                }
            }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given an and inside should accept a value that passes every rule of the and`() {
            val node = ValidationNode(path = "document", value = "ID-12345").apply {
                or {
                    cpf()
                    and {
                        startsWith("ID-")
                        length(8)
                    }
                }
            }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given rules on the items of an empty list should accept it`() {
            val node = ValidationNode(path = "tags", value = emptyList<String>()).apply {
                or {
                    each { minLength(3) }
                }
            }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no rules should accept the value`() {
            val node = ValidationNode(path = "document", value = "123").apply { or { } }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a message with a template placeholder should keep it as text`() {
            val node = ValidationNode(path = "token", value = "x").apply {
                or {
                    constraint("has {right} inside") { false }
                    constraint("is another rule") { false }
                }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "token", message = "has {right} inside or is another rule", code = "or")
            )
        }

        @Test
        fun `given neither a CPF nor a CNPJ should report one violation with both messages`() {
            val node = ValidationNode(path = "document", value = "123").apply {
                or {
                    cpf()
                    cnpj()
                }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "document", message = "must be a valid CPF or must be a valid CNPJ", code = "or")
            )
        }

        @Test
        fun `given a custom message should report it instead of the messages of the rules`() {
            val node = ValidationNode(path = "document", value = "123").apply {
                or("must be a CPF or a CNPJ") {
                    cpf()
                    cnpj()
                }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "document", message = "must be a CPF or a CNPJ", code = "or")
            )
        }

        @Test
        fun `given an and inside failing should report only the failing rules of the and`() {
            val node = ValidationNode(path = "document", value = "ID-1").apply {
                or {
                    cpf()
                    and {
                        startsWith("ID-")
                        length(8)
                    }
                }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(
                    path = "document",
                    message = "must be a valid CPF or must have exactly 8 characters",
                    code = "or"
                )
            )
        }

        @Test
        fun `given each document of a list should report the one that is neither under its index`() {
            val node = ValidationNode(path = "documents", value = listOf("529.982.247-25", "123")).apply {
                each {
                    or {
                        cpf()
                        cnpj()
                    }
                }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "documents[1]", message = "must be a valid CPF or must be a valid CNPJ", code = "or")
            )
        }

        @Test
        fun `given rules on the items inside should treat all the items as one rule`() {
            val node = ValidationNode(path = "tags", value = listOf("ab", "gift")).apply {
                or {
                    each { minLength(3) }
                }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "tags", message = "must have at least 3 characters", code = "or")
            )
        }

        @Test
        fun `given a nested object with one invalid property should treat the whole object as one rule`() {
            val node = ValidationNode(path = "address", value = Address(street = null)).apply {
                or {
                    valid()
                }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "address", message = "is required", code = "or")
            )
        }

        @Test
        fun `given a rule skipped by a null reference should decide with the other rules`() {
            val reserved: String? = null
            val node = ValidationNode(path = "document", value = "123").apply {
                or {
                    equalTo(reserved)
                    cpf()
                }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "document", message = "must be a valid CPF", code = "or")
            )
        }

        @Test
        fun `given nested operators should run each rule once`() {
            var runs = 0
            val node = ValidationNode(path = "document", value = "123").apply {
                or {
                    or {
                        or {
                            constraint("never passes") {
                                runs++
                                false
                            }
                        }
                    }
                }
            }

            node.validate()

            runs shouldBe 1
        }

        @Test
        fun `given an and of two failing rules on the items should group its messages`() {
            val node = ValidationNode(path = "tags", value = listOf("x")).apply {
                or {
                    constraint("must be empty") { false }
                    each {
                        and {
                            minLength(3)
                            startsWith("#")
                        }
                    }
                }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(
                    path = "tags",
                    message = "must be empty or (must have at least 3 characters and must start with #)",
                    code = "or"
                )
            )
        }

        @Test
        fun `given an and of two failing rules inside should group its messages`() {
            val node = ValidationNode(path = "document", value = "X").apply {
                or {
                    cpf()
                    and {
                        startsWith("ID-")
                        length(8)
                    }
                }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(
                    path = "document",
                    message = "must be a valid CPF or (must start with ID- and must have exactly 8 characters)",
                    code = "or"
                )
            )
        }

        @Test
        fun `given three rules failing should join the three messages`() {
            val node = ValidationNode(path = "login", value = "123").apply {
                or {
                    cpf()
                    cnpj()
                    email()
                }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(
                    path = "login",
                    message = "must be a valid CPF or must be a valid CNPJ or must be a valid email",
                    code = "or"
                )
            )
        }

        @Test
        fun `given a configured or message should join the messages with it`() {
            Validity.configure {
                messages {
                    or = "{left} ou {right}"
                }
            }
            val node = ValidationNode(path = "document", value = "123").apply {
                or {
                    cpf()
                    cnpj()
                }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "document", message = "must be a valid CPF ou must be a valid CNPJ", code = "or")
            )
        }

        @Test
        fun `given a value with a placeholder text should not fill it again`() {
            val node = ValidationNode(path = "token", value = "{path}").apply {
                or {
                    constraint("got {value}") { false }
                    constraint("is another rule") { false }
                }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "token", message = "got {path} or is another rule", code = "or")
            )
        }

        @Test
        fun `given the path placeholder in the configured or message should fill it`() {
            Validity.configure {
                messages {
                    or = "{path}: {left} ou {right}"
                }
            }
            val node = ValidationNode(path = "document", value = "123").apply {
                or {
                    cpf()
                    cnpj()
                }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(
                    path = "document",
                    message = "document: must be a valid CPF ou must be a valid CNPJ",
                    code = "or"
                )
            )
        }
    }

    @Nested
    @DisplayName("When and is called")
    inner class And {
        @Test
        fun `given a value that passes every rule should accept it`() {
            val node = ValidationNode(path = "code", value = "12345").apply {
                and {
                    length(5)
                    digits()
                }
            }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no rules should accept the value`() {
            val node = ValidationNode(path = "code", value = "abc").apply { and { } }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given failing rules should report one violation with only their messages`() {
            val node = ValidationNode(path = "code", value = "abcdefg").apply {
                and {
                    minLength(3)
                    maxLength(5)
                    digits()
                }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(
                    path = "code",
                    message = "must have at most 5 characters and must contain only digits",
                    code = "and"
                )
            )
        }

        @Test
        fun `given a custom message should report it instead of the messages of the rules`() {
            val node = ValidationNode(path = "code", value = "abcdefg").apply {
                and("must be a code of 5 digits") {
                    length(5)
                    digits()
                }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "code", message = "must be a code of 5 digits", code = "and")
            )
        }

        @Test
        fun `given the same message from several items should report it once`() {
            val node = ValidationNode(path = "tags", value = listOf("ab", "c")).apply {
                and {
                    each { minLength(3) }
                }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "tags", message = "must have at least 3 characters", code = "and")
            )
        }

        @Test
        fun `given an or of two failing rules inside should group its messages`() {
            val node = ValidationNode(path = "document", value = "123").apply {
                and {
                    or {
                        cpf()
                        cnpj()
                    }
                    minLength(20)
                }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(
                    path = "document",
                    message = "(must be a valid CPF or must be a valid CNPJ) and must have at least 20 characters",
                    code = "and"
                )
            )
        }

        @Test
        fun `given a configured and message should join the messages with it`() {
            Validity.configure {
                messages {
                    and = "{left} e {right}"
                }
            }
            val node = ValidationNode(path = "code", value = "abcdefg").apply {
                and {
                    length(5)
                    digits()
                }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(
                    path = "code",
                    message = "must have exactly 5 characters e must contain only digits",
                    code = "and"
                )
            )
        }
    }

    @Nested
    @DisplayName("When not is called")
    inner class Not {
        @Test
        fun `given a value that fails the rule should accept it`() {
            val node = ValidationNode(path = "username", value = "ana").apply {
                not { equalTo("admin") }
            }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given several rules should accept a value that fails all of them`() {
            val node = ValidationNode(path = "username", value = "ana").apply {
                not {
                    equalTo("admin")
                    equalTo("root")
                }
            }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a rule skipped by a null reference should accept the value`() {
            val reserved: String? = null
            val node = ValidationNode(path = "username", value = "ana").apply {
                not { equalTo(reserved) }
            }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no rules should accept the value`() {
            val node = ValidationNode(path = "username", value = "admin").apply { not { } }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a value that passes the rule should report it`() {
            val node = ValidationNode(path = "username", value = "admin").apply {
                not { equalTo("admin") }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "username", message = "is not allowed", code = "not")
            )
        }

        @ParameterizedTest
        @ValueSource(strings = ["admin", "root"])
        fun `given several rules should report a value that passes any of them`(
            username: String
        ) {
            val node = ValidationNode(path = "username", value = username).apply {
                not {
                    equalTo("admin")
                    equalTo("root")
                }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "username", message = "is not allowed", code = "not")
            )
        }

        @Test
        fun `given a custom message should report it`() {
            val node = ValidationNode(path = "username", value = "admin").apply {
                not("admin is reserved") { equalTo("admin") }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "username", message = "admin is reserved", code = "not")
            )
        }

        @Test
        fun `given a configured not message should report it`() {
            Validity.configure {
                messages {
                    not = "não é permitido"
                }
            }
            val node = ValidationNode(path = "username", value = "admin").apply {
                not { equalTo("admin") }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "username", message = "não é permitido", code = "not")
            )
        }
    }
}
