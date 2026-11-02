package io.github.soleworks.validity

/**
 * The default messages of the constraints of Norway, in `io.github.soleworks.validity.constraints.norway`.
 *
 * Change them with [Messages.norway].
 */
@ValidityDsl
public class NorwayMessages internal constructor() {
    /** Default message of `organisasjonsnummer`. */
    public var organisasjonsnummer: String = "must be a valid organisasjonsnummer"

    /** Default message of `fodselsnummer`. */
    public var fodselsnummer: String = "must be a valid fødselsnummer"
}
