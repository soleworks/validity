package io.github.soleworks.validity.constraints.southafrica

import io.github.soleworks.validity.ValidityDsl

/**
 * The default messages of the constraints of South Africa.
 *
 * Change them with [Messages.southAfrica][io.github.soleworks.validity.Messages.southAfrica].
 */
@ValidityDsl
public class SouthAfricaMessages internal constructor() {
    /** Default message of `cipc`. */
    public var cipc: String = "must be a valid company registration number"

    /** Default message of `idNumber`. */
    public var idNumber: String = "must be a valid ID number"
}
