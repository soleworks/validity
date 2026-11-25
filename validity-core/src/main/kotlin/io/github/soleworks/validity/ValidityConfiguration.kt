package io.github.soleworks.validity

import java.time.Clock

/**
 * What a [Validity] is made of: the language and the messages of the violations, and the clock.
 *
 * It is the receiver of [Validity.configure] and of [Validity.invoke].
 */
@ValidityDsl
public class ValidityConfiguration internal constructor() {
    private val messageBlocks = mutableListOf<Messages.() -> Unit>()

    /**
     * The language of the built-in messages, [Language.EN] by default.
     *
     * The messages changed with [messages] keep their text in every language.
     *
     * ```kotlin
     * Validity.configure { language = Language.PT_BR }
     * ```
     */
    public var language: Language = Language.EN

    /**
     * The clock that says what "now" and "today" are, for [past][io.github.soleworks.validity.constraints.past],
     * [minAge][io.github.soleworks.validity.constraints.minAge], [now] and every constraint about the present. The
     * system
     * clock, in the default time zone, by default.
     */
    public var clock: Clock = Clock.systemDefaultZone()

    internal val messages: Messages by lazy {
        val blocks = messageBlocks

        Messages(language).apply { blocks.forEach { block -> block() } }
    }

    /**
     * Changes the messages of the violations.
     *
     * ```kotlin
     * Validity.configure {
     *     messages {
     *         required = "é obrigatório"
     *         minLength = "deve ter pelo menos {min} caracteres"
     *     }
     * }
     * ```
     *
     * @param block sets the messages it changes. The others keep their default, in the [language].
     */
    public fun messages(
        block: Messages.() -> Unit
    ) {
        messageBlocks += block
    }
}
