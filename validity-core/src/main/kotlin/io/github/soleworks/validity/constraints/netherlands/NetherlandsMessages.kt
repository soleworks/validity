package io.github.soleworks.validity.constraints.netherlands

import io.github.soleworks.validity.ValidityDsl

/**
 * The default messages of the constraints of the Netherlands.
 *
 * Change them with [Messages.netherlands][io.github.soleworks.validity.Messages.netherlands].
 */
@ValidityDsl
public class NetherlandsMessages internal constructor() {
    /** Default message of `bsn`. */
    public var bsn: String = "must be a valid BSN"

    /** Default message of `kvk`. */
    public var kvk: String = "must be a valid KvK number"
}
