package io.github.soleworks.validity

import java.time.Clock

/**
 * What a [Validity] is made of: the messages of the violations and the clock.
 *
 * It is the receiver of [Validity.configure] and of [Validity.invoke].
 */
@ValidityDsl
public class ValidityConfiguration internal constructor() {
    internal val messages: Messages = Messages()

    /**
     * The clock that says what "now" and "today" are, for [past][io.github.soleworks.validity.constraints.past],
     * [minAge][io.github.soleworks.validity.constraints.minAge], [now] and every constraint about the present. The
     * system
     * clock, in the default time zone, by default.
     */
    public var clock: Clock = Clock.systemDefaultZone()

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
     * @param block sets the messages it changes. The others keep their default.
     */
    public fun messages(
        block: Messages.() -> Unit
    ): Unit = messages.block()
}
