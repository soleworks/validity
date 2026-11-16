package io.github.soleworks.validity.constraints.portugal

import io.github.soleworks.validity.ValidityDsl

/**
 * The default messages of the constraints of Portugal.
 *
 * Change them with [Messages.portugal][io.github.soleworks.validity.Messages.portugal].
 */
@ValidityDsl
public class PortugalMessages internal constructor() {
    /** Default message of `nif`. */
    public var nif: String = "must be a valid NIF"

    /** Default message of `nipc`. */
    public var nipc: String = "must be a valid NIPC"
}
