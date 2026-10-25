package io.github.soleworks.validity

public class MessagesKey<T : Any>(
    internal val create: () -> T
) {
    public val current: T
        get() = messages.module(this)
}
