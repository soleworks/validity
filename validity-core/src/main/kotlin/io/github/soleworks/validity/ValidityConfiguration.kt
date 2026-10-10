package io.github.soleworks.validity

import java.time.Clock

@ValidityDsl
public class ValidityConfiguration internal constructor() {
    internal val messages: Messages = Messages()

    public var clock: Clock = Clock.systemDefaultZone()

    public fun messages(
        block: Messages.() -> Unit
    ): Unit = messages.block()
}
