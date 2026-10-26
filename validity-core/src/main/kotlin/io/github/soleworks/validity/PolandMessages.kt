package io.github.soleworks.validity

@ValidityDsl
public class PolandMessages internal constructor() {
    public var pesel: String = "must be a valid PESEL"
    public var nip: String = "must be a valid NIP"
}
