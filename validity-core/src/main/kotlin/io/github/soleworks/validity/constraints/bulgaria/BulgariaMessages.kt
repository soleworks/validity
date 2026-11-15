package io.github.soleworks.validity.constraints.bulgaria

import io.github.soleworks.validity.ValidityDsl

/**
 * The default messages of the constraints of Bulgaria.
 *
 * Change them with [Messages.bulgaria][io.github.soleworks.validity.Messages.bulgaria].
 */
@ValidityDsl
public class BulgariaMessages internal constructor() {
    /** Default message of `egn`. */
    public var egn: String = "must be a valid EGN"
}
