package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.Messages
import io.github.soleworks.validity.ValidationNode

public fun ValidationNode<String>.minLength(
    min: Int,
    message: String = Messages.MIN_LENGTH
): Unit = minLength(
    min = min,
    message = { message.replace("{min}", "$min") }
)

public fun ValidationNode<String>.minLength(
    min: Int,
    message: String.(Int) -> String
): Unit = constraint(
    message = { it.message(min) },
    predicate = { it.length >= min }
)

public fun ValidationNode<String>.maxLength(
    max: Int,
    message: String = Messages.MAX_LENGTH
): Unit = maxLength(
    max = max,
    message = { message.replace("{max}", "$max") }
)

public fun ValidationNode<String>.maxLength(
    max: Int,
    message: String.(Int) -> String
): Unit = constraint(
    message = { it.message(max) },
    predicate = { it.length <= max }
)

public fun ValidationNode<String>.length(
    length: Int,
    message: String = Messages.LENGTH
): Unit = length(
    length = length,
    message = { message.replace("{length}", "$length") }
)

public fun ValidationNode<String>.length(
    length: Int,
    message: String.(Int) -> String
): Unit = constraint(
    message = { it.message(length) },
    predicate = { it.length == length }
)

public fun ValidationNode<String>.lengthBetween(
    min: Int,
    max: Int,
    message: String = Messages.LENGTH_BETWEEN
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
    predicate = { it.length in min..max }
)

public fun ValidationNode<String>.notEmpty(
    message: String = Messages.NOT_EMPTY
): Unit = constraint(
    message = message,
    predicate = { it.isNotEmpty() }
)

public fun ValidationNode<String>.notBlank(
    message: String = Messages.NOT_BLANK
): Unit = constraint(
    message = message,
    predicate = { it.isNotBlank() }
)

public fun ValidationNode<String>.matches(
    regex: Regex,
    message: String = Messages.MATCHES
): Unit = matches(
    regex = regex,
    message = { message.replace("{regex}", "$regex") }
)

public fun ValidationNode<String>.matches(
    regex: Regex,
    message: String.(Regex) -> String
): Unit = constraint(
    message = { it.message(regex) },
    predicate = { regex.matches(it) }
)

public fun ValidationNode<String>.notMatches(
    regex: Regex,
    message: String = Messages.NOT_MATCHES
): Unit = notMatches(
    regex = regex,
    message = { message.replace("{regex}", "$regex") }
)

public fun ValidationNode<String>.notMatches(
    regex: Regex,
    message: String.(Regex) -> String
): Unit = constraint(
    message = { it.message(regex) },
    predicate = { !regex.matches(it) }
)

public fun ValidationNode<String>.contains(
    text: String?,
    message: String = Messages.CONTAINS
): Unit = contains(
    text = text,
    message = { message.replace("{text}", "$text") }
)

public fun ValidationNode<String>.contains(
    text: String?,
    message: String.(String?) -> String
): Unit = constraint(
    message = { it.message(text) },
    predicate = { text == null || it.contains(text) }
)

public fun ValidationNode<String>.notContains(
    text: String?,
    message: String = Messages.NOT_CONTAINS
): Unit = notContains(
    text = text,
    message = { message.replace("{text}", "$text") }
)

public fun ValidationNode<String>.notContains(
    text: String?,
    message: String.(String?) -> String
): Unit = constraint(
    message = { it.message(text) },
    predicate = { text == null || !it.contains(text) }
)

public fun ValidationNode<String>.startsWith(
    prefix: String?,
    message: String = Messages.STARTS_WITH
): Unit = startsWith(
    prefix = prefix,
    message = { message.replace("{prefix}", "$prefix") }
)

public fun ValidationNode<String>.startsWith(
    prefix: String?,
    message: String.(String?) -> String
): Unit = constraint(
    message = { it.message(prefix) },
    predicate = { prefix == null || it.startsWith(prefix) }
)

public fun ValidationNode<String>.notStartsWith(
    prefix: String?,
    message: String = Messages.NOT_STARTS_WITH
): Unit = notStartsWith(
    prefix = prefix,
    message = { message.replace("{prefix}", "$prefix") }
)

public fun ValidationNode<String>.notStartsWith(
    prefix: String?,
    message: String.(String?) -> String
): Unit = constraint(
    message = { it.message(prefix) },
    predicate = { prefix == null || !it.startsWith(prefix) }
)

public fun ValidationNode<String>.endsWith(
    suffix: String?,
    message: String = Messages.ENDS_WITH
): Unit = endsWith(
    suffix = suffix,
    message = { message.replace("{suffix}", "$suffix") }
)

public fun ValidationNode<String>.endsWith(
    suffix: String?,
    message: String.(String?) -> String
): Unit = constraint(
    message = { it.message(suffix) },
    predicate = { suffix == null || it.endsWith(suffix) }
)

public fun ValidationNode<String>.notEndsWith(
    suffix: String?,
    message: String = Messages.NOT_ENDS_WITH
): Unit = notEndsWith(
    suffix = suffix,
    message = { message.replace("{suffix}", "$suffix") }
)

public fun ValidationNode<String>.notEndsWith(
    suffix: String?,
    message: String.(String?) -> String
): Unit = constraint(
    message = { it.message(suffix) },
    predicate = { suffix == null || !it.endsWith(suffix) }
)

public fun ValidationNode<String>.uppercase(
    message: String = Messages.UPPERCASE
): Unit = constraint(
    message = message,
    predicate = { it.none(Char::isLowerCase) }
)

public fun ValidationNode<String>.lowercase(
    message: String = Messages.LOWERCASE
): Unit = constraint(
    message = message,
    predicate = { it.none(Char::isUpperCase) }
)

public fun ValidationNode<String>.letters(
    message: String = Messages.LETTERS
): Unit = constraint(
    message = message,
    predicate = { it.all(Char::isLetter) }
)

public fun ValidationNode<String>.digits(
    message: String = Messages.DIGITS
): Unit = constraint(
    message = message,
    predicate = { it.all(Char::isDigit) }
)

public fun ValidationNode<String>.lettersOrDigits(
    message: String = Messages.LETTERS_OR_DIGITS
): Unit = constraint(
    message = message,
    predicate = { it.all(Char::isLetterOrDigit) }
)

public fun ValidationNode<String>.ascii(
    message: String = Messages.ASCII
): Unit = constraint(
    message = message,
    predicate = { Charsets.US_ASCII.newEncoder().canEncode(it) }
)
