package io.github.soleworks.validity.samples

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.each
import io.github.soleworks.validity.metadata
import io.github.soleworks.validity.required
import io.github.soleworks.validity.valid
import io.github.soleworks.validity.validation

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
