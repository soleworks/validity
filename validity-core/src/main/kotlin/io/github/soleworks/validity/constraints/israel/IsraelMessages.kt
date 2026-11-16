package io.github.soleworks.validity.constraints.israel

import io.github.soleworks.validity.ValidityDsl

/**
 * The default messages of the constraints of Israel.
 *
 * Change them with [Messages.israel][io.github.soleworks.validity.Messages.israel].
 */
@ValidityDsl
public class IsraelMessages internal constructor() {
    /** Default message of `teudatZehut`. */
    public var teudatZehut: String = "must be a valid teudat zehut"

    /** Default message of `companyNumber`. */
    public var companyNumber: String = "must be a valid company number"
}
