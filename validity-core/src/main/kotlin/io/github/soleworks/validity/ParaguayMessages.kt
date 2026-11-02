package io.github.soleworks.validity

/**
 * The default messages of the constraints of Paraguay, in `io.github.soleworks.validity.constraints.paraguay`.
 *
 * Change them with [Messages.paraguay].
 */
@ValidityDsl
public class ParaguayMessages internal constructor() {
    /** Default message of `ruc`. */
    public var ruc: String = "must be a valid RUC"

    /** Default message of `ci`. */
    public var ci: String = "must be a valid CI"
}
