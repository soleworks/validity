package io.github.soleworks.validity

@ValidityDsl
public class Messages internal constructor() {
    public var required: String = "is required"
    public var forbidden: String = "must be null"
    public var atLeastOneOf: String = "at least one of {fields} is required"
    public var atMostOneOf: String = "at most one of {fields} can be filled"
    public var exactlyOneOf: String = "exactly one of {fields} must be filled"
    public var allOrNoneOf: String = "{fields} must be filled together"
    public var equalTo: String = "must be equal to {other}"
    public var notEqualTo: String = "must not be equal to {other}"
    public var oneOf: String = "must be one of {values}"
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

    internal val argentina: ArgentinaMessages = ArgentinaMessages()
    internal val austria: AustriaMessages = AustriaMessages()
    internal val belgium: BelgiumMessages = BelgiumMessages()
    internal val brazil: BrazilMessages = BrazilMessages()
    internal val bulgaria: BulgariaMessages = BulgariaMessages()
    internal val canada: CanadaMessages = CanadaMessages()
    internal val china: ChinaMessages = ChinaMessages()
    internal val cyprus: CyprusMessages = CyprusMessages()
    internal val czechRepublic: CzechRepublicMessages = CzechRepublicMessages()
    internal val denmark: DenmarkMessages = DenmarkMessages()
    internal val estonia: EstoniaMessages = EstoniaMessages()
    internal val finland: FinlandMessages = FinlandMessages()
    internal val france: FranceMessages = FranceMessages()
    internal val germany: GermanyMessages = GermanyMessages()
    internal val greece: GreeceMessages = GreeceMessages()
    internal val hongKong: HongKongMessages = HongKongMessages()
    internal val india: IndiaMessages = IndiaMessages()
    internal val iran: IranMessages = IranMessages()
    internal val ireland: IrelandMessages = IrelandMessages()
    internal val israel: IsraelMessages = IsraelMessages()
    internal val italy: ItalyMessages = ItalyMessages()
    internal val latvia: LatviaMessages = LatviaMessages()
    internal val libya: LibyaMessages = LibyaMessages()
    internal val lithuania: LithuaniaMessages = LithuaniaMessages()
    internal val malta: MaltaMessages = MaltaMessages()
    internal val netherlands: NetherlandsMessages = NetherlandsMessages()
    internal val norway: NorwayMessages = NorwayMessages()
    internal val pakistan: PakistanMessages = PakistanMessages()
    internal val poland: PolandMessages = PolandMessages()
    internal val portugal: PortugalMessages = PortugalMessages()
    internal val romania: RomaniaMessages = RomaniaMessages()
    internal val russia: RussiaMessages = RussiaMessages()
    internal val slovakia: SlovakiaMessages = SlovakiaMessages()
    internal val slovenia: SloveniaMessages = SloveniaMessages()
    internal val southKorea: SouthKoreaMessages = SouthKoreaMessages()
    internal val spain: SpainMessages = SpainMessages()
    internal val sriLanka: SriLankaMessages = SriLankaMessages()
    internal val sweden: SwedenMessages = SwedenMessages()
    internal val taiwan: TaiwanMessages = TaiwanMessages()
    internal val thailand: ThailandMessages = ThailandMessages()
    internal val tunisia: TunisiaMessages = TunisiaMessages()
    internal val ukraine: UkraineMessages = UkraineMessages()
    internal val unitedKingdom: UnitedKingdomMessages = UnitedKingdomMessages()
    internal val unitedStates: UnitedStatesMessages = UnitedStatesMessages()

    public fun argentina(
        block: ArgentinaMessages.() -> Unit
    ): Unit = argentina.block()

    public fun austria(
        block: AustriaMessages.() -> Unit
    ): Unit = austria.block()

    public fun belgium(
        block: BelgiumMessages.() -> Unit
    ): Unit = belgium.block()

    public fun brazil(
        block: BrazilMessages.() -> Unit
    ): Unit = brazil.block()

    public fun bulgaria(
        block: BulgariaMessages.() -> Unit
    ): Unit = bulgaria.block()

    public fun canada(
        block: CanadaMessages.() -> Unit
    ): Unit = canada.block()

    public fun china(
        block: ChinaMessages.() -> Unit
    ): Unit = china.block()

    public fun cyprus(
        block: CyprusMessages.() -> Unit
    ): Unit = cyprus.block()

    public fun czechRepublic(
        block: CzechRepublicMessages.() -> Unit
    ): Unit = czechRepublic.block()

    public fun denmark(
        block: DenmarkMessages.() -> Unit
    ): Unit = denmark.block()

    public fun estonia(
        block: EstoniaMessages.() -> Unit
    ): Unit = estonia.block()

    public fun finland(
        block: FinlandMessages.() -> Unit
    ): Unit = finland.block()

    public fun france(
        block: FranceMessages.() -> Unit
    ): Unit = france.block()

    public fun germany(
        block: GermanyMessages.() -> Unit
    ): Unit = germany.block()

    public fun greece(
        block: GreeceMessages.() -> Unit
    ): Unit = greece.block()

    public fun hongKong(
        block: HongKongMessages.() -> Unit
    ): Unit = hongKong.block()

    public fun india(
        block: IndiaMessages.() -> Unit
    ): Unit = india.block()

    public fun iran(
        block: IranMessages.() -> Unit
    ): Unit = iran.block()

    public fun ireland(
        block: IrelandMessages.() -> Unit
    ): Unit = ireland.block()

    public fun israel(
        block: IsraelMessages.() -> Unit
    ): Unit = israel.block()

    public fun italy(
        block: ItalyMessages.() -> Unit
    ): Unit = italy.block()

    public fun latvia(
        block: LatviaMessages.() -> Unit
    ): Unit = latvia.block()

    public fun libya(
        block: LibyaMessages.() -> Unit
    ): Unit = libya.block()

    public fun lithuania(
        block: LithuaniaMessages.() -> Unit
    ): Unit = lithuania.block()

    public fun malta(
        block: MaltaMessages.() -> Unit
    ): Unit = malta.block()

    public fun netherlands(
        block: NetherlandsMessages.() -> Unit
    ): Unit = netherlands.block()

    public fun norway(
        block: NorwayMessages.() -> Unit
    ): Unit = norway.block()

    public fun pakistan(
        block: PakistanMessages.() -> Unit
    ): Unit = pakistan.block()

    public fun poland(
        block: PolandMessages.() -> Unit
    ): Unit = poland.block()

    public fun portugal(
        block: PortugalMessages.() -> Unit
    ): Unit = portugal.block()

    public fun romania(
        block: RomaniaMessages.() -> Unit
    ): Unit = romania.block()

    public fun russia(
        block: RussiaMessages.() -> Unit
    ): Unit = russia.block()

    public fun slovakia(
        block: SlovakiaMessages.() -> Unit
    ): Unit = slovakia.block()

    public fun slovenia(
        block: SloveniaMessages.() -> Unit
    ): Unit = slovenia.block()

    public fun southKorea(
        block: SouthKoreaMessages.() -> Unit
    ): Unit = southKorea.block()

    public fun spain(
        block: SpainMessages.() -> Unit
    ): Unit = spain.block()

    public fun sriLanka(
        block: SriLankaMessages.() -> Unit
    ): Unit = sriLanka.block()

    public fun sweden(
        block: SwedenMessages.() -> Unit
    ): Unit = sweden.block()

    public fun taiwan(
        block: TaiwanMessages.() -> Unit
    ): Unit = taiwan.block()

    public fun thailand(
        block: ThailandMessages.() -> Unit
    ): Unit = thailand.block()

    public fun tunisia(
        block: TunisiaMessages.() -> Unit
    ): Unit = tunisia.block()

    public fun ukraine(
        block: UkraineMessages.() -> Unit
    ): Unit = ukraine.block()

    public fun unitedKingdom(
        block: UnitedKingdomMessages.() -> Unit
    ): Unit = unitedKingdom.block()

    public fun unitedStates(
        block: UnitedStatesMessages.() -> Unit
    ): Unit = unitedStates.block()
}

@ValidityDsl
public class ArgentinaMessages internal constructor() {
    public var cuit: String = "must be a valid CUIT"
    public var cuil: String = "must be a valid CUIL"
}

@ValidityDsl
public class AustriaMessages internal constructor() {
    public var abgabenkontonummer: String = "must be a valid Abgabenkontonummer"
}

@ValidityDsl
public class BelgiumMessages internal constructor() {
    public var rijksregisternummer: String = "must be a valid rijksregisternummer"
}

@ValidityDsl
public class BrazilMessages internal constructor() {
    public var cpf: String = "must be a valid CPF"
    public var cnpj: String = "must be a valid CNPJ"
    public var cnh: String = "must be a valid CNH"
    public var pis: String = "must be a valid PIS"
    public var tituloEleitoral: String = "must be a valid título eleitoral"
    public var chaveNfe: String = "must be a valid NF-e access key"
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
public class ChinaMessages internal constructor() {
    public var residentId: String = "must be a valid resident identity card number"
}

@ValidityDsl
public class CyprusMessages internal constructor() {
    public var afm: String = "must be a valid AFM"
}

@ValidityDsl
public class CzechRepublicMessages internal constructor() {
    public var rodneCislo: String = "must be a valid rodné číslo"
}

@ValidityDsl
public class DenmarkMessages internal constructor() {
    public var cpr: String = "must be a valid CPR number"
}

@ValidityDsl
public class EstoniaMessages internal constructor() {
    public var isikukood: String = "must be a valid isikukood"
}

@ValidityDsl
public class FinlandMessages internal constructor() {
    public var hetu: String = "must be a valid HETU"
}

@ValidityDsl
public class FranceMessages internal constructor() {
    public var spi: String = "must be a valid SPI"
}

@ValidityDsl
public class GermanyMessages internal constructor() {
    public var steuerId: String = "must be a valid Steuer-IdNr"
}

@ValidityDsl
public class GreeceMessages internal constructor() {
    public var afm: String = "must be a valid AFM"
}

@ValidityDsl
public class HongKongMessages internal constructor() {
    public var hkid: String = "must be a valid HKID"
}

@ValidityDsl
public class IndiaMessages internal constructor() {
    public var aadhaar: String = "must be a valid Aadhaar"
    public var pan: String = "must be a valid PAN"
}

@ValidityDsl
public class IranMessages internal constructor() {
    public var codeMelli: String = "must be a valid national identity code"
}

@ValidityDsl
public class IrelandMessages internal constructor() {
    public var pps: String = "must be a valid PPS number"
}

@ValidityDsl
public class IsraelMessages internal constructor() {
    public var teudatZehut: String = "must be a valid teudat zehut"
}

@ValidityDsl
public class ItalyMessages internal constructor() {
    public var codiceFiscale: String = "must be a valid codice fiscale"
    public var cie: String = "must be a valid CIE number"
}

@ValidityDsl
public class LatviaMessages internal constructor() {
    public var personasKods: String = "must be a valid personas kods"
}

@ValidityDsl
public class LibyaMessages internal constructor() {
    public var nin: String = "must be a valid NIN"
}

@ValidityDsl
public class LithuaniaMessages internal constructor() {
    public var asmensKodas: String = "must be a valid asmens kodas"
}

@ValidityDsl
public class MaltaMessages internal constructor() {
    public var idCardNumber: String = "must be a valid ID card number"
}

@ValidityDsl
public class NetherlandsMessages internal constructor() {
    public var bsn: String = "must be a valid BSN"
}

@ValidityDsl
public class NorwayMessages internal constructor() {
    public var fodselsnummer: String = "must be a valid fødselsnummer"
}

@ValidityDsl
public class PakistanMessages internal constructor() {
    public var cnic: String = "must be a valid CNIC"
}

@ValidityDsl
public class PolandMessages internal constructor() {
    public var pesel: String = "must be a valid PESEL"
    public var nip: String = "must be a valid NIP"
    public var regon: String = "must be a valid REGON"
    public var dowodOsobisty: String = "must be a valid dowód osobisty number"
}

@ValidityDsl
public class PortugalMessages internal constructor() {
    public var nif: String = "must be a valid NIF"
}

@ValidityDsl
public class RomaniaMessages internal constructor() {
    public var cnp: String = "must be a valid CNP"
}

@ValidityDsl
public class RussiaMessages internal constructor() {
    public var innIndividual: String = "must be a valid individual INN"
    public var innLegalEntity: String = "must be a valid legal entity INN"
}

@ValidityDsl
public class SlovakiaMessages internal constructor() {
    public var rodneCislo: String = "must be a valid rodné číslo"
}

@ValidityDsl
public class SloveniaMessages internal constructor() {
    public var davcnaStevilka: String = "must be a valid davčna številka"
}

@ValidityDsl
public class SouthKoreaMessages internal constructor() {
    public var rrn: String = "must be a valid RRN"
}

@ValidityDsl
public class SpainMessages internal constructor() {
    public var dni: String = "must be a valid DNI"
    public var nie: String = "must be a valid NIE"
    public var nif: String = "must be a valid NIF"
}

@ValidityDsl
public class SriLankaMessages internal constructor() {
    public var nic: String = "must be a valid NIC"
}

@ValidityDsl
public class SwedenMessages internal constructor() {
    public var personnummer: String = "must be a valid personnummer"
    public var samordningsnummer: String = "must be a valid samordningsnummer"
}

@ValidityDsl
public class TaiwanMessages internal constructor() {
    public var nationalId: String = "must be a valid national identification number"
}

@ValidityDsl
public class ThailandMessages internal constructor() {
    public var nationalId: String = "must be a valid national identification number"
}

@ValidityDsl
public class TunisiaMessages internal constructor() {
    public var cin: String = "must be a valid national identity card number"
}

@ValidityDsl
public class UkraineMessages internal constructor() {
    public var rnokpp: String = "must be a valid RNOKPP"
}

@ValidityDsl
public class UnitedKingdomMessages internal constructor() {
    public var nino: String = "must be a valid NINO"
}

@ValidityDsl
public class UnitedStatesMessages internal constructor() {
    public var ein: String = "must be a valid EIN"
}
