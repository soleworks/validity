package io.github.soleworks.validity.constraints.austria

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.constraints.hasLuhnCheckDigit
import io.github.soleworks.validity.messages

private val ABGABENKONTONUMMER_FORMAT = Regex("\\d{9}")
private val SYMBOLS = Regex("[-\\\\/!@#\$%^&*()+=\\[\\]]+")

public fun ValidationNode<String>.abgabenkontonummer(
    message: String = messages.austria.abgabenkontonummer
): Unit = constraint(
    message = message,
    code = "abgabenkontonummer",
    predicate = {
        it.replace(SYMBOLS, "").let { number -> ABGABENKONTONUMMER_FORMAT.matches(number) && number.hasLuhnCheckDigit() }
    }
)

