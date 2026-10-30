package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.clock
import io.github.soleworks.validity.filled
import io.github.soleworks.validity.messages
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.Period
import java.time.ZonedDateTime
import kotlin.time.Duration
import kotlin.time.toJavaDuration

private val WEEKEND = setOf(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY)

/**
 * Checks that someone born on the date is at least [min] years old today.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * When [min] is `null`, nothing is checked.
 *
 * Fails with code `minAge` and, by default, the message `must be at least {min} years old`
 * ([Messages.minAge][io.github.soleworks.validity.Messages.minAge]).
 *
 * ```kotlin
 * ::birthDate required { minAge(18) }
 * ```
 *
 * @param min the youngest age accepted, in whole years.
 * @param message the message of the violation. `{min}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<LocalDate>.minAge(
    min: Int?,
    message: String = messages.minAge
): Unit = minAge(
    min = min,
    message = { message.filled("min" to min) }
)

/**
 * Checks that someone born on the date is at least [min] years old today.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * When [min] is `null`, nothing is checked.
 *
 * Fails with code `minAge` and the message that [message] builds.
 *
 * @param min the youngest age accepted, in whole years.
 * @param message builds the message from the value (the receiver) and [min].
 */
public fun ValidationNode<LocalDate>.minAge(
    min: Int?,
    message: suspend LocalDate.(Int) -> String
): Unit = constraint(
    argument = min,
    message = message,
    code = "minAge",
    predicate = { value, argument -> value.age() >= argument }
)

/**
 * Checks that someone born on the date is at most [max] years old today.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * When [max] is `null`, nothing is checked.
 *
 * Fails with code `maxAge` and, by default, the message `must be at most {max} years old`
 * ([Messages.maxAge][io.github.soleworks.validity.Messages.maxAge]).
 *
 * @param max the oldest age accepted, in whole years.
 * @param message the message of the violation. `{max}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<LocalDate>.maxAge(
    max: Int?,
    message: String = messages.maxAge
): Unit = maxAge(
    max = max,
    message = { message.filled("max" to max) }
)

/**
 * Checks that someone born on the date is at most [max] years old today.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * When [max] is `null`, nothing is checked.
 *
 * Fails with code `maxAge` and the message that [message] builds.
 *
 * @param max the oldest age accepted, in whole years.
 * @param message builds the message from the value (the receiver) and [max].
 */
public fun ValidationNode<LocalDate>.maxAge(
    max: Int?,
    message: suspend LocalDate.(Int) -> String
): Unit = constraint(
    argument = max,
    message = message,
    code = "maxAge",
    predicate = { value, argument -> value.age() <= argument }
)

/**
 * Checks that the date is between today and [duration] from today, both included.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * Fails with code `withinNext` and, by default, the message `must be within the next {duration}`
 * ([Messages.withinNext][io.github.soleworks.validity.Messages.withinNext]).
 *
 * ```kotlin
 * ::deliveryDate required { withinNext(30.days) }
 * ```
 *
 * @param duration how far ahead of today it can be. Only whole days count.
 * @param message the message of the violation. `{duration}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<LocalDate>.withinNext(
    duration: Duration?,
    message: String = messages.withinNext
): Unit = withinNext(
    duration = duration,
    message = { message.filled("duration" to duration) }
)

/**
 * Checks that the date is between today and [duration] from today, both included.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * Fails with code `withinNext` and the message that [message] builds.
 *
 * @param duration how far ahead of today it can be. Only whole days count.
 * @param message builds the message from the value (the receiver) and [duration].
 */
public fun ValidationNode<LocalDate>.withinNext(
    duration: Duration?,
    message: suspend LocalDate.(Duration) -> String
): Unit = within(
    duration = duration,
    code = "withinNext",
    message = message,
    window = { value, today, length -> value in today..today.plusDays(length.inWholeDays) },
    now = { LocalDate.now(clock) }
)

/**
 * Checks that the date and time is between now and [duration] from now, both included.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * Fails with code `withinNext` and, by default, the message `must be within the next {duration}`
 * ([Messages.withinNext][io.github.soleworks.validity.Messages.withinNext]).
 *
 * @param duration how far ahead of now it can be.
 * @param message the message of the violation. `{duration}`, `{path}` and `{value}` are filled in.
 */
@JvmName("withinNextLocalDateTime")
public fun ValidationNode<LocalDateTime>.withinNext(
    duration: Duration?,
    message: String = messages.withinNext
): Unit = withinNext(
    duration = duration,
    message = { message.filled("duration" to duration) }
)

/**
 * Checks that the date and time is between now and [duration] from now, both included.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * Fails with code `withinNext` and the message that [message] builds.
 *
 * @param duration how far ahead of now it can be.
 * @param message builds the message from the value (the receiver) and [duration].
 */
@JvmName("withinNextLocalDateTime")
public fun ValidationNode<LocalDateTime>.withinNext(
    duration: Duration?,
    message: suspend LocalDateTime.(Duration) -> String
): Unit = within(
    duration = duration,
    code = "withinNext",
    message = message,
    window = { value, now, length -> value in now..now.plus(length.toJavaDuration()) },
    now = { LocalDateTime.now(clock) }
)

/**
 * Checks that the date and time is between now and [duration] from now, both included.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * Fails with code `withinNext` and, by default, the message `must be within the next {duration}`
 * ([Messages.withinNext][io.github.soleworks.validity.Messages.withinNext]).
 *
 * @param duration how far ahead of now it can be.
 * @param message the message of the violation. `{duration}`, `{path}` and `{value}` are filled in.
 */
@JvmName("withinNextZonedDateTime")
public fun ValidationNode<ZonedDateTime>.withinNext(
    duration: Duration?,
    message: String = messages.withinNext
): Unit = withinNext(
    duration = duration,
    message = { message.filled("duration" to duration) }
)

/**
 * Checks that the date and time is between now and [duration] from now, both included.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * Fails with code `withinNext` and the message that [message] builds.
 *
 * @param duration how far ahead of now it can be.
 * @param message builds the message from the value (the receiver) and [duration].
 */
@JvmName("withinNextZonedDateTime")
public fun ValidationNode<ZonedDateTime>.withinNext(
    duration: Duration?,
    message: suspend ZonedDateTime.(Duration) -> String
): Unit = within(
    duration = duration,
    code = "withinNext",
    message = message,
    window = { value, now, length -> value in now..now.plus(length.toJavaDuration()) },
    now = { ZonedDateTime.now(clock) }
)

/**
 * Checks that the date and time is between now and [duration] from now, both included.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * Fails with code `withinNext` and, by default, the message `must be within the next {duration}`
 * ([Messages.withinNext][io.github.soleworks.validity.Messages.withinNext]).
 *
 * @param duration how far ahead of now it can be.
 * @param message the message of the violation. `{duration}`, `{path}` and `{value}` are filled in.
 */
@JvmName("withinNextOffsetDateTime")
public fun ValidationNode<OffsetDateTime>.withinNext(
    duration: Duration?,
    message: String = messages.withinNext
): Unit = withinNext(
    duration = duration,
    message = { message.filled("duration" to duration) }
)

/**
 * Checks that the date and time is between now and [duration] from now, both included.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * Fails with code `withinNext` and the message that [message] builds.
 *
 * @param duration how far ahead of now it can be.
 * @param message builds the message from the value (the receiver) and [duration].
 */
@JvmName("withinNextOffsetDateTime")
public fun ValidationNode<OffsetDateTime>.withinNext(
    duration: Duration?,
    message: suspend OffsetDateTime.(Duration) -> String
): Unit = within(
    duration = duration,
    code = "withinNext",
    message = message,
    window = { value, now, length -> value in now..now.plus(length.toJavaDuration()) },
    now = { OffsetDateTime.now(clock) }
)

/**
 * Checks that the instant is between now and [duration] from now, both included.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * Fails with code `withinNext` and, by default, the message `must be within the next {duration}`
 * ([Messages.withinNext][io.github.soleworks.validity.Messages.withinNext]).
 *
 * @param duration how far ahead of now it can be.
 * @param message the message of the violation. `{duration}`, `{path}` and `{value}` are filled in.
 */
@JvmName("withinNextInstant")
public fun ValidationNode<Instant>.withinNext(
    duration: Duration?,
    message: String = messages.withinNext
): Unit = withinNext(
    duration = duration,
    message = { message.filled("duration" to duration) }
)

/**
 * Checks that the instant is between now and [duration] from now, both included.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * Fails with code `withinNext` and the message that [message] builds.
 *
 * @param duration how far ahead of now it can be.
 * @param message builds the message from the value (the receiver) and [duration].
 */
@JvmName("withinNextInstant")
public fun ValidationNode<Instant>.withinNext(
    duration: Duration?,
    message: suspend Instant.(Duration) -> String
): Unit = within(
    duration = duration,
    code = "withinNext",
    message = message,
    window = { value, now, length -> value in now..now.plus(length.toJavaDuration()) },
    now = { Instant.now(clock) }
)

/**
 * Checks that the date is between [duration] before today and today, both included.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * Fails with code `withinLast` and, by default, the message `must be within the last {duration}`
 * ([Messages.withinLast][io.github.soleworks.validity.Messages.withinLast]).
 *
 * @param duration how far before today it can be. Only whole days count.
 * @param message the message of the violation. `{duration}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<LocalDate>.withinLast(
    duration: Duration?,
    message: String = messages.withinLast
): Unit = withinLast(
    duration = duration,
    message = { message.filled("duration" to duration) }
)

/**
 * Checks that the date is between [duration] before today and today, both included.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * Fails with code `withinLast` and the message that [message] builds.
 *
 * @param duration how far before today it can be. Only whole days count.
 * @param message builds the message from the value (the receiver) and [duration].
 */
public fun ValidationNode<LocalDate>.withinLast(
    duration: Duration?,
    message: suspend LocalDate.(Duration) -> String
): Unit = within(
    duration = duration,
    code = "withinLast",
    message = message,
    window = { value, today, length -> value in today.minusDays(length.inWholeDays)..today },
    now = { LocalDate.now(clock) }
)

/**
 * Checks that the date and time is between [duration] ago and now, both included.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * Fails with code `withinLast` and, by default, the message `must be within the last {duration}`
 * ([Messages.withinLast][io.github.soleworks.validity.Messages.withinLast]).
 *
 * @param duration how far before now it can be.
 * @param message the message of the violation. `{duration}`, `{path}` and `{value}` are filled in.
 */
@JvmName("withinLastLocalDateTime")
public fun ValidationNode<LocalDateTime>.withinLast(
    duration: Duration?,
    message: String = messages.withinLast
): Unit = withinLast(
    duration = duration,
    message = { message.filled("duration" to duration) }
)

/**
 * Checks that the date and time is between [duration] ago and now, both included.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * Fails with code `withinLast` and the message that [message] builds.
 *
 * @param duration how far before now it can be.
 * @param message builds the message from the value (the receiver) and [duration].
 */
@JvmName("withinLastLocalDateTime")
public fun ValidationNode<LocalDateTime>.withinLast(
    duration: Duration?,
    message: suspend LocalDateTime.(Duration) -> String
): Unit = within(
    duration = duration,
    code = "withinLast",
    message = message,
    window = { value, now, length -> value in now.minus(length.toJavaDuration())..now },
    now = { LocalDateTime.now(clock) }
)

/**
 * Checks that the date and time is between [duration] ago and now, both included.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * Fails with code `withinLast` and, by default, the message `must be within the last {duration}`
 * ([Messages.withinLast][io.github.soleworks.validity.Messages.withinLast]).
 *
 * @param duration how far before now it can be.
 * @param message the message of the violation. `{duration}`, `{path}` and `{value}` are filled in.
 */
@JvmName("withinLastZonedDateTime")
public fun ValidationNode<ZonedDateTime>.withinLast(
    duration: Duration?,
    message: String = messages.withinLast
): Unit = withinLast(
    duration = duration,
    message = { message.filled("duration" to duration) }
)

/**
 * Checks that the date and time is between [duration] ago and now, both included.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * Fails with code `withinLast` and the message that [message] builds.
 *
 * @param duration how far before now it can be.
 * @param message builds the message from the value (the receiver) and [duration].
 */
@JvmName("withinLastZonedDateTime")
public fun ValidationNode<ZonedDateTime>.withinLast(
    duration: Duration?,
    message: suspend ZonedDateTime.(Duration) -> String
): Unit = within(
    duration = duration,
    code = "withinLast",
    message = message,
    window = { value, now, length -> value in now.minus(length.toJavaDuration())..now },
    now = { ZonedDateTime.now(clock) }
)

/**
 * Checks that the date and time is between [duration] ago and now, both included.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * Fails with code `withinLast` and, by default, the message `must be within the last {duration}`
 * ([Messages.withinLast][io.github.soleworks.validity.Messages.withinLast]).
 *
 * @param duration how far before now it can be.
 * @param message the message of the violation. `{duration}`, `{path}` and `{value}` are filled in.
 */
@JvmName("withinLastOffsetDateTime")
public fun ValidationNode<OffsetDateTime>.withinLast(
    duration: Duration?,
    message: String = messages.withinLast
): Unit = withinLast(
    duration = duration,
    message = { message.filled("duration" to duration) }
)

/**
 * Checks that the date and time is between [duration] ago and now, both included.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * Fails with code `withinLast` and the message that [message] builds.
 *
 * @param duration how far before now it can be.
 * @param message builds the message from the value (the receiver) and [duration].
 */
@JvmName("withinLastOffsetDateTime")
public fun ValidationNode<OffsetDateTime>.withinLast(
    duration: Duration?,
    message: suspend OffsetDateTime.(Duration) -> String
): Unit = within(
    duration = duration,
    code = "withinLast",
    message = message,
    window = { value, now, length -> value in now.minus(length.toJavaDuration())..now },
    now = { OffsetDateTime.now(clock) }
)

/**
 * Checks that the instant is between [duration] ago and now, both included.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * Fails with code `withinLast` and, by default, the message `must be within the last {duration}`
 * ([Messages.withinLast][io.github.soleworks.validity.Messages.withinLast]).
 *
 * @param duration how far before now it can be.
 * @param message the message of the violation. `{duration}`, `{path}` and `{value}` are filled in.
 */
@JvmName("withinLastInstant")
public fun ValidationNode<Instant>.withinLast(
    duration: Duration?,
    message: String = messages.withinLast
): Unit = withinLast(
    duration = duration,
    message = { message.filled("duration" to duration) }
)

/**
 * Checks that the instant is between [duration] ago and now, both included.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * Fails with code `withinLast` and the message that [message] builds.
 *
 * @param duration how far before now it can be.
 * @param message builds the message from the value (the receiver) and [duration].
 */
@JvmName("withinLastInstant")
public fun ValidationNode<Instant>.withinLast(
    duration: Duration?,
    message: suspend Instant.(Duration) -> String
): Unit = within(
    duration = duration,
    code = "withinLast",
    message = message,
    window = { value, now, length -> value in now.minus(length.toJavaDuration())..now },
    now = { Instant.now(clock) }
)

/**
 * Checks that the date falls on today.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * Fails with code `today` and, by default, the message `must be today`
 * ([Messages.today][io.github.soleworks.validity.Messages.today]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<LocalDate>.today(
    message: String = messages.today
): Unit = constraint(
    message = message,
    code = "today",
    predicate = { it == LocalDate.now(clock) }
)

/**
 * Checks that the date and time falls on today.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * Fails with code `today` and, by default, the message `must be today`
 * ([Messages.today][io.github.soleworks.validity.Messages.today]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("todayLocalDateTime")
public fun ValidationNode<LocalDateTime>.today(
    message: String = messages.today
): Unit = constraint(
    message = message,
    code = "today",
    predicate = { it.toLocalDate() == LocalDate.now(clock) }
)

/**
 * Checks that the date and time falls on today.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * It is moved to the zone of the clock before its date is compared.
 *
 * Fails with code `today` and, by default, the message `must be today`
 * ([Messages.today][io.github.soleworks.validity.Messages.today]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("todayZonedDateTime")
public fun ValidationNode<ZonedDateTime>.today(
    message: String = messages.today
): Unit = constraint(
    message = message,
    code = "today",
    predicate = { it.withZoneSameInstant(clock.zone).toLocalDate() == LocalDate.now(clock) }
)

/**
 * Checks that the date and time falls on today.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * It is moved to the zone of the clock before its date is compared.
 *
 * Fails with code `today` and, by default, the message `must be today`
 * ([Messages.today][io.github.soleworks.validity.Messages.today]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("todayOffsetDateTime")
public fun ValidationNode<OffsetDateTime>.today(
    message: String = messages.today
): Unit = constraint(
    message = message,
    code = "today",
    predicate = { it.atZoneSameInstant(clock.zone).toLocalDate() == LocalDate.now(clock) }
)

/**
 * Checks that the date falls from Monday to Friday.
 *
 * Fails with code `weekday` and, by default, the message `must be a weekday`
 * ([Messages.weekday][io.github.soleworks.validity.Messages.weekday]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<LocalDate>.weekday(
    message: String = messages.weekday
): Unit = constraint(
    message = message,
    code = "weekday",
    predicate = { it.dayOfWeek !in WEEKEND }
)

/**
 * Checks that the date and time falls from Monday to Friday.
 *
 * Fails with code `weekday` and, by default, the message `must be a weekday`
 * ([Messages.weekday][io.github.soleworks.validity.Messages.weekday]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("weekdayLocalDateTime")
public fun ValidationNode<LocalDateTime>.weekday(
    message: String = messages.weekday
): Unit = constraint(
    message = message,
    code = "weekday",
    predicate = { it.dayOfWeek !in WEEKEND }
)

/**
 * Checks that the date and time falls from Monday to Friday.
 *
 * Fails with code `weekday` and, by default, the message `must be a weekday`
 * ([Messages.weekday][io.github.soleworks.validity.Messages.weekday]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("weekdayZonedDateTime")
public fun ValidationNode<ZonedDateTime>.weekday(
    message: String = messages.weekday
): Unit = constraint(
    message = message,
    code = "weekday",
    predicate = { it.dayOfWeek !in WEEKEND }
)

/**
 * Checks that the date and time falls from Monday to Friday.
 *
 * Fails with code `weekday` and, by default, the message `must be a weekday`
 * ([Messages.weekday][io.github.soleworks.validity.Messages.weekday]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("weekdayOffsetDateTime")
public fun ValidationNode<OffsetDateTime>.weekday(
    message: String = messages.weekday
): Unit = constraint(
    message = message,
    code = "weekday",
    predicate = { it.dayOfWeek !in WEEKEND }
)

/**
 * Checks that the date falls on a Saturday or a Sunday.
 *
 * Fails with code `weekend` and, by default, the message `must be on a weekend`
 * ([Messages.weekend][io.github.soleworks.validity.Messages.weekend]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<LocalDate>.weekend(
    message: String = messages.weekend
): Unit = constraint(
    message = message,
    code = "weekend",
    predicate = { it.dayOfWeek in WEEKEND }
)

/**
 * Checks that the date and time falls on a Saturday or a Sunday.
 *
 * Fails with code `weekend` and, by default, the message `must be on a weekend`
 * ([Messages.weekend][io.github.soleworks.validity.Messages.weekend]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("weekendLocalDateTime")
public fun ValidationNode<LocalDateTime>.weekend(
    message: String = messages.weekend
): Unit = constraint(
    message = message,
    code = "weekend",
    predicate = { it.dayOfWeek in WEEKEND }
)

/**
 * Checks that the date and time falls on a Saturday or a Sunday.
 *
 * Fails with code `weekend` and, by default, the message `must be on a weekend`
 * ([Messages.weekend][io.github.soleworks.validity.Messages.weekend]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("weekendZonedDateTime")
public fun ValidationNode<ZonedDateTime>.weekend(
    message: String = messages.weekend
): Unit = constraint(
    message = message,
    code = "weekend",
    predicate = { it.dayOfWeek in WEEKEND }
)

/**
 * Checks that the date and time falls on a Saturday or a Sunday.
 *
 * Fails with code `weekend` and, by default, the message `must be on a weekend`
 * ([Messages.weekend][io.github.soleworks.validity.Messages.weekend]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("weekendOffsetDateTime")
public fun ValidationNode<OffsetDateTime>.weekend(
    message: String = messages.weekend
): Unit = constraint(
    message = message,
    code = "weekend",
    predicate = { it.dayOfWeek in WEEKEND }
)

private fun <V> ValidationNode<V>.within(
    duration: Duration?,
    code: String,
    message: suspend V.(Duration) -> String,
    window: suspend (value: V, now: V, length: Duration) -> Boolean,
    now: suspend () -> V
) = constraint(
    argument = duration,
    message = message,
    code = code,
    predicate = { value, argument -> window(value, now(), argument) }
)

private fun LocalDate.age(): Int = Period.between(this, LocalDate.now(clock)).years
