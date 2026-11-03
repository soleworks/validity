package io.github.soleworks.validity

/**
 * The default messages of the constraints of Peru, in `io.github.soleworks.validity.constraints.peru`.
 *
 * Change them with [Messages.peru].
 */
@ValidityDsl
public class PeruMessages internal constructor() {
    /** Default message of `ruc`. */
    public var ruc: String = "must be a valid RUC"

    /** Default message of `dni`. */
    public var dni: String = "must be a valid DNI"
}
