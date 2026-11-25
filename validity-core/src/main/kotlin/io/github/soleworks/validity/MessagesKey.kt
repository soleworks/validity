package io.github.soleworks.validity

/**
 * Identifies the messages of a module that is not part of `validity-core`, such as `validity-phone`.
 *
 * A module declares one key, and its constraints read their default messages from [current]. Users change them with
 * [Messages.module], or with the function the module adds to [Messages].
 *
 * ```kotlin
 * private val GREETING_MESSAGES = MessagesKey(
 *     create = ::GreetingMessages,
 *     translations = mapOf(Language.PT_BR to { greeting = "olá" })
 * )
 *
 * Validity.configure { messages { module(GREETING_MESSAGES).greeting = "oi" } }
 * ```
 *
 * @param T the class that holds the messages of the module.
 * @property create creates the messages with their defaults, in English, once per configuration.
 * @property translations sets the messages in each other [Language] the module is written in. A language it leaves
 * out keeps the English defaults.
 */
public class MessagesKey<T : Any>(
    internal val create: () -> T,
    internal val translations: Map<Language, T.() -> Unit> = emptyMap()
) {
    /** The messages of this module in the configuration that is validating. */
    public val current: T
        get() = messages.module(this)
}
