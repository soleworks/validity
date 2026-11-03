package io.github.soleworks.validity.samples.docs

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.constraints.HashAlgorithm
import io.github.soleworks.validity.constraints.base64
import io.github.soleworks.validity.constraints.hash
import io.github.soleworks.validity.constraints.json
import io.github.soleworks.validity.ifPresent
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation

data class Attachment(
    val content: String?,
    val checksum: String?,
    val attributes: String? = null
) : Validatable {
    override fun validation() = validation {
        ::content required { base64() }
        ::checksum required { hash(HashAlgorithm.SHA256) }
        ::attributes ifPresent { json() }
    }
}
