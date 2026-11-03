package io.github.soleworks.validity

/**
 * The default messages of the constraints of Chile, in `io.github.soleworks.validity.constraints.chile`.
 *
 * Change them with [Messages.chile].
 */
@ValidityDsl
public class ChileMessages internal constructor() {
    /** Default message of `rut`. */
    public var rut: String = "must be a valid RUT"
}
