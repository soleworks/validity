package io.github.soleworks.validity

/**
 * The default messages of the constraints of Australia, in `io.github.soleworks.validity.constraints.australia`.
 *
 * Change them with [Messages.australia].
 */
@ValidityDsl
public class AustraliaMessages internal constructor() {
    /** Default message of `abn`. */
    public var abn: String = "must be a valid ABN"

    /** Default message of `tfn`. */
    public var tfn: String = "must be a valid TFN"
}
