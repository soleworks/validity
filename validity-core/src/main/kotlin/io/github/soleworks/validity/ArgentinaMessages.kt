package io.github.soleworks.validity

/**
 * The default messages of the constraints of Argentina, in `io.github.soleworks.validity.constraints.argentina`.
 *
 * Change them with [Messages.argentina].
 */
@ValidityDsl
public class ArgentinaMessages internal constructor() {
    /** Default message of `cuit`. */
    public var cuit: String = "must be a valid CUIT"

    /** Default message of `cuil`. */
    public var cuil: String = "must be a valid CUIL"
}
