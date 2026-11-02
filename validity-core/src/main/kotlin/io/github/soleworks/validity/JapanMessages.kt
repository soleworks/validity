package io.github.soleworks.validity

/**
 * The default messages of the constraints of Japan, in `io.github.soleworks.validity.constraints.japan`.
 *
 * Change them with [Messages.japan].
 */
@ValidityDsl
public class JapanMessages internal constructor() {
    /** Default message of `corporateNumber`. */
    public var corporateNumber: String = "must be a valid corporate number"

    /** Default message of `myNumber`. */
    public var myNumber: String = "must be a valid My Number"
}
