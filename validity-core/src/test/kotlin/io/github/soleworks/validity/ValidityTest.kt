package io.github.soleworks.validity

import io.github.soleworks.validity.constraints.brazil.cpf
import io.github.soleworks.validity.samples.Customer
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ValidityTest {
    @AfterEach
    fun restoreDefaultMessages() {
        Validity.configure { }
    }

    @Nested
    @DisplayName("When configure is called")
    inner class Configure {
        @Test
        fun `given no configuration should report the default message in English`() {
            val customer = Customer(name = "A")

            customer.validate().violations shouldBe listOf(Violation("name", "must have at least 2 characters", "minLength"))
        }

        @Test
        fun `given a message with a placeholder should fill it with the value of the rule`() {
            Validity.configure {
                messages {
                    minLength = "deve ter pelo menos {min} caracteres"
                }
            }

            val customer = Customer(name = "A")

            customer.validate().violations shouldBe listOf(Violation("name", "deve ter pelo menos 2 caracteres", "minLength"))
        }

        @Test
        fun `given the required message should report it for a missing field`() {
            Validity.configure {
                messages {
                    required = "é obrigatório"
                }
            }

            val customer = Customer(name = null)

            customer.validate().violations shouldBe listOf(Violation("name", "é obrigatório", "required"))
        }

        @Test
        fun `given a message passed to the function should keep it over the configured one`() {
            Validity.configure {
                messages {
                    required = "é obrigatório"
                }
            }

            val customer = Customer(email = null)

            customer.validate().violations shouldBe listOf(Violation("email", "email is mandatory", "required"))
        }

        @Test
        fun `given a message of a country should report it`() {
            Validity.configure {
                messages {
                    brazil {
                        cpf = "CPF inválido"
                    }
                }
            }

            val node = ValidationNode("cpf", "123").apply { cpf() }

            node.validate() shouldBe listOf(Violation("cpf", "CPF inválido", "cpf"))
        }

        @Test
        fun `given a second configuration should replace the first one`() {
            Validity.configure {
                messages {
                    minLength = "deve ter pelo menos {min} caracteres"
                }
            }
            Validity.configure {
                messages {
                    required = "é obrigatório"
                }
            }

            val customer = Customer(name = "A")

            customer.validate().violations shouldBe listOf(Violation("name", "must have at least 2 characters", "minLength"))
        }
    }
}
