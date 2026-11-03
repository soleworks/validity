package io.github.soleworks.validity

/**
 * The default messages of the constraints of South Korea, in `io.github.soleworks.validity.constraints.southkorea`.
 *
 * Change them with [Messages.southKorea].
 */
@ValidityDsl
public class SouthKoreaMessages internal constructor() {
    /** Default message of `brn`. */
    public var brn: String = "must be a valid business registration number"

    /** Default message of `rrn`. */
    public var rrn: String = "must be a valid resident registration number"
}
