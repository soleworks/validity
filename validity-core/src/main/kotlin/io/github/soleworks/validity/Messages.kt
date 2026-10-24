package io.github.soleworks.validity

@ValidityDsl
public class Messages internal constructor() {
    public var required: String = "is required"
    public var forbidden: String = "must be null"
    public var atLeastOneOf: String = "at least one of {fields} is required"
    public var atMostOneOf: String = "at most one of {fields} can be filled"
    public var exactlyOneOf: String = "exactly one of {fields} must be filled"
    public var allOrNoneOf: String = "{fields} must be filled together"
    public var atLeastOf: String = "at least {count} of {fields} must be filled"
    public var atMostOf: String = "at most {count} of {fields} can be filled"
    public var exactlyOf: String = "exactly {count} of {fields} must be filled"
    public var equalTo: String = "must be equal to {other}"
    public var notEqualTo: String = "must not be equal to {other}"
    public var oneOf: String = "must be one of {values}"
    public var enum: String = "must be one of {values}"
    public var noneOf: String = "must not be one of {values}"
    public var or: String = "{left} or {right}"
    public var and: String = "{left} and {right}"
    public var not: String = "is not allowed"

    public var min: String = "must be at least {min}"
    public var max: String = "must be at most {max}"
    public var greaterThan: String = "must be greater than {other}"
    public var lessThan: String = "must be less than {other}"
    public var between: String = "must be between {min} and {max}"

    public var minLength: String = "must have at least {min} characters"
    public var maxLength: String = "must have at most {max} characters"
    public var length: String = "must have exactly {length} characters"
    public var lengthBetween: String = "must have between {min} and {max} characters"
    public var notEmpty: String = "must not be empty"
    public var notBlank: String = "must not be blank"
    public var matches: String = "must match {regex}"
    public var notMatches: String = "must not match {regex}"
    public var contains: String = "must contain {text}"
    public var notContains: String = "must not contain {text}"
    public var startsWith: String = "must start with {prefix}"
    public var notStartsWith: String = "must not start with {prefix}"
    public var endsWith: String = "must end with {suffix}"
    public var notEndsWith: String = "must not end with {suffix}"
    public var uppercase: String = "must be uppercase"
    public var lowercase: String = "must be lowercase"
    public var letters: String = "must contain only letters"
    public var digits: String = "must contain only digits"
    public var lettersOrDigits: String = "must contain only letters or digits"
    public var ascii: String = "must contain only ASCII characters"

    public var positive: String = "must be positive"
    public var negative: String = "must be negative"
    public var positiveOrZero: String = "must be positive or zero"
    public var negativeOrZero: String = "must be negative or zero"
    public var multipleOf: String = "must be a multiple of {factor}"
    public var finite: String = "must be finite"
    public var maxDecimalPlaces: String = "must have at most {max} decimal places"
    public var maxIntegerDigits: String = "must have at most {max} integer digits"

    public var past: String = "must be in the past"
    public var future: String = "must be in the future"
    public var pastOrPresent: String = "must be in the past or present"
    public var futureOrPresent: String = "must be in the future or present"
    public var after: String = "must be after {other}"
    public var before: String = "must be before {other}"
    public var afterOrEqual: String = "must be after or equal to {other}"
    public var beforeOrEqual: String = "must be before or equal to {other}"
    public var betweenDates: String = "must be between {start} and {end}"

    public var minSize: String = "must have at least {min} items"
    public var maxSize: String = "must have at most {max} items"
    public var size: String = "must have exactly {size} items"
    public var sizeBetween: String = "must have between {min} and {max} items"
    public var distinct: String = "must not contain duplicates"
    public var containsElement: String = "must contain {element}"
    public var containsAll: String = "must contain all of {elements}"
    public var containsAny: String = "must contain any of {elements}"

    public var minEntries: String = "must have at least {min} entries"
    public var maxEntries: String = "must have at most {max} entries"
    public var entries: String = "must have exactly {size} entries"
    public var entriesBetween: String = "must have between {min} and {max} entries"
    public var containsKey: String = "must contain the key {key}"
    public var containsKeys: String = "must contain the keys {keys}"

    public var email: String = "must be a valid email"
    public var url: String = "must be a valid URL"
    public var hostname: String = "must be a valid hostname"
    public var ipv4: String = "must be a valid IPv4 address"
    public var ipv6: String = "must be a valid IPv6 address"
    public var ip: String = "must be a valid IP address"
    public var cidr: String = "must be a valid CIDR block"
    public var macAddress: String = "must be a valid MAC address"
    public var slug: String = "must be a valid slug"
    public var jwt: String = "must be a valid JWT"
    public var dataUri: String = "must be a valid data URI"
    public var mimeType: String = "must be a valid MIME type"

    public var uuid: String = "must be a valid UUID"
    public var ulid: String = "must be a valid ULID"
    public var objectId: String = "must be a valid ObjectId"
    public var semver: String = "must be a valid semantic version"
    public var isbn: String = "must be a valid ISBN"
    public var isbn10: String = "must be a valid ISBN-10"
    public var isbn13: String = "must be a valid ISBN-13"
    public var issn: String = "must be a valid ISSN"
    public var ean: String = "must be a valid EAN"
    public var isrc: String = "must be a valid ISRC"
    public var imei: String = "must be a valid IMEI"
    public var luhn: String = "must have a valid Luhn check digit"
    public var hash: String = "must be a valid {algorithm} hash"

    public var base64: String = "must be valid Base64"
    public var base64Url: String = "must be valid URL-safe Base64"
    public var base32: String = "must be valid Base32"
    public var base58: String = "must be valid Base58"
    public var hexadecimal: String = "must be hexadecimal"
    public var json: String = "must be valid JSON"

    public var hexColor: String = "must be a valid hexadecimal color"
    public var rgbColor: String = "must be a valid RGB color"
    public var hslColor: String = "must be a valid HSL color"

    public var creditCard: String = "must be a valid credit card number"
    public var iban: String = "must be a valid IBAN"
    public var bic: String = "must be a valid BIC"
    public var isin: String = "must be a valid ISIN"
    public var currencyCode: String = "must be a valid ISO 4217 currency code"
    public var bitcoinAddress: String = "must be a valid Bitcoin address"
    public var ethereumAddress: String = "must be a valid Ethereum address"

    public var isoCountryCode: String = "must be a valid ISO 3166-1 alpha-2 country code"
    public var isoCountryCodeAlpha3: String = "must be a valid ISO 3166-1 alpha-3 country code"
    public var languageCode: String = "must be a valid ISO 639-1 language code"
    public var locale: String = "must be a valid locale"
    public var timeZone: String = "must be a valid time zone"
    public var latitude: String = "must be a valid latitude"
    public var longitude: String = "must be a valid longitude"
    public var postalCode: String = "must be a valid postal code for {country}"

    public var numeric: String = "must be numeric"
    public var integer: String = "must be an integer"
    public var containsUppercase: String = "must contain an uppercase letter"
    public var containsLowercase: String = "must contain a lowercase letter"
    public var containsDigit: String = "must contain a digit"
    public var containsSymbol: String = "must contain a symbol"

    public var isoDate: String = "must be an ISO 8601 date"
    public var isoTime: String = "must be an ISO 8601 time"
    public var isoDateTime: String = "must be an ISO 8601 date and time with offset"
    public var isoDuration: String = "must be an ISO 8601 duration"
    public var dateFormat: String = "must match the date format {pattern}"

    internal val brazil: BrazilMessages = BrazilMessages()
    internal val bulgaria: BulgariaMessages = BulgariaMessages()
    internal val canada: CanadaMessages = CanadaMessages()
    internal val france: FranceMessages = FranceMessages()
    internal val israel: IsraelMessages = IsraelMessages()
    internal val netherlands: NetherlandsMessages = NetherlandsMessages()
    internal val pakistan: PakistanMessages = PakistanMessages()
    internal val poland: PolandMessages = PolandMessages()
    internal val russia: RussiaMessages = RussiaMessages()
    internal val slovenia: SloveniaMessages = SloveniaMessages()
    internal val spain: SpainMessages = SpainMessages()
    internal val thailand: ThailandMessages = ThailandMessages()
    internal val ukraine: UkraineMessages = UkraineMessages()
    internal val unitedStates: UnitedStatesMessages = UnitedStatesMessages()

    public fun brazil(
        block: BrazilMessages.() -> Unit
    ): Unit = brazil.block()

    public fun bulgaria(
        block: BulgariaMessages.() -> Unit
    ): Unit = bulgaria.block()

    public fun canada(
        block: CanadaMessages.() -> Unit
    ): Unit = canada.block()

    public fun france(
        block: FranceMessages.() -> Unit
    ): Unit = france.block()

    public fun israel(
        block: IsraelMessages.() -> Unit
    ): Unit = israel.block()

    public fun netherlands(
        block: NetherlandsMessages.() -> Unit
    ): Unit = netherlands.block()

    public fun pakistan(
        block: PakistanMessages.() -> Unit
    ): Unit = pakistan.block()

    public fun poland(
        block: PolandMessages.() -> Unit
    ): Unit = poland.block()

    public fun russia(
        block: RussiaMessages.() -> Unit
    ): Unit = russia.block()

    public fun slovenia(
        block: SloveniaMessages.() -> Unit
    ): Unit = slovenia.block()

    public fun spain(
        block: SpainMessages.() -> Unit
    ): Unit = spain.block()

    public fun thailand(
        block: ThailandMessages.() -> Unit
    ): Unit = thailand.block()

    public fun ukraine(
        block: UkraineMessages.() -> Unit
    ): Unit = ukraine.block()

    public fun unitedStates(
        block: UnitedStatesMessages.() -> Unit
    ): Unit = unitedStates.block()
}

@ValidityDsl
public class BrazilMessages internal constructor() {
    public var cpf: String = "must be a valid CPF"
    public var cnpj: String = "must be a valid CNPJ"
    public var cnh: String = "must be a valid CNH"
    public var pis: String = "must be a valid PIS"
    public var cep: String = "must be a valid CEP"
    public var placa: String = "must be a valid license plate"
}

@ValidityDsl
public class BulgariaMessages internal constructor() {
    public var egn: String = "must be a valid EGN"
}

@ValidityDsl
public class CanadaMessages internal constructor() {
    public var sin: String = "must be a valid SIN"
}

@ValidityDsl
public class FranceMessages internal constructor() {
    public var spi: String = "must be a valid SPI"
}

@ValidityDsl
public class IsraelMessages internal constructor() {
    public var teudatZehut: String = "must be a valid teudat zehut"
}

@ValidityDsl
public class NetherlandsMessages internal constructor() {
    public var bsn: String = "must be a valid BSN"
}

@ValidityDsl
public class PakistanMessages internal constructor() {
    public var cnic: String = "must be a valid CNIC"
}

@ValidityDsl
public class PolandMessages internal constructor() {
    public var pesel: String = "must be a valid PESEL"
    public var nip: String = "must be a valid NIP"
}

@ValidityDsl
public class RussiaMessages internal constructor() {
    public var innIndividual: String = "must be a valid individual INN"
    public var innLegalEntity: String = "must be a valid legal entity INN"
}

@ValidityDsl
public class SloveniaMessages internal constructor() {
    public var davcnaStevilka: String = "must be a valid davčna številka"
}

@ValidityDsl
public class SpainMessages internal constructor() {
    public var dni: String = "must be a valid DNI"
    public var nie: String = "must be a valid NIE"
}

@ValidityDsl
public class ThailandMessages internal constructor() {
    public var nationalId: String = "must be a valid national identification number"
}

@ValidityDsl
public class UkraineMessages internal constructor() {
    public var rnokpp: String = "must be a valid RNOKPP"
}

@ValidityDsl
public class UnitedStatesMessages internal constructor() {
    public var ein: String = "must be a valid EIN"
}
