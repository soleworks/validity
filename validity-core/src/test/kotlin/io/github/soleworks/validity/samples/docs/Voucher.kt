package io.github.soleworks.validity.samples.docs

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.constraints.and
import io.github.soleworks.validity.constraints.length
import io.github.soleworks.validity.constraints.or
import io.github.soleworks.validity.constraints.startsWith
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation

data class Voucher(
    val code: String?
) : Validatable {
    override fun validation() = validation {
        ::code required {
            or {
                length(8)
                and {
                    startsWith("X")
                    length(10)
                }
            }
        }
    }
}
