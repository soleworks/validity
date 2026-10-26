package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.clock
import io.github.soleworks.validity.filled
import io.github.soleworks.validity.messages
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.OffsetTime
import java.time.Year
import java.time.YearMonth
import java.time.ZonedDateTime
import java.util.Date

/**
 * Checks that the date is before now.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * Fails with code `past` and, by default, the message `must be in the past`
 * ([Messages.past][io.github.soleworks.validity.Messages.past]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<LocalDate>.past(
    message: String = messages.past
): Unit = constraint(
    message = message,
    code = "past",
    predicate = { it.isBefore(LocalDate.now(clock)) }
)

/**
 * Checks that the date and time is before now.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * Fails with code `past` and, by default, the message `must be in the past`
 * ([Messages.past][io.github.soleworks.validity.Messages.past]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("pastLocalDateTime")
public fun ValidationNode<LocalDateTime>.past(
    message: String = messages.past
): Unit = constraint(
    message = message,
    code = "past",
    predicate = { it.isBefore(LocalDateTime.now(clock)) }
)

/**
 * Checks that the date and time is before now.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * Fails with code `past` and, by default, the message `must be in the past`
 * ([Messages.past][io.github.soleworks.validity.Messages.past]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("pastZonedDateTime")
public fun ValidationNode<ZonedDateTime>.past(
    message: String = messages.past
): Unit = constraint(
    message = message,
    code = "past",
    predicate = { it.isBefore(ZonedDateTime.now(clock)) }
)

/**
 * Checks that the date and time is before now.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * Fails with code `past` and, by default, the message `must be in the past`
 * ([Messages.past][io.github.soleworks.validity.Messages.past]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("pastOffsetDateTime")
public fun ValidationNode<OffsetDateTime>.past(
    message: String = messages.past
): Unit = constraint(
    message = message,
    code = "past",
    predicate = { it.isBefore(OffsetDateTime.now(clock)) }
)

/**
 * Checks that the instant is before now.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * Fails with code `past` and, by default, the message `must be in the past`
 * ([Messages.past][io.github.soleworks.validity.Messages.past]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("pastInstant")
public fun ValidationNode<Instant>.past(
    message: String = messages.past
): Unit = constraint(
    message = message,
    code = "past",
    predicate = { it.isBefore(Instant.now(clock)) }
)

/**
 * Checks that the month is before now.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * Fails with code `past` and, by default, the message `must be in the past`
 * ([Messages.past][io.github.soleworks.validity.Messages.past]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("pastYearMonth")
public fun ValidationNode<YearMonth>.past(
    message: String = messages.past
): Unit = constraint(
    message = message,
    code = "past",
    predicate = { it.isBefore(YearMonth.now(clock)) }
)

/**
 * Checks that the year is before now.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * Fails with code `past` and, by default, the message `must be in the past`
 * ([Messages.past][io.github.soleworks.validity.Messages.past]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("pastYear")
public fun ValidationNode<Year>.past(
    message: String = messages.past
): Unit = constraint(
    message = message,
    code = "past",
    predicate = { it.isBefore(Year.now(clock)) }
)

/**
 * Checks that the date is before now.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * Fails with code `past` and, by default, the message `must be in the past`
 * ([Messages.past][io.github.soleworks.validity.Messages.past]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("pastDate")
public fun ValidationNode<Date>.past(
    message: String = messages.past
): Unit = constraint(
    message = message,
    code = "past",
    predicate = { it.before(Date(clock.millis())) }
)

/**
 * Checks that the date is after now.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * Fails with code `future` and, by default, the message `must be in the future`
 * ([Messages.future][io.github.soleworks.validity.Messages.future]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<LocalDate>.future(
    message: String = messages.future
): Unit = constraint(
    message = message,
    code = "future",
    predicate = { it.isAfter(LocalDate.now(clock)) }
)

/**
 * Checks that the date and time is after now.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * Fails with code `future` and, by default, the message `must be in the future`
 * ([Messages.future][io.github.soleworks.validity.Messages.future]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("futureLocalDateTime")
public fun ValidationNode<LocalDateTime>.future(
    message: String = messages.future
): Unit = constraint(
    message = message,
    code = "future",
    predicate = { it.isAfter(LocalDateTime.now(clock)) }
)

/**
 * Checks that the date and time is after now.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * Fails with code `future` and, by default, the message `must be in the future`
 * ([Messages.future][io.github.soleworks.validity.Messages.future]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("futureZonedDateTime")
public fun ValidationNode<ZonedDateTime>.future(
    message: String = messages.future
): Unit = constraint(
    message = message,
    code = "future",
    predicate = { it.isAfter(ZonedDateTime.now(clock)) }
)

/**
 * Checks that the date and time is after now.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * Fails with code `future` and, by default, the message `must be in the future`
 * ([Messages.future][io.github.soleworks.validity.Messages.future]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("futureOffsetDateTime")
public fun ValidationNode<OffsetDateTime>.future(
    message: String = messages.future
): Unit = constraint(
    message = message,
    code = "future",
    predicate = { it.isAfter(OffsetDateTime.now(clock)) }
)

/**
 * Checks that the instant is after now.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * Fails with code `future` and, by default, the message `must be in the future`
 * ([Messages.future][io.github.soleworks.validity.Messages.future]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("futureInstant")
public fun ValidationNode<Instant>.future(
    message: String = messages.future
): Unit = constraint(
    message = message,
    code = "future",
    predicate = { it.isAfter(Instant.now(clock)) }
)

/**
 * Checks that the month is after now.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * Fails with code `future` and, by default, the message `must be in the future`
 * ([Messages.future][io.github.soleworks.validity.Messages.future]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("futureYearMonth")
public fun ValidationNode<YearMonth>.future(
    message: String = messages.future
): Unit = constraint(
    message = message,
    code = "future",
    predicate = { it.isAfter(YearMonth.now(clock)) }
)

/**
 * Checks that the year is after now.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * Fails with code `future` and, by default, the message `must be in the future`
 * ([Messages.future][io.github.soleworks.validity.Messages.future]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("futureYear")
public fun ValidationNode<Year>.future(
    message: String = messages.future
): Unit = constraint(
    message = message,
    code = "future",
    predicate = { it.isAfter(Year.now(clock)) }
)

/**
 * Checks that the date is after now.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * Fails with code `future` and, by default, the message `must be in the future`
 * ([Messages.future][io.github.soleworks.validity.Messages.future]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("futureDate")
public fun ValidationNode<Date>.future(
    message: String = messages.future
): Unit = constraint(
    message = message,
    code = "future",
    predicate = { it.after(Date(clock.millis())) }
)

/**
 * Checks that the date is now or before.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * Fails with code `pastOrPresent` and, by default, the message `must be in the past or present`
 * ([Messages.pastOrPresent][io.github.soleworks.validity.Messages.pastOrPresent]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<LocalDate>.pastOrPresent(
    message: String = messages.pastOrPresent
): Unit = constraint(
    message = message,
    code = "pastOrPresent",
    predicate = { !it.isAfter(LocalDate.now(clock)) }
)

/**
 * Checks that the date and time is now or before.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * Fails with code `pastOrPresent` and, by default, the message `must be in the past or present`
 * ([Messages.pastOrPresent][io.github.soleworks.validity.Messages.pastOrPresent]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("pastOrPresentLocalDateTime")
public fun ValidationNode<LocalDateTime>.pastOrPresent(
    message: String = messages.pastOrPresent
): Unit = constraint(
    message = message,
    code = "pastOrPresent",
    predicate = { !it.isAfter(LocalDateTime.now(clock)) }
)

/**
 * Checks that the date and time is now or before.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * Fails with code `pastOrPresent` and, by default, the message `must be in the past or present`
 * ([Messages.pastOrPresent][io.github.soleworks.validity.Messages.pastOrPresent]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("pastOrPresentZonedDateTime")
public fun ValidationNode<ZonedDateTime>.pastOrPresent(
    message: String = messages.pastOrPresent
): Unit = constraint(
    message = message,
    code = "pastOrPresent",
    predicate = { !it.isAfter(ZonedDateTime.now(clock)) }
)

/**
 * Checks that the date and time is now or before.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * Fails with code `pastOrPresent` and, by default, the message `must be in the past or present`
 * ([Messages.pastOrPresent][io.github.soleworks.validity.Messages.pastOrPresent]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("pastOrPresentOffsetDateTime")
public fun ValidationNode<OffsetDateTime>.pastOrPresent(
    message: String = messages.pastOrPresent
): Unit = constraint(
    message = message,
    code = "pastOrPresent",
    predicate = { !it.isAfter(OffsetDateTime.now(clock)) }
)

/**
 * Checks that the instant is now or before.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * Fails with code `pastOrPresent` and, by default, the message `must be in the past or present`
 * ([Messages.pastOrPresent][io.github.soleworks.validity.Messages.pastOrPresent]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("pastOrPresentInstant")
public fun ValidationNode<Instant>.pastOrPresent(
    message: String = messages.pastOrPresent
): Unit = constraint(
    message = message,
    code = "pastOrPresent",
    predicate = { !it.isAfter(Instant.now(clock)) }
)

/**
 * Checks that the month is now or before.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * Fails with code `pastOrPresent` and, by default, the message `must be in the past or present`
 * ([Messages.pastOrPresent][io.github.soleworks.validity.Messages.pastOrPresent]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("pastOrPresentYearMonth")
public fun ValidationNode<YearMonth>.pastOrPresent(
    message: String = messages.pastOrPresent
): Unit = constraint(
    message = message,
    code = "pastOrPresent",
    predicate = { !it.isAfter(YearMonth.now(clock)) }
)

/**
 * Checks that the year is now or before.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * Fails with code `pastOrPresent` and, by default, the message `must be in the past or present`
 * ([Messages.pastOrPresent][io.github.soleworks.validity.Messages.pastOrPresent]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("pastOrPresentYear")
public fun ValidationNode<Year>.pastOrPresent(
    message: String = messages.pastOrPresent
): Unit = constraint(
    message = message,
    code = "pastOrPresent",
    predicate = { !it.isAfter(Year.now(clock)) }
)

/**
 * Checks that the date is now or before.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * Fails with code `pastOrPresent` and, by default, the message `must be in the past or present`
 * ([Messages.pastOrPresent][io.github.soleworks.validity.Messages.pastOrPresent]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("pastOrPresentDate")
public fun ValidationNode<Date>.pastOrPresent(
    message: String = messages.pastOrPresent
): Unit = constraint(
    message = message,
    code = "pastOrPresent",
    predicate = { !it.after(Date(clock.millis())) }
)

/**
 * Checks that the date is now or after.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * Fails with code `futureOrPresent` and, by default, the message `must be in the future or present`
 * ([Messages.futureOrPresent][io.github.soleworks.validity.Messages.futureOrPresent]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<LocalDate>.futureOrPresent(
    message: String = messages.futureOrPresent
): Unit = constraint(
    message = message,
    code = "futureOrPresent",
    predicate = { !it.isBefore(LocalDate.now(clock)) }
)

/**
 * Checks that the date and time is now or after.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * Fails with code `futureOrPresent` and, by default, the message `must be in the future or present`
 * ([Messages.futureOrPresent][io.github.soleworks.validity.Messages.futureOrPresent]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("futureOrPresentLocalDateTime")
public fun ValidationNode<LocalDateTime>.futureOrPresent(
    message: String = messages.futureOrPresent
): Unit = constraint(
    message = message,
    code = "futureOrPresent",
    predicate = { !it.isBefore(LocalDateTime.now(clock)) }
)

/**
 * Checks that the date and time is now or after.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * Fails with code `futureOrPresent` and, by default, the message `must be in the future or present`
 * ([Messages.futureOrPresent][io.github.soleworks.validity.Messages.futureOrPresent]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("futureOrPresentZonedDateTime")
public fun ValidationNode<ZonedDateTime>.futureOrPresent(
    message: String = messages.futureOrPresent
): Unit = constraint(
    message = message,
    code = "futureOrPresent",
    predicate = { !it.isBefore(ZonedDateTime.now(clock)) }
)

/**
 * Checks that the date and time is now or after.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * Fails with code `futureOrPresent` and, by default, the message `must be in the future or present`
 * ([Messages.futureOrPresent][io.github.soleworks.validity.Messages.futureOrPresent]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("futureOrPresentOffsetDateTime")
public fun ValidationNode<OffsetDateTime>.futureOrPresent(
    message: String = messages.futureOrPresent
): Unit = constraint(
    message = message,
    code = "futureOrPresent",
    predicate = { !it.isBefore(OffsetDateTime.now(clock)) }
)

/**
 * Checks that the instant is now or after.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * Fails with code `futureOrPresent` and, by default, the message `must be in the future or present`
 * ([Messages.futureOrPresent][io.github.soleworks.validity.Messages.futureOrPresent]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("futureOrPresentInstant")
public fun ValidationNode<Instant>.futureOrPresent(
    message: String = messages.futureOrPresent
): Unit = constraint(
    message = message,
    code = "futureOrPresent",
    predicate = { !it.isBefore(Instant.now(clock)) }
)

/**
 * Checks that the month is now or after.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * Fails with code `futureOrPresent` and, by default, the message `must be in the future or present`
 * ([Messages.futureOrPresent][io.github.soleworks.validity.Messages.futureOrPresent]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("futureOrPresentYearMonth")
public fun ValidationNode<YearMonth>.futureOrPresent(
    message: String = messages.futureOrPresent
): Unit = constraint(
    message = message,
    code = "futureOrPresent",
    predicate = { !it.isBefore(YearMonth.now(clock)) }
)

/**
 * Checks that the year is now or after.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * Fails with code `futureOrPresent` and, by default, the message `must be in the future or present`
 * ([Messages.futureOrPresent][io.github.soleworks.validity.Messages.futureOrPresent]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("futureOrPresentYear")
public fun ValidationNode<Year>.futureOrPresent(
    message: String = messages.futureOrPresent
): Unit = constraint(
    message = message,
    code = "futureOrPresent",
    predicate = { !it.isBefore(Year.now(clock)) }
)

/**
 * Checks that the date is now or after.
 *
 * Now comes from the [clock][io.github.soleworks.validity.ValidityConfiguration.clock] of the configuration, so tests
 * can fix it.
 *
 * Fails with code `futureOrPresent` and, by default, the message `must be in the future or present`
 * ([Messages.futureOrPresent][io.github.soleworks.validity.Messages.futureOrPresent]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
@JvmName("futureOrPresentDate")
public fun ValidationNode<Date>.futureOrPresent(
    message: String = messages.futureOrPresent
): Unit = constraint(
    message = message,
    code = "futureOrPresent",
    predicate = { !it.before(Date(clock.millis())) }
)

/**
 * Checks that the date is after [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `after` and, by default, the message `must be after {other}`
 * ([Messages.after][io.github.soleworks.validity.Messages.after]).
 *
 * @param other the moment it must come after.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<LocalDate>.after(
    other: LocalDate?,
    message: String = messages.after
): Unit = after(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the date is after [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `after` and the message that [message] builds.
 *
 * @param other the moment it must come after.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<LocalDate>.after(
    other: LocalDate?,
    message: LocalDate.(LocalDate) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "after",
        predicate = { it.isAfter(other) }
    )
}

/**
 * Checks that the date and time is after [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `after` and, by default, the message `must be after {other}`
 * ([Messages.after][io.github.soleworks.validity.Messages.after]).
 *
 * @param other the moment it must come after.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<LocalDateTime>.after(
    other: LocalDateTime?,
    message: String = messages.after
): Unit = after(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the date and time is after [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `after` and the message that [message] builds.
 *
 * @param other the moment it must come after.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<LocalDateTime>.after(
    other: LocalDateTime?,
    message: LocalDateTime.(LocalDateTime) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "after",
        predicate = { it.isAfter(other) }
    )
}

/**
 * Checks that the time is after [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `after` and, by default, the message `must be after {other}`
 * ([Messages.after][io.github.soleworks.validity.Messages.after]).
 *
 * @param other the moment it must come after.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<LocalTime>.after(
    other: LocalTime?,
    message: String = messages.after
): Unit = after(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the time is after [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `after` and the message that [message] builds.
 *
 * @param other the moment it must come after.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<LocalTime>.after(
    other: LocalTime?,
    message: LocalTime.(LocalTime) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "after",
        predicate = { it.isAfter(other) }
    )
}

/**
 * Checks that the time is after [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `after` and, by default, the message `must be after {other}`
 * ([Messages.after][io.github.soleworks.validity.Messages.after]).
 *
 * @param other the moment it must come after.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<OffsetTime>.after(
    other: OffsetTime?,
    message: String = messages.after
): Unit = after(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the time is after [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `after` and the message that [message] builds.
 *
 * @param other the moment it must come after.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<OffsetTime>.after(
    other: OffsetTime?,
    message: OffsetTime.(OffsetTime) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "after",
        predicate = { it.isAfter(other) }
    )
}

/**
 * Checks that the date and time is after [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `after` and, by default, the message `must be after {other}`
 * ([Messages.after][io.github.soleworks.validity.Messages.after]).
 *
 * @param other the moment it must come after.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<ZonedDateTime>.after(
    other: ZonedDateTime?,
    message: String = messages.after
): Unit = after(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the date and time is after [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `after` and the message that [message] builds.
 *
 * @param other the moment it must come after.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<ZonedDateTime>.after(
    other: ZonedDateTime?,
    message: ZonedDateTime.(ZonedDateTime) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "after",
        predicate = { it.isAfter(other) }
    )
}

/**
 * Checks that the date and time is after [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `after` and, by default, the message `must be after {other}`
 * ([Messages.after][io.github.soleworks.validity.Messages.after]).
 *
 * @param other the moment it must come after.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<OffsetDateTime>.after(
    other: OffsetDateTime?,
    message: String = messages.after
): Unit = after(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the date and time is after [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `after` and the message that [message] builds.
 *
 * @param other the moment it must come after.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<OffsetDateTime>.after(
    other: OffsetDateTime?,
    message: OffsetDateTime.(OffsetDateTime) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "after",
        predicate = { it.isAfter(other) }
    )
}

/**
 * Checks that the instant is after [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `after` and, by default, the message `must be after {other}`
 * ([Messages.after][io.github.soleworks.validity.Messages.after]).
 *
 * @param other the moment it must come after.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Instant>.after(
    other: Instant?,
    message: String = messages.after
): Unit = after(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the instant is after [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `after` and the message that [message] builds.
 *
 * @param other the moment it must come after.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<Instant>.after(
    other: Instant?,
    message: Instant.(Instant) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "after",
        predicate = { it.isAfter(other) }
    )
}

/**
 * Checks that the month is after [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `after` and, by default, the message `must be after {other}`
 * ([Messages.after][io.github.soleworks.validity.Messages.after]).
 *
 * @param other the moment it must come after.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<YearMonth>.after(
    other: YearMonth?,
    message: String = messages.after
): Unit = after(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the month is after [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `after` and the message that [message] builds.
 *
 * @param other the moment it must come after.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<YearMonth>.after(
    other: YearMonth?,
    message: YearMonth.(YearMonth) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "after",
        predicate = { it.isAfter(other) }
    )
}

/**
 * Checks that the year is after [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `after` and, by default, the message `must be after {other}`
 * ([Messages.after][io.github.soleworks.validity.Messages.after]).
 *
 * @param other the moment it must come after.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Year>.after(
    other: Year?,
    message: String = messages.after
): Unit = after(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the year is after [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `after` and the message that [message] builds.
 *
 * @param other the moment it must come after.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<Year>.after(
    other: Year?,
    message: Year.(Year) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "after",
        predicate = { it.isAfter(other) }
    )
}

/**
 * Checks that the date is after [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `after` and, by default, the message `must be after {other}`
 * ([Messages.after][io.github.soleworks.validity.Messages.after]).
 *
 * @param other the moment it must come after.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Date>.after(
    other: Date?,
    message: String = messages.after
): Unit = after(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the date is after [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `after` and the message that [message] builds.
 *
 * @param other the moment it must come after.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<Date>.after(
    other: Date?,
    message: Date.(Date) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "after",
        predicate = { it.after(other) }
    )
}

/**
 * Checks that the date is before [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `before` and, by default, the message `must be before {other}`
 * ([Messages.before][io.github.soleworks.validity.Messages.before]).
 *
 * @param other the moment it must come before.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<LocalDate>.before(
    other: LocalDate?,
    message: String = messages.before
): Unit = before(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the date is before [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `before` and the message that [message] builds.
 *
 * @param other the moment it must come before.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<LocalDate>.before(
    other: LocalDate?,
    message: LocalDate.(LocalDate) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "before",
        predicate = { it.isBefore(other) }
    )
}

/**
 * Checks that the date and time is before [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `before` and, by default, the message `must be before {other}`
 * ([Messages.before][io.github.soleworks.validity.Messages.before]).
 *
 * @param other the moment it must come before.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<LocalDateTime>.before(
    other: LocalDateTime?,
    message: String = messages.before
): Unit = before(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the date and time is before [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `before` and the message that [message] builds.
 *
 * @param other the moment it must come before.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<LocalDateTime>.before(
    other: LocalDateTime?,
    message: LocalDateTime.(LocalDateTime) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "before",
        predicate = { it.isBefore(other) }
    )
}

/**
 * Checks that the time is before [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `before` and, by default, the message `must be before {other}`
 * ([Messages.before][io.github.soleworks.validity.Messages.before]).
 *
 * @param other the moment it must come before.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<LocalTime>.before(
    other: LocalTime?,
    message: String = messages.before
): Unit = before(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the time is before [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `before` and the message that [message] builds.
 *
 * @param other the moment it must come before.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<LocalTime>.before(
    other: LocalTime?,
    message: LocalTime.(LocalTime) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "before",
        predicate = { it.isBefore(other) }
    )
}

/**
 * Checks that the time is before [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `before` and, by default, the message `must be before {other}`
 * ([Messages.before][io.github.soleworks.validity.Messages.before]).
 *
 * @param other the moment it must come before.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<OffsetTime>.before(
    other: OffsetTime?,
    message: String = messages.before
): Unit = before(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the time is before [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `before` and the message that [message] builds.
 *
 * @param other the moment it must come before.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<OffsetTime>.before(
    other: OffsetTime?,
    message: OffsetTime.(OffsetTime) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "before",
        predicate = { it.isBefore(other) }
    )
}

/**
 * Checks that the date and time is before [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `before` and, by default, the message `must be before {other}`
 * ([Messages.before][io.github.soleworks.validity.Messages.before]).
 *
 * @param other the moment it must come before.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<ZonedDateTime>.before(
    other: ZonedDateTime?,
    message: String = messages.before
): Unit = before(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the date and time is before [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `before` and the message that [message] builds.
 *
 * @param other the moment it must come before.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<ZonedDateTime>.before(
    other: ZonedDateTime?,
    message: ZonedDateTime.(ZonedDateTime) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "before",
        predicate = { it.isBefore(other) }
    )
}

/**
 * Checks that the date and time is before [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `before` and, by default, the message `must be before {other}`
 * ([Messages.before][io.github.soleworks.validity.Messages.before]).
 *
 * @param other the moment it must come before.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<OffsetDateTime>.before(
    other: OffsetDateTime?,
    message: String = messages.before
): Unit = before(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the date and time is before [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `before` and the message that [message] builds.
 *
 * @param other the moment it must come before.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<OffsetDateTime>.before(
    other: OffsetDateTime?,
    message: OffsetDateTime.(OffsetDateTime) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "before",
        predicate = { it.isBefore(other) }
    )
}

/**
 * Checks that the instant is before [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `before` and, by default, the message `must be before {other}`
 * ([Messages.before][io.github.soleworks.validity.Messages.before]).
 *
 * @param other the moment it must come before.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Instant>.before(
    other: Instant?,
    message: String = messages.before
): Unit = before(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the instant is before [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `before` and the message that [message] builds.
 *
 * @param other the moment it must come before.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<Instant>.before(
    other: Instant?,
    message: Instant.(Instant) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "before",
        predicate = { it.isBefore(other) }
    )
}

/**
 * Checks that the month is before [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `before` and, by default, the message `must be before {other}`
 * ([Messages.before][io.github.soleworks.validity.Messages.before]).
 *
 * @param other the moment it must come before.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<YearMonth>.before(
    other: YearMonth?,
    message: String = messages.before
): Unit = before(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the month is before [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `before` and the message that [message] builds.
 *
 * @param other the moment it must come before.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<YearMonth>.before(
    other: YearMonth?,
    message: YearMonth.(YearMonth) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "before",
        predicate = { it.isBefore(other) }
    )
}

/**
 * Checks that the year is before [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `before` and, by default, the message `must be before {other}`
 * ([Messages.before][io.github.soleworks.validity.Messages.before]).
 *
 * @param other the moment it must come before.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Year>.before(
    other: Year?,
    message: String = messages.before
): Unit = before(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the year is before [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `before` and the message that [message] builds.
 *
 * @param other the moment it must come before.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<Year>.before(
    other: Year?,
    message: Year.(Year) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "before",
        predicate = { it.isBefore(other) }
    )
}

/**
 * Checks that the date is before [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `before` and, by default, the message `must be before {other}`
 * ([Messages.before][io.github.soleworks.validity.Messages.before]).
 *
 * @param other the moment it must come before.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Date>.before(
    other: Date?,
    message: String = messages.before
): Unit = before(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the date is before [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `before` and the message that [message] builds.
 *
 * @param other the moment it must come before.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<Date>.before(
    other: Date?,
    message: Date.(Date) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "before",
        predicate = { it.before(other) }
    )
}

/**
 * Checks that the date is [other] or after.
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `afterOrEqual` and, by default, the message `must be after or equal to {other}`
 * ([Messages.afterOrEqual][io.github.soleworks.validity.Messages.afterOrEqual]).
 *
 * @param other the earliest moment accepted.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<LocalDate>.afterOrEqual(
    other: LocalDate?,
    message: String = messages.afterOrEqual
): Unit = afterOrEqual(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the date is [other] or after.
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `afterOrEqual` and the message that [message] builds.
 *
 * @param other the earliest moment accepted.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<LocalDate>.afterOrEqual(
    other: LocalDate?,
    message: LocalDate.(LocalDate) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "afterOrEqual",
        predicate = { !it.isBefore(other) }
    )
}

/**
 * Checks that the date and time is [other] or after.
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `afterOrEqual` and, by default, the message `must be after or equal to {other}`
 * ([Messages.afterOrEqual][io.github.soleworks.validity.Messages.afterOrEqual]).
 *
 * @param other the earliest moment accepted.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<LocalDateTime>.afterOrEqual(
    other: LocalDateTime?,
    message: String = messages.afterOrEqual
): Unit = afterOrEqual(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the date and time is [other] or after.
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `afterOrEqual` and the message that [message] builds.
 *
 * @param other the earliest moment accepted.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<LocalDateTime>.afterOrEqual(
    other: LocalDateTime?,
    message: LocalDateTime.(LocalDateTime) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "afterOrEqual",
        predicate = { !it.isBefore(other) }
    )
}

/**
 * Checks that the time is [other] or after.
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `afterOrEqual` and, by default, the message `must be after or equal to {other}`
 * ([Messages.afterOrEqual][io.github.soleworks.validity.Messages.afterOrEqual]).
 *
 * @param other the earliest moment accepted.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<LocalTime>.afterOrEqual(
    other: LocalTime?,
    message: String = messages.afterOrEqual
): Unit = afterOrEqual(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the time is [other] or after.
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `afterOrEqual` and the message that [message] builds.
 *
 * @param other the earliest moment accepted.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<LocalTime>.afterOrEqual(
    other: LocalTime?,
    message: LocalTime.(LocalTime) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "afterOrEqual",
        predicate = { !it.isBefore(other) }
    )
}

/**
 * Checks that the time is [other] or after.
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `afterOrEqual` and, by default, the message `must be after or equal to {other}`
 * ([Messages.afterOrEqual][io.github.soleworks.validity.Messages.afterOrEqual]).
 *
 * @param other the earliest moment accepted.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<OffsetTime>.afterOrEqual(
    other: OffsetTime?,
    message: String = messages.afterOrEqual
): Unit = afterOrEqual(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the time is [other] or after.
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `afterOrEqual` and the message that [message] builds.
 *
 * @param other the earliest moment accepted.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<OffsetTime>.afterOrEqual(
    other: OffsetTime?,
    message: OffsetTime.(OffsetTime) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "afterOrEqual",
        predicate = { !it.isBefore(other) }
    )
}

/**
 * Checks that the date and time is [other] or after.
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `afterOrEqual` and, by default, the message `must be after or equal to {other}`
 * ([Messages.afterOrEqual][io.github.soleworks.validity.Messages.afterOrEqual]).
 *
 * @param other the earliest moment accepted.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<ZonedDateTime>.afterOrEqual(
    other: ZonedDateTime?,
    message: String = messages.afterOrEqual
): Unit = afterOrEqual(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the date and time is [other] or after.
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `afterOrEqual` and the message that [message] builds.
 *
 * @param other the earliest moment accepted.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<ZonedDateTime>.afterOrEqual(
    other: ZonedDateTime?,
    message: ZonedDateTime.(ZonedDateTime) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "afterOrEqual",
        predicate = { !it.isBefore(other) }
    )
}

/**
 * Checks that the date and time is [other] or after.
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `afterOrEqual` and, by default, the message `must be after or equal to {other}`
 * ([Messages.afterOrEqual][io.github.soleworks.validity.Messages.afterOrEqual]).
 *
 * @param other the earliest moment accepted.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<OffsetDateTime>.afterOrEqual(
    other: OffsetDateTime?,
    message: String = messages.afterOrEqual
): Unit = afterOrEqual(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the date and time is [other] or after.
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `afterOrEqual` and the message that [message] builds.
 *
 * @param other the earliest moment accepted.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<OffsetDateTime>.afterOrEqual(
    other: OffsetDateTime?,
    message: OffsetDateTime.(OffsetDateTime) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "afterOrEqual",
        predicate = { !it.isBefore(other) }
    )
}

/**
 * Checks that the instant is [other] or after.
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `afterOrEqual` and, by default, the message `must be after or equal to {other}`
 * ([Messages.afterOrEqual][io.github.soleworks.validity.Messages.afterOrEqual]).
 *
 * @param other the earliest moment accepted.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Instant>.afterOrEqual(
    other: Instant?,
    message: String = messages.afterOrEqual
): Unit = afterOrEqual(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the instant is [other] or after.
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `afterOrEqual` and the message that [message] builds.
 *
 * @param other the earliest moment accepted.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<Instant>.afterOrEqual(
    other: Instant?,
    message: Instant.(Instant) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "afterOrEqual",
        predicate = { !it.isBefore(other) }
    )
}

/**
 * Checks that the month is [other] or after.
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `afterOrEqual` and, by default, the message `must be after or equal to {other}`
 * ([Messages.afterOrEqual][io.github.soleworks.validity.Messages.afterOrEqual]).
 *
 * @param other the earliest moment accepted.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<YearMonth>.afterOrEqual(
    other: YearMonth?,
    message: String = messages.afterOrEqual
): Unit = afterOrEqual(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the month is [other] or after.
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `afterOrEqual` and the message that [message] builds.
 *
 * @param other the earliest moment accepted.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<YearMonth>.afterOrEqual(
    other: YearMonth?,
    message: YearMonth.(YearMonth) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "afterOrEqual",
        predicate = { !it.isBefore(other) }
    )
}

/**
 * Checks that the year is [other] or after.
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `afterOrEqual` and, by default, the message `must be after or equal to {other}`
 * ([Messages.afterOrEqual][io.github.soleworks.validity.Messages.afterOrEqual]).
 *
 * @param other the earliest moment accepted.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Year>.afterOrEqual(
    other: Year?,
    message: String = messages.afterOrEqual
): Unit = afterOrEqual(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the year is [other] or after.
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `afterOrEqual` and the message that [message] builds.
 *
 * @param other the earliest moment accepted.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<Year>.afterOrEqual(
    other: Year?,
    message: Year.(Year) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "afterOrEqual",
        predicate = { !it.isBefore(other) }
    )
}

/**
 * Checks that the date is [other] or after.
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `afterOrEqual` and, by default, the message `must be after or equal to {other}`
 * ([Messages.afterOrEqual][io.github.soleworks.validity.Messages.afterOrEqual]).
 *
 * @param other the earliest moment accepted.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Date>.afterOrEqual(
    other: Date?,
    message: String = messages.afterOrEqual
): Unit = afterOrEqual(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the date is [other] or after.
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `afterOrEqual` and the message that [message] builds.
 *
 * @param other the earliest moment accepted.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<Date>.afterOrEqual(
    other: Date?,
    message: Date.(Date) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "afterOrEqual",
        predicate = { !it.before(other) }
    )
}

/**
 * Checks that the date is [other] or before.
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `beforeOrEqual` and, by default, the message `must be before or equal to {other}`
 * ([Messages.beforeOrEqual][io.github.soleworks.validity.Messages.beforeOrEqual]).
 *
 * @param other the latest moment accepted.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<LocalDate>.beforeOrEqual(
    other: LocalDate?,
    message: String = messages.beforeOrEqual
): Unit = beforeOrEqual(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the date is [other] or before.
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `beforeOrEqual` and the message that [message] builds.
 *
 * @param other the latest moment accepted.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<LocalDate>.beforeOrEqual(
    other: LocalDate?,
    message: LocalDate.(LocalDate) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "beforeOrEqual",
        predicate = { !it.isAfter(other) }
    )
}

/**
 * Checks that the date and time is [other] or before.
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `beforeOrEqual` and, by default, the message `must be before or equal to {other}`
 * ([Messages.beforeOrEqual][io.github.soleworks.validity.Messages.beforeOrEqual]).
 *
 * @param other the latest moment accepted.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<LocalDateTime>.beforeOrEqual(
    other: LocalDateTime?,
    message: String = messages.beforeOrEqual
): Unit = beforeOrEqual(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the date and time is [other] or before.
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `beforeOrEqual` and the message that [message] builds.
 *
 * @param other the latest moment accepted.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<LocalDateTime>.beforeOrEqual(
    other: LocalDateTime?,
    message: LocalDateTime.(LocalDateTime) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "beforeOrEqual",
        predicate = { !it.isAfter(other) }
    )
}

/**
 * Checks that the time is [other] or before.
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `beforeOrEqual` and, by default, the message `must be before or equal to {other}`
 * ([Messages.beforeOrEqual][io.github.soleworks.validity.Messages.beforeOrEqual]).
 *
 * @param other the latest moment accepted.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<LocalTime>.beforeOrEqual(
    other: LocalTime?,
    message: String = messages.beforeOrEqual
): Unit = beforeOrEqual(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the time is [other] or before.
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `beforeOrEqual` and the message that [message] builds.
 *
 * @param other the latest moment accepted.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<LocalTime>.beforeOrEqual(
    other: LocalTime?,
    message: LocalTime.(LocalTime) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "beforeOrEqual",
        predicate = { !it.isAfter(other) }
    )
}

/**
 * Checks that the time is [other] or before.
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `beforeOrEqual` and, by default, the message `must be before or equal to {other}`
 * ([Messages.beforeOrEqual][io.github.soleworks.validity.Messages.beforeOrEqual]).
 *
 * @param other the latest moment accepted.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<OffsetTime>.beforeOrEqual(
    other: OffsetTime?,
    message: String = messages.beforeOrEqual
): Unit = beforeOrEqual(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the time is [other] or before.
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `beforeOrEqual` and the message that [message] builds.
 *
 * @param other the latest moment accepted.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<OffsetTime>.beforeOrEqual(
    other: OffsetTime?,
    message: OffsetTime.(OffsetTime) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "beforeOrEqual",
        predicate = { !it.isAfter(other) }
    )
}

/**
 * Checks that the date and time is [other] or before.
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `beforeOrEqual` and, by default, the message `must be before or equal to {other}`
 * ([Messages.beforeOrEqual][io.github.soleworks.validity.Messages.beforeOrEqual]).
 *
 * @param other the latest moment accepted.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<ZonedDateTime>.beforeOrEqual(
    other: ZonedDateTime?,
    message: String = messages.beforeOrEqual
): Unit = beforeOrEqual(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the date and time is [other] or before.
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `beforeOrEqual` and the message that [message] builds.
 *
 * @param other the latest moment accepted.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<ZonedDateTime>.beforeOrEqual(
    other: ZonedDateTime?,
    message: ZonedDateTime.(ZonedDateTime) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "beforeOrEqual",
        predicate = { !it.isAfter(other) }
    )
}

/**
 * Checks that the date and time is [other] or before.
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `beforeOrEqual` and, by default, the message `must be before or equal to {other}`
 * ([Messages.beforeOrEqual][io.github.soleworks.validity.Messages.beforeOrEqual]).
 *
 * @param other the latest moment accepted.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<OffsetDateTime>.beforeOrEqual(
    other: OffsetDateTime?,
    message: String = messages.beforeOrEqual
): Unit = beforeOrEqual(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the date and time is [other] or before.
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `beforeOrEqual` and the message that [message] builds.
 *
 * @param other the latest moment accepted.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<OffsetDateTime>.beforeOrEqual(
    other: OffsetDateTime?,
    message: OffsetDateTime.(OffsetDateTime) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "beforeOrEqual",
        predicate = { !it.isAfter(other) }
    )
}

/**
 * Checks that the instant is [other] or before.
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `beforeOrEqual` and, by default, the message `must be before or equal to {other}`
 * ([Messages.beforeOrEqual][io.github.soleworks.validity.Messages.beforeOrEqual]).
 *
 * @param other the latest moment accepted.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Instant>.beforeOrEqual(
    other: Instant?,
    message: String = messages.beforeOrEqual
): Unit = beforeOrEqual(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the instant is [other] or before.
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `beforeOrEqual` and the message that [message] builds.
 *
 * @param other the latest moment accepted.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<Instant>.beforeOrEqual(
    other: Instant?,
    message: Instant.(Instant) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "beforeOrEqual",
        predicate = { !it.isAfter(other) }
    )
}

/**
 * Checks that the month is [other] or before.
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `beforeOrEqual` and, by default, the message `must be before or equal to {other}`
 * ([Messages.beforeOrEqual][io.github.soleworks.validity.Messages.beforeOrEqual]).
 *
 * @param other the latest moment accepted.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<YearMonth>.beforeOrEqual(
    other: YearMonth?,
    message: String = messages.beforeOrEqual
): Unit = beforeOrEqual(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the month is [other] or before.
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `beforeOrEqual` and the message that [message] builds.
 *
 * @param other the latest moment accepted.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<YearMonth>.beforeOrEqual(
    other: YearMonth?,
    message: YearMonth.(YearMonth) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "beforeOrEqual",
        predicate = { !it.isAfter(other) }
    )
}

/**
 * Checks that the year is [other] or before.
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `beforeOrEqual` and, by default, the message `must be before or equal to {other}`
 * ([Messages.beforeOrEqual][io.github.soleworks.validity.Messages.beforeOrEqual]).
 *
 * @param other the latest moment accepted.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Year>.beforeOrEqual(
    other: Year?,
    message: String = messages.beforeOrEqual
): Unit = beforeOrEqual(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the year is [other] or before.
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `beforeOrEqual` and the message that [message] builds.
 *
 * @param other the latest moment accepted.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<Year>.beforeOrEqual(
    other: Year?,
    message: Year.(Year) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "beforeOrEqual",
        predicate = { !it.isAfter(other) }
    )
}

/**
 * Checks that the date is [other] or before.
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `beforeOrEqual` and, by default, the message `must be before or equal to {other}`
 * ([Messages.beforeOrEqual][io.github.soleworks.validity.Messages.beforeOrEqual]).
 *
 * @param other the latest moment accepted.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Date>.beforeOrEqual(
    other: Date?,
    message: String = messages.beforeOrEqual
): Unit = beforeOrEqual(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the date is [other] or before.
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `beforeOrEqual` and the message that [message] builds.
 *
 * @param other the latest moment accepted.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<Date>.beforeOrEqual(
    other: Date?,
    message: Date.(Date) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "beforeOrEqual",
        predicate = { !it.after(other) }
    )
}

/**
 * Checks that the date is between [start] and [end], both included.
 *
 * When [start] or [end] is `null`, nothing is checked.
 *
 * Fails with code `between` and, by default, the message `must be between {start} and {end}`
 * ([Messages.betweenDates][io.github.soleworks.validity.Messages.betweenDates]).
 *
 * ```kotlin
 * ::checkOut required { between(checkIn, checkIn?.plusDays(30)) }
 * ```
 *
 * @param start the earliest moment accepted.
 * @param end the latest moment accepted.
 * @param message the message of the violation. `{start}`, `{end}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<LocalDate>.between(
    start: LocalDate?,
    end: LocalDate?,
    message: String = messages.betweenDates
): Unit = between(
    start = start,
    end = end,
    message = { _, _ -> message.filled("start" to start, "end" to end) }
)

/**
 * Checks that the date is between [start] and [end], both included.
 *
 * When [start] or [end] is `null`, nothing is checked.
 *
 * Fails with code `between` and the message that [message] builds.
 *
 * @param start the earliest moment accepted.
 * @param end the latest moment accepted.
 * @param message builds the message from the value (the receiver), [start] and [end].
 */
public fun ValidationNode<LocalDate>.between(
    start: LocalDate?,
    end: LocalDate?,
    message: LocalDate.(LocalDate, LocalDate) -> String
) {
    if (start == null || end == null)
        return

    constraint(
        message = { it.message(start, end) },
        code = "between",
        predicate = { !it.isBefore(start) && !it.isAfter(end) }
    )
}

/**
 * Checks that the date and time is between [start] and [end], both included.
 *
 * When [start] or [end] is `null`, nothing is checked.
 *
 * Fails with code `between` and, by default, the message `must be between {start} and {end}`
 * ([Messages.betweenDates][io.github.soleworks.validity.Messages.betweenDates]).
 *
 * @param start the earliest moment accepted.
 * @param end the latest moment accepted.
 * @param message the message of the violation. `{start}`, `{end}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<LocalDateTime>.between(
    start: LocalDateTime?,
    end: LocalDateTime?,
    message: String = messages.betweenDates
): Unit = between(
    start = start,
    end = end,
    message = { _, _ -> message.filled("start" to start, "end" to end) }
)

/**
 * Checks that the date and time is between [start] and [end], both included.
 *
 * When [start] or [end] is `null`, nothing is checked.
 *
 * Fails with code `between` and the message that [message] builds.
 *
 * @param start the earliest moment accepted.
 * @param end the latest moment accepted.
 * @param message builds the message from the value (the receiver), [start] and [end].
 */
public fun ValidationNode<LocalDateTime>.between(
    start: LocalDateTime?,
    end: LocalDateTime?,
    message: LocalDateTime.(LocalDateTime, LocalDateTime) -> String
) {
    if (start == null || end == null)
        return

    constraint(
        message = { it.message(start, end) },
        code = "between",
        predicate = { !it.isBefore(start) && !it.isAfter(end) }
    )
}

/**
 * Checks that the time is between [start] and [end], both included.
 *
 * When [start] or [end] is `null`, nothing is checked.
 *
 * Fails with code `between` and, by default, the message `must be between {start} and {end}`
 * ([Messages.betweenDates][io.github.soleworks.validity.Messages.betweenDates]).
 *
 * @param start the earliest moment accepted.
 * @param end the latest moment accepted.
 * @param message the message of the violation. `{start}`, `{end}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<LocalTime>.between(
    start: LocalTime?,
    end: LocalTime?,
    message: String = messages.betweenDates
): Unit = between(
    start = start,
    end = end,
    message = { _, _ -> message.filled("start" to start, "end" to end) }
)

/**
 * Checks that the time is between [start] and [end], both included.
 *
 * When [start] or [end] is `null`, nothing is checked.
 *
 * Fails with code `between` and the message that [message] builds.
 *
 * @param start the earliest moment accepted.
 * @param end the latest moment accepted.
 * @param message builds the message from the value (the receiver), [start] and [end].
 */
public fun ValidationNode<LocalTime>.between(
    start: LocalTime?,
    end: LocalTime?,
    message: LocalTime.(LocalTime, LocalTime) -> String
) {
    if (start == null || end == null)
        return

    constraint(
        message = { it.message(start, end) },
        code = "between",
        predicate = { !it.isBefore(start) && !it.isAfter(end) }
    )
}

/**
 * Checks that the time is between [start] and [end], both included.
 *
 * When [start] or [end] is `null`, nothing is checked.
 *
 * Fails with code `between` and, by default, the message `must be between {start} and {end}`
 * ([Messages.betweenDates][io.github.soleworks.validity.Messages.betweenDates]).
 *
 * @param start the earliest moment accepted.
 * @param end the latest moment accepted.
 * @param message the message of the violation. `{start}`, `{end}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<OffsetTime>.between(
    start: OffsetTime?,
    end: OffsetTime?,
    message: String = messages.betweenDates
): Unit = between(
    start = start,
    end = end,
    message = { _, _ -> message.filled("start" to start, "end" to end) }
)

/**
 * Checks that the time is between [start] and [end], both included.
 *
 * When [start] or [end] is `null`, nothing is checked.
 *
 * Fails with code `between` and the message that [message] builds.
 *
 * @param start the earliest moment accepted.
 * @param end the latest moment accepted.
 * @param message builds the message from the value (the receiver), [start] and [end].
 */
public fun ValidationNode<OffsetTime>.between(
    start: OffsetTime?,
    end: OffsetTime?,
    message: OffsetTime.(OffsetTime, OffsetTime) -> String
) {
    if (start == null || end == null)
        return

    constraint(
        message = { it.message(start, end) },
        code = "between",
        predicate = { !it.isBefore(start) && !it.isAfter(end) }
    )
}

/**
 * Checks that the date and time is between [start] and [end], both included.
 *
 * When [start] or [end] is `null`, nothing is checked.
 *
 * Fails with code `between` and, by default, the message `must be between {start} and {end}`
 * ([Messages.betweenDates][io.github.soleworks.validity.Messages.betweenDates]).
 *
 * @param start the earliest moment accepted.
 * @param end the latest moment accepted.
 * @param message the message of the violation. `{start}`, `{end}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<ZonedDateTime>.between(
    start: ZonedDateTime?,
    end: ZonedDateTime?,
    message: String = messages.betweenDates
): Unit = between(
    start = start,
    end = end,
    message = { _, _ -> message.filled("start" to start, "end" to end) }
)

/**
 * Checks that the date and time is between [start] and [end], both included.
 *
 * When [start] or [end] is `null`, nothing is checked.
 *
 * Fails with code `between` and the message that [message] builds.
 *
 * @param start the earliest moment accepted.
 * @param end the latest moment accepted.
 * @param message builds the message from the value (the receiver), [start] and [end].
 */
public fun ValidationNode<ZonedDateTime>.between(
    start: ZonedDateTime?,
    end: ZonedDateTime?,
    message: ZonedDateTime.(ZonedDateTime, ZonedDateTime) -> String
) {
    if (start == null || end == null)
        return

    constraint(
        message = { it.message(start, end) },
        code = "between",
        predicate = { !it.isBefore(start) && !it.isAfter(end) }
    )
}

/**
 * Checks that the date and time is between [start] and [end], both included.
 *
 * When [start] or [end] is `null`, nothing is checked.
 *
 * Fails with code `between` and, by default, the message `must be between {start} and {end}`
 * ([Messages.betweenDates][io.github.soleworks.validity.Messages.betweenDates]).
 *
 * @param start the earliest moment accepted.
 * @param end the latest moment accepted.
 * @param message the message of the violation. `{start}`, `{end}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<OffsetDateTime>.between(
    start: OffsetDateTime?,
    end: OffsetDateTime?,
    message: String = messages.betweenDates
): Unit = between(
    start = start,
    end = end,
    message = { _, _ -> message.filled("start" to start, "end" to end) }
)

/**
 * Checks that the date and time is between [start] and [end], both included.
 *
 * When [start] or [end] is `null`, nothing is checked.
 *
 * Fails with code `between` and the message that [message] builds.
 *
 * @param start the earliest moment accepted.
 * @param end the latest moment accepted.
 * @param message builds the message from the value (the receiver), [start] and [end].
 */
public fun ValidationNode<OffsetDateTime>.between(
    start: OffsetDateTime?,
    end: OffsetDateTime?,
    message: OffsetDateTime.(OffsetDateTime, OffsetDateTime) -> String
) {
    if (start == null || end == null)
        return

    constraint(
        message = { it.message(start, end) },
        code = "between",
        predicate = { !it.isBefore(start) && !it.isAfter(end) }
    )
}

/**
 * Checks that the instant is between [start] and [end], both included.
 *
 * When [start] or [end] is `null`, nothing is checked.
 *
 * Fails with code `between` and, by default, the message `must be between {start} and {end}`
 * ([Messages.betweenDates][io.github.soleworks.validity.Messages.betweenDates]).
 *
 * @param start the earliest moment accepted.
 * @param end the latest moment accepted.
 * @param message the message of the violation. `{start}`, `{end}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Instant>.between(
    start: Instant?,
    end: Instant?,
    message: String = messages.betweenDates
): Unit = between(
    start = start,
    end = end,
    message = { _, _ -> message.filled("start" to start, "end" to end) }
)

/**
 * Checks that the instant is between [start] and [end], both included.
 *
 * When [start] or [end] is `null`, nothing is checked.
 *
 * Fails with code `between` and the message that [message] builds.
 *
 * @param start the earliest moment accepted.
 * @param end the latest moment accepted.
 * @param message builds the message from the value (the receiver), [start] and [end].
 */
public fun ValidationNode<Instant>.between(
    start: Instant?,
    end: Instant?,
    message: Instant.(Instant, Instant) -> String
) {
    if (start == null || end == null)
        return

    constraint(
        message = { it.message(start, end) },
        code = "between",
        predicate = { !it.isBefore(start) && !it.isAfter(end) }
    )
}

/**
 * Checks that the month is between [start] and [end], both included.
 *
 * When [start] or [end] is `null`, nothing is checked.
 *
 * Fails with code `between` and, by default, the message `must be between {start} and {end}`
 * ([Messages.betweenDates][io.github.soleworks.validity.Messages.betweenDates]).
 *
 * @param start the earliest moment accepted.
 * @param end the latest moment accepted.
 * @param message the message of the violation. `{start}`, `{end}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<YearMonth>.between(
    start: YearMonth?,
    end: YearMonth?,
    message: String = messages.betweenDates
): Unit = between(
    start = start,
    end = end,
    message = { _, _ -> message.filled("start" to start, "end" to end) }
)

/**
 * Checks that the month is between [start] and [end], both included.
 *
 * When [start] or [end] is `null`, nothing is checked.
 *
 * Fails with code `between` and the message that [message] builds.
 *
 * @param start the earliest moment accepted.
 * @param end the latest moment accepted.
 * @param message builds the message from the value (the receiver), [start] and [end].
 */
public fun ValidationNode<YearMonth>.between(
    start: YearMonth?,
    end: YearMonth?,
    message: YearMonth.(YearMonth, YearMonth) -> String
) {
    if (start == null || end == null)
        return

    constraint(
        message = { it.message(start, end) },
        code = "between",
        predicate = { !it.isBefore(start) && !it.isAfter(end) }
    )
}

/**
 * Checks that the year is between [start] and [end], both included.
 *
 * When [start] or [end] is `null`, nothing is checked.
 *
 * Fails with code `between` and, by default, the message `must be between {start} and {end}`
 * ([Messages.betweenDates][io.github.soleworks.validity.Messages.betweenDates]).
 *
 * @param start the earliest moment accepted.
 * @param end the latest moment accepted.
 * @param message the message of the violation. `{start}`, `{end}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Year>.between(
    start: Year?,
    end: Year?,
    message: String = messages.betweenDates
): Unit = between(
    start = start,
    end = end,
    message = { _, _ -> message.filled("start" to start, "end" to end) }
)

/**
 * Checks that the year is between [start] and [end], both included.
 *
 * When [start] or [end] is `null`, nothing is checked.
 *
 * Fails with code `between` and the message that [message] builds.
 *
 * @param start the earliest moment accepted.
 * @param end the latest moment accepted.
 * @param message builds the message from the value (the receiver), [start] and [end].
 */
public fun ValidationNode<Year>.between(
    start: Year?,
    end: Year?,
    message: Year.(Year, Year) -> String
) {
    if (start == null || end == null)
        return

    constraint(
        message = { it.message(start, end) },
        code = "between",
        predicate = { !it.isBefore(start) && !it.isAfter(end) }
    )
}

/**
 * Checks that the date is between [start] and [end], both included.
 *
 * When [start] or [end] is `null`, nothing is checked.
 *
 * Fails with code `between` and, by default, the message `must be between {start} and {end}`
 * ([Messages.betweenDates][io.github.soleworks.validity.Messages.betweenDates]).
 *
 * @param start the earliest moment accepted.
 * @param end the latest moment accepted.
 * @param message the message of the violation. `{start}`, `{end}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Date>.between(
    start: Date?,
    end: Date?,
    message: String = messages.betweenDates
): Unit = between(
    start = start,
    end = end,
    message = { _, _ -> message.filled("start" to start, "end" to end) }
)

/**
 * Checks that the date is between [start] and [end], both included.
 *
 * When [start] or [end] is `null`, nothing is checked.
 *
 * Fails with code `between` and the message that [message] builds.
 *
 * @param start the earliest moment accepted.
 * @param end the latest moment accepted.
 * @param message builds the message from the value (the receiver), [start] and [end].
 */
public fun ValidationNode<Date>.between(
    start: Date?,
    end: Date?,
    message: Date.(Date, Date) -> String
) {
    if (start == null || end == null)
        return

    constraint(
        message = { it.message(start, end) },
        code = "between",
        predicate = { !it.before(start) && !it.after(end) }
    )
}

/**
 * Checks that the duration is at least [min].
 *
 * When [min] is `null`, nothing is checked.
 *
 * Fails with code `min` and, by default, the message `must be at least {min}`
 * ([Messages.min][io.github.soleworks.validity.Messages.min]).
 *
 * @param min the smallest value accepted.
 * @param message the message of the violation. `{min}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Duration>.min(
    min: Duration?,
    message: String = messages.min
): Unit = min(
    min = min,
    message = { message.filled("min" to min) }
)

/**
 * Checks that the duration is at least [min].
 *
 * When [min] is `null`, nothing is checked.
 *
 * Fails with code `min` and the message that [message] builds.
 *
 * @param min the smallest value accepted.
 * @param message builds the message from the value (the receiver) and [min].
 */
public fun ValidationNode<Duration>.min(
    min: Duration?,
    message: Duration.(Duration) -> String
) {
    if (min == null)
        return

    constraint(
        message = { it.message(min) },
        code = "min",
        predicate = { it >= min }
    )
}

/**
 * Checks that the duration is at most [max].
 *
 * When [max] is `null`, nothing is checked.
 *
 * Fails with code `max` and, by default, the message `must be at most {max}`
 * ([Messages.max][io.github.soleworks.validity.Messages.max]).
 *
 * @param max the largest value accepted.
 * @param message the message of the violation. `{max}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Duration>.max(
    max: Duration?,
    message: String = messages.max
): Unit = max(
    max = max,
    message = { message.filled("max" to max) }
)

/**
 * Checks that the duration is at most [max].
 *
 * When [max] is `null`, nothing is checked.
 *
 * Fails with code `max` and the message that [message] builds.
 *
 * @param max the largest value accepted.
 * @param message builds the message from the value (the receiver) and [max].
 */
public fun ValidationNode<Duration>.max(
    max: Duration?,
    message: Duration.(Duration) -> String
) {
    if (max == null)
        return

    constraint(
        message = { it.message(max) },
        code = "max",
        predicate = { it <= max }
    )
}

/**
 * Checks that the duration is greater than [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `greaterThan` and, by default, the message `must be greater than {other}`
 * ([Messages.greaterThan][io.github.soleworks.validity.Messages.greaterThan]).
 *
 * @param other the value it must be greater than.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Duration>.greaterThan(
    other: Duration?,
    message: String = messages.greaterThan
): Unit = greaterThan(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the duration is greater than [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `greaterThan` and the message that [message] builds.
 *
 * @param other the value it must be greater than.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<Duration>.greaterThan(
    other: Duration?,
    message: Duration.(Duration) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "greaterThan",
        predicate = { it > other }
    )
}

/**
 * Checks that the duration is less than [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `lessThan` and, by default, the message `must be less than {other}`
 * ([Messages.lessThan][io.github.soleworks.validity.Messages.lessThan]).
 *
 * @param other the value it must be less than.
 * @param message the message of the violation. `{other}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Duration>.lessThan(
    other: Duration?,
    message: String = messages.lessThan
): Unit = lessThan(
    other = other,
    message = { message.filled("other" to other) }
)

/**
 * Checks that the duration is less than [other].
 *
 * When [other] is `null`, nothing is checked.
 *
 * Fails with code `lessThan` and the message that [message] builds.
 *
 * @param other the value it must be less than.
 * @param message builds the message from the value (the receiver) and [other].
 */
public fun ValidationNode<Duration>.lessThan(
    other: Duration?,
    message: Duration.(Duration) -> String
) {
    if (other == null)
        return

    constraint(
        message = { it.message(other) },
        code = "lessThan",
        predicate = { it < other }
    )
}

/**
 * Checks that the duration is between [min] and [max], both included.
 *
 * When [min] or [max] is `null`, nothing is checked.
 *
 * Fails with code `between` and, by default, the message `must be between {min} and {max}`
 * ([Messages.between][io.github.soleworks.validity.Messages.between]).
 *
 * @param min the shortest duration accepted.
 * @param max the longest duration accepted.
 * @param message the message of the violation. `{min}`, `{max}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<Duration>.between(
    min: Duration?,
    max: Duration?,
    message: String = messages.between
): Unit = between(
    min = min,
    max = max,
    message = { _, _ -> message.filled("min" to min, "max" to max) }
)

/**
 * Checks that the duration is between [min] and [max], both included.
 *
 * When [min] or [max] is `null`, nothing is checked.
 *
 * Fails with code `between` and the message that [message] builds.
 *
 * @param min the shortest duration accepted.
 * @param max the longest duration accepted.
 * @param message builds the message from the value (the receiver), [min] and [max].
 */
public fun ValidationNode<Duration>.between(
    min: Duration?,
    max: Duration?,
    message: Duration.(Duration, Duration) -> String
) {
    if (min == null || max == null)
        return

    constraint(
        message = { it.message(min, max) },
        code = "between",
        predicate = { it in min..max }
    )
}
