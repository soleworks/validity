package io.github.soleworks.validity.samples.docs

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.constraints.between
import io.github.soleworks.validity.constraints.min
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation

data class Packet(
    val ttl: UByte?,
    val port: UShort?,
    val sequence: ULong?
) : Validatable {
    override fun validation() = validation {
        ::ttl required { between(min = 1u, max = 64u) }
        ::port required { min(1024u) }
        ::sequence required { min(1uL) }
    }
}
