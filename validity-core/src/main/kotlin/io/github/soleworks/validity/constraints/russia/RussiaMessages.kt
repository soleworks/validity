package io.github.soleworks.validity.constraints.russia

import io.github.soleworks.validity.ValidityDsl

/**
 * The default messages of the constraints of Russia.
 *
 * Change them with [Messages.russia][io.github.soleworks.validity.Messages.russia].
 */
@ValidityDsl
public class RussiaMessages internal constructor() {
    /** Default message of `innIndividual`. */
    public var innIndividual: String = "must be a valid individual INN"

    /** Default message of `innLegalEntity`. */
    public var innLegalEntity: String = "must be a valid legal entity INN"
}
