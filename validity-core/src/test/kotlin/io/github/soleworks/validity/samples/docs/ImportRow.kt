package io.github.soleworks.validity.samples.docs

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.constraints.dateFormat
import io.github.soleworks.validity.constraints.isoDate
import io.github.soleworks.validity.constraints.isoDuration
import io.github.soleworks.validity.ifPresent
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation

data class ImportRow(
    val createdOn: String?,
    val legacyDate: String? = null,
    val retention: String? = null
) : Validatable {
    override fun validation() = validation {
        ::createdOn required { isoDate() }
        ::legacyDate ifPresent { dateFormat("dd/MM/yyyy") }
        ::retention ifPresent { isoDuration() }
    }
}
