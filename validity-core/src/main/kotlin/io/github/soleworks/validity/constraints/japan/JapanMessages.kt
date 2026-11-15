package io.github.soleworks.validity.constraints.japan

import io.github.soleworks.validity.ValidityDsl

/**
 * The default messages of the constraints of Japan.
 *
 * Change them with [Messages.japan][io.github.soleworks.validity.Messages.japan].
 */
@ValidityDsl
public class JapanMessages internal constructor() {
    /** Default message of `corporateNumber`. */
    public var corporateNumber: String = "must be a valid corporate number"

    /** Default message of `myNumber`. */
    public var myNumber: String = "must be a valid My Number"
}
