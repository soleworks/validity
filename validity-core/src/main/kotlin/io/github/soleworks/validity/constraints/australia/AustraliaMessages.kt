package io.github.soleworks.validity.constraints.australia

import io.github.soleworks.validity.ValidityDsl

/**
 * The default messages of the constraints of Australia.
 *
 * Change them with [Messages.australia][io.github.soleworks.validity.Messages.australia].
 */
@ValidityDsl
public class AustraliaMessages internal constructor() {
    /** Default message of `abn`. */
    public var abn: String = "must be a valid ABN"

    /** Default message of `tfn`. */
    public var tfn: String = "must be a valid TFN"
}
