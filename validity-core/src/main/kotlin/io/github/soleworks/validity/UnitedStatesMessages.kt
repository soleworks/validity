package io.github.soleworks.validity

/**
 * The default messages of the constraints of the United States, in
 * `io.github.soleworks.validity.constraints.unitedstates`.
 *
 * Change them with [Messages.unitedStates].
 */
@ValidityDsl
public class UnitedStatesMessages internal constructor() {
    /** Default message of `ein`. */
    public var ein: String = "must be a valid EIN"

    /** Default message of `ssn`. */
    public var ssn: String = "must be a valid SSN"

    /** Default message of `itin`. */
    public var itin: String = "must be a valid ITIN"
}
