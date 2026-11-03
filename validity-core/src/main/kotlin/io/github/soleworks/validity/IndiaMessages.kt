package io.github.soleworks.validity

/**
 * The default messages of the constraints of India, in `io.github.soleworks.validity.constraints.india`.
 *
 * Change them with [Messages.india].
 */
@ValidityDsl
public class IndiaMessages internal constructor() {
    /** Default message of `pan`. */
    public var pan: String = "must be a valid PAN"

    /** Default message of `gstin`. */
    public var gstin: String = "must be a valid GSTIN"
}
