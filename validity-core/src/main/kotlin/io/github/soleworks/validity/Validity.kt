package io.github.soleworks.validity

import java.time.Clock

public object Validity {
    @Volatile
    internal var configuration: ValidityConfiguration = ValidityConfiguration()
        private set

    public fun configure(
        block: ValidityConfiguration.() -> Unit
    ) {
        configuration = ValidityConfiguration().apply(block)
    }
}

internal val messages: Messages
    get() = Validity.configuration.messages

internal val clock: Clock
    get() = Validity.configuration.clock
