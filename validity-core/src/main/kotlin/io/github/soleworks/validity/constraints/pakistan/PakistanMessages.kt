package io.github.soleworks.validity.constraints.pakistan

import io.github.soleworks.validity.ValidityDsl

/**
 * The default messages of the constraints of Pakistan.
 *
 * Change them with [Messages.pakistan][io.github.soleworks.validity.Messages.pakistan].
 */
@ValidityDsl
public class PakistanMessages internal constructor() {
    /** Default message of `cnic`. */
    public var cnic: String = "must be a valid CNIC"
}
