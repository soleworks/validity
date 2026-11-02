package io.github.soleworks.validity

/**
 * The default messages of the constraints of Switzerland, in `io.github.soleworks.validity.constraints.switzerland`.
 *
 * Change them with [Messages.switzerland].
 */
@ValidityDsl
public class SwitzerlandMessages internal constructor() {
    /** Default message of `uid`. */
    public var uid: String = "must be a valid UID"

    /** Default message of `ahv`. */
    public var ahv: String = "must be a valid AHV number"
}
