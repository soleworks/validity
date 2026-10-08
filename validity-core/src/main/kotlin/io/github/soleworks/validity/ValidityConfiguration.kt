package io.github.soleworks.validity

@ValidityDsl
public class ValidityConfiguration internal constructor() {
    internal val messages: Messages = Messages()

    public fun messages(
        block: Messages.() -> Unit
    ): Unit = messages.block()
}
