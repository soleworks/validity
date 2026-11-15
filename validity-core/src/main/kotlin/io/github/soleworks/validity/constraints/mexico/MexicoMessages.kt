package io.github.soleworks.validity.constraints.mexico

import io.github.soleworks.validity.ValidityDsl

/**
 * The default messages of the constraints of Mexico.
 *
 * Change them with [Messages.mexico][io.github.soleworks.validity.Messages.mexico].
 */
@ValidityDsl
public class MexicoMessages internal constructor() {
    /** Default message of `rfc`. */
    public var rfc: String = "must be a valid RFC"
}
