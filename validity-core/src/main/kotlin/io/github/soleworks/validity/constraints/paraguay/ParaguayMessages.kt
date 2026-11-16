package io.github.soleworks.validity.constraints.paraguay

import io.github.soleworks.validity.ValidityDsl

/**
 * The default messages of the constraints of Paraguay.
 *
 * Change them with [Messages.paraguay][io.github.soleworks.validity.Messages.paraguay].
 */
@ValidityDsl
public class ParaguayMessages internal constructor() {
    /** Default message of `ruc`. */
    public var ruc: String = "must be a valid RUC"

    /** Default message of `ci`. */
    public var ci: String = "must be a valid CI"
}
