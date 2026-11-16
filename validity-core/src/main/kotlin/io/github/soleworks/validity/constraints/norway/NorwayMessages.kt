package io.github.soleworks.validity.constraints.norway

import io.github.soleworks.validity.ValidityDsl

/**
 * The default messages of the constraints of Norway.
 *
 * Change them with [Messages.norway][io.github.soleworks.validity.Messages.norway].
 */
@ValidityDsl
public class NorwayMessages internal constructor() {
    /** Default message of `organisasjonsnummer`. */
    public var organisasjonsnummer: String = "must be a valid organisasjonsnummer"

    /** Default message of `fodselsnummer`. */
    public var fodselsnummer: String = "must be a valid fødselsnummer"
}
