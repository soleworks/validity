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

/**
 * Checks that the string has at least [min] characters.
 *
 * Fails with code `minLength` and, by default, the message `must have at least {min} characters`
 * ([Messages.minLength][io.github.soleworks.validity.Messages.minLength]).
 *
 * @param min the fewest characters accepted.
 * @param message the message of the violation. `{min}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.minLength(
    min: Int,
    message: String = messages.minLength
): Unit = minLength(
    min = min,
    message = { message.filled("min" to min) }
)

/**
 * Checks that the string has at least [min] characters.
 *
 * Fails with code `minLength` and the message that [message] builds.
 *
 * @param min the fewest characters accepted.
 * @param message builds the message from the value (the receiver) and [min].
 */
public fun ValidationNode<String>.minLength(
    min: Int,
    message: suspend String.(Int) -> String
): Unit = constraint(
    message = { it.message(min) },
    code = "minLength",
    predicate = { it.length >= min }
)

/**
 * Checks that the string has at most [max] characters.
 *
 * Fails with code `maxLength` and, by default, the message `must have at most {max} characters`
 * ([Messages.maxLength][io.github.soleworks.validity.Messages.maxLength]).
 *
 * @param max the most characters accepted.
 * @param message the message of the violation. `{max}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.maxLength(
    max: Int,
    message: String = messages.maxLength
): Unit = maxLength(
    max = max,
    message = { message.filled("max" to max) }
)

/**
 * Checks that the string has at most [max] characters.
 *
 * Fails with code `maxLength` and the message that [message] builds.
 *
 * @param max the most characters accepted.
 * @param message builds the message from the value (the receiver) and [max].
 */
public fun ValidationNode<String>.maxLength(
    max: Int,
    message: suspend String.(Int) -> String
): Unit = constraint(
    message = { it.message(max) },
    code = "maxLength",
    predicate = { it.length <= max }
)

/**
 * Checks that the string has exactly [length] characters.
 *
 * Fails with code `length` and, by default, the message `must have exactly {length} characters`
 * ([Messages.length][io.github.soleworks.validity.Messages.length]).
 *
 * @param length the number of characters required.
 * @param message the message of the violation. `{length}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.length(
    length: Int,
    message: String = messages.length
): Unit = length(
    length = length,
    message = { message.filled("length" to length) }
)

/**
 * Checks that the string has exactly [length] characters.
 *
 * Fails with code `length` and the message that [message] builds.
 *
 * @param length the number of characters required.
 * @param message builds the message from the value (the receiver) and [length].
 */
public fun ValidationNode<String>.length(
    length: Int,
    message: suspend String.(Int) -> String
): Unit = constraint(
    message = { it.message(length) },
    code = "length",
    predicate = { it.length == length }
)

/**
 * Checks that the string has between [min] and [max] characters, both included.
 *
 * Fails with code `lengthBetween` and, by default, the message `must have between {min} and {max} characters`
 * ([Messages.lengthBetween][io.github.soleworks.validity.Messages.lengthBetween]).
 *
 * ```kotlin
 * ::username required { lengthBetween(3, 20) }
 * ```
 *
 * @param min the fewest characters accepted.
 * @param max the most characters accepted.
 * @param message the message of the violation. `{min}`, `{max}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.lengthBetween(
    min: Int,
    max: Int,
    message: String = messages.lengthBetween
): Unit = lengthBetween(
    min = min,
    max = max,
    message = { _, _ -> message.filled("min" to min, "max" to max) }
)

/**
 * Checks that the string has between [min] and [max] characters, both included.
 *
 * Fails with code `lengthBetween` and the message that [message] builds.
 *
 * @param min the fewest characters accepted.
 * @param max the most characters accepted.
 * @param message builds the message from the value (the receiver), [min] and [max].
 */
public fun ValidationNode<String>.lengthBetween(
    min: Int,
    max: Int,
    message: suspend String.(Int, Int) -> String
): Unit = constraint(
    message = { it.message(min, max) },
    code = "lengthBetween",
    predicate = { it.length in min..max }
)

/**
 * Checks that the string takes at least [min] bytes when encoded with [charset].
 *
 * Fails with code `minBytes` and, by default, the message `must have at least {min} bytes`
 * ([Messages.minBytes][io.github.soleworks.validity.Messages.minBytes]).
 *
 * @param min the fewest bytes accepted.
 * @param charset the encoding the bytes are counted in.
 * @param message the message of the violation. `{min}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.minBytes(
    min: Int,
    charset: Charset = Charsets.UTF_8,
    message: String = messages.minBytes
): Unit = minBytes(
    min = min,
    charset = charset,
    message = { message.filled("min" to min) }
)

/**
 * Checks that the string takes at least [min] bytes when encoded with [charset].
 *
 * Fails with code `minBytes` and the message that [message] builds.
 *
 * @param min the fewest bytes accepted.
 * @param charset the encoding the bytes are counted in.
 * @param message builds the message from the value (the receiver) and [min].
 */
public fun ValidationNode<String>.minBytes(
    min: Int,
    charset: Charset = Charsets.UTF_8,
    message: suspend String.(Int) -> String
): Unit = constraint(
    message = { it.message(min) },
    code = "minBytes",
    predicate = { it.toByteArray(charset).size >= min }
)

/**
 * Checks that the string takes at most [max] bytes when encoded with [charset].
 *
 * Use it for columns and protocols that limit bytes, not characters: `"é"` is one character and two UTF-8 bytes.
 *
 * Fails with code `maxBytes` and, by default, the message `must have at most {max} bytes`
 * ([Messages.maxBytes][io.github.soleworks.validity.Messages.maxBytes]).
 *
 * @param max the most bytes accepted.
 * @param charset the encoding the bytes are counted in.
 * @param message the message of the violation. `{max}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.maxBytes(
    max: Int,
    charset: Charset = Charsets.UTF_8,
    message: String = messages.maxBytes
): Unit = maxBytes(
    max = max,
    charset = charset,
    message = { message.filled("max" to max) }
)

/**
 * Checks that the string takes at most [max] bytes when encoded with [charset].
 *
 * Use it for columns and protocols that limit bytes, not characters: `"é"` is one character and two UTF-8 bytes.
 *
 * Fails with code `maxBytes` and the message that [message] builds.
 *
 * @param max the most bytes accepted.
 * @param charset the encoding the bytes are counted in.
 * @param message builds the message from the value (the receiver) and [max].
 */
public fun ValidationNode<String>.maxBytes(
    max: Int,
    charset: Charset = Charsets.UTF_8,
    message: suspend String.(Int) -> String
): Unit = constraint(
    message = { it.message(max) },
    code = "maxBytes",
    predicate = { it.toByteArray(charset).size <= max }
)

/**
 * Checks that the string has at least one character.
 *
 * A string of spaces passes. Use [notBlank] to refuse it.
 *
 * Fails with code `notEmpty` and, by default, the message `must not be empty`
 * ([Messages.notEmpty][io.github.soleworks.validity.Messages.notEmpty]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.notEmpty(
    message: String = messages.notEmpty
): Unit = constraint(
    message = message,
    code = "notEmpty",
    predicate = { it.isNotEmpty() }
)

/**
 * Checks that the string has no characters.
 *
 * Fails with code `empty` and, by default, the message `must be empty`
 * ([Messages.empty][io.github.soleworks.validity.Messages.empty]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.empty(
    message: String = messages.empty
): Unit = constraint(
    message = message,
    code = "empty",
    predicate = { it.isEmpty() }
)

/**
 * Checks that the string has at least one character that is not whitespace.
 *
 * Fails with code `notBlank` and, by default, the message `must not be blank`
 * ([Messages.notBlank][io.github.soleworks.validity.Messages.notBlank]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.notBlank(
    message: String = messages.notBlank
): Unit = constraint(
    message = message,
    code = "notBlank",
    predicate = { it.isNotBlank() }
)

/**
 * Checks that the string is empty or has only whitespace.
 *
 * Fails with code `blank` and, by default, the message `must be blank`
 * ([Messages.blank][io.github.soleworks.validity.Messages.blank]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.blank(
    message: String = messages.blank
): Unit = constraint(
    message = message,
    code = "blank",
    predicate = { it.isBlank() }
)

/**
 * Checks that the string does not start or end with whitespace.
 *
 * Fails with code `trimmed` and, by default, the message `must not start or end with whitespace`
 * ([Messages.trimmed][io.github.soleworks.validity.Messages.trimmed]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.trimmed(
    message: String = messages.trimmed
): Unit = constraint(
    message = message,
    code = "trimmed",
    predicate = { it == it.trim() }
)

/**
 * Checks that the string is equal to [other], ignoring case when [ignoreCase] is `true`.
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `equalTo` and, by default, the message `must be equal to {other}`
 * ([Messages.equalTo][io.github.soleworks.validity.Messages.equalTo]).
 *
 * ```kotlin
 * ::confirmEmail required { equalTo(email, ignoreCase = true) }
 * ```
 *
 * @param other the text it must be equal to.
 * @param ignoreCase whether upper and lower case letters are the same.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.equalTo(
    other: String?,
    ignoreCase: Boolean,
    message: String = messages.equalTo
): Unit = equalTo(
    other = other,
    ignoreCase = ignoreCase,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the string is equal to [other], ignoring case when [ignoreCase] is `true`.
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `equalTo` and the message that [message] builds.
 *
 * @param other the text it must be equal to.
 * @param ignoreCase whether upper and lower case letters are the same.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<String>.equalTo(
    other: String?,
    ignoreCase: Boolean,
    message: suspend String.(String) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "equalTo",
    predicate = { value, argument -> value.equals(argument, ignoreCase) }
)

/**
 * Checks that the string is one of [values], ignoring case when [ignoreCase] is `true`.
 *
 * When [values] is `null`, nothing is checked.
 *
 * Fails with code `oneOf` and, by default, the message `must be one of {values}`
 * ([Messages.oneOf][io.github.soleworks.validity.Messages.oneOf]).
 *
 * @param values the texts accepted.
 * @param ignoreCase whether upper and lower case letters are the same.
 * @param message the message of the violation. `{values}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.oneOf(
    values: Iterable<String>?,
    ignoreCase: Boolean,
    message: String = messages.oneOf
): Unit = oneOf(
    values = values,
    ignoreCase = ignoreCase,
    message = { message.filled("values" to values) }
)

/**
 * Checks that the string is one of [values], ignoring case when [ignoreCase] is `true`.
 *
 * When [values] is `null`, nothing is checked.
 *
 * Fails with code `oneOf` and the message that [message] builds.
 *
 * @param values the texts accepted.
 * @param ignoreCase whether upper and lower case letters are the same.
 * @param message builds the message from the value (the receiver) and [values].
 */
public fun ValidationNode<String>.oneOf(
    values: Iterable<String>?,
    ignoreCase: Boolean,
    message: suspend String.(Iterable<String>) -> String
): Unit = constraint(
    argument = values,
    message = message,
    code = "oneOf",
    predicate = { value, argument -> argument.any { it.equals(value, ignoreCase) } }
)

/**
 * Checks that the whole of the string matches [regex].
 *
 * Fails with code `matches` and, by default, the message `must match {regex}`
 * ([Messages.matches][io.github.soleworks.validity.Messages.matches]).
 *
 * ```kotlin
 * ::sku required { matches(Regex("[A-Z]{3}-\\d{4}")) }
 * ```
 *
 * @param regex the pattern the whole string must match.
 * @param message the message of the violation. `{regex}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.matches(
    regex: Regex,
    message: String = messages.matches
): Unit = matches(
    regex = regex,
    message = { message.filled("regex" to regex) }
)

/**
 * Checks that the whole of the string matches [regex].
 *
 * Fails with code `matches` and the message that [message] builds.
 *
 * @param regex the pattern the whole string must match.
 * @param message builds the message from the value (the receiver) and [regex].
 */
public fun ValidationNode<String>.matches(
    regex: Regex,
    message: suspend String.(Regex) -> String
): Unit = constraint(
    message = { it.message(regex) },
    code = "matches",
    predicate = { regex.matches(it) }
)

/**
 * Checks that the whole of the string does not match [regex].
 *
 * A string that only contains a match passes, since the whole string is what is matched.
 *
 * Fails with code `notMatches` and, by default, the message `must not match {regex}`
 * ([Messages.notMatches][io.github.soleworks.validity.Messages.notMatches]).
 *
 * @param regex the pattern the whole string must not match.
 * @param message the message of the violation. `{regex}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.notMatches(
    regex: Regex,
    message: String = messages.notMatches
): Unit = notMatches(
    regex = regex,
    message = { message.filled("regex" to regex) }
)

/**
 * Checks that the whole of the string does not match [regex].
 *
 * A string that only contains a match passes, since the whole string is what is matched.
 *
 * Fails with code `notMatches` and the message that [message] builds.
 *
 * @param regex the pattern the whole string must not match.
 * @param message builds the message from the value (the receiver) and [regex].
 */
public fun ValidationNode<String>.notMatches(
    regex: Regex,
    message: suspend String.(Regex) -> String
): Unit = constraint(
    message = { it.message(regex) },
    code = "notMatches",
    predicate = { !regex.matches(it) }
)

/**
 * Checks that the string contains [text].
 *
 * When [text] is `null`, nothing is checked.
 *
 * Fails with code `contains` and, by default, the message `must contain {text}`
 * ([Messages.contains][io.github.soleworks.validity.Messages.contains]).
 *
 * @param text the text it must contain.
 * @param ignoreCase whether upper and lower case letters are the same.
 * @param message the message of the violation. `{text}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.contains(
    text: String?,
    ignoreCase: Boolean = false,
    message: String = messages.contains
): Unit = contains(
    text = text,
    ignoreCase = ignoreCase,
    message = { message.filled("text" to text) }
)

/**
 * Checks that the string contains [text].
 *
 * When [text] is `null`, nothing is checked.
 *
 * Fails with code `contains` and the message that [message] builds.
 *
 * @param text the text it must contain.
 * @param ignoreCase whether upper and lower case letters are the same.
 * @param message builds the message from the value (the receiver) and [text].
 */
public fun ValidationNode<String>.contains(
    text: String?,
    ignoreCase: Boolean = false,
    message: suspend String.(String) -> String
): Unit = constraint(
    argument = text,
    message = message,
    code = "contains",
    predicate = { value, argument -> value.contains(argument, ignoreCase) }
)

/**
 * Checks that the string does not contain [text].
 *
 * When [text] is `null`, nothing is checked.
 *
 * Fails with code `notContains` and, by default, the message `must not contain {text}`
 * ([Messages.notContains][io.github.soleworks.validity.Messages.notContains]).
 *
 * @param text the text it must not contain.
 * @param ignoreCase whether upper and lower case letters are the same.
 * @param message the message of the violation. `{text}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.notContains(
    text: String?,
    ignoreCase: Boolean = false,
    message: String = messages.notContains
): Unit = notContains(
    text = text,
    ignoreCase = ignoreCase,
    message = { message.filled("text" to text) }
)

/**
 * Checks that the string does not contain [text].
 *
 * When [text] is `null`, nothing is checked.
 *
 * Fails with code `notContains` and the message that [message] builds.
 *
 * @param text the text it must not contain.
 * @param ignoreCase whether upper and lower case letters are the same.
 * @param message builds the message from the value (the receiver) and [text].
 */
public fun ValidationNode<String>.notContains(
    text: String?,
    ignoreCase: Boolean = false,
    message: suspend String.(String) -> String
): Unit = constraint(
    argument = text,
    message = message,
    code = "notContains",
    predicate = { value, argument -> !value.contains(argument, ignoreCase) }
)

/**
 * Checks that the string starts with [prefix].
 *
 * When [prefix] is `null`, nothing is checked.
 *
 * Fails with code `startsWith` and, by default, the message `must start with {prefix}`
 * ([Messages.startsWith][io.github.soleworks.validity.Messages.startsWith]).
 *
 * @param prefix the text it must start with.
 * @param ignoreCase whether upper and lower case letters are the same.
 * @param message the message of the violation. `{prefix}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.startsWith(
    prefix: String?,
    ignoreCase: Boolean = false,
    message: String = messages.startsWith
): Unit = startsWith(
    prefix = prefix,
    ignoreCase = ignoreCase,
    message = { message.filled("prefix" to prefix) }
)

/**
 * Checks that the string starts with [prefix].
 *
 * When [prefix] is `null`, nothing is checked.
 *
 * Fails with code `startsWith` and the message that [message] builds.
 *
 * @param prefix the text it must start with.
 * @param ignoreCase whether upper and lower case letters are the same.
 * @param message builds the message from the value (the receiver) and [prefix].
 */
public fun ValidationNode<String>.startsWith(
    prefix: String?,
    ignoreCase: Boolean = false,
    message: suspend String.(String) -> String
): Unit = constraint(
    argument = prefix,
    message = message,
    code = "startsWith",
    predicate = { value, argument -> value.startsWith(argument, ignoreCase) }
)

/**
 * Checks that the string does not start with [prefix].
 *
 * When [prefix] is `null`, nothing is checked.
 *
 * Fails with code `notStartsWith` and, by default, the message `must not start with {prefix}`
 * ([Messages.notStartsWith][io.github.soleworks.validity.Messages.notStartsWith]).
 *
 * @param prefix the text it must not start with.
 * @param ignoreCase whether upper and lower case letters are the same.
 * @param message the message of the violation. `{prefix}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.notStartsWith(
    prefix: String?,
    ignoreCase: Boolean = false,
    message: String = messages.notStartsWith
): Unit = notStartsWith(
    prefix = prefix,
    ignoreCase = ignoreCase,
    message = { message.filled("prefix" to prefix) }
)

/**
 * Checks that the string does not start with [prefix].
 *
 * When [prefix] is `null`, nothing is checked.
 *
 * Fails with code `notStartsWith` and the message that [message] builds.
 *
 * @param prefix the text it must not start with.
 * @param ignoreCase whether upper and lower case letters are the same.
 * @param message builds the message from the value (the receiver) and [prefix].
 */
public fun ValidationNode<String>.notStartsWith(
    prefix: String?,
    ignoreCase: Boolean = false,
    message: suspend String.(String) -> String
): Unit = constraint(
    argument = prefix,
    message = message,
    code = "notStartsWith",
    predicate = { value, argument -> !value.startsWith(argument, ignoreCase) }
)

/**
 * Checks that the string ends with [suffix].
 *
 * When [suffix] is `null`, nothing is checked.
 *
 * Fails with code `endsWith` and, by default, the message `must end with {suffix}`
 * ([Messages.endsWith][io.github.soleworks.validity.Messages.endsWith]).
 *
 * @param suffix the text it must end with.
 * @param ignoreCase whether upper and lower case letters are the same.
 * @param message the message of the violation. `{suffix}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.endsWith(
    suffix: String?,
    ignoreCase: Boolean = false,
    message: String = messages.endsWith
): Unit = endsWith(
    suffix = suffix,
    ignoreCase = ignoreCase,
    message = { message.filled("suffix" to suffix) }
)

/**
 * Checks that the string ends with [suffix].
 *
 * When [suffix] is `null`, nothing is checked.
 *
 * Fails with code `endsWith` and the message that [message] builds.
 *
 * @param suffix the text it must end with.
 * @param ignoreCase whether upper and lower case letters are the same.
 * @param message builds the message from the value (the receiver) and [suffix].
 */
public fun ValidationNode<String>.endsWith(
    suffix: String?,
    ignoreCase: Boolean = false,
    message: suspend String.(String) -> String
): Unit = constraint(
    argument = suffix,
    message = message,
    code = "endsWith",
    predicate = { value, argument -> value.endsWith(argument, ignoreCase) }
)

/**
 * Checks that the string does not end with [suffix].
 *
 * When [suffix] is `null`, nothing is checked.
 *
 * Fails with code `notEndsWith` and, by default, the message `must not end with {suffix}`
 * ([Messages.notEndsWith][io.github.soleworks.validity.Messages.notEndsWith]).
 *
 * @param suffix the text it must not end with.
 * @param ignoreCase whether upper and lower case letters are the same.
 * @param message the message of the violation. `{suffix}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.notEndsWith(
    suffix: String?,
    ignoreCase: Boolean = false,
    message: String = messages.notEndsWith
): Unit = notEndsWith(
    suffix = suffix,
    ignoreCase = ignoreCase,
    message = { message.filled("suffix" to suffix) }
)

/**
 * Checks that the string does not end with [suffix].
 *
 * When [suffix] is `null`, nothing is checked.
 *
 * Fails with code `notEndsWith` and the message that [message] builds.
 *
 * @param suffix the text it must not end with.
 * @param ignoreCase whether upper and lower case letters are the same.
 * @param message builds the message from the value (the receiver) and [suffix].
 */
public fun ValidationNode<String>.notEndsWith(
    suffix: String?,
    ignoreCase: Boolean = false,
    message: suspend String.(String) -> String
): Unit = constraint(
    argument = suffix,
    message = message,
    code = "notEndsWith",
    predicate = { value, argument -> !value.endsWith(argument, ignoreCase) }
)

/**
 * Checks that the string has no lowercase letters.
 *
 * Digits, symbols and the empty string pass.
 *
 * Fails with code `uppercase` and, by default, the message `must be uppercase`
 * ([Messages.uppercase][io.github.soleworks.validity.Messages.uppercase]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.uppercase(
    message: String = messages.uppercase
): Unit = constraint(
    message = message,
    code = "uppercase",
    predicate = { it.none(Char::isLowerCase) }
)

/**
 * Checks that the string has no uppercase letters.
 *
 * Digits, symbols and the empty string pass.
 *
 * Fails with code `lowercase` and, by default, the message `must be lowercase`
 * ([Messages.lowercase][io.github.soleworks.validity.Messages.lowercase]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.lowercase(
    message: String = messages.lowercase
): Unit = constraint(
    message = message,
    code = "lowercase",
    predicate = { it.none(Char::isUpperCase) }
)

/**
 * Checks that the string is not empty and has only letters, in any script.
 *
 * Fails with code `letters` and, by default, the message `must contain only letters`
 * ([Messages.letters][io.github.soleworks.validity.Messages.letters]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.letters(
    message: String = messages.letters
): Unit = constraint(
    message = message,
    code = "letters",
    predicate = { it.isNotEmpty() && it.all(Char::isLetter) }
)

/**
 * Checks that the string is not empty and has only the digits `0` to `9`.
 *
 * Fails with code `digits` and, by default, the message `must contain only digits`
 * ([Messages.digits][io.github.soleworks.validity.Messages.digits]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.digits(
    message: String = messages.digits
): Unit = constraint(
    message = message,
    code = "digits",
    predicate = { it.isNotEmpty() && it.all { char -> char in ASCII_DIGITS } }
)

/**
 * Checks that the string is not empty and has only letters, in any script, and the digits `0` to `9`.
 *
 * Fails with code `lettersOrDigits` and, by default, the message `must contain only letters or digits`
 * ([Messages.lettersOrDigits][io.github.soleworks.validity.Messages.lettersOrDigits]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.lettersOrDigits(
    message: String = messages.lettersOrDigits
): Unit = constraint(
    message = message,
    code = "lettersOrDigits",
    predicate = { it.isNotEmpty() && it.all { char -> char.isLetter() || char in ASCII_DIGITS } }
)

/**
 * Checks that the string is not empty and has only ASCII characters.
 *
 * Fails with code `ascii` and, by default, the message `must contain only ASCII characters`
 * ([Messages.ascii][io.github.soleworks.validity.Messages.ascii]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.ascii(
    message: String = messages.ascii
): Unit = constraint(
    message = message,
    code = "ascii",
    predicate = { it.isNotEmpty() && it.all { char -> char.code < ASCII_LIMIT } }
)

/**
 * Checks that the string is not empty and has only characters from [characters].
 *
 * When [characters] is `null`, nothing is checked.
 *
 * Fails with code `containsOnly` and, by default, the message `must contain only the characters {characters}`
 * ([Messages.containsOnly][io.github.soleworks.validity.Messages.containsOnly]).
 *
 * ```kotlin
 * ::pin required { containsOnly("0123456789") }
 * ```
 *
 * @param characters the characters accepted, in any order.
 * @param message the message of the violation. `{characters}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.containsOnly(
    characters: String?,
    message: String = messages.containsOnly
): Unit = containsOnly(
    characters = characters,
    message = { message.filled("characters" to characters) }
)

/**
 * Checks that the string is not empty and has only characters from [characters].
 *
 * When [characters] is `null`, nothing is checked.
 *
 * Fails with code `containsOnly` and the message that [message] builds.
 *
 * @param characters the characters accepted, in any order.
 * @param message builds the message from the value (the receiver) and [characters].
 */
public fun ValidationNode<String>.containsOnly(
    characters: String?,
    message: suspend String.(String) -> String
): Unit = constraint(
    argument = characters,
    message = message,
    code = "containsOnly",
    predicate = { value, argument -> value.isNotEmpty() && value.all { char -> char in argument } }
)

/**
 * Checks that the string is already in the Unicode normalization [form].
 *
 * Fails with code `normalized` and, by default, the message `must be in Unicode {form} form`
 * ([Messages.normalized][io.github.soleworks.validity.Messages.normalized]).
 *
 * @param form the normalization form, NFC by default.
 * @param message the message of the violation. `{form}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.normalized(
    form: Normalizer.Form = Normalizer.Form.NFC,
    message: String = messages.normalized
): Unit = normalized(
    form = form,
    message = { message.filled("form" to form) }
)

/**
 * Checks that the string is already in the Unicode normalization [form].
 *
 * Fails with code `normalized` and the message that [message] builds.
 *
 * @param form the normalization form, NFC by default.
 * @param message builds the message from the value (the receiver) and [form].
 */
public fun ValidationNode<String>.normalized(
    form: Normalizer.Form = Normalizer.Form.NFC,
    message: suspend String.(Normalizer.Form) -> String
): Unit = constraint(
    message = { it.message(form) },
    code = "normalized",
    predicate = { Normalizer.isNormalized(it, form) }
)

/**
 * Checks that the string is a decimal number, such as `42`, `-7` or `+3.14`.
 *
 * There is no exponent and no thousands separator.
 *
 * Fails with code `numeric` and, by default, the message `must be numeric`
 * ([Messages.numeric][io.github.soleworks.validity.Messages.numeric]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.numeric(
    message: String = messages.numeric
): Unit = constraint(
    message = message,
    code = "numeric",
    predicate = { NUMERIC_FORMAT.matches(it) }
)

/**
 * Checks that the string is a whole number, such as `42`, `-7` or `+3`.
 *
 * Fails with code `integer` and, by default, the message `must be an integer`
 * ([Messages.integer][io.github.soleworks.validity.Messages.integer]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.integer(
    message: String = messages.integer
): Unit = constraint(
    message = message,
    code = "integer",
    predicate = { INTEGER_FORMAT.matches(it) }
)

/**
 * Checks that the string is `true` or `false`, in lowercase.
 *
 * Fails with code `boolean` and, by default, the message `must be true or false`
 * ([Messages.boolean][io.github.soleworks.validity.Messages.boolean]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.boolean(
    message: String = messages.boolean
): Unit = constraint(
    message = message,
    code = "boolean",
    predicate = { it.toBooleanStrictOrNull() != null }
)

/**
 * Checks that the string has at least one uppercase letter.
 *
 * Fails with code `containsUppercase` and, by default, the message `must contain an uppercase letter`
 * ([Messages.containsUppercase][io.github.soleworks.validity.Messages.containsUppercase]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.containsUppercase(
    message: String = messages.containsUppercase
): Unit = constraint(
    message = message,
    code = "containsUppercase",
    predicate = { it.any(Char::isUpperCase) }
)

/**
 * Checks that the string has at least one lowercase letter.
 *
 * Fails with code `containsLowercase` and, by default, the message `must contain a lowercase letter`
 * ([Messages.containsLowercase][io.github.soleworks.validity.Messages.containsLowercase]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.containsLowercase(
    message: String = messages.containsLowercase
): Unit = constraint(
    message = message,
    code = "containsLowercase",
    predicate = { it.any(Char::isLowerCase) }
)

/**
 * Checks that the string has at least one of the digits `0` to `9`.
 *
 * Fails with code `containsDigit` and, by default, the message `must contain a digit`
 * ([Messages.containsDigit][io.github.soleworks.validity.Messages.containsDigit]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.containsDigit(
    message: String = messages.containsDigit
): Unit = constraint(
    message = message,
    code = "containsDigit",
    predicate = { it.any { char -> char in ASCII_DIGITS } }
)

/**
 * Checks that the string has at least one character that is not a letter, a digit or whitespace.
 *
 * Fails with code `containsSymbol` and, by default, the message `must contain a symbol`
 * ([Messages.containsSymbol][io.github.soleworks.validity.Messages.containsSymbol]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.containsSymbol(
    message: String = messages.containsSymbol
): Unit = constraint(
    message = message,
    code = "containsSymbol",
    predicate = { it.any { char -> !char.isLetterOrDigit() && !char.isWhitespace() } }
)

/**
 * Checks that the string is the name of an entry of the enum [E].
 *
 * Fails with code `enum` and, by default, the message `must be one of {values}`
 * ([Messages.enum][io.github.soleworks.validity.Messages.enum]).
 *
 * ```kotlin
 * ::method required { enum<PaymentMethod>() }
 * ```
 *
 * @param E the enum whose entry names are accepted.
 * @param ignoreCase whether upper and lower case letters are the same.
 * @param message the message of the violation. `{values}` (the entry names), `{path}` and `{value}` are filled in.
 */
public inline fun <reified E : Enum<E>> ValidationNode<String>.enum(
    ignoreCase: Boolean = false,
    message: String = messages.enum
): Unit = enum<E>(
    ignoreCase = ignoreCase,
    message = { values -> message.filled("values" to values.map { it.name }) }
)

/**
 * Checks that the string is the name of an entry of the enum [E].
 *
 * Fails with code `enum` and the message that [message] builds.
 *
 * @param E the enum whose entry names are accepted.
 * @param ignoreCase whether upper and lower case letters are the same.
 * @param message builds the message from the value (the receiver) and the entries of [E].
 */
public inline fun <reified E : Enum<E>> ValidationNode<String>.enum(
    ignoreCase: Boolean = false,
    noinline message: suspend String.(List<E>) -> String
): Unit = constraint(
    message = { it.message(enumEntries<E>()) },
    code = "enum",
    predicate = { value -> enumEntries<E>().any { it.name.equals(value, ignoreCase) } }
)
