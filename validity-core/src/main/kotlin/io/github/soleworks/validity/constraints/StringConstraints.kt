package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.filled
import io.github.soleworks.validity.messages
import java.nio.charset.Charset
import java.text.Normalizer
import kotlin.enums.enumEntries

private const val ASCII_LIMIT = 128

private val ASCII_DIGITS = '0'..'9'

internal val NUMERIC_FORMAT = Regex("""[+-]?\d+(?:\.\d+)?""")
private val INTEGER_FORMAT = Regex("""[+-]?\d+""")

public fun ValidationNode<String>.minLength(
    min: Int,
    message: String = messages.minLength
): Unit = minLength(
    min = min,
    message = { message.filled("min" to min) }
)

public fun ValidationNode<String>.minLength(
    min: Int,
    message: String.(Int) -> String
): Unit = constraint(
    message = { it.message(min) },
    code = "minLength",
    predicate = { it.length >= min }
)

public fun ValidationNode<String>.maxLength(
    max: Int,
    message: String = messages.maxLength
): Unit = maxLength(
    max = max,
    message = { message.filled("max" to max) }
)

public fun ValidationNode<String>.maxLength(
    max: Int,
    message: String.(Int) -> String
): Unit = constraint(
    message = { it.message(max) },
    code = "maxLength",
    predicate = { it.length <= max }
)

public fun ValidationNode<String>.length(
    length: Int,
    message: String = messages.length
): Unit = length(
    length = length,
    message = { message.filled("length" to length) }
)

public fun ValidationNode<String>.length(
    length: Int,
    message: String.(Int) -> String
): Unit = constraint(
    message = { it.message(length) },
    code = "length",
    predicate = { it.length == length }
)

public fun ValidationNode<String>.lengthBetween(
    min: Int,
    max: Int,
    message: String = messages.lengthBetween
): Unit = lengthBetween(
    min = min,
    max = max,
    message = { _, _ -> message.filled("min" to min, "max" to max) }
)

public fun ValidationNode<String>.lengthBetween(
    min: Int,
    max: Int,
    message: String.(Int, Int) -> String
): Unit = constraint(
    message = { it.message(min, max) },
    code = "lengthBetween",
    predicate = { it.length in min..max }
)

public fun ValidationNode<String>.minBytes(
    min: Int,
    charset: Charset = Charsets.UTF_8,
    message: String = messages.minBytes
): Unit = minBytes(
    min = min,
    charset = charset,
    message = { message.filled("min" to min) }
)

public fun ValidationNode<String>.minBytes(
    min: Int,
    charset: Charset = Charsets.UTF_8,
    message: String.(Int) -> String
): Unit = constraint(
    message = { it.message(min) },
    code = "minBytes",
    predicate = { it.toByteArray(charset).size >= min }
)

public fun ValidationNode<String>.maxBytes(
    max: Int,
    charset: Charset = Charsets.UTF_8,
    message: String = messages.maxBytes
): Unit = maxBytes(
    max = max,
    charset = charset,
    message = { message.filled("max" to max) }
)

public fun ValidationNode<String>.maxBytes(
    max: Int,
    charset: Charset = Charsets.UTF_8,
    message: String.(Int) -> String
): Unit = constraint(
    message = { it.message(max) },
    code = "maxBytes",
    predicate = { it.toByteArray(charset).size <= max }
)

public fun ValidationNode<String>.notEmpty(
    message: String = messages.notEmpty
): Unit = constraint(
    message = message,
    code = "notEmpty",
    predicate = { it.isNotEmpty() }
)

public fun ValidationNode<String>.empty(
    message: String = messages.empty
): Unit = constraint(
    message = message,
    code = "empty",
    predicate = { it.isEmpty() }
)

public fun ValidationNode<String>.notBlank(
    message: String = messages.notBlank
): Unit = constraint(
    message = message,
    code = "notBlank",
    predicate = { it.isNotBlank() }
)

public fun ValidationNode<String>.blank(
    message: String = messages.blank
): Unit = constraint(
    message = message,
    code = "blank",
    predicate = { it.isBlank() }
)

public fun ValidationNode<String>.trimmed(
    message: String = messages.trimmed
): Unit = constraint(
    message = message,
    code = "trimmed",
    predicate = { it == it.trim() }
)

public fun ValidationNode<String>.equalTo(
    other: String?,
    ignoreCase: Boolean,
    message: String = messages.equalTo
): Unit = equalTo(
    other = other,
    ignoreCase = ignoreCase,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<String>.equalTo(
    other: String?,
    ignoreCase: Boolean,
    message: String.(String) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "equalTo",
        predicate = { it.equals(other, ignoreCase) }
    )
}

public fun ValidationNode<String>.oneOf(
    values: Iterable<String>?,
    ignoreCase: Boolean,
    message: String = messages.oneOf
): Unit = oneOf(
    values = values,
    ignoreCase = ignoreCase,
    message = { message.filled("values" to values) }
)

public fun ValidationNode<String>.oneOf(
    values: Iterable<String>?,
    ignoreCase: Boolean,
    message: String.(Iterable<String>) -> String
) {
    if (values == null)
        return

    constraint(
        message = { it.message(values) },
        code = "oneOf",
        predicate = { value -> values.any { it.equals(value, ignoreCase) } }
    )
}

public fun ValidationNode<String>.matches(
    regex: Regex,
    message: String = messages.matches
): Unit = matches(
    regex = regex,
    message = { message.filled("regex" to regex) }
)

public fun ValidationNode<String>.matches(
    regex: Regex,
    message: String.(Regex) -> String
): Unit = constraint(
    message = { it.message(regex) },
    code = "matches",
    predicate = { regex.matches(it) }
)

public fun ValidationNode<String>.notMatches(
    regex: Regex,
    message: String = messages.notMatches
): Unit = notMatches(
    regex = regex,
    message = { message.filled("regex" to regex) }
)

public fun ValidationNode<String>.notMatches(
    regex: Regex,
    message: String.(Regex) -> String
): Unit = constraint(
    message = { it.message(regex) },
    code = "notMatches",
    predicate = { !regex.matches(it) }
)

public fun ValidationNode<String>.contains(
    text: String?,
    ignoreCase: Boolean = false,
    message: String = messages.contains
): Unit = contains(
    text = text,
    ignoreCase = ignoreCase,
    message = { message.filled("text" to text) }
)

public fun ValidationNode<String>.contains(
    text: String?,
    ignoreCase: Boolean = false,
    message: String.(String) -> String
) {
    if (text == null)
        return

    constraint(
        message = { it.message(text) },
        code = "contains",
        predicate = { it.contains(text, ignoreCase) }
    )
}

public fun ValidationNode<String>.notContains(
    text: String?,
    ignoreCase: Boolean = false,
    message: String = messages.notContains
): Unit = notContains(
    text = text,
    ignoreCase = ignoreCase,
    message = { message.filled("text" to text) }
)

public fun ValidationNode<String>.notContains(
    text: String?,
    ignoreCase: Boolean = false,
    message: String.(String) -> String
) {
    if (text == null)
        return

    constraint(
        message = { it.message(text) },
        code = "notContains",
        predicate = { !it.contains(text, ignoreCase) }
    )
}

public fun ValidationNode<String>.startsWith(
    prefix: String?,
    ignoreCase: Boolean = false,
    message: String = messages.startsWith
): Unit = startsWith(
    prefix = prefix,
    ignoreCase = ignoreCase,
    message = { message.filled("prefix" to prefix) }
)

public fun ValidationNode<String>.startsWith(
    prefix: String?,
    ignoreCase: Boolean = false,
    message: String.(String) -> String
) {
    if (prefix == null)
        return

    constraint(
        message = { it.message(prefix) },
        code = "startsWith",
        predicate = { it.startsWith(prefix, ignoreCase) }
    )
}

public fun ValidationNode<String>.notStartsWith(
    prefix: String?,
    ignoreCase: Boolean = false,
    message: String = messages.notStartsWith
): Unit = notStartsWith(
    prefix = prefix,
    ignoreCase = ignoreCase,
    message = { message.filled("prefix" to prefix) }
)

public fun ValidationNode<String>.notStartsWith(
    prefix: String?,
    ignoreCase: Boolean = false,
    message: String.(String) -> String
) {
    if (prefix == null)
        return

    constraint(
        message = { it.message(prefix) },
        code = "notStartsWith",
        predicate = { !it.startsWith(prefix, ignoreCase) }
    )
}

public fun ValidationNode<String>.endsWith(
    suffix: String?,
    ignoreCase: Boolean = false,
    message: String = messages.endsWith
): Unit = endsWith(
    suffix = suffix,
    ignoreCase = ignoreCase,
    message = { message.filled("suffix" to suffix) }
)

public fun ValidationNode<String>.endsWith(
    suffix: String?,
    ignoreCase: Boolean = false,
    message: String.(String) -> String
) {
    if (suffix == null)
        return

    constraint(
        message = { it.message(suffix) },
        code = "endsWith",
        predicate = { it.endsWith(suffix, ignoreCase) }
    )
}

public fun ValidationNode<String>.notEndsWith(
    suffix: String?,
    ignoreCase: Boolean = false,
    message: String = messages.notEndsWith
): Unit = notEndsWith(
    suffix = suffix,
    ignoreCase = ignoreCase,
    message = { message.filled("suffix" to suffix) }
)

public fun ValidationNode<String>.notEndsWith(
    suffix: String?,
    ignoreCase: Boolean = false,
    message: String.(String) -> String
) {
    if (suffix == null)
        return

    constraint(
        message = { it.message(suffix) },
        code = "notEndsWith",
        predicate = { !it.endsWith(suffix, ignoreCase) }
    )
}

public fun ValidationNode<String>.uppercase(
    message: String = messages.uppercase
): Unit = constraint(
    message = message,
    code = "uppercase",
    predicate = { it.none(Char::isLowerCase) }
)

public fun ValidationNode<String>.lowercase(
    message: String = messages.lowercase
): Unit = constraint(
    message = message,
    code = "lowercase",
    predicate = { it.none(Char::isUpperCase) }
)

public fun ValidationNode<String>.letters(
    message: String = messages.letters
): Unit = constraint(
    message = message,
    code = "letters",
    predicate = { it.isNotEmpty() && it.all(Char::isLetter) }
)

public fun ValidationNode<String>.digits(
    message: String = messages.digits
): Unit = constraint(
    message = message,
    code = "digits",
    predicate = { it.isNotEmpty() && it.all { char -> char in ASCII_DIGITS } }
)

public fun ValidationNode<String>.lettersOrDigits(
    message: String = messages.lettersOrDigits
): Unit = constraint(
    message = message,
    code = "lettersOrDigits",
    predicate = { it.isNotEmpty() && it.all { char -> char.isLetter() || char in ASCII_DIGITS } }
)

public fun ValidationNode<String>.ascii(
    message: String = messages.ascii
): Unit = constraint(
    message = message,
    code = "ascii",
    predicate = { it.isNotEmpty() && it.all { char -> char.code < ASCII_LIMIT } }
)

public fun ValidationNode<String>.containsOnly(
    characters: String?,
    message: String = messages.containsOnly
): Unit = containsOnly(
    characters = characters,
    message = { message.filled("characters" to characters) }
)

public fun ValidationNode<String>.containsOnly(
    characters: String?,
    message: String.(String) -> String
) {
    if (characters == null)
        return

    constraint(
        message = { it.message(characters) },
        code = "containsOnly",
        predicate = { it.isNotEmpty() && it.all { char -> char in characters } }
    )
}

public fun ValidationNode<String>.normalized(
    form: Normalizer.Form = Normalizer.Form.NFC,
    message: String = messages.normalized
): Unit = normalized(
    form = form,
    message = { message.filled("form" to form) }
)

public fun ValidationNode<String>.normalized(
    form: Normalizer.Form = Normalizer.Form.NFC,
    message: String.(Normalizer.Form) -> String
): Unit = constraint(
    message = { it.message(form) },
    code = "normalized",
    predicate = { Normalizer.isNormalized(it, form) }
)

public fun ValidationNode<String>.numeric(
    message: String = messages.numeric
): Unit = constraint(
    message = message,
    code = "numeric",
    predicate = { NUMERIC_FORMAT.matches(it) }
)

public fun ValidationNode<String>.integer(
    message: String = messages.integer
): Unit = constraint(
    message = message,
    code = "integer",
    predicate = { INTEGER_FORMAT.matches(it) }
)

public fun ValidationNode<String>.boolean(
    message: String = messages.boolean
): Unit = constraint(
    message = message,
    code = "boolean",
    predicate = { it.toBooleanStrictOrNull() != null }
)

public fun ValidationNode<String>.containsUppercase(
    message: String = messages.containsUppercase
): Unit = constraint(
    message = message,
    code = "containsUppercase",
    predicate = { it.any(Char::isUpperCase) }
)

public fun ValidationNode<String>.containsLowercase(
    message: String = messages.containsLowercase
): Unit = constraint(
    message = message,
    code = "containsLowercase",
    predicate = { it.any(Char::isLowerCase) }
)

public fun ValidationNode<String>.containsDigit(
    message: String = messages.containsDigit
): Unit = constraint(
    message = message,
    code = "containsDigit",
    predicate = { it.any { char -> char in ASCII_DIGITS } }
)

public fun ValidationNode<String>.containsSymbol(
    message: String = messages.containsSymbol
): Unit = constraint(
    message = message,
    code = "containsSymbol",
    predicate = { it.any { char -> !char.isLetterOrDigit() && !char.isWhitespace() } }
)

public inline fun <reified E : Enum<E>> ValidationNode<String>.enum(
    ignoreCase: Boolean = false,
    message: String = messages.enum
): Unit = enum<E>(
    ignoreCase = ignoreCase,
    message = { values -> message.filled("values" to values.map { it.name }) }
)

public inline fun <reified E : Enum<E>> ValidationNode<String>.enum(
    ignoreCase: Boolean = false,
    noinline message: String.(List<E>) -> String
): Unit = constraint(
    message = { it.message(enumEntries<E>()) },
    code = "enum",
    predicate = { value -> enumEntries<E>().any { it.name.equals(value, ignoreCase) } }
)
