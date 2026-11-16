package io.github.soleworks.validity.constraints.china

import io.github.soleworks.validity.ValidityDsl

/**
 * The default messages of the constraints of China.
 *
 * Change them with [Messages.china][io.github.soleworks.validity.Messages.china].
 */
@ValidityDsl
public class ChinaMessages internal constructor() {
    /** Default message of `uscc`. */
    public var uscc: String = "must be a valid unified social credit code"
}
