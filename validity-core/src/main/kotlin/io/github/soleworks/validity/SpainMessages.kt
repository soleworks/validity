package io.github.soleworks.validity

/**
 * The default messages of the constraints of Spain, in `io.github.soleworks.validity.constraints.spain`.
 *
 * Change them with [Messages.spain].
 */
@ValidityDsl
public class SpainMessages internal constructor() {
    /** Default message of `dni`. */
    public var dni: String = "must be a valid DNI"

    /** Default message of `nie`. */
    public var nie: String = "must be a valid NIE"
}
