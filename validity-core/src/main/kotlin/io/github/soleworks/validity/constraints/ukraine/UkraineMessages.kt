package io.github.soleworks.validity.constraints.ukraine

import io.github.soleworks.validity.ValidityDsl

/**
 * The default messages of the constraints of Ukraine.
 *
 * Change them with [Messages.ukraine][io.github.soleworks.validity.Messages.ukraine].
 */
@ValidityDsl
public class UkraineMessages internal constructor() {
    /** Default message of `rnokpp`. */
    public var rnokpp: String = "must be a valid RNOKPP"
}
