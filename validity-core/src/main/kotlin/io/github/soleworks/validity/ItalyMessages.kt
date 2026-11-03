package io.github.soleworks.validity

/**
 * The default messages of the constraints of Italy, in `io.github.soleworks.validity.constraints.italy`.
 *
 * Change them with [Messages.italy].
 */
@ValidityDsl
public class ItalyMessages internal constructor() {
    /** Default message of `partitaIva`. */
    public var partitaIva: String = "must be a valid partita IVA"

    /** Default message of `codiceFiscale`. */
    public var codiceFiscale: String = "must be a valid codice fiscale"
}
