package io.github.soleworks.validity

/**
 * The default messages of the constraints of Portugal, in `io.github.soleworks.validity.constraints.portugal`.
 *
 * Change them with [Messages.portugal].
 */
@ValidityDsl
public class PortugalMessages internal constructor() {
    /** Default message of `nif`. */
    public var nif: String = "must be a valid NIF"

    /** Default message of `nipc`. */
    public var nipc: String = "must be a valid NIPC"
}
