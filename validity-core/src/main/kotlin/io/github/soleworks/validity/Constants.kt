package io.github.soleworks.validity

internal object Messages {
    const val REQUIRED: String = "is required"
    const val EQUAL_TO: String = "must be equal to {other}"
    const val NOT_EQUAL_TO: String = "must not be equal to {other}"
    const val ONE_OF: String = "must be one of {values}"
    const val NONE_OF: String = "must not be one of {values}"

    const val MIN: String = "must be at least {min}"
    const val MAX: String = "must be at most {max}"
    const val GREATER_THAN: String = "must be greater than {other}"
    const val LESS_THAN: String = "must be less than {other}"
    const val BETWEEN: String = "must be between {min} and {max}"

    const val MIN_LENGTH: String = "must have at least {min} characters"
    const val MAX_LENGTH: String = "must have at most {max} characters"
    const val LENGTH: String = "must have exactly {length} characters"
    const val LENGTH_BETWEEN: String = "must have between {min} and {max} characters"
    const val NOT_EMPTY: String = "must not be empty"
    const val NOT_BLANK: String = "must not be blank"
    const val MATCHES: String = "must match {regex}"
    const val NOT_MATCHES: String = "must not match {regex}"
    const val CONTAINS: String = "must contain {text}"
    const val NOT_CONTAINS: String = "must not contain {text}"
    const val STARTS_WITH: String = "must start with {prefix}"
    const val NOT_STARTS_WITH: String = "must not start with {prefix}"
    const val ENDS_WITH: String = "must end with {suffix}"
    const val NOT_ENDS_WITH: String = "must not end with {suffix}"
    const val UPPERCASE: String = "must be uppercase"
    const val LOWERCASE: String = "must be lowercase"
    const val LETTERS: String = "must contain only letters"
    const val DIGITS: String = "must contain only digits"
    const val LETTERS_OR_DIGITS: String = "must contain only letters or digits"
    const val ASCII: String = "must contain only ASCII characters"

    const val POSITIVE: String = "must be positive"
    const val NEGATIVE: String = "must be negative"
    const val POSITIVE_OR_ZERO: String = "must be positive or zero"
    const val NEGATIVE_OR_ZERO: String = "must be negative or zero"
    const val MULTIPLE_OF: String = "must be a multiple of {factor}"
    const val FINITE: String = "must be finite"
    const val MAX_DECIMAL_PLACES: String = "must have at most {max} decimal places"
    const val MAX_INTEGER_DIGITS: String = "must have at most {max} integer digits"

    const val PAST: String = "must be in the past"
    const val FUTURE: String = "must be in the future"
    const val PAST_OR_PRESENT: String = "must be in the past or present"
    const val FUTURE_OR_PRESENT: String = "must be in the future or present"
    const val AFTER: String = "must be after {other}"
    const val BEFORE: String = "must be before {other}"
    const val AFTER_OR_EQUAL: String = "must be after or equal to {other}"
    const val BEFORE_OR_EQUAL: String = "must be before or equal to {other}"
    const val BETWEEN_DATES: String = "must be between {start} and {end}"

    const val MIN_SIZE: String = "must have at least {min} items"
    const val MAX_SIZE: String = "must have at most {max} items"
    const val SIZE: String = "must have exactly {size} items"
    const val SIZE_BETWEEN: String = "must have between {min} and {max} items"
    const val DISTINCT: String = "must not contain duplicates"
    const val CONTAINS_ELEMENT: String = "must contain {element}"
    const val CONTAINS_ALL: String = "must contain all of {elements}"
    const val CONTAINS_ANY: String = "must contain any of {elements}"

    const val MIN_ENTRIES: String = "must have at least {min} entries"
    const val MAX_ENTRIES: String = "must have at most {max} entries"
    const val ENTRIES: String = "must have exactly {size} entries"
    const val ENTRIES_BETWEEN: String = "must have between {min} and {max} entries"
    const val CONTAINS_KEY: String = "must contain the key {key}"
    const val CONTAINS_KEYS: String = "must contain the keys {keys}"
}

internal object BrazilMessages {
    const val CPF: String = "must be a valid CPF"
    const val CNPJ: String = "must be a valid CNPJ"
    const val CNH: String = "must be a valid CNH"
    const val PIS: String = "must be a valid PIS"
    const val TITULO_ELEITORAL: String = "must be a valid título eleitoral"
    const val CHAVE_NFE: String = "must be a valid NF-e access key"
    const val CEP: String = "must be a valid CEP"
    const val PLACA: String = "must be a valid license plate"
}

internal object FormatMessages {
    const val EMAIL: String = "must be a valid email"
    const val URL: String = "must be a valid URL"
    const val HOSTNAME: String = "must be a valid hostname"
    const val IPV4: String = "must be a valid IPv4 address"
    const val IPV6: String = "must be a valid IPv6 address"
    const val IP: String = "must be a valid IP address"
    const val CIDR: String = "must be a valid CIDR block"
    const val MAC_ADDRESS: String = "must be a valid MAC address"
    const val SLUG: String = "must be a valid slug"
    const val JWT: String = "must be a valid JWT"
    const val DATA_URI: String = "must be a valid data URI"
    const val MIME_TYPE: String = "must be a valid MIME type"

    const val UUID: String = "must be a valid UUID"
    const val ULID: String = "must be a valid ULID"
    const val OBJECT_ID: String = "must be a valid ObjectId"
    const val SEMVER: String = "must be a valid semantic version"
    const val ISBN: String = "must be a valid ISBN"
    const val ISBN10: String = "must be a valid ISBN-10"
    const val ISBN13: String = "must be a valid ISBN-13"
    const val ISSN: String = "must be a valid ISSN"
    const val EAN: String = "must be a valid EAN"
    const val ISRC: String = "must be a valid ISRC"
    const val IMEI: String = "must be a valid IMEI"
    const val LUHN: String = "must have a valid Luhn check digit"
    const val HASH: String = "must be a valid {algorithm} hash"

    const val BASE64: String = "must be valid Base64"
    const val BASE64_URL: String = "must be valid URL-safe Base64"
    const val BASE32: String = "must be valid Base32"
    const val BASE58: String = "must be valid Base58"
    const val HEXADECIMAL: String = "must be hexadecimal"
    const val JSON: String = "must be valid JSON"

    const val HEX_COLOR: String = "must be a valid hexadecimal color"
    const val RGB_COLOR: String = "must be a valid RGB color"
    const val HSL_COLOR: String = "must be a valid HSL color"

    const val CREDIT_CARD: String = "must be a valid credit card number"
    const val IBAN: String = "must be a valid IBAN"
    const val BIC: String = "must be a valid BIC"
    const val ISIN: String = "must be a valid ISIN"
    const val CURRENCY_CODE: String = "must be a valid ISO 4217 currency code"
    const val BITCOIN_ADDRESS: String = "must be a valid Bitcoin address"
    const val ETHEREUM_ADDRESS: String = "must be a valid Ethereum address"

    const val ISO_COUNTRY_CODE: String = "must be a valid ISO 3166-1 alpha-2 country code"
    const val ISO_COUNTRY_CODE_ALPHA3: String = "must be a valid ISO 3166-1 alpha-3 country code"
    const val LANGUAGE_CODE: String = "must be a valid ISO 639-1 language code"
    const val LOCALE: String = "must be a valid locale"
    const val TIME_ZONE: String = "must be a valid time zone"
    const val LATITUDE: String = "must be a valid latitude"
    const val LONGITUDE: String = "must be a valid longitude"

    const val NUMERIC: String = "must be numeric"
    const val INTEGER: String = "must be an integer"
    const val CONTAINS_UPPERCASE: String = "must contain an uppercase letter"
    const val CONTAINS_LOWERCASE: String = "must contain a lowercase letter"
    const val CONTAINS_DIGIT: String = "must contain a digit"
    const val CONTAINS_SYMBOL: String = "must contain a symbol"

    const val ISO_DATE: String = "must be an ISO 8601 date"
    const val ISO_TIME: String = "must be an ISO 8601 time"
    const val ISO_DATE_TIME: String = "must be an ISO 8601 date and time with offset"
    const val ISO_DURATION: String = "must be an ISO 8601 duration"
    const val DATE_FORMAT: String = "must match the date format {pattern}"
}

internal object PortugalMessages {
    const val NIF: String = "must be a valid NIF"
}

internal object SpainMessages {
    const val DNI: String = "must be a valid DNI"
    const val NIE: String = "must be a valid NIE"
    const val NIF: String = "must be a valid NIF"
}

internal object ItalyMessages {
    const val CODICE_FISCALE: String = "must be a valid codice fiscale"
    const val CIE: String = "must be a valid CIE number"
}

internal object FranceMessages {
    const val SPI: String = "must be a valid SPI"
}

internal object BelgiumMessages {
    const val RIJKSREGISTERNUMMER: String = "must be a valid rijksregisternummer"
}

internal object NetherlandsMessages {
    const val BSN: String = "must be a valid BSN"
}

internal object IrelandMessages {
    const val PPS: String = "must be a valid PPS number"
}

internal object UnitedKingdomMessages {
    const val NINO: String = "must be a valid NINO"
}

internal object GermanyMessages {
    const val STEUER_ID: String = "must be a valid Steuer-IdNr"
}

internal object AustriaMessages {
    const val ABGABENKONTONUMMER: String = "must be a valid Abgabenkontonummer"
}

internal object DenmarkMessages {
    const val CPR: String = "must be a valid CPR number"
}

internal object SwedenMessages {
    const val PERSONNUMMER: String = "must be a valid personnummer"
    const val SAMORDNINGSNUMMER: String = "must be a valid samordningsnummer"
}

internal object NorwayMessages {
    const val FODSELSNUMMER: String = "must be a valid fødselsnummer"
}

internal object FinlandMessages {
    const val HETU: String = "must be a valid HETU"
}

internal object MaltaMessages {
    const val ID_CARD_NUMBER: String = "must be a valid ID card number"
}

internal object PolandMessages {
    const val PESEL: String = "must be a valid PESEL"
    const val NIP: String = "must be a valid NIP"
    const val REGON: String = "must be a valid REGON"
    const val DOWOD_OSOBISTY: String = "must be a valid dowód osobisty number"
}

internal object CzechRepublicMessages {
    const val RODNE_CISLO: String = "must be a valid rodné číslo"
}

internal object SlovakiaMessages {
    const val RODNE_CISLO: String = "must be a valid rodné číslo"
}

internal object RomaniaMessages {
    const val CNP: String = "must be a valid CNP"
}

internal object BulgariaMessages {
    const val EGN: String = "must be a valid EGN"
}

internal object SloveniaMessages {
    const val DAVCNA_STEVILKA: String = "must be a valid davčna številka"
}

internal object EstoniaMessages {
    const val ISIKUKOOD: String = "must be a valid isikukood"
}

internal object LatviaMessages {
    const val PERSONAS_KODS: String = "must be a valid personas kods"
}

internal object LithuaniaMessages {
    const val ASMENS_KODAS: String = "must be a valid asmens kodas"
}

internal object GreeceMessages {
    const val AFM: String = "must be a valid AFM"
}

internal object CyprusMessages {
    const val AFM: String = "must be a valid AFM"
}

internal object UkraineMessages {
    const val RNOKPP: String = "must be a valid RNOKPP"
}

internal object RussiaMessages {
    const val INN_INDIVIDUAL: String = "must be a valid individual INN"
    const val INN_LEGAL_ENTITY: String = "must be a valid legal entity INN"
}

internal object UnitedStatesMessages {
    const val EIN: String = "must be a valid EIN"
}

internal object CanadaMessages {
    const val SIN: String = "must be a valid SIN"
}

internal object ArgentinaMessages {
    const val CUIT: String = "must be a valid CUIT"
    const val CUIL: String = "must be a valid CUIL"
}

internal object IndiaMessages {
    const val AADHAAR: String = "must be a valid Aadhaar"
    const val PAN: String = "must be a valid PAN"
}

internal object PakistanMessages {
    const val CNIC: String = "must be a valid CNIC"
}

internal object SriLankaMessages {
    const val NIC: String = "must be a valid NIC"
}

internal object ChinaMessages {
    const val RESIDENT_ID: String = "must be a valid resident identity card number"
}

internal object TaiwanMessages {
    const val NATIONAL_ID: String = "must be a valid national identification number"
}

internal object HongKongMessages {
    const val HKID: String = "must be a valid HKID"
}

internal object ThailandMessages {
    const val NATIONAL_ID: String = "must be a valid national identification number"
}

internal object SouthKoreaMessages {
    const val RRN: String = "must be a valid RRN"
}

internal object IranMessages {
    const val CODE_MELLI: String = "must be a valid national identity code"
}

internal object IsraelMessages {
    const val TEUDAT_ZEHUT: String = "must be a valid teudat zehut"
}

internal object LibyaMessages {
    const val NIN: String = "must be a valid NIN"
}

internal object TunisiaMessages {
    const val CIN: String = "must be a valid national identity card number"
}
