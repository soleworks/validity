package io.github.soleworks.validity

import java.util.concurrent.ConcurrentHashMap

/**
 * The default messages of the constraints, one property per message.
 *
 * Each property is named after the constraint that uses it, which is also the code of its violation, and its text can
 * have placeholders in braces: `{min}` is replaced by the argument of the constraint, `{path}` by the path of the
 * value and `{value}` by the value. Change them with [ValidityConfiguration.messages]:
 *
 * ```kotlin
 * Validity.configure {
 *     messages {
 *         minLength = "deve ter pelo menos {min} caracteres"
 *         brazil { cpf = "CPF inválido" }
 *     }
 * }
 * ```
 *
 * A message given to a constraint, as in `minLength(3, message = "too short")`, takes the place of the default.
 */
@ValidityDsl
public class Messages internal constructor() {
    /** Default message of `notNull` and `required`. */
    public var required: String = "is required"

    /** Default message of `forbidden`. */
    public var forbidden: String = "must be null"

    /** Default message of `atLeastOneOf`. Takes `{fields}`. */
    public var atLeastOneOf: String = "at least one of {fields} is required"

    /** Default message of `atMostOneOf`. Takes `{fields}`. */
    public var atMostOneOf: String = "at most one of {fields} can be filled"

    /** Default message of `exactlyOneOf`. Takes `{fields}`. */
    public var exactlyOneOf: String = "exactly one of {fields} must be filled"

    /** Default message of `allOrNoneOf`. Takes `{fields}`. */
    public var allOrNoneOf: String = "{fields} must be filled together"

    /** Default message of `atLeastOf`. Takes `{count}` and `{fields}`. */
    public var atLeastOf: String = "at least {count} of {fields} must be filled"

    /** Default message of `atMostOf`. Takes `{count}` and `{fields}`. */
    public var atMostOf: String = "at most {count} of {fields} can be filled"

    /** Default message of `exactlyOf`. Takes `{count}` and `{fields}`. */
    public var exactlyOf: String = "exactly {count} of {fields} must be filled"

    /** Default message of `equalTo`. Takes `{other}`. */
    public var equalTo: String = "must be equal to {other}"

    /** Default message of `notEqualTo`. Takes `{other}`. */
    public var notEqualTo: String = "must not be equal to {other}"

    /** Default message of `oneOf`. Takes `{values}`. */
    public var oneOf: String = "must be one of {values}"

    /** Default message of `enum`. Takes `{values}`. */
    public var enum: String = "must be one of {values}"

    /** Default message of `noneOf`. Takes `{values}`. */
    public var noneOf: String = "must not be one of {values}"

    /** Joins the failures of the branches of `or` into its message. Takes `{left}` and `{right}`. */
    public var or: String = "{left} or {right}"

    /**
     * Joins the failures of `and`, and of an `or` branch that broke more than one rule. Takes `{left}` and `{right}`.
     */
    public var and: String = "{left} and {right}"

    /** Default message of `not`. */
    public var not: String = "is not allowed"

    /** Default message of `convert`. */
    public var convert: String = "has an invalid format"

    /** Default message of `convertToBigDecimal`. */
    public var convertToBigDecimal: String = "must be a valid number"

    /** Default message of `convertToInt`. */
    public var convertToInt: String = "must be a valid integer"

    /** Default message of `convertToLong`. */
    public var convertToLong: String = "must be a valid integer"

    /** Default message of `convertToBigInteger`. */
    public var convertToBigInteger: String = "must be a valid integer"

    /** Default message of `convertToDouble`. */
    public var convertToDouble: String = "must be a valid number"

    /** Default message of `convertToBoolean`. */
    public var convertToBoolean: String = "must be true or false"

    /** Default message of `convertToUuid`. */
    public var convertToUuid: String = "must be a valid UUID"

    /** Default message of `convertToEnum`. Takes `{values}`. */
    public var convertToEnum: String = "must be one of {values}"

    /** Default message of `convertToLocalDate`. */
    public var convertToLocalDate: String = "must be a valid ISO 8601 date"

    /** Default message of `convertToLocalDateTime`. */
    public var convertToLocalDateTime: String = "must be a valid ISO 8601 date and time"

    /** Default message of `convertToLocalTime`. */
    public var convertToLocalTime: String = "must be a valid ISO 8601 time"

    /** Default message of `convertToOffsetDateTime`. */
    public var convertToOffsetDateTime: String = "must be a valid ISO 8601 date and time with offset"

    /** Default message of `convertToInstant`. */
    public var convertToInstant: String = "must be a valid ISO 8601 instant"

    /** Default message of `convertToYearMonth`. */
    public var convertToYearMonth: String = "must be a valid ISO 8601 year and month"

    /** Default message of `convertToDuration`. */
    public var convertToDuration: String = "must be a valid ISO 8601 duration"

    /** Default message of `isTrue`. */
    public var mustBeTrue: String = "must be true"

    /** Default message of `isFalse`. */
    public var mustBeFalse: String = "must be false"

    /** Default message of `min`. Takes `{min}`. */
    public var min: String = "must be at least {min}"

    /** Default message of `max`. Takes `{max}`. */
    public var max: String = "must be at most {max}"

    /** Default message of `greaterThan`. Takes `{other}`. */
    public var greaterThan: String = "must be greater than {other}"

    /** Default message of `lessThan`. Takes `{other}`. */
    public var lessThan: String = "must be less than {other}"

    /** Default message of `between`. Takes `{min}` and `{max}`. */
    public var between: String = "must be between {min} and {max}"

    /** Default message of `minLength`. Takes `{min}`. */
    public var minLength: String = "must have at least {min} characters"

    /** Default message of `maxLength`. Takes `{max}`. */
    public var maxLength: String = "must have at most {max} characters"

    /** Default message of `length`. Takes `{length}`. */
    public var length: String = "must have exactly {length} characters"

    /** Default message of `lengthBetween`. Takes `{min}` and `{max}`. */
    public var lengthBetween: String = "must have between {min} and {max} characters"

    /** Default message of `minBytes`. Takes `{min}`. */
    public var minBytes: String = "must have at least {min} bytes"

    /** Default message of `maxBytes`. Takes `{max}`. */
    public var maxBytes: String = "must have at most {max} bytes"

    /** Default message of `notEmpty`. */
    public var notEmpty: String = "must not be empty"

    /** Default message of `empty`. */
    public var empty: String = "must be empty"

    /** Default message of `notBlank`. */
    public var notBlank: String = "must not be blank"

    /** Default message of `blank`. */
    public var blank: String = "must be blank"

    /** Default message of `trimmed`. */
    public var trimmed: String = "must not start or end with whitespace"

    /** Default message of `matches`. Takes `{regex}`. */
    public var matches: String = "must match {regex}"

    /** Default message of `notMatches`. Takes `{regex}`. */
    public var notMatches: String = "must not match {regex}"

    /** Default message of `contains`. Takes `{text}`. */
    public var contains: String = "must contain {text}"

    /** Default message of `notContains`. Takes `{text}`. */
    public var notContains: String = "must not contain {text}"

    /** Default message of `startsWith`. Takes `{prefix}`. */
    public var startsWith: String = "must start with {prefix}"

    /** Default message of `notStartsWith`. Takes `{prefix}`. */
    public var notStartsWith: String = "must not start with {prefix}"

    /** Default message of `endsWith`. Takes `{suffix}`. */
    public var endsWith: String = "must end with {suffix}"

    /** Default message of `notEndsWith`. Takes `{suffix}`. */
    public var notEndsWith: String = "must not end with {suffix}"

    /** Default message of `uppercase`. */
    public var uppercase: String = "must be uppercase"

    /** Default message of `lowercase`. */
    public var lowercase: String = "must be lowercase"

    /** Default message of `letters`. */
    public var letters: String = "must contain only letters"

    /** Default message of `digits`. */
    public var digits: String = "must contain only digits"

    /** Default message of `lettersOrDigits`. */
    public var lettersOrDigits: String = "must contain only letters or digits"

    /** Default message of `ascii`. */
    public var ascii: String = "must contain only ASCII characters"

    /** Default message of `containsOnly`. Takes `{characters}`. */
    public var containsOnly: String = "must contain only the characters {characters}"

    /** Default message of `normalized`. Takes `{form}`. */
    public var normalized: String = "must be in Unicode {form} form"

    /** Default message of `positive`. */
    public var positive: String = "must be positive"

    /** Default message of `negative`. */
    public var negative: String = "must be negative"

    /** Default message of `positiveOrZero`. */
    public var positiveOrZero: String = "must be positive or zero"

    /** Default message of `negativeOrZero`. */
    public var negativeOrZero: String = "must be negative or zero"

    /** Default message of `multipleOf`. Takes `{factor}`. */
    public var multipleOf: String = "must be a multiple of {factor}"

    /** Default message of `finite`. */
    public var finite: String = "must be finite"

    /** Default message of `maxDecimalPlaces`. Takes `{max}`. */
    public var maxDecimalPlaces: String = "must have at most {max} decimal places"

    /** Default message of `maxIntegerDigits`. Takes `{max}`. */
    public var maxIntegerDigits: String = "must have at most {max} integer digits"

    /** Default message of `decimalPlaces`. Takes `{count}`. */
    public var decimalPlaces: String = "must have exactly {count} decimal places"

    /** Default message of `minDecimalPlaces`. Takes `{min}`. */
    public var minDecimalPlaces: String = "must have at least {min} decimal places"

    /** Default message of `minDigits`. Takes `{min}`. */
    public var minDigits: String = "must have at least {min} digits"

    /** Default message of `maxDigits`. Takes `{max}`. */
    public var maxDigits: String = "must have at most {max} digits"

    /** Default message of `digitCount`. Takes `{count}`. */
    public var digitCount: String = "must have exactly {count} digits"

    /** Default message of `past`. */
    public var past: String = "must be in the past"

    /** Default message of `future`. */
    public var future: String = "must be in the future"

    /** Default message of `pastOrPresent`. */
    public var pastOrPresent: String = "must be in the past or present"

    /** Default message of `futureOrPresent`. */
    public var futureOrPresent: String = "must be in the future or present"

    /** Default message of `after`. Takes `{other}`. */
    public var after: String = "must be after {other}"

    /** Default message of `before`. Takes `{other}`. */
    public var before: String = "must be before {other}"

    /** Default message of `afterOrEqual`. Takes `{other}`. */
    public var afterOrEqual: String = "must be after or equal to {other}"

    /** Default message of `beforeOrEqual`. Takes `{other}`. */
    public var beforeOrEqual: String = "must be before or equal to {other}"

    /** Default message of `between`. Takes `{start}` and `{end}`. */
    public var betweenDates: String = "must be between {start} and {end}"

    /** Default message of `minAge`. Takes `{min}`. */
    public var minAge: String = "must be at least {min} years old"

    /** Default message of `maxAge`. Takes `{max}`. */
    public var maxAge: String = "must be at most {max} years old"

    /** Default message of `withinNext`. Takes `{duration}`. */
    public var withinNext: String = "must be within the next {duration}"

    /** Default message of `withinLast`. Takes `{duration}`. */
    public var withinLast: String = "must be within the last {duration}"

    /** Default message of `today`. */
    public var today: String = "must be today"

    /** Default message of `weekday`. */
    public var weekday: String = "must be a weekday"

    /** Default message of `weekend`. */
    public var weekend: String = "must be on a weekend"

    /** Default message of `minSize`. Takes `{min}`. */
    public var minSize: String = "must have at least {min} items"

    /** Default message of `maxSize`. Takes `{max}`. */
    public var maxSize: String = "must have at most {max} items"

    /** Default message of `size`. Takes `{size}`. */
    public var size: String = "must have exactly {size} items"

    /** Default message of `sizeBetween`. Takes `{min}` and `{max}`. */
    public var sizeBetween: String = "must have between {min} and {max} items"

    /** Default message of `distinct` and `distinctBy`. */
    public var distinct: String = "must not contain duplicates"

    /** Default message of `contains`. Takes `{element}`. */
    public var containsElement: String = "must contain {element}"

    /** Default message of `notContains`. Takes `{element}`. */
    public var notContainsElement: String = "must not contain {element}"

    /** Default message of `containsAll`. Takes `{elements}`. */
    public var containsAll: String = "must contain all of {elements}"

    /** Default message of `containsAny`. Takes `{elements}`. */
    public var containsAny: String = "must contain any of {elements}"

    /** Default message of `containsNone`. Takes `{elements}`. */
    public var containsNone: String = "must not contain any of {elements}"

    /** Default message of `minSize`. Takes `{min}`. */
    public var minEntries: String = "must have at least {min} entries"

    /** Default message of `maxSize`. Takes `{max}`. */
    public var maxEntries: String = "must have at most {max} entries"

    /** Default message of `size`. Takes `{size}`. */
    public var entries: String = "must have exactly {size} entries"

    /** Default message of `sizeBetween`. Takes `{min}` and `{max}`. */
    public var entriesBetween: String = "must have between {min} and {max} entries"

    /** Default message of `containsKey`. Takes `{key}`. */
    public var containsKey: String = "must contain the key {key}"

    /** Default message of `containsKeys`. Takes `{keys}`. */
    public var containsKeys: String = "must contain the keys {keys}"

    /** Default message of `notContainsKey`. Takes `{key}`. */
    public var notContainsKey: String = "must not contain the key {key}"

    /** Default message of `onlyKeys`. Takes `{keys}`. */
    public var onlyKeys: String = "must contain only the keys {keys}"

    /** Default message of `containsValue`. Takes `{other}`. */
    public var containsValue: String = "must contain the value {other}"

    /** Default message of `email`. */
    public var email: String = "must be a valid email"

    /** Default message of `url`. */
    public var url: String = "must be a valid URL"

    /** Default message of `hostname`. */
    public var hostname: String = "must be a valid hostname"

    /** Default message of `domain`. */
    public var domain: String = "must be a valid domain name"

    /** Default message of `port`. */
    public var port: String = "must be a valid port number"

    /** Default message of `ipv4`. */
    public var ipv4: String = "must be a valid IPv4 address"

    /** Default message of `ipv6`. */
    public var ipv6: String = "must be a valid IPv6 address"

    /** Default message of `ip`. */
    public var ip: String = "must be a valid IP address"

    /** Default message of `cidr`. */
    public var cidr: String = "must be a valid CIDR block"

    /** Default message of `macAddress`. */
    public var macAddress: String = "must be a valid MAC address"

    /** Default message of `slug`. */
    public var slug: String = "must be a valid slug"

    /** Default message of `jwt`. */
    public var jwt: String = "must be a valid JWT"

    /** Default message of `dataUri`. */
    public var dataUri: String = "must be a valid data URI"

    /** Default message of `mimeType`. */
    public var mimeType: String = "must be a valid MIME type"

    /** Default message of `uuid`. */
    public var uuid: String = "must be a valid UUID"

    /** Default message of `ulid`. */
    public var ulid: String = "must be a valid ULID"

    /** Default message of `objectId`. */
    public var objectId: String = "must be a valid ObjectId"

    /** Default message of `semver`. */
    public var semver: String = "must be a valid semantic version"

    /** Default message of `isbn`. */
    public var isbn: String = "must be a valid ISBN"

    /** Default message of `isbn10`. */
    public var isbn10: String = "must be a valid ISBN-10"

    /** Default message of `isbn13`. */
    public var isbn13: String = "must be a valid ISBN-13"

    /** Default message of `issn`. */
    public var issn: String = "must be a valid ISSN"

    /** Default message of `ean`. */
    public var ean: String = "must be a valid EAN"

    /** Default message of `isrc`. */
    public var isrc: String = "must be a valid ISRC"

    /** Default message of `imei`. */
    public var imei: String = "must be a valid IMEI"

    /** Default message of `luhn`. */
    public var luhn: String = "must have a valid Luhn check digit"

    /** Default message of `hash`. Takes `{algorithm}`. */
    public var hash: String = "must be a valid {algorithm} hash"

    /** Default message of `base64`. */
    public var base64: String = "must be valid Base64"

    /** Default message of `base64Url`. */
    public var base64Url: String = "must be valid URL-safe Base64"

    /** Default message of `base32`. */
    public var base32: String = "must be valid Base32"

    /** Default message of `base58`. */
    public var base58: String = "must be valid Base58"

    /** Default message of `hexadecimal`. */
    public var hexadecimal: String = "must be hexadecimal"

    /** Default message of `json`. */
    public var json: String = "must be valid JSON"

    /** Default message of `hexColor`. */
    public var hexColor: String = "must be a valid hexadecimal color"

    /** Default message of `rgbColor`. */
    public var rgbColor: String = "must be a valid RGB color"

    /** Default message of `hslColor`. */
    public var hslColor: String = "must be a valid HSL color"

    /** Default message of `creditCard`. */
    public var creditCard: String = "must be a valid credit card number"

    /** Default message of `iban`. */
    public var iban: String = "must be a valid IBAN"

    /** Default message of `bic`. */
    public var bic: String = "must be a valid BIC"

    /** Default message of `isin`. */
    public var isin: String = "must be a valid ISIN"

    /** Default message of `currencyCode`. */
    public var currencyCode: String = "must be a valid ISO 4217 currency code"

    /** Default message of `bitcoinAddress`. */
    public var bitcoinAddress: String = "must be a valid Bitcoin address"

    /** Default message of `ethereumAddress`. */
    public var ethereumAddress: String = "must be a valid Ethereum address"

    /** Default message of `isoCountryCode`. */
    public var isoCountryCode: String = "must be a valid ISO 3166-1 alpha-2 country code"

    /** Default message of `isoCountryCodeAlpha3`. */
    public var isoCountryCodeAlpha3: String = "must be a valid ISO 3166-1 alpha-3 country code"

    /** Default message of `languageCode`. */
    public var languageCode: String = "must be a valid ISO 639-1 language code"

    /** Default message of `locale`. */
    public var locale: String = "must be a valid locale"

    /** Default message of `timeZone`. */
    public var timeZone: String = "must be a valid time zone"

    /** Default message of `latitude`. */
    public var latitude: String = "must be a valid latitude"

    /** Default message of `longitude`. */
    public var longitude: String = "must be a valid longitude"

    /** Default message of `coordinates`. */
    public var coordinates: String = "must be a valid latitude and longitude pair"

    /** Default message of `postalCode`. Takes `{country}`. */
    public var postalCode: String = "must be a valid postal code for {country}"

    /** Default message of `numeric`. */
    public var numeric: String = "must be numeric"

    /** Default message of `integer`. */
    public var integer: String = "must be an integer"

    /** Default message of `boolean`. */
    public var boolean: String = "must be true or false"

    /** Default message of `containsUppercase`. */
    public var containsUppercase: String = "must contain an uppercase letter"

    /** Default message of `containsLowercase`. */
    public var containsLowercase: String = "must contain a lowercase letter"

    /** Default message of `containsDigit`. */
    public var containsDigit: String = "must contain a digit"

    /** Default message of `containsSymbol`. */
    public var containsSymbol: String = "must contain a symbol"

    /** Default message of `isoDate`. */
    public var isoDate: String = "must be an ISO 8601 date"

    /** Default message of `isoTime`. */
    public var isoTime: String = "must be an ISO 8601 time"

    /** Default message of `isoDateTime`. */
    public var isoDateTime: String = "must be an ISO 8601 date and time with offset"

    /** Default message of `isoDuration`. */
    public var isoDuration: String = "must be an ISO 8601 duration"

    /** Default message of `dateFormat`. Takes `{pattern}`. */
    public var dateFormat: String = "must match the date format {pattern}"

    private val modules = ConcurrentHashMap<MessagesKey<*>, Any>()

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

    /**
     * Changes the messages of the constraints of Brazil.
     *
     * @param block sets the messages it changes. The others keep their default.
     */
    public fun brazil(
        block: BrazilMessages.() -> Unit
    ): Unit = brazil.block()

    /**
     * Changes the messages of the constraints of Bulgaria.
     *
     * @param block sets the messages it changes. The others keep their default.
     */
    public fun bulgaria(
        block: BulgariaMessages.() -> Unit
    ): Unit = bulgaria.block()

    /**
     * Changes the messages of the constraints of Canada.
     *
     * @param block sets the messages it changes. The others keep their default.
     */
    public fun canada(
        block: CanadaMessages.() -> Unit
    ): Unit = canada.block()

    /**
     * Changes the messages of the constraints of France.
     *
     * @param block sets the messages it changes. The others keep their default.
     */
    public fun france(
        block: FranceMessages.() -> Unit
    ): Unit = france.block()

    /**
     * Changes the messages of the constraints of Israel.
     *
     * @param block sets the messages it changes. The others keep their default.
     */
    public fun israel(
        block: IsraelMessages.() -> Unit
    ): Unit = israel.block()

    /**
     * Changes the messages of the constraints of the Netherlands.
     *
     * @param block sets the messages it changes. The others keep their default.
     */
    public fun netherlands(
        block: NetherlandsMessages.() -> Unit
    ): Unit = netherlands.block()

    /**
     * Changes the messages of the constraints of Pakistan.
     *
     * @param block sets the messages it changes. The others keep their default.
     */
    public fun pakistan(
        block: PakistanMessages.() -> Unit
    ): Unit = pakistan.block()

    /**
     * Changes the messages of the constraints of Poland.
     *
     * @param block sets the messages it changes. The others keep their default.
     */
    public fun poland(
        block: PolandMessages.() -> Unit
    ): Unit = poland.block()

    /**
     * Changes the messages of the constraints of Russia.
     *
     * @param block sets the messages it changes. The others keep their default.
     */
    public fun russia(
        block: RussiaMessages.() -> Unit
    ): Unit = russia.block()

    /**
     * Changes the messages of the constraints of Slovenia.
     *
     * @param block sets the messages it changes. The others keep their default.
     */
    public fun slovenia(
        block: SloveniaMessages.() -> Unit
    ): Unit = slovenia.block()

    /**
     * Changes the messages of the constraints of Spain.
     *
     * @param block sets the messages it changes. The others keep their default.
     */
    public fun spain(
        block: SpainMessages.() -> Unit
    ): Unit = spain.block()

    /**
     * Changes the messages of the constraints of Thailand.
     *
     * @param block sets the messages it changes. The others keep their default.
     */
    public fun thailand(
        block: ThailandMessages.() -> Unit
    ): Unit = thailand.block()

    /**
     * Changes the messages of the constraints of Ukraine.
     *
     * @param block sets the messages it changes. The others keep their default.
     */
    public fun ukraine(
        block: UkraineMessages.() -> Unit
    ): Unit = ukraine.block()

    /**
     * Changes the messages of the constraints of the United States.
     *
     * @param block sets the messages it changes. The others keep their default.
     */
    public fun unitedStates(
        block: UnitedStatesMessages.() -> Unit
    ): Unit = unitedStates.block()

    /**
     * Returns the messages of a module that is not part of `validity-core`, such as `validity-phone`, to change them.
     *
     * ```kotlin
     * Validity.configure { messages { module(GREETING_MESSAGES).greeting = "olá" } }
     * ```
     *
     * @param T the class that holds the messages of the module.
     * @param key the key the module declares for its messages.
     * @return the messages of the module in this configuration.
     */
    @Suppress("UNCHECKED_CAST")
    public fun <T : Any> module(
        key: MessagesKey<T>
    ): T = modules.computeIfAbsent(key) { key.create() } as T
}
