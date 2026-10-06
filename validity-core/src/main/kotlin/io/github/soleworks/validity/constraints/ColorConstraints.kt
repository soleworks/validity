package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.FormatMessages
import io.github.soleworks.validity.ValidationNode

private const val MAX_CHANNEL = 255
private const val MAX_HUE = 360.0
private const val MAX_PERCENTAGE = 100.0
private const val ALPHA = "(?:\\s*,\\s*(?:0|1|0?\\.\\d+))?"

private val HEX_COLOR_FORMAT = Regex("#(?:[0-9a-fA-F]{3}|[0-9a-fA-F]{4}|[0-9a-fA-F]{6}|[0-9a-fA-F]{8})")
private val RGB_COLOR_FORMAT = Regex("rgba?\\(\\s*(\\d{1,3})\\s*,\\s*(\\d{1,3})\\s*,\\s*(\\d{1,3})$ALPHA\\s*\\)")
private val HSL_COLOR_FORMAT = Regex(
    "hsla?\\(\\s*(\\d{1,3}(?:\\.\\d+)?)\\s*,\\s*(\\d{1,3}(?:\\.\\d+)?)%\\s*,\\s*(\\d{1,3}(?:\\.\\d+)?)%$ALPHA\\s*\\)"
)

public fun ValidationNode<String>.hexColor(
    message: String = FormatMessages.HEX_COLOR
): Unit = constraint(
    message = message,
    predicate = { HEX_COLOR_FORMAT.matches(it) }
)

public fun ValidationNode<String>.rgbColor(
    message: String = FormatMessages.RGB_COLOR
): Unit = constraint(
    message = message,
    predicate = { it.isRgbColor() }
)

public fun ValidationNode<String>.hslColor(
    message: String = FormatMessages.HSL_COLOR
): Unit = constraint(
    message = message,
    predicate = { it.isHslColor() }
)

private fun String.isRgbColor(): Boolean = RGB_COLOR_FORMAT.matchEntire(this)
    ?.groupValues
    ?.drop(1)
    ?.all { channel -> channel.toInt() <= MAX_CHANNEL } == true

private fun String.isHslColor(): Boolean {
    val values = HSL_COLOR_FORMAT.matchEntire(this)?.groupValues?.drop(1)?.map(String::toDouble) ?: return false

    return values[0] <= MAX_HUE && values[1] <= MAX_PERCENTAGE && values[2] <= MAX_PERCENTAGE
}
