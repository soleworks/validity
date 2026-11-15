package io.github.soleworks.validity.constraints.india

import io.github.soleworks.validity.ValidityDsl

/**
 * The default messages of the constraints of India.
 *
 * Change them with [Messages.india][io.github.soleworks.validity.Messages.india].
 */
@ValidityDsl
public class IndiaMessages internal constructor() {
    /** Default message of `pan`. */
    public var pan: String = "must be a valid PAN"

    /** Default message of `gstin`. */
    public var gstin: String = "must be a valid GSTIN"
}
