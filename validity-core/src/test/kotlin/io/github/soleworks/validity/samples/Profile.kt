package io.github.soleworks.validity.samples

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.constraints.brazil.cpf
import io.github.soleworks.validity.constraints.email
import io.github.soleworks.validity.constraints.maxLength
import io.github.soleworks.validity.constraints.or
import io.github.soleworks.validity.each
import io.github.soleworks.validity.required
import io.github.soleworks.validity.valid
import io.github.soleworks.validity.validation

data class Mailbox(
    val email: String? = "ana@mail.com",
    val login: String? = "ana@mail.com"
) : Validatable {
    override fun validation() = validation {
        ::email required {
            failFast {
                email()
                maxLength(20)
            }
        }
        ::login required {
            or {
                failFast {
                    email()
                    maxLength(20)
                }
                cpf()
            }
        }
    }
}

data class Profile(
    val mailbox: Mailbox? = Mailbox(),
    val mailboxes: List<Mailbox>? = listOf(Mailbox())
) : Validatable {
    override fun validation() = validation {
        ::mailbox required { valid() }
        metadata("list" to "mailboxes") {
            ::mailboxes required { each { valid() } }
        }
    }
}
