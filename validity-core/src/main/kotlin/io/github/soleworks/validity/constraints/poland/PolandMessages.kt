package io.github.soleworks.validity.constraints.poland

import io.github.soleworks.validity.ValidityDsl

/**
 * The default messages of the constraints of Poland.
 *
 * Change them with [Messages.poland][io.github.soleworks.validity.Messages.poland].
 */
@ValidityDsl
public class PolandMessages internal constructor() {
    /** Default message of `pesel`. */
    public var pesel: String = "must be a valid PESEL"

    /** Default message of `nip`. */
    public var nip: String = "must be a valid NIP"
}
