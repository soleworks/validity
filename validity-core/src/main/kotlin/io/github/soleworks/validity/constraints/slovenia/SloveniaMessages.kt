package io.github.soleworks.validity.constraints.slovenia

import io.github.soleworks.validity.ValidityDsl

/**
 * The default messages of the constraints of Slovenia.
 *
 * Change them with [Messages.slovenia][io.github.soleworks.validity.Messages.slovenia].
 */
@ValidityDsl
public class SloveniaMessages internal constructor() {
    /** Default message of `davcnaStevilka`. */
    public var davcnaStevilka: String = "must be a valid davčna številka"
}
