package io.github.soleworks.validity.samples.docs

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.constraints.cidr
import io.github.soleworks.validity.constraints.email
import io.github.soleworks.validity.constraints.url
import io.github.soleworks.validity.each
import io.github.soleworks.validity.ifPresent
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation

data class Webhook(
    val endpoint: String?,
    val contact: String?,
    val allowedNetworks: List<String>? = null
) : Validatable {
    override fun validation() = validation {
        ::endpoint required { url(schemes = setOf("https")) }
        ::contact required { email() }
        ::allowedNetworks ifPresent { each { cidr() } }
    }
}
