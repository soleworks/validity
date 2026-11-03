package io.github.soleworks.validity

/**
 * The default messages of the constraints of South Africa, in `io.github.soleworks.validity.constraints.southafrica`.
 *
 * Change them with [Messages.southAfrica].
 */
@ValidityDsl
public class SouthAfricaMessages internal constructor() {
    /** Default message of `cipc`. */
    public var cipc: String = "must be a valid company registration number"

    /** Default message of `idNumber`. */
    public var idNumber: String = "must be a valid ID number"
}
