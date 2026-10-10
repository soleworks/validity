package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.messages

private val NUMERIC_FORMAT = Regex("[+-]?\\d+(?:\\.\\d+)?")
private val INTEGER_FORMAT = Regex("[+-]?\\d+")

public fun ValidationNode<String>.minLength(
    min: Int,
    message: String = messages.minLength
): Unit = minLength(
    min = min,
    message = { message.replace("{min}", "$min") }
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
    message = { message.replace("{max}", "$max") }
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
    message = { message.replace("{length}", "$length") }
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
    message = { _, _ -> message.replace("{min}", "$min").replace("{max}", "$max") }
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

public fun ValidationNode<String>.notEmpty(
    message: String = messages.notEmpty
): Unit = constraint(
    message = message,
    code = "notEmpty",
    predicate = { it.isNotEmpty() }
)

public fun ValidationNode<String>.notBlank(
    message: String = messages.notBlank
): Unit = constraint(
    message = message,
    code = "notBlank",
    predicate = { it.isNotBlank() }
)

public fun ValidationNode<String>.matches(
    regex: Regex,
    message: String = messages.matches
): Unit = matches(
    regex = regex,
    message = { message.replace("{regex}", "$regex") }
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
    message = { message.replace("{regex}", "$regex") }
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
    message: String = messages.contains
): Unit = contains(
    text = text,
    message = { message.replace("{text}", "$text") }
)

public fun ValidationNode<String>.contains(
    text: String?,
    message: String.(String?) -> String
): Unit = constraint(
    message = { it.message(text) },
    code = "contains",
    skipped = text == null,
    predicate = { text == null || it.contains(text) }
)

public fun ValidationNode<String>.notContains(
    text: String?,
    message: String = messages.notContains
): Unit = notContains(
    text = text,
    message = { message.replace("{text}", "$text") }
)

public fun ValidationNode<String>.notContains(
    text: String?,
    message: String.(String?) -> String
): Unit = constraint(
    message = { it.message(text) },
    code = "notContains",
    skipped = text == null,
    predicate = { text == null || !it.contains(text) }
)

public fun ValidationNode<String>.startsWith(
    prefix: String?,
    message: String = messages.startsWith
): Unit = startsWith(
    prefix = prefix,
    message = { message.replace("{prefix}", "$prefix") }
)

public fun ValidationNode<String>.startsWith(
    prefix: String?,
    message: String.(String?) -> String
): Unit = constraint(
    message = { it.message(prefix) },
    code = "startsWith",
    skipped = prefix == null,
    predicate = { prefix == null || it.startsWith(prefix) }
)

public fun ValidationNode<String>.notStartsWith(
    prefix: String?,
    message: String = messages.notStartsWith
): Unit = notStartsWith(
    prefix = prefix,
    message = { message.replace("{prefix}", "$prefix") }
)

public fun ValidationNode<String>.notStartsWith(
    prefix: String?,
    message: String.(String?) -> String
): Unit = constraint(
    message = { it.message(prefix) },
    code = "notStartsWith",
    skipped = prefix == null,
    predicate = { prefix == null || !it.startsWith(prefix) }
)

public fun ValidationNode<String>.endsWith(
    suffix: String?,
    message: String = messages.endsWith
): Unit = endsWith(
    suffix = suffix,
    message = { message.replace("{suffix}", "$suffix") }
)

public fun ValidationNode<String>.endsWith(
    suffix: String?,
    message: String.(String?) -> String
): Unit = constraint(
    message = { it.message(suffix) },
    code = "endsWith",
    skipped = suffix == null,
    predicate = { suffix == null || it.endsWith(suffix) }
)

public fun ValidationNode<String>.notEndsWith(
    suffix: String?,
    message: String = messages.notEndsWith
): Unit = notEndsWith(
    suffix = suffix,
    message = { message.replace("{suffix}", "$suffix") }
)

public fun ValidationNode<String>.notEndsWith(
    suffix: String?,
    message: String.(String?) -> String
): Unit = constraint(
    message = { it.message(suffix) },
    code = "notEndsWith",
    skipped = suffix == null,
    predicate = { suffix == null || !it.endsWith(suffix) }
)

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
    predicate = { it.all(Char::isLetter) }
)

public fun ValidationNode<String>.digits(
    message: String = messages.digits
): Unit = constraint(
    message = message,
    code = "digits",
    predicate = { it.all(Char::isDigit) }
)

public fun ValidationNode<String>.lettersOrDigits(
    message: String = messages.lettersOrDigits
): Unit = constraint(
    message = message,
    code = "lettersOrDigits",
    predicate = { it.all(Char::isLetterOrDigit) }
)

public fun ValidationNode<String>.ascii(
    message: String = messages.ascii
): Unit = constraint(
    message = message,
    code = "ascii",
    predicate = { Charsets.US_ASCII.newEncoder().canEncode(it) }
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
    predicate = { it.any(Char::isDigit) }
)

public fun ValidationNode<String>.containsSymbol(
    message: String = messages.containsSymbol
): Unit = constraint(
    message = message,
    code = "containsSymbol",
    predicate = { it.any { char -> !char.isLetterOrDigit() && !char.isWhitespace() } }
)

public inline fun <reified E : Enum<E>> ValidationNode<String>.enum(
    message: String = messages.enum
): Unit = enum<E>(
    message = { values -> message.replace("{values}", values.joinToString()) }
)

public inline fun <reified E : Enum<E>> ValidationNode<String>.enum(
    noinline message: String.(List<E>) -> String
): Unit = constraint(
    message = { it.message(enumValues<E>().toList()) },
    code = "enum",
    predicate = { value -> enumValues<E>().any { it.name == value } }
)
