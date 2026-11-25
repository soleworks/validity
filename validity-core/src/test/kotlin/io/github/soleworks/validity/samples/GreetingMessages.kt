package io.github.soleworks.validity.samples

import io.github.soleworks.validity.Language
import io.github.soleworks.validity.MessagesKey

val GREETING_MESSAGES = MessagesKey(
    create = ::GreetingMessages,
    translations = mapOf(Language.PT_BR to { greeting = "olá" })
)

class GreetingMessages {
    var greeting: String = "hello"
}
