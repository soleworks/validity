package io.github.soleworks.validity.constraints.uruguay

import io.github.soleworks.validity.ValidityDsl

/**
 * The default messages of the constraints of Uruguay.
 *
 * Change them with [Messages.uruguay][io.github.soleworks.validity.Messages.uruguay].
 */
@ValidityDsl
public class UruguayMessages internal constructor() {
    /** Default message of `rut`. */
    public var rut: String = "must be a valid RUT"

    /** Default message of `ci`. */
    public var ci: String = "must be a valid CI"
}
