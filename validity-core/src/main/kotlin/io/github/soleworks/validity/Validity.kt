package io.github.soleworks.validity

import java.time.Clock
import kotlinx.coroutines.asContextElement
import kotlinx.coroutines.withContext

/**
 * A configuration of Validity: the messages of the violations and the clock that says what "now" is.
 *
 * Most applications set it once, at startup, with [configure]. A [Validity] made with [invoke] is a variation of that
 * configuration, for one call: pass it to [validate][io.github.soleworks.validity.validate] to validate in another
 * language or at a fixed time, without changing the configuration of everyone else.
 *
 * ```kotlin
 * Validity.configure {
 *     messages { required = "é obrigatório" }
 * }
 *
 * val portuguese = Validity { messages { minLength = "deve ter pelo menos {min} caracteres" } }
 *
 * customer.validate(portuguese)
 * ```
 */
public class Validity private constructor(
    private val blocks: List<ValidityConfiguration.() -> Unit>
) {
    internal val configuration: ValidityConfiguration = ValidityConfiguration().apply {
        blocks.forEach { block -> block() }
    }

    internal suspend fun <T> use(
        block: suspend () -> T
    ): T = withContext(active.asContextElement(this)) { block() }

    /** Creates and configures the instances of [Validity], and holds the default one. */
    public companion object {
        private val active = ThreadLocal<Validity>()

        @Volatile
        internal var default: Validity = Validity(emptyList())
            private set

        internal val current: Validity
            get() = active.get() ?: default

        /**
         * Replaces the default configuration, the one every validation uses unless it is given another.
         *
         * Each call starts from the built-in defaults, so a second call undoes the first. Call it once, at startup.
         *
         * @param block sets the messages and the clock.
         */
        public fun configure(
            block: ValidityConfiguration.() -> Unit
        ) {
            default = Validity(listOf(block))
        }

        /**
         * Creates a configuration that starts from the default one and applies [block] on top of it.
         *
         * The default configuration does not change.
         *
         * ```kotlin
         * val validity = Validity { clock = Clock.fixed(Instant.parse("2030-01-01T00:00:00Z"), ZoneOffset.UTC) }
         *
         * subscription.validate(validity)
         * ```
         *
         * @param block sets the messages and the clock.
         */
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

/**
 * Reads the current time from the clock of the configuration that is validating, so a custom constraint follows a fixed
 * clock the same way [past][io.github.soleworks.validity.constraints.past] and the built-in constraints do.
 *
 * ```kotlin
 * ::startsOn required {
 *     constraint("must start within a year") { it <= now(LocalDate::now).plusYears(1) }
 * }
 * ```
 *
 * @param T the type of what [of] reads.
 * @param of reads the time from the clock, such as `LocalDate::now` or `Instant::now`.
 * @return what [of] returns.
 */
public inline fun <T> now(
    of: (Clock) -> T
): T = of(clock)
