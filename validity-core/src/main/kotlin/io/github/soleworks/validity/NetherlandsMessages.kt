package io.github.soleworks.validity

/**
 * The default messages of the constraints of the Netherlands, in
 * `io.github.soleworks.validity.constraints.netherlands`.
 *
 * Change them with [Messages.netherlands].
 */
@ValidityDsl
public class NetherlandsMessages internal constructor() {
    /** Default message of `bsn`. */
    public var bsn: String = "must be a valid BSN"
}
