package io.github.soleworks.validity.constraints.chile

import io.github.soleworks.validity.ValidityDsl

/**
 * The default messages of the constraints of Chile.
 *
 * Change them with [Messages.chile][io.github.soleworks.validity.Messages.chile].
 */
@ValidityDsl
public class ChileMessages internal constructor() {
    /** Default message of `rut`. */
    public var rut: String = "must be a valid RUT"
}
