package io.github.soleworks.validity.constraints.colombia

import io.github.soleworks.validity.ValidityDsl

/**
 * The default messages of the constraints of Colombia.
 *
 * Change them with [Messages.colombia][io.github.soleworks.validity.Messages.colombia].
 */
@ValidityDsl
public class ColombiaMessages internal constructor() {
    /** Default message of `nit`. */
    public var nit: String = "must be a valid NIT"

    /** Default message of `cc`. */
    public var cc: String = "must be a valid CC"
}
