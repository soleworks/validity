package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.filled
import io.github.soleworks.validity.messages

private const val UUID_VERSION_INDEX = 14
private const val HEX_RADIX = 16
private const val MODULUS = 11
private const val DECIMAL_MODULUS = 10
private const val MAX_DIGIT = 9
private const val CHECK_TEN = 'X'
private const val CHECK_TEN_VALUE = 10
private const val GTIN_WEIGHT = 3

private val UUID_FORMAT = Regex("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}")
private val ULID_FORMAT = Regex("[0-7][0-9A-HJKMNP-TV-Za-hjkmnp-tv-z]{25}")
private val OBJECT_ID_FORMAT = Regex("[0-9a-fA-F]{24}")
private val SEMVER_FORMAT = Regex(
    """(0|[1-9]\d*)\.(0|[1-9]\d*)\.(0|[1-9]\d*)""" +
        """(?:-((?:0|[1-9]\d*|\d*[a-zA-Z-][0-9a-zA-Z-]*)(?:\.(?:0|[1-9]\d*|\d*[a-zA-Z-][0-9a-zA-Z-]*))*))?""" +
        """(?:\+([0-9a-zA-Z-]+(?:\.[0-9a-zA-Z-]+)*))?"""
)
private val ISBN10_FORMAT = Regex("""\d{9}[\dX]""")
private val ISBN13_FORMAT = Regex("""97[89]\d{10}""")
private val ISSN_FORMAT = Regex("""\d{4}-?\d{3}[\dX]""")
private val EAN_FORMAT = Regex("""\d{8}|\d{13}|\d{14}""")
private val ISRC_FORMAT = Regex("""[A-Z]{2}[A-Z0-9]{3}\d{7}""")
private val IMEI_FORMAT = Regex("""\d{15}""")
private val LUHN_FORMAT = Regex("""\d{2,}""")
private val ISBN10_WEIGHTS = (10 downTo 1).toList()
private val ISSN_WEIGHTS = (8 downTo 2).toList()
private val GTIN_WEIGHTS = List(13) { index -> if (index % 2 == 0) GTIN_WEIGHT else 1 }

/**
 * Checks that the string is a UUID in its 36-character form, such as `123e4567-e89b-12d3-a456-426614174000`, in either
 * case.
 *
 * Fails with code `uuid` and, by default, the message `must be a valid UUID`
 * ([Messages.uuid][io.github.soleworks.validity.Messages.uuid]).
 *
 * ```kotlin
 * ::id required { uuid(version = 4) }
 * ```
 *
 * @param version the UUID version required, such as `4` or `7`. With `null`, any version passes.
 * @param message the message of the violation. `{version}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.uuid(
    version: Int? = null,
    message: String = messages.uuid
): Unit = uuid(
    version = version,
    message = { message.filled("version" to version) }
)

/**
 * Checks that the string is a UUID in its 36-character form, such as `123e4567-e89b-12d3-a456-426614174000`, in either
 * case.
 *
 * Fails with code `uuid` and the message that [message] builds.
 *
 * @param version the UUID version required, such as `4` or `7`. With `null`, any version passes.
 * @param message builds the message from the value (the receiver) and [version].
 */
public fun ValidationNode<String>.uuid(
    version: Int? = null,
    message: suspend String.(Int?) -> String
): Unit = constraint(
    message = { it.message(version) },
    code = "uuid",
    predicate = { it.isUuid(version) }
)

/**
 * Checks that the string is a ULID: 26 Crockford Base32 characters, in either case.
 *
 * Fails with code `ulid` and, by default, the message `must be a valid ULID`
 * ([Messages.ulid][io.github.soleworks.validity.Messages.ulid]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.ulid(
    message: String = messages.ulid
): Unit = constraint(
    message = message,
    code = "ulid",
    predicate = { ULID_FORMAT.matches(it) }
)

/**
 * Checks that the string is a MongoDB ObjectId: 24 hexadecimal digits.
 *
 * Fails with code `objectId` and, by default, the message `must be a valid ObjectId`
 * ([Messages.objectId][io.github.soleworks.validity.Messages.objectId]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.objectId(
    message: String = messages.objectId
): Unit = constraint(
    message = message,
    code = "objectId",
    predicate = { OBJECT_ID_FORMAT.matches(it) }
)

/**
 * Checks that the string is a Semantic Versioning 2.0 version, such as `1.4.0`, `2.0.0-rc.1` or `1.0.0+build.5`.
 *
 * A leading `v` is not accepted.
 *
 * Fails with code `semver` and, by default, the message `must be a valid semantic version`
 * ([Messages.semver][io.github.soleworks.validity.Messages.semver]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.semver(
    message: String = messages.semver
): Unit = constraint(
    message = message,
    code = "semver",
    predicate = { SEMVER_FORMAT.matches(it) }
)

/**
 * Checks that the string is an ISBN-10 or an ISBN-13 with a valid check digit.
 *
 * Spaces and dashes are ignored.
 *
 * Fails with code `isbn` and, by default, the message `must be a valid ISBN`
 * ([Messages.isbn][io.github.soleworks.validity.Messages.isbn]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.isbn(
    message: String = messages.isbn
): Unit = constraint(
    message = message,
    code = "isbn",
    predicate = { it.withoutSeparators().let { isbn -> isbn.isIsbn10() || isbn.isIsbn13() } }
)

/**
 * Checks that the string is an ISBN-10 with a valid check digit, which can be `X`.
 *
 * Spaces and dashes are ignored.
 *
 * Fails with code `isbn10` and, by default, the message `must be a valid ISBN-10`
 * ([Messages.isbn10][io.github.soleworks.validity.Messages.isbn10]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.isbn10(
    message: String = messages.isbn10
): Unit = constraint(
    message = message,
    code = "isbn10",
    predicate = { it.withoutSeparators().isIsbn10() }
)

/**
 * Checks that the string is an ISBN-13, starting with 978 or 979, with a valid check digit.
 *
 * Spaces and dashes are ignored.
 *
 * Fails with code `isbn13` and, by default, the message `must be a valid ISBN-13`
 * ([Messages.isbn13][io.github.soleworks.validity.Messages.isbn13]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.isbn13(
    message: String = messages.isbn13
): Unit = constraint(
    message = message,
    code = "isbn13",
    predicate = { it.withoutSeparators().isIsbn13() }
)

/**
 * Checks that the string is an ISSN, such as `0317-8471`, with a valid check digit, which can be `X`.
 *
 * The dash is optional.
 *
 * Fails with code `issn` and, by default, the message `must be a valid ISSN`
 * ([Messages.issn][io.github.soleworks.validity.Messages.issn]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.issn(
    message: String = messages.issn
): Unit = constraint(
    message = message,
    code = "issn",
    predicate = { ISSN_FORMAT.matches(it) && it.withoutSeparators().isIssn() }
)

/**
 * Checks that the string is an EAN-8, EAN-13 or GTIN-14 barcode number with a valid check digit.
 *
 * Fails with code `ean` and, by default, the message `must be a valid EAN`
 * ([Messages.ean][io.github.soleworks.validity.Messages.ean]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.ean(
    message: String = messages.ean
): Unit = constraint(
    message = message,
    code = "ean",
    predicate = { EAN_FORMAT.matches(it) && it.hasGtinCheckDigit() }
)

/**
 * Checks that the string is an ISRC with no dashes, such as `USRC17607839`.
 *
 * Fails with code `isrc` and, by default, the message `must be a valid ISRC`
 * ([Messages.isrc][io.github.soleworks.validity.Messages.isrc]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.isrc(
    message: String = messages.isrc
): Unit = constraint(
    message = message,
    code = "isrc",
    predicate = { ISRC_FORMAT.matches(it) }
)

/**
 * Checks that the string is an IMEI: 15 digits with a valid Luhn check digit.
 *
 * Fails with code `imei` and, by default, the message `must be a valid IMEI`
 * ([Messages.imei][io.github.soleworks.validity.Messages.imei]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.imei(
    message: String = messages.imei
): Unit = constraint(
    message = message,
    code = "imei",
    predicate = { IMEI_FORMAT.matches(it) && it.hasLuhnCheckDigit() }
)

/**
 * Checks that the string has only digits, at least two, and its last digit is a valid Luhn check digit.
 *
 * Fails with code `luhn` and, by default, the message `must have a valid Luhn check digit`
 * ([Messages.luhn][io.github.soleworks.validity.Messages.luhn]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.luhn(
    message: String = messages.luhn
): Unit = constraint(
    message = message,
    code = "luhn",
    predicate = { LUHN_FORMAT.matches(it) && it.hasLuhnCheckDigit() }
)

/**
 * Checks that the string is a hexadecimal digest of the length [algorithm] produces.
 *
 * Only the length and the digits are checked: an MD5 and an MD4 hash look the same.
 *
 * Fails with code `hash` and, by default, the message `must be a valid {algorithm} hash`
 * ([Messages.hash][io.github.soleworks.validity.Messages.hash]).
 *
 * ```kotlin
 * ::checksum required { hash(HashAlgorithm.SHA256) }
 * ```
 *
 * @param algorithm the algorithm whose digest length is required.
 * @param message the message of the violation. `{algorithm}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.hash(
    algorithm: HashAlgorithm,
    message: String = messages.hash
): Unit = hash(
    algorithm = algorithm,
    message = { message.filled("algorithm" to algorithm) }
)

/**
 * Checks that the string is a hexadecimal digest of the length [algorithm] produces.
 *
 * Only the length and the digits are checked: an MD5 and an MD4 hash look the same.
 *
 * Fails with code `hash` and the message that [message] builds.
 *
 * @param algorithm the algorithm whose digest length is required.
 * @param message builds the message from the value (the receiver) and [algorithm].
 */
public fun ValidationNode<String>.hash(
    algorithm: HashAlgorithm,
    message: suspend String.(HashAlgorithm) -> String
): Unit = constraint(
    message = { it.message(algorithm) },
    code = "hash",
    predicate = { it.length == algorithm.length && HEXADECIMAL_FORMAT.matches(it) }
)

internal fun String.hasLuhnCheckDigit(): Boolean = digits()
    .reversed()
    .mapIndexed { index, digit -> if (index % 2 == 1) digit.luhnDoubled() else digit }
    .sum() % DECIMAL_MODULUS == 0

private fun Int.luhnDoubled(): Int =
    (this * 2).let { doubled -> if (doubled > MAX_DIGIT) doubled - MAX_DIGIT else doubled }

private fun String.isUuid(
    version: Int?
): Boolean = UUID_FORMAT.matches(this) &&
    (version == null || this[UUID_VERSION_INDEX].digitToInt(HEX_RADIX) == version)

private fun String.withoutSeparators(): String = filterNot { it == '-' || it == ' ' }

private fun String.checkValues(): List<Int> =
    map { char -> if (char == CHECK_TEN) CHECK_TEN_VALUE else char.digitToInt() }

private fun String.isIsbn10(): Boolean =
    ISBN10_FORMAT.matches(this) && checkValues().weightedSum(ISBN10_WEIGHTS) % MODULUS == 0

private fun String.isIsbn13(): Boolean = ISBN13_FORMAT.matches(this) && hasGtinCheckDigit()

private fun String.isIssn(): Boolean {
    val checkDigit = (MODULUS - digits().weightedSum(ISSN_WEIGHTS) % MODULUS) % MODULUS

    return last() == if (checkDigit == CHECK_TEN_VALUE) CHECK_TEN else checkDigit.digitToChar()
}

private fun String.hasGtinCheckDigit(): Boolean {
    val digits = digits()
    val sum = digits.dropLast(1).reversed().weightedSum(GTIN_WEIGHTS)

    return digits.last() == (DECIMAL_MODULUS - sum % DECIMAL_MODULUS) % DECIMAL_MODULUS
}
