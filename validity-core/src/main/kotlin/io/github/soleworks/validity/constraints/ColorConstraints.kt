package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.messages

private const val MAX_CHANNEL = 255
private const val MAX_HUE = 360.0
private const val MAX_PERCENTAGE = 100.0
private const val ALPHA = """(?:\s*,\s*(?:0?\.\d+|[01](?:\.0+)?))?"""

private val HEX_COLOR_FORMAT = Regex("#(?:[0-9a-fA-F]{3}|[0-9a-fA-F]{4}|[0-9a-fA-F]{6}|[0-9a-fA-F]{8})")
private val RGB_COLOR_FORMAT = Regex("""rgba?\(\s*(\d{1,3})\s*,\s*(\d{1,3})\s*,\s*(\d{1,3})$ALPHA\s*\)""")
private val HSL_COLOR_FORMAT = Regex(
    """hsla?\(\s*(\d{1,3}(?:\.\d+)?)\s*,\s*(\d{1,3}(?:\.\d+)?)%\s*,\s*(\d{1,3}(?:\.\d+)?)%$ALPHA\s*\)"""
)

/**
 * Checks that the string is a hexadecimal color: `#` and 3, 4, 6 or 8 hexadecimal digits, such as `#fff` or
 * `#1a2b3c80`.
 *
 * Fails with code `hexColor` and, by default, the message `must be a valid hexadecimal color`
 * ([Messages.hexColor][io.github.soleworks.validity.Messages.hexColor]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.hexColor(
    message: String = messages.hexColor
): Unit = constraint(
    message = message,
    code = "hexColor",
    predicate = { HEX_COLOR_FORMAT.matches(it) }
)

/**
 * Checks that the string is a CSS `rgb()` or `rgba()` color, such as `rgb(255, 0, 0)` or `rgba(0, 0, 0, 0.5)`.
 *
 * Each channel is a whole number from 0 to 255, and the alpha from 0 to 1.
 *
 * Fails with code `rgbColor` and, by default, the message `must be a valid RGB color`
 * ([Messages.rgbColor][io.github.soleworks.validity.Messages.rgbColor]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.rgbColor(
    message: String = messages.rgbColor
): Unit = constraint(
    message = message,
    code = "rgbColor",
    predicate = { it.isRgbColor() }
)

/**
 * Checks that the string is a CSS `hsl()` or `hsla()` color, such as `hsl(120, 100%, 50%)`.
 *
 * The hue goes from 0 to 360, saturation and lightness from 0% to 100%, and the alpha from 0 to 1.
 *
 * Fails with code `hslColor` and, by default, the message `must be a valid HSL color`
 * ([Messages.hslColor][io.github.soleworks.validity.Messages.hslColor]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.hslColor(
    message: String = messages.hslColor
): Unit = constraint(
    message = message,
    code = "hslColor",
    predicate = { it.isHslColor() }
)

private fun String.isRgbColor(): Boolean =
    RGB_COLOR_FORMAT.groups(this)?.all { channel -> channel.toInt() <= MAX_CHANNEL } == true

private fun String.isHslColor(): Boolean {
    val (hue, saturation, lightness) = HSL_COLOR_FORMAT.groups(this)?.map(String::toDouble) ?: return false

    return hue <= MAX_HUE && saturation <= MAX_PERCENTAGE && lightness <= MAX_PERCENTAGE
}
