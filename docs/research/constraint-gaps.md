# Constraint gaps against peer validation libraries

Research only: nothing here is implemented, and every name, signature and message below is a proposal for the maintainer
to accept, rename or drop.

- Snapshot of validity: `main` at `6452c7d` (after PR #27, object-level presence rules).
- Peer docs read on 2026-10-24.
- Out of scope: the "now"-based temporal rules being built in another session (`minAge`/`maxAge`,
  `withinNext`/`withinLast`, `today`, `weekday`/`weekend`) and the rework of how the clock reaches rules.

## 1. What validity has today

Every `public fun ValidationNode<T>.name(...)` in `validity-core/.../constraints/` (country packages included) and in
`validity-phone`, grouped by the type of the value. Overloads that differ only in the `message` (text vs. lambda) are
listed once.

| Value type | Constraints |
|---|---|
| Any `V` | `notNull` (on `V?`), `equalTo`, `notEqualTo`, `oneOf`, `noneOf`, `or`, `and`, `not` |
| Object (premises, `context(Validation)`) | `required`, `ifPresent`, `forbidden`, `atLeastOneOf`, `atMostOneOf`, `exactlyOneOf`, `allOrNoneOf`, `atLeastOf`, `atMostOf`, `exactlyOf` |
| Accessors | `each` (Iterable, Array, Map), `eachKey`, `eachValue`, `valid` |
| `String`: length and content | `minLength`, `maxLength`, `length`, `lengthBetween`, `notEmpty`, `notBlank`, `matches`, `notMatches`, `contains`, `notContains`, `startsWith`, `notStartsWith`, `endsWith`, `notEndsWith` |
| `String`: characters | `uppercase`, `lowercase`, `letters`, `digits`, `lettersOrDigits`, `ascii`, `numeric`, `integer`, `containsUppercase`, `containsLowercase`, `containsDigit`, `containsSymbol`, `enum<E>` |
| `String`: internet | `email`, `url(schemes)`, `hostname`, `ipv4`, `ipv6`, `ip`, `cidr`, `macAddress`, `slug`, `jwt`, `dataUri`, `mimeType` |
| `String`: identifiers | `uuid(version)`, `ulid`, `objectId`, `semver`, `isbn`, `isbn10`, `isbn13`, `issn`, `ean`, `isrc`, `imei`, `luhn`, `hash(algorithm)` |
| `String`: encodings | `base64`, `base64Url`, `base32`, `base58`, `hexadecimal`, `json` |
| `String`: colors | `hexColor`, `rgbColor`, `hslColor` |
| `String`: finance | `creditCard`, `iban`, `bic`, `isin`, `currencyCode`, `bitcoinAddress`, `ethereumAddress` |
| `String`: location | `isoCountryCode`, `isoCountryCodeAlpha3`, `languageCode`, `locale`, `timeZone`, `latitude`, `longitude`, `postalCode(country)` |
| `String`: date formats | `isoDate`, `isoTime`, `isoDateTime`, `isoDuration`, `dateFormat(pattern)` |
| `String`: countries | Brazil `cpf`, `cnpj` (alphanumeric too), `cnh`, `pis`, `cep`, `placa` · Bulgaria `egn` · Canada `sin` · France `spi` · Israel `teudatZehut` · Netherlands `bsn` · Pakistan `cnic` · Poland `pesel`, `nip` · Russia `innIndividual`, `innLegalEntity` · Slovenia `davcnaStevilka` · Spain `dni`, `nie` · Thailand `nationalId` · Ukraine `rnokpp` · United States `ein` |
| `String`: phone (`validity-phone`) | `phone(countryCode, areaCode, format)`, `mobilePhone(...)`, `countryCode`, `areaCode` |
| `Int`, `Long`, `Short`, `Byte`, `Double`, `Float`, `BigInteger`, `BigDecimal` | `min`, `max`, `greaterThan`, `lessThan`, `between`, `positive`, `negative`, `positiveOrZero`, `negativeOrZero` |
| Integral types, `BigInteger`, `BigDecimal` | `multipleOf` (not on `Double`/`Float`) |
| `Double`, `Float` | `finite` |
| `BigDecimal` | `maxDecimalPlaces`, `maxIntegerDigits` |
| `Double`, `Float`, `BigDecimal`, `String` | `latitude`, `longitude` |
| `LocalDate`, `LocalDateTime`, `ZonedDateTime`, `OffsetDateTime`, `Instant`, `YearMonth`, `Year`, `Date` | `past`, `future`, `pastOrPresent`, `futureOrPresent` |
| The types above plus `LocalTime`, `OffsetTime` | `after`, `before`, `afterOrEqual`, `beforeOrEqual`, `between` |
| `java.time.Duration`, `kotlin.time.Duration` | `min`, `max`, `greaterThan`, `lessThan`, `between` |
| `Collection<*>`, `Array<*>` | `minSize`, `maxSize`, `size`, `sizeBetween`, `notEmpty`, `distinct` |
| `List<E>`, `Set<E>`, `Array<E>` | `distinctBy`, `contains`, `containsAll`, `containsAny` |
| `Map<*, *>` | `minSize`, `maxSize`, `size`, `sizeBetween`, `notEmpty`, `containsKey`, `containsKeys` |

## 2. Peer libraries surveyed

| Abbr. | Library | Source read |
|---|---|---|
| HV | Hibernate Validator 9 / Jakarta Bean Validation | <https://docs.hibernate.org/stable/validator/reference/en-US/html_single/#section-builtin-constraints> |
| Kf | Konform | <https://github.com/konform-kt/konform> and its constraint sources: <https://github.com/konform-kt/konform/tree/main/src/commonMain/kotlin/io/konform/validation/constraints> (the README shows only examples) |
| Ak | Akkurate | <https://github.com/nesk/akkurate> and its constraint builders: <https://github.com/nesk/akkurate/tree/main/akkurate-core/src/commonMain/kotlin/dev/nesk/akkurate/constraints/builders> (plus `jvmMain`) |
| Vk | Valiktor | <https://github.com/valiktor/valiktor> and <https://github.com/valiktor/valiktor/tree/master/valiktor-core/src/main/kotlin/org/valiktor/functions> |
| Zod | Zod 4 | <https://zod.dev/api> |
| Joi | Joi 18 | <https://joi.dev/api/18.x.x> (source: <https://github.com/hapijs/joi/blob/master/API.md>) |
| Yup | Yup | <https://github.com/jquense/yup#api> |
| CV | class-validator | <https://github.com/typestack/class-validator#validation-decorators> |
| VJS | validator.js | <https://github.com/validatorjs/validator.js#validators> |
| Lv | Laravel 12 validation | <https://laravel.com/docs/12.x/validation#available-validation-rules> |
| FV | FluentValidation | <https://docs.fluentvalidation.net/en/latest/built-in-validators.html> |
| Py | Pydantic 2 | <https://pydantic.dev/docs/validation/latest/api/pydantic/types/>, <https://pydantic.dev/docs/validation/latest/api/pydantic/networks/> |

The Kotlin libraries (Kf, Ak, Vk) document their constraints mostly in code, so their sources are cited alongside the
README.

## 3. Gap table

### How to read the signatures

Every proposal follows the conventions the Konsist harness enforces: the receiver is a `ValidationNode<T>`, the return
type is `Unit`, and the text overload takes `message: String = messages.<key>`. A rule that takes an argument also gets
the lambda overload, as `min` and `after` do (`message: T.(Arg) -> String`); the rows mark it **+λ**. A rule with no
argument gets only the text overload, like `email` today. Null arguments skip the rule, as in `min(min: Int?)`.

Priority is how common the rule is across the 12 libraries and how much it fits a typed Kotlin DSL:

- **P1**: in 4 or more libraries, or a clear hole in a family validity already has.
- **P2**: in 2 or 3 libraries.
- **P3**: niche (1 library, locale-specific, or easy to compose from what exists).

Note on names: the harness rule *every function named is or has should return Boolean* rules out `isTrue`, `hasDigits`
and similar peer names for constraints.

### 3.1 Boolean and any value

| # | Constraint | Types | Libraries (n) | Proposed signature | Default message (`Messages.kt`) | Pri. |
|---|---|---|---|---|---|---|
| 1 | Must be `true` | `Boolean` | HV `@AssertTrue`, Ak `isTrue`, Vk `isTrue`, Lv `accepted` (4) | `fun ValidationNode<Boolean>.accepted(message: String = messages.accepted)` | `accepted = "must be accepted"` | P1 |
| 2 | Must be `false` | `Boolean` | HV `@AssertFalse`, Ak `isFalse`, Vk `isFalse`, Lv `declined` (4) | `fun ValidationNode<Boolean>.declined(message: String = messages.declined)` | `declined = "must be declined"` | P1 |
| 3 | Instance of a type | any `V` | Ak `isInstanceOf`, CV `@IsInstance`, Zod `z.instanceof`, Joi `object.instance` (4) | `fun <V> ValidationNode<V>.instanceOf(type: KClass<*>, message: String = messages.instanceOf)` **+λ** | `instanceOf = "must be an instance of {type}"` | P3 (types make it rare; useful with sealed hierarchies) |

### 3.2 String

| # | Constraint | Types | Libraries (n) | Proposed signature | Default message | Pri. |
|---|---|---|---|---|---|---|
| 4 | Must be empty | `String` | Ak `isEmpty`, Vk `isEmpty`, CV `@IsEmpty`, FV `Empty`, VJS `isEmpty` (5) | `fun ValidationNode<String>.empty(message: String = messages.empty)` | `empty = "must be empty"` | P1 |
| 5 | Must be blank | `String` | Ak `isBlank`, Vk `isBlank`, FV `Empty` (whitespace counts), VJS `isEmpty(ignore_whitespace)` (4) | `fun ValidationNode<String>.blank(message: String = messages.blank)` | `blank = "must be blank"` | P2 |
| 6 | Case-insensitive comparisons | `String` | Vk `isEqualToIgnoringCase`, `isInIgnoringCase`, `containsIgnoringCase`, `startsWithIgnoringCase`, `endsWithIgnoringCase`; VJS `contains(ignoreCase)`; Joi `string.insensitive`; FV `IsEnumName(caseSensitive)`; Lv `distinct:ignore_case` (5) | Add `ignoreCase: Boolean = false` before `message` on the `String` `contains`, `notContains`, `startsWith`, `notStartsWith`, `endsWith`, `notEndsWith`, `enum`; add `fun ValidationNode<String>.equalTo(other: String?, ignoreCase: Boolean = false, message: String = messages.equalTo)` and `oneOf(values: Collection<String>, ignoreCase: Boolean = false, ...)` **+λ** (see Q3) | Reuse the existing keys | P1 |
| 7 | No leading or trailing whitespace | `String` | Joi `string.trim`, Yup `trim` (strict) (2); Py `strip_whitespace` only transforms | `fun ValidationNode<String>.trimmed(message: String = messages.trimmed)` | `trimmed = "must not start or end with whitespace"` | P2 |
| 8 | Only characters from a set | `String` | VJS `isWhitelisted`, Lv `alpha_dash`, Joi `token` (3) | `fun ValidationNode<String>.containsOnly(characters: String?, message: String = messages.containsOnly)` **+λ** | `containsOnly = "must contain only the characters {characters}"` | P2 |
| 9 | Byte length | `String` | VJS `isByteLength`, CV `@IsByteLength` (2) | `fun ValidationNode<String>.maxBytes(max: Int, charset: Charset = Charsets.UTF_8, message: String = messages.maxBytes)` **+λ**; `minBytes` likewise | `maxBytes = "must have at most {max} bytes"`, `minBytes = "must have at least {min} bytes"` | P2 |
| 10 | Unicode normalization form | `String` | HV `@Normalized`, Joi `string.normalize` (2) | `fun ValidationNode<String>.normalized(form: Normalizer.Form = Normalizer.Form.NFC, message: String = messages.normalized)` **+λ** | `normalized = "must be in Unicode {form} form"` | P3 |
| 11 | Length in code points | `String` | HV `@CodePointLength` (1) | Not a new rule: see Q4 on what `length` counts | n/a | P3 |
| 12 | Boolean text | `String` | VJS `isBoolean`, CV `@IsBooleanString`, Lv `boolean` (3) | `fun ValidationNode<String>.boolean(message: String = messages.boolean)` (accepts `true`/`false`, like `integer` accepts integer text) | `boolean = "must be true or false"` | P2 |
| 13 | Decimal text with a number of places | `String` | VJS `isDecimal(decimal_digits)`, CV `@IsDecimal`, Lv `decimal:min,max` (3) | `fun ValidationNode<String>.decimal(places: IntRange? = null, message: String = messages.decimal)` **+λ** | `decimal = "must be a decimal number"` | P3 (`numeric` covers the format; places are easier on a parsed `BigDecimal`) |
| 14 | Octal text | `String` | VJS `isOctal`, CV `@IsOctal` (2) | `fun ValidationNode<String>.octal(message: String = messages.octal)` | `octal = "must be octal"` | P3 |
| 15 | Strong password | `String` | VJS `isStrongPassword`, CV `@IsStrongPassword` (2) | Not proposed: `minLength` + `containsUppercase` + `containsLowercase` + `containsDigit` + `containsSymbol` already compose it (see Q9) | n/a | P3 |
| 16 | Emoji | `String` | Zod `z.emoji` (1) | `fun ValidationNode<String>.emoji(message: String = messages.emoji)` | `emoji = "must contain only emoji"` | P3 |
| 17 | Width / multibyte / surrogate pairs | `String` | VJS `isFullWidth`, `isHalfWidth`, `isVariableWidth`, `isMultibyte`, `isSurrogatePair`; CV the same (2) | Not proposed (CJK-specific, rarely asked for) | n/a | P3 |

### 3.3 Internet, location and identifier formats (`String`, plus `Int` for ports)

| # | Constraint | Types | Libraries (n) | Proposed signature | Default message | Pri. |
|---|---|---|---|---|---|---|
| 18 | Domain name with a TLD (FQDN) | `String` | Joi `string.domain`, VJS `isFQDN`, CV `@IsFQDN` (3) | `fun ValidationNode<String>.domain(message: String = messages.domain)` (`hostname` accepts `localhost`; this one needs a TLD of at least 2 letters) | `domain = "must be a valid domain name"` | P2 |
| 19 | Port number | `Int`, `String` | Joi `number.port`, VJS `isPort`, CV `@IsPort` (3) | `fun ValidationNode<Int>.port(message: String = messages.port)` and the same on `String` | `port = "must be a valid port number"` | P2 |
| 20 | Latitude-longitude pair | `String` | VJS `isLatLong`, CV `@IsLatLong` (2) | `fun ValidationNode<String>.coordinates(message: String = messages.coordinates)` | `coordinates = "must be a valid latitude and longitude pair"` | P2 |
| 21 | ISO 3166-1 numeric country code | `String` | VJS `isISO31661Numeric`, CV `@IsISO31661Numeric` (2) | `fun ValidationNode<String>.isoCountryCodeNumeric(message: String = messages.isoCountryCodeNumeric)` | `isoCountryCodeNumeric = "must be a valid ISO 3166-1 numeric country code"` | P2 |
| 22 | IP version of a CIDR block | `String` | Zod `z.cidrv4`, `z.cidrv6` (1) | `cidrV4()` / `cidrV6()`, mirroring `ipv4`/`ipv6` | `cidrV4 = "must be a valid IPv4 CIDR block"`, `cidrV6 = "must be a valid IPv6 CIDR block"` | P3 |
| 23 | URL parts (host, port, max length) | `String` | HV `@URL(protocol, host, port)`, Py `UrlConstraints` (2) | Extra optional parameters on `url` (`hosts: Set<String>? = null`, `maxLength: Int? = null`) | Reuse `url` | P3 |
| 24 | ISO 15924 script code | `String` | VJS `isISO15924` (1) | `fun ValidationNode<String>.scriptCode(message: String = messages.scriptCode)` | `scriptCode = "must be a valid ISO 15924 script code"` | P3 |
| 25 | E.164 phone format | `String` | Zod `z.e164` (1) | Belongs in `validity-phone`, maybe as a `PhoneFormat` value instead of a new rule | n/a | P3 |
| 26 | CUID, CUID2, NanoID | `String` | Zod `z.cuid`, `z.cuid2`, `z.nanoid` (1) | `cuid()`, `cuid2()`, `nanoid()` in `IdentifierConstraints.kt` | `cuid = "must be a valid CUID"`, etc. | P3 |
| 27 | Magnet and mailto URIs | `String` | VJS `isMagnetURI`, `isMailtoURI`; CV `@IsMagnetURI` (2) | `magnetUri()`, `mailtoUri()` | `magnetUri = "must be a valid magnet URI"`, `mailtoUri = "must be a valid mailto URI"` | P3 |
| 28 | Card brand | `String` | VJS `isCreditCard({ provider })`, Py `PaymentCardNumber.brand` (2) | `creditCard(brands: Set<CardBrand>? = null, ...)` **+λ**, with a new `CardBrand` enum in its own file | `creditCard` reused; brand in `{brands}` | P3 |
| 29 | Currency amount text | `String` | VJS `isCurrency`, CV `@IsCurrency` (2) | Not proposed (locale-heavy; parse to `BigDecimal` instead) | n/a | P3 |
| 30 | RFC 3339 date-time | `String` | VJS `isRFC3339`, CV `@IsRFC3339` (2) | Not proposed: `isoDateTime` (needs an offset) already accepts RFC 3339; say so in its KDoc | n/a | P3 |
| 31 | 12/24-hour time text | `String` | VJS `isTime`, CV `@IsMilitaryTime` (2) | Not proposed: `isoTime` or `dateFormat("hh:mm a")` cover it | n/a | P3 |
| 32 | Generic mod 10 / mod 11 check digit | `String` | HV `@Mod10Check`, `@Mod11Check` (1) | `mod11(...)` next to `luhn` | `mod11 = "must have a valid mod 11 check digit"` | P3 |
| 33 | ISO 6346 container code | `String` | VJS `isISO6346` (1) | `containerCode()` | `containerCode = "must be a valid ISO 6346 container code"` | P3 |
| 34 | Unix timestamp text | `String` | Joi `date.timestamp` (1) | Not proposed | n/a | P3 |

### 3.4 Country-specific identifiers

validity already keeps these in `constraints/<country>/`, with the messages in a `<Country>Messages` class. Peers have
these that validity lacks:

| # | Constraint | Libraries (n) | Proposed signature | Default message | Pri. |
|---|---|---|---|---|---|
| 35 | Brazil: Título Eleitoral (voter ID) | HV `@TituloEleitoral` (1) | `fun ValidationNode<String>.tituloEleitoral(message: String = messages.brazil.tituloEleitoral)` | `"must be a valid título eleitoral"` | P2 (Brazil is validity's best-covered country) |
| 36 | Poland: REGON | HV `@REGON` (1) | `poland.regon()` | `"must be a valid REGON"` | P3 |
| 37 | South Korea: resident registration number | HV `@KorRRN` (1) | new package `southkorea`, `rrn()` | `"must be a valid resident registration number"` | P3 |
| 38 | United States: ABA routing number | VJS `isAbaRouting` (1) | `unitedstates.abaRouting()` | `"must be a valid ABA routing number"` | P3 |
| 39 | Per-locale passport, identity card, tax ID, license plate, VAT | VJS `isPassportNumber`, `isIdentityCard`, `isTaxID`, `isLicensePlate`, `isVAT`; CV the same (2) | One country at a time, in the country packages (see Q10) | n/a | P3 |

### 3.5 Numbers

| # | Constraint | Types | Libraries (n) | Proposed signature | Default message | Pri. |
|---|---|---|---|---|---|---|
| 40 | Whole number | `Double`, `Float`, `BigDecimal` | Zod `z.int`, Joi `number.integer`, Yup `integer`, CV `@IsInt` (4) | `fun ValidationNode<Double>.integer(message: String = messages.integer)`, the same for `Float` and `BigDecimal` | Reuse `integer = "must be an integer"` | P1 |
| 41 | `multipleOf` on floating point | `Double`, `Float` | Zod `.multipleOf`, Joi `number.multiple`, Py `multiple_of`, Kf `multipleOf`, Lv `multiple_of` (5) | `fun ValidationNode<Double>.multipleOf(factor: Double?, message: String = messages.multipleOf)` **+λ**, same for `Float` (see Q5) | Reuse `multipleOf` | P1 |
| 42 | Count of digits in an integer | `Int`, `Long`, `BigInteger` | Lv `digits`, `digits_between`, `min_digits`, `max_digits`; Vk `hasDigits`; HV `@Digits(integer)` (3) | `minDigits(min: Int)`, `maxDigits(max: Int)`, `digitCount(count: Int)` **+λ** (`digits` is taken by the `String` rule, see Q6) | `minDigits = "must have at least {min} digits"`, `maxDigits = "must have at most {max} digits"`, `digitCount = "must have exactly {count} digits"` | P2 |
| 43 | Total digits (precision) | `BigDecimal` | FV `PrecisionScale`, Py `max_digits` (2) | `maxDigits(max: Int)` on `BigDecimal` as well (same key as #42) | `maxDigits` | P2 |
| 44 | Exact or minimum decimal places | `BigDecimal` | Ak `hasFractionalCountEqualTo`, Vk `hasDecimalDigits(min, max)`, Lv `decimal:min,max` (3) | `decimalPlaces(count: Int)`, `minDecimalPlaces(min: Int)` **+λ**, next to `maxDecimalPlaces` | `decimalPlaces = "must have exactly {count} decimal places"`, `minDecimalPlaces = "must have at least {min} decimal places"` | P2 |
| 45 | Decimal places on floating point | `Double`, `Float` | Ak `hasFractionalCountEqualTo` (Float/Double), Vk `hasDecimalDigits` (Double), Joi `number.precision` (3) | `maxDecimalPlaces(max: Int)` on `Double`/`Float` (see Q5) | Reuse `maxDecimalPlaces` | P2 |
| 46 | Exclusive range | numbers | FV `ExclusiveBetween`, HV `@DecimalMin/@DecimalMax(inclusive = false)` (2) | Not proposed: `greaterThan` + `lessThan` say it. Alternative: `inclusive: Boolean = true` on `between` | n/a | P3 |
| 47 | Unsigned and other `Comparable` types | `UInt`, `ULong`, `UShort`, `UByte`, any `Comparable<T>` | Kf `minimum`/`maximum`/`exclusiveMinimum`/`exclusiveMaximum` on `Comparable<T>`; Ak on `Number` (2) | Either per-type overloads (`ValidationNode<UInt>.min(min: UInt?, ...)`, the current style), or one `fun <T : Comparable<T>> ValidationNode<T>.min(...)` set (see Q7) | Reuse `min`, `max`, `greaterThan`, `lessThan`, `between` | P2 |
| 48 | `notBetween`, `zero`, `one` | numbers | Vk `isNotBetween`, `isZero`, `isOne` (1) | Not proposed: `not { between(...) }`, `equalTo(0)` | n/a | P3 |

### 3.6 Collections, arrays and maps

| # | Constraint | Types | Libraries (n) | Proposed signature | Default message | Pri. |
|---|---|---|---|---|---|---|
| 49 | Must not contain an element | `List<E>`, `Set<E>`, `Array<E>` | Ak `isNotContaining`, CV `@ArrayNotContains`, Lv `doesnt_contain` (3) | `fun <E> ValidationNode<List<E>>.notContains(element: E?, message: String = messages.notContainsElement)` **+λ**, plus `Set`/`Array` | `notContainsElement = "must not contain {element}"` (the `String` rule owns `notContains`, just as `containsElement` sits beside `contains`) | P1 |
| 50 | Must contain none of | `List<E>`, `Set<E>`, `Array<E>` | CV `@ArrayNotContains(values)`, Lv `doesnt_contain:a,b` (2) + `noneOf` symmetry | `fun <E> ValidationNode<List<E>>.containsNone(elements: Collection<E>?, message: String = messages.containsNone)` **+λ** | `containsNone = "must not contain any of {elements}"` | P1 (rounds out `containsAll`/`containsAny`) |
| 51 | Must be empty | `Collection<*>`, `Array<*>`, `Map<*, *>` | Ak `isEmpty`, Vk `isEmpty`, FV `Empty` (3) | `fun ValidationNode<out Collection<*>>.empty(message: String = messages.empty)`, same for `Array` and `Map` (same key as #4) | `empty` | P2 |
| 52 | Map contains a value | `Map<*, V>` | Ak `isContainingValue`, Vk `containsValue`, `containsAllValues`, `containsAnyValue` (2) | `fun <V> ValidationNode<out Map<*, V>>.containsValue(value: V?, message: String = messages.containsValue)` **+λ** | `containsValue = "must contain the value {value}"` | P2 |
| 53 | Map lacks a key | `Map<K, *>` | Ak `isNotContainingKey` (1) | `fun <K> ValidationNode<out Map<K, *>>.notContainsKey(key: K?, message: String = messages.notContainsKey)` **+λ** | `notContainsKey = "must not contain the key {key}"` | P3 |
| 54 | Only allowed keys | `Map<K, *>` | Lv `array:keys`, Zod `z.strictObject`, Joi `object.unknown(false)`, Yup `noUnknown` (4, as object rules) | `fun <K> ValidationNode<out Map<K, *>>.onlyKeys(keys: Collection<K>?, message: String = messages.onlyKeys)` **+λ** | `onlyKeys = "must contain only the keys {keys}"` | P2 |
| 55 | Byte array size | `ByteArray` (and maybe other primitive arrays) | Joi `binary.min/max/length`, Py `conbytes(min_length, max_length)`, Zod `file.min/max` (3) | `minSize`, `maxSize`, `size`, `sizeBetween`, `notEmpty` overloads on `ByteArray` (see Q8) | Reuse the size keys, or `minBytes`/`maxBytes` from #9 | P2 |
| 56 | Sorted | `List<T : Comparable<T>>` | Joi `array.sort` (1) | `sorted()`, `sortedBy(selector)` | `sorted = "must be sorted"` | P3 |
| 57 | No null elements | `Collection<E?>` | Joi `array.sparse(false)` (1) | `noNulls()` | `noNulls = "must not contain null"` | P3 (Kotlin types usually prevent it) |
| 58 | Some element matches | `Iterable<E>` | Joi `array.has` (1) | An accessor next to `each` (for example `any { }`), not a constraint | n/a | P3 |

### 3.7 Object-level rules (premises)

These follow `Premises.kt`, not the `ValidationNode` convention: `context(validation: Validation)`, `vararg properties:
KProperty0<*>`, `path: String = ""`, `message: String = messages.<key>`, reported through `presence` with `{fields}`.

| # | Constraint | Libraries (n) | Proposed signature | Default message | Pri. |
|---|---|---|---|---|---|
| 59 | B is required when A is filled | Joi `object.with`, Lv `required_with`, `required_with_all`, `present_with` (2) | `fun requiredWith(property: KProperty0<*>, vararg properties: KProperty0<*>, path: String = "", message: String = messages.requiredWith)` | `requiredWith = "{fields} are required when {field} is filled"` | P2 |
| 60 | B must be empty when A is filled | Joi `object.without`, Lv `prohibits`, `missing_with`, `exclude_with` (2) | `fun forbiddenWith(property: KProperty0<*>, vararg properties: KProperty0<*>, path: String = "", message: String = messages.forbiddenWith)` | `forbiddenWith = "{fields} must be empty when {field} is filled"` | P2 |
| 61 | Not all together (`nand`) | Joi `object.nand` (1) | Not proposed: `atMostOf(n - 1, ...)` | n/a | P3 |

## 4. Covered already, or left out on purpose

- **Covered by existing rules**: required/nullable/optional modifiers (`required`, `ifPresent`, `forbidden`); Joi `or`,
  `xor`, `oxor`, `and` (`atLeastOneOf`, `exactlyOneOf`, `atMostOneOf`, `allOrNoneOf`); Laravel `required_without`
  (`atLeastOneOf`); `same`/`confirmed`/`different` (`equalTo`/`notEqualTo` with the other value); `in`/`not_in`
  (`oneOf`/`noneOf`); Laravel `required_if` and Yup/Joi `when` (plain Kotlin `if`); `distinct` with a key (`distinctBy`);
  `@UniqueElements` (`distinct`); `@Range` (`between`); `@DurationMin`/`@DurationMax` (`min`/`max` on `Duration`); date
  `min`/`max` (`afterOrEqual`/`beforeOrEqual`); `date_equals` (`equalTo`); NaN checks (`finite`); custom rules (`constraint`,
  `or`, `and`, `not`).
- **Temporal rules owned by the other session**: `minAge`/`maxAge`, `withinNext`/`withinLast`, `today` (Vk
  `isToday`/`isNotToday`), `weekday`/`weekend`. The only temporal gap found outside that list is the sign of a
  `Duration` (Ak `isPositive`/`isNegative` on durations; 1 library, P3). It touches `TemporalConstraints.kt`, which the
  clock rework is editing, so it should wait until that work merges.
- **Runtime type checks** that Kotlin's types already give: `IsString`, `IsInt`, `IsArray`, `IsBoolean`, Laravel
  `string`/`array`/`list`/`numeric:strict`, Zod/Joi base types, Pydantic `Strict*`, `AwareDatetime`/`NaiveDatetime`
  (`OffsetDateTime` vs `LocalDateTime`).
- **I/O and framework rules**: Laravel `exists`, `unique`, `active_url`, `current_password`, `email:dns`; Pydantic
  `FilePath`, `DirectoryPath`, `NewPath`; uploaded files (`file`, `image`, `mimes`, `mimetypes`, `dimensions`,
  `extensions`, Zod `z.file`).
- **Transforms, not checks**: trim, truncate, replace, case conversion, Joi `rename`, Pydantic `to_upper`.
- **Money**: HV `@Currency` and Valiktor's JavaMoney/Joda-Money modules need JSR 354. If wanted, that is a separate
  module like `validity-phone`.
- **DSNs** (Pydantic `PostgresDsn`, `RedisDsn`, ...): `url(schemes = setOf("postgres"))` covers the common case.
- **kotlinx-datetime types** (Akkurate has a module for them): validity is JVM and `java.time` only. A separate module
  would be its own decision.

## 5. Phased implementation plan

Small PRs, one source file (plus `Messages.kt` and the matching test file) each where possible. Each PR adds tests in the
harness shape (nested inner class per function, Given/When/Then, one assertion, `ValidationNode` built directly) and keeps
Detekt and Konsist green. The order puts the P1 rules and the fewest design questions first.

| Phase | PR | Files | Contents | Depends on |
|---|---|---|---|---|
| 1 | Collections and maps | `CollectionConstraints.kt`, `MapConstraints.kt`, `Messages.kt` | #49 `notContains`, #50 `containsNone`, #51 `empty` (collection, array, map), #52 `containsValue`, #53 `notContainsKey`, #54 `onlyKeys` | Q2 (`empty` key shared with strings) |
| 2 | Whole numbers and digits | `NumberConstraints.kt`, `Messages.kt` | #40 `integer` on `Double`/`Float`/`BigDecimal`, #42–#44 `minDigits`/`maxDigits`/`digitCount`/`decimalPlaces`/`minDecimalPlaces` | Q6 (names) |
| 3 | Floating-point arithmetic | `NumberConstraints.kt` | #41 `multipleOf` and #45 `maxDecimalPlaces` on `Double`/`Float` | Q5 (tolerance policy); after phase 2 (same file) |
| 4 | Boolean | new `BooleanConstraints.kt`, `Messages.kt` | #1 `accepted`, #2 `declined` | Q1 (names) |
| 5 | Strings, no API change | `StringConstraints.kt`, `Messages.kt` | #4 `empty`, #5 `blank`, #7 `trimmed`, #8 `containsOnly`, #9 `minBytes`/`maxBytes`, #12 `boolean` | Q2; after phase 1 if `empty` shares its key |
| 6 | Case-insensitive strings | `StringConstraints.kt`, `AnyConstraints.kt` (for overload resolution only) | #6 `ignoreCase` on `contains`…`notEndsWith`, `enum`, plus `String` `equalTo`/`oneOf` | Q3 (changes existing signatures); after phase 5 (same file) |
| 7 | Internet and location formats | `InternetConstraints.kt`, `LocationConstraints.kt`, `Messages.kt` | #18 `domain`, #19 `port` (`String`; the `Int` overload goes in `NumberConstraints.kt`, see Q11), #20 `coordinates`, #21 `isoCountryCodeNumeric` | none |
| 8 | Object premises | `Premises.kt`, `Messages.kt` | #59 `requiredWith`, #60 `forbiddenWith` | Q12 (`{field}` placeholder and reported path) |
| 9 | Type coverage | `NumberConstraints.kt`, `CollectionConstraints.kt` | #47 unsigned/`Comparable` overloads, #55 `ByteArray` sizes | Q7, Q8 |
| 10 | Country identifiers (optional) | `brazil/`, `poland/`, new `southkorea/`, `unitedstates/`, `Messages.kt` | #35 `tituloEleitoral`, #36 `regon`, #37 `rrn`, #38 `abaRouting` | none; one PR per country is fine |
| 11 | P3 leftovers (pick by demand) | various | #3 `instanceOf`, #10 `normalized`, #14 `octal`, #16 `emoji`, #22 `cidrV4`/`cidrV6`, #24 `scriptCode`, #26 `cuid`/`cuid2`/`nanoid`, #27 `magnetUri`/`mailtoUri`, #28 card brands, #32 `mod11`, #33 `containerCode`, #56 `sorted`, #57 `noNulls`, `Duration` sign | `Duration` sign waits for the clock rework |

Phases 1, 2, 4, 7, 8 and 10 touch different files and can run in parallel. Phases 3, 5, 6 and 9 share files with an
earlier phase, so they follow it.

## 6. Design questions for the maintainer

1. **Boolean names.** The harness forbids `isTrue`/`isFalse` for constraints. Options: `accepted`/`declined` (Laravel;
   suits consent checkboxes, odd for other flags), `` `true`() ``/`` `false`() `` (needs backticks), or
   `mustBeTrue`/`mustBeFalse` (breaks the noun/adjective style of the other names). The proposal uses
   `accepted`/`declined`. The message could read "must be true" whichever name wins.
2. **One `empty` key for strings, collections and maps?** Messages today split by value type (`minLength` vs `minSize`
   vs `minEntries`), but `notEmpty` is a single key shared by all of them. The proposal follows `notEmpty` and uses one
   `empty`.
3. **Case-insensitivity: a parameter or new functions?** A parameter (`ignoreCase: Boolean = false`, the Kotlin stdlib
   style) changes the signatures of six existing rules but adds no names. Separate `…IgnoringCase` functions (the Valiktor
   style) double the surface. Also: should a `String`-only `equalTo(other: String?, ignoreCase)` sit beside the generic
   `equalTo<V>`? Overload resolution picks the more specific receiver, but the KDoc has to explain it.
4. **What does "length" count?** `minLength`/`maxLength` count UTF-16 units (`String.length`), so one emoji can count as
   2. HV adds `@CodePointLength` for this. Options: keep it and document it, add a `CountMode` parameter, or switch to
   code points.
5. **Floating-point arithmetic.** `multipleOf` and decimal places on `Double`/`Float` hit binary rounding (`0.3` is not a
   multiple of `0.1` in IEEE 754). Options: leave them out on purpose (today's state; the user converts to
   `BigDecimal`), compare through `BigDecimal(value.toString())` (what users expect), or take an explicit tolerance.
6. **Digit-count names.** `digits()` already means "only digit characters" on `String`. The proposal uses
   `minDigits`/`maxDigits`/`digitCount` on numbers. Should `maxDigits` on `BigDecimal` mean total digits (precision, as in
   Pydantic), beside the existing `maxIntegerDigits`/`maxDecimalPlaces`?
7. **Generic `Comparable` overloads vs. per-type ones.** Today each numeric type has its own overload. One generic
   `fun <T : Comparable<T>> ValidationNode<T>.min(...)` would cover `UInt`, `ULong`, `Char`, `BigDecimal`, and so on,
   but could clash with the per-type overloads and with the temporal `after`/`before` naming. Per-type unsigned overloads
   keep today's style at the cost of about 80 more functions.
8. **Primitive arrays.** Should `ByteArray` (and `IntArray`, `LongArray`, ...) get the size rules? If only `ByteArray`,
   should its messages talk about bytes (`minBytes`) rather than items?
9. **Composite rules** such as a strong password or `alpha_dash`. validity leans on composition (`containsUppercase`,
   `containsSymbol`, `containsOnly`). Keep it that way, or add a convenience `password(...)` rule?
10. **Country rules: which countries, and how far?** validator.js offers passport, identity card, tax ID, license plate
    and VAT formats for dozens of locales. Adding them one country at a time in the existing packages keeps today's shape.
    Is there a target list, for example the countries validity already has?
11. **Placement of `port`.** It applies to `Int` (`NumberConstraints.kt`?) and `String` (`InternetConstraints.kt`).
    Today `latitude` keeps all its overloads in `LocationConstraints.kt`, by topic rather than by type. Should `port`
    follow that and live in `InternetConstraints.kt` for both types?
12. **Message shape of `requiredWith`/`forbiddenWith`.** The current premises fill `{fields}` only. These need the
    trigger field too (`{field}`), and a choice of which fields go in the violation's `fields` metadata (the missing
    dependents, presumably), in line with what PR #27 did for `atLeastOf`.
