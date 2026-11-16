package io.github.soleworks.validity.constraints.canada

import io.github.soleworks.validity.ValidityDsl

/**
 * The default messages of the constraints of Canada.
 *
 * Change them with [Messages.canada][io.github.soleworks.validity.Messages.canada].
 */
@ValidityDsl
public class CanadaMessages internal constructor() {
    /** Default message of `sin`. */
    public var sin: String = "must be a valid SIN"

    /** Default message of `bn`. */
    public var bn: String = "must be a valid business number"
}
