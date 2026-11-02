package io.github.soleworks.validity

/**
 * The default messages of the constraints of Germany, in `io.github.soleworks.validity.constraints.germany`.
 *
 * Change them with [Messages.germany].
 */
@ValidityDsl
public class GermanyMessages internal constructor() {
    /** Default message of `ustIdNr`. */
    public var ustIdNr: String = "must be a valid USt-IdNr."

    /** Default message of `steuerId`. */
    public var steuerId: String = "must be a valid Steuer-ID"
}
