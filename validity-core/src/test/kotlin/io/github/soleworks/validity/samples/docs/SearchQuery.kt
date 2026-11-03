package io.github.soleworks.validity.samples.docs

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.constraints.between
import io.github.soleworks.validity.constraints.min
import io.github.soleworks.validity.constraints.pastOrPresent
import io.github.soleworks.validity.convertToEnum
import io.github.soleworks.validity.convertToInt
import io.github.soleworks.validity.convertToLocalDate
import io.github.soleworks.validity.ifPresent
import io.github.soleworks.validity.validation

data class SearchQuery(
    val page: String? = null,
    val size: String? = null,
    val since: String? = null,
    val sort: String? = null
) : Validatable {
    override fun validation() = validation {
        ::page ifPresent { convertToInt { min(1) } }
        ::size ifPresent { convertToInt { between(min = 1, max = 100) } }
        ::since ifPresent { convertToLocalDate { pastOrPresent() } }
        ::sort ifPresent { convertToEnum<SortOrder>(ignoreCase = true) {} }
    }
}
