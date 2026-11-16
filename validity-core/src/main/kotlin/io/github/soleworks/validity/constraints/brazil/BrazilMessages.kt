package io.github.soleworks.validity.constraints.brazil

import io.github.soleworks.validity.ValidityDsl

/**
 * The default messages of the constraints of Brazil.
 *
 * Change them with [Messages.brazil][io.github.soleworks.validity.Messages.brazil].
 */
@ValidityDsl
public class BrazilMessages internal constructor() {
    /** Default message of `cpf`. */
    public var cpf: String = "must be a valid CPF"

    /** Default message of `cnpj`. */
    public var cnpj: String = "must be a valid CNPJ"

    /** Default message of `cnh`. */
    public var cnh: String = "must be a valid CNH"

    /** Default message of `pis`. */
    public var pis: String = "must be a valid PIS"

    /** Default message of `cep`. */
    public var cep: String = "must be a valid CEP"

    /** Default message of `placa`. */
    public var placa: String = "must be a valid license plate"
}
