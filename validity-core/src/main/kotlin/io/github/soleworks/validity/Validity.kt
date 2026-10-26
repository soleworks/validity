package io.github.soleworks.validity

import java.time.Clock
import kotlinx.coroutines.asContextElement
import kotlinx.coroutines.withContext

public class Validity private constructor(
    private val blocks: List<ValidityConfiguration.() -> Unit>
) {
    internal val configuration: ValidityConfiguration = ValidityConfiguration().apply {
        blocks.forEach { block -> block() }
    }

    internal suspend fun <T> use(
        block: suspend () -> T
    ): T = withContext(active.asContextElement(this)) { block() }

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

@PublishedApi
internal val clock: Clock
    get() = Validity.current.configuration.clock

public inline fun <T> now(
    of: (Clock) -> T
): T = of(clock)
