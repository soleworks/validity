package io.github.soleworks.validity

/**
 * The default messages of the constraints of Uruguay, in `io.github.soleworks.validity.constraints.uruguay`.
 *
 * Change them with [Messages.uruguay].
 */
@ValidityDsl
public class UruguayMessages internal constructor() {
    /** Default message of `rut`. */
    public var rut: String = "must be a valid RUT"

    /** Default message of `ci`. */
    public var ci: String = "must be a valid CI"
}
