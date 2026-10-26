package io.github.soleworks.validity.phone

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Validity
import io.github.soleworks.validity.Violation
import io.github.soleworks.validity.phone.samples.Contact
import io.github.soleworks.validity.validateBlocking
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class PhoneMessagesTest {
    @AfterEach
    fun restoreDefaultMessages() {
        Validity.configure {
            messages {
                phone { }
            }
        }
    }

    @Nested
    @DisplayName("When phone is called")
    inner class Phone {
        @Test
        fun `given no configuration should report the default message in English`() {
            val node = ValidationNode(path = "phone", value = "1").apply { phone() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "phone", message = "must be a valid phone number", code = "phone")
            )
        }

        @Test
        fun `given the phone messages should report them`() {
            Validity.configure {
                messages {
                    phone {
                        phone = "telefone inválido"
                        mobilePhone = "celular inválido"
                    }
                }
            }
            val node = ValidationNode(path = "phone", value = "1").apply {
                phone()
                mobilePhone()
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "phone", message = "telefone inválido", code = "phone"),
                Violation(path = "phone", message = "celular inválido", code = "mobilePhone")
            )
        }

        @Test
        fun `given the phone messages again should start from the default ones`() {
            Validity.configure {
                messages {
                    phone {
                        phone = "telefone inválido"
                    }
                }
            }
            Validity.configure {
                messages {
                    phone {
                        mobilePhone = "celular inválido"
                    }
                }
            }
            val node = ValidationNode(path = "phone", value = "1").apply { phone() }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "phone", message = "must be a valid phone number", code = "phone")
            )
        }

        @Test
        fun `given the phone messages in a Validity should report them only in its validations`() {
            val validity = Validity { messages { phone { phone = "telefone inválido" } } }
            val contact = Contact(phone = "1")

            val violations = listOf(
                contact.validateBlocking(validity),
                contact.validateBlocking()
            ).flatMap { it.violations }

            violations shouldBe listOf(
                Violation(path = "phone", message = "telefone inválido", code = "phone"),
                Violation(path = "phone", message = "must be a valid phone number", code = "phone")
            )
        }
    }
}
