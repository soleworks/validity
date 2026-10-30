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
}
