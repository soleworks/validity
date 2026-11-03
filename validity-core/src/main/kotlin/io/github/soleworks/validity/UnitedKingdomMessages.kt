package io.github.soleworks.validity

/**
 * The default messages of the constraints of the United Kingdom, in
 * `io.github.soleworks.validity.constraints.unitedkingdom`.
 *
 * Change them with [Messages.unitedKingdom].
 */
@ValidityDsl
public class UnitedKingdomMessages internal constructor() {
    /** Default message of `vat`. */
    public var vat: String = "must be a valid VAT number"

    /** Default message of `nino`. */
    public var nino: String = "must be a valid National Insurance number"
}
