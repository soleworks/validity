package io.github.soleworks.validity

@ValidityDsl
public class BrazilMessages internal constructor() {
    public var cpf: String = "must be a valid CPF"
    public var cnpj: String = "must be a valid CNPJ"
    public var cnh: String = "must be a valid CNH"
    public var pis: String = "must be a valid PIS"
    public var cep: String = "must be a valid CEP"
    public var placa: String = "must be a valid license plate"
}
