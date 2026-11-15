package io.github.soleworks.validity.constraints.belgium

import io.github.soleworks.validity.ValidityDsl

/**
 * The default messages of the constraints of Belgium.
 *
 * Change them with [Messages.belgium][io.github.soleworks.validity.Messages.belgium].
 */
@ValidityDsl
public class BelgiumMessages internal constructor() {
    /** Default message of `bce`. */
    public var bce: String = "must be a valid enterprise number"

    /** Default message of `nationalNumber`. */
    public var nationalNumber: String = "must be a valid national number"
}
