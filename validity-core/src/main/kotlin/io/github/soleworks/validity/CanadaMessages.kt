package io.github.soleworks.validity

/**
 * The default messages of the constraints of Canada, in `io.github.soleworks.validity.constraints.canada`.
 *
 * Change them with [Messages.canada].
 */
@ValidityDsl
public class CanadaMessages internal constructor() {
    /** Default message of `sin`. */
    public var sin: String = "must be a valid SIN"
}
