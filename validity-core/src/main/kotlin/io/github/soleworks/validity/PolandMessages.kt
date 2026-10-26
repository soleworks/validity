package io.github.soleworks.validity

/**
 * The default messages of the constraints of Poland, in `io.github.soleworks.validity.constraints.poland`.
 *
 * Change them with [Messages.poland].
 */
@ValidityDsl
public class PolandMessages internal constructor() {
    /** Default message of `pesel`. */
    public var pesel: String = "must be a valid PESEL"

    /** Default message of `nip`. */
    public var nip: String = "must be a valid NIP"
}
