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

public fun ValidationNode<LocalDate>.past(
    message: String = messages.past
): Unit = constraint(
    message = message,
    code = "past",
    predicate = { it.isBefore(LocalDate.now(clock)) }
)

@JvmName("pastLocalDateTime")
public fun ValidationNode<LocalDateTime>.past(
    message: String = messages.past
): Unit = constraint(
    message = message,
    code = "past",
    predicate = { it.isBefore(LocalDateTime.now(clock)) }
)

@JvmName("pastZonedDateTime")
public fun ValidationNode<ZonedDateTime>.past(
    message: String = messages.past
): Unit = constraint(
    message = message,
    code = "past",
    predicate = { it.isBefore(ZonedDateTime.now(clock)) }
)

@JvmName("pastOffsetDateTime")
public fun ValidationNode<OffsetDateTime>.past(
    message: String = messages.past
): Unit = constraint(
    message = message,
    code = "past",
    predicate = { it.isBefore(OffsetDateTime.now(clock)) }
)

@JvmName("pastInstant")
public fun ValidationNode<Instant>.past(
    message: String = messages.past
): Unit = constraint(
    message = message,
    code = "past",
    predicate = { it.isBefore(Instant.now(clock)) }
)

@JvmName("pastYearMonth")
public fun ValidationNode<YearMonth>.past(
    message: String = messages.past
): Unit = constraint(
    message = message,
    code = "past",
    predicate = { it.isBefore(YearMonth.now(clock)) }
)

@JvmName("pastYear")
public fun ValidationNode<Year>.past(
    message: String = messages.past
): Unit = constraint(
    message = message,
    code = "past",
    predicate = { it.isBefore(Year.now(clock)) }
)

@JvmName("pastDate")
public fun ValidationNode<Date>.past(
    message: String = messages.past
): Unit = constraint(
    message = message,
    code = "past",
    predicate = { it.before(Date(clock.millis())) }
)

public fun ValidationNode<LocalDate>.future(
    message: String = messages.future
): Unit = constraint(
    message = message,
    code = "future",
    predicate = { it.isAfter(LocalDate.now(clock)) }
)

@JvmName("futureLocalDateTime")
public fun ValidationNode<LocalDateTime>.future(
    message: String = messages.future
): Unit = constraint(
    message = message,
    code = "future",
    predicate = { it.isAfter(LocalDateTime.now(clock)) }
)

@JvmName("futureZonedDateTime")
public fun ValidationNode<ZonedDateTime>.future(
    message: String = messages.future
): Unit = constraint(
    message = message,
    code = "future",
    predicate = { it.isAfter(ZonedDateTime.now(clock)) }
)

@JvmName("futureOffsetDateTime")
public fun ValidationNode<OffsetDateTime>.future(
    message: String = messages.future
): Unit = constraint(
    message = message,
    code = "future",
    predicate = { it.isAfter(OffsetDateTime.now(clock)) }
)

@JvmName("futureInstant")
public fun ValidationNode<Instant>.future(
    message: String = messages.future
): Unit = constraint(
    message = message,
    code = "future",
    predicate = { it.isAfter(Instant.now(clock)) }
)

@JvmName("futureYearMonth")
public fun ValidationNode<YearMonth>.future(
    message: String = messages.future
): Unit = constraint(
    message = message,
    code = "future",
    predicate = { it.isAfter(YearMonth.now(clock)) }
)

@JvmName("futureYear")
public fun ValidationNode<Year>.future(
    message: String = messages.future
): Unit = constraint(
    message = message,
    code = "future",
    predicate = { it.isAfter(Year.now(clock)) }
)

@JvmName("futureDate")
public fun ValidationNode<Date>.future(
    message: String = messages.future
): Unit = constraint(
    message = message,
    code = "future",
    predicate = { it.after(Date(clock.millis())) }
)

public fun ValidationNode<LocalDate>.pastOrPresent(
    message: String = messages.pastOrPresent
): Unit = constraint(
    message = message,
    code = "pastOrPresent",
    predicate = { !it.isAfter(LocalDate.now(clock)) }
)

@JvmName("pastOrPresentLocalDateTime")
public fun ValidationNode<LocalDateTime>.pastOrPresent(
    message: String = messages.pastOrPresent
): Unit = constraint(
    message = message,
    code = "pastOrPresent",
    predicate = { !it.isAfter(LocalDateTime.now(clock)) }
)

@JvmName("pastOrPresentZonedDateTime")
public fun ValidationNode<ZonedDateTime>.pastOrPresent(
    message: String = messages.pastOrPresent
): Unit = constraint(
    message = message,
    code = "pastOrPresent",
    predicate = { !it.isAfter(ZonedDateTime.now(clock)) }
)

@JvmName("pastOrPresentOffsetDateTime")
public fun ValidationNode<OffsetDateTime>.pastOrPresent(
    message: String = messages.pastOrPresent
): Unit = constraint(
    message = message,
    code = "pastOrPresent",
    predicate = { !it.isAfter(OffsetDateTime.now(clock)) }
)

@JvmName("pastOrPresentInstant")
public fun ValidationNode<Instant>.pastOrPresent(
    message: String = messages.pastOrPresent
): Unit = constraint(
    message = message,
    code = "pastOrPresent",
    predicate = { !it.isAfter(Instant.now(clock)) }
)

@JvmName("pastOrPresentYearMonth")
public fun ValidationNode<YearMonth>.pastOrPresent(
    message: String = messages.pastOrPresent
): Unit = constraint(
    message = message,
    code = "pastOrPresent",
    predicate = { !it.isAfter(YearMonth.now(clock)) }
)

@JvmName("pastOrPresentYear")
public fun ValidationNode<Year>.pastOrPresent(
    message: String = messages.pastOrPresent
): Unit = constraint(
    message = message,
    code = "pastOrPresent",
    predicate = { !it.isAfter(Year.now(clock)) }
)

@JvmName("pastOrPresentDate")
public fun ValidationNode<Date>.pastOrPresent(
    message: String = messages.pastOrPresent
): Unit = constraint(
    message = message,
    code = "pastOrPresent",
    predicate = { !it.after(Date(clock.millis())) }
)

public fun ValidationNode<LocalDate>.futureOrPresent(
    message: String = messages.futureOrPresent
): Unit = constraint(
    message = message,
    code = "futureOrPresent",
    predicate = { !it.isBefore(LocalDate.now(clock)) }
)

@JvmName("futureOrPresentLocalDateTime")
public fun ValidationNode<LocalDateTime>.futureOrPresent(
    message: String = messages.futureOrPresent
): Unit = constraint(
    message = message,
    code = "futureOrPresent",
    predicate = { !it.isBefore(LocalDateTime.now(clock)) }
)

@JvmName("futureOrPresentZonedDateTime")
public fun ValidationNode<ZonedDateTime>.futureOrPresent(
    message: String = messages.futureOrPresent
): Unit = constraint(
    message = message,
    code = "futureOrPresent",
    predicate = { !it.isBefore(ZonedDateTime.now(clock)) }
)

@JvmName("futureOrPresentOffsetDateTime")
public fun ValidationNode<OffsetDateTime>.futureOrPresent(
    message: String = messages.futureOrPresent
): Unit = constraint(
    message = message,
    code = "futureOrPresent",
    predicate = { !it.isBefore(OffsetDateTime.now(clock)) }
)

@JvmName("futureOrPresentInstant")
public fun ValidationNode<Instant>.futureOrPresent(
    message: String = messages.futureOrPresent
): Unit = constraint(
    message = message,
    code = "futureOrPresent",
    predicate = { !it.isBefore(Instant.now(clock)) }
)

@JvmName("futureOrPresentYearMonth")
public fun ValidationNode<YearMonth>.futureOrPresent(
    message: String = messages.futureOrPresent
): Unit = constraint(
    message = message,
    code = "futureOrPresent",
    predicate = { !it.isBefore(YearMonth.now(clock)) }
)

@JvmName("futureOrPresentYear")
public fun ValidationNode<Year>.futureOrPresent(
    message: String = messages.futureOrPresent
): Unit = constraint(
    message = message,
    code = "futureOrPresent",
    predicate = { !it.isBefore(Year.now(clock)) }
)

@JvmName("futureOrPresentDate")
public fun ValidationNode<Date>.futureOrPresent(
    message: String = messages.futureOrPresent
): Unit = constraint(
    message = message,
    code = "futureOrPresent",
    predicate = { !it.before(Date(clock.millis())) }
)

public fun ValidationNode<LocalDate>.after(
    other: LocalDate?,
    message: String = messages.after
): Unit = after(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<LocalDate>.after(
    other: LocalDate?,
    message: suspend LocalDate.(LocalDate) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "after",
    predicate = { value, argument -> value.isAfter(argument) }
)

public fun ValidationNode<LocalDateTime>.after(
    other: LocalDateTime?,
    message: String = messages.after
): Unit = after(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<LocalDateTime>.after(
    other: LocalDateTime?,
    message: suspend LocalDateTime.(LocalDateTime) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "after",
    predicate = { value, argument -> value.isAfter(argument) }
)

public fun ValidationNode<LocalTime>.after(
    other: LocalTime?,
    message: String = messages.after
): Unit = after(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<LocalTime>.after(
    other: LocalTime?,
    message: suspend LocalTime.(LocalTime) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "after",
    predicate = { value, argument -> value.isAfter(argument) }
)

public fun ValidationNode<OffsetTime>.after(
    other: OffsetTime?,
    message: String = messages.after
): Unit = after(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<OffsetTime>.after(
    other: OffsetTime?,
    message: suspend OffsetTime.(OffsetTime) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "after",
    predicate = { value, argument -> value.isAfter(argument) }
)

public fun ValidationNode<ZonedDateTime>.after(
    other: ZonedDateTime?,
    message: String = messages.after
): Unit = after(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<ZonedDateTime>.after(
    other: ZonedDateTime?,
    message: suspend ZonedDateTime.(ZonedDateTime) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "after",
    predicate = { value, argument -> value.isAfter(argument) }
)

public fun ValidationNode<OffsetDateTime>.after(
    other: OffsetDateTime?,
    message: String = messages.after
): Unit = after(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<OffsetDateTime>.after(
    other: OffsetDateTime?,
    message: suspend OffsetDateTime.(OffsetDateTime) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "after",
    predicate = { value, argument -> value.isAfter(argument) }
)

public fun ValidationNode<Instant>.after(
    other: Instant?,
    message: String = messages.after
): Unit = after(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<Instant>.after(
    other: Instant?,
    message: suspend Instant.(Instant) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "after",
    predicate = { value, argument -> value.isAfter(argument) }
)

public fun ValidationNode<YearMonth>.after(
    other: YearMonth?,
    message: String = messages.after
): Unit = after(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<YearMonth>.after(
    other: YearMonth?,
    message: suspend YearMonth.(YearMonth) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "after",
    predicate = { value, argument -> value.isAfter(argument) }
)

public fun ValidationNode<Year>.after(
    other: Year?,
    message: String = messages.after
): Unit = after(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<Year>.after(
    other: Year?,
    message: suspend Year.(Year) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "after",
    predicate = { value, argument -> value.isAfter(argument) }
)

public fun ValidationNode<Date>.after(
    other: Date?,
    message: String = messages.after
): Unit = after(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<Date>.after(
    other: Date?,
    message: suspend Date.(Date) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "after",
    predicate = { value, argument -> value.after(argument) }
)

public fun ValidationNode<LocalDate>.before(
    other: LocalDate?,
    message: String = messages.before
): Unit = before(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<LocalDate>.before(
    other: LocalDate?,
    message: suspend LocalDate.(LocalDate) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "before",
    predicate = { value, argument -> value.isBefore(argument) }
)

public fun ValidationNode<LocalDateTime>.before(
    other: LocalDateTime?,
    message: String = messages.before
): Unit = before(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<LocalDateTime>.before(
    other: LocalDateTime?,
    message: suspend LocalDateTime.(LocalDateTime) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "before",
    predicate = { value, argument -> value.isBefore(argument) }
)

public fun ValidationNode<LocalTime>.before(
    other: LocalTime?,
    message: String = messages.before
): Unit = before(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<LocalTime>.before(
    other: LocalTime?,
    message: suspend LocalTime.(LocalTime) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "before",
    predicate = { value, argument -> value.isBefore(argument) }
)

public fun ValidationNode<OffsetTime>.before(
    other: OffsetTime?,
    message: String = messages.before
): Unit = before(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<OffsetTime>.before(
    other: OffsetTime?,
    message: suspend OffsetTime.(OffsetTime) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "before",
    predicate = { value, argument -> value.isBefore(argument) }
)

public fun ValidationNode<ZonedDateTime>.before(
    other: ZonedDateTime?,
    message: String = messages.before
): Unit = before(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<ZonedDateTime>.before(
    other: ZonedDateTime?,
    message: suspend ZonedDateTime.(ZonedDateTime) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "before",
    predicate = { value, argument -> value.isBefore(argument) }
)

public fun ValidationNode<OffsetDateTime>.before(
    other: OffsetDateTime?,
    message: String = messages.before
): Unit = before(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<OffsetDateTime>.before(
    other: OffsetDateTime?,
    message: suspend OffsetDateTime.(OffsetDateTime) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "before",
    predicate = { value, argument -> value.isBefore(argument) }
)

public fun ValidationNode<Instant>.before(
    other: Instant?,
    message: String = messages.before
): Unit = before(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<Instant>.before(
    other: Instant?,
    message: suspend Instant.(Instant) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "before",
    predicate = { value, argument -> value.isBefore(argument) }
)

public fun ValidationNode<YearMonth>.before(
    other: YearMonth?,
    message: String = messages.before
): Unit = before(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<YearMonth>.before(
    other: YearMonth?,
    message: suspend YearMonth.(YearMonth) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "before",
    predicate = { value, argument -> value.isBefore(argument) }
)

public fun ValidationNode<Year>.before(
    other: Year?,
    message: String = messages.before
): Unit = before(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<Year>.before(
    other: Year?,
    message: suspend Year.(Year) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "before",
    predicate = { value, argument -> value.isBefore(argument) }
)

public fun ValidationNode<Date>.before(
    other: Date?,
    message: String = messages.before
): Unit = before(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<Date>.before(
    other: Date?,
    message: suspend Date.(Date) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "before",
    predicate = { value, argument -> value.before(argument) }
)

public fun ValidationNode<LocalDate>.afterOrEqual(
    other: LocalDate?,
    message: String = messages.afterOrEqual
): Unit = afterOrEqual(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<LocalDate>.afterOrEqual(
    other: LocalDate?,
    message: suspend LocalDate.(LocalDate) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "afterOrEqual",
    predicate = { value, argument -> !value.isBefore(argument) }
)

public fun ValidationNode<LocalDateTime>.afterOrEqual(
    other: LocalDateTime?,
    message: String = messages.afterOrEqual
): Unit = afterOrEqual(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<LocalDateTime>.afterOrEqual(
    other: LocalDateTime?,
    message: suspend LocalDateTime.(LocalDateTime) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "afterOrEqual",
    predicate = { value, argument -> !value.isBefore(argument) }
)

public fun ValidationNode<LocalTime>.afterOrEqual(
    other: LocalTime?,
    message: String = messages.afterOrEqual
): Unit = afterOrEqual(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<LocalTime>.afterOrEqual(
    other: LocalTime?,
    message: suspend LocalTime.(LocalTime) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "afterOrEqual",
    predicate = { value, argument -> !value.isBefore(argument) }
)

public fun ValidationNode<OffsetTime>.afterOrEqual(
    other: OffsetTime?,
    message: String = messages.afterOrEqual
): Unit = afterOrEqual(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<OffsetTime>.afterOrEqual(
    other: OffsetTime?,
    message: suspend OffsetTime.(OffsetTime) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "afterOrEqual",
    predicate = { value, argument -> !value.isBefore(argument) }
)

public fun ValidationNode<ZonedDateTime>.afterOrEqual(
    other: ZonedDateTime?,
    message: String = messages.afterOrEqual
): Unit = afterOrEqual(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<ZonedDateTime>.afterOrEqual(
    other: ZonedDateTime?,
    message: suspend ZonedDateTime.(ZonedDateTime) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "afterOrEqual",
    predicate = { value, argument -> !value.isBefore(argument) }
)

public fun ValidationNode<OffsetDateTime>.afterOrEqual(
    other: OffsetDateTime?,
    message: String = messages.afterOrEqual
): Unit = afterOrEqual(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<OffsetDateTime>.afterOrEqual(
    other: OffsetDateTime?,
    message: suspend OffsetDateTime.(OffsetDateTime) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "afterOrEqual",
    predicate = { value, argument -> !value.isBefore(argument) }
)

public fun ValidationNode<Instant>.afterOrEqual(
    other: Instant?,
    message: String = messages.afterOrEqual
): Unit = afterOrEqual(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<Instant>.afterOrEqual(
    other: Instant?,
    message: suspend Instant.(Instant) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "afterOrEqual",
    predicate = { value, argument -> !value.isBefore(argument) }
)

public fun ValidationNode<YearMonth>.afterOrEqual(
    other: YearMonth?,
    message: String = messages.afterOrEqual
): Unit = afterOrEqual(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<YearMonth>.afterOrEqual(
    other: YearMonth?,
    message: suspend YearMonth.(YearMonth) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "afterOrEqual",
    predicate = { value, argument -> !value.isBefore(argument) }
)

public fun ValidationNode<Year>.afterOrEqual(
    other: Year?,
    message: String = messages.afterOrEqual
): Unit = afterOrEqual(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<Year>.afterOrEqual(
    other: Year?,
    message: suspend Year.(Year) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "afterOrEqual",
    predicate = { value, argument -> !value.isBefore(argument) }
)

public fun ValidationNode<Date>.afterOrEqual(
    other: Date?,
    message: String = messages.afterOrEqual
): Unit = afterOrEqual(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<Date>.afterOrEqual(
    other: Date?,
    message: suspend Date.(Date) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "afterOrEqual",
    predicate = { value, argument -> !value.before(argument) }
)

public fun ValidationNode<LocalDate>.beforeOrEqual(
    other: LocalDate?,
    message: String = messages.beforeOrEqual
): Unit = beforeOrEqual(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<LocalDate>.beforeOrEqual(
    other: LocalDate?,
    message: suspend LocalDate.(LocalDate) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "beforeOrEqual",
    predicate = { value, argument -> !value.isAfter(argument) }
)

public fun ValidationNode<LocalDateTime>.beforeOrEqual(
    other: LocalDateTime?,
    message: String = messages.beforeOrEqual
): Unit = beforeOrEqual(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<LocalDateTime>.beforeOrEqual(
    other: LocalDateTime?,
    message: suspend LocalDateTime.(LocalDateTime) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "beforeOrEqual",
    predicate = { value, argument -> !value.isAfter(argument) }
)

public fun ValidationNode<LocalTime>.beforeOrEqual(
    other: LocalTime?,
    message: String = messages.beforeOrEqual
): Unit = beforeOrEqual(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<LocalTime>.beforeOrEqual(
    other: LocalTime?,
    message: suspend LocalTime.(LocalTime) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "beforeOrEqual",
    predicate = { value, argument -> !value.isAfter(argument) }
)

public fun ValidationNode<OffsetTime>.beforeOrEqual(
    other: OffsetTime?,
    message: String = messages.beforeOrEqual
): Unit = beforeOrEqual(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<OffsetTime>.beforeOrEqual(
    other: OffsetTime?,
    message: suspend OffsetTime.(OffsetTime) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "beforeOrEqual",
    predicate = { value, argument -> !value.isAfter(argument) }
)

public fun ValidationNode<ZonedDateTime>.beforeOrEqual(
    other: ZonedDateTime?,
    message: String = messages.beforeOrEqual
): Unit = beforeOrEqual(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<ZonedDateTime>.beforeOrEqual(
    other: ZonedDateTime?,
    message: suspend ZonedDateTime.(ZonedDateTime) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "beforeOrEqual",
    predicate = { value, argument -> !value.isAfter(argument) }
)

public fun ValidationNode<OffsetDateTime>.beforeOrEqual(
    other: OffsetDateTime?,
    message: String = messages.beforeOrEqual
): Unit = beforeOrEqual(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<OffsetDateTime>.beforeOrEqual(
    other: OffsetDateTime?,
    message: suspend OffsetDateTime.(OffsetDateTime) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "beforeOrEqual",
    predicate = { value, argument -> !value.isAfter(argument) }
)

public fun ValidationNode<Instant>.beforeOrEqual(
    other: Instant?,
    message: String = messages.beforeOrEqual
): Unit = beforeOrEqual(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<Instant>.beforeOrEqual(
    other: Instant?,
    message: suspend Instant.(Instant) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "beforeOrEqual",
    predicate = { value, argument -> !value.isAfter(argument) }
)

public fun ValidationNode<YearMonth>.beforeOrEqual(
    other: YearMonth?,
    message: String = messages.beforeOrEqual
): Unit = beforeOrEqual(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<YearMonth>.beforeOrEqual(
    other: YearMonth?,
    message: suspend YearMonth.(YearMonth) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "beforeOrEqual",
    predicate = { value, argument -> !value.isAfter(argument) }
)

public fun ValidationNode<Year>.beforeOrEqual(
    other: Year?,
    message: String = messages.beforeOrEqual
): Unit = beforeOrEqual(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<Year>.beforeOrEqual(
    other: Year?,
    message: suspend Year.(Year) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "beforeOrEqual",
    predicate = { value, argument -> !value.isAfter(argument) }
)

public fun ValidationNode<Date>.beforeOrEqual(
    other: Date?,
    message: String = messages.beforeOrEqual
): Unit = beforeOrEqual(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<Date>.beforeOrEqual(
    other: Date?,
    message: suspend Date.(Date) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "beforeOrEqual",
    predicate = { value, argument -> !value.after(argument) }
)

public fun ValidationNode<LocalDate>.between(
    start: LocalDate?,
    end: LocalDate?,
    message: String = messages.betweenDates
): Unit = between(
    start = start,
    end = end,
    message = { _, _ -> message.filled("start" to start, "end" to end) }
)

public fun ValidationNode<LocalDate>.between(
    start: LocalDate?,
    end: LocalDate?,
    message: suspend LocalDate.(LocalDate, LocalDate) -> String
): Unit = constraint(
    first = start,
    second = end,
    message = message,
    code = "between",
    predicate = { value, first, second -> !value.isBefore(first) && !value.isAfter(second) }
)

public fun ValidationNode<LocalDateTime>.between(
    start: LocalDateTime?,
    end: LocalDateTime?,
    message: String = messages.betweenDates
): Unit = between(
    start = start,
    end = end,
    message = { _, _ -> message.filled("start" to start, "end" to end) }
)

public fun ValidationNode<LocalDateTime>.between(
    start: LocalDateTime?,
    end: LocalDateTime?,
    message: suspend LocalDateTime.(LocalDateTime, LocalDateTime) -> String
): Unit = constraint(
    first = start,
    second = end,
    message = message,
    code = "between",
    predicate = { value, first, second -> !value.isBefore(first) && !value.isAfter(second) }
)

public fun ValidationNode<LocalTime>.between(
    start: LocalTime?,
    end: LocalTime?,
    message: String = messages.betweenDates
): Unit = between(
    start = start,
    end = end,
    message = { _, _ -> message.filled("start" to start, "end" to end) }
)

public fun ValidationNode<LocalTime>.between(
    start: LocalTime?,
    end: LocalTime?,
    message: suspend LocalTime.(LocalTime, LocalTime) -> String
): Unit = constraint(
    first = start,
    second = end,
    message = message,
    code = "between",
    predicate = { value, first, second -> !value.isBefore(first) && !value.isAfter(second) }
)

public fun ValidationNode<OffsetTime>.between(
    start: OffsetTime?,
    end: OffsetTime?,
    message: String = messages.betweenDates
): Unit = between(
    start = start,
    end = end,
    message = { _, _ -> message.filled("start" to start, "end" to end) }
)

public fun ValidationNode<OffsetTime>.between(
    start: OffsetTime?,
    end: OffsetTime?,
    message: suspend OffsetTime.(OffsetTime, OffsetTime) -> String
): Unit = constraint(
    first = start,
    second = end,
    message = message,
    code = "between",
    predicate = { value, first, second -> !value.isBefore(first) && !value.isAfter(second) }
)

public fun ValidationNode<ZonedDateTime>.between(
    start: ZonedDateTime?,
    end: ZonedDateTime?,
    message: String = messages.betweenDates
): Unit = between(
    start = start,
    end = end,
    message = { _, _ -> message.filled("start" to start, "end" to end) }
)

public fun ValidationNode<ZonedDateTime>.between(
    start: ZonedDateTime?,
    end: ZonedDateTime?,
    message: suspend ZonedDateTime.(ZonedDateTime, ZonedDateTime) -> String
): Unit = constraint(
    first = start,
    second = end,
    message = message,
    code = "between",
    predicate = { value, first, second -> !value.isBefore(first) && !value.isAfter(second) }
)

public fun ValidationNode<OffsetDateTime>.between(
    start: OffsetDateTime?,
    end: OffsetDateTime?,
    message: String = messages.betweenDates
): Unit = between(
    start = start,
    end = end,
    message = { _, _ -> message.filled("start" to start, "end" to end) }
)

public fun ValidationNode<OffsetDateTime>.between(
    start: OffsetDateTime?,
    end: OffsetDateTime?,
    message: suspend OffsetDateTime.(OffsetDateTime, OffsetDateTime) -> String
): Unit = constraint(
    first = start,
    second = end,
    message = message,
    code = "between",
    predicate = { value, first, second -> !value.isBefore(first) && !value.isAfter(second) }
)

public fun ValidationNode<Instant>.between(
    start: Instant?,
    end: Instant?,
    message: String = messages.betweenDates
): Unit = between(
    start = start,
    end = end,
    message = { _, _ -> message.filled("start" to start, "end" to end) }
)

public fun ValidationNode<Instant>.between(
    start: Instant?,
    end: Instant?,
    message: suspend Instant.(Instant, Instant) -> String
): Unit = constraint(
    first = start,
    second = end,
    message = message,
    code = "between",
    predicate = { value, first, second -> !value.isBefore(first) && !value.isAfter(second) }
)

public fun ValidationNode<YearMonth>.between(
    start: YearMonth?,
    end: YearMonth?,
    message: String = messages.betweenDates
): Unit = between(
    start = start,
    end = end,
    message = { _, _ -> message.filled("start" to start, "end" to end) }
)

public fun ValidationNode<YearMonth>.between(
    start: YearMonth?,
    end: YearMonth?,
    message: suspend YearMonth.(YearMonth, YearMonth) -> String
): Unit = constraint(
    first = start,
    second = end,
    message = message,
    code = "between",
    predicate = { value, first, second -> !value.isBefore(first) && !value.isAfter(second) }
)

public fun ValidationNode<Year>.between(
    start: Year?,
    end: Year?,
    message: String = messages.betweenDates
): Unit = between(
    start = start,
    end = end,
    message = { _, _ -> message.filled("start" to start, "end" to end) }
)

public fun ValidationNode<Year>.between(
    start: Year?,
    end: Year?,
    message: suspend Year.(Year, Year) -> String
): Unit = constraint(
    first = start,
    second = end,
    message = message,
    code = "between",
    predicate = { value, first, second -> !value.isBefore(first) && !value.isAfter(second) }
)

public fun ValidationNode<Date>.between(
    start: Date?,
    end: Date?,
    message: String = messages.betweenDates
): Unit = between(
    start = start,
    end = end,
    message = { _, _ -> message.filled("start" to start, "end" to end) }
)

public fun ValidationNode<Date>.between(
    start: Date?,
    end: Date?,
    message: suspend Date.(Date, Date) -> String
): Unit = constraint(
    first = start,
    second = end,
    message = message,
    code = "between",
    predicate = { value, first, second -> !value.before(first) && !value.after(second) }
)

public fun ValidationNode<Duration>.min(
    min: Duration?,
    message: String = messages.min
): Unit = min(
    min = min,
    message = { message.filled("min" to min) }
)

public fun ValidationNode<Duration>.min(
    min: Duration?,
    message: suspend Duration.(Duration) -> String
): Unit = constraint(
    argument = min,
    message = message,
    code = "min",
    predicate = { value, argument -> value >= argument }
)

public fun ValidationNode<Duration>.max(
    max: Duration?,
    message: String = messages.max
): Unit = max(
    max = max,
    message = { message.filled("max" to max) }
)

public fun ValidationNode<Duration>.max(
    max: Duration?,
    message: suspend Duration.(Duration) -> String
): Unit = constraint(
    argument = max,
    message = message,
    code = "max",
    predicate = { value, argument -> value <= argument }
)

public fun ValidationNode<Duration>.greaterThan(
    other: Duration?,
    message: String = messages.greaterThan
): Unit = greaterThan(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<Duration>.greaterThan(
    other: Duration?,
    message: suspend Duration.(Duration) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "greaterThan",
    predicate = { value, argument -> value > argument }
)

public fun ValidationNode<Duration>.lessThan(
    other: Duration?,
    message: String = messages.lessThan
): Unit = lessThan(
    other = other,
    message = { message.filled("other" to other) }
)

public fun ValidationNode<Duration>.lessThan(
    other: Duration?,
    message: suspend Duration.(Duration) -> String
): Unit = constraint(
    argument = other,
    message = message,
    code = "lessThan",
    predicate = { value, argument -> value < argument }
)

public fun ValidationNode<Duration>.between(
    min: Duration?,
    max: Duration?,
    message: String = messages.between
): Unit = between(
    min = min,
    max = max,
    message = { _, _ -> message.filled("min" to min, "max" to max) }
)

public fun ValidationNode<Duration>.between(
    min: Duration?,
    max: Duration?,
    message: suspend Duration.(Duration, Duration) -> String
): Unit = constraint(
    first = min,
    second = max,
    message = message,
    code = "between",
    predicate = { value, first, second -> value in first..second }
)
