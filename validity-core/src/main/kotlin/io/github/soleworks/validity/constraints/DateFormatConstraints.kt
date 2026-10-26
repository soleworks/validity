package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.filled
import io.github.soleworks.validity.messages
import java.time.LocalDate
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

private const val ISO_DURATION_DATE = """(?:\d+Y)?(?:\d+M)?(?:\d+W)?(?:\d+D)?"""
private const val ISO_DURATION_TIME = """(?:T(?=\d)(?:\d+H)?(?:\d+M)?(?:\d+(?:\.\d+)?S)?)?"""

private val ISO_DURATION_FORMAT = Regex("P(?!$)$ISO_DURATION_DATE$ISO_DURATION_TIME")

/**
 * Checks that the string is an ISO 8601 date, such as `2024-02-29`, that exists in the calendar.
 *
 * Fails with code `isoDate` and, by default, the message `must be an ISO 8601 date`
 * ([Messages.isoDate][io.github.soleworks.validity.Messages.isoDate]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.isoDate(
    message: String = messages.isoDate
): Unit = constraint(
    message = message,
    code = "isoDate",
    predicate = { runCatching { LocalDate.parse(it) }.isSuccess }
)

/**
 * Checks that the string is an ISO 8601 time, such as `13:45` or `13:45:30.5`, with no offset.
 *
 * Fails with code `isoTime` and, by default, the message `must be an ISO 8601 time`
 * ([Messages.isoTime][io.github.soleworks.validity.Messages.isoTime]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.isoTime(
    message: String = messages.isoTime
): Unit = constraint(
    message = message,
    code = "isoTime",
    predicate = { runCatching { LocalTime.parse(it) }.isSuccess }
)

/**
 * Checks that the string is an ISO 8601 date and time with an offset, such as `2024-02-29T13:45:30Z` or
 * `2024-02-29T13:45:30+01:00`.
 *
 * Fails with code `isoDateTime` and, by default, the message `must be an ISO 8601 date and time with offset`
 * ([Messages.isoDateTime][io.github.soleworks.validity.Messages.isoDateTime]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.isoDateTime(
    message: String = messages.isoDateTime
): Unit = constraint(
    message = message,
    code = "isoDateTime",
    predicate = { runCatching { OffsetDateTime.parse(it) }.isSuccess }
)

/**
 * Checks that the string is an ISO 8601 duration, such as `P1Y2M`, `P3W` or `PT1H30M`.
 *
 * Fails with code `isoDuration` and, by default, the message `must be an ISO 8601 duration`
 * ([Messages.isoDuration][io.github.soleworks.validity.Messages.isoDuration]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.isoDuration(
    message: String = messages.isoDuration
): Unit = constraint(
    message = message,
    code = "isoDuration",
    predicate = { ISO_DURATION_FORMAT.matches(it) }
)

/**
 * Checks that the string is a date or time written exactly in [pattern].
 *
 * The string is parsed with [DateTimeFormatter.ofPattern][java.time.format.DateTimeFormatter.ofPattern] and must print
 * back the same, so `2024-2-9` does not match `yyyy-MM-dd`.
 *
 * Fails with code `dateFormat` and, by default, the message `must match the date format {pattern}`
 * ([Messages.dateFormat][io.github.soleworks.validity.Messages.dateFormat]).
 *
 * ```kotlin
 * ::birthDate required { dateFormat("dd/MM/yyyy") }
 * ```
 *
 * @param pattern the pattern, as [java.time.format.DateTimeFormatter] reads it.
 * @param message the message of the violation. `{pattern}`, `{path}` and `{value}` are filled in.
 * @throws IllegalArgumentException when the value is checked and [pattern] is not a valid pattern.
 */
public fun ValidationNode<String>.dateFormat(
    pattern: String,
    message: String = messages.dateFormat
): Unit = dateFormat(
    pattern = pattern,
    message = { message.filled("pattern" to pattern) }
)

/**
 * Checks that the string is a date or time written exactly in [pattern].
 *
 * The string is parsed with [DateTimeFormatter.ofPattern][java.time.format.DateTimeFormatter.ofPattern] and must print
 * back the same, so `2024-2-9` does not match `yyyy-MM-dd`.
 *
 * Fails with code `dateFormat` and the message that [message] builds.
 *
 * @param pattern the pattern, as [java.time.format.DateTimeFormatter] reads it.
 * @param message builds the message from the value (the receiver) and [pattern].
 * @throws IllegalArgumentException when the value is checked and [pattern] is not a valid pattern.
 */
public fun ValidationNode<String>.dateFormat(
    pattern: String,
    message: suspend String.(String) -> String
): Unit = constraint(
    message = { it.message(pattern) },
    code = "dateFormat",
    predicate = { it.matchesDateFormat(DateTimeFormatter.ofPattern(pattern)) }
)

private fun String.matchesDateFormat(
    formatter: DateTimeFormatter
): Boolean = runCatching { formatter.format(formatter.parse(this)) == this }.getOrDefault(false)
