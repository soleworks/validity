package io.github.soleworks.validity.constraints.italy

import io.github.soleworks.validity.ValidityDsl

/**
 * The default messages of the constraints of Italy.
 *
 * Change them with [Messages.italy][io.github.soleworks.validity.Messages.italy].
 */
@ValidityDsl
public class ItalyMessages internal constructor() {
    /** Default message of `partitaIva`. */
    public var partitaIva: String = "must be a valid partita IVA"

    /** Default message of `codiceFiscale`. */
    public var codiceFiscale: String = "must be a valid codice fiscale"
}
