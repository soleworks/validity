package io.github.soleworks.validity.constraints.germany

import io.github.soleworks.validity.ValidityDsl

/**
 * The default messages of the constraints of Germany.
 *
 * Change them with [Messages.germany][io.github.soleworks.validity.Messages.germany].
 */
@ValidityDsl
public class GermanyMessages internal constructor() {
    /** Default message of `ustIdNr`. */
    public var ustIdNr: String = "must be a valid USt-IdNr."

    /** Default message of `steuerId`. */
    public var steuerId: String = "must be a valid Steuer-ID"
}
