package io.github.soleworks.validity.constraints.sweden

import io.github.soleworks.validity.ValidityDsl

/**
 * The default messages of the constraints of Sweden.
 *
 * Change them with [Messages.sweden][io.github.soleworks.validity.Messages.sweden].
 */
@ValidityDsl
public class SwedenMessages internal constructor() {
    /** Default message of `organisationsnummer`. */
    public var organisationsnummer: String = "must be a valid organisationsnummer"

    /** Default message of `personnummer`. */
    public var personnummer: String = "must be a valid personnummer"
}
