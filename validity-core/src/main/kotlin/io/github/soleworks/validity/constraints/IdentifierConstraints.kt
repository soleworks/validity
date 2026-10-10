package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.messages

private const val UUID_VERSION_INDEX = 14
private const val HEX_RADIX = 16
private const val ISBN10_CHECK_TEN = 'X'

private val UUID_FORMAT = Regex("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}")
private val ULID_FORMAT = Regex("[0-7][0-9A-HJKMNP-TV-Za-hjkmnp-tv-z]{25}")
private val OBJECT_ID_FORMAT = Regex("[0-9a-fA-F]{24}")
private val SEMVER_FORMAT = Regex(
    "(0|[1-9]\\d*)\\.(0|[1-9]\\d*)\\.(0|[1-9]\\d*)" +
        "(?:-((?:0|[1-9]\\d*|\\d*[a-zA-Z-][0-9a-zA-Z-]*)(?:\\.(?:0|[1-9]\\d*|\\d*[a-zA-Z-][0-9a-zA-Z-]*))*))?" +
        "(?:\\+([0-9a-zA-Z-]+(?:\\.[0-9a-zA-Z-]+)*))?"
)
private val ISBN10_FORMAT = Regex("\\d{9}[\\dX]")
private val ISBN13_FORMAT = Regex("97[89]\\d{10}")
private val ISSN_FORMAT = Regex("\\d{4}-?\\d{3}[\\dX]")
private val EAN_FORMAT = Regex("\\d{8}|\\d{13}|\\d{14}")
private val ISRC_FORMAT = Regex("[A-Z]{2}[A-Z0-9]{3}\\d{7}")
private val IMEI_FORMAT = Regex("\\d{15}")
private val LUHN_FORMAT = Regex("\\d{2,}")
private val HEX_FORMAT = Regex("[0-9a-fA-F]+")

public fun ValidationNode<String>.uuid(
    version: Int? = null,
    message: String = messages.uuid
): Unit = uuid(
    version = version,
    message = { message.replace("{version}", "$version") }
)

public fun ValidationNode<String>.uuid(
    version: Int? = null,
    message: String.(Int?) -> String
): Unit = constraint(
    message = { it.message(version) },
    code = "uuid",
    predicate = { it.isUuid(version) }
)

public fun ValidationNode<String>.ulid(
    message: String = messages.ulid
): Unit = constraint(
    message = message,
    code = "ulid",
    predicate = { ULID_FORMAT.matches(it) }
)

public fun ValidationNode<String>.objectId(
    message: String = messages.objectId
): Unit = constraint(
    message = message,
    code = "objectId",
    predicate = { OBJECT_ID_FORMAT.matches(it) }
)

public fun ValidationNode<String>.semver(
    message: String = messages.semver
): Unit = constraint(
    message = message,
    code = "semver",
    predicate = { SEMVER_FORMAT.matches(it) }
)

public fun ValidationNode<String>.isbn(
    message: String = messages.isbn
): Unit = constraint(
    message = message,
    code = "isbn",
    predicate = { it.withoutSeparators().let { isbn -> isbn.isIsbn10() || isbn.isIsbn13() } }
)

public fun ValidationNode<String>.isbn10(
    message: String = messages.isbn10
): Unit = constraint(
    message = message,
    code = "isbn10",
    predicate = { it.withoutSeparators().isIsbn10() }
)

public fun ValidationNode<String>.isbn13(
    message: String = messages.isbn13
): Unit = constraint(
    message = message,
    code = "isbn13",
    predicate = { it.withoutSeparators().isIsbn13() }
)

public fun ValidationNode<String>.issn(
    message: String = messages.issn
): Unit = constraint(
    message = message,
    code = "issn",
    predicate = { ISSN_FORMAT.matches(it) && it.replace("-", "").isIssn() }
)

public fun ValidationNode<String>.ean(
    message: String = messages.ean
): Unit = constraint(
    message = message,
    code = "ean",
    predicate = { EAN_FORMAT.matches(it) && it.hasGtinCheckDigit() }
)

public fun ValidationNode<String>.isrc(
    message: String = messages.isrc
): Unit = constraint(
    message = message,
    code = "isrc",
    predicate = { ISRC_FORMAT.matches(it) }
)

public fun ValidationNode<String>.imei(
    message: String = messages.imei
): Unit = constraint(
    message = message,
    code = "imei",
    predicate = { IMEI_FORMAT.matches(it) && it.hasLuhnCheckDigit() }
)

public fun ValidationNode<String>.luhn(
    message: String = messages.luhn
): Unit = constraint(
    message = message,
    code = "luhn",
    predicate = { LUHN_FORMAT.matches(it) && it.hasLuhnCheckDigit() }
)

public fun ValidationNode<String>.hash(
    algorithm: HashAlgorithm,
    message: String = messages.hash
): Unit = hash(
    algorithm = algorithm,
    message = { message.replace("{algorithm}", "$algorithm") }
)

public fun ValidationNode<String>.hash(
    algorithm: HashAlgorithm,
    message: String.(HashAlgorithm) -> String
): Unit = constraint(
    message = { it.message(algorithm) },
    code = "hash",
    predicate = { it.length == algorithm.length && HEX_FORMAT.matches(it) }
)

internal fun String.hasLuhnCheckDigit(): Boolean = reversed()
    .mapIndexed { index, char -> if (index % 2 == 1) char.digitToInt().luhnDoubled() else char.digitToInt() }
    .sum() % 10 == 0

private fun Int.luhnDoubled(): Int = (this * 2).let { doubled -> if (doubled > 9) doubled - 9 else doubled }

private fun String.isUuid(version: Int?): Boolean =
    UUID_FORMAT.matches(this) && (version == null || this[UUID_VERSION_INDEX].digitToInt(HEX_RADIX) == version)

private fun String.withoutSeparators(): String = filterNot { it == '-' || it == ' ' }

private fun String.isIsbn10(): Boolean = ISBN10_FORMAT.matches(this) && mapIndexed { index, char ->
    (10 - index) * if (char == ISBN10_CHECK_TEN) 10 else char.digitToInt()
}.sum() % 11 == 0

private fun String.isIsbn13(): Boolean = ISBN13_FORMAT.matches(this) && hasGtinCheckDigit()

private fun String.isIssn(): Boolean {
    val sum = take(7).mapIndexed { index, char -> (8 - index) * char.digitToInt() }.sum()
    val check = (11 - sum % 11) % 11

    return last() == if (check == 10) ISBN10_CHECK_TEN else check.digitToChar()
}

private fun String.hasGtinCheckDigit(): Boolean {
    val sum = dropLast(1)
        .reversed()
        .mapIndexed { index, char -> char.digitToInt() * if (index % 2 == 0) 3 else 1 }
        .sum()

    return last().digitToInt() == (10 - sum % 10) % 10
}
