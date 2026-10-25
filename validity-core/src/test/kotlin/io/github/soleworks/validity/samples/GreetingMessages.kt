package io.github.soleworks.validity.samples

import io.github.soleworks.validity.MessagesKey

val GREETING_MESSAGES = MessagesKey(::GreetingMessages)

class GreetingMessages {
    var greeting: String = "hello"
}
