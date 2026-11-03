package io.github.soleworks.validity

/**
 * The default messages of the constraints of Sweden, in `io.github.soleworks.validity.constraints.sweden`.
 *
 * Change them with [Messages.sweden].
 */
@ValidityDsl
public class SwedenMessages internal constructor() {
    /** Default message of `organisationsnummer`. */
    public var organisationsnummer: String = "must be a valid organisationsnummer"

    /** Default message of `personnummer`. */
    public var personnummer: String = "must be a valid personnummer"
}
