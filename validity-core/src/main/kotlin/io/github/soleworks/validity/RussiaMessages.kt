package io.github.soleworks.validity

/**
 * The default messages of the constraints of Russia, in `io.github.soleworks.validity.constraints.russia`.
 *
 * Change them with [Messages.russia].
 */
@ValidityDsl
public class RussiaMessages internal constructor() {
    /** Default message of `innIndividual`. */
    public var innIndividual: String = "must be a valid individual INN"

    /** Default message of `innLegalEntity`. */
    public var innLegalEntity: String = "must be a valid legal entity INN"
}
