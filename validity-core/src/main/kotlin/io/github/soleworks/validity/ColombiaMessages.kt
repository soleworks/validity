package io.github.soleworks.validity

/**
 * The default messages of the constraints of Colombia, in `io.github.soleworks.validity.constraints.colombia`.
 *
 * Change them with [Messages.colombia].
 */
@ValidityDsl
public class ColombiaMessages internal constructor() {
    /** Default message of `nit`. */
    public var nit: String = "must be a valid NIT"

    /** Default message of `cc`. */
    public var cc: String = "must be a valid CC"
}
