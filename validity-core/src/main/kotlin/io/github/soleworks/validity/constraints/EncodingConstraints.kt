package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.messages

private const val HEX_DIGITS = "0123456789abcdefABCDEF"
private const val UNICODE_ESCAPE_LENGTH = 4
private const val FIRST_PRINTABLE = ' '

private val BASE64_FORMAT = Regex("(?:[A-Za-z0-9+/]{4})*(?:[A-Za-z0-9+/]{2}==|[A-Za-z0-9+/]{3}=)?")
private val BASE64_URL_FORMAT = Regex("(?:[A-Za-z0-9_-]{4})*(?:[A-Za-z0-9_-]{2,3})?")
private val BASE32_FORMAT = Regex("(?:[A-Z2-7]{8})*(?:[A-Z2-7]{2}={6}|[A-Z2-7]{4}={4}|[A-Z2-7]{5}={3}|[A-Z2-7]{7}=)?")
private val BASE58_FORMAT = Regex("[1-9A-HJ-NP-Za-km-z]+")
internal val HEXADECIMAL_FORMAT = Regex("[0-9a-fA-F]+")
private val JSON_NUMBER_FORMAT = Regex("""-?(?:0|[1-9]\d*)(?:\.\d+)?(?:[eE][+-]?\d+)?""")
private val JSON_WHITESPACE = setOf(' ', '\t', '\n', '\r')
private val JSON_ESCAPES = setOf('"', '\\', '/', 'b', 'f', 'n', 'r', 't')

/**
 * Checks that the string is Base64 with the standard alphabet and its `=` padding.
 *
 * The empty string passes.
 *
 * Fails with code `base64` and, by default, the message `must be valid Base64`
 * ([Messages.base64][io.github.soleworks.validity.Messages.base64]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.base64(
    message: String = messages.base64
): Unit = constraint(
    message = message,
    code = "base64",
    predicate = { BASE64_FORMAT.matches(it) }
)

/**
 * Checks that the string is Base64 with the URL-safe alphabet (`-` and `_`) and no padding.
 *
 * The empty string passes.
 *
 * Fails with code `base64Url` and, by default, the message `must be valid URL-safe Base64`
 * ([Messages.base64Url][io.github.soleworks.validity.Messages.base64Url]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.base64Url(
    message: String = messages.base64Url
): Unit = constraint(
    message = message,
    code = "base64Url",
    predicate = { BASE64_URL_FORMAT.matches(it) }
)

/**
 * Checks that the string is Base32 (RFC 4648) in uppercase, with its `=` padding.
 *
 * The empty string passes.
 *
 * Fails with code `base32` and, by default, the message `must be valid Base32`
 * ([Messages.base32][io.github.soleworks.validity.Messages.base32]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.base32(
    message: String = messages.base32
): Unit = constraint(
    message = message,
    code = "base32",
    predicate = { BASE32_FORMAT.matches(it) }
)

/**
 * Checks that the string is not empty and is Base58, the Bitcoin alphabet without `0`, `O`, `I` and `l`.
 *
 * Fails with code `base58` and, by default, the message `must be valid Base58`
 * ([Messages.base58][io.github.soleworks.validity.Messages.base58]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.base58(
    message: String = messages.base58
): Unit = constraint(
    message = message,
    code = "base58",
    predicate = { BASE58_FORMAT.matches(it) }
)

/**
 * Checks that the string is not empty and has only hexadecimal digits, in either case.
 *
 * Fails with code `hexadecimal` and, by default, the message `must be hexadecimal`
 * ([Messages.hexadecimal][io.github.soleworks.validity.Messages.hexadecimal]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.hexadecimal(
    message: String = messages.hexadecimal
): Unit = constraint(
    message = message,
    code = "hexadecimal",
    predicate = { HEXADECIMAL_FORMAT.matches(it) }
)

/**
 * Checks that the string is one valid JSON value: an object, an array, a string, a number, `true`, `false` or `null`.
 *
 * Fails with code `json` and, by default, the message `must be valid JSON`
 * ([Messages.json][io.github.soleworks.validity.Messages.json]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.json(
    message: String = messages.json
): Unit = constraint(
    message = message,
    code = "json",
    predicate = { JsonReader(it).isValid() }
)

private class JsonReader(
    private val text: String
) {
    private var position = 0

    fun isValid(): Boolean = runCatching {
        readValue()
        skipWhitespace()
        position == text.length
    }.getOrDefault(false)

    private fun readValue() {
        skipWhitespace()

        when (peek()) {
            '{' -> readObject()
            '[' -> readArray()
            '"' -> readString()
            't' -> readLiteral("true")
            'f' -> readLiteral("false")
            'n' -> readLiteral("null")
            else -> readNumber()
        }
    }

    private fun readObject() {
        expect('{')
        skipWhitespace()

        if (consume('}'))
            return

        do {
            skipWhitespace()
            readString()
            skipWhitespace()
            expect(':')
            readValue()
            skipWhitespace()
        } while (consume(','))

        expect('}')
    }

    private fun readArray() {
        expect('[')
        skipWhitespace()

        if (consume(']'))
            return

        do {
            readValue()
            skipWhitespace()
        } while (consume(','))

        expect(']')
    }

    private fun readString() {
        expect('"')

        while (true) {
            val char = next()

            when {
                char == '"' -> return
                char == '\\' -> readEscape()
                char < FIRST_PRINTABLE -> error("Control character in string")
            }
        }
    }

    private fun readEscape() {
        val char = next()

        if (char == 'u')
            repeat(UNICODE_ESCAPE_LENGTH) { require(next() in HEX_DIGITS) }
        else
            require(char in JSON_ESCAPES)
    }

    private fun readNumber() {
        val match = JSON_NUMBER_FORMAT.matchAt(text, position)

        requireNotNull(match)

        position = match.range.last + 1
    }

    private fun readLiteral(
        literal: String
    ) {
        require(text.startsWith(literal, position))

        position += literal.length
    }

    private fun skipWhitespace() {
        while (position < text.length && text[position] in JSON_WHITESPACE)
            position++
    }

    private fun peek(): Char = text.getOrElse(position) { error("Unexpected end of JSON") }

    private fun next(): Char = peek().also { position++ }

    private fun expect(
        char: Char
    ) = require(next() == char)

    private fun consume(
        char: Char
    ): Boolean {
        val isFound = position < text.length && text[position] == char

        if (isFound)
            position++

        return isFound
    }
}
