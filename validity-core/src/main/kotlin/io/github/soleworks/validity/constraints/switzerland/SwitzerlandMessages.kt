package io.github.soleworks.validity.constraints.switzerland

import io.github.soleworks.validity.ValidityDsl

/**
 * The default messages of the constraints of Switzerland.
 *
 * Change them with [Messages.switzerland][io.github.soleworks.validity.Messages.switzerland].
 */
@ValidityDsl
public class SwitzerlandMessages internal constructor() {
    /** Default message of `uid`. */
    public var uid: String = "must be a valid UID"

    /** Default message of `ahv`. */
    public var ahv: String = "must be a valid AHV number"
}
