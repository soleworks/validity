package io.github.soleworks.validity

/**
 * The default messages of the constraints of China, in `io.github.soleworks.validity.constraints.china`.
 *
 * Change them with [Messages.china].
 */
@ValidityDsl
public class ChinaMessages internal constructor() {
    /** Default message of `uscc`. */
    public var uscc: String = "must be a valid unified social credit code"
}
