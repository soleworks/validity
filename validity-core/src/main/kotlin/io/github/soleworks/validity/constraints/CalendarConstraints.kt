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

public fun ValidationNode<LocalDate>.minAge(
    min: Int?,
    message: String = messages.minAge
): Unit = minAge(
    min = min,
    message = { message.filled("min" to min) }
)

public fun ValidationNode<LocalDate>.minAge(
    min: Int?,
    message: LocalDate.(Int) -> String
) {
    if (min == null)
        return

    constraint(
        message = { it.message(min) },
        code = "minAge",
        predicate = { it.age() >= min }
    )
}

public fun ValidationNode<LocalDate>.maxAge(
    max: Int?,
    message: String = messages.maxAge
): Unit = maxAge(
    max = max,
    message = { message.filled("max" to max) }
)

public fun ValidationNode<LocalDate>.maxAge(
    max: Int?,
    message: LocalDate.(Int) -> String
) {
    if (max == null)
        return

    constraint(
        message = { it.message(max) },
        code = "maxAge",
        predicate = { it.age() <= max }
    )
}

public fun ValidationNode<LocalDate>.withinNext(
    duration: Duration?,
    message: String = messages.withinNext
): Unit = withinNext(
    duration = duration,
    message = { message.filled("duration" to duration) }
)

public fun ValidationNode<LocalDate>.withinNext(
    duration: Duration?,
    message: LocalDate.(Duration) -> String
): Unit = within(
    duration = duration,
    code = "withinNext",
    message = message,
    window = { value, today, length -> value in today..today.plusDays(length.inWholeDays) },
    now = { LocalDate.now(clock) }
)

@JvmName("withinNextLocalDateTime")
public fun ValidationNode<LocalDateTime>.withinNext(
    duration: Duration?,
    message: String = messages.withinNext
): Unit = withinNext(
    duration = duration,
    message = { message.filled("duration" to duration) }
)

@JvmName("withinNextLocalDateTime")
public fun ValidationNode<LocalDateTime>.withinNext(
    duration: Duration?,
    message: LocalDateTime.(Duration) -> String
): Unit = within(
    duration = duration,
    code = "withinNext",
    message = message,
    window = { value, now, length -> value in now..now.plus(length.toJavaDuration()) },
    now = { LocalDateTime.now(clock) }
)

@JvmName("withinNextZonedDateTime")
public fun ValidationNode<ZonedDateTime>.withinNext(
    duration: Duration?,
    message: String = messages.withinNext
): Unit = withinNext(
    duration = duration,
    message = { message.filled("duration" to duration) }
)

@JvmName("withinNextZonedDateTime")
public fun ValidationNode<ZonedDateTime>.withinNext(
    duration: Duration?,
    message: ZonedDateTime.(Duration) -> String
): Unit = within(
    duration = duration,
    code = "withinNext",
    message = message,
    window = { value, now, length -> value in now..now.plus(length.toJavaDuration()) },
    now = { ZonedDateTime.now(clock) }
)

@JvmName("withinNextOffsetDateTime")
public fun ValidationNode<OffsetDateTime>.withinNext(
    duration: Duration?,
    message: String = messages.withinNext
): Unit = withinNext(
    duration = duration,
    message = { message.filled("duration" to duration) }
)

@JvmName("withinNextOffsetDateTime")
public fun ValidationNode<OffsetDateTime>.withinNext(
    duration: Duration?,
    message: OffsetDateTime.(Duration) -> String
): Unit = within(
    duration = duration,
    code = "withinNext",
    message = message,
    window = { value, now, length -> value in now..now.plus(length.toJavaDuration()) },
    now = { OffsetDateTime.now(clock) }
)

@JvmName("withinNextInstant")
public fun ValidationNode<Instant>.withinNext(
    duration: Duration?,
    message: String = messages.withinNext
): Unit = withinNext(
    duration = duration,
    message = { message.filled("duration" to duration) }
)

@JvmName("withinNextInstant")
public fun ValidationNode<Instant>.withinNext(
    duration: Duration?,
    message: Instant.(Duration) -> String
): Unit = within(
    duration = duration,
    code = "withinNext",
    message = message,
    window = { value, now, length -> value in now..now.plus(length.toJavaDuration()) },
    now = { Instant.now(clock) }
)

public fun ValidationNode<LocalDate>.withinLast(
    duration: Duration?,
    message: String = messages.withinLast
): Unit = withinLast(
    duration = duration,
    message = { message.filled("duration" to duration) }
)

public fun ValidationNode<LocalDate>.withinLast(
    duration: Duration?,
    message: LocalDate.(Duration) -> String
): Unit = within(
    duration = duration,
    code = "withinLast",
    message = message,
    window = { value, today, length -> value in today.minusDays(length.inWholeDays)..today },
    now = { LocalDate.now(clock) }
)

@JvmName("withinLastLocalDateTime")
public fun ValidationNode<LocalDateTime>.withinLast(
    duration: Duration?,
    message: String = messages.withinLast
): Unit = withinLast(
    duration = duration,
    message = { message.filled("duration" to duration) }
)

@JvmName("withinLastLocalDateTime")
public fun ValidationNode<LocalDateTime>.withinLast(
    duration: Duration?,
    message: LocalDateTime.(Duration) -> String
): Unit = within(
    duration = duration,
    code = "withinLast",
    message = message,
    window = { value, now, length -> value in now.minus(length.toJavaDuration())..now },
    now = { LocalDateTime.now(clock) }
)

@JvmName("withinLastZonedDateTime")
public fun ValidationNode<ZonedDateTime>.withinLast(
    duration: Duration?,
    message: String = messages.withinLast
): Unit = withinLast(
    duration = duration,
    message = { message.filled("duration" to duration) }
)

@JvmName("withinLastZonedDateTime")
public fun ValidationNode<ZonedDateTime>.withinLast(
    duration: Duration?,
    message: ZonedDateTime.(Duration) -> String
): Unit = within(
    duration = duration,
    code = "withinLast",
    message = message,
    window = { value, now, length -> value in now.minus(length.toJavaDuration())..now },
    now = { ZonedDateTime.now(clock) }
)

@JvmName("withinLastOffsetDateTime")
public fun ValidationNode<OffsetDateTime>.withinLast(
    duration: Duration?,
    message: String = messages.withinLast
): Unit = withinLast(
    duration = duration,
    message = { message.filled("duration" to duration) }
)

@JvmName("withinLastOffsetDateTime")
public fun ValidationNode<OffsetDateTime>.withinLast(
    duration: Duration?,
    message: OffsetDateTime.(Duration) -> String
): Unit = within(
    duration = duration,
    code = "withinLast",
    message = message,
    window = { value, now, length -> value in now.minus(length.toJavaDuration())..now },
    now = { OffsetDateTime.now(clock) }
)

@JvmName("withinLastInstant")
public fun ValidationNode<Instant>.withinLast(
    duration: Duration?,
    message: String = messages.withinLast
): Unit = withinLast(
    duration = duration,
    message = { message.filled("duration" to duration) }
)

@JvmName("withinLastInstant")
public fun ValidationNode<Instant>.withinLast(
    duration: Duration?,
    message: Instant.(Duration) -> String
): Unit = within(
    duration = duration,
    code = "withinLast",
    message = message,
    window = { value, now, length -> value in now.minus(length.toJavaDuration())..now },
    now = { Instant.now(clock) }
)

public fun ValidationNode<LocalDate>.today(
    message: String = messages.today
): Unit = constraint(
    message = message,
    code = "today",
    predicate = { it == LocalDate.now(clock) }
)

@JvmName("todayLocalDateTime")
public fun ValidationNode<LocalDateTime>.today(
    message: String = messages.today
): Unit = constraint(
    message = message,
    code = "today",
    predicate = { it.toLocalDate() == LocalDate.now(clock) }
)

@JvmName("todayZonedDateTime")
public fun ValidationNode<ZonedDateTime>.today(
    message: String = messages.today
): Unit = constraint(
    message = message,
    code = "today",
    predicate = { it.withZoneSameInstant(clock.zone).toLocalDate() == LocalDate.now(clock) }
)

@JvmName("todayOffsetDateTime")
public fun ValidationNode<OffsetDateTime>.today(
    message: String = messages.today
): Unit = constraint(
    message = message,
    code = "today",
    predicate = { it.atZoneSameInstant(clock.zone).toLocalDate() == LocalDate.now(clock) }
)

public fun ValidationNode<LocalDate>.weekday(
    message: String = messages.weekday
): Unit = constraint(
    message = message,
    code = "weekday",
    predicate = { it.dayOfWeek !in WEEKEND }
)

@JvmName("weekdayLocalDateTime")
public fun ValidationNode<LocalDateTime>.weekday(
    message: String = messages.weekday
): Unit = constraint(
    message = message,
    code = "weekday",
    predicate = { it.dayOfWeek !in WEEKEND }
)

@JvmName("weekdayZonedDateTime")
public fun ValidationNode<ZonedDateTime>.weekday(
    message: String = messages.weekday
): Unit = constraint(
    message = message,
    code = "weekday",
    predicate = { it.dayOfWeek !in WEEKEND }
)

@JvmName("weekdayOffsetDateTime")
public fun ValidationNode<OffsetDateTime>.weekday(
    message: String = messages.weekday
): Unit = constraint(
    message = message,
    code = "weekday",
    predicate = { it.dayOfWeek !in WEEKEND }
)

public fun ValidationNode<LocalDate>.weekend(
    message: String = messages.weekend
): Unit = constraint(
    message = message,
    code = "weekend",
    predicate = { it.dayOfWeek in WEEKEND }
)

@JvmName("weekendLocalDateTime")
public fun ValidationNode<LocalDateTime>.weekend(
    message: String = messages.weekend
): Unit = constraint(
    message = message,
    code = "weekend",
    predicate = { it.dayOfWeek in WEEKEND }
)

@JvmName("weekendZonedDateTime")
public fun ValidationNode<ZonedDateTime>.weekend(
    message: String = messages.weekend
): Unit = constraint(
    message = message,
    code = "weekend",
    predicate = { it.dayOfWeek in WEEKEND }
)

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
    message: V.(Duration) -> String,
    window: (value: V, now: V, length: Duration) -> Boolean,
    now: () -> V
) {
    if (duration == null)
        return

    constraint(
        message = { it.message(duration) },
        code = code,
        predicate = { value -> window(value, now(), duration) }
    )
}

private fun LocalDate.age(): Int = Period.between(this, LocalDate.now(clock)).years
