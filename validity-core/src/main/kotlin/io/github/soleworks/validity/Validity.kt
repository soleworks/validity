package io.github.soleworks.validity

import java.time.Clock

public class Validity private constructor(
    private val blocks: List<ValidityConfiguration.() -> Unit>
) {
    internal val configuration: ValidityConfiguration = ValidityConfiguration().apply {
        blocks.forEach { block -> block() }
    }

    internal fun <T> use(
        block: () -> T
    ): T {
        val outer = active.get()

        active.set(this)

        try {
            return block()
        } finally {
            if (outer == null)
                active.remove()
            else
                active.set(outer)
        }
    }

    public companion object {
        private val active = ThreadLocal<Validity>()

        @Volatile
        internal var default: Validity = Validity(emptyList())
            private set

        internal val current: Validity
            get() = active.get() ?: default

        public fun configure(
            block: ValidityConfiguration.() -> Unit
        ) {
            default = Validity(listOf(block))
        }

        public operator fun invoke(
            block: ValidityConfiguration.() -> Unit
        ): Validity = Validity(default.blocks + block)
    }
}

@PublishedApi
internal val messages: Messages
    get() = Validity.current.configuration.messages

internal val clock: Clock
    get() = Validity.current.configuration.clock

public fun <T> now(
    of: (Clock) -> T
): T = of(clock)
