package io.github.soleworks.validity

/**
 * The default messages of the constraints of Belgium, in `io.github.soleworks.validity.constraints.belgium`.
 *
 * Change them with [Messages.belgium].
 */
@ValidityDsl
public class BelgiumMessages internal constructor() {
    /** Default message of `bce`. */
    public var bce: String = "must be a valid enterprise number"

    /** Default message of `nationalNumber`. */
    public var nationalNumber: String = "must be a valid national number"
}
