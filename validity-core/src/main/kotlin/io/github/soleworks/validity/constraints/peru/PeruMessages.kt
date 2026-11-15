package io.github.soleworks.validity.constraints.peru

import io.github.soleworks.validity.ValidityDsl

/**
 * The default messages of the constraints of Peru.
 *
 * Change them with [Messages.peru][io.github.soleworks.validity.Messages.peru].
 */
@ValidityDsl
public class PeruMessages internal constructor() {
    /** Default message of `ruc`. */
    public var ruc: String = "must be a valid RUC"

    /** Default message of `dni`. */
    public var dni: String = "must be a valid DNI"
}
