package io.github.soleworks.validity.constraints.thailand

import io.github.soleworks.validity.ValidityDsl

/**
 * The default messages of the constraints of Thailand.
 *
 * Change them with [Messages.thailand][io.github.soleworks.validity.Messages.thailand].
 */
@ValidityDsl
public class ThailandMessages internal constructor() {
    /** Default message of `nationalId`. */
    public var nationalId: String = "must be a valid national identification number"
}
