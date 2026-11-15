package io.github.soleworks.validity.constraints.argentina

import io.github.soleworks.validity.ValidityDsl

/**
 * The default messages of the constraints of Argentina.
 *
 * Change them with [Messages.argentina][io.github.soleworks.validity.Messages.argentina].
 */
@ValidityDsl
public class ArgentinaMessages internal constructor() {
    /** Default message of `cuit`. */
    public var cuit: String = "must be a valid CUIT"

    /** Default message of `cuil`. */
    public var cuil: String = "must be a valid CUIL"
}
