package io.github.soleworks.validity

/**
 * The default messages of the constraints of Thailand, in `io.github.soleworks.validity.constraints.thailand`.
 *
 * Change them with [Messages.thailand].
 */
@ValidityDsl
public class ThailandMessages internal constructor() {
    /** Default message of `nationalId`. */
    public var nationalId: String = "must be a valid national identification number"
}
