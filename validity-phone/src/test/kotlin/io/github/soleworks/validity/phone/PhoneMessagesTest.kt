package io.github.soleworks.validity.phone

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Validity
import io.github.soleworks.validity.Violation
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
            val node = ValidationNode("phone", "1").apply { phone() }

            node.validate() shouldBe listOf(Violation("phone", "must be a valid phone number"))
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

            val node = ValidationNode("phone", "1").apply {
                phone()
                mobilePhone()
            }

            node.validate() shouldBe listOf(
                Violation("phone", "telefone inválido"),
                Violation("phone", "celular inválido")
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

            val node = ValidationNode("phone", "1").apply { phone() }

            node.validate() shouldBe listOf(Violation("phone", "must be a valid phone number"))
        }
    }
}
