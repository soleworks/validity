package io.github.soleworks.validity

/**
 * The default messages of the constraints of Pakistan, in `io.github.soleworks.validity.constraints.pakistan`.
 *
 * Change them with [Messages.pakistan].
 */
@ValidityDsl
public class PakistanMessages internal constructor() {
    /** Default message of `cnic`. */
    public var cnic: String = "must be a valid CNIC"
}
