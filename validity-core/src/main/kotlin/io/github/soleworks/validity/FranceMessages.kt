package io.github.soleworks.validity

/**
 * The default messages of the constraints of France, in `io.github.soleworks.validity.constraints.france`.
 *
 * Change them with [Messages.france].
 */
@ValidityDsl
public class FranceMessages internal constructor() {
    /** Default message of `spi`. */
    public var spi: String = "must be a valid SPI"

    /** Default message of `siren`. */
    public var siren: String = "must be a valid SIREN"

    /** Default message of `siret`. */
    public var siret: String = "must be a valid SIRET"
}
