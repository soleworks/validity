package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.messages
import java.time.LocalDate
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

private const val ISO_DURATION_DATE = "(?:\\d+Y)?(?:\\d+M)?(?:\\d+W)?(?:\\d+D)?"
private const val ISO_DURATION_TIME = "(?:T(?=\\d)(?:\\d+H)?(?:\\d+M)?(?:\\d+(?:\\.\\d+)?S)?)?"

private val ISO_DURATION_FORMAT = Regex("P(?!$)$ISO_DURATION_DATE$ISO_DURATION_TIME")

public fun ValidationNode<String>.isoDate(
    message: String = messages.isoDate
): Unit = constraint(
    message = message,
    code = "isoDate",
    predicate = { runCatching { LocalDate.parse(it) }.isSuccess }
)

public fun ValidationNode<String>.isoTime(
    message: String = messages.isoTime
): Unit = constraint(
    message = message,
    code = "isoTime",
    predicate = { runCatching { LocalTime.parse(it) }.isSuccess }
)

public fun ValidationNode<String>.isoDateTime(
    message: String = messages.isoDateTime
): Unit = constraint(
    message = message,
    code = "isoDateTime",
    predicate = { runCatching { OffsetDateTime.parse(it) }.isSuccess }
)

public fun ValidationNode<String>.isoDuration(
    message: String = messages.isoDuration
): Unit = constraint(
    message = message,
    code = "isoDuration",
    predicate = { ISO_DURATION_FORMAT.matches(it) }
)

public fun ValidationNode<String>.dateFormat(
    pattern: String,
    message: String = messages.dateFormat
): Unit = dateFormat(
    pattern = pattern,
    message = { message.replace("{pattern}", pattern) }
)

public fun ValidationNode<String>.dateFormat(
    pattern: String,
    message: String.(String) -> String
): Unit = constraint(
    message = { it.message(pattern) },
    code = "dateFormat",
    predicate = { it.matchesDateFormat(DateTimeFormatter.ofPattern(pattern)) }
)

private fun String.matchesDateFormat(formatter: DateTimeFormatter): Boolean =
    runCatching { formatter.format(formatter.parse(this)) == this }.getOrDefault(false)
