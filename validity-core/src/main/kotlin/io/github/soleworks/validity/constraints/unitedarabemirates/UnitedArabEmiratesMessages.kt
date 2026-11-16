package io.github.soleworks.validity.constraints.unitedarabemirates

import io.github.soleworks.validity.ValidityDsl

/**
 * The default messages of the constraints of the United Arab Emirates.
 *
 * Change them with [Messages.unitedArabEmirates][io.github.soleworks.validity.Messages.unitedArabEmirates].
 */
@ValidityDsl
public class UnitedArabEmiratesMessages internal constructor() {
    /** Default message of `tradeLicense`. */
    public var tradeLicense: String = "must be a valid trade license number"

    /** Default message of `emiratesId`. */
    public var emiratesId: String = "must be a valid Emirates ID"
}
